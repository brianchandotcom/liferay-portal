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

import com.liferay.commerce.product.constants.CommerceProductActionKeys;
import com.liferay.commerce.product.model.CommerceProductDefinition;
import com.liferay.commerce.product.model.CommerceProductOption;
import com.liferay.commerce.product.service.base.CommerceProductOptionServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CommerceProductDefinitionPermission;
import com.liferay.commerce.product.service.permission.CommerceProductOptionPermission;
import com.liferay.commerce.product.service.permission.CommerceProductPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class CommerceProductOptionServiceImpl
	extends CommerceProductOptionServiceBaseImpl {


	@Override
	public CommerceProductOption fetchCommerceProductOption(
		long commerceProductOptionId)
		throws PortalException {

		CommerceProductOption commerceProductOption =
			commerceProductOptionLocalService.
				fetchCommerceProductOption(commerceProductOptionId);

		if (commerceProductOption != null) {
			CommerceProductOptionPermission.check(
				getPermissionChecker(), commerceProductOption,
				ActionKeys.VIEW);
		}

		return commerceProductOption;
	}

	@Override
	public CommerceProductOption addCommerceProductOption(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String ddmFormFieldTypeName, ServiceContext serviceContext)
		throws PortalException {

		CommerceProductPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceProductActionKeys.ADD_COMMERCE_PRODUCT_OPTION);

		return commerceProductOptionLocalService.addCommerceProductOption(
			nameMap, descriptionMap, ddmFormFieldTypeName, serviceContext);
	}

	@Override
	public CommerceProductOption deleteCommerceProductOption(
			CommerceProductOption commerceProductOption)
		throws PortalException {

		CommerceProductOptionPermission.check(
			getPermissionChecker(), commerceProductOption, ActionKeys.DELETE);

		return commerceProductOptionLocalService.deleteCommerceProductOption(
			commerceProductOption);
	}

	@Override
	public CommerceProductOption deleteCommerceProductOption(
			long commerceProductOptionId)
		throws PortalException {

		CommerceProductOptionPermission.check(
			getPermissionChecker(), commerceProductOptionId, ActionKeys.DELETE);

		return commerceProductOptionLocalService.deleteCommerceProductOption(
			commerceProductOptionId);
	}

	@Override
	public CommerceProductOption getCommerceProductOption(
			long commerceProductOptionId)
		throws PortalException {

		CommerceProductOptionPermission.check(
			getPermissionChecker(), commerceProductOptionId, ActionKeys.VIEW);

		return commerceProductOptionLocalService.getCommerceProductOption(
			commerceProductOptionId);
	}

	@Override
	public List<CommerceProductOption> getCommerceProductOptions(
		long groupId, int start, int end) {

		return commerceProductOptionLocalService.getCommerceProductOptions(
			groupId, start, end);
	}

	@Override
	public List<CommerceProductOption> getCommerceProductOptions(
		long groupId, int start, int end,
		OrderByComparator<CommerceProductOption> orderByComparator) {

		return commerceProductOptionLocalService.getCommerceProductOptions(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceProductOptionsCount(long groupId) {
		return commerceProductOptionLocalService.getCommerceProductOptionsCount(
			groupId);
	}

	@Override
	public CommerceProductOption updateCommerceProductOption(
			long commerceProductOptionId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String ddmFormFieldTypeName,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceProductOptionPermission.check(
			getPermissionChecker(), commerceProductOptionId, ActionKeys.UPDATE);

		return commerceProductOptionLocalService.updateCommerceProductOption(
			commerceProductOptionId, nameMap, descriptionMap,
			ddmFormFieldTypeName, serviceContext);
	}

}