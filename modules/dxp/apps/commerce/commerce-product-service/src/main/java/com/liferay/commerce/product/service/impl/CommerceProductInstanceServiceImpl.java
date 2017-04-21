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

package com.liferay.commerce.product.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.constants.CommerceProductActionKeys;
import com.liferay.commerce.product.model.CommerceProductInstance;
import com.liferay.commerce.product.service.base.CommerceProductInstanceServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CommerceProductDefinitionPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Marco Leo
 */
@ProviderType
public class CommerceProductInstanceServiceImpl
	extends CommerceProductInstanceServiceBaseImpl {

	@Override
	public CommerceProductInstance addCommerceProductInstance(
			long commerceProductDefinitionId, String sku, String ddmContent,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		CommerceProductDefinitionPermission.check(
			getPermissionChecker(), commerceProductDefinitionId,
			CommerceProductActionKeys.ADD_COMMERCE_PRODUCT_INSTANCE);

		return commerceProductInstanceLocalService.addCommerceProductInstance(
			commerceProductDefinitionId, sku, ddmContent, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public CommerceProductInstance deleteCommerceProductInstance(
			CommerceProductInstance commerceProductInstance)
		throws PortalException {

		CommerceProductDefinitionPermission.checkCommerceProductInstance(
			getPermissionChecker(), commerceProductInstance,
			CommerceProductActionKeys.DELETE_COMMERCE_PRODUCT_INSTANCE);

		return commerceProductInstanceLocalService.
			deleteCommerceProductInstance(commerceProductInstance);
	}

	@Override
	public CommerceProductInstance deleteCommerceProductInstance(
			long commerceProductInstanceId)
		throws PortalException {

		CommerceProductDefinitionPermission.checkCommerceProductInstance(
			getPermissionChecker(), commerceProductInstanceId,
			CommerceProductActionKeys.DELETE_COMMERCE_PRODUCT_INSTANCE);

		return commerceProductInstanceLocalService.
			deleteCommerceProductInstance(commerceProductInstanceId);
	}

	@Override
	public List<CommerceProductInstance> getCommerceProductInstances(
			long commerceProductDefinitionId, int start, int end)
		throws PortalException {

		CommerceProductDefinitionPermission.check(
			getPermissionChecker(), commerceProductDefinitionId,
			ActionKeys.VIEW);

		return commerceProductInstanceLocalService.getCommerceProductInstances(
			commerceProductDefinitionId, start, end);
	}

	@Override
	public List<CommerceProductInstance> getCommerceProductInstances(
			long commerceProductDefinitionId, int start, int end,
			OrderByComparator<CommerceProductInstance> orderByComparator)
		throws PortalException {

		CommerceProductDefinitionPermission.check(
			getPermissionChecker(), commerceProductDefinitionId,
			ActionKeys.VIEW);

		return commerceProductInstanceLocalService.getCommerceProductInstances(
			commerceProductDefinitionId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceProductInstancesCount(
			long commerceProductDefinitionId)
		throws PortalException {

		CommerceProductDefinitionPermission.check(
			getPermissionChecker(), commerceProductDefinitionId,
			ActionKeys.VIEW);

		return commerceProductInstanceLocalService.
			getCommerceProductInstancesCount(commerceProductDefinitionId);
	}

	@Override
	public CommerceProductInstance updateCommerceProductInstance(
			long commerceProductInstanceId, String sku, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		CommerceProductDefinitionPermission.checkCommerceProductInstance(
			getPermissionChecker(), commerceProductInstanceId,
			CommerceProductActionKeys.UPDATE_COMMERCE_PRODUCT_INSTANCE);

		return commerceProductInstanceLocalService.
			updateCommerceProductInstance(
				commerceProductInstanceId, sku, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, serviceContext);
	}

	@Override
	public CommerceProductInstance updateStatus(
			long userId, long commerceProductInstanceId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		CommerceProductDefinitionPermission.checkCommerceProductInstance(
			getPermissionChecker(), commerceProductInstanceId,
			CommerceProductActionKeys.UPDATE_COMMERCE_PRODUCT_INSTANCE);

		return commerceProductInstanceLocalService.updateStatus(
			userId, commerceProductInstanceId, status, serviceContext,
			workflowContext);
	}

}