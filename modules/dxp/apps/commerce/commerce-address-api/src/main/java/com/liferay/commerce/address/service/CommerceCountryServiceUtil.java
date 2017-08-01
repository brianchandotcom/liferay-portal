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

package com.liferay.commerce.address.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceCountry. This utility wraps
 * {@link com.liferay.commerce.address.service.impl.CommerceCountryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCountryService
 * @see com.liferay.commerce.address.service.base.CommerceCountryServiceBaseImpl
 * @see com.liferay.commerce.address.service.impl.CommerceCountryServiceImpl
 * @generated
 */
@ProviderType
public class CommerceCountryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.address.service.impl.CommerceCountryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.address.model.CommerceCountry addCommerceCountry(
		java.lang.String name, boolean allowsBilling, boolean allowsShipping,
		java.lang.String twoLettersISOCode,
		java.lang.String threeLettersISOCode, int numericISOCode,
		boolean subjectToVAT, double priority, boolean published,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceCountry(name, allowsBilling, allowsShipping,
			twoLettersISOCode, threeLettersISOCode, numericISOCode,
			subjectToVAT, priority, published, serviceContext);
	}

	public static com.liferay.commerce.address.model.CommerceCountry deleteCommerceCountry(
		long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceCountry(commerceCountryId);
	}

	public static com.liferay.commerce.address.model.CommerceCountry fetchCommerceCountry(
		long commerceCountryId) {
		return getService().fetchCommerceCountry(commerceCountryId);
	}

	public static com.liferay.commerce.address.model.CommerceCountry updateCommerceCountry(
		long commerceCountryId, java.lang.String name, boolean allowsBilling,
		boolean allowsShipping, java.lang.String twoLettersISOCode,
		java.lang.String threeLettersISOCode, int numericISOCode,
		boolean subjectToVAT, double priority, boolean published)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceCountry(commerceCountryId, name,
			allowsBilling, allowsShipping, twoLettersISOCode,
			threeLettersISOCode, numericISOCode, subjectToVAT, priority,
			published);
	}

	public static int getCommerceCountriesCount() {
		return getService().getCommerceCountriesCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.commerce.address.model.CommerceCountry> getCommerceCountries(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.address.model.CommerceCountry> orderByComparator) {
		return getService().getCommerceCountries(start, end, orderByComparator);
	}

	public static CommerceCountryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceCountryService, CommerceCountryService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceCountryService.class);
}