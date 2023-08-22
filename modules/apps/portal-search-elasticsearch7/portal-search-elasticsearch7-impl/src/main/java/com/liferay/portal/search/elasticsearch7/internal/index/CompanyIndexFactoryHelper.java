/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.osgi.service.tracker.collections.EagerServiceTrackerCustomizer;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionNotInitializedException;
import com.liferay.portal.search.elasticsearch7.internal.helper.SearchLogHelperUtil;
import com.liferay.portal.search.elasticsearch7.internal.index.util.IndexFactoryCompanyIdRegistryUtil;
import com.liferay.portal.search.elasticsearch7.internal.settings.SettingsBuilder;
import com.liferay.portal.search.elasticsearch7.internal.util.ResourceUtil;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.spi.model.index.contributor.IndexContributor;
import com.liferay.portal.search.spi.settings.IndexSettingsContributor;

import java.io.IOException;

import java.util.List;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.xcontent.XContentType;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
@Component(service = CompanyIndexFactoryHelper.class)
public class CompanyIndexFactoryHelper {

	public void createIndex(String indexName, IndicesClient indicesClient) {
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

	public void deleteIndex(
		String indexName, IndicesClient indicesClient, long companyId,
		boolean resetBothIndexNames) {

		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(
			indexName);

		try {
			ActionResponse actionResponse = indicesClient.delete(
				deleteIndexRequest, RequestOptions.DEFAULT);

			SearchLogHelperUtil.logActionResponse(_log, actionResponse);

			if (FeatureFlagManagerUtil.isEnabled("LPS-183661") &&
				(companyId != CompanyConstants.SYSTEM)) {

				if (resetBothIndexNames) {
					_companyLocalService.updateIndexNames(
						companyId, null, null);
				}
				else {
					_companyLocalService.updateIndexNameNext(companyId, null);
				}
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public List<IndexContributor> getIndexContributors() {
		return _indexContributorServiceTrackerList.toList();
	}

	public String getIndexName(long companyId) {
		return _indexNameBuilder.getIndexName(companyId);
	}

	public boolean hasIndex(IndicesClient indicesClient, String indexName) {
		GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);

		try {
			return indicesClient.exists(
				getIndexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_indexContributorServiceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, IndexContributor.class);

		_indexSettingsContributorServiceTrackerList =
			ServiceTrackerListFactory.open(
				bundleContext, IndexSettingsContributor.class, null,
				new EagerServiceTrackerCustomizer
					<IndexSettingsContributor, IndexSettingsContributor>() {

					@Override
					public IndexSettingsContributor addingService(
						ServiceReference<IndexSettingsContributor>
							serviceReference) {

						IndexSettingsContributor indexSettingsContributor =
							bundleContext.getService(serviceReference);

						_processContributions(indexSettingsContributor);

						return indexSettingsContributor;
					}

					@Override
					public void modifiedService(
						ServiceReference<IndexSettingsContributor>
							serviceReference,
						IndexSettingsContributor indexSettingsContributor) {
					}

					@Override
					public void removedService(
						ServiceReference<IndexSettingsContributor>
							serviceReference,
						IndexSettingsContributor indexSettingsContributor) {

						bundleContext.ungetService(serviceReference);
					}

				});
	}

	@Deactivate
	protected void deactivate() {
		if (_indexContributorServiceTrackerList != null) {
			_indexContributorServiceTrackerList.close();
		}

		if (_indexSettingsContributorServiceTrackerList != null) {
			_indexSettingsContributorServiceTrackerList.close();
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

	private void _executeIndexContributorsAfterCreate(String indexName) {
		for (IndexContributor indexContributor :
				_indexContributorServiceTrackerList) {

			_executeIndexContributorAfterCreate(indexContributor, indexName);
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
			getClass(), "/META-INF/settings/index-settings-defaults.json");

		builder.loadFromSource(defaultIndexSettings, XContentType.JSON);
	}

	private void _loadIndexConfigurations(SettingsBuilder settingsBuilder) {
		settingsBuilder.put(
			"index.number_of_replicas",
			_elasticsearchConfigurationWrapper.indexNumberOfReplicas());
		settingsBuilder.put(
			"index.number_of_shards",
			_elasticsearchConfigurationWrapper.indexNumberOfShards());
		settingsBuilder.put(
			"index.max_result_window",
			String.valueOf(
				_elasticsearchConfigurationWrapper.indexMaxResultWindow()));
	}

	private void _loadIndexSettingsContributors(Settings.Builder builder) {
		for (IndexSettingsContributor indexSettingsContributor :
				_indexSettingsContributorServiceTrackerList) {

			indexSettingsContributor.populate(builder::put);
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

		for (IndexSettingsContributor indexSettingsContributor :
				_indexSettingsContributorServiceTrackerList) {

			indexSettingsContributor.contribute(
				indexName, liferayDocumentTypeFactory);
		}
	}

	private void _processContributions(
		IndexSettingsContributor indexSettingsContributor) {

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

		for (Long companyId :
				IndexFactoryCompanyIdRegistryUtil.getCompanyIds()) {

			indexSettingsContributor.contribute(
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
		CompanyIndexFactoryHelper.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private volatile ElasticsearchConfigurationWrapper
		_elasticsearchConfigurationWrapper;

	@Reference
	private ElasticsearchConnectionManager _elasticsearchConnectionManager;

	private ServiceTrackerList<IndexContributor>
		_indexContributorServiceTrackerList;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	private ServiceTrackerList<IndexSettingsContributor>
		_indexSettingsContributorServiceTrackerList;

	@Reference
	private JSONFactory _jsonFactory;

}