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

import com.liferay.analytics.reports.web.internal.data.model.TimeRange;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

import org.osgi.service.component.annotations.Component;

/**
 * @author David Arques
 */
@Component(immediate = true, service = AnalyticsReportsDataProvider.class)
public class AnalyticsReportsDataProvider {

	public JSONObject getHistoricalReadsJSONObject(
			long plid, TimeRange timeRange)
		throws PortalException {

		return _getHistoricalJSONObject(timeRange.getIntervals());
	}

	public JSONObject getHistoricalViewsJSONObject(
			long plid, TimeRange timeRange)
		throws PortalException {

		return _getHistoricalJSONObject(timeRange.getIntervals());
	}

	public Long getTotalReads(long plid) {
		return 107152L;
	}

	public Long getTotalViews(long plid) {
		return 187427L;
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

}