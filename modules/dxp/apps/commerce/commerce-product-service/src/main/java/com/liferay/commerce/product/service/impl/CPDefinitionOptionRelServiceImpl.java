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

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.service.base.CPDefinitionOptionRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
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
public class CPDefinitionOptionRelServiceImpl
	extends CPDefinitionOptionRelServiceBaseImpl {

	@Override
	public CPDefinitionOptionRel addCPDefinitionOptionRel(
			long cpDefinitionId, long cpOptionId, ServiceContext serviceContext)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionOptionRelLocalService.addCPDefinitionOptionRel(
			cpDefinitionId, cpOptionId, true, serviceContext);
	}

	@Override
	public void deleteCPDefinitionOptionRel(long cpDefinitionOptionRelId)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelLocalService.getCPDefinitionOptionRel(
				cpDefinitionOptionRelId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionOptionRel.getCPDefinitionId(),
			ActionKeys.UPDATE);

		cpDefinitionOptionRelLocalService.deleteCPDefinitionOptionRel(
			cpDefinitionOptionRel);
	}

	@Override
	public CPDefinitionOptionRel fetchCPDefinitionOptionRel(
			long cpDefinitionOptionRelId)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelLocalService.fetchCPDefinitionOptionRel(
				cpDefinitionOptionRelId);

		if (cpDefinitionOptionRel != null) {
			_cpDefinitionModelResourcePermission.check(
				getPermissionChecker(),
				cpDefinitionOptionRel.getCPDefinitionId(), ActionKeys.VIEW);
		}

		return cpDefinitionOptionRel;
	}

	@Override
	public CPDefinitionOptionRel getCPDefinitionOptionRel(
			long cpDefinitionOptionRelId)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelLocalService.getCPDefinitionOptionRel(
				cpDefinitionOptionRelId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionOptionRel.getCPDefinitionId(),
			ActionKeys.VIEW);

		return cpDefinitionOptionRel;
	}

	@Override
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(
			long cpDefinitionId, boolean skuContributor)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpDefinitionOptionRelLocalService.getCPDefinitionOptionRels(
			cpDefinitionId, skuContributor);
	}

	@Override
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(
			long cpDefinitionId, int start, int end)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpDefinitionOptionRelLocalService.getCPDefinitionOptionRels(
			cpDefinitionId, start, end);
	}

	@Override
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(
			long cpDefinitionId, int start, int end,
			OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpDefinitionOptionRelLocalService.getCPDefinitionOptionRels(
			cpDefinitionId, start, end, orderByComparator);
	}

	@Override
	public int getCPDefinitionOptionRelsCount(long cpDefinitionId)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpDefinitionOptionRelLocalService.getCPDefinitionOptionRelsCount(
			cpDefinitionId);
	}

	@Override
	public int getCPDefinitionOptionRelsCount(
			long cpDefinitionId, boolean skuContributor)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpDefinitionOptionRelLocalService.getCPDefinitionOptionRelsCount(
			cpDefinitionId, skuContributor);
	}

	@Override
	public BaseModelSearchResult<CPDefinitionOptionRel>
			searchCPDefinitionOptionRels(
				long companyId, long groupId, long cpDefinitionId,
				String keywords, int start, int end, Sort sort)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

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

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelLocalService.getCPDefinitionOptionRel(
				cpDefinitionOptionRelId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionOptionRel.getCPDefinitionId(),
			ActionKeys.UPDATE);

		return cpDefinitionOptionRelLocalService.updateCPDefinitionOptionRel(
			cpDefinitionOptionRel.getCPDefinitionOptionRelId(), cpOptionId,
			nameMap, descriptionMap, ddmFormFieldTypeName, priority, facetable,
			required, skuContributor, serviceContext);
	}

	private static volatile ModelResourcePermission<CPDefinition>
		_cpDefinitionModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CPDefinitionOptionRelServiceImpl.class,
				"_cpDefinitionModelResourcePermission", CPDefinition.class);

}