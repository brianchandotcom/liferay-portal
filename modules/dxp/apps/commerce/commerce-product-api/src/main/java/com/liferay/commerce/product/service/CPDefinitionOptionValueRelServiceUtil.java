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
 * Provides the remote service utility for CPDefinitionOptionValueRel. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPDefinitionOptionValueRelServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPDefinitionOptionValueRelService
 * @see com.liferay.commerce.product.service.base.CPDefinitionOptionValueRelServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPDefinitionOptionValueRelServiceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionOptionValueRelServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPDefinitionOptionValueRelServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel addCPDefinitionOptionValueRel(
		long cpDefinitionOptionRelId,
		java.util.Map<java.util.Locale, String> nameMap, double priority,
		String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPDefinitionOptionValueRel(cpDefinitionOptionRelId,
			nameMap, priority, key, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel deleteCPDefinitionOptionValueRel(
		com.liferay.commerce.product.model.CPDefinitionOptionValueRel cpDefinitionOptionValueRel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCPDefinitionOptionValueRel(cpDefinitionOptionValueRel);
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel deleteCPDefinitionOptionValueRel(
		long cpDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel fetchCPDefinitionOptionValueRel(
		long cpDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .fetchCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel getCPDefinitionOptionValueRel(
		long cpDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long cpDefinitionOptionRelId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionOptionValueRels(cpDefinitionOptionRelId,
			start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long cpDefinitionOptionRelId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionOptionValueRels(cpDefinitionOptionRelId,
			start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long groupId, String key, int start, int end) {
		return getService()
				   .getCPDefinitionOptionValueRels(groupId, key, start, end);
	}

	public static int getCPDefinitionOptionValueRelsCount(
		long cpDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionOptionValueRelsCount(cpDefinitionOptionRelId);
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

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> searchCPDefinitionOptionValueRels(
		long companyId, long groupId, long cpDefinitionOptionRelId,
		String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCPDefinitionOptionValueRels(companyId, groupId,
			cpDefinitionOptionRelId, keywords, start, end, sort);
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel updateCPDefinitionOptionValueRel(
		long cpDefinitionOptionValueRelId,
		java.util.Map<java.util.Locale, String> nameMap, double priority,
		String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId,
			nameMap, priority, key, serviceContext);
	}

	public static CPDefinitionOptionValueRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionOptionValueRelService, CPDefinitionOptionValueRelService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionOptionValueRelService.class);

		ServiceTracker<CPDefinitionOptionValueRelService, CPDefinitionOptionValueRelService> serviceTracker =
			new ServiceTracker<CPDefinitionOptionValueRelService, CPDefinitionOptionValueRelService>(bundle.getBundleContext(),
				CPDefinitionOptionValueRelService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}