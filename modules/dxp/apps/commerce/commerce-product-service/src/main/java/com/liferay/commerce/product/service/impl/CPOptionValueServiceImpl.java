/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.base.CPOptionValueServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CPOptionPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
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

		CPOptionPermission.check(
			getPermissionChecker(), cpOptionId,
			CPActionKeys.ADD_COMMERCE_PRODUCT_OPTION_VALUE);

		return cpOptionValueLocalService.addCPOptionValue(
			cpOptionId, titleMap, priority, key, serviceContext);
	}

	@Override
	public CPOptionValue deleteCPOptionValue(CPOptionValue cpOptionValue)
		throws PortalException {

		CPOptionPermission.checkCPOptionValue(
			getPermissionChecker(), cpOptionValue.getCPOptionValueId(),
			CPActionKeys.DELETE_COMMERCE_PRODUCT_OPTION_VALUE);

		return cpOptionValueLocalService.deleteCPOptionValue(cpOptionValue);
	}

	@Override
	public CPOptionValue deleteCPOptionValue(long cpOptionValueId)
		throws PortalException {

		CPOptionPermission.checkCPOptionValue(
			getPermissionChecker(), cpOptionValueId,
			CPActionKeys.DELETE_COMMERCE_PRODUCT_OPTION_VALUE);

		return cpOptionValueLocalService.deleteCPOptionValue(cpOptionValueId);
	}

	@Override
	public CPOptionValue fetchCPOptionValue(long cpOptionValueId)
		throws PortalException {

		CPOptionValue cpOptionValue =
			cpOptionValueLocalService.fetchCPOptionValue(cpOptionValueId);

		if (cpOptionValue != null) {
			CPOptionPermission.checkCPOptionValue(
				getPermissionChecker(), cpOptionValue, ActionKeys.VIEW);
		}

		return cpOptionValue;
	}

	@Override
	public CPOptionValue getCPOptionValue(long cpOptionValueId)
		throws PortalException {

		CPOptionPermission.checkCPOptionValue(
			getPermissionChecker(), cpOptionValueId, ActionKeys.VIEW);

		return cpOptionValueLocalService.getCPOptionValue(cpOptionValueId);
	}

	@Override
	public List<CPOptionValue> getCPOptionValues(
			long cpOptionId, int start, int end)
		throws PortalException {

		CPOptionPermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.VIEW);

		return cpOptionValueLocalService.getCPOptionValues(
			cpOptionId, start, end);
	}

	@Override
	public List<CPOptionValue> getCPOptionValues(
			long cpOptionId, int start, int end,
			OrderByComparator<CPOptionValue> orderByComparator)
		throws PortalException {

		CPOptionPermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.VIEW);

		return cpOptionValueLocalService.getCPOptionValues(
			cpOptionId, start, end, orderByComparator);
	}

	@Override
	public int getCPOptionValuesCount(long cpOptionId) throws PortalException {
		CPOptionPermission.check(
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

		CPOptionPermission.checkCPOptionValue(
			getPermissionChecker(), cpOptionValueId,
			CPActionKeys.UPDATE_COMMERCE_PRODUCT_OPTION_VALUE);

		return cpOptionValueLocalService.updateCPOptionValue(
			cpOptionValueId, titleMap, priority, key, serviceContext);
	}

}