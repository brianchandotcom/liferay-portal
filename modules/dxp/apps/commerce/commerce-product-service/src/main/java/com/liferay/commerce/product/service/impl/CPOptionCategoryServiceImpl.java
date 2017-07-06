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
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.service.base.CPOptionCategoryServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CPOptionCategoryPermission;
import com.liferay.commerce.product.service.permission.CPPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPOptionCategoryServiceImpl
	extends CPOptionCategoryServiceBaseImpl {

	@Override
	public CPOptionCategory addCPOptionCategory(
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			double priority, String key, ServiceContext serviceContext)
		throws PortalException {

		CPPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CPActionKeys.ADD_COMMERCE_PRODUCT_OPTION_CATEGORY);

		return cpOptionCategoryLocalService.addCPOptionCategory(
			titleMap, descriptionMap, priority, key, serviceContext);
	}

	@Override
	public CPOptionCategory deleteCPOptionCategory(
			CPOptionCategory cpOptionCategory)
		throws PortalException {

		CPOptionCategoryPermission.check(
			getPermissionChecker(), cpOptionCategory, ActionKeys.DELETE);

		return cpOptionCategoryLocalService.deleteCPOptionCategory(
			cpOptionCategory);
	}

	@Override
	public CPOptionCategory deleteCPOptionCategory(long cpOptionCategoryId)
		throws PortalException {

		CPOptionCategoryPermission.check(
			getPermissionChecker(), cpOptionCategoryId, ActionKeys.DELETE);

		return cpOptionCategoryLocalService.deleteCPOptionCategory(
			cpOptionCategoryId);
	}

	@Override
	public CPOptionCategory fetchCPOptionCategory(long cpOptionCategoryId)
		throws PortalException {

		CPOptionCategory cpOptionCategory =
			cpOptionCategoryLocalService.fetchCPOptionCategory(
				cpOptionCategoryId);

		if (cpOptionCategory != null) {
			CPOptionCategoryPermission.check(
				getPermissionChecker(), cpOptionCategory, ActionKeys.VIEW);
		}

		return cpOptionCategory;
	}

	@Override
	public List<CPOptionCategory> getCPOptionCategories(
		long groupId, int start, int end) {

		return cpOptionCategoryPersistence.filterFindByGroupId(
			groupId, start, end);
	}

	@Override
	public List<CPOptionCategory> getCPOptionCategories(
		long groupId, int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator) {

		return cpOptionCategoryPersistence.filterFindByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCPOptionCategoriesCount(long groupId) {
		return cpOptionCategoryPersistence.filterCountByGroupId(groupId);
	}

	@Override
	public CPOptionCategory getCPOptionCategory(long cpOptionCategoryId)
		throws PortalException {

		CPOptionCategoryPermission.check(
			getPermissionChecker(), cpOptionCategoryId, ActionKeys.VIEW);

		return cpOptionCategoryLocalService.getCPOptionCategory(
			cpOptionCategoryId);
	}

	@Override
	public CPOptionCategory updateCPOptionCategory(
			long cpOptionCategoryId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, double priority, String key,
			ServiceContext serviceContext)
		throws PortalException {

		CPOptionCategoryPermission.check(
			getPermissionChecker(), cpOptionCategoryId, ActionKeys.UPDATE);

		return cpOptionCategoryLocalService.updateCPOptionCategory(
			cpOptionCategoryId, titleMap, descriptionMap, priority, key,
			serviceContext);
	}

}