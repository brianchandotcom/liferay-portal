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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CPOptionCategory. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CPOptionCategoryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPOptionCategoryService
 * @generated
 */
public class CPOptionCategoryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPOptionCategoryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPOptionCategory
			addCPOptionCategory(
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				double priority, String key,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCPOptionCategory(
			titleMap, descriptionMap, priority, key, serviceContext);
	}

	public static void deleteCPOptionCategory(long cpOptionCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCPOptionCategory(cpOptionCategoryId);
	}

	public static com.liferay.commerce.product.model.CPOptionCategory
			fetchCPOptionCategory(long cpOptionCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCPOptionCategory(cpOptionCategoryId);
	}

	public static com.liferay.commerce.product.model.CPOptionCategory
			getCPOptionCategory(long cpOptionCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCPOptionCategory(cpOptionCategoryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.product.model.CPOptionCategory>
				searchCPOptionCategories(
					long companyId, String keywords, int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCPOptionCategories(
			companyId, keywords, start, end, sort);
	}

	public static com.liferay.commerce.product.model.CPOptionCategory
			updateCPOptionCategory(
				long cpOptionCategoryId,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				double priority, String key)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCPOptionCategory(
			cpOptionCategoryId, titleMap, descriptionMap, priority, key);
	}

	public static CPOptionCategoryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CPOptionCategoryService, CPOptionCategoryService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPOptionCategoryService.class);

		ServiceTracker<CPOptionCategoryService, CPOptionCategoryService>
			serviceTracker =
				new ServiceTracker
					<CPOptionCategoryService, CPOptionCategoryService>(
						bundle.getBundleContext(),
						CPOptionCategoryService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}