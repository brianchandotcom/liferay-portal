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

import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.base.CPTaxCategoryServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CPPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public class CPTaxCategoryServiceImpl extends CPTaxCategoryServiceBaseImpl {

	@Override
	public CPTaxCategory addCPTaxCategory(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			ServiceContext serviceContext)
		throws PortalException {

		CPPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_TAX_CATEGORIES);

		return cpTaxCategoryLocalService.addCPTaxCategory(
			nameMap, descriptionMap, serviceContext);
	}

	@Override
	public void deleteCPTaxCategory(long cpTaxCategoryId)
		throws PortalException {

		CPTaxCategory cpTaxCategory =
			cpTaxCategoryLocalService.getCPTaxCategory(cpTaxCategoryId);

		CPPermission.check(
			getPermissionChecker(), cpTaxCategory.getGroupId(),
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_TAX_CATEGORIES);

		cpTaxCategoryLocalService.deleteCPTaxCategory(cpTaxCategory);
	}

	@Override
	public List<CPTaxCategory> getCPTaxCategories(long groupId)
		throws PortalException {

		CPPermission.check(
			getPermissionChecker(), groupId,
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_TAX_CATEGORIES);

		return cpTaxCategoryLocalService.getCPTaxCategories(groupId);
	}

	@Override
	public List<CPTaxCategory> getCPTaxCategories(
			long groupId, int start, int end,
			OrderByComparator<CPTaxCategory> orderByComparator)
		throws PortalException {

		CPPermission.check(
			getPermissionChecker(), groupId,
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_TAX_CATEGORIES);

		return cpTaxCategoryLocalService.getCPTaxCategories(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCPTaxCategoriesCount(long groupId) throws PortalException {
		CPPermission.check(
			getPermissionChecker(), groupId,
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_TAX_CATEGORIES);

		return cpTaxCategoryLocalService.getCPTaxCategoriesCount(groupId);
	}

	@Override
	public CPTaxCategory getCPTaxCategory(long cpTaxCategoryId)
		throws PortalException {

		CPTaxCategory cpTaxCategory =
			cpTaxCategoryLocalService.getCPTaxCategory(cpTaxCategoryId);

		CPPermission.check(
			getPermissionChecker(), cpTaxCategory.getGroupId(),
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_TAX_CATEGORIES);

		return cpTaxCategory;
	}

	@Override
	public CPTaxCategory updateCPTaxCategory(
			long cpTaxCategoryId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap)
		throws PortalException {

		CPTaxCategory cpTaxCategory =
			cpTaxCategoryLocalService.getCPTaxCategory(cpTaxCategoryId);

		CPPermission.check(
			getPermissionChecker(), cpTaxCategory.getGroupId(),
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_TAX_CATEGORIES);

		return cpTaxCategoryLocalService.updateCPTaxCategory(
			cpTaxCategoryId, nameMap, descriptionMap);
	}

}