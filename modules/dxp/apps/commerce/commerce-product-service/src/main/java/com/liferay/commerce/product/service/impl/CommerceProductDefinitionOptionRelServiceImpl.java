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

import com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel;
import com.liferay.commerce.product.service.base.CommerceProductDefinitionOptionRelServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CommerceProductDefinitionPermission;
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
public class CommerceProductDefinitionOptionRelServiceImpl
	extends CommerceProductDefinitionOptionRelServiceBaseImpl {

	@Override
	public CommerceProductDefinitionOptionRel
			addCommerceProductDefinitionOptionRel(
				long commerceProductDefinitionId, long commerceProductOptionId,
				Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
				String ddmFormFieldTypeName, int priority,
				ServiceContext serviceContext)
		throws PortalException {

		CommerceProductDefinitionPermission.check(
			getPermissionChecker(), commerceProductDefinitionId,
			ActionKeys.UPDATE);

		return commerceProductDefinitionOptionRelLocalService.
			addCommerceProductDefinitionOptionRel(
				commerceProductDefinitionId, commerceProductOptionId, nameMap,
				descriptionMap, ddmFormFieldTypeName, priority, serviceContext);
	}

	@Override
	public CommerceProductDefinitionOptionRel
			deleteCommerceProductDefinitionOptionRel(
				CommerceProductDefinitionOptionRel
					commerceProductDefinitionOptionRel)
		throws PortalException {

		CommerceProductDefinitionPermission.
			checkCommerceProductDefinitionOptionRel(
				getPermissionChecker(), commerceProductDefinitionOptionRel,
				ActionKeys.UPDATE);

		return commerceProductDefinitionOptionRelLocalService.
			deleteCommerceProductDefinitionOptionRel(
				commerceProductDefinitionOptionRel);
	}

	@Override
	public CommerceProductDefinitionOptionRel
			deleteCommerceProductDefinitionOptionRel(
				long commerceProductDefinitionOptionRelId)
		throws PortalException {

		CommerceProductDefinitionPermission.
			checkCommerceProductDefinitionOptionRel(
				getPermissionChecker(), commerceProductDefinitionOptionRelId,
				ActionKeys.UPDATE);

		return commerceProductDefinitionOptionRelLocalService.
			deleteCommerceProductDefinitionOptionRel(
				commerceProductDefinitionOptionRelId);
	}

	@Override
	public CommerceProductDefinitionOptionRel
			getCommerceProductDefinitionOptionRel(
				long commerceProductDefinitionOptionRelId)
		throws PortalException {

		CommerceProductDefinitionPermission.
			checkCommerceProductDefinitionOptionRel(
				getPermissionChecker(), commerceProductDefinitionOptionRelId,
				ActionKeys.VIEW);

		return commerceProductDefinitionOptionRelLocalService.
			getCommerceProductDefinitionOptionRel(
				commerceProductDefinitionOptionRelId);
	}

	@Override
	public List<CommerceProductDefinitionOptionRel>
			getCommerceProductDefinitionOptionRels(
				long commerceProductDefinitionId, int start, int end)
		throws PortalException {

		CommerceProductDefinitionPermission.check(
			getPermissionChecker(), commerceProductDefinitionId,
			ActionKeys.VIEW);

		return commerceProductDefinitionOptionRelLocalService.
			getCommerceProductDefinitionOptionRels(
				commerceProductDefinitionId, start, end);
	}

	@Override
	public List<CommerceProductDefinitionOptionRel>
			getCommerceProductDefinitionOptionRels(
				long commerceProductDefinitionId, int start, int end,
				OrderByComparator<CommerceProductDefinitionOptionRel>
					orderByComparator)
		throws PortalException {

		CommerceProductDefinitionPermission.check(
			getPermissionChecker(), commerceProductDefinitionId,
			ActionKeys.VIEW);

		return commerceProductDefinitionOptionRelLocalService.
			getCommerceProductDefinitionOptionRels(
				commerceProductDefinitionId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceProductDefinitionOptionRelsCount(
			long commerceProductDefinitionId)
		throws PortalException {

		CommerceProductDefinitionPermission.check(
			getPermissionChecker(), commerceProductDefinitionId,
			ActionKeys.VIEW);

		return commerceProductDefinitionOptionRelLocalService.
			getCommerceProductDefinitionOptionRelsCount(
				commerceProductDefinitionId);
	}

	@Override
	public CommerceProductDefinitionOptionRel
			updateCommerceProductDefinitionOptionRel(
				long commerceProductDefinitionOptionRelId,
				long commerceProductOptionId, Map<Locale, String> nameMap,
				Map<Locale, String> descriptionMap, String ddmFormFieldTypeName,
				int priority, ServiceContext serviceContext)
		throws PortalException {

		CommerceProductDefinitionPermission.
			checkCommerceProductDefinitionOptionRel(
				getPermissionChecker(), commerceProductDefinitionOptionRelId,
				ActionKeys.UPDATE);

		return commerceProductDefinitionOptionRelLocalService.
			updateCommerceProductDefinitionOptionRel(
				commerceProductDefinitionOptionRelId, commerceProductOptionId,
				nameMap, descriptionMap, ddmFormFieldTypeName, priority,
				serviceContext);
	}

}