/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.internal.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;

import com.liferay.analytics.cms.rest.dto.v1_0.Metric;
import com.liferay.analytics.cms.rest.dto.v1_0.ObjectEntryAcquisitionChannel;
import com.liferay.analytics.cms.rest.dto.v1_0.ObjectEntryHistogramMetric;
import com.liferay.analytics.cms.rest.dto.v1_0.ObjectEntryMetric;
import com.liferay.analytics.cms.rest.dto.v1_0.ObjectEntryTopPages;
import com.liferay.analytics.cms.rest.dto.v1_0.PerformanceAssetConsumption;
import com.liferay.analytics.cms.rest.dto.v1_0.PerformanceAssetConsumptionItem;
import com.liferay.analytics.cms.rest.dto.v1_0.PerformanceOverviewMetric;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectDefinitionTable;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.HttpURLConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rachael Koestartyo
 */
public class AnalyticsCloudClient {

	public AnalyticsCloudClient(Http http) {
		this(http, null);
	}

	public AnalyticsCloudClient(
		Http http, ObjectDefinitionLocalService objectDefinitionLocalService) {

		_http = http;
		_objectDefinitionLocalService = objectDefinitionLocalService;
	}

	public List<ObjectEntryAcquisitionChannel>
			getObjectEntryAcquisitionChannels(
				AnalyticsConfiguration analyticsConfiguration,
				String externalReferenceCode, Long groupId, Integer rangeKey)
		throws Exception {

		try {
			Http.Options options = _getOptions(analyticsConfiguration);

			List<Long> groupIds = new ArrayList<>();

			if (groupId != null) {
				groupIds.add(groupId);
			}

			options.setLocation(
				_getUrl(
					analyticsConfiguration.liferayAnalyticsDataSourceId(),
					externalReferenceCode, groupIds,
					analyticsConfiguration.liferayAnalyticsFaroBackendURL(),
					"/acquisition-channels", rangeKey, null));

			String content = _http.URLtoString(options);

			Http.Response response = options.getResponse();

			if (response.getResponseCode() != HttpURLConnection.HTTP_OK) {
				if (_log.isDebugEnabled()) {
					_log.debug("Response code " + response.getResponseCode());
				}

				throw new PortalException(
					"Unable to get object entry acquisition channels");
			}

			ObjectMapper objectMapper = ObjectMapperHolder._objectMapper;

			JsonNode jsonNode = objectMapper.readTree(content);

			if (jsonNode == null) {
				return null;
			}

			TypeFactory typeFactory = TypeFactory.defaultInstance();

			ObjectReader objectReader = objectMapper.readerFor(
				typeFactory.constructCollectionType(
					List.class, ObjectEntryAcquisitionChannel.class));

			return objectReader.readValue(jsonNode);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new PortalException(
				"Unable to get object entry acquisition channels", exception);
		}
	}

	public ObjectEntryHistogramMetric getObjectEntryHistogramMetric(
			AnalyticsConfiguration analyticsConfiguration,
			String externalReferenceCode, Long groupId, Integer rangeKey,
			String[] selectedMetrics)
		throws Exception {

		try {
			Http.Options options = _getOptions(analyticsConfiguration);

			List<Long> groupIds = new ArrayList<>();

			if (groupId != null) {
				groupIds.add(groupId);
			}

			options.setLocation(
				_getUrl(
					analyticsConfiguration.liferayAnalyticsDataSourceId(),
					externalReferenceCode, groupIds,
					analyticsConfiguration.liferayAnalyticsFaroBackendURL(),
					"/overview/histogram", rangeKey, selectedMetrics));

			String content = _http.URLtoString(options);

			Http.Response response = options.getResponse();

			if (response.getResponseCode() == HttpURLConnection.HTTP_OK) {
				ObjectEntryHistogramMetric objectEntryHistogramMetric = null;

				JsonNode jsonNode = ObjectMapperHolder._objectMapper.readTree(
					content);

				if (jsonNode != null) {
					TypeFactory typeFactory = TypeFactory.defaultInstance();

					ObjectReader objectReader =
						ObjectMapperHolder._objectMapper.readerFor(
							typeFactory.constructType(
								ObjectEntryHistogramMetric.class));

					objectEntryHistogramMetric = objectReader.readValue(
						jsonNode);
				}

				return objectEntryHistogramMetric;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Response code " + response.getResponseCode());
			}

			throw new PortalException(
				"Unable to get object entry histogram metric");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new PortalException(
				"Unable to get object entry histogram metric", exception);
		}
	}

	public ObjectEntryMetric getObjectEntryMetric(
			AnalyticsConfiguration analyticsConfiguration,
			String externalReferenceCode, Long groupId, Integer rangeKey,
			String[] selectedMetrics)
		throws Exception {

		try {
			Http.Options options = _getOptions(analyticsConfiguration);

			List<Long> groupIds = new ArrayList<>();

			if (groupId != null) {
				groupIds.add(groupId);
			}

			options.setLocation(
				_getUrl(
					analyticsConfiguration.liferayAnalyticsDataSourceId(),
					externalReferenceCode, groupIds,
					analyticsConfiguration.liferayAnalyticsFaroBackendURL(),
					"/overview", rangeKey, selectedMetrics));

			String content = _http.URLtoString(options);

			Http.Response response = options.getResponse();

			if (response.getResponseCode() == HttpURLConnection.HTTP_OK) {
				ObjectEntryMetric objectEntryMetric = null;

				JsonNode jsonNode = ObjectMapperHolder._objectMapper.readTree(
					content);

				if (jsonNode != null) {
					_renameKey(
						jsonNode, "classification", "trendClassification");

					TypeFactory typeFactory = TypeFactory.defaultInstance();

					ObjectReader objectReader =
						ObjectMapperHolder._objectMapper.readerFor(
							typeFactory.constructType(ObjectEntryMetric.class));

					objectEntryMetric = objectReader.readValue(jsonNode);
				}

				return objectEntryMetric;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Response code " + response.getResponseCode());
			}

			throw new PortalException("Unable to get object entry metric");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new PortalException(
				"Unable to get object entry metric", exception);
		}
	}

	public ObjectEntryTopPages getObjectEntryTopPages(
			AnalyticsConfiguration analyticsConfiguration,
			String externalReferenceCode, Long groupId, Integer rangeKey)
		throws Exception {

		try {
			Http.Options options = _getOptions(analyticsConfiguration);

			List<Long> groupIds = new ArrayList<>();

			if (groupId != null) {
				groupIds.add(groupId);
			}

			options.setLocation(
				_getUrl(
					analyticsConfiguration.liferayAnalyticsDataSourceId(),
					externalReferenceCode, groupIds,
					analyticsConfiguration.liferayAnalyticsFaroBackendURL(),
					"/appears-on", rangeKey, null));

			String content = _http.URLtoString(options);

			Http.Response response = options.getResponse();

			if (response.getResponseCode() == HttpURLConnection.HTTP_OK) {
				ObjectEntryTopPages objectEntryTopPages = null;

				JsonNode jsonNode = ObjectMapperHolder._objectMapper.readTree(
					content);

				if (jsonNode != null) {
					_renameKey(jsonNode, "topPages", "pages");

					TypeFactory typeFactory = TypeFactory.defaultInstance();

					ObjectReader objectReader =
						ObjectMapperHolder._objectMapper.readerFor(
							typeFactory.constructType(
								ObjectEntryTopPages.class));

					objectEntryTopPages = objectReader.readValue(jsonNode);
				}

				return objectEntryTopPages;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Response code " + response.getResponseCode());
			}

			throw new PortalException("Unable to get object entry top pages");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new PortalException(
				"Unable to get object entry top pages", exception);
		}
	}

	public PerformanceAssetConsumption getPerformanceAssetConsumption(
			AnalyticsConfiguration analyticsConfiguration, Long categoryId,
			String groupBy, List<Long> groupIds, Locale locale,
			String metricType, String objectType, int page, Integer rangeKey,
			int size, Long tagId, Long vocabularyId)
		throws PortalException {

		try {
			Http.Options options = _getOptions(analyticsConfiguration);

			options.setLocation(
				_getUrl(
					categoryId,
					analyticsConfiguration.liferayAnalyticsDataSourceId(), null,
					groupBy, groupIds,
					analyticsConfiguration.liferayAnalyticsFaroBackendURL(),
					metricType, objectType, page, "/categories", rangeKey, null,
					size, tagId, vocabularyId));

			String content = _http.URLtoString(options);

			Http.Response response = options.getResponse();

			if (response.getResponseCode() == HttpURLConnection.HTTP_OK) {
				PerformanceAssetConsumption performanceAssetConsumption = null;

				JsonNode jsonNode = ObjectMapperHolder._objectMapper.readTree(
					content);

				if (jsonNode != null) {
					_renameKey(
						jsonNode, "performanceAssetConsumptionItemsCount",
						"total");
					_renameKey(
						jsonNode, "performanceAssetConsumptionItems",
						"metrics");

					ObjectReader objectReader =
						ObjectMapperHolder._objectMapper.readerFor(
							PerformanceAssetConsumption.class);

					performanceAssetConsumption = objectReader.readValue(
						jsonNode);

					if (StringUtil.equalsIgnoreCase(groupBy, "structure")) {
						_updatePerformanceAssetConsumptionFromObjectDefinition(
							locale, performanceAssetConsumption);
					}
				}

				return performanceAssetConsumption;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Response code " + response.getResponseCode());
			}

			throw new PortalException(
				"Unable to get performance asset consumption");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new PortalException(
				"Unable to get performance asset consumption", exception);
		}
	}

	public PerformanceOverviewMetric getPerformanceOverviewMetric(
			AnalyticsConfiguration analyticsConfiguration, List<Long> groupIds,
			Integer rangeKey)
		throws Exception {

		try {
			Http.Options options = _getOptions(analyticsConfiguration);

			options.setLocation(
				_getUrl(
					analyticsConfiguration.liferayAnalyticsDataSourceId(), null,
					groupIds,
					analyticsConfiguration.liferayAnalyticsFaroBackendURL(),
					"/performance-overview-metric", rangeKey, null));

			String content = _http.URLtoString(options);

			Http.Response response = options.getResponse();

			if (response.getResponseCode() == HttpURLConnection.HTTP_OK) {
				PerformanceOverviewMetric performanceOverviewMetric = null;

				JsonNode jsonNode = ObjectMapperHolder._objectMapper.readTree(
					content);

				if (jsonNode != null) {
					TypeFactory typeFactory = TypeFactory.defaultInstance();

					ObjectReader objectReader =
						ObjectMapperHolder._objectMapper.readerFor(
							typeFactory.constructCollectionType(
								List.class, Metric.class));

					performanceOverviewMetric = _getPerformanceOverviewMetric(
						objectReader.readValue(jsonNode));
				}

				return performanceOverviewMetric;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Response code " + response.getResponseCode());
			}

			throw new PortalException(
				"Unable to get performance overview metric");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new PortalException(
				"Unable to get performance overview metric", exception);
		}
	}

	private Http.Options _getOptions(
			AnalyticsConfiguration analyticsConfiguration)
		throws Exception {

		Http.Options options = new Http.Options();

		options.addHeader(
			"OSB-Asah-Faro-Backend-Security-Signature",
			analyticsConfiguration.
				liferayAnalyticsFaroBackendSecuritySignature());
		options.addHeader(
			"OSB-Asah-Project-ID",
			analyticsConfiguration.liferayAnalyticsProjectId());

		return options;
	}

	private PerformanceOverviewMetric _getPerformanceOverviewMetric(
		List<Metric> metrics) {

		PerformanceOverviewMetric performanceOverviewMetric =
			new PerformanceOverviewMetric();

		for (Metric metric : metrics) {
			String metricType = metric.getMetricType();

			if (StringUtil.equals(metricType, "downloadsMetric")) {
				performanceOverviewMetric.setDownloadsMetric(() -> metric);
			}
			else if (StringUtil.equals(metricType, "impressionsMetric")) {
				performanceOverviewMetric.setImpressionsMetric(() -> metric);
			}
			else if (StringUtil.equals(metricType, "readsMetric")) {
				performanceOverviewMetric.setReadsMetric(() -> metric);
			}
			else if (StringUtil.equals(metricType, "viewsMetric")) {
				performanceOverviewMetric.setViewsMetric(() -> metric);
			}
		}

		return performanceOverviewMetric;
	}

	private String _getUrl(
		Long categoryId, String dataSourceId, String externalReferenceCode,
		String groupBy, List<Long> groupIds,
		String liferayAnalyticsFaroBackendURL, String metricType,
		String objectType, Integer page, String path, Integer rangeKey,
		String[] selectedMetrics, Integer size, Long tagId, Long vocabularyId) {

		String url = String.join(
			StringPool.BLANK, liferayAnalyticsFaroBackendURL,
			"/api/1.0/asset-metric/objectEntry", path);

		if (categoryId != null) {
			url = HttpComponentsUtil.addParameter(
				url, "categoryId", categoryId);
		}

		if (Validator.isNotNull(dataSourceId)) {
			url = HttpComponentsUtil.addParameter(
				url, "dataSourceId", dataSourceId);
		}

		if (Validator.isNotNull(externalReferenceCode)) {
			url = HttpComponentsUtil.addParameter(
				url, "externalReferenceCode", externalReferenceCode);
		}

		if (Validator.isNotNull(groupBy)) {
			url = HttpComponentsUtil.addParameter(url, "groupBy", groupBy);
		}

		if (!groupIds.isEmpty()) {
			url = HttpComponentsUtil.addParameter(
				url, "groupIds", StringUtil.merge(groupIds, StringPool.COMMA));
		}

		if (Validator.isNotNull(metricType)) {
			url = HttpComponentsUtil.addParameter(
				url, "assetSummaryMetricTypeString", metricType);
		}

		if (Validator.isNotNull(objectType)) {
			url = HttpComponentsUtil.addParameter(
				url, "objectType", objectType);
		}

		if (page != null) {
			url = HttpComponentsUtil.addParameter(url, "page", page);
		}

		if (rangeKey != null) {
			url = HttpComponentsUtil.addParameter(url, "rangeKey", rangeKey);
		}

		if (ArrayUtil.isNotEmpty(selectedMetrics)) {
			url = HttpComponentsUtil.addParameter(
				url, "selectedMetrics",
				StringUtil.merge(selectedMetrics, StringPool.COMMA));
		}

		if (size != null) {
			url = HttpComponentsUtil.addParameter(url, "size", size);
		}

		if (tagId != null) {
			url = HttpComponentsUtil.addParameter(url, "tagId", tagId);
		}

		if (vocabularyId != null) {
			url = HttpComponentsUtil.addParameter(
				url, "vocabularyId", vocabularyId);
		}

		return url;
	}

	private String _getUrl(
		String dataSourceId, String externalReferenceCode, List<Long> groupIds,
		String liferayAnalyticsFaroBackendURL, String path, Integer rangeKey,
		String[] selectedMetrics) {

		return _getUrl(
			null, dataSourceId, externalReferenceCode, null, groupIds,
			liferayAnalyticsFaroBackendURL, null, null, null, path, rangeKey,
			selectedMetrics, null, null, null);
	}

	private void _renameKey(JsonNode jsonNode, String newKey, String oldKey) {
		if (jsonNode == null) {
			return;
		}

		if (jsonNode.isObject()) {
			ObjectNode objectNode = (ObjectNode)jsonNode;

			if (objectNode.has(oldKey)) {
				JsonNode objectJsonNode = objectNode.remove(oldKey);

				objectNode.set(newKey, objectJsonNode);
			}

			Iterator<Map.Entry<String, JsonNode>> iterator =
				objectNode.fields();

			iterator.forEachRemaining(
				entry -> _renameKey(entry.getValue(), newKey, oldKey));
		}
		else if (jsonNode.isArray()) {
			ArrayNode arrayNode = (ArrayNode)jsonNode;

			for (JsonNode arrayJsonNode : arrayNode) {
				_renameKey(arrayJsonNode, newKey, oldKey);
			}
		}
	}

	private void _updatePerformanceAssetConsumptionFromObjectDefinition(
		Locale locale,
		PerformanceAssetConsumption performanceAssetConsumption) {

		PerformanceAssetConsumptionItem[] performanceAssetConsumptionItems =
			performanceAssetConsumption.getPerformanceAssetConsumptionItems();

		if (ArrayUtil.isEmpty(performanceAssetConsumptionItems)) {
			return;
		}

		List<String> names = TransformUtil.transformToList(
			performanceAssetConsumptionItems,
			PerformanceAssetConsumptionItem::getTitle);

		Map<String, ObjectDefinition> objectDefinitionsMap = new HashMap<>();

		for (ObjectDefinition objectDefinition :
				(List<ObjectDefinition>)_objectDefinitionLocalService.dslQuery(
					DSLQueryFactoryUtil.select(
						ObjectDefinitionTable.INSTANCE
					).from(
						ObjectDefinitionTable.INSTANCE
					).where(
						ObjectDefinitionTable.INSTANCE.companyId.eq(
							CompanyThreadLocal.getCompanyId()
						).and(
							ObjectDefinitionTable.INSTANCE.name.in(
								names.toArray(new String[0]))
						)
					))) {

			objectDefinitionsMap.put(
				objectDefinition.getName(), objectDefinition);
		}

		for (PerformanceAssetConsumptionItem performanceAssetConsumptionItem :
				performanceAssetConsumptionItems) {

			ObjectDefinition objectDefinition = objectDefinitionsMap.get(
				performanceAssetConsumptionItem.getTitle());

			if (objectDefinition != null) {
				performanceAssetConsumptionItem.setKey(
					objectDefinition::getExternalReferenceCode);
				performanceAssetConsumptionItem.setTitle(
					() -> objectDefinition.getLabel(locale));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsCloudClient.class);

	private final Http _http;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;

	private static class ObjectMapperHolder {

		private static final ObjectMapper _objectMapper = new ObjectMapper() {
			{
				configure(
					DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			}
		};

	}

}