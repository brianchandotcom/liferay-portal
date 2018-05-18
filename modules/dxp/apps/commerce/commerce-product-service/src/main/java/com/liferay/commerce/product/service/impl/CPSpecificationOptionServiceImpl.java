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
import com.liferay.commerce.product.constants.CPOptionCategoryConstants;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.base.CPSpecificationOptionServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CPOptionCategoryPermission;
import com.liferay.commerce.product.service.permission.CPPermission;
import com.liferay.commerce.product.service.permission.CPSpecificationOptionPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Andrea Di Giorgi
 */
public class CPSpecificationOptionServiceImpl
	extends CPSpecificationOptionServiceBaseImpl {

	@Override
	public CPSpecificationOption addCPSpecificationOption(
			long cpOptionCategoryId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, boolean facetable, String key,
			ServiceContext serviceContext)
		throws PortalException {

		CPPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CPActionKeys.ADD_COMMERCE_PRODUCT_SPECIFICATION_OPTION);

		if (cpOptionCategoryId !=
				CPOptionCategoryConstants.DEFAULT_CP_OPTION_CATEGORY_ID) {

			CPOptionCategoryPermission.check(
				getPermissionChecker(), cpOptionCategoryId, ActionKeys.VIEW);
		}

		return cpSpecificationOptionLocalService.addCPSpecificationOption(
			cpOptionCategoryId, titleMap, descriptionMap, facetable, key,
			serviceContext);
	}

	@Override
	public void deleteCPSpecificationOption(long cpSpecificationOptionId)
		throws PortalException {

		CPSpecificationOptionPermission.check(
			getPermissionChecker(), cpSpecificationOptionId, ActionKeys.DELETE);

		cpSpecificationOptionLocalService.deleteCPSpecificationOption(
			cpSpecificationOptionId);
	}

	@Override
	public CPSpecificationOption fetchCPSpecificationOption(
			long cpSpecificationOptionId)
		throws PortalException {

		CPSpecificationOptionPermission.check(
			getPermissionChecker(), cpSpecificationOptionId, ActionKeys.VIEW);

		return cpSpecificationOptionLocalService.fetchCPSpecificationOption(
			cpSpecificationOptionId);
	}

	@Override
	public CPSpecificationOption fetchCPSpecificationOption(
			long groupId, String key)
		throws PortalException {

		CPSpecificationOption cpSpecificationOption =
			cpSpecificationOptionLocalService.fetchCPSpecificationOption(
				groupId, key);

		if (cpSpecificationOption != null) {
			CPSpecificationOptionPermission.check(
				getPermissionChecker(), cpSpecificationOption, ActionKeys.VIEW);
		}

		return cpSpecificationOption;
	}

	@Override
	public CPSpecificationOption getCPSpecificationOption(
			long cpSpecificationOptionId)
		throws PortalException {

		CPSpecificationOptionPermission.check(
			getPermissionChecker(), cpSpecificationOptionId, ActionKeys.VIEW);

		return cpSpecificationOptionLocalService.getCPSpecificationOption(
			cpSpecificationOptionId);
	}

	@Override
	public List<CPSpecificationOption> getCPSpecificationOptions(
			long groupId, int start, int end,
			OrderByComparator<CPSpecificationOption> orderByComparator)
		throws PortalException {

		return cpSpecificationOptionPersistence.filterFindByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCPSpecificationOptionsCount(long groupId) {
		return cpSpecificationOptionPersistence.filterCountByGroupId(groupId);
	}

	@Override
	public BaseModelSearchResult<CPSpecificationOption>
			searchCPSpecificationOptions(
				long companyId, long groupId, String keywords, int start,
				int end, Sort sort)
		throws PortalException {

		return cpSpecificationOptionLocalService.searchCPSpecificationOptions(
			companyId, groupId, keywords, start, end, sort);
	}

	@Override
	public CPSpecificationOption updateCPSpecificationOption(
			long cpSpecificationOptionId, long cpOptionCategoryId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			boolean facetable, String key, ServiceContext serviceContext)
		throws PortalException {

		CPSpecificationOptionPermission.check(
			getPermissionChecker(), cpSpecificationOptionId, ActionKeys.UPDATE);

		if (cpOptionCategoryId !=
				CPOptionCategoryConstants.DEFAULT_CP_OPTION_CATEGORY_ID) {

			CPOptionCategoryPermission.check(
				getPermissionChecker(), cpOptionCategoryId, ActionKeys.VIEW);
		}

		return cpSpecificationOptionLocalService.updateCPSpecificationOption(
			cpSpecificationOptionId, cpOptionCategoryId, titleMap,
			descriptionMap, facetable, key, serviceContext);
	}

}