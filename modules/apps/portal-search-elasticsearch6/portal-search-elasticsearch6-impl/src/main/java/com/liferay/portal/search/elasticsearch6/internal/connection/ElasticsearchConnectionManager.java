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

package com.liferay.portal.search.elasticsearch6.internal.connection;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.elasticsearch6.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch6.internal.index.IndexFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch6.configuration.ElasticsearchConfiguration",
	immediate = true,
	service = {
		ElasticsearchClientResolver.class, ElasticsearchConnectionManager.class
	}
)
public class ElasticsearchConnectionManager
	implements ElasticsearchClientResolver {

	public void activate(OperationMode operationMode) {
		validate(operationMode);

		_operationMode = operationMode;
	}

	public void connect() {
		ElasticsearchConnection elasticsearchConnection =
			getElasticsearchConnection();

		elasticsearchConnection.connect();
	}

	public AdminClient getAdminClient() {
		Client client = getClient();

		return client.admin();
	}

	@Override
	public Client getClient() {
		ElasticsearchConnection elasticsearchConnection =
			getElasticsearchConnection();

		if (elasticsearchConnection == null) {
			throw new ElasticsearchConnectionNotInitializedException();
		}

		return elasticsearchConnection.getClient();
	}

	public ElasticsearchConnection getElasticsearchConnection() {
		return _elasticsearchConnections.get(_operationMode);
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

	public synchronized void unregisterCompanyId(long companyId) {
		_companyIds.remove(companyId);
	}

	public void unsetElasticsearchConnection(
		ElasticsearchConnection elasticsearchConnection) {

		_elasticsearchConnections.remove(
			elasticsearchConnection.getOperationMode());

		elasticsearchConnection.close();
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_elasticsearchConfiguration = ConfigurableUtil.createConfigurable(
			ElasticsearchConfiguration.class, properties);

		activate(translate(_elasticsearchConfiguration.operationMode()));
	}

	@Modified
	protected synchronized void modified(Map<String, Object> properties) {
		_elasticsearchConfiguration = ConfigurableUtil.createConfigurable(
			ElasticsearchConfiguration.class, properties);

		modify(translate(_elasticsearchConfiguration.operationMode()));
	}

	protected synchronized void modify(OperationMode operationMode) {
		if (Objects.equals(operationMode, _operationMode)) {
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
				_log.error("Unable to close " + oldElasticsearchConnection, e);
			}
		}

		_operationMode = operationMode;

		for (Long companyId : _companyIds.values()) {
			try {
				indexFactory.createIndices(getAdminClient(), companyId);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to reinitialize index for company " + companyId,
						e);
				}
			}
		}
	}

	protected OperationMode translate(
		com.liferay.portal.search.elasticsearch6.configuration.OperationMode
			operationMode) {

		return OperationMode.valueOf(operationMode.name());
	}

	protected void validate(OperationMode operationMode) {
		if (!_elasticsearchConnections.containsKey(operationMode)) {
			throw new MissingOperationModeException(operationMode);
		}
	}

	@Reference(unbind = "-")
	protected IndexFactory indexFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchConnectionManager.class);

	private final Map<Long, Long> _companyIds = new HashMap<>();
	private volatile ElasticsearchConfiguration _elasticsearchConfiguration;
	private final Map<OperationMode, ElasticsearchConnection>
		_elasticsearchConnections = new HashMap<>();
	private OperationMode _operationMode;

}