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
import com.liferay.commerce.currency.service.base.CommerceCurrencyServiceBaseImpl;
import com.liferay.commerce.currency.service.permission.CommerceCurrencyPermission;
import com.liferay.commerce.currency.util.ExchangeRateProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

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
			String code, Map<Locale, String> nameMap, double rate,
			String roundingType, boolean primary, double priority,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		CommerceCurrencyPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.addCommerceCurrency(
			code, nameMap, rate, roundingType, primary, priority, active,
			serviceContext);
	}

	@Override
	public void deleteCommerceCurrency(long commerceCurrencyId)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			commerceCurrencyPersistence.findByPrimaryKey(commerceCurrencyId);

		CommerceCurrencyPermission.check(
			getPermissionChecker(), commerceCurrency.getGroupId(),
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		commerceCurrencyLocalService.deleteCommerceCurrency(commerceCurrency);
	}

	@Override
	public CommerceCurrency fetchPrimaryCommerceCurrency(long groupId) {
		return commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
			groupId);
	}

	@Override
	public List<CommerceCurrency> getCommerceCurrencies(
		long groupId, boolean active, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return commerceCurrencyLocalService.getCommerceCurrencies(
			groupId, active, start, end, orderByComparator);
	}

	@Override
	public List<CommerceCurrency> getCommerceCurrencies(
		long groupId, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return commerceCurrencyLocalService.getCommerceCurrencies(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceCurrenciesCount(long groupId) {
		return commerceCurrencyLocalService.getCommerceCurrenciesCount(groupId);
	}

	@Override
	public int getCommerceCurrenciesCount(long groupId, boolean active) {
		return commerceCurrencyLocalService.getCommerceCurrenciesCount(
			groupId, active);
	}

	@Override
	public CommerceCurrency getCommerceCurrency(long commerceCurrencyId)
		throws PortalException {

		return commerceCurrencyLocalService.getCommerceCurrency(
			commerceCurrencyId);
	}

	@Override
	public CommerceCurrency setActive(long commerceCurrencyId, boolean active)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			commerceCurrencyPersistence.findByPrimaryKey(commerceCurrencyId);

		CommerceCurrencyPermission.check(
			getPermissionChecker(), commerceCurrency.getGroupId(),
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.setActive(
			commerceCurrencyId, active);
	}

	@Override
	public CommerceCurrency setPrimary(long commerceCurrencyId, boolean primary)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			commerceCurrencyPersistence.findByPrimaryKey(commerceCurrencyId);

		CommerceCurrencyPermission.check(
			getPermissionChecker(), commerceCurrency.getGroupId(),
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.setPrimary(
			commerceCurrencyId, primary);
	}

	@Override
	public CommerceCurrency updateCommerceCurrency(
			long commerceCurrencyId, String code, Map<Locale, String> nameMap,
			double rate, String roundingType, boolean primary, double priority,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			commerceCurrencyPersistence.findByPrimaryKey(commerceCurrencyId);

		CommerceCurrencyPermission.check(
			getPermissionChecker(), commerceCurrency.getGroupId(),
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.updateCommerceCurrency(
			commerceCurrency.getCommerceCurrencyId(), code, nameMap, rate,
			roundingType, primary, priority, active, serviceContext);
	}

	@Override
	public void updateExchangeRate(
			long commerceCurrencyId, ExchangeRateProvider exchangeRateProvider)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			commerceCurrencyPersistence.findByPrimaryKey(commerceCurrencyId);

		CommerceCurrencyPermission.check(
			getPermissionChecker(), commerceCurrency.getGroupId(),
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		commerceCurrencyLocalService.updateExchangeRate(
			commerceCurrencyId, exchangeRateProvider);
	}

	@Override
	public void updateExchangeRates(
			long groupId, ExchangeRateProvider exchangeRateProvider)
		throws Exception {

		CommerceCurrencyPermission.check(
			getPermissionChecker(), groupId,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		commerceCurrencyLocalService.updateExchangeRates(
			groupId, exchangeRateProvider);
	}

}