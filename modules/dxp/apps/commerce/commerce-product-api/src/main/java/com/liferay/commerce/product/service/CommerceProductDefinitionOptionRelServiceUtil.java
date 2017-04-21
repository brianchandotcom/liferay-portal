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
 * Provides the remote service utility for CommerceProductDefinitionOptionRel. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CommerceProductDefinitionOptionRelServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionOptionRelService
 * @see com.liferay.commerce.product.service.base.CommerceProductDefinitionOptionRelServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CommerceProductDefinitionOptionRelServiceImpl
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionOptionRelServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CommerceProductDefinitionOptionRelServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel addCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionId, long commerceProductOptionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceProductDefinitionOptionRel(commerceProductDefinitionId,
			commerceProductOptionId, nameMap, descriptionMap,
			ddmFormFieldTypeName, priority, serviceContext);
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel deleteCommerceProductDefinitionOptionRel(
		com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRel);
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel deleteCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRelId);
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel updateCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionOptionRelId,
		long commerceProductOptionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRelId,
			commerceProductOptionId, nameMap, descriptionMap,
			ddmFormFieldTypeName, priority, serviceContext);
	}

	public static int getCommerceProductDefinitionOptionRelsCount(
		long commerceProductDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductDefinitionOptionRelsCount(commerceProductDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> getCommerceProductDefinitionOptionRels(
		long commerceProductDefinitionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductDefinitionOptionRels(commerceProductDefinitionId,
			start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> getCommerceProductDefinitionOptionRels(
		long commerceProductDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductDefinitionOptionRels(commerceProductDefinitionId,
			start, end, orderByComparator);
	}

	public static CommerceProductDefinitionOptionRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceProductDefinitionOptionRelService, CommerceProductDefinitionOptionRelService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceProductDefinitionOptionRelService.class);
}