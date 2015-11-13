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

package com.liferay.portal.search.elasticsearch.connection;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.ClusterAdminClient;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration",
	immediate = true, service = ElasticsearchConnectionManager.class
)
public class ElasticsearchConnectionManager {

	public void connect() {
		ElasticsearchConnection elasticsearchConnection =
			getElasticsearchConnection();

		elasticsearchConnection.connect();
	}

	public AdminClient getAdminClient() {
		Client client = getClient();

		return client.admin();
	}

	public Client getClient() {
		ElasticsearchConnection elasticsearchConnection =
			getElasticsearchConnection();

		if (elasticsearchConnection == null) {
			throw new ElasticsearchConnectionNotInitializedException();
		}

		return elasticsearchConnection.getClient();
	}

	public ClusterAdminClient getClusterAdminClient() {
		AdminClient adminClient = getAdminClient();

		return adminClient.cluster();
	}

	public ClusterHealthResponse getClusterHealthResponse(long timeout) {
		ElasticsearchConnection elasticsearchConnection =
			getElasticsearchConnection();

		return elasticsearchConnection.getClusterHealthResponse(timeout);
	}

	public ElasticsearchConnection getElasticsearchConnection() {
		return _elasticsearchConnections.get(_operationMode);
	}

	@Reference(
		cardinality = ReferenceCardinality.MANDATORY,
		target = "(operation.mode=EMBEDDED)",
		unbind = "unsetElasticsearchConnection"
	)
	public void setEmbeddedElasticsearchConnection(
		ElasticsearchConnection elasticsearchConnection) {

		_elasticsearchConnections.put(
			elasticsearchConnection.getOperationMode(),
			elasticsearchConnection);
	}

	@Reference(
		cardinality = ReferenceCardinality.MANDATORY,
		target = "(operation.mode=REMOTE)",
		unbind = "unsetElasticsearchConnection"
	)
	public void setRemoteElasticsearchConnection(
		ElasticsearchConnection elasticsearchConnection) {

		_elasticsearchConnections.put(
			elasticsearchConnection.getOperationMode(),
			elasticsearchConnection);
	}

	public void unsetElasticsearchConnection(
		ElasticsearchConnection elasticsearchConnection) {

		_elasticsearchConnections.remove(
			elasticsearchConnection.getOperationMode());

		elasticsearchConnection.close();
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_elasticsearchConfiguration = Configurable.createConfigurable(
			ElasticsearchConfiguration.class, properties);

		activate(_elasticsearchConfiguration.operationMode());
	}

	protected void activate(OperationMode operationMode) {
		validate(operationMode);

		_operationMode = operationMode;
	}

	@Modified
	protected synchronized void modified(Map<String, Object> properties) {
		_elasticsearchConfiguration = Configurable.createConfigurable(
			ElasticsearchConfiguration.class, properties);

		modify(_elasticsearchConfiguration.operationMode());
	}

	protected void modify(OperationMode operationMode) {
		if (Validator.equals(operationMode, _operationMode)) {
			return;
		}

		validate(operationMode);

		ElasticsearchConnection newElasticsearchConnection =
			_elasticsearchConnections.get(operationMode);

		newElasticsearchConnection.connect();

		if (_operationMode != null) {
			ElasticsearchConnection oldElasticsearchConnection =
				_elasticsearchConnections.get(_operationMode);

			try {
				oldElasticsearchConnection.close();
			}
			catch (Exception e) {
				if (_log.isErrorEnabled()) {
					_log.error(
						"Unable to close " + oldElasticsearchConnection, e);
				}
			}
		}

		_operationMode = operationMode;
	}

	protected void validate(OperationMode operationMode) {
		if (!_elasticsearchConnections.containsKey(operationMode)) {
			throw new MissingOperationModeException(operationMode);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchConnectionManager.class);

	private volatile ElasticsearchConfiguration _elasticsearchConfiguration;
	private final Map<OperationMode, ElasticsearchConnection>
		_elasticsearchConnections = new HashMap<>();
	private OperationMode _operationMode;

}