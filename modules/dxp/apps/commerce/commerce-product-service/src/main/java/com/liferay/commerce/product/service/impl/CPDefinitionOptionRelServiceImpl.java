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

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.service.base.CPDefinitionOptionRelServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CPDefinitionPermission;
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
public class CPDefinitionOptionRelServiceImpl
	extends CPDefinitionOptionRelServiceBaseImpl {

	@Override
	public CPDefinitionOptionRel addCPDefinitionOptionRel(
			long cpDefinitionId, long cpOptionId, boolean importOptionValue,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionOptionRelLocalService.addCPDefinitionOptionRel(
			cpDefinitionId, cpOptionId, importOptionValue, serviceContext);
	}

	@Override
	public CPDefinitionOptionRel addCPDefinitionOptionRel(
			long cpDefinitionId, long cpOptionId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String ddmFormFieldTypeName,
			double priority, boolean facetable, boolean required,
			boolean skuContributor, boolean importOptionValue,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionOptionRelLocalService.addCPDefinitionOptionRel(
			cpDefinitionId, cpOptionId, nameMap, descriptionMap,
			ddmFormFieldTypeName, priority, facetable, required, skuContributor,
			importOptionValue, serviceContext);
	}

	@Override
	public CPDefinitionOptionRel addCPDefinitionOptionRel(
			long cpDefinitionId, long cpOptionId, ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionOptionRelLocalService.addCPDefinitionOptionRel(
			cpDefinitionId, cpOptionId, true, serviceContext);
	}

	@Override
	public CPDefinitionOptionRel deleteCPDefinitionOptionRel(
			CPDefinitionOptionRel cpDefinitionOptionRel)
		throws PortalException {

		CPDefinitionPermission.checkCPDefinitionOptionRel(
			getPermissionChecker(), cpDefinitionOptionRel, ActionKeys.UPDATE);

		return cpDefinitionOptionRelLocalService.deleteCPDefinitionOptionRel(
			cpDefinitionOptionRel);
	}

	@Override
	public CPDefinitionOptionRel deleteCPDefinitionOptionRel(
			long cpDefinitionOptionRelId)
		throws PortalException {

		CPDefinitionPermission.checkCPDefinitionOptionRel(
			getPermissionChecker(), cpDefinitionOptionRelId, ActionKeys.UPDATE);

		return cpDefinitionOptionRelLocalService.deleteCPDefinitionOptionRel(
			cpDefinitionOptionRelId);
	}

	@Override
	public CPDefinitionOptionRel fetchCPDefinitionOptionRel(
			long cpDefinitionOptionRelId)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelLocalService.fetchCPDefinitionOptionRel(
				cpDefinitionOptionRelId);

		if (cpDefinitionOptionRel != null) {
			CPDefinitionPermission.checkCPDefinitionOptionRel(
				getPermissionChecker(), cpDefinitionOptionRel, ActionKeys.VIEW);
		}

		return cpDefinitionOptionRel;
	}

	@Override
	public CPDefinitionOptionRel getCPDefinitionOptionRel(
			long cpDefinitionOptionRelId)
		throws PortalException {

		CPDefinitionPermission.checkCPDefinitionOptionRel(
			getPermissionChecker(), cpDefinitionOptionRelId, ActionKeys.VIEW);

		return cpDefinitionOptionRelLocalService.getCPDefinitionOptionRel(
			cpDefinitionOptionRelId);
	}

	@Override
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(
			long cpDefinitionId, boolean skuContributor)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpDefinitionOptionRelLocalService.getCPDefinitionOptionRels(
			cpDefinitionId, skuContributor);
	}

	@Override
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(
			long cpDefinitionId, int start, int end)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpDefinitionOptionRelLocalService.getCPDefinitionOptionRels(
			cpDefinitionId, start, end);
	}

	@Override
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(
			long cpDefinitionId, int start, int end,
			OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpDefinitionOptionRelLocalService.getCPDefinitionOptionRels(
			cpDefinitionId, start, end, orderByComparator);
	}

	@Override
	public int getCPDefinitionOptionRelsCount(long cpDefinitionId)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpDefinitionOptionRelLocalService.getCPDefinitionOptionRelsCount(
			cpDefinitionId);
	}

	@Override
	public int getCPDefinitionOptionRelsCount(
			long cpDefinitionId, boolean skuContributor)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpDefinitionOptionRelLocalService.getCPDefinitionOptionRelsCount(
			cpDefinitionId, skuContributor);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		return cpDefinitionOptionRelLocalService.search(searchContext);
	}

	@Override
	public BaseModelSearchResult<CPDefinitionOptionRel>
			searchCPDefinitionOptionRels(
				long companyId, long groupId, long cpDefinitionId,
				String keywords, int start, int end, Sort sort)
		throws PortalException {

		return cpDefinitionOptionRelLocalService.searchCPDefinitionOptionRels(
			companyId, groupId, cpDefinitionId, keywords, start, end, sort);
	}

	@Override
	public CPDefinitionOptionRel updateCPDefinitionOptionRel(
			long cpDefinitionOptionRelId, long cpOptionId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String ddmFormFieldTypeName, double priority, boolean facetable,
			boolean required, boolean skuContributor,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.checkCPDefinitionOptionRel(
			getPermissionChecker(), cpDefinitionOptionRelId, ActionKeys.UPDATE);

		return cpDefinitionOptionRelLocalService.updateCPDefinitionOptionRel(
			cpDefinitionOptionRelId, cpOptionId, nameMap, descriptionMap,
			ddmFormFieldTypeName, priority, facetable, required, skuContributor,
			serviceContext);
	}

}