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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceCountry. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceCountryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCountryService
 * @see com.liferay.commerce.service.base.CommerceCountryServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceCountryServiceImpl
 * @generated
 */
@ProviderType
public class CommerceCountryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceCountryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceCountry addCommerceCountry(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		boolean billingAllowed, boolean shippingAllowed,
		java.lang.String twoLettersISOCode,
		java.lang.String threeLettersISOCode, int numericISOCode,
		boolean subjectToVAT, double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceCountry(nameMap, billingAllowed,
			shippingAllowed, twoLettersISOCode, threeLettersISOCode,
			numericISOCode, subjectToVAT, priority, active, serviceContext);
	}

	public static void deleteCommerceCountry(long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceCountry(commerceCountryId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry> getBillingCommerceCountries(
		long groupId, boolean billingAllowed, boolean active) {
		return getService()
				   .getBillingCommerceCountries(groupId, billingAllowed, active);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry> getCommerceCountries(
		long groupId, boolean active) {
		return getService().getCommerceCountries(groupId, active);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry> getCommerceCountries(
		long groupId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceCountry> orderByComparator) {
		return getService()
				   .getCommerceCountries(groupId, active, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry> getCommerceCountries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceCountry> orderByComparator) {
		return getService()
				   .getCommerceCountries(groupId, start, end, orderByComparator);
	}

	public static int getCommerceCountriesCount(long groupId) {
		return getService().getCommerceCountriesCount(groupId);
	}

	public static int getCommerceCountriesCount(long groupId, boolean active) {
		return getService().getCommerceCountriesCount(groupId, active);
	}

	public static com.liferay.commerce.model.CommerceCountry getCommerceCountry(
		long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceCountry(commerceCountryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry> getShippingCommerceCountries(
		long groupId, boolean shippingAllowed, boolean active) {
		return getService()
				   .getShippingCommerceCountries(groupId, shippingAllowed,
			active);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry> getWarehouseCommerceCountries(
		long groupId, boolean all)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getWarehouseCommerceCountries(groupId, all);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceCountry> searchCommerceCountries(
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().searchCommerceCountries(searchContext);
	}

	public static com.liferay.commerce.model.CommerceCountry updateCommerceCountry(
		long commerceCountryId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		boolean billingAllowed, boolean shippingAllowed,
		java.lang.String twoLettersISOCode,
		java.lang.String threeLettersISOCode, int numericISOCode,
		boolean subjectToVAT, double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceCountry(commerceCountryId, nameMap,
			billingAllowed, shippingAllowed, twoLettersISOCode,
			threeLettersISOCode, numericISOCode, subjectToVAT, priority,
			active, serviceContext);
	}

	public static CommerceCountryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceCountryService, CommerceCountryService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceCountryService.class);

		ServiceTracker<CommerceCountryService, CommerceCountryService> serviceTracker =
			new ServiceTracker<CommerceCountryService, CommerceCountryService>(bundle.getBundleContext(),
				CommerceCountryService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}