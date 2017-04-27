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

import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.base.CPInstanceServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CPDefinitionPermission;
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
public class CPInstanceServiceImpl extends CPInstanceServiceBaseImpl {

	@Override
	public CPInstance addCPInstance(
			long cpDefinitionId, String sku, String ddmContent,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId,
			CPActionKeys.ADD_COMMERCE_PRODUCT_INSTANCE);

		return cpInstanceLocalService.addCPInstance(
			cpDefinitionId, sku, ddmContent, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public CPInstance deleteCPInstance(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), cpInstance,
			CPActionKeys.DELETE_COMMERCE_PRODUCT_INSTANCE);

		return cpInstanceLocalService.deleteCPInstance(cpInstance);
	}

	@Override
	public CPInstance deleteCPInstance(long cpInstanceId)
		throws PortalException {

		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), cpInstanceId,
			CPActionKeys.DELETE_COMMERCE_PRODUCT_INSTANCE);

		return cpInstanceLocalService.deleteCPInstance(cpInstanceId);
	}

	@Override
	public CPInstance getCPInstance(long cpInstanceId) throws PortalException {
		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), cpInstanceId, ActionKeys.VIEW);

		return cpInstanceLocalService.getCPInstance(cpInstanceId);
	}

	@Override
	public List<CPInstance> getCPInstances(
			long cpDefinitionId, int start, int end)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpInstanceLocalService.getCPInstances(
			cpDefinitionId, start, end);
	}

	@Override
	public List<CPInstance> getCPInstances(
			long cpDefinitionId, int start, int end,
			OrderByComparator<CPInstance> orderByComparator)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpInstanceLocalService.getCPInstances(
			cpDefinitionId, start, end, orderByComparator);
	}

	@Override
	public int getCPInstancesCount(long cpDefinitionId) throws PortalException {
		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpInstanceLocalService.getCPInstancesCount(cpDefinitionId);
	}

	@Override
	public CPInstance updateCPInstance(
			long cpInstanceId, String sku, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), cpInstanceId,
			CPActionKeys.UPDATE_COMMERCE_PRODUCT_INSTANCE);

		return cpInstanceLocalService.
			updateCPInstance(
				cpInstanceId, sku, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				serviceContext);
	}

	@Override
	public CPInstance updateStatus(
			long userId, long cpInstanceId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), cpInstanceId,
			CPActionKeys.UPDATE_COMMERCE_PRODUCT_INSTANCE);

		return cpInstanceLocalService.updateStatus(
			userId, cpInstanceId, status, serviceContext, workflowContext);
	}

}