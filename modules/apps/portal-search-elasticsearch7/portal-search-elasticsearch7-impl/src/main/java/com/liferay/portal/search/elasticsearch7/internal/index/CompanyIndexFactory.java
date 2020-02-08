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

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch7.internal.index.contributor.IndexContributorReceiver;
import com.liferay.portal.search.elasticsearch7.internal.settings.IndexSettingsContributorHelper;
import com.liferay.portal.search.elasticsearch7.internal.settings.SettingsBuilder;
import com.liferay.portal.search.elasticsearch7.internal.util.LogUtil;
import com.liferay.portal.search.elasticsearch7.internal.util.ResourceUtil;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.spi.model.index.contributor.IndexContributor;

import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration",
	immediate = true,
	service = {IndexContributorReceiver.class, IndexFactory.class}
)
public class CompanyIndexFactory
	implements IndexContributorReceiver, IndexFactory {

	public void addIndexContributor(IndexContributor indexContributor) {
		_indexContributors.add(indexContributor);
	}

	@Override
	public void createIndices(IndicesClient indicesClient, long companyId) {
		String indexName = getIndexName(companyId);

		if (hasIndex(indicesClient, indexName)) {
			return;
		}

		createIndex(indexName, indicesClient);
	}

	@Override
	public void deleteIndices(IndicesClient indicesClient, long companyId) {
		String indexName = getIndexName(companyId);

		if (!hasIndex(indicesClient, indexName)) {
			return;
		}

		executeIndexContributorsBeforeRemove(indexName);

		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(
			indexName);

		try {
			ActionResponse actionResponse = indicesClient.delete(
				deleteIndexRequest, RequestOptions.DEFAULT);

			LogUtil.logActionResponse(_log, actionResponse);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public void removeIndexContributor(IndexContributor indexContributor) {
		_indexContributors.remove(indexContributor);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		ElasticsearchConfiguration elasticsearchConfiguration =
			ConfigurableUtil.createConfigurable(
				ElasticsearchConfiguration.class, properties);

		setAdditionalIndexConfigurations(
			elasticsearchConfiguration.additionalIndexConfigurations());
		setAdditionalTypeMappings(
			elasticsearchConfiguration.additionalTypeMappings());
		setIndexNumberOfReplicas(
			elasticsearchConfiguration.indexNumberOfReplicas());
		setIndexNumberOfShards(
			elasticsearchConfiguration.indexNumberOfShards());
		setOverrideTypeMappings(
			elasticsearchConfiguration.overrideTypeMappings());
	}

	protected void addLiferayDocumentTypeMappings(
		CreateIndexRequest createIndexRequest,
		LiferayDocumentTypeFactory liferayDocumentTypeFactory) {

		if (Validator.isNotNull(_overrideTypeMappings)) {
			liferayDocumentTypeFactory.createLiferayDocumentTypeMappings(
				createIndexRequest, _overrideTypeMappings);
		}
		else {
			liferayDocumentTypeFactory.createRequiredDefaultTypeMappings(
				createIndexRequest);
		}
	}

	protected void createIndex(String indexName, IndicesClient indicesClient) {
		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			indexName);

		LiferayDocumentTypeFactory liferayDocumentTypeFactory =
			new LiferayDocumentTypeFactory(indicesClient, jsonFactory);

		setSettings(createIndexRequest, liferayDocumentTypeFactory);

		addLiferayDocumentTypeMappings(
			createIndexRequest, liferayDocumentTypeFactory);

		try {
			ActionResponse actionResponse = indicesClient.create(
				createIndexRequest, RequestOptions.DEFAULT);

			LogUtil.logActionResponse(_log, actionResponse);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		updateLiferayDocumentType(indexName, liferayDocumentTypeFactory);

		executeIndexContributorsAfterCreate(indexName);
	}

	protected void executeIndexContributorAfterCreate(
		IndexContributor indexContributor, String indexName) {

		try {
			indexContributor.onAfterCreate(indexName);
		}
		catch (Throwable t) {
			_log.error(
				StringBundler.concat(
					"Unable to apply contributor ", indexContributor,
					"to index ", indexName),
				t);
		}
	}

	protected void executeIndexContributorBeforeRemove(
		IndexContributor indexContributor, String indexName) {

		try {
			indexContributor.onBeforeRemove(indexName);
		}
		catch (Throwable t) {
			_log.error(
				StringBundler.concat(
					"Unable to apply contributor ", indexContributor,
					" when removing index ", indexName),
				t);
		}
	}

	protected void executeIndexContributorsAfterCreate(String indexName) {
		for (IndexContributor indexContributor : _indexContributors) {
			executeIndexContributorAfterCreate(indexContributor, indexName);
		}
	}

	protected void executeIndexContributorsBeforeRemove(String indexName) {
		for (IndexContributor indexContributor : _indexContributors) {
			executeIndexContributorBeforeRemove(indexContributor, indexName);
		}
	}

	protected String getIndexName(long companyId) {
		return indexNameBuilder.getIndexName(companyId);
	}

	protected boolean hasIndex(IndicesClient indicesClient, String indexName) {
		GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);

		try {
			return indicesClient.exists(
				getIndexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected void loadAdditionalIndexConfigurations(
		SettingsBuilder settingsBuilder) {

		settingsBuilder.loadFromSource(_additionalIndexConfigurations);
	}

	protected void loadAdditionalTypeMappings(
		String indexName,
		LiferayDocumentTypeFactory liferayDocumentTypeFactory) {

		if (Validator.isNull(_additionalTypeMappings)) {
			return;
		}

		liferayDocumentTypeFactory.addTypeMappings(
			indexName, _additionalTypeMappings);
	}

	protected void loadDefaultIndexSettings(SettingsBuilder settingsBuilder) {
		Settings.Builder builder = settingsBuilder.getBuilder();

		String defaultIndexSettings = ResourceUtil.getResourceAsString(
			getClass(), "/META-INF/index-settings-defaults.json");

		builder.loadFromSource(defaultIndexSettings, XContentType.JSON);
	}

	protected void loadIndexConfigurations(SettingsBuilder settingsBuilder) {
		settingsBuilder.put("index.number_of_replicas", _indexNumberOfReplicas);
		settingsBuilder.put("index.number_of_shards", _indexNumberOfShards);
	}

	protected void loadTestModeIndexSettings(SettingsBuilder settingsBuilder) {
		if (!PortalRunMode.isTestMode()) {
			return;
		}

		settingsBuilder.put("index.refresh_interval", "1ms");
		settingsBuilder.put("index.search.slowlog.threshold.fetch.warn", "-1");
		settingsBuilder.put("index.search.slowlog.threshold.query.warn", "-1");
		settingsBuilder.put("index.translog.sync_interval", "100ms");
	}

	protected void setAdditionalIndexConfigurations(
		String additionalIndexConfigurations) {

		_additionalIndexConfigurations = additionalIndexConfigurations;
	}

	protected void setAdditionalTypeMappings(String additionalTypeMappings) {
		_additionalTypeMappings = additionalTypeMappings;
	}

	protected void setIndexNumberOfReplicas(String indexNumberOfReplicas) {
		_indexNumberOfReplicas = indexNumberOfReplicas;
	}

	protected void setIndexNumberOfShards(String indexNumberOfShards) {
		_indexNumberOfShards = indexNumberOfShards;
	}

	protected void setOverrideTypeMappings(String overrideTypeMappings) {
		_overrideTypeMappings = overrideTypeMappings;
	}

	protected void setSettings(
		CreateIndexRequest createIndexRequest,
		LiferayDocumentTypeFactory liferayDocumentTypeFactory) {

		Settings.Builder builder = Settings.builder();

		liferayDocumentTypeFactory.createRequiredDefaultAnalyzers(builder);

		SettingsBuilder settingsBuilder = new SettingsBuilder(builder);

		loadDefaultIndexSettings(settingsBuilder);

		loadTestModeIndexSettings(settingsBuilder);

		loadIndexConfigurations(settingsBuilder);

		loadAdditionalIndexConfigurations(settingsBuilder);

		_indexSettingsContributorHelper.loadIndexSettingsContributors(builder);

		createIndexRequest.settings(builder);
	}

	protected void updateLiferayDocumentType(
		String indexName,
		LiferayDocumentTypeFactory liferayDocumentTypeFactory) {

		if (Validator.isNotNull(_overrideTypeMappings)) {
			return;
		}

		loadAdditionalTypeMappings(indexName, liferayDocumentTypeFactory);

		_indexSettingsContributorHelper.loadTypeMappingsContributors(
			indexName, liferayDocumentTypeFactory);

		liferayDocumentTypeFactory.createOptionalDefaultTypeMappings(indexName);
	}

	@Reference
	protected IndexNameBuilder indexNameBuilder;

	@Reference
	protected JSONFactory jsonFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyIndexFactory.class);

	private volatile String _additionalIndexConfigurations;
	private String _additionalTypeMappings;
	private final List<IndexContributor> _indexContributors =
		new CopyOnWriteArrayList<>();
	private String _indexNumberOfReplicas;
	private String _indexNumberOfShards;

	@Reference
	private IndexSettingsContributorHelper _indexSettingsContributorHelper;

	private String _overrideTypeMappings;

}