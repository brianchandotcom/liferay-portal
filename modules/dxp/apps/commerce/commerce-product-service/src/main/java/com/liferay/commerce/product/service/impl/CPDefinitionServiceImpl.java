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
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.base.CPDefinitionServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CPDefinitionPermission;
import com.liferay.commerce.product.service.permission.CPPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 */
@ProviderType
public class CPDefinitionServiceImpl
	extends CPDefinitionServiceBaseImpl {

	@Override
	public CPDefinition addCPDefinition(
			String baseSKU, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String productTypeName,
			String ddmStructureKey, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		CPPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CPActionKeys.ADD_COMMERCE_PRODUCT_DEFINITION);

		return cpDefinitionLocalService.
			addCPDefinition(
				baseSKU, titleMap, descriptionMap, productTypeName,
				ddmStructureKey, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				serviceContext);
	}

	@Override
	public CPDefinition deleteCPDefinition(
			CPDefinition cpDefinition)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinition,
			ActionKeys.DELETE);

		return cpDefinitionLocalService.
			deleteCPDefinition(cpDefinition);
	}

	@Override
	public CPDefinition deleteCPDefinition(
			long cpDefinitionId)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId,
			ActionKeys.DELETE);

		return cpDefinitionLocalService.
			deleteCPDefinition(cpDefinitionId);
	}

	@Override
	public CPDefinition fetchCPDefinition(
			long cpDefinitionId)
		throws PortalException {

		CPDefinition cpDefinition =
			cpDefinitionLocalService.
				fetchCPDefinition(cpDefinitionId);

		if (cpDefinition != null) {
			CPDefinitionPermission.check(
				getPermissionChecker(), cpDefinition,
				ActionKeys.VIEW);
		}

		return cpDefinition;
	}

	@Override
	public CPDefinition getCPDefinition(
			long cpDefinitionId)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId,
			ActionKeys.UPDATE);

		return cpDefinitionLocalService.
			getCPDefinition(cpDefinitionId);
	}

	@Override
	public List<CPDefinition> getCPDefinitions(
		long groupId, int start, int end) {

		return cpDefinitionLocalService.
			getCPDefinitions(groupId, start, end);
	}

	@Override
	public List<CPDefinition> getCPDefinitions(
		long groupId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return cpDefinitionLocalService.
			getCPDefinitions(
				groupId, start, end, orderByComparator);
	}

	@Override
	public int getCPDefinitionsCount(long groupId) {
		return cpDefinitionLocalService.
			getCPDefinitionsCount(groupId);
	}

	@Override
	public CPDefinition updateCPDefinition(
			long cpDefinitionId, String baseSKU,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String productTypeName, String ddmStructureKey,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId,
			ActionKeys.UPDATE);

		return cpDefinitionLocalService.
			updateCPDefinition(
				cpDefinitionId, baseSKU, titleMap, descriptionMap,
				productTypeName, ddmStructureKey, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, serviceContext);
	}

	@Override
	public CPDefinition updateStatus(
			long userId, long cpDefinitionId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId,
			ActionKeys.UPDATE);

		return cpDefinitionLocalService.updateStatus(
			userId, cpDefinitionId, status, serviceContext,
			workflowContext);
	}

}