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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch.index.IndexFactory;
import com.liferay.portal.search.elasticsearch.settings.SettingsContributor;

import java.io.InputStream;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Future;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequestBuilder;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;

/**
 * @author Michael C. Han
 */
public abstract class BaseElasticsearchConnection
	implements ElasticsearchConnection {

	@Override
	public void close() {
		if (_client == null) {
			return;
		}

		_client.close();

		_client = null;
	}

	@Override
	public void connect() {
		Settings.Builder builder = Settings.builder();

		loadOptionalDefaultConfigurations(builder);

		String[] additionalConfigurations =
			elasticsearchConfiguration.additionalConfigurations();

		if (ArrayUtil.isNotEmpty(additionalConfigurations)) {
			StringBundler sb = new StringBundler(
				additionalConfigurations.length * 2);

			for (String additionalConfiguration : additionalConfigurations) {
				sb.append(additionalConfiguration);
				sb.append(StringPool.NEW_LINE);
			}

			builder.loadFromSource(sb.toString());
		}

		loadRequiredDefaultConfigurations(builder);

		loadSettingsContributors(builder);

		_client = createClient(builder);
	}

	@Override
	public Client getClient() {
		return _client;
	}

	@Override
	public ClusterHealthResponse getClusterHealthResponse(long timeout) {
		Client client = getClient();

		AdminClient adminClient = client.admin();

		ClusterAdminClient clusterAdminClient = adminClient.cluster();

		ClusterHealthRequestBuilder clusterHealthRequestBuilder =
			clusterAdminClient.prepareHealth();

		clusterHealthRequestBuilder.setTimeout(
			TimeValue.timeValueMillis(timeout));

		clusterHealthRequestBuilder.setWaitForYellowStatus();

		Future<ClusterHealthResponse> future =
			clusterHealthRequestBuilder.execute();

		try {
			return future.get();
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public boolean isConnected() {
		if (_client != null) {
			return true;
		}

		return false;
	}

	public void setIndexFactory(IndexFactory indexFactory) {
		_indexFactory = indexFactory;
	}

	protected void addSettingsContributor(
		SettingsContributor settingsContributor) {

		_settingsContributors.add(settingsContributor);
	}

	protected abstract Client createClient(Settings.Builder builder);

	protected IndexFactory getIndexFactory() {
		return _indexFactory;
	}

	protected void loadOptionalDefaultConfigurations(Settings.Builder builder) {
		try {
			Class<?> clazz = getClass();

			String defaultConfiguration =
				"/META-INF/elasticsearch-optional-defaults.yml";

			InputStream inputStream = clazz.getResourceAsStream(
				defaultConfiguration);

			builder.loadFromStream(defaultConfiguration, inputStream);
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info("Unable to load optional default configurations", e);
			}
		}
	}

	protected abstract void loadRequiredDefaultConfigurations(
		Settings.Builder builder);

	protected void loadSettingsContributors(Settings.Builder builder) {
		for (SettingsContributor settingsContributor : _settingsContributors) {
			settingsContributor.populate(builder);
		}
	}

	protected void removeSettingsContributor(
		SettingsContributor settingsContributor) {

		_settingsContributors.remove(settingsContributor);
	}

	protected volatile ElasticsearchConfiguration elasticsearchConfiguration;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseElasticsearchConnection.class);

	private Client _client;
	private IndexFactory _indexFactory;
	private final Set<SettingsContributor> _settingsContributors =
		new ConcurrentSkipListSet<>();

}