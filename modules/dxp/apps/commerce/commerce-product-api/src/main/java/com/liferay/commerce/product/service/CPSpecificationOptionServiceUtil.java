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
 * Provides the remote service utility for CPSpecificationOption. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPSpecificationOptionServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPSpecificationOptionService
 * @see com.liferay.commerce.product.service.base.CPSpecificationOptionServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPSpecificationOptionServiceImpl
 * @generated
 */
@ProviderType
public class CPSpecificationOptionServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPSpecificationOptionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPSpecificationOption addCPSpecificationOption(
		long cpOptionCategoryId,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		boolean facetable, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPSpecificationOption(cpOptionCategoryId, titleMap,
			descriptionMap, facetable, key, serviceContext);
	}

	public static void deleteCPSpecificationOption(long cpSpecificationOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCPSpecificationOption(cpSpecificationOptionId);
	}

	public static com.liferay.commerce.product.model.CPSpecificationOption fetchCPSpecificationOption(
		long cpSpecificationOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCPSpecificationOption(cpSpecificationOptionId);
	}

	public static com.liferay.commerce.product.model.CPSpecificationOption fetchCPSpecificationOption(
		long groupId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCPSpecificationOption(groupId, key);
	}

	public static com.liferay.commerce.product.model.CPSpecificationOption getCPSpecificationOption(
		long cpSpecificationOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPSpecificationOption(cpSpecificationOptionId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPSpecificationOption> getCPSpecificationOptions(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPSpecificationOption> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPSpecificationOptions(groupId, start, end,
			orderByComparator);
	}

	public static int getCPSpecificationOptionsCount(long groupId) {
		return getService().getCPSpecificationOptionsCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPSpecificationOption> searchCPSpecificationOptions(
		long companyId, long groupId, String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCPSpecificationOptions(companyId, groupId, keywords,
			start, end, sort);
	}

	public static com.liferay.commerce.product.model.CPSpecificationOption updateCPSpecificationOption(
		long cpSpecificationOptionId, long cpOptionCategoryId,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		boolean facetable, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPSpecificationOption(cpSpecificationOptionId,
			cpOptionCategoryId, titleMap, descriptionMap, facetable, key,
			serviceContext);
	}

	public static CPSpecificationOptionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPSpecificationOptionService, CPSpecificationOptionService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPSpecificationOptionService.class);

		ServiceTracker<CPSpecificationOptionService, CPSpecificationOptionService> serviceTracker =
			new ServiceTracker<CPSpecificationOptionService, CPSpecificationOptionService>(bundle.getBundleContext(),
				CPSpecificationOptionService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}