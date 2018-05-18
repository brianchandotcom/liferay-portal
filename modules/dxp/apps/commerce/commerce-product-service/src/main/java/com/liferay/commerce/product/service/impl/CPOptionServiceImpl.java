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
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.base.CPOptionServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CPOptionPermission;
import com.liferay.commerce.product.service.permission.CPPermission;
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
public class CPOptionServiceImpl extends CPOptionServiceBaseImpl {

	@Override
	public CPOption addCPOption(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String ddmFormFieldTypeName, boolean facetable, boolean required,
			boolean skuContributor, String key, ServiceContext serviceContext)
		throws PortalException {

		CPPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CPActionKeys.ADD_COMMERCE_PRODUCT_OPTION);

		return cpOptionLocalService.addCPOption(
			nameMap, descriptionMap, ddmFormFieldTypeName, facetable, required,
			skuContributor, key, serviceContext);
	}

	@Override
	public void deleteCPOption(long cpOptionId) throws PortalException {
		CPOptionPermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.DELETE);

		cpOptionLocalService.deleteCPOption(cpOptionId);
	}

	@Override
	public CPOption fetchCPOption(long cpOptionId) throws PortalException {
		CPOption cpOption = cpOptionLocalService.fetchCPOption(cpOptionId);

		if (cpOption != null) {
			CPOptionPermission.check(
				getPermissionChecker(), cpOption, ActionKeys.VIEW);
		}

		return cpOption;
	}

	@Override
	public CPOption fetchCPOption(long groupId, String key)
		throws PortalException {

		CPOption cpOption = cpOptionLocalService.fetchCPOption(groupId, key);

		if (cpOption != null) {
			CPOptionPermission.check(
				getPermissionChecker(), cpOption, ActionKeys.VIEW);
		}

		return cpOption;
	}

	@Override
	public CPOption getCPOption(long cpOptionId) throws PortalException {
		CPOptionPermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.VIEW);

		return cpOptionLocalService.getCPOption(cpOptionId);
	}

	@Override
	public List<CPOption> getCPOptions(long groupId, int start, int end) {
		return cpOptionLocalService.getCPOptions(groupId, start, end);
	}

	@Override
	public List<CPOption> getCPOptions(
		long groupId, int start, int end,
		OrderByComparator<CPOption> orderByComparator) {

		return cpOptionLocalService.getCPOptions(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCPOptionsCount(long groupId) {
		return cpOptionLocalService.getCPOptionsCount(groupId);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		return cpOptionLocalService.search(searchContext);
	}

	@Override
	public BaseModelSearchResult<CPOption> searchCPOptions(
			long companyId, long groupId, String keywords, int start, int end,
			Sort sort)
		throws PortalException {

		return cpOptionLocalService.searchCPOptions(
			companyId, groupId, keywords, start, end, sort);
	}

	@Override
	public CPOption setFacetable(long cpOptionId, boolean facetable)
		throws PortalException {

		CPOptionPermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.UPDATE);

		return cpOptionLocalService.setFacetable(cpOptionId, facetable);
	}

	@Override
	public CPOption setRequired(long cpOptionId, boolean required)
		throws PortalException {

		CPOptionPermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.UPDATE);

		return cpOptionLocalService.setRequired(cpOptionId, required);
	}

	@Override
	public CPOption setSkuContributor(long cpOptionId, boolean skuContributor)
		throws PortalException {

		CPOptionPermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.UPDATE);

		return cpOptionLocalService.setSkuContributor(
			cpOptionId, skuContributor);
	}

	@Override
	public CPOption updateCPOption(
			long cpOptionId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String ddmFormFieldTypeName,
			boolean facetable, boolean required, boolean skuContributor,
			String key, ServiceContext serviceContext)
		throws PortalException {

		CPOptionPermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.UPDATE);

		return cpOptionLocalService.updateCPOption(
			cpOptionId, nameMap, descriptionMap, ddmFormFieldTypeName,
			facetable, required, skuContributor, key, serviceContext);
	}

}