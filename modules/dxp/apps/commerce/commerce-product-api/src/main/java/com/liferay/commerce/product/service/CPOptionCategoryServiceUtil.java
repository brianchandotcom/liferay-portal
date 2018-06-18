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
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		double priority, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPOptionCategory(titleMap, descriptionMap, priority,
			key, serviceContext);
	}

	public static void deleteCPOptionCategory(long cpOptionCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCPOptionCategory(cpOptionCategoryId);
	}

	public static com.liferay.commerce.product.model.CPOptionCategory fetchCPOptionCategory(
		long cpOptionCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCPOptionCategory(cpOptionCategoryId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPOptionCategory> getCPOptionCategories(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPOptionCategories(groupId, start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPOptionCategory> getCPOptionCategories(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPOptionCategory> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPOptionCategories(groupId, start, end, orderByComparator);
	}

	public static int getCPOptionCategoriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
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
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.product.model.CPOptionCategory updateCPOptionCategory(
		long cpOptionCategoryId,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		double priority, String key,
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