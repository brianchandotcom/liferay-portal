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
 * Provides the remote service utility for CommerceProductDefinitionOptionValueRel. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CommerceProductDefinitionOptionValueRelServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionOptionValueRelService
 * @see com.liferay.commerce.product.service.base.CommerceProductDefinitionOptionValueRelServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CommerceProductDefinitionOptionValueRelServiceImpl
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionOptionValueRelServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CommerceProductDefinitionOptionValueRelServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel addCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionRelId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionRelId,
			titleMap, priority, serviceContext);
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel deleteCommerceProductDefinitionOptionValueRel(
		com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRel);
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel deleteCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRelId);
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel getCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRelId);
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel updateCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRelId,
			titleMap, priority, serviceContext);
	}

	public static int getCommerceProductDefinitionOptionValueRelsCount(
		long commerceProductDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductDefinitionOptionValueRelsCount(commerceProductDefinitionOptionRelId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> getCommerceProductDefinitionOptionValueRels(
		long commerceProductDefinitionOptionRelId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductDefinitionOptionValueRels(commerceProductDefinitionOptionRelId,
			start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> getCommerceProductDefinitionOptionValueRels(
		long commerceProductDefinitionOptionRelId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductDefinitionOptionValueRels(commerceProductDefinitionOptionRelId,
			start, end, orderByComparator);
	}

	public static CommerceProductDefinitionOptionValueRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceProductDefinitionOptionValueRelService, CommerceProductDefinitionOptionValueRelService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceProductDefinitionOptionValueRelService.class);
}