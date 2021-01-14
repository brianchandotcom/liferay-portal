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
 * Provides the remote service utility for CPOption. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CPOptionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPOptionService
 * @generated
 */
public class CPOptionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPOptionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPOption addCPOption(
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String ddmFormFieldTypeName, boolean facetable, boolean required,
			boolean skuContributor, String key,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCPOption(
			nameMap, descriptionMap, ddmFormFieldTypeName, facetable, required,
			skuContributor, key, serviceContext);
	}

	public static void deleteCPOption(long cpOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCPOption(cpOptionId);
	}

	public static com.liferay.commerce.product.model.CPOption
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.product.model.CPOption fetchCPOption(
			long cpOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCPOption(cpOptionId);
	}

	public static com.liferay.commerce.product.model.CPOption fetchCPOption(
			long companyId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCPOption(companyId, key);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPOption>
			findCPOptionByCompanyId(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.product.model.CPOption>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().findCPOptionByCompanyId(
			companyId, start, end, orderByComparator);
	}

	public static com.liferay.commerce.product.model.CPOption getCPOption(
			long cpOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCPOption(cpOptionId);
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
		<com.liferay.commerce.product.model.CPOption> searchCPOptions(
				long companyId, String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCPOptions(
			companyId, keywords, start, end, sort);
	}

	public static com.liferay.commerce.product.model.CPOption updateCPOption(
			long cpOptionId, java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String ddmFormFieldTypeName, boolean facetable, boolean required,
			boolean skuContributor, String key,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCPOption(
			cpOptionId, nameMap, descriptionMap, ddmFormFieldTypeName,
			facetable, required, skuContributor, key, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPOption
			updateCPOptionExternalReferenceCode(
				long cpOptionId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCPOptionExternalReferenceCode(
			cpOptionId, externalReferenceCode);
	}

	public static com.liferay.commerce.product.model.CPOption upsertCPOption(
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String ddmFormFieldTypeName, boolean facetable, boolean required,
			boolean skuContributor, String key, String externalReferenceCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCPOption(
			nameMap, descriptionMap, ddmFormFieldTypeName, facetable, required,
			skuContributor, key, externalReferenceCode, serviceContext);
	}

	public static CPOptionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPOptionService, CPOptionService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPOptionService.class);

		ServiceTracker<CPOptionService, CPOptionService> serviceTracker =
			new ServiceTracker<CPOptionService, CPOptionService>(
				bundle.getBundleContext(), CPOptionService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}