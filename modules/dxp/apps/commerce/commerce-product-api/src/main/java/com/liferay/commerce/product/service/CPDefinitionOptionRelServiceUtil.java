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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CPDefinitionOptionRel. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPDefinitionOptionRelServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPDefinitionOptionRelService
 * @see com.liferay.commerce.product.service.base.CPDefinitionOptionRelServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPDefinitionOptionRelServiceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionOptionRelServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPDefinitionOptionRelServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPDefinitionOptionRel addCPDefinitionOptionRel(
		long cpDefinitionId, long cpOptionId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPDefinitionOptionRel(cpDefinitionId, cpOptionId,
			serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRel addCPDefinitionOptionRel(
		long cpDefinitionId, long cpOptionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, int priority, boolean facetable,
		boolean skuContributor,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPDefinitionOptionRel(cpDefinitionId, cpOptionId,
			nameMap, descriptionMap, ddmFormFieldTypeName, priority, facetable,
			skuContributor, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRel deleteCPDefinitionOptionRel(
		com.liferay.commerce.product.model.CPDefinitionOptionRel cpDefinitionOptionRel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPDefinitionOptionRel(cpDefinitionOptionRel);
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRel deleteCPDefinitionOptionRel(
		long cpDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPDefinitionOptionRel(cpDefinitionOptionRelId);
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRel fetchCPDefinitionOptionRel(
		long cpDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCPDefinitionOptionRel(cpDefinitionOptionRelId);
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRel getCPDefinitionOptionRel(
		long cpDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPDefinitionOptionRel(cpDefinitionOptionRelId);
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRel updateCPDefinitionOptionRel(
		long cpDefinitionOptionRelId, long cpOptionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, int priority, boolean facetable,
		boolean skuContributor,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPDefinitionOptionRel(cpDefinitionOptionRelId,
			cpOptionId, nameMap, descriptionMap, ddmFormFieldTypeName,
			priority, facetable, skuContributor, serviceContext);
	}

	public static int getCPDefinitionOptionRelsCount(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPDefinitionOptionRelsCount(cpDefinitionId);
	}

	public static int getSkuContributorCPDefinitionOptionRelCount(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getSkuContributorCPDefinitionOptionRelCount(cpDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPDefinitionOptionRels(cpDefinitionId, start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionOptionRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionOptionRels(cpDefinitionId, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getSkuContributorCPDefinitionOptionRels(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getSkuContributorCPDefinitionOptionRels(cpDefinitionId);
	}

	public static CPDefinitionOptionRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionOptionRelService, CPDefinitionOptionRelService> _serviceTracker =
		ServiceTrackerFactory.open(CPDefinitionOptionRelService.class);
}