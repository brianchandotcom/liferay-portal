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
import com.liferay.commerce.product.model.CommerceProductDefinition;
import com.liferay.commerce.product.service.base.CommerceProductDefinitionServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CommerceProductDefinitionPermission;
import com.liferay.commerce.product.service.permission.CommerceProductPermission;
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
public class CommerceProductDefinitionServiceImpl
	extends CommerceProductDefinitionServiceBaseImpl {

	@Override
	public CommerceProductDefinition addCommerceProductDefinition(
			String baseSKU, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String productTypeName,
			String ddmStructureKey, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceProductPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceProductActionKeys.ADD_COMMERCE_PRODUCT_DEFINITION);

		return commerceProductDefinitionLocalService.
			addCommerceProductDefinition(
				baseSKU, titleMap, descriptionMap, productTypeName,
				ddmStructureKey, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				serviceContext);
	}

	@Override
	public CommerceProductDefinition deleteCommerceProductDefinition(
			CommerceProductDefinition commerceProductDefinition)
		throws PortalException {

		CommerceProductDefinitionPermission.check(
			getPermissionChecker(), commerceProductDefinition,
			ActionKeys.DELETE);

		return commerceProductDefinitionLocalService.
			deleteCommerceProductDefinition(commerceProductDefinition);
	}

	@Override
	public CommerceProductDefinition deleteCommerceProductDefinition(
			long commerceProductDefinitionId)
		throws PortalException {

		CommerceProductDefinitionPermission.check(
			getPermissionChecker(), commerceProductDefinitionId,
			ActionKeys.DELETE);

		return commerceProductDefinitionLocalService.
			deleteCommerceProductDefinition(commerceProductDefinitionId);
	}

	@Override
	public CommerceProductDefinition getCommerceProductDefinition(
			long commerceProductDefinitionId)
		throws PortalException {

		CommerceProductDefinitionPermission.check(
			getPermissionChecker(), commerceProductDefinitionId,
			ActionKeys.UPDATE);

		return commerceProductDefinitionLocalService.
			getCommerceProductDefinition(commerceProductDefinitionId);
	}

	@Override
	public List<CommerceProductDefinition> getCommerceProductDefinitions(
		long groupId, int start, int end) {

		return commerceProductDefinitionLocalService.
			getCommerceProductDefinitions(groupId, start, end);
	}

	@Override
	public List<CommerceProductDefinition> getCommerceProductDefinitions(
		long groupId, int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {

		return commerceProductDefinitionLocalService.
			getCommerceProductDefinitions(
				groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceProductDefinitionsCount(long groupId) {
		return commerceProductDefinitionLocalService.
			getCommerceProductDefinitionsCount(groupId);
	}

	@Override
	public CommerceProductDefinition updateCommerceProductDefinition(
			long commerceProductDefinitionId, String baseSKU,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String productTypeName, String ddmStructureKey,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		CommerceProductDefinitionPermission.check(
			getPermissionChecker(), commerceProductDefinitionId,
			ActionKeys.UPDATE);

		return commerceProductDefinitionLocalService.
			updateCommerceProductDefinition(
				commerceProductDefinitionId, baseSKU, titleMap, descriptionMap,
				productTypeName, ddmStructureKey, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, serviceContext);
	}

	@Override
	public CommerceProductDefinition updateStatus(
			long userId, long commerceProductDefinitionId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		CommerceProductDefinitionPermission.check(
			getPermissionChecker(), commerceProductDefinitionId,
			ActionKeys.UPDATE);

		return commerceProductDefinitionLocalService.updateStatus(
			userId, commerceProductDefinitionId, status, serviceContext,
			workflowContext);
	}

}