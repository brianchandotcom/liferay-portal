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

package com.liferay.commerce.vat.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceVatNumber. This utility wraps
 * {@link com.liferay.commerce.vat.service.impl.CommerceVatNumberServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVatNumberService
 * @see com.liferay.commerce.vat.service.base.CommerceVatNumberServiceBaseImpl
 * @see com.liferay.commerce.vat.service.impl.CommerceVatNumberServiceImpl
 * @generated
 */
@ProviderType
public class CommerceVatNumberServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.vat.service.impl.CommerceVatNumberServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.vat.model.CommerceVatNumber addCommerceVatNumber(
		java.lang.String className, long classPK, java.lang.String vatNumber,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceVatNumber(className, classPK, vatNumber,
			serviceContext);
	}

	public static void deleteCommerceVatNumber(long commerceVatNumberId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceVatNumber(commerceVatNumberId);
	}

	public static com.liferay.commerce.vat.model.CommerceVatNumber fetchCommerceVatNumber(
		long commerceVatNumberId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCommerceVatNumber(commerceVatNumberId);
	}

	public static com.liferay.commerce.vat.model.CommerceVatNumber fetchCommerceVatNumber(
		long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCommerceVatNumber(groupId, className, classPK);
	}

	public static java.util.List<com.liferay.commerce.vat.model.CommerceVatNumber> getCommerceVatNumbers(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.vat.model.CommerceVatNumber> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceVatNumbers(groupId, start, end, orderByComparator);
	}

	public static int getCommerceVatNumbersCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceVatNumbersCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.vat.model.CommerceVatNumber> searchCommerceVatNumbers(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCommerceVatNumbers(companyId, groupId, keywords,
			start, end, sort);
	}

	public static com.liferay.commerce.vat.model.CommerceVatNumber updateCommerceVatNumber(
		long commerceVatNumberId, java.lang.String vatNumber)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceVatNumber(commerceVatNumberId, vatNumber);
	}

	public static CommerceVatNumberService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceVatNumberService, CommerceVatNumberService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceVatNumberService.class);
}