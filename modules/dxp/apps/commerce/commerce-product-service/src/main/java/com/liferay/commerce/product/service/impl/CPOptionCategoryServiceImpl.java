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
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.service.base.CPOptionCategoryServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
public class CPOptionCategoryServiceImpl
	extends CPOptionCategoryServiceBaseImpl {

	@Override
	public CPOptionCategory addCPOptionCategory(
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			double priority, String key, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CPActionKeys.ADD_COMMERCE_PRODUCT_OPTION_CATEGORY);

		return cpOptionCategoryLocalService.addCPOptionCategory(
			titleMap, descriptionMap, priority, key, serviceContext);
	}

	@Override
	public void deleteCPOptionCategory(long cpOptionCategoryId)
		throws PortalException {

		CPOptionCategory cpOptionCategory =
			cpOptionCategoryService.getCPOptionCategory(cpOptionCategoryId);

		cpOptionCategoryLocalService.deleteCPOptionCategory(cpOptionCategory);
	}

	@Override
	public CPOptionCategory fetchCPOptionCategory(long cpOptionCategoryId)
		throws PortalException {

		CPOptionCategory cpOptionCategory =
			cpOptionCategoryLocalService.fetchCPOptionCategory(
				cpOptionCategoryId);

		if (cpOptionCategory != null) {
			_portletResourcePermission.check(
				getPermissionChecker(), cpOptionCategory.getGroupId(),
				CPActionKeys.MANAGE_COMMERCE_PRODUCT_OPTION_CATEGORIES);
		}

		return cpOptionCategory;
	}

	@Override
	public List<CPOptionCategory> getCPOptionCategories(
			long groupId, int start, int end)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_OPTION_CATEGORIES);

		return cpOptionCategoryLocalService.getCPOptionCategories(
			groupId, start, end);
	}

	@Override
	public List<CPOptionCategory> getCPOptionCategories(
			long groupId, int start, int end,
			OrderByComparator<CPOptionCategory> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_OPTION_CATEGORIES);

		return cpOptionCategoryLocalService.getCPOptionCategories(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCPOptionCategoriesCount(long groupId) throws PortalException {
		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_OPTION_CATEGORIES);

		return cpOptionCategoryLocalService.getCPOptionCategoriesCount(groupId);
	}

	@Override
	public CPOptionCategory getCPOptionCategory(long cpOptionCategoryId)
		throws PortalException {

		CPOptionCategory cpOptionCategory =
			cpOptionCategoryLocalService.getCPOptionCategory(
				cpOptionCategoryId);

		_portletResourcePermission.check(
			getPermissionChecker(), cpOptionCategory.getGroupId(),
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_OPTION_CATEGORIES);

		return cpOptionCategory;
	}

	@Override
	public CPOptionCategory updateCPOptionCategory(
			long cpOptionCategoryId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, double priority, String key,
			ServiceContext serviceContext)
		throws PortalException {

		CPOptionCategory cpOptionCategory =
			cpOptionCategoryService.getCPOptionCategory(cpOptionCategoryId);

		return cpOptionCategoryLocalService.updateCPOptionCategory(
			cpOptionCategory.getCPOptionCategoryId(), titleMap, descriptionMap,
			priority, key, serviceContext);
	}

	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CPOptionCategoryServiceImpl.class, "_portletResourcePermission",
				CPConstants.RESOURCE_NAME);

}