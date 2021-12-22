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
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationObserver;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionNotInitializedException;
import com.liferay.portal.search.elasticsearch7.internal.helper.SearchLogHelperUtil;
import com.liferay.portal.search.elasticsearch7.internal.index.contributor.IndexContributorReceiver;
import com.liferay.portal.search.elasticsearch7.internal.settings.SettingsBuilder;
import com.liferay.portal.search.elasticsearch7.internal.util.ResourceUtil;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.spi.model.index.contributor.IndexContributor;
import com.liferay.portal.search.spi.settings.IndexSettingsContributor;
import com.liferay.portal.search.spi.settings.IndexSettingsHelper;

import java.io.IOException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.xcontent.XContentType;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	service = {IndexContributorReceiver.class, IndexFactory.class}
)
public class CompanyIndexFactory
	implements ElasticsearchConfigurationObserver, IndexContributorReceiver,
			   IndexFactory {

	@Override
	public void addIndexContributor(IndexContributor indexContributor) {
		_indexContributors.add(indexContributor);
	}

	@Override
	public int compareTo(
		ElasticsearchConfigurationObserver elasticsearchConfigurationObserver) {

		return _elasticsearchConfigurationWrapper.compare(
			this, elasticsearchConfigurationObserver);
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

		_executeIndexContributorsBeforeRemove(indexName);

		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(
			indexName);

		try {
			ActionResponse actionResponse = indicesClient.delete(
				deleteIndexRequest, RequestOptions.DEFAULT);

			SearchLogHelperUtil.logActionResponse(_log, actionResponse);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public int getPriority() {
		return 3;
	}

	@Override
	public void onElasticsearchConfigurationUpdate() {
		_createCompanyIndexes();
	}

	@Override
	public synchronized void registerCompanyId(long companyId) {
		_companyIds.add(companyId);
	}

	@Override
	public void removeIndexContributor(IndexContributor indexContributor) {
		_indexContributors.remove(indexContributor);
	}

	@Override
	public synchronized void unregisterCompanyId(long companyId) {
		_companyIds.remove(companyId);
	}

	@Activate
	protected void activate() {
		_elasticsearchConfigurationWrapper.register(this);

		_createCompanyIndexes();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addElasticsearchIndexSettingsContributor(
		com.liferay.portal.search.elasticsearch7.settings.
			IndexSettingsContributor indexSettingsContributor) {

		_elasticsearchIndexSettingsContributors.add(indexSettingsContributor);

		_processContributions(
			(indexName, liferayDocumentTypeFactory) ->
				indexSettingsContributor.contribute(
					indexName, liferayDocumentTypeFactory));
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addIndexSettingsContributor(
		IndexSettingsContributor indexSettingsContributor) {

		_indexSettingsContributors.add(indexSettingsContributor);

		_processContributions(
			(indexName, liferayDocumentTypeFactory) ->
				indexSettingsContributor.contribute(
					indexName, liferayDocumentTypeFactory));
	}

	protected void createIndex(String indexName, IndicesClient indicesClient) {
		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			indexName);

		LiferayDocumentTypeFactory liferayDocumentTypeFactory =
			new LiferayDocumentTypeFactory(indicesClient, _jsonFactory);

		_setSettings(createIndexRequest, liferayDocumentTypeFactory);

		_addLiferayDocumentTypeMappings(
			createIndexRequest, liferayDocumentTypeFactory);

		try {
			ActionResponse actionResponse = indicesClient.create(
				createIndexRequest, RequestOptions.DEFAULT);

			SearchLogHelperUtil.logActionResponse(_log, actionResponse);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		_updateLiferayDocumentType(indexName, liferayDocumentTypeFactory);

		_executeIndexContributorsAfterCreate(indexName);
	}

	@Deactivate
	protected void deactivate() {
		_elasticsearchConfigurationWrapper.unregister(this);
	}

	protected String getIndexName(long companyId) {
		return _indexNameBuilder.getIndexName(companyId);
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

	protected void loadAdditionalTypeMappings(
		String indexName,
		LiferayDocumentTypeFactory liferayDocumentTypeFactory) {

		if (Validator.isNull(
				_elasticsearchConfigurationWrapper.additionalTypeMappings())) {

			return;
		}

		liferayDocumentTypeFactory.addTypeMappings(
			indexName,
			_elasticsearchConfigurationWrapper.additionalTypeMappings());
	}

	protected void removeElasticsearchIndexSettingsContributor(
		com.liferay.portal.search.elasticsearch7.settings.
			IndexSettingsContributor indexSettingsContributor) {

		_elasticsearchIndexSettingsContributors.remove(
			indexSettingsContributor);
	}

	protected void removeIndexSettingsContributor(
		IndexSettingsContributor indexSettingsContributor) {

		_indexSettingsContributors.remove(indexSettingsContributor);
	}

	@Reference(unbind = "-")
	protected void setElasticsearchConfigurationWrapper(
		ElasticsearchConfigurationWrapper elasticsearchConfigurationWrapper) {

		_elasticsearchConfigurationWrapper = elasticsearchConfigurationWrapper;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchConnectionManager(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		_elasticsearchConnectionManager = elasticsearchConnectionManager;
	}

	@Reference(unbind = "-")
	protected void setIndexNameBuilder(IndexNameBuilder indexNameBuilder) {
		_indexNameBuilder = indexNameBuilder;
	}

	@Reference(unbind = "-")
	protected void setJsonFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	private void _addLiferayDocumentTypeMappings(
		CreateIndexRequest createIndexRequest,
		LiferayDocumentTypeFactory liferayDocumentTypeFactory) {

		if (Validator.isNotNull(
				_elasticsearchConfigurationWrapper.overrideTypeMappings())) {

			liferayDocumentTypeFactory.createLiferayDocumentTypeMappings(
				createIndexRequest,
				_elasticsearchConfigurationWrapper.overrideTypeMappings());
		}
		else {
			liferayDocumentTypeFactory.createRequiredDefaultTypeMappings(
				createIndexRequest);
		}
	}

	private synchronized void _createCompanyIndexes() {
		for (Long companyId : _companyIds) {
			try {
				RestHighLevelClient restHighLevelClient =
					_elasticsearchConnectionManager.getRestHighLevelClient();

				createIndices(restHighLevelClient.indices(), companyId);
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

	private void _executeIndexContributorAfterCreate(
		IndexContributor indexContributor, String indexName) {

		try {
			indexContributor.onAfterCreate(indexName);
		}
		catch (Throwable throwable) {
			_log.error(
				StringBundler.concat(
					"Unable to apply contributor ", indexContributor,
					"to index ", indexName),
				throwable);
		}
	}

	private void _executeIndexContributorBeforeRemove(
		IndexContributor indexContributor, String indexName) {

		try {
			indexContributor.onBeforeRemove(indexName);
		}
		catch (Throwable throwable) {
			_log.error(
				StringBundler.concat(
					"Unable to apply contributor ", indexContributor,
					" when removing index ", indexName),
				throwable);
		}
	}

	private void _executeIndexContributorsAfterCreate(String indexName) {
		for (IndexContributor indexContributor : _indexContributors) {
			_executeIndexContributorAfterCreate(indexContributor, indexName);
		}
	}

	private void _executeIndexContributorsBeforeRemove(String indexName) {
		for (IndexContributor indexContributor : _indexContributors) {
			_executeIndexContributorBeforeRemove(indexContributor, indexName);
		}
	}

	private void _loadAdditionalIndexConfigurations(
		SettingsBuilder settingsBuilder) {

		settingsBuilder.loadFromSource(
			_elasticsearchConfigurationWrapper.additionalIndexConfigurations());
	}

	private void _loadDefaultIndexSettings(SettingsBuilder settingsBuilder) {
		Settings.Builder builder = settingsBuilder.getBuilder();

		String defaultIndexSettings = ResourceUtil.getResourceAsString(
			getClass(), "/META-INF/index-settings-defaults.json");

		builder.loadFromSource(defaultIndexSettings, XContentType.JSON);
	}

	private void _loadIndexConfigurations(SettingsBuilder settingsBuilder) {
		settingsBuilder.put(
			"index.number_of_replicas",
			_elasticsearchConfigurationWrapper.indexNumberOfReplicas());
		settingsBuilder.put(
			"index.number_of_shards",
			_elasticsearchConfigurationWrapper.indexNumberOfShards());
	}

	private void _loadIndexSettingsContributors(Settings.Builder builder) {
		com.liferay.portal.search.elasticsearch7.settings.IndexSettingsHelper
			elasticsearchIndexSettingsHelper = (setting, value) -> builder.put(
				setting, value);

		for (com.liferay.portal.search.elasticsearch7.settings.
				IndexSettingsContributor indexSettingsContributor :
					_elasticsearchIndexSettingsContributors) {

			indexSettingsContributor.populate(elasticsearchIndexSettingsHelper);
		}

		IndexSettingsHelper indexSettingsHelper =
			(setting, value) -> builder.put(setting, value);

		for (IndexSettingsContributor indexSettingsContributor1 :
				_indexSettingsContributors) {

			indexSettingsContributor1.populate(indexSettingsHelper);
		}
	}

	private void _loadTestModeIndexSettings(SettingsBuilder settingsBuilder) {
		if (!PortalRunMode.isTestMode()) {
			return;
		}

		settingsBuilder.put("index.refresh_interval", "1ms");
		settingsBuilder.put("index.search.slowlog.threshold.fetch.warn", "-1");
		settingsBuilder.put("index.search.slowlog.threshold.query.warn", "-1");
		settingsBuilder.put("index.translog.sync_interval", "100ms");
	}

	private void _loadTypeMappingsContributors(
		String indexName,
		LiferayDocumentTypeFactory liferayDocumentTypeFactory) {

		for (com.liferay.portal.search.elasticsearch7.settings.
				IndexSettingsContributor elasticsearchIndexSettingsContributor :
					_elasticsearchIndexSettingsContributors) {

			elasticsearchIndexSettingsContributor.contribute(
				indexName, liferayDocumentTypeFactory);
		}

		for (IndexSettingsContributor indexSettingsContributor :
				_indexSettingsContributors) {

			indexSettingsContributor.contribute(
				indexName, liferayDocumentTypeFactory);
		}
	}

	private void _processContributions(
		BiConsumer<String, LiferayDocumentTypeFactory> biConsumer) {

		if (Validator.isNotNull(
				_elasticsearchConfigurationWrapper.overrideTypeMappings())) {

			return;
		}

		RestHighLevelClient restHighLevelClient = null;

		try {
			restHighLevelClient =
				_elasticsearchConnectionManager.getRestHighLevelClient();
		}
		catch (ElasticsearchConnectionNotInitializedException
					elasticsearchConnectionNotInitializedException) {

			if (_log.isInfoEnabled()) {
				_log.info("Skipping index settings contributor");
			}

			return;
		}

		LiferayDocumentTypeFactory liferayDocumentTypeFactory =
			new LiferayDocumentTypeFactory(
				restHighLevelClient.indices(), _jsonFactory);

		for (Long companyId : _companyIds) {
			biConsumer.accept(
				getIndexName(companyId), liferayDocumentTypeFactory);
		}
	}

	private void _setSettings(
		CreateIndexRequest createIndexRequest,
		LiferayDocumentTypeFactory liferayDocumentTypeFactory) {

		Settings.Builder builder = Settings.builder();

		liferayDocumentTypeFactory.createRequiredDefaultAnalyzers(builder);

		SettingsBuilder settingsBuilder = new SettingsBuilder(builder);

		_loadDefaultIndexSettings(settingsBuilder);

		_loadTestModeIndexSettings(settingsBuilder);

		_loadIndexConfigurations(settingsBuilder);

		_loadAdditionalIndexConfigurations(settingsBuilder);

		_loadIndexSettingsContributors(builder);

		if (Validator.isNotNull(builder.get("index.number_of_replicas"))) {
			builder.put("index.auto_expand_replicas", false);
		}

		createIndexRequest.settings(builder);
	}

	private void _updateLiferayDocumentType(
		String indexName,
		LiferayDocumentTypeFactory liferayDocumentTypeFactory) {

		if (Validator.isNotNull(
				_elasticsearchConfigurationWrapper.overrideTypeMappings())) {

			return;
		}

		loadAdditionalTypeMappings(indexName, liferayDocumentTypeFactory);

		_loadTypeMappingsContributors(indexName, liferayDocumentTypeFactory);

		liferayDocumentTypeFactory.createOptionalDefaultTypeMappings(indexName);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyIndexFactory.class);

	private final Set<Long> _companyIds = new HashSet<>();
	private volatile ElasticsearchConfigurationWrapper
		_elasticsearchConfigurationWrapper;
	private ElasticsearchConnectionManager _elasticsearchConnectionManager;
	private final Set
		<com.liferay.portal.search.elasticsearch7.settings.
			IndexSettingsContributor> _elasticsearchIndexSettingsContributors =
				new ConcurrentSkipListSet<>();
	private final List<IndexContributor> _indexContributors =
		new CopyOnWriteArrayList<>();
	private IndexNameBuilder _indexNameBuilder;
	private final Set<IndexSettingsContributor> _indexSettingsContributors =
		ConcurrentHashMap.newKeySet();
	private JSONFactory _jsonFactory;

}