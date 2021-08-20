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

package com.liferay.commerce.currency.service;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for CommerceCurrency. This utility wraps
 * <code>com.liferay.commerce.currency.service.impl.CommerceCurrencyServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Andrea Di Giorgi
 * @see CommerceCurrencyService
 * @generated
 */
public class CommerceCurrencyServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.currency.service.impl.CommerceCurrencyServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceCurrency addCommerceCurrency(
			String code, Map<java.util.Locale, String> nameMap, String symbol,
			java.math.BigDecimal rate,
			Map<java.util.Locale, String> formatPatternMap,
			int maxFractionDigits, int minFractionDigits, String roundingMode,
			boolean primary, double priority, boolean active)
		throws PortalException {

		return getService().addCommerceCurrency(
			code, nameMap, symbol, rate, formatPatternMap, maxFractionDigits,
			minFractionDigits, roundingMode, primary, priority, active);
	}

	public static void deleteCommerceCurrency(long commerceCurrencyId)
		throws PortalException {

		getService().deleteCommerceCurrency(commerceCurrencyId);
	}

	public static CommerceCurrency fetchPrimaryCommerceCurrency(long companyId)
		throws PortalException {

		return getService().fetchPrimaryCommerceCurrency(companyId);
	}

	public static List<CommerceCurrency> getCommerceCurrencies(
			long companyId, boolean active, int start, int end,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws PortalException {

		return getService().getCommerceCurrencies(
			companyId, active, start, end, orderByComparator);
	}

	public static List<CommerceCurrency> getCommerceCurrencies(
			long companyId, int start, int end,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws PortalException {

		return getService().getCommerceCurrencies(
			companyId, start, end, orderByComparator);
	}

	public static int getCommerceCurrenciesCount(long companyId)
		throws PortalException {

		return getService().getCommerceCurrenciesCount(companyId);
	}

	public static int getCommerceCurrenciesCount(long companyId, boolean active)
		throws PortalException {

		return getService().getCommerceCurrenciesCount(companyId, active);
	}

	public static CommerceCurrency getCommerceCurrency(long commerceCurrencyId)
		throws PortalException {

		return getService().getCommerceCurrency(commerceCurrencyId);
	}

	public static CommerceCurrency getCommerceCurrency(
			long companyId, String code)
		throws PortalException {

		return getService().getCommerceCurrency(companyId, code);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceCurrency setActive(
			long commerceCurrencyId, boolean active)
		throws PortalException {

		return getService().setActive(commerceCurrencyId, active);
	}

	public static CommerceCurrency setPrimary(
			long commerceCurrencyId, boolean primary)
		throws PortalException {

		return getService().setPrimary(commerceCurrencyId, primary);
	}

	public static CommerceCurrency updateCommerceCurrency(
			long commerceCurrencyId, String code,
			Map<java.util.Locale, String> nameMap, String symbol,
			java.math.BigDecimal rate,
			Map<java.util.Locale, String> formatPatternMap,
			int maxFractionDigits, int minFractionDigits, String roundingMode,
			boolean primary, double priority, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCommerceCurrency(
			commerceCurrencyId, code, nameMap, symbol, rate, formatPatternMap,
			maxFractionDigits, minFractionDigits, roundingMode, primary,
			priority, active, serviceContext);
	}

	public static void updateExchangeRate(
			long commerceCurrencyId, String exchangeRateProviderKey)
		throws PortalException {

		getService().updateExchangeRate(
			commerceCurrencyId, exchangeRateProviderKey);
	}

	public static void updateExchangeRates() throws PortalException {
		getService().updateExchangeRates();
	}

	public static CommerceCurrencyService getService() {
		return _service;
	}

	private static volatile CommerceCurrencyService _service;

}