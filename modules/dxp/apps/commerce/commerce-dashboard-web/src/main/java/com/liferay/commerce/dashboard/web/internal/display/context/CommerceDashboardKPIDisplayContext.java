/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.dashboard.web.internal.display.context;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.math.BigDecimal;
import java.math.MathContext;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.portlet.RenderRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceDashboardKPIDisplayContext
	extends CommerceDashboardDisplayContext {

	public CommerceDashboardKPIDisplayContext(
			CommerceCurrencyLocalService commerceCurrencyLocalService,
			CommerceMoneyFactory commerceMoneyFactory,
			CommerceOrderLocalService commerceOrderLocalService,
			ConfigurationProvider configurationProvider,
			RenderRequest renderRequest)
		throws PortalException {

		super(configurationProvider, renderRequest);

		_commerceCurrencyLocalService = commerceCurrencyLocalService;
		_commerceMoneyFactory = commerceMoneyFactory;
		_commerceOrderLocalService = commerceOrderLocalService;

		Calendar startCalendar = CalendarFactoryUtil.getCalendar(
			getStartDateYear(), getStartDateMonth(), getStartDateDay());
		Calendar endCalendar = CalendarFactoryUtil.getCalendar(
			getEndDateYear(), getEndDateMonth(), getEndDateDay());

		_startDate = startCalendar.getTime();
		_endDate = endCalendar.getTime();
	}

	public CommerceMoney getAverageOrderPrice() throws PortalException {
		if (_averageOrderPrice != null) {
			return _averageOrderPrice;
		}

		long ordersCount = getOrdersCount();
		CommerceMoney ordersTotal = getOrdersTotal();

		if (ordersCount <= 0) {
			CommerceCurrency commerceCurrency =
				ordersTotal.getCommerceCurrency();

			_averageOrderPrice = commerceCurrency.getZero();
		}
		else {
			BigDecimal price = ordersTotal.getPrice();

			price = price.divide(
				BigDecimal.valueOf(ordersCount), MathContext.DECIMAL128);

			_averageOrderPrice = _commerceMoneyFactory.create(
				ordersTotal.getCommerceCurrency(), price);
		}

		return _averageOrderPrice;
	}

	public long getOrdersCount() {
		if (_ordersCount != null) {
			return _ordersCount;
		}

		DynamicQuery dynamicQuery = _getCommerceOrderDynamicQuery();

		dynamicQuery.setProjection(ProjectionFactoryUtil.rowCount());

		List<Long> results = _commerceOrderLocalService.dynamicQuery(
			dynamicQuery);

		if (ListUtil.isEmpty(results)) {
			_ordersCount = 0L;
		}
		else {
			_ordersCount = results.get(0);
		}

		return _ordersCount;
	}

	public CommerceMoney getOrdersTotal() throws PortalException {
		if (_ordersTotal != null) {
			return _ordersTotal;
		}

		CommerceContext commerceContext =
			commerceDashboardRequestHelper.getCommerceContext();

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();

		DynamicQuery dynamicQuery = _getCommerceOrderDynamicQuery();

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(ProjectionFactoryUtil.sum("total"));
		projectionList.add(
			ProjectionFactoryUtil.groupProperty("commerceCurrencyId"));

		dynamicQuery.setProjection(projectionList);

		List<Object[]> results = _commerceOrderLocalService.dynamicQuery(
			dynamicQuery);

		if (ListUtil.isEmpty(results)) {
			_ordersTotal = commerceCurrency.getZero();
		}
		else {
			BigDecimal price = BigDecimal.ZERO;

			for (Object[] result : results) {
				BigDecimal curPrice = (BigDecimal)result[0];
				long curCommerceCurrencyId = (long)result[1];

				if (curCommerceCurrencyId !=
						commerceCurrency.getCommerceCurrencyId()) {

					CommerceCurrency curCommerceCurrency =
						_commerceCurrencyLocalService.fetchCommerceCurrency(
							curCommerceCurrencyId);

					if (curCommerceCurrency == null) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to find currency with ID " +
									curCommerceCurrencyId);
						}

						continue;
					}

					curPrice = curPrice.multiply(curCommerceCurrency.getRate());
				}

				price = price.add(curPrice);
			}

			_ordersTotal = _commerceMoneyFactory.create(
				commerceCurrency, price);
		}

		return _ordersTotal;
	}

	private DynamicQuery _getCommerceOrderDynamicQuery() {
		DynamicQuery dynamicQuery = _commerceOrderLocalService.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.between(
				"createDate", _startDate, _endDate));
		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("orderOrganizationId", getCustomerId()));
		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("orderStatus", _ORDER_STATUS));
		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"siteGroupId",
				commerceDashboardRequestHelper.getSiteGroupId()));

		return dynamicQuery;
	}

	private static final int _ORDER_STATUS =
		CommerceOrderConstants.ORDER_STATUS_TRANSMITTED;

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDashboardKPIDisplayContext.class);

	private CommerceMoney _averageOrderPrice;
	private final CommerceCurrencyLocalService _commerceCurrencyLocalService;
	private final CommerceMoneyFactory _commerceMoneyFactory;
	private final CommerceOrderLocalService _commerceOrderLocalService;
	private final Date _endDate;
	private Long _ordersCount;
	private CommerceMoney _ordersTotal;
	private final Date _startDate;

}