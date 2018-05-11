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
 * Provides the remote service utility for CPOption. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPOptionServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPOptionService
 * @see com.liferay.commerce.product.service.base.CPOptionServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPOptionServiceImpl
 * @generated
 */
@ProviderType
public class CPOptionServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPOptionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPOption addCPOption(
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		String ddmFormFieldTypeName, boolean facetable, boolean required,
		boolean skuContributor, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPOption(nameMap, descriptionMap, ddmFormFieldTypeName,
			facetable, required, skuContributor, key, serviceContext);
	}

	public static void deleteCPOption(long cpOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCPOption(cpOptionId);
	}

	public static com.liferay.commerce.product.model.CPOption fetchCPOption(
		long cpOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCPOption(cpOptionId);
	}

	public static com.liferay.commerce.product.model.CPOption fetchCPOption(
		long groupId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCPOption(groupId, key);
	}

	public static com.liferay.commerce.product.model.CPOption getCPOption(
		long cpOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPOption(cpOptionId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPOption> getCPOptions(
		long groupId, int start, int end) {
		return getService().getCPOptions(groupId, start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPOption> getCPOptions(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPOption> orderByComparator) {
		return getService().getCPOptions(groupId, start, end, orderByComparator);
	}

	public static int getCPOptionsCount(long groupId) {
		return getService().getCPOptionsCount(groupId);
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

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPOption> searchCPOptions(
		long companyId, long groupId, String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCPOptions(companyId, groupId, keywords, start, end,
			sort);
	}

	public static com.liferay.commerce.product.model.CPOption setFacetable(
		long cpOptionId, boolean facetable)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().setFacetable(cpOptionId, facetable);
	}

	public static com.liferay.commerce.product.model.CPOption setRequired(
		long cpOptionId, boolean required)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().setRequired(cpOptionId, required);
	}

	public static com.liferay.commerce.product.model.CPOption setSkuContributor(
		long cpOptionId, boolean skuContributor)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().setSkuContributor(cpOptionId, skuContributor);
	}

	public static com.liferay.commerce.product.model.CPOption updateCPOption(
		long cpOptionId, java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		String ddmFormFieldTypeName, boolean facetable, boolean required,
		boolean skuContributor, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPOption(cpOptionId, nameMap, descriptionMap,
			ddmFormFieldTypeName, facetable, required, skuContributor, key,
			serviceContext);
	}

	public static CPOptionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPOptionService, CPOptionService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPOptionService.class);

		ServiceTracker<CPOptionService, CPOptionService> serviceTracker = new ServiceTracker<CPOptionService, CPOptionService>(bundle.getBundleContext(),
				CPOptionService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}