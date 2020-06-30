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
 * Provides the remote service utility for CPSpecificationOption. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CPSpecificationOptionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPSpecificationOptionService
 * @generated
 */
public class CPSpecificationOptionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPSpecificationOptionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPSpecificationOption
			addCPSpecificationOption(
				long cpOptionCategoryId,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				boolean facetable, String key,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCPSpecificationOption(
			cpOptionCategoryId, titleMap, descriptionMap, facetable, key,
			serviceContext);
	}

	public static void deleteCPSpecificationOption(long cpSpecificationOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCPSpecificationOption(cpSpecificationOptionId);
	}

	public static com.liferay.commerce.product.model.CPSpecificationOption
			fetchCPSpecificationOption(long companyId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCPSpecificationOption(companyId, key);
	}

	public static com.liferay.commerce.product.model.CPSpecificationOption
			getCPSpecificationOption(long cpSpecificationOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCPSpecificationOption(cpSpecificationOptionId);
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
		<com.liferay.commerce.product.model.CPSpecificationOption>
				searchCPSpecificationOptions(
					long companyId, Boolean facetable, String keywords,
					int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCPSpecificationOptions(
			companyId, facetable, keywords, start, end, sort);
	}

	public static com.liferay.commerce.product.model.CPSpecificationOption
			updateCPSpecificationOption(
				long cpSpecificationOptionId, long cpOptionCategoryId,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				boolean facetable, String key,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCPSpecificationOption(
			cpSpecificationOptionId, cpOptionCategoryId, titleMap,
			descriptionMap, facetable, key, serviceContext);
	}

	public static CPSpecificationOptionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CPSpecificationOptionService, CPSpecificationOptionService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CPSpecificationOptionService.class);

		ServiceTracker
			<CPSpecificationOptionService, CPSpecificationOptionService>
				serviceTracker =
					new ServiceTracker
						<CPSpecificationOptionService,
						 CPSpecificationOptionService>(
							 bundle.getBundleContext(),
							 CPSpecificationOptionService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}