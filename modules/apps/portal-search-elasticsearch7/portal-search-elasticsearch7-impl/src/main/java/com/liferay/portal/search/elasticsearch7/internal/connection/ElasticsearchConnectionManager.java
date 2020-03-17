/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.configuration.CrossClusterReplicationConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch7.internal.index.IndexFactory;
import com.liferay.portal.search.elasticsearch7.internal.util.LogUtil;

import java.net.InetAddress;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.elasticsearch.client.RestHighLevelClient;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration",
	immediate = true,
	service = {
		ElasticsearchClientResolver.class, ElasticsearchConnectionManager.class
	}
)
public class ElasticsearchConnectionManager
	implements ElasticsearchClientResolver {

	public ElasticsearchConnection getElasticsearchConnection() {
		return getElasticsearchConnection(null, false);
	}

	public ElasticsearchConnection getElasticsearchConnection(
		boolean preferLocalCluster) {

		return getElasticsearchConnection(null, preferLocalCluster);
	}

	public ElasticsearchConnection getElasticsearchConnection(
		String connectionId) {

		if (_log.isInfoEnabled()) {
			_log.info("Getting connection with ID: " + connectionId);
		}

		return _elasticsearchConnections.get(connectionId);
	}

	public String getLocalClusterConnectionId() {
		ClusterNode localClusterNode = _clusterExecutor.getLocalClusterNode();

		if (localClusterNode == null) {
			List<String> localClusterConnectionIds =
				getLocalClusterConnectionIds();

			return localClusterConnectionIds.get(0);
		}

		InetAddress portalInetAddress = localClusterNode.getPortalInetAddress();

		if (portalInetAddress == null) {
			return null;
		}

		String localClusterNodeHostName =
			portalInetAddress.getHostName() + StringPool.COLON +
				localClusterNode.getPortalPort();

		String[] localClusterConnectionConfigurations =
			crossClusterReplicationConfigurationWrapper.
				getCCRLocalClusterConnectionConfigurations();

		for (String localClusterConnectionConfiguration :
				localClusterConnectionConfigurations) {

			List<String> localClusterConnectionConfigurationParts =
				StringUtil.split(
					localClusterConnectionConfiguration, CharPool.EQUAL);

			String hostName = localClusterConnectionConfigurationParts.get(0);
			String connectionId = localClusterConnectionConfigurationParts.get(
				1);

			if (hostName.equals(localClusterNodeHostName)) {
				return connectionId;
			}
		}

		return null;
	}

	public List<String> getLocalClusterConnectionIds() {
		List<String> connectionIds = new ArrayList<>();

		String[] localClusterConnectionConfigurations =
			crossClusterReplicationConfigurationWrapper.
				getCCRLocalClusterConnectionConfigurations();

		for (String localClusterConnectionConfiguration :
				localClusterConnectionConfigurations) {

			List<String> localClusterConnectionConfigurationParts =
				StringUtil.split(
					localClusterConnectionConfiguration, CharPool.EQUAL);

			connectionIds.add(localClusterConnectionConfigurationParts.get(1));
		}

		return connectionIds;
	}

	@Override
	public RestHighLevelClient getRestHighLevelClient() {
		return getRestHighLevelClient(null);
	}

	@Override
	public RestHighLevelClient getRestHighLevelClient(String connectionId) {
		return getRestHighLevelClient(connectionId, false);
	}

	@Override
	public RestHighLevelClient getRestHighLevelClient(
		String connectionId, boolean preferLocalCluster) {

		ElasticsearchConnection elasticsearchConnection =
			getElasticsearchConnection(connectionId, preferLocalCluster);

		if (elasticsearchConnection == null) {
			throw new ElasticsearchConnectionNotInitializedException(
				_getExceptionMessage(
					"Elasticsearch connection not found.", connectionId,
					preferLocalCluster));
		}

		RestHighLevelClient restHighLevelClient =
			elasticsearchConnection.getRestHighLevelClient();

		if (restHighLevelClient == null) {
			throw new ElasticsearchConnectionNotInitializedException(
				_getExceptionMessage(
					"REST high level client not found.",
					elasticsearchConnection.getConnectionId(),
					preferLocalCluster));
		}

		return restHighLevelClient;
	}

	public boolean isCrossClusterReplicationEnabled() {
		if (crossClusterReplicationConfigurationWrapper == null) {
			return false;
		}

		if (ArrayUtil.isEmpty(
				crossClusterReplicationConfigurationWrapper.
					getCCRLocalClusterConnectionConfigurations())) {

			return false;
		}

		return crossClusterReplicationConfigurationWrapper.isCCREnabled();
	}

	public synchronized void registerCompanyId(long companyId) {
		_companyIds.put(companyId, companyId);
	}

	@Reference(
		cardinality = ReferenceCardinality.MANDATORY,
		target = "(operation.mode=EMBEDDED)",
		unbind = "unsetElasticsearchConnection"
	)
	public void setEmbeddedElasticsearchConnection(
		ElasticsearchConnection elasticsearchConnection) {

		_elasticsearchConnections.put(
			elasticsearchConnection.getConnectionId(), elasticsearchConnection);
	}

	public void setOperationMode(OperationMode operationMode) {
		_operationMode = operationMode;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(operation.mode=REMOTE)",
		unbind = "unsetElasticsearchConnection"
	)
	public void setRemoteElasticsearchConnection(
		ElasticsearchConnection elasticsearchConnection) {

		if (elasticsearchConnection.isActive()) {
			elasticsearchConnection.connect();
		}

		String connectionId = elasticsearchConnection.getConnectionId();

		if (connectionId != null) {
			_elasticsearchConnections.put(
				connectionId, elasticsearchConnection);
		}
	}

	public synchronized void unregisterCompanyId(long companyId) {
		_companyIds.remove(companyId);
	}

	public void unsetElasticsearchConnection(
		ElasticsearchConnection elasticsearchConnection) {

		elasticsearchConnection.close();

		String connectionId = elasticsearchConnection.getConnectionId();

		if (connectionId != null) {
			_elasticsearchConnections.remove(connectionId);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		setConfiguration(properties);

		if (_operationMode == OperationMode.EMBEDDED) {
			ElasticsearchConnection elasticsearchConnection =
				_elasticsearchConnections.get(
					String.valueOf(OperationMode.EMBEDDED));

			elasticsearchConnection.connect();
		}
	}

	protected synchronized void createCompanyIndexes() {
		for (Long companyId : _companyIds.values()) {
			try {
				RestHighLevelClient restHighLevelClient =
					getRestHighLevelClient();

				indexFactory.createIndices(
					restHighLevelClient.indices(), companyId);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to reinitialize index for company " + companyId,
						exception);
				}
			}
		}
	}

	@Deactivate
	protected void deactivate() {
		Collection<ElasticsearchConnection> elasticsearchConnections =
			_elasticsearchConnections.values();

		for (ElasticsearchConnection elasticsearchConnection :
				elasticsearchConnections) {

			elasticsearchConnection.close();
		}
	}

	protected ElasticsearchConnection getElasticsearchConnection(
		String connectionId, boolean preferLocalCluster) {

		if (_operationMode == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Operation mode is not set");
			}

			return null;
		}

		if (!Validator.isBlank(connectionId)) {
			if (_log.isInfoEnabled()) {
				_log.info("Getting connection with ID: " + connectionId);
			}

			return _elasticsearchConnections.get(connectionId);
		}

		if (isOperationModeEmbedded()) {
			if (_log.isInfoEnabled()) {
				_log.info("Getting EMBEDDED connection");
			}

			return _elasticsearchConnections.get(
				String.valueOf(OperationMode.EMBEDDED));
		}

		if (preferLocalCluster && isCrossClusterReplicationEnabled()) {
			String localClusterConnectionId = getLocalClusterConnectionId();

			if (localClusterConnectionId != null) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Getting local cluster connection with ID: " +
							localClusterConnectionId);
				}

				return _elasticsearchConnections.get(localClusterConnectionId);
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Getting remote cluster connection with ID: " +
					_elasticsearchConfiguration.remoteClusterConnectionId());
		}

		return _elasticsearchConnections.get(
			_elasticsearchConfiguration.remoteClusterConnectionId());
	}

	protected boolean isOperationModeEmbedded() {
		return Objects.equals(_operationMode, OperationMode.EMBEDDED);
	}

	@Modified
	protected synchronized void modified(Map<String, Object> properties) {
		setConfiguration(properties);

		createCompanyIndexes();
	}

	@Reference(unbind = "-")
	protected void setClusterExecutor(ClusterExecutor clusterExecutor) {
		_clusterExecutor = clusterExecutor;
	}

	protected void setConfiguration(Map<String, Object> properties) {
		_elasticsearchConfiguration = ConfigurableUtil.createConfigurable(
			ElasticsearchConfiguration.class, properties);

		setOperationMode(
			translate(_elasticsearchConfiguration.operationMode()));
		LogUtil.setRestClientLoggerLevel(
			_elasticsearchConfiguration.restClientLoggerLevel());
	}

	protected OperationMode translate(
		com.liferay.portal.search.elasticsearch7.configuration.OperationMode
			operationMode) {

		return OperationMode.valueOf(operationMode.name());
	}

	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	protected volatile CrossClusterReplicationConfigurationWrapper
		crossClusterReplicationConfigurationWrapper;

	@Reference(unbind = "-")
	protected IndexFactory indexFactory;

	private String _getExceptionMessage(
		String message, String connectionId, boolean preferLocalCluster) {

		return StringBundler.concat(
			message, " Operation Mode: ", _operationMode, ", Connection ID: ",
			connectionId, ", Prefer Local Cluster: ", preferLocalCluster,
			", Cross-Cluster Replication Enabled: ",
			isCrossClusterReplicationEnabled(), ". Enable INFO logs on ",
			ElasticsearchConnectionManager.class, " for more information");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchConnectionManager.class);

	private ClusterExecutor _clusterExecutor;
	private final Map<Long, Long> _companyIds = new HashMap<>();
	private volatile ElasticsearchConfiguration _elasticsearchConfiguration;
	private final Map<String, ElasticsearchConnection>
		_elasticsearchConnections = new ConcurrentHashMap<>();
	private volatile OperationMode _operationMode;

}