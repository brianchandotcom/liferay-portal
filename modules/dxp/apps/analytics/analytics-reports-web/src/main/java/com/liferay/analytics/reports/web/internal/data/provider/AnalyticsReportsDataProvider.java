/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.web.internal.data.provider;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

import com.liferay.analytics.reports.web.internal.client.AsahFaroBackendClient;
import com.liferay.analytics.reports.web.internal.model.TimeRange;
import com.liferay.analytics.reports.web.internal.model.TrafficSource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author David Arques
 */
public class AnalyticsReportsDataProvider {

	public AnalyticsReportsDataProvider() {
		this(new AsahFaroBackendClient());
	}

	public AnalyticsReportsDataProvider(
		AsahFaroBackendClient asahFaroBackendClient) {

		if (asahFaroBackendClient == null) {
			throw new IllegalArgumentException(
				"Asah Faro backend client is null");
		}

		_asahFaroBackendClient = asahFaroBackendClient;
	}

	public JSONObject getHistoricalReadsJSONObject(
			long plid, TimeRange timeRange)
		throws PortalException {

		return _getHistoricalJSONObject(timeRange.getIntervalLocalDateTimes());
	}

	public JSONObject getHistoricalViewsJSONObject(
			long plid, TimeRange timeRange)
		throws PortalException {

		return _getHistoricalJSONObject(timeRange.getIntervalLocalDateTimes());
	}

	public Long getTotalReads(long companyId, String url)
		throws PortalException {

		try {
			return Long.valueOf(
				_asahFaroBackendClient.doGet(
					companyId, "api/1.0/pages/read-count?url=" + url));
		}
		catch (Exception exception) {
			throw new PortalException("Unable to get total reads", exception);
		}
	}

	public Long getTotalViews(long companyId, String url)
		throws PortalException {

		try {
			return Long.valueOf(
				_asahFaroBackendClient.doGet(
					companyId, "api/1.0/pages/view-count?url=" + url));
		}
		catch (Exception exception) {
			throw new PortalException("Unable to get total views", exception);
		}
	}

	public List<TrafficSource> getTrafficSources(long companyId, String url)
		throws PortalException {

		try {
			String response = _asahFaroBackendClient.doGet(
				companyId, "api/seo/1.0/traffic-sources?url=" + url);

			TypeFactory typeFactory = _objectMapper.getTypeFactory();

			return _objectMapper.readValue(
				response,
				typeFactory.constructCollectionType(
					List.class, TrafficSource.class));
		}
		catch (Exception exception) {
			throw new PortalException(
				"Unable to get traffic sources", exception);
		}
	}

	public boolean isValidAnalyticsConnection(long companyId) {
		return _asahFaroBackendClient.isValidConnection(companyId);
	}

	private JSONObject _getHistoricalJSONObject(
		Collection<LocalDateTime> intervals) {

		JSONArray intervalsJSONArray = JSONFactoryUtil.createJSONArray();
		int totalValue = 0;

		for (LocalDateTime interval : intervals) {
			int value = _getRandomInt();

			intervalsJSONArray.put(
				JSONUtil.put(
					"key",
					DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(interval)
				).put(
					"value", value
				));

			totalValue = totalValue + value;
		}

		return JSONUtil.put(
			"histogram", intervalsJSONArray
		).put(
			"value", totalValue
		);
	}

	private int _getRandomInt() {
		ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

		return threadLocalRandom.nextInt(0, 200 + 1);
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
			enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
			configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		}
	};

	private final AsahFaroBackendClient _asahFaroBackendClient;

}