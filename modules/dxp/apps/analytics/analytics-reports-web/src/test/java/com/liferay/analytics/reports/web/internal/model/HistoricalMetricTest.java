/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.web.internal.model;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Collections;
import java.util.Date;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class HistoricalMetricTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testNewHistoricalMetric() {
		HistoricalMetric historicalMetric = new HistoricalMetric(
			Collections.emptyList(), 0);

		Assert.assertEquals(
			Collections.emptyList(), historicalMetric.getHistogramMetrics());
		Assert.assertEquals(0.0, historicalMetric.getValue(), 0.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNewHistoricalMetricWithNullHistogramMetrics() {
		new HistoricalMetric(null, 0);
	}

	@Test
	public void testToJSONObject() {
		HistogramMetric histogramMetric = new HistogramMetric(
			new Date(), RandomTestUtil.randomDouble());

		HistoricalMetric historicalMetric = new HistoricalMetric(
			Collections.singletonList(histogramMetric),
			RandomTestUtil.randomDouble());

		JSONObject jsonObject = historicalMetric.toJSONObject();

		Assert.assertEquals(
			JSONUtil.put(
				"histogram",
				JSONUtil.put(
					JSONUtil.put(
						"key", _formatDate(histogramMetric.getKey())
					).put(
						"value", histogramMetric.getValue()
					))
			).put(
				"value", historicalMetric.getValue()
			).toString(),
			jsonObject.toString());
	}

	private String _formatDate(Date date) {
		Instant instant = date.toInstant();

		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

		LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

		return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}

}