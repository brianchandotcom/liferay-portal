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

package com.liferay.headless.commerce.machine.learning.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.machine.learning.forecast.SkuCommerceMLForecast;
import com.liferay.commerce.machine.learning.forecast.SkuCommerceMLForecastManager;
import com.liferay.headless.commerce.machine.learning.client.dto.v1_0.SkuForecast;
import com.liferay.headless.commerce.machine.learning.client.pagination.Page;
import com.liferay.headless.commerce.machine.learning.client.pagination.Pagination;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.test.rule.Inject;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Riccardo Ferrari
 */
@RunWith(Arquillian.class)
public class SkuForecastResourceTest extends BaseSkuForecastResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_skuCommerceMLForecasts = _addSkuCommerceMLForecasts(
			4, _FORECAST_LENGTH + _HISTORY_LENGTH);

		Stream<SkuCommerceMLForecast> stream = _skuCommerceMLForecasts.stream();

		_skus = stream.map(
			SkuCommerceMLForecast::getSku
		).distinct(
		).collect(
			Collectors.toList()
		);
	}

	@Override
	@Test
	public void testGetSkuForecastsByMonthlyRevenuePage() throws Exception {
		List<String> expectedSkus = _skus.subList(0, 2);

		IdempotentRetryAssert.retryAssert(
			1, TimeUnit.SECONDS, 3, TimeUnit.SECONDS,
			() -> {
				_testGetSkuForecastsByMonthlyRevenuePage(expectedSkus);

				return null;
			});
	}

	@Override
	@Test
	public void testGetSkuForecastsByMonthlyRevenuePageWithPagination()
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			1, TimeUnit.SECONDS, 3, TimeUnit.SECONDS,
			() -> {
				_testGetSkuForecastsByMonthlyRevenuePageWithPagination();

				return null;
			});
	}

	private List<SkuCommerceMLForecast> _addSkuCommerceMLForecasts(
			int forecastCount, int seriesLength)
		throws Exception {

		List<SkuCommerceMLForecast> skuCommerceMLForecasts = new ArrayList<>();

		LocalDateTime endLocalDateTime = LocalDateTime.ofInstant(
			_actualDate.toInstant(), ZoneOffset.systemDefault());

		endLocalDateTime = endLocalDateTime.truncatedTo(ChronoUnit.DAYS);

		endLocalDateTime = endLocalDateTime.withDayOfMonth(1);

		endLocalDateTime = endLocalDateTime.plusMonths(_FORECAST_LENGTH);

		for (int i = 0; i < forecastCount; i++) {
			String sku = RandomTestUtil.randomString();

			for (int j = 0; j < seriesLength; j++) {
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

	private SkuCommerceMLForecast _createSkuCommerceMLForecast(
		String sku, Date timestamp) {

		SkuCommerceMLForecast skuCommerceMLForecast =
			_skuCommerceMLForecastManager.create();

		skuCommerceMLForecast.setActual((float)RandomTestUtil.nextDouble());
		skuCommerceMLForecast.setCompanyId(testCompany.getCompanyId());
		skuCommerceMLForecast.setForecast((float)RandomTestUtil.nextDouble());
		skuCommerceMLForecast.setForecastLowerBound(
			(float)RandomTestUtil.nextDouble());
		skuCommerceMLForecast.setForecastUpperBound(
			(float)RandomTestUtil.nextDouble());
		skuCommerceMLForecast.setSku(sku);
		skuCommerceMLForecast.setJobId(RandomTestUtil.randomString());
		skuCommerceMLForecast.setPeriod("month");
		skuCommerceMLForecast.setTarget("quantity");
		skuCommerceMLForecast.setTimestamp(timestamp);

		return skuCommerceMLForecast;
	}

	private void _testGetSkuForecastsByMonthlyRevenuePage(
			List<String> expectedSkus)
		throws Exception {

		int expectedTotalCount =
			expectedSkus.size() * (_FORECAST_LENGTH + _HISTORY_LENGTH);

		Page<SkuForecast> skuForecastsByMonthlyRevenuePage =
			skuForecastResource.getSkuForecastsByMonthlyRevenuePage(
				_FORECAST_LENGTH, _actualDate, _HISTORY_LENGTH,
				expectedSkus.toArray(new String[0]), Pagination.of(1, 10));

		Assert.assertEquals(
			expectedTotalCount,
			skuForecastsByMonthlyRevenuePage.getTotalCount());

		Collection<SkuForecast> skuForecasts =
			skuForecastsByMonthlyRevenuePage.getItems();

		Stream<SkuForecast> stream = skuForecasts.stream();

		List<String> actualSkus = stream.map(
			SkuForecast::getSku
		).distinct(
		).collect(
			Collectors.toList()
		);

		Assert.assertTrue(
			expectedSkus.containsAll(actualSkus) &&
			actualSkus.containsAll(expectedSkus));
	}

	private void _testGetSkuForecastsByMonthlyRevenuePageWithPagination()
		throws Exception {

		List<SkuForecast> skuForecasts = new ArrayList<>();

		int pageSize = _HISTORY_LENGTH + _FORECAST_LENGTH;

		for (int i = 1; i <= _skus.size(); i++) {
			Page<SkuForecast> skuForecastPage =
				skuForecastResource.getSkuForecastsByMonthlyRevenuePage(
					_FORECAST_LENGTH, _actualDate, _HISTORY_LENGTH,
					_skus.toArray(new String[0]), Pagination.of(i, pageSize));

			skuForecasts.addAll(skuForecastPage.getItems());
		}

		assertEqualsIgnoringOrder(
			TransformUtil.transform(
				_skuCommerceMLForecasts,
				skuCommerceMLForecast -> new SkuForecast() {
					{
						actual = skuCommerceMLForecast.getActual();
						forecast = skuCommerceMLForecast.getForecast();
						forecastLowerBound =
							skuCommerceMLForecast.getForecastLowerBound();
						forecastUpperBound =
							skuCommerceMLForecast.getForecastUpperBound();
						sku = skuCommerceMLForecast.getSku();
						timestamp = skuCommerceMLForecast.getTimestamp();
						unit = skuCommerceMLForecast.getTarget();
					}
				}),
			skuForecasts);
	}

	private Date _toDate(LocalDateTime localDateTime) {
		ZonedDateTime zonedDateTime = localDateTime.atZone(
			ZoneOffset.systemDefault());

		return Date.from(zonedDateTime.toInstant());
	}

	private static final int _FORECAST_LENGTH = 2;

	private static final int _HISTORY_LENGTH = 9;

	private final Date _actualDate = RandomTestUtil.nextDate();

	@Inject
	private SkuCommerceMLForecastManager _skuCommerceMLForecastManager;

	private List<SkuCommerceMLForecast> _skuCommerceMLForecasts;
	private List<String> _skus;

}