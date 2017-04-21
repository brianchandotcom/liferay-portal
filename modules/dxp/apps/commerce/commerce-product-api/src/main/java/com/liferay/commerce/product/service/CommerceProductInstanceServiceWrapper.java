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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceProductInstanceService}.
 *
 * @author Marco Leo
 * @see CommerceProductInstanceService
 * @generated
 */
@ProviderType
public class CommerceProductInstanceServiceWrapper
	implements CommerceProductInstanceService,
		ServiceWrapper<CommerceProductInstanceService> {
	public CommerceProductInstanceServiceWrapper(
		CommerceProductInstanceService commerceProductInstanceService) {
		_commerceProductInstanceService = commerceProductInstanceService;
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductInstance addCommerceProductInstance(
		long commerceProductDefinitionId, java.lang.String sku,
		java.lang.String ddmContent, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductInstanceService.addCommerceProductInstance(commerceProductDefinitionId,
			sku, ddmContent, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductInstance deleteCommerceProductInstance(
		com.liferay.commerce.product.model.CommerceProductInstance commerceProductInstance)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductInstanceService.deleteCommerceProductInstance(commerceProductInstance);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductInstance deleteCommerceProductInstance(
		long commerceProductInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductInstanceService.deleteCommerceProductInstance(commerceProductInstanceId);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductInstance getCommerceProductInstance(
		long commerceProductInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductInstanceService.getCommerceProductInstance(commerceProductInstanceId);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductInstance updateCommerceProductInstance(
		long commerceProductInstanceId, java.lang.String sku,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductInstanceService.updateCommerceProductInstance(commerceProductInstanceId,
			sku, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductInstance updateStatus(
		long userId, long commerceProductInstanceId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductInstanceService.updateStatus(userId,
			commerceProductInstanceId, status, serviceContext, workflowContext);
	}

	@Override
	public int getCommerceProductInstancesCount(
		long commerceProductDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductInstanceService.getCommerceProductInstancesCount(commerceProductDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceProductInstanceService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductInstance> getCommerceProductInstances(
		long commerceProductDefinitionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductInstanceService.getCommerceProductInstances(commerceProductDefinitionId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductInstance> getCommerceProductInstances(
		long commerceProductDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductInstance> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductInstanceService.getCommerceProductInstances(commerceProductDefinitionId,
			start, end, orderByComparator);
	}

	@Override
	public CommerceProductInstanceService getWrappedService() {
		return _commerceProductInstanceService;
	}

	@Override
	public void setWrappedService(
		CommerceProductInstanceService commerceProductInstanceService) {
		_commerceProductInstanceService = commerceProductInstanceService;
	}

	private CommerceProductInstanceService _commerceProductInstanceService;
}