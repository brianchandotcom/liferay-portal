/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.index;

import com.liferay.osgi.service.tracker.collections.EagerServiceTrackerCustomizer;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.UpdateIndexSettingsIndexRequest;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.opensearch2.internal.configuration.OpenSearchConfigurationWrapper;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionNotInitializedException;
import com.liferay.portal.search.opensearch2.internal.index.util.IndexFactoryCompanyIdRegistryUtil;
import com.liferay.portal.search.opensearch2.internal.util.IndexUtil;
import com.liferay.portal.search.opensearch2.internal.util.JsonpUtil;
import com.liferay.portal.search.spi.index.listener.CompanyIndexListener;
import com.liferay.portal.search.spi.settings.IndexSettingsContributor;

import jakarta.json.spi.JsonProvider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opensearch.client.json.JsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.mapping.TypeMapping;
import org.opensearch.client.opensearch.indices.CreateIndexRequest;
import org.opensearch.client.opensearch.indices.CreateIndexResponse;
import org.opensearch.client.opensearch.indices.DeleteIndexRequest;
import org.opensearch.client.opensearch.indices.ExistsRequest;
import org.opensearch.client.opensearch.indices.IndexSettings;
import org.opensearch.client.opensearch.indices.OpenSearchIndicesClient;
import org.opensearch.client.opensearch.indices.PutIndicesSettingsRequest;
import org.opensearch.client.transport.endpoints.BooleanResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 * @author Petteri Karttunen
 */
@Component(service = IndexHelper.class)
public class IndexHelperImpl implements IndexHelper {

	@Override
	public void createIndex(
		String indexName, OpenSearchIndicesClient openSearchIndicesClient) {

		MappingsFactory mappingsFactory = new MappingsFactory(
			_jsonFactory, openSearchIndicesClient,
			_openSearchConfigurationWrapper);

		SettingsFactory settingsFactory = new SettingsFactory(
			_jsonFactory, _openSearchConfigurationWrapper);

		_createIndex(
			indexName, mappingsFactory, openSearchIndicesClient,
			settingsFactory);

		if (Validator.isNull(
				_openSearchConfigurationWrapper.overrideTypeMappings())) {

			_executeMappingsContributors(indexName, mappingsFactory);

			mappingsFactory.addOptionalDefaultMappings(indexName);
		}

		_executeCompanyIndexListenersAfterCreate(indexName);

		if (PortalRunMode.isTestMode()) {
			_setTestModeIndexSettings(
				settingsFactory.getTestModeIndexSettings(),
				openSearchIndicesClient);
		}
	}

	@Override
	public void deleteIndex(
		long companyId, String indexName,
		OpenSearchIndicesClient openSearchIndicesClient,
		boolean resetBothIndexNames) {

		_executeCompanyIndexListenersBeforeRemove(indexName);

		try {
			JsonpUtil.logInfoResponse(
				openSearchIndicesClient.delete(
					DeleteIndexRequest.of(
						deleteIndexRequest -> deleteIndexRequest.index(
							indexName))),
				_log);

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

	@Override
	public List<CompanyIndexListener> getCompanyIndexListeners() {
		return _companyIndexListenerServiceTrackerList.toList();
	}

	@Override
	public String getIndexName(long companyId) {
		return _indexNameBuilder.getIndexName(companyId);
	}

	@Override
	public boolean hasIndex(
		String indexName, OpenSearchIndicesClient openSearchIndicesClient) {

		try {
			BooleanResponse booleanResponse = openSearchIndicesClient.exists(
				ExistsRequest.of(
					existRequest -> existRequest.index(indexName)));

			return booleanResponse.value();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public void updateMaxResultWindow() {
		int maxResultWindow =
			_openSearchConfigurationWrapper.indexMaxResultWindow();

		_companyLocalService.forEachCompanyId(
			companyId -> _updateMaxResultWindow(companyId, maxResultWindow),
			IndexFactoryCompanyIdRegistryUtil.getCompanyIds());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_companyIndexListenerServiceTrackerList =
			ServiceTrackerListFactory.open(
				bundleContext, CompanyIndexListener.class);

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
		if (_companyIndexListenerServiceTrackerList != null) {
			_companyIndexListenerServiceTrackerList.close();
		}

		if (_indexSettingsContributorServiceTrackerList != null) {
			_indexSettingsContributorServiceTrackerList.close();
		}
	}

	private CreateIndexRequest.Builder _createCreateIndexRequestBuilder(
		JSONObject configurationJSONObject) {

		CreateIndexRequest.Builder builder = new CreateIndexRequest.Builder();

		JsonpMapper jsonpMapper = _openSearchConnectionManager.getJsonpMapper(
			null);

		JsonProvider jsonProvider = jsonpMapper.jsonProvider();

		String mappings = String.valueOf(
			configurationJSONObject.getJSONObject("mappings"));

		String settings = String.valueOf(
			configurationJSONObject.getJSONObject("settings"));

		try {
			try (InputStream inputStream = new ByteArrayInputStream(
					mappings.getBytes(StandardCharsets.UTF_8))) {

				builder.mappings(
					TypeMapping._DESERIALIZER.deserialize(
						jsonProvider.createParser(inputStream), jsonpMapper));
			}

			try (InputStream inputStream = new ByteArrayInputStream(
					settings.getBytes(StandardCharsets.UTF_8))) {

				builder.settings(
					IndexSettings._DESERIALIZER.deserialize(
						jsonProvider.createParser(inputStream), jsonpMapper));
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return builder;
	}

	private void _createIndex(
		String indexName, MappingsFactory mappingsFactory,
		OpenSearchIndicesClient openSearchIndicesClient,
		SettingsFactory settingsFactory) {

		CreateIndexRequest.Builder builder = _createCreateIndexRequestBuilder(
			JSONUtil.put(
				"mappings", mappingsFactory.getMappingsJSONObject()
			).put(
				"settings", _createSettingsJSONObject(settingsFactory)
			));

		builder.index(indexName);

		JsonpUtil.logInfoResponse(
			_getCreateIndexResponse(builder.build(), openSearchIndicesClient),
			_log);
	}

	private JSONObject _createSettingsJSONObject(
		SettingsFactory settingsFactory) {

		JSONObject settingsJSONObject = settingsFactory.getSettingsJSONObject();

		_executeIndexSettingsContributors(settingsJSONObject);

		JSONObject indexJSONObject = settingsJSONObject.getJSONObject("index");

		if (indexJSONObject.has("number_of_replicas")) {
			indexJSONObject.put("auto_expand_replicas", false);
		}

		return settingsJSONObject;
	}

	private JSONObject _dotNotationSettingsToJSONObject(
		Map<String, String> settings) {

		JSONObject settingsJSONObject = _jsonFactory.createJSONObject();

		for (Map.Entry<String, String> entry : settings.entrySet()) {
			JSONObject settingJSONObject = _jsonFactory.createJSONObject();

			String setting = entry.getKey();

			String[] settingParts = setting.split("\\.");

			for (int i = settingParts.length - 1; i >= 0; i--) {
				if (i == (settingParts.length - 1)) {
					settingJSONObject.put(settingParts[i], entry.getValue());
				}
				else {
					settingJSONObject = JSONUtil.put(
						settingParts[i], settingJSONObject);
				}
			}

			IndexUtil.mergeToJsonObject(settingsJSONObject, settingJSONObject);
		}

		return settingsJSONObject;
	}

	private void _executeCompanyIndexListenerAfterCreate(
		CompanyIndexListener companyIndexListener, String indexName) {

		try {
			companyIndexListener.onAfterCreate(indexName);
		}
		catch (Throwable throwable) {
			_log.error(
				StringBundler.concat(
					"Unable to apply contributor ", companyIndexListener,
					" after creating index ", indexName),
				throwable);
		}
	}

	private void _executeCompanyIndexListenerBeforeRemove(
		CompanyIndexListener companyIndexListener, String indexName) {

		try {
			companyIndexListener.onBeforeRemove(indexName);
		}
		catch (Throwable throwable) {
			_log.error(
				StringBundler.concat(
					"Unable to apply contributor ", companyIndexListener,
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

	private void _executeCompanyIndexListenersBeforeRemove(String indexName) {
		for (CompanyIndexListener companyIndexListener :
				getCompanyIndexListeners()) {

			_executeCompanyIndexListenerBeforeRemove(
				companyIndexListener, indexName);
		}
	}

	private void _executeIndexSettingsContributors(
		JSONObject indexSettingsJSONObject) {

		Map<String, String> contributedSettings = new HashMap<>();

		for (IndexSettingsContributor indexSettingsContributor :
				_indexSettingsContributorServiceTrackerList) {

			indexSettingsContributor.populate(contributedSettings::put);
		}

		if (MapUtil.isEmpty(contributedSettings)) {
			return;
		}

		IndexUtil.mergeToJsonObject(
			indexSettingsJSONObject,
			_dotNotationSettingsToJSONObject(contributedSettings));
	}

	private void _executeMappingsContributors(
		String indexName, MappingsFactory mappingsFactory) {

		for (IndexSettingsContributor indexSettingsContributor :
				_indexSettingsContributorServiceTrackerList) {

			indexSettingsContributor.contribute(indexName, mappingsFactory);
		}
	}

	private CreateIndexResponse _getCreateIndexResponse(
		CreateIndexRequest createIndexRequest,
		OpenSearchIndicesClient openSearchIndicesClient) {

		try {
			return openSearchIndicesClient.create(createIndexRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _processContributions(
		IndexSettingsContributor indexSettingsContributor) {

		if (Validator.isNotNull(
				_openSearchConfigurationWrapper.overrideTypeMappings())) {

			return;
		}

		try {
			OpenSearchClient openSearchClient =
				_openSearchConnectionManager.getOpenSearchClient();

			MappingsFactory mappingsFactory = new MappingsFactory(
				_jsonFactory, openSearchClient.indices(),
				_openSearchConfigurationWrapper);

			_companyLocalService.forEachCompanyId(
				companyId -> indexSettingsContributor.contribute(
					getIndexName(companyId), mappingsFactory),
				IndexFactoryCompanyIdRegistryUtil.getCompanyIds());
		}
		catch (OpenSearchConnectionNotInitializedException
					openSearchConnectionNotInitializedException) {

			_log.error(openSearchConnectionNotInitializedException);
		}
	}

	private void _setTestModeIndexSettings(
		IndexSettings indexSettings,
		OpenSearchIndicesClient openSearchIndicesClient) {

		try {
			openSearchIndicesClient.putSettings(
				PutIndicesSettingsRequest.of(
					putIndicesSettingsRequest ->
						putIndicesSettingsRequest.settings(indexSettings)));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _updateMaxResultWindow(long companyId, int maxResultWindow) {
		String indexName = _indexNameBuilder.getIndexName(companyId);

		UpdateIndexSettingsIndexRequest updateIndexSettingsIndexRequest =
			new UpdateIndexSettingsIndexRequest(indexName);

		updateIndexSettingsIndexRequest.setSettings(
			JSONUtil.put(
				"index", JSONUtil.put("max_result_window", maxResultWindow)
			).toString());

		_searchEngineAdapter.execute(updateIndexSettingsIndexRequest);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Updated index.max_result_window to ", maxResultWindow,
					" for index ", indexName));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexHelperImpl.class);

	private ServiceTrackerList<CompanyIndexListener>
		_companyIndexListenerServiceTrackerList;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	private ServiceTrackerList<IndexSettingsContributor>
		_indexSettingsContributorServiceTrackerList;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private OpenSearchConfigurationWrapper _openSearchConfigurationWrapper;

	@Reference
	private OpenSearchConnectionManager _openSearchConnectionManager;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}