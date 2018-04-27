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
 * Provides the remote service utility for CPOptionValue. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPOptionValueServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPOptionValueService
 * @see com.liferay.commerce.product.service.base.CPOptionValueServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPOptionValueServiceImpl
 * @generated
 */
@ProviderType
public class CPOptionValueServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPOptionValueServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPOptionValue addCPOptionValue(
		long cpOptionId, java.util.Map<java.util.Locale, String> titleMap,
		double priority, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPOptionValue(cpOptionId, titleMap, priority, key,
			serviceContext);
	}

	public static com.liferay.commerce.product.model.CPOptionValue deleteCPOptionValue(
		com.liferay.commerce.product.model.CPOptionValue cpOptionValue)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPOptionValue(cpOptionValue);
	}

	public static com.liferay.commerce.product.model.CPOptionValue deleteCPOptionValue(
		long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPOptionValue(cpOptionValueId);
	}

	public static com.liferay.commerce.product.model.CPOptionValue fetchCPOptionValue(
		long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCPOptionValue(cpOptionValueId);
	}

	public static com.liferay.commerce.product.model.CPOptionValue getCPOptionValue(
		long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPOptionValue(cpOptionValueId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPOptionValue> getCPOptionValues(
		long cpOptionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPOptionValues(cpOptionId, start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPOptionValue> getCPOptionValues(
		long cpOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPOptionValue> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPOptionValues(cpOptionId, start, end, orderByComparator);
	}

	public static int getCPOptionValuesCount(long cpOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPOptionValuesCount(cpOptionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return getService().search(searchContext);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPOptionValue> searchCPOptionValues(
		long companyId, long groupId, long cpOptionId, String keywords,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCPOptionValues(companyId, groupId, cpOptionId,
			keywords, start, end, sort);
	}

	public static com.liferay.commerce.product.model.CPOptionValue updateCPOptionValue(
		long cpOptionValueId, java.util.Map<java.util.Locale, String> titleMap,
		double priority, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPOptionValue(cpOptionValueId, titleMap, priority,
			key, serviceContext);
	}

	public static CPOptionValueService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPOptionValueService, CPOptionValueService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPOptionValueService.class);

		ServiceTracker<CPOptionValueService, CPOptionValueService> serviceTracker =
			new ServiceTracker<CPOptionValueService, CPOptionValueService>(bundle.getBundleContext(),
				CPOptionValueService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}