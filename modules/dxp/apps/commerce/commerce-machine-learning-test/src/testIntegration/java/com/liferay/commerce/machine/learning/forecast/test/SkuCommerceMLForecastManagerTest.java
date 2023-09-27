/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.forecast.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.machine.learning.forecast.SkuCommerceMLForecast;
import com.liferay.commerce.machine.learning.forecast.SkuCommerceMLForecastManager;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Riccardo Ferrari
 */
@RunWith(Arquillian.class)
public class SkuCommerceMLForecastManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_actualDate = RandomTestUtil.nextDate();

		_skuCommerceMLForecasts = _addSkuCommerceMLForecasts();
	}

	@Test
	public void testGetSkuCommerceMLForecast() throws Exception {
		SkuCommerceMLForecast skuCommerceMLForecast =
			_skuCommerceMLForecasts.get(
				RandomTestUtil.randomInt(
					0, _skuCommerceMLForecasts.size() - 1));

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				_assertResultEquals(
					skuCommerceMLForecast.getForecastId(),
					skuCommerceMLForecast);

				return null;
			});
	}

	@Test
	public void testGetSkuCommerceMLForecasts() throws Exception {
		SkuCommerceMLForecast skuCommerceMLForecast =
			_skuCommerceMLForecasts.get(
				RandomTestUtil.randomInt(
					0, _skuCommerceMLForecasts.size() - 1));

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				_assertResultEquals(
					skuCommerceMLForecast.getSku(),
					ListUtil.filter(
						_skuCommerceMLForecasts,
						forecast -> Objects.equals(
							skuCommerceMLForecast.getSku(),
							forecast.getSku())));

				return null;
			});
	}

	private List<SkuCommerceMLForecast> _addSkuCommerceMLForecasts()
		throws Exception {

		List<SkuCommerceMLForecast> skuCommerceMLForecasts = new ArrayList<>();

		LocalDateTime endLocalDateTime = LocalDateTime.ofInstant(
			_actualDate.toInstant(), ZoneOffset.systemDefault());

		endLocalDateTime = endLocalDateTime.truncatedTo(ChronoUnit.DAYS);

		endLocalDateTime = endLocalDateTime.withDayOfMonth(1);

		endLocalDateTime = endLocalDateTime.plusMonths(_FORECAST_LENGTH);

		for (int i = 0; i < _FORECAST_COUNT; i++) {
			String sku = RandomTestUtil.randomString();

			for (int j = 0; j < (_FORECAST_LENGTH + _HISTORY_LENGTH); j++) {
				SkuCommerceMLForecast skuCommerceMLForecast =
					_createSkuCommerceMLForecast(
						sku, _toDate(endLocalDateTime.minusMonths(j)));

				skuCommerceMLForecast =
					_skuCommerceMLForecastManager.addSkuCommerceMLForecast(
						skuCommerceMLForecast);

				skuCommerceMLForecasts.add(skuCommerceMLForecast);
			}
		}

		return skuCommerceMLForecasts;
	}

	private void _assertResultEquals(
			long forecastId,
			SkuCommerceMLForecast expectedSkuCommerceMLForecast)
		throws Exception {

		SkuCommerceMLForecast skuCommerceMLForecast =
			_skuCommerceMLForecastManager.getSkuCommerceMLForecast(
				TestPropsValues.getCompanyId(), forecastId);

		Assert.assertNotNull(skuCommerceMLForecast);

		Assert.assertEquals(
			expectedSkuCommerceMLForecast.getForecastId(),
			skuCommerceMLForecast.getForecastId());

		Assert.assertEquals(
			"Scope", expectedSkuCommerceMLForecast.getScope(),
			skuCommerceMLForecast.getScope());
	}

	private void _assertResultEquals(
			String sku,
			List<SkuCommerceMLForecast> expectedSkuCommerceMLForecasts)
		throws Exception {

		List<SkuCommerceMLForecast> skuCommerceMLForecasts =
			_skuCommerceMLForecastManager.
				getMonthlyQuantitySkuCommerceMLForecasts(
					TestPropsValues.getCompanyId(), sku, _actualDate,
					_HISTORY_LENGTH, _FORECAST_LENGTH);

		Assert.assertEquals(
			"Forecast list size", expectedSkuCommerceMLForecasts.size(),
			skuCommerceMLForecasts.size());

		for (int i = 0; i < expectedSkuCommerceMLForecasts.size(); i++) {
			SkuCommerceMLForecast skuCommerceMLForecast =
				skuCommerceMLForecasts.get(i);

			SkuCommerceMLForecast expectedSkuCommerceMLForecast =
				expectedSkuCommerceMLForecasts.get(i);

			Assert.assertEquals(
				"Period", expectedSkuCommerceMLForecast.getPeriod(),
				skuCommerceMLForecast.getPeriod());

			Assert.assertEquals(
				"Scope", expectedSkuCommerceMLForecast.getScope(),
				skuCommerceMLForecast.getScope());

			Assert.assertEquals(
				"Sku", expectedSkuCommerceMLForecast.getSku(),
				skuCommerceMLForecast.getSku());

			Assert.assertEquals(
				"Target", expectedSkuCommerceMLForecast.getTarget(),
				skuCommerceMLForecast.getTarget());

			Assert.assertEquals(
				"Timestamp", expectedSkuCommerceMLForecast.getTimestamp(),
				skuCommerceMLForecast.getTimestamp());
		}
	}

	private SkuCommerceMLForecast _createSkuCommerceMLForecast(
			String sku, Date timestamp)
		throws Exception {

		SkuCommerceMLForecast skuCommerceMLForecast =
			_skuCommerceMLForecastManager.create();

		skuCommerceMLForecast.setActual((float)RandomTestUtil.randomDouble());
		skuCommerceMLForecast.setCompanyId(TestPropsValues.getCompanyId());
		skuCommerceMLForecast.setForecast((float)RandomTestUtil.randomDouble());
		skuCommerceMLForecast.setForecastLowerBound(
			(float)RandomTestUtil.randomDouble());
		skuCommerceMLForecast.setForecastUpperBound(
			(float)RandomTestUtil.randomDouble());
		skuCommerceMLForecast.setSku(sku);
		skuCommerceMLForecast.setJobId(RandomTestUtil.randomString());
		skuCommerceMLForecast.setPeriod("month");
		skuCommerceMLForecast.setTarget("quantity");
		skuCommerceMLForecast.setTimestamp(timestamp);

		return skuCommerceMLForecast;
	}

	private Date _toDate(LocalDateTime localDateTime) {
		ZonedDateTime zonedDateTime = localDateTime.atZone(
			ZoneOffset.systemDefault());

		return Date.from(zonedDateTime.toInstant());
	}

	private static final int _FORECAST_COUNT = 4;

	private static final int _FORECAST_LENGTH = 2;

	private static final int _HISTORY_LENGTH = 9;

	private Date _actualDate;

	@Inject
	private SkuCommerceMLForecastManager _skuCommerceMLForecastManager;

	private List<SkuCommerceMLForecast> _skuCommerceMLForecasts;

}