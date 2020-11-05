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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.ccr.CrossClusterReplicationConfigurationHelper;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationObserver;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.configuration.OperationModeResolver;
import com.liferay.portal.search.elasticsearch7.internal.connection.constants.ConnectionConstants;
import com.liferay.portal.search.elasticsearch7.internal.util.SearchLogHelperUtil;

import java.net.InetAddress;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.elasticsearch.client.RestHighLevelClient;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	service = {
		ElasticsearchClientResolver.class, ElasticsearchConnectionManager.class
	}
)
public class ElasticsearchConnectionManager
	implements ElasticsearchClientResolver, ElasticsearchConfigurationObserver {

	public void addElasticsearchConnection(
		ElasticsearchConnection elasticsearchConnection) {

		String connectionId = elasticsearchConnection.getConnectionId();

		if (connectionId == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Skipping connection because connection ID is null");
			}

			return;
		}

		if (elasticsearchConnection.isActive()) {
			try {
				elasticsearchConnection.connect();
			}
			catch (RuntimeException runtimeException) {
				if (connectionId.equals(
						ConnectionConstants.SIDECAR_CONNECTION_ID)) {

					_log.error(
						StringBundler.concat(
							"Elasticsearch sidecar could not be started. ",
							"Search will be unavailable. Manual installation ",
							"of Elasticsearch and activation of remote mode ",
							"is recommended."),
						runtimeException);
				}

				throw runtimeException;
			}
		}

		_elasticsearchConnections.put(connectionId, elasticsearchConnection);
	}

	@Override
	public int compareTo(
		ElasticsearchConfigurationObserver elasticsearchConfigurationObserver) {

		return elasticsearchConfigurationWrapper.compare(
			this, elasticsearchConfigurationObserver);
	}

	public ElasticsearchConnection getElasticsearchConnection() {
		return getElasticsearchConnection(null, false);
	}

	public ElasticsearchConnection getElasticsearchConnection(
		boolean preferLocalCluster) {

		return getElasticsearchConnection(null, preferLocalCluster);
	}

	public ElasticsearchConnection getElasticsearchConnection(
		String connectionId) {

		ElasticsearchConnection elasticsearchConnection =
			_elasticsearchConnections.get(connectionId);

		if (_log.isInfoEnabled()) {
			if (elasticsearchConnection != null) {
				_log.info("Returning connection with ID: " + connectionId);
			}
			else {
				_log.info(
					"Connection not found. Returning null for ID: " +
						connectionId);
			}
		}

		return elasticsearchConnection;
	}

	public String getLocalClusterConnectionId() {
		ClusterNode localClusterNode = _clusterExecutor.getLocalClusterNode();

		if (localClusterNode == null) {
			List<String> localClusterConnectionIds =
				crossClusterReplicationConfigurationHelper.
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

		Map<String, String> localClusterConnectionConfigurations =
			crossClusterReplicationConfigurationHelper.
				getLocalClusterConnectionIdsMap();

		return localClusterConnectionConfigurations.get(
			localClusterNodeHostName);
	}

	@Override
	public int getPriority() {
		return 2;
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
		if (crossClusterReplicationConfigurationHelper == null) {
			return false;
		}

		return crossClusterReplicationConfigurationHelper.
			isCrossClusterReplicationEnabled();
	}

	@Override
	public void onElasticsearchConfigurationUpdate() {
		applyConfigurations();
	}

	public void removeElasticsearchConnection(String connectionId) {
		if (connectionId == null) {
			return;
		}

		ElasticsearchConnection elasticsearchConnection =
			_elasticsearchConnections.get(connectionId);

		if (elasticsearchConnection == null) {
			return;
		}

		elasticsearchConnection.close();

		_elasticsearchConnections.remove(connectionId);
	}

	@Activate
	protected void activate() {
		elasticsearchConfigurationWrapper.register(this);

		applyConfigurations();
	}

	protected void applyConfigurations() {
		SearchLogHelperUtil.setRESTClientLoggerLevel(
			elasticsearchConfigurationWrapper.restClientLoggerLevel());

		if (operationModeResolver.isProductionModeEnabled()) {
			if (Validator.isBlank(
					elasticsearchConfigurationWrapper.
						remoteClusterConnectionId())) {

				addElasticsearchConnection(
					_createRemoteElasticsearchConnection());
			}
		}
		else {
			removeElasticsearchConnection(
				ConnectionConstants.REMOTE_CONNECTION_ID);
		}
	}

	@Deactivate
	protected void deactivate() {
		elasticsearchConfigurationWrapper.unregister(this);

		Collection<ElasticsearchConnection> elasticsearchConnections =
			_elasticsearchConnections.values();

		for (ElasticsearchConnection elasticsearchConnection :
				elasticsearchConnections) {

			elasticsearchConnection.close();
		}
	}

	protected ElasticsearchConnection getElasticsearchConnection(
		String connectionId, boolean preferLocalCluster) {

		if (_log.isInfoEnabled()) {
			_log.info("Connection requested for ID: " + connectionId);
		}

		if (!Validator.isBlank(connectionId)) {
			if (_log.isInfoEnabled()) {
				_log.info("Getting connection with ID: " + connectionId);
			}

			return getElasticsearchConnection(connectionId);
		}

		if (operationModeResolver.isDevelopmentModeEnabled()) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Getting " + ConnectionConstants.SIDECAR_CONNECTION_ID +
						" connection");
			}

			return getElasticsearchConnection(
				ConnectionConstants.SIDECAR_CONNECTION_ID);
		}

		if (preferLocalCluster && isCrossClusterReplicationEnabled()) {
			String localClusterConnectionId = getLocalClusterConnectionId();

			if (localClusterConnectionId != null) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Getting local cluster connection with ID: " +
							localClusterConnectionId);
				}

				return getElasticsearchConnection(localClusterConnectionId);
			}
		}

		String remoteClusterConnectionId =
			elasticsearchConfigurationWrapper.remoteClusterConnectionId();

		if (Validator.isBlank(remoteClusterConnectionId)) {
			remoteClusterConnectionId =
				ConnectionConstants.REMOTE_CONNECTION_ID;
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Getting remote cluster connection with ID: " +
					remoteClusterConnectionId);
		}

		return getElasticsearchConnection(remoteClusterConnectionId);
	}

	@Reference(unbind = "-")
	protected void setClusterExecutor(ClusterExecutor clusterExecutor) {
		_clusterExecutor = clusterExecutor;
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected CrossClusterReplicationConfigurationHelper
		crossClusterReplicationConfigurationHelper;

	@Reference
	protected volatile ElasticsearchConfigurationWrapper
		elasticsearchConfigurationWrapper;

	@Reference
	protected OperationModeResolver operationModeResolver;

	private ElasticsearchConnection _createRemoteElasticsearchConnection() {
		ElasticsearchConnectionBuilder elasticsearchConnectionBuilder =
			new ElasticsearchConnectionBuilder();

		elasticsearchConnectionBuilder.active(
			true
		).authenticationEnabled(
			elasticsearchConfigurationWrapper.authenticationEnabled()
		).connectionId(
			ConnectionConstants.REMOTE_CONNECTION_ID
		).httpSSLEnabled(
			elasticsearchConfigurationWrapper.httpSSLEnabled()
		).networkHostAddresses(
			elasticsearchConfigurationWrapper.networkHostAddresses()
		).password(
			elasticsearchConfigurationWrapper.password()
		).truststorePassword(
			elasticsearchConfigurationWrapper.truststorePassword()
		).truststorePath(
			elasticsearchConfigurationWrapper.truststorePath()
		).truststoreType(
			elasticsearchConfigurationWrapper.truststoreType()
		).userName(
			elasticsearchConfigurationWrapper.userName()
		);

		return elasticsearchConnectionBuilder.build();
	}

	private String _getExceptionMessage(
		String message, String connectionId, boolean preferLocalCluster) {

		return StringBundler.concat(
			message, " Production Mode Enabled: ",
			operationModeResolver.isProductionModeEnabled(),
			", Connection ID: ", connectionId, ", Prefer Local Cluster: ",
			preferLocalCluster, ", Cross-Cluster Replication Enabled: ",
			isCrossClusterReplicationEnabled(), ". Enable INFO logs on ",
			ElasticsearchConnectionManager.class, " for more information");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchConnectionManager.class);

	private ClusterExecutor _clusterExecutor;
	private final Map<String, ElasticsearchConnection>
		_elasticsearchConnections = new ConcurrentHashMap<>();

}