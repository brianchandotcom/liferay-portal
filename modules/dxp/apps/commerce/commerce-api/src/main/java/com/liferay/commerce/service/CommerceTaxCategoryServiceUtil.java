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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceTaxCategory. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceTaxCategoryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxCategoryService
 * @see com.liferay.commerce.service.base.CommerceTaxCategoryServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceTaxCategoryServiceImpl
 * @generated
 */
@ProviderType
public class CommerceTaxCategoryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceTaxCategoryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceTaxCategory addCommerceTaxCategory(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceTaxCategory(nameMap, descriptionMap,
			serviceContext);
	}

	public static void deleteCommerceTaxCategory(long commerceTaxCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceTaxCategory(commerceTaxCategoryId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceTaxCategory> getCommerceTaxCategories(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceTaxCategory> orderByComparator) {
		return getService()
				   .getCommerceTaxCategories(groupId, start, end,
			orderByComparator);
	}

	public static int getCommerceTaxCategoriesCount(long groupId) {
		return getService().getCommerceTaxCategoriesCount(groupId);
	}

	public static com.liferay.commerce.model.CommerceTaxCategory getCommerceTaxCategory(
		long commerceTaxCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceTaxCategory(commerceTaxCategoryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.model.CommerceTaxCategory updateCommerceTaxCategory(
		long commerceTaxCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceTaxCategory(commerceTaxCategoryId, nameMap,
			descriptionMap);
	}

	public static CommerceTaxCategoryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceTaxCategoryService, CommerceTaxCategoryService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceTaxCategoryService.class);
}