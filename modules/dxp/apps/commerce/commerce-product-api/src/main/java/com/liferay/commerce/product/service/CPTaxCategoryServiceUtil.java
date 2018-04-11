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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CPTaxCategory. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPTaxCategoryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPTaxCategoryService
 * @see com.liferay.commerce.product.service.base.CPTaxCategoryServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPTaxCategoryServiceImpl
 * @generated
 */
@ProviderType
public class CPTaxCategoryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPTaxCategoryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPTaxCategory addCPTaxCategory(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPTaxCategory(nameMap, descriptionMap, serviceContext);
	}

	public static void deleteCPTaxCategory(long cpTaxCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCPTaxCategory(cpTaxCategoryId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPTaxCategory> getCPTaxCategories(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPTaxCategories(groupId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPTaxCategory> getCPTaxCategories(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPTaxCategory> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPTaxCategories(groupId, start, end, orderByComparator);
	}

	public static int getCPTaxCategoriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPTaxCategoriesCount(groupId);
	}

	public static com.liferay.commerce.product.model.CPTaxCategory getCPTaxCategory(
		long cpTaxCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPTaxCategory(cpTaxCategoryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.product.model.CPTaxCategory updateCPTaxCategory(
		long cpTaxCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPTaxCategory(cpTaxCategoryId, nameMap, descriptionMap);
	}

	public static CPTaxCategoryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPTaxCategoryService, CPTaxCategoryService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPTaxCategoryService.class);

		ServiceTracker<CPTaxCategoryService, CPTaxCategoryService> serviceTracker =
			new ServiceTracker<CPTaxCategoryService, CPTaxCategoryService>(bundle.getBundleContext(),
				CPTaxCategoryService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}