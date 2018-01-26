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

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceCurrency. This utility wraps
 * {@link com.liferay.commerce.currency.service.impl.CommerceCurrencyServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Andrea Di Giorgi
 * @see CommerceCurrencyService
 * @see com.liferay.commerce.currency.service.base.CommerceCurrencyServiceBaseImpl
 * @see com.liferay.commerce.currency.service.impl.CommerceCurrencyServiceImpl
 * @generated
 */
@ProviderType
public class CommerceCurrencyServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.currency.service.impl.CommerceCurrencyServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.currency.model.CommerceCurrency addCommerceCurrency(
		java.lang.String code,
		java.util.Map<java.util.Locale, java.lang.String> nameMap, double rate,
		java.lang.String roundingType, boolean primary, double priority,
		boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceCurrency(code, nameMap, rate, roundingType,
			primary, priority, active, serviceContext);
	}

	public static void deleteCommerceCurrency(long commerceCurrencyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceCurrency(commerceCurrencyId);
	}

	public static com.liferay.commerce.currency.model.CommerceCurrency fetchPrimaryCommerceCurrency(
		long groupId) {
		return getService().fetchPrimaryCommerceCurrency(groupId);
	}

	public static java.util.List<com.liferay.commerce.currency.model.CommerceCurrency> getCommerceCurrencies(
		long groupId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.currency.model.CommerceCurrency> orderByComparator) {
		return getService()
				   .getCommerceCurrencies(groupId, active, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.commerce.currency.model.CommerceCurrency> getCommerceCurrencies(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.currency.model.CommerceCurrency> orderByComparator) {
		return getService()
				   .getCommerceCurrencies(groupId, start, end, orderByComparator);
	}

	public static int getCommerceCurrenciesCount(long groupId) {
		return getService().getCommerceCurrenciesCount(groupId);
	}

	public static int getCommerceCurrenciesCount(long groupId, boolean active) {
		return getService().getCommerceCurrenciesCount(groupId, active);
	}

	public static com.liferay.commerce.currency.model.CommerceCurrency getCommerceCurrency(
		long commerceCurrencyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceCurrency(commerceCurrencyId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.currency.model.CommerceCurrency setActive(
		long commerceCurrencyId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().setActive(commerceCurrencyId, active);
	}

	public static com.liferay.commerce.currency.model.CommerceCurrency setPrimary(
		long commerceCurrencyId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().setPrimary(commerceCurrencyId, primary);
	}

	public static com.liferay.commerce.currency.model.CommerceCurrency updateCommerceCurrency(
		long commerceCurrencyId, java.lang.String code,
		java.util.Map<java.util.Locale, java.lang.String> nameMap, double rate,
		java.lang.String roundingType, boolean primary, double priority,
		boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceCurrency(commerceCurrencyId, code, nameMap,
			rate, roundingType, primary, priority, active, serviceContext);
	}

	public static void updateExchangeRate(long commerceCurrencyId,
		com.liferay.commerce.currency.util.ExchangeRateProvider exchangeRateProvider)
		throws java.lang.Exception {
		getService().updateExchangeRate(commerceCurrencyId, exchangeRateProvider);
	}

	public static void updateExchangeRates(long groupId,
		com.liferay.commerce.currency.util.ExchangeRateProvider exchangeRateProvider)
		throws java.lang.Exception {
		getService().updateExchangeRates(groupId, exchangeRateProvider);
	}

	public static CommerceCurrencyService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceCurrencyService, CommerceCurrencyService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceCurrencyService.class);
}