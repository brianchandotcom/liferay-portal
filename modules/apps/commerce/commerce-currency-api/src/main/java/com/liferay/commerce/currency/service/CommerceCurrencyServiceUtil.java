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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

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
	public static com.liferay.commerce.currency.model.CommerceCurrency
			addCommerceCurrency(
				long userId, String code,
				java.util.Map<java.util.Locale, String> nameMap, String symbol,
				java.math.BigDecimal rate,
				java.util.Map<java.util.Locale, String> formatPatternMap,
				int maxFractionDigits, int minFractionDigits,
				String roundingMode, boolean primary, double priority,
				boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceCurrency(
			userId, code, nameMap, symbol, rate, formatPatternMap,
			maxFractionDigits, minFractionDigits, roundingMode, primary,
			priority, active);
	}

	public static void deleteCommerceCurrency(long commerceCurrencyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceCurrency(commerceCurrencyId);
	}

	public static com.liferay.commerce.currency.model.CommerceCurrency
			fetchPrimaryCommerceCurrency(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchPrimaryCommerceCurrency(companyId);
	}

	public static java.util.List
		<com.liferay.commerce.currency.model.CommerceCurrency>
				getCommerceCurrencies(
					long companyId, boolean active, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.currency.model.CommerceCurrency>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceCurrencies(
			companyId, active, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.commerce.currency.model.CommerceCurrency>
				getCommerceCurrencies(
					long companyId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.currency.model.CommerceCurrency>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceCurrencies(
			companyId, start, end, orderByComparator);
	}

	public static int getCommerceCurrenciesCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceCurrenciesCount(companyId);
	}

	public static int getCommerceCurrenciesCount(long companyId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceCurrenciesCount(companyId, active);
	}

	public static com.liferay.commerce.currency.model.CommerceCurrency
			getCommerceCurrency(long commerceCurrencyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceCurrency(commerceCurrencyId);
	}

	public static com.liferay.commerce.currency.model.CommerceCurrency
			getCommerceCurrency(long companyId, String code)
		throws com.liferay.portal.kernel.exception.PortalException {

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

	public static com.liferay.commerce.currency.model.CommerceCurrency
			setActive(long commerceCurrencyId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().setActive(commerceCurrencyId, active);
	}

	public static com.liferay.commerce.currency.model.CommerceCurrency
			setPrimary(long commerceCurrencyId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().setPrimary(commerceCurrencyId, primary);
	}

	public static com.liferay.commerce.currency.model.CommerceCurrency
			updateCommerceCurrency(
				long commerceCurrencyId, String code,
				java.util.Map<java.util.Locale, String> nameMap, String symbol,
				java.math.BigDecimal rate,
				java.util.Map<java.util.Locale, String> formatPatternMap,
				int maxFractionDigits, int minFractionDigits,
				String roundingMode, boolean primary, double priority,
				boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceCurrency(
			commerceCurrencyId, code, nameMap, symbol, rate, formatPatternMap,
			maxFractionDigits, minFractionDigits, roundingMode, primary,
			priority, active, serviceContext);
	}

	public static void updateExchangeRate(
			long commerceCurrencyId, String exchangeRateProviderKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateExchangeRate(
			commerceCurrencyId, exchangeRateProviderKey);
	}

	public static void updateExchangeRates()
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateExchangeRates();
	}

	public static CommerceCurrencyService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceCurrencyService, CommerceCurrencyService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceCurrencyService.class);

		ServiceTracker<CommerceCurrencyService, CommerceCurrencyService>
			serviceTracker =
				new ServiceTracker
					<CommerceCurrencyService, CommerceCurrencyService>(
						bundle.getBundleContext(),
						CommerceCurrencyService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}