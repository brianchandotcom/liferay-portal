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

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.configuration.CrossClusterReplicationConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch7.internal.index.IndexFactory;
import com.liferay.portal.search.elasticsearch7.internal.util.LogUtil;

import java.util.Collection;
import java.util.HashMap;
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

		return _elasticsearchConnections.get(connectionId);
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
			throw new ElasticsearchConnectionNotInitializedException();
		}

		RestHighLevelClient restHighLevelClient =
			elasticsearchConnection.getRestHighLevelClient();

		if (restHighLevelClient == null) {
			throw new ElasticsearchConnectionNotInitializedException();
		}

		return restHighLevelClient;
	}

	public boolean isCrossClusterReplicationEnabled() {
		if (crossClusterReplicationConfigurationWrapper == null) {
			return false;
		}

		if (Validator.isBlank(
				crossClusterReplicationConfigurationWrapper.
					getCCRLocalClusterConnectionId())) {

			return false;
		}

		return crossClusterReplicationConfigurationWrapper.isCCREnabled();
	}

	public synchronized void registerCompanyId(long companyId) {
		_companyIds.put(companyId, companyId);
	}

	public void removeElasticsearchConnection(String connectionId) {
		ElasticsearchConnection elasticsearchConnection =
			_elasticsearchConnections.get(connectionId);

		if (elasticsearchConnection != null) {
			elasticsearchConnection.close();

			_elasticsearchConnections.remove(connectionId);
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MANDATORY,
		target = "(operation.mode=EMBEDDED)",
		unbind = "unsetElasticsearchConnection"
	)
	public void setEmbeddedElasticsearchConnection(
		ElasticsearchConnection elasticsearchConnection) {

		_elasticsearchConnections.put(
			EmbeddedElasticsearchConnection.CONNECTION_ID,
			elasticsearchConnection);
	}

	public void setOperationMode(OperationMode operationMode) {
		_operationMode = operationMode;
	}

	@Reference(
		cardinality = ReferenceCardinality.AT_LEAST_ONE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(operation.mode=REMOTE)",
		unbind = "unsetElasticsearchConnection"
	)
	public void setRemoteElasticsearchConnection(
		ElasticsearchConnection elasticsearchConnection) {

		ElasticsearchConnection oldElasticsearchConnection =
			_elasticsearchConnections.put(
				elasticsearchConnection.getConnectionId(),
				elasticsearchConnection);

		if (oldElasticsearchConnection != null) {
			oldElasticsearchConnection.close();
		}
	}

	public synchronized void unregisterCompanyId(long companyId) {
		_companyIds.remove(companyId);
	}

	public void unsetElasticsearchConnection(
		ElasticsearchConnection elasticsearchConnection) {

		elasticsearchConnection.close();

		_elasticsearchConnections.remove(
			elasticsearchConnection.getConnectionId());
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_elasticsearchConfiguration = ConfigurableUtil.createConfigurable(
			ElasticsearchConfiguration.class, properties);

		setOperationMode(
			translate(_elasticsearchConfiguration.operationMode()));
		LogUtil.setRestClientLoggerLevel(
			_elasticsearchConfiguration.restClientLoggerLevel());
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

		if (!Validator.isBlank(connectionId)) {
			return _elasticsearchConnections.get(connectionId);
		}

		if (isOperationModeEmbedded()) {
			return _elasticsearchConnections.get(
				EmbeddedElasticsearchConnection.CONNECTION_ID);
		}

		if (preferLocalCluster && isCrossClusterReplicationEnabled()) {
			return _elasticsearchConnections.get(
				crossClusterReplicationConfigurationWrapper.
					getCCRLocalClusterConnectionId());
		}

		return _elasticsearchConnections.get(
			_elasticsearchConfiguration.remoteClusterConnectionId());
	}

	protected boolean isOperationModeEmbedded() {
		return Objects.equals(_operationMode, OperationMode.EMBEDDED);
	}

	@Modified
	protected synchronized void modified(Map<String, Object> properties) {
		_elasticsearchConfiguration = ConfigurableUtil.createConfigurable(
			ElasticsearchConfiguration.class, properties);

		setOperationMode(
			translate(_elasticsearchConfiguration.operationMode()));

		LogUtil.setRestClientLoggerLevel(
			_elasticsearchConfiguration.restClientLoggerLevel());

		createCompanyIndexes();
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

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchConnectionManager.class);

	private final Map<Long, Long> _companyIds = new HashMap<>();
	private volatile ElasticsearchConfiguration _elasticsearchConfiguration;
	private final Map<String, ElasticsearchConnection>
		_elasticsearchConnections = new ConcurrentHashMap<>();
	private volatile OperationMode _operationMode;

}