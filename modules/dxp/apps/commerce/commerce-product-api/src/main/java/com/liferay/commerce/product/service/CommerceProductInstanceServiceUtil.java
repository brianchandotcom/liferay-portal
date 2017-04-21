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
 * Provides the remote service utility for CommerceProductInstance. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CommerceProductInstanceServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceProductInstanceService
 * @see com.liferay.commerce.product.service.base.CommerceProductInstanceServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CommerceProductInstanceServiceImpl
 * @generated
 */
@ProviderType
public class CommerceProductInstanceServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CommerceProductInstanceServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CommerceProductInstance addCommerceProductInstance(
		long commerceProductDefinitionId, java.lang.String sku,
		java.lang.String ddmContent, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceProductInstance(commerceProductDefinitionId,
			sku, ddmContent, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	public static com.liferay.commerce.product.model.CommerceProductInstance deleteCommerceProductInstance(
		com.liferay.commerce.product.model.CommerceProductInstance commerceProductInstance)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceProductInstance(commerceProductInstance);
	}

	public static com.liferay.commerce.product.model.CommerceProductInstance deleteCommerceProductInstance(
		long commerceProductInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceProductInstance(commerceProductInstanceId);
	}

	public static com.liferay.commerce.product.model.CommerceProductInstance getCommerceProductInstance(
		long commerceProductInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceProductInstance(commerceProductInstanceId);
	}

	public static com.liferay.commerce.product.model.CommerceProductInstance updateCommerceProductInstance(
		long commerceProductInstanceId, java.lang.String sku,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceProductInstance(commerceProductInstanceId,
			sku, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	public static com.liferay.commerce.product.model.CommerceProductInstance updateStatus(
		long userId, long commerceProductInstanceId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateStatus(userId, commerceProductInstanceId, status,
			serviceContext, workflowContext);
	}

	public static int getCommerceProductInstancesCount(
		long commerceProductDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductInstancesCount(commerceProductDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductInstance> getCommerceProductInstances(
		long commerceProductDefinitionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductInstances(commerceProductDefinitionId,
			start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductInstance> getCommerceProductInstances(
		long commerceProductDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductInstance> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductInstances(commerceProductDefinitionId,
			start, end, orderByComparator);
	}

	public static CommerceProductInstanceService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceProductInstanceService, CommerceProductInstanceService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceProductInstanceService.class);
}