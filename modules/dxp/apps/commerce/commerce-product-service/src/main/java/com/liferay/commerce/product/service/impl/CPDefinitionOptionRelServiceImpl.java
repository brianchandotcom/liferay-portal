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

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.service.base.CPDefinitionOptionRelServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CPDefinitionPermission;
import com.liferay.portal.kernel.exception.PortalException;
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
	public CPDefinitionOptionRel
			addCPDefinitionOptionRel(
				long cpDefinitionId, long cpOptionId,
				Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
				String ddmFormFieldTypeName, int priority, boolean facetable,
				boolean skuContributor, ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId,
			ActionKeys.UPDATE);

		return cpDefinitionOptionRelLocalService.
			addCPDefinitionOptionRel(
				cpDefinitionId, cpOptionId, nameMap,
				descriptionMap, ddmFormFieldTypeName, priority, facetable,
				skuContributor, serviceContext);
	}

	@Override
	public CPDefinitionOptionRel
			addCPDefinitionOptionRel(
				long cpDefinitionId, long cpOptionId,
				ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId,
			ActionKeys.UPDATE);

		return cpDefinitionOptionRelLocalService.
			addCPDefinitionOptionRel(
				cpDefinitionId, cpOptionId,
				serviceContext);
	}

	@Override
	public CPDefinitionOptionRel
			deleteCPDefinitionOptionRel(
				CPDefinitionOptionRel
					cpDefinitionOptionRel)
		throws PortalException {

		CPDefinitionPermission.
			checkCPDefinitionOptionRel(
				getPermissionChecker(), cpDefinitionOptionRel,
				ActionKeys.UPDATE);

		return cpDefinitionOptionRelLocalService.
			deleteCPDefinitionOptionRel(
				cpDefinitionOptionRel);
	}

	@Override
	public CPDefinitionOptionRel
			deleteCPDefinitionOptionRel(
				long cpDefinitionOptionRelId)
		throws PortalException {

		CPDefinitionPermission.
			checkCPDefinitionOptionRel(
				getPermissionChecker(), cpDefinitionOptionRelId,
				ActionKeys.UPDATE);

		return cpDefinitionOptionRelLocalService.
			deleteCPDefinitionOptionRel(
				cpDefinitionOptionRelId);
	}

	@Override
	public CPDefinitionOptionRel
			fetchCPDefinitionOptionRel(
				long cpDefinitionOptionRelId)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelLocalService.
				fetchCPDefinitionOptionRel(
					cpDefinitionOptionRelId);

		if (cpDefinitionOptionRel != null) {
			CPDefinitionPermission.
				checkCPDefinitionOptionRel(
					getPermissionChecker(), cpDefinitionOptionRel,
					ActionKeys.VIEW);
		}

		return cpDefinitionOptionRel;
	}

	@Override
	public CPDefinitionOptionRel
			getCPDefinitionOptionRel(
				long cpDefinitionOptionRelId)
		throws PortalException {

		CPDefinitionPermission.
			checkCPDefinitionOptionRel(
				getPermissionChecker(), cpDefinitionOptionRelId,
				ActionKeys.VIEW);

		return cpDefinitionOptionRelLocalService.
			getCPDefinitionOptionRel(
				cpDefinitionOptionRelId);
	}

	@Override
	public List<CPDefinitionOptionRel>
			getCPDefinitionOptionRels(
				long cpDefinitionId, int start, int end)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId,
			ActionKeys.VIEW);

		return cpDefinitionOptionRelLocalService.
			getCPDefinitionOptionRels(
				cpDefinitionId, start, end);
	}

	@Override
	public List<CPDefinitionOptionRel>
			getCPDefinitionOptionRels(
				long cpDefinitionId, int start, int end,
				OrderByComparator<CPDefinitionOptionRel>
					orderByComparator)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId,
			ActionKeys.VIEW);

		return cpDefinitionOptionRelLocalService.
			getCPDefinitionOptionRels(
				cpDefinitionId, start, end, orderByComparator);
	}

	@Override
	public int getCPDefinitionOptionRelsCount(
			long cpDefinitionId)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId,
			ActionKeys.VIEW);

		return cpDefinitionOptionRelLocalService.
			getCPDefinitionOptionRelsCount(
				cpDefinitionId);
	}

	@Override
	public CPDefinitionOptionRel
			updateCPDefinitionOptionRel(
				long cpDefinitionOptionRelId,
				long cpOptionId, Map<Locale, String> nameMap,
				Map<Locale, String> descriptionMap, String ddmFormFieldTypeName,
				int priority, boolean facetable, boolean skuContributor,
				ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.
			checkCPDefinitionOptionRel(
				getPermissionChecker(), cpDefinitionOptionRelId,
				ActionKeys.UPDATE);

		return cpDefinitionOptionRelLocalService.
			updateCPDefinitionOptionRel(
				cpDefinitionOptionRelId, cpOptionId,
				nameMap, descriptionMap, ddmFormFieldTypeName, priority,
				facetable, skuContributor, serviceContext);
	}

}