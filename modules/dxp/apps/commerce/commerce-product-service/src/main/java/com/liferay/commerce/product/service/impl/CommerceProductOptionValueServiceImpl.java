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
import com.liferay.commerce.product.model.CommerceProductOptionValue;
import com.liferay.commerce.product.service.base.CommerceProductOptionValueServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CommerceProductOptionPermission;
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
public class CommerceProductOptionValueServiceImpl
	extends CommerceProductOptionValueServiceBaseImpl {

	@Override
	public CommerceProductOptionValue addCommerceProductOptionValue(
			long commerceProductOptionId, Map<Locale, String> titleMap,
			int priority, ServiceContext serviceContext)
		throws PortalException {

		CommerceProductOptionPermission.check(
			getPermissionChecker(), commerceProductOptionId,
			CommerceProductActionKeys.ADD_COMMERCE_PRODUCT_OPTION_VALUE);

		return commerceProductOptionValueLocalService.
			addCommerceProductOptionValue(
				commerceProductOptionId, titleMap, priority, serviceContext);
	}

	@Override
	public CommerceProductOptionValue deleteCommerceProductOptionValue(
			CommerceProductOptionValue commerceProductOptionValue)
		throws PortalException {

		CommerceProductOptionPermission.checkCommerceProductOptionValue(
			getPermissionChecker(),
			commerceProductOptionValue.getCommerceProductOptionValueId(),
			CommerceProductActionKeys.DELETE_COMMERCE_PRODUCT_OPTION_VALUE);

		return commerceProductOptionValueLocalService.
			deleteCommerceProductOptionValue(commerceProductOptionValue);
	}

	@Override
	public CommerceProductOptionValue deleteCommerceProductOptionValue(
			long commerceProductOptionValueId)
		throws PortalException {

		CommerceProductOptionPermission.checkCommerceProductOptionValue(
			getPermissionChecker(), commerceProductOptionValueId,
			CommerceProductActionKeys.DELETE_COMMERCE_PRODUCT_OPTION_VALUE);

		return commerceProductOptionValueLocalService.
			deleteCommerceProductOptionValue(commerceProductOptionValueId);
	}

	@Override
	public CommerceProductOptionValue getCommerceProductOptionValue(
			long commerceProductOptionValueId)
		throws PortalException {

		CommerceProductOptionPermission.checkCommerceProductOptionValue(
			getPermissionChecker(), commerceProductOptionValueId,
			ActionKeys.VIEW);

		return commerceProductOptionValueLocalService.
			getCommerceProductOptionValue(commerceProductOptionValueId);
	}

	@Override
	public List<CommerceProductOptionValue> getCommerceProductOptionValues(
			long commerceProductOptionId, int start, int end)
		throws PortalException {

		CommerceProductOptionPermission.check(
			getPermissionChecker(), commerceProductOptionId, ActionKeys.VIEW);
		return commerceProductOptionValueLocalService.
			getCommerceProductOptionValues(commerceProductOptionId, start, end);
	}

	@Override
	public List<CommerceProductOptionValue> getCommerceProductOptionValues(
			long commerceProductOptionId, int start, int end,
			OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws PortalException {

		CommerceProductOptionPermission.check(
			getPermissionChecker(), commerceProductOptionId, ActionKeys.VIEW);
		return commerceProductOptionValueLocalService.
			getCommerceProductOptionValues(
				commerceProductOptionId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceProductOptionValuesCount(long commerceProductOptionId)
		throws PortalException {

		CommerceProductOptionPermission.check(
			getPermissionChecker(), commerceProductOptionId, ActionKeys.VIEW);

		return commerceProductOptionValueLocalService.
			getCommerceProductOptionValuesCount(commerceProductOptionId);
	}

	@Override
	public CommerceProductOptionValue updateCommerceProductOptionValue(
			long commerceProductOptionValueId, Map<Locale, String> titleMap,
			int priority, ServiceContext serviceContext)
		throws PortalException {

		CommerceProductOptionPermission.checkCommerceProductOptionValue(
			getPermissionChecker(), commerceProductOptionValueId,
			CommerceProductActionKeys.UPDATE_COMMERCE_PRODUCT_OPTION_VALUE);

		return commerceProductOptionValueLocalService.
			updateCommerceProductOptionValue(
				commerceProductOptionValueId, titleMap, priority,
				serviceContext);
	}

}