/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.osgi.service.tracker.collections.EagerServiceTrackerCustomizer;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.search.spi.index.configuration.contributor.IndexConfigurationContributor;
import com.liferay.portal.search.spi.index.listener.CompanyIndexListener;

import java.io.IOException;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;

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
			new LiferayDocumentTypeFactory(
				indexName, indicesClient, _jsonFactory);

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

		_updateLiferayDocumentType(liferayDocumentTypeFactory);

		_executeCompanyIndexListenersAfterCreate(indexName);
	}

	public void deleteIndex(
		String indexName, IndicesClient indicesClient, long companyId,
		boolean resetBothIndexNames) {

		_executeCompanyIndexListenersBeforeDelete(indexName);

		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(
			indexName);

		try {
			ActionResponse actionResponse = indicesClient.delete(
				deleteIndexRequest, RequestOptions.DEFAULT);

			SearchLogHelperUtil.logActionResponse(_log, actionResponse);

			if (companyId != CompanyConstants.SYSTEM) {
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
		_companyIndexListenerServiceTrackerList =
			ServiceTrackerListFactory.open(
				bundleContext, CompanyIndexListener.class);

		_indexConfigurationContributorServiceTrackerList =
			ServiceTrackerListFactory.open(
				bundleContext, IndexConfigurationContributor.class, null,
				new EagerServiceTrackerCustomizer
					<IndexConfigurationContributor,
					 IndexConfigurationContributor>() {

					@Override
					public IndexConfigurationContributor addingService(
						ServiceReference<IndexConfigurationContributor>
							serviceReference) {

						IndexConfigurationContributor
							indexConfigurationContributor =
								bundleContext.getService(serviceReference);

						_processContributions(indexConfigurationContributor);

						return indexConfigurationContributor;
					}

					@Override
					public void modifiedService(
						ServiceReference<IndexConfigurationContributor>
							serviceReference,
						IndexConfigurationContributor
							indexConfigurationContributor) {
					}

					@Override
					public void removedService(
						ServiceReference<IndexConfigurationContributor>
							serviceReference,
						IndexConfigurationContributor
							indexConfigurationContributor) {

						bundleContext.ungetService(serviceReference);
					}

				});
	}

	@Deactivate
	protected void deactivate() {
		if (_companyIndexListenerServiceTrackerList != null) {
			_companyIndexListenerServiceTrackerList.close();
		}

		if (_indexConfigurationContributorServiceTrackerList != null) {
			_indexConfigurationContributorServiceTrackerList.close();
		}
	}

	protected void loadAdditionalTypeMappings(
		LiferayDocumentTypeFactory liferayDocumentTypeFactory) {

		if (Validator.isNull(
				_elasticsearchConfigurationWrapper.additionalTypeMappings())) {

			return;
		}

		liferayDocumentTypeFactory.putTypeMappings(
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

	private void _executeCompanyIndexListenerAfterCreate(
		CompanyIndexListener companyIndexListener, String indexName) {

		try {
			companyIndexListener.onAfterCreate(indexName);
		}
		catch (Throwable throwable) {
			_log.error(
				StringBundler.concat(
					"Unable to apply listener ", companyIndexListener,
					" after creating index ", indexName),
				throwable);
		}
	}

	private void _executeCompanyIndexListenerBeforeDelete(
		CompanyIndexListener companyIndexListener, String indexName) {

		try {
			companyIndexListener.onBeforeDelete(indexName);
		}
		catch (Throwable throwable) {
			_log.error(
				StringBundler.concat(
					"Unable to apply listener ", companyIndexListener,
					" before deleting index ", indexName),
				throwable);
		}
	}

	private void _executeCompanyIndexListenersAfterCreate(String indexName) {
		for (CompanyIndexListener companyIndexListener :
				_companyIndexListenerServiceTrackerList) {

			_executeCompanyIndexListenerAfterCreate(
				companyIndexListener, indexName);
		}
	}

	private void _executeCompanyIndexListenersBeforeDelete(String indexName) {
		for (CompanyIndexListener companyIndexListener :
				_companyIndexListenerServiceTrackerList) {

			_executeCompanyIndexListenerBeforeDelete(
				companyIndexListener, indexName);
		}
	}

	private void _loadAdditionalIndexConfigurations(
		SettingsBuilder settingsBuilder) {

		settingsBuilder.loadFromSource(
			_elasticsearchConfigurationWrapper.additionalIndexConfigurations());
	}

	private void _loadDefaultIndexSettings(SettingsBuilder settingsBuilder) {
		String defaultIndexSettings = ResourceUtil.getResourceAsString(
			getClass(), "/META-INF/settings/index-settings-defaults.json");

		settingsBuilder.loadFromSource(defaultIndexSettings);
	}

	private void _loadIndexConfigurationContributors(
		SettingsBuilder settingsBuilder) {

		for (IndexConfigurationContributor indexConfigurationContributor :
				_indexConfigurationContributorServiceTrackerList) {

			indexConfigurationContributor.contributeSettings(
				settingsBuilder::put);
		}
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
		LiferayDocumentTypeFactory liferayDocumentTypeFactory) {

		for (IndexConfigurationContributor indexConfigurationContributor :
				_indexConfigurationContributorServiceTrackerList) {

			indexConfigurationContributor.contributeMappings(
				liferayDocumentTypeFactory);
		}
	}

	private void _processContributions(
		IndexConfigurationContributor indexConfigurationContributor) {

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

		for (Long companyId :
				IndexFactoryCompanyIdRegistryUtil.getCompanyIds()) {

			LiferayDocumentTypeFactory liferayDocumentTypeFactory =
				new LiferayDocumentTypeFactory(
					getIndexName(companyId), restHighLevelClient.indices(),
					_jsonFactory);

			indexConfigurationContributor.contributeMappings(
				liferayDocumentTypeFactory);
		}
	}

	private void _setSettings(
		CreateIndexRequest createIndexRequest,
		LiferayDocumentTypeFactory liferayDocumentTypeFactory) {

		SettingsBuilder settingsBuilder = new SettingsBuilder(
			Settings.builder());

		liferayDocumentTypeFactory.createRequiredDefaultAnalyzers(
			settingsBuilder);

		_loadDefaultIndexSettings(settingsBuilder);

		_loadTestModeIndexSettings(settingsBuilder);

		_loadIndexConfigurations(settingsBuilder);

		_loadAdditionalIndexConfigurations(settingsBuilder);

		_loadIndexConfigurationContributors(settingsBuilder);

		if (Validator.isNotNull(
				settingsBuilder.get("index.number_of_replicas"))) {

			settingsBuilder.put("index.auto_expand_replicas", false);
		}

		createIndexRequest.settings(settingsBuilder.getBuilder());
	}

	private void _updateLiferayDocumentType(
		LiferayDocumentTypeFactory liferayDocumentTypeFactory) {

		if (Validator.isNotNull(
				_elasticsearchConfigurationWrapper.overrideTypeMappings())) {

			return;
		}

		loadAdditionalTypeMappings(liferayDocumentTypeFactory);

		_loadTypeMappingsContributors(liferayDocumentTypeFactory);

		liferayDocumentTypeFactory.createOptionalDefaultTypeMappings();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyIndexFactoryHelper.class);

	private ServiceTrackerList<CompanyIndexListener>
		_companyIndexListenerServiceTrackerList;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ElasticsearchConfigurationWrapper
		_elasticsearchConfigurationWrapper;

	@Reference
	private ElasticsearchConnectionManager _elasticsearchConnectionManager;

	private ServiceTrackerList<IndexConfigurationContributor>
		_indexConfigurationContributorServiceTrackerList;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private JSONFactory _jsonFactory;

}