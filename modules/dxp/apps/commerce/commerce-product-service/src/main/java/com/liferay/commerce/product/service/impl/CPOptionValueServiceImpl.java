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
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.base.CPOptionValueServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class CPOptionValueServiceImpl extends CPOptionValueServiceBaseImpl {

	@Override
	public CPOptionValue addCPOptionValue(
			long cpOptionId, Map<Locale, String> titleMap, double priority,
			String key, ServiceContext serviceContext)
		throws PortalException {

		_cpOptionModelResourcePermission.check(
			getPermissionChecker(), cpOptionId,
			CPActionKeys.ADD_COMMERCE_PRODUCT_OPTION_VALUE);

		return cpOptionValueLocalService.addCPOptionValue(
			cpOptionId, titleMap, priority, key, serviceContext);
	}

	@Override
	public void deleteCPOptionValue(CPOptionValue cpOptionValue)
		throws PortalException {

		_cpOptionModelResourcePermission.check(
			getPermissionChecker(), cpOptionValue.getCPOptionId(),
			CPActionKeys.DELETE_COMMERCE_PRODUCT_OPTION_VALUE);

		cpOptionValueLocalService.deleteCPOptionValue(cpOptionValue);
	}

	@Override
	public void deleteCPOptionValue(long cpOptionValueId)
		throws PortalException {

		CPOptionValue cpOptionValue =
			cpOptionValueLocalService.getCPOptionValue(cpOptionValueId);

		_cpOptionModelResourcePermission.check(
			getPermissionChecker(), cpOptionValue.getCPOptionId(),
			CPActionKeys.DELETE_COMMERCE_PRODUCT_OPTION_VALUE);

		cpOptionValueLocalService.deleteCPOptionValue(cpOptionValue);
	}

	@Override
	public CPOptionValue fetchCPOptionValue(long cpOptionValueId)
		throws PortalException {

		CPOptionValue cpOptionValue =
			cpOptionValueLocalService.fetchCPOptionValue(cpOptionValueId);

		if (cpOptionValue != null) {
			_cpOptionModelResourcePermission.check(
				getPermissionChecker(), cpOptionValue.getCPOptionId(),
				ActionKeys.VIEW);
		}

		return cpOptionValue;
	}

	@Override
	public CPOptionValue getCPOptionValue(long cpOptionValueId)
		throws PortalException {

		CPOptionValue cpOptionValue =
			cpOptionValueLocalService.getCPOptionValue(cpOptionValueId);

		_cpOptionModelResourcePermission.check(
			getPermissionChecker(), cpOptionValue.getCPOptionId(),
			ActionKeys.VIEW);

		return cpOptionValue;
	}

	@Override
	public List<CPOptionValue> getCPOptionValues(
			long cpOptionId, int start, int end)
		throws PortalException {

		_cpOptionModelResourcePermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.VIEW);

		return cpOptionValueLocalService.getCPOptionValues(
			cpOptionId, start, end);
	}

	@Override
	public List<CPOptionValue> getCPOptionValues(
			long cpOptionId, int start, int end,
			OrderByComparator<CPOptionValue> orderByComparator)
		throws PortalException {

		_cpOptionModelResourcePermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.VIEW);

		return cpOptionValueLocalService.getCPOptionValues(
			cpOptionId, start, end, orderByComparator);
	}

	@Override
	public int getCPOptionValuesCount(long cpOptionId) throws PortalException {
		_cpOptionModelResourcePermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.VIEW);

		return cpOptionValueLocalService.getCPOptionValuesCount(cpOptionId);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		return cpOptionValueLocalService.search(searchContext);
	}

	@Override
	public BaseModelSearchResult<CPOptionValue> searchCPOptionValues(
			long companyId, long groupId, long cpOptionId, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		return cpOptionValueLocalService.searchCPOptionValues(
			companyId, groupId, cpOptionId, keywords, start, end, sort);
	}

	@Override
	public CPOptionValue updateCPOptionValue(
			long cpOptionValueId, Map<Locale, String> titleMap, double priority,
			String key, ServiceContext serviceContext)
		throws PortalException {

		CPOptionValue cpOptionValue =
			cpOptionValueLocalService.getCPOptionValue(cpOptionValueId);

		_cpOptionModelResourcePermission.check(
			getPermissionChecker(), cpOptionValue.getCPOptionId(),
			CPActionKeys.UPDATE_COMMERCE_PRODUCT_OPTION_VALUE);

		return cpOptionValueLocalService.updateCPOptionValue(
			cpOptionValueId, titleMap, priority, key, serviceContext);
	}

	private static volatile ModelResourcePermission<CPOption>
		_cpOptionModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CPOptionValueServiceImpl.class,
				"_cpOptionModelResourcePermission", CPOption.class);

}