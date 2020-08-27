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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;

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

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CPActionKeys.ADD_COMMERCE_PRODUCT_OPTION_CATEGORY);

		return cpOptionCategoryLocalService.addCPOptionCategory(
			getUserId(), titleMap, descriptionMap, priority, key,
			serviceContext);
	}

	@Override
	public void deleteCPOptionCategory(long cpOptionCategoryId)
		throws PortalException {

		_cpOptionCategoryModelResourcePermission.check(
			getPermissionChecker(), cpOptionCategoryId, ActionKeys.DELETE);

		cpOptionCategoryLocalService.deleteCPOptionCategory(cpOptionCategoryId);
	}

	@Override
	public CPOptionCategory fetchCPOptionCategory(long cpOptionCategoryId)
		throws PortalException {

		CPOptionCategory cpOptionCategory =
			cpOptionCategoryLocalService.fetchCPOptionCategory(
				cpOptionCategoryId);

		if (cpOptionCategory != null) {
			_cpOptionCategoryModelResourcePermission.check(
				getPermissionChecker(), cpOptionCategory, ActionKeys.VIEW);
		}

		return cpOptionCategory;
	}

	@Override
	public CPOptionCategory getCPOptionCategory(long cpOptionCategoryId)
		throws PortalException {

		_cpOptionCategoryModelResourcePermission.check(
			getPermissionChecker(), cpOptionCategoryId, ActionKeys.VIEW);

		return cpOptionCategoryLocalService.getCPOptionCategory(
			cpOptionCategoryId);
	}

	@Override
	public BaseModelSearchResult<CPOptionCategory> searchCPOptionCategories(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		return cpOptionCategoryLocalService.searchCPOptionCategories(
			companyId, keywords, start, end, sort);
	}

	@Override
	public CPOptionCategory updateCPOptionCategory(
			long cpOptionCategoryId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, double priority, String key)
		throws PortalException {

		_cpOptionCategoryModelResourcePermission.check(
			getPermissionChecker(), cpOptionCategoryId, ActionKeys.UPDATE);

		return cpOptionCategoryLocalService.updateCPOptionCategory(
			cpOptionCategoryId, titleMap, descriptionMap, priority, key);
	}

	private static volatile ModelResourcePermission<CPOptionCategory>
		_cpOptionCategoryModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CPOptionCategoryServiceImpl.class,
				"_cpOptionCategoryModelResourcePermission",
				CPOptionCategory.class);

}