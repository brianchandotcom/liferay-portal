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

package com.liferay.commerce.currency.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

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
		String code, java.util.Map<java.util.Locale, String> nameMap,
		java.math.BigDecimal rate,
		java.util.Map<java.util.Locale, String> formatPatternMap,
		int maxFractionDigits, int minFractionDigits, String roundingMode,
		boolean primary, double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceCurrency(code, nameMap, rate, formatPatternMap,
			maxFractionDigits, minFractionDigits, roundingMode, primary,
			priority, active, serviceContext);
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

	public static com.liferay.commerce.currency.model.CommerceCurrency getCommerceCurrency(
		long groupId, String code)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceCurrency(groupId, code);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
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
		long commerceCurrencyId, String code,
		java.util.Map<java.util.Locale, String> nameMap,
		java.math.BigDecimal rate,
		java.util.Map<java.util.Locale, String> formatPatternMap,
		int maxFractionDigits, int minFractionDigits, String roundingMode,
		boolean primary, double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceCurrency(commerceCurrencyId, code, nameMap,
			rate, formatPatternMap, maxFractionDigits, minFractionDigits,
			roundingMode, primary, priority, active, serviceContext);
	}

	public static void updateExchangeRate(long commerceCurrencyId,
		String exchangeRateProviderKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateExchangeRate(commerceCurrencyId, exchangeRateProviderKey);
	}

	public static void updateExchangeRates(
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateExchangeRates(serviceContext);
	}

	public static CommerceCurrencyService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceCurrencyService, CommerceCurrencyService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceCurrencyService.class);

		ServiceTracker<CommerceCurrencyService, CommerceCurrencyService> serviceTracker =
			new ServiceTracker<CommerceCurrencyService, CommerceCurrencyService>(bundle.getBundleContext(),
				CommerceCurrencyService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}