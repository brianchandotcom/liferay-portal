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

package com.liferay.commerce.currency.service.impl;

import com.liferay.commerce.currency.constants.CommerceCurrencyActionKeys;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceCurrencyConstants;
import com.liferay.commerce.currency.service.base.CommerceCurrencyServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.math.BigDecimal;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceCurrencyServiceImpl
	extends CommerceCurrencyServiceBaseImpl {

	@Override
	public CommerceCurrency addCommerceCurrency(
			String code, Map<Locale, String> nameMap, String symbol,
			BigDecimal rate, Map<Locale, String> formatPatternMap,
			int maxFractionDigits, int minFractionDigits, String roundingMode,
			boolean primary, double priority, boolean active)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.addCommerceCurrency(
			getUserId(), code, nameMap, symbol, rate, formatPatternMap,
			maxFractionDigits, minFractionDigits, roundingMode, primary,
			priority, active);
	}

	@Override
	public void deleteCommerceCurrency(long commerceCurrencyId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		commerceCurrencyLocalService.deleteCommerceCurrency(commerceCurrencyId);
	}

	@Override
	public CommerceCurrency fetchPrimaryCommerceCurrency(long companyId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
			companyId);
	}

	@Override
	public List<CommerceCurrency> getCommerceCurrencies(
			long companyId, boolean active, int start, int end,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.getCommerceCurrencies(
			companyId, active, start, end, orderByComparator);
	}

	@Override
	public List<CommerceCurrency> getCommerceCurrencies(
			long companyId, int start, int end,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.getCommerceCurrencies(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceCurrenciesCount(long companyId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.getCommerceCurrenciesCount(
			companyId);
	}

	@Override
	public int getCommerceCurrenciesCount(long companyId, boolean active)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.getCommerceCurrenciesCount(
			companyId, active);
	}

	@Override
	public CommerceCurrency getCommerceCurrency(long commerceCurrencyId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.getCommerceCurrency(
			commerceCurrencyId);
	}

	@Override
	public CommerceCurrency getCommerceCurrency(long companyId, String code)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.getCommerceCurrency(
			companyId, code);
	}

	@Override
	public CommerceCurrency setActive(long commerceCurrencyId, boolean active)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.setActive(
			commerceCurrencyId, active);
	}

	@Override
	public CommerceCurrency setPrimary(long commerceCurrencyId, boolean primary)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.setPrimary(
			commerceCurrencyId, primary);
	}

	@Override
	public CommerceCurrency updateCommerceCurrency(
			long commerceCurrencyId, String code, Map<Locale, String> nameMap,
			String symbol, BigDecimal rate,
			Map<Locale, String> formatPatternMap, int maxFractionDigits,
			int minFractionDigits, String roundingMode, boolean primary,
			double priority, boolean active, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.updateCommerceCurrency(
			commerceCurrencyId, code, nameMap, symbol, rate, formatPatternMap,
			maxFractionDigits, minFractionDigits, roundingMode, primary,
			priority, active, serviceContext);
	}

	@Override
	public void updateExchangeRate(
			long commerceCurrencyId, String exchangeRateProviderKey)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		commerceCurrencyLocalService.updateExchangeRate(
			commerceCurrencyId, exchangeRateProviderKey);
	}

	@Override
	public void updateExchangeRates() throws PortalException {
		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		commerceCurrencyLocalService.updateExchangeRates();
	}

	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CommerceCurrencyServiceImpl.class, "_portletResourcePermission",
				CommerceCurrencyConstants.RESOURCE_NAME);

}