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
 * Provides the remote service utility for CPOptionCategory. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPOptionCategoryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPOptionCategoryService
 * @see com.liferay.commerce.product.service.base.CPOptionCategoryServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPOptionCategoryServiceImpl
 * @generated
 */
@ProviderType
public class CPOptionCategoryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPOptionCategoryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPOptionCategory addCPOptionCategory(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		double priority, java.lang.String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPOptionCategory(titleMap, descriptionMap, priority,
			key, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPOptionCategory deleteCPOptionCategory(
		com.liferay.commerce.product.model.CPOptionCategory cpOptionCategory)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPOptionCategory(cpOptionCategory);
	}

	public static com.liferay.commerce.product.model.CPOptionCategory deleteCPOptionCategory(
		long cpOptionCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPOptionCategory(cpOptionCategoryId);
	}

	public static com.liferay.commerce.product.model.CPOptionCategory fetchCPOptionCategory(
		long cpOptionCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCPOptionCategory(cpOptionCategoryId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPOptionCategory> getCPOptionCategories(
		long groupId, int start, int end) {
		return getService().getCPOptionCategories(groupId, start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPOptionCategory> getCPOptionCategories(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPOptionCategory> orderByComparator) {
		return getService()
				   .getCPOptionCategories(groupId, start, end, orderByComparator);
	}

	public static int getCPOptionCategoriesCount(long groupId) {
		return getService().getCPOptionCategoriesCount(groupId);
	}

	public static com.liferay.commerce.product.model.CPOptionCategory getCPOptionCategory(
		long cpOptionCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPOptionCategory(cpOptionCategoryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.product.model.CPOptionCategory updateCPOptionCategory(
		long cpOptionCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		double priority, java.lang.String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPOptionCategory(cpOptionCategoryId, titleMap,
			descriptionMap, priority, key, serviceContext);
	}

	public static CPOptionCategoryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPOptionCategoryService, CPOptionCategoryService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPOptionCategoryService.class);

		ServiceTracker<CPOptionCategoryService, CPOptionCategoryService> serviceTracker =
			new ServiceTracker<CPOptionCategoryService, CPOptionCategoryService>(bundle.getBundleContext(),
				CPOptionCategoryService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}