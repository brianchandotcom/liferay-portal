/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.index;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.opensearch2.internal.configuration.OpenSearchConfigurationWrapper;
import com.liferay.portal.search.opensearch2.internal.index.constants.IndexSettingsConstants;
import com.liferay.portal.search.opensearch2.internal.util.IndexUtil;
import com.liferay.portal.search.opensearch2.internal.util.ResourceUtil;

import org.apache.commons.lang.StringUtils;

import org.opensearch.client.opensearch._types.Time;
import org.opensearch.client.opensearch._types.TimeUnit;
import org.opensearch.client.opensearch.indices.IndexSettings;
import org.opensearch.client.opensearch.indices.IndexSettingsSearch;
import org.opensearch.client.opensearch.indices.SearchSlowlog;
import org.opensearch.client.opensearch.indices.SearchSlowlogThresholds;
import org.opensearch.client.opensearch.indices.SlowlogThresholdLevels;
import org.opensearch.client.opensearch.indices.Translog;

/**
 * @author Petteri Karttunen
 */
public class SettingsFactory {

	public SettingsFactory(
		JSONFactory jsonFactory,
		OpenSearchConfigurationWrapper openSearchConfigurationWrapper) {

		_jsonFactory = jsonFactory;
		_openSearchConfigurationWrapper = openSearchConfigurationWrapper;
	}

	public JSONObject getSettingsJSONObject() {
		JSONObject settingsJSONObject = _createJSONObject(
			ResourceUtil.getResourceAsString(
				getClass(), IndexSettingsConstants.INDEX_SETTINGS_FILE_NAME));

		_mergeSettingsFromConfiguration(settingsJSONObject);

		_mergeAdditionalIndexConfigurations(settingsJSONObject);

		return settingsJSONObject;
	}

	public IndexSettings getTestModeIndexSettings() {
		IndexSettings.Builder builder = new IndexSettings.Builder();

		builder.refreshInterval(
			Time.of(time -> time.time(1 + TimeUnit.Milliseconds.jsonValue())));
		builder.search(_getTestModeIndexSettingsSearch());
		builder.translog(
			Translog.of(
				transLog -> transLog.syncInterval(
					Time.of(
						time -> time.time(
							100 + TimeUnit.Milliseconds.jsonValue())))));

		return builder.build();
	}

	private JSONObject _createJSONObject(String jsonString) {
		try {
			return _jsonFactory.createJSONObject(jsonString);
		}
		catch (JSONException jsonException) {
			throw new RuntimeException(jsonException);
		}
	}

	private IndexSettingsSearch _getTestModeIndexSettingsSearch() {
		IndexSettingsSearch.Builder indexSettingsSearchBuilder =
			new IndexSettingsSearch.Builder();

		SearchSlowlogThresholds.Builder searchSlowlogThresholdsBuilder =
			new SearchSlowlogThresholds.Builder();

		searchSlowlogThresholdsBuilder.fetch(
			SlowlogThresholdLevels.of(
				slowlogThresholdLevels -> slowlogThresholdLevels.warn(
					Time.of(time -> time.time("-1")))));

		searchSlowlogThresholdsBuilder.query(
			SlowlogThresholdLevels.of(
				slowlogThresholdLevels -> slowlogThresholdLevels.warn(
					Time.of(time -> time.time("-1")))));

		indexSettingsSearchBuilder.slowlog(
			SearchSlowlog.of(
				searchSlowLog -> searchSlowLog.threshold(
					searchSlowlogThresholdsBuilder.build())));

		return indexSettingsSearchBuilder.build();
	}

	private void _mergeAdditionalIndexConfigurations(
		JSONObject settingsJSONObject) {

		String additionalIndexConfigurations =
			_openSearchConfigurationWrapper.additionalIndexConfigurations();

		if (Validator.isBlank(additionalIndexConfigurations)) {
			return;
		}

		IndexUtil.mergeToJsonObject(
			settingsJSONObject,
			_createJSONObject(additionalIndexConfigurations));
	}

	private void _mergeSettingsFromConfiguration(
		JSONObject settingsJSONObject) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			"max_result_window",
			_openSearchConfigurationWrapper.indexMaxResultWindow());

		if (!StringUtils.isBlank(
				_openSearchConfigurationWrapper.indexNumberOfReplicas())) {

			jsonObject.put(
				"number_of_replicas",
				_openSearchConfigurationWrapper.indexNumberOfReplicas());
		}

		if (!StringUtils.isBlank(
				_openSearchConfigurationWrapper.indexNumberOfShards())) {

			jsonObject.put(
				"number_of_shards",
				_openSearchConfigurationWrapper.indexNumberOfShards());
		}

		IndexUtil.mergeToJsonObject(settingsJSONObject, jsonObject);
	}

	private final JSONFactory _jsonFactory;
	private final OpenSearchConfigurationWrapper
		_openSearchConfigurationWrapper;

}