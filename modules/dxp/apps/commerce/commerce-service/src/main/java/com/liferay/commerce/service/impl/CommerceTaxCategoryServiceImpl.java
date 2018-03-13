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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.model.CommerceTaxCategory;
import com.liferay.commerce.service.base.CommerceTaxCategoryServiceBaseImpl;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxCategoryServiceImpl
	extends CommerceTaxCategoryServiceBaseImpl {

	@Override
	public CommerceTaxCategory addCommerceTaxCategory(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_TAX_CATEGORIES);

		return commerceTaxCategoryLocalService.addCommerceTaxCategory(
			nameMap, descriptionMap, serviceContext);
	}

	@Override
	public void deleteCommerceTaxCategory(long commerceTaxCategoryId)
		throws PortalException {

		CommerceTaxCategory commerceTaxCategory =
			commerceTaxCategoryLocalService.getCommerceTaxCategory(
				commerceTaxCategoryId);

		CommercePermission.check(
			getPermissionChecker(), commerceTaxCategory.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_TAX_CATEGORIES);

		commerceTaxCategoryLocalService.deleteCommerceTaxCategory(
			commerceTaxCategory);
	}

	@Override
	public List<CommerceTaxCategory> getCommerceTaxCategories(
		long groupId, int start, int end,
		OrderByComparator<CommerceTaxCategory> orderByComparator) {

		return commerceTaxCategoryLocalService.getCommerceTaxCategories(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTaxCategoriesCount(long groupId) {
		return commerceTaxCategoryLocalService.getCommerceTaxCategoriesCount(
			groupId);
	}

	@Override
	public CommerceTaxCategory getCommerceTaxCategory(
			long commerceTaxCategoryId)
		throws PortalException {

		return commerceTaxCategoryLocalService.getCommerceTaxCategory(
			commerceTaxCategoryId);
	}

	@Override
	public CommerceTaxCategory updateCommerceTaxCategory(
			long commerceTaxCategoryId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap)
		throws PortalException {

		CommerceTaxCategory commerceTaxCategory =
			commerceTaxCategoryLocalService.getCommerceTaxCategory(
				commerceTaxCategoryId);

		CommercePermission.check(
			getPermissionChecker(), commerceTaxCategory.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_TAX_CATEGORIES);

		return commerceTaxCategoryLocalService.updateCommerceTaxCategory(
			commerceTaxCategoryId, nameMap, descriptionMap);
	}

}