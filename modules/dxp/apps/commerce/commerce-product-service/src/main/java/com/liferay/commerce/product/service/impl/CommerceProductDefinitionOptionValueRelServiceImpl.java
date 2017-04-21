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

import com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel;
import com.liferay.commerce.product.service.base.CommerceProductDefinitionOptionValueRelServiceBaseImpl;
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
public class CommerceProductDefinitionOptionValueRelServiceImpl
	extends CommerceProductDefinitionOptionValueRelServiceBaseImpl {

	public CommerceProductDefinitionOptionValueRel
			addCommerceProductDefinitionOptionValueRel(
				long commerceProductDefinitionOptionRelId,
				Map<Locale, String> titleMap, int priority,
				ServiceContext serviceContext)
		throws PortalException {

		CommerceProductDefinitionPermission.
			checkCommerceProductDefinitionOptionRel(
				getPermissionChecker(), commerceProductDefinitionOptionRelId,
				ActionKeys.UPDATE);

		return commerceProductDefinitionOptionValueRelLocalService.
			addCommerceProductDefinitionOptionValueRel(
				commerceProductDefinitionOptionRelId, titleMap, priority,
				serviceContext);
	}

	@Override
	public CommerceProductDefinitionOptionValueRel
			deleteCommerceProductDefinitionOptionValueRel(
				CommerceProductDefinitionOptionValueRel
					commerceProductDefinitionOptionValueRel)
		throws PortalException {

		CommerceProductDefinitionPermission.
			checkCommerceProductDefinitionOptionValueRel(
				getPermissionChecker(), commerceProductDefinitionOptionValueRel,
				ActionKeys.UPDATE);

		return commerceProductDefinitionOptionValueRelLocalService.
			deleteCommerceProductDefinitionOptionValueRel(
				commerceProductDefinitionOptionValueRel);
	}

	@Override
	public CommerceProductDefinitionOptionValueRel
			deleteCommerceProductDefinitionOptionValueRel(
				long commerceProductDefinitionOptionValueRelId)
		throws PortalException {

		CommerceProductDefinitionPermission.
			checkCommerceProductDefinitionOptionValueRel(
				getPermissionChecker(),
				commerceProductDefinitionOptionValueRelId, ActionKeys.UPDATE);

		return commerceProductDefinitionOptionValueRelLocalService.
			deleteCommerceProductDefinitionOptionValueRel(
				commerceProductDefinitionOptionValueRelId);
	}

	@Override
	public List<CommerceProductDefinitionOptionValueRel>
			getCommerceProductDefinitionOptionValueRels(
				long commerceProductDefinitionOptionRelId, int start, int end)
		throws PortalException {

		CommerceProductDefinitionPermission.
			checkCommerceProductDefinitionOptionRel(
				getPermissionChecker(), commerceProductDefinitionOptionRelId,
				ActionKeys.VIEW);

		return commerceProductDefinitionOptionValueRelLocalService.
			getCommerceProductDefinitionOptionValueRels(
				commerceProductDefinitionOptionRelId, start, end);
	}

	@Override
	public List<CommerceProductDefinitionOptionValueRel>
			getCommerceProductDefinitionOptionValueRels(
				long commerceProductDefinitionOptionRelId, int start, int end,
				OrderByComparator<CommerceProductDefinitionOptionValueRel>
					orderByComparator)
		throws PortalException {

		CommerceProductDefinitionPermission.
			checkCommerceProductDefinitionOptionRel(
				getPermissionChecker(), commerceProductDefinitionOptionRelId,
				ActionKeys.VIEW);

		return commerceProductDefinitionOptionValueRelLocalService.
			getCommerceProductDefinitionOptionValueRels(
				commerceProductDefinitionOptionRelId, start, end,
				orderByComparator);
	}

	@Override
	public int getCommerceProductDefinitionOptionValueRelsCount(
			long commerceProductDefinitionOptionRelId)
		throws PortalException {

		CommerceProductDefinitionPermission.
			checkCommerceProductDefinitionOptionRel(
				getPermissionChecker(), commerceProductDefinitionOptionRelId,
				ActionKeys.VIEW);

		return commerceProductDefinitionOptionValueRelLocalService.
			getCommerceProductDefinitionOptionValueRelsCount(
				commerceProductDefinitionOptionRelId);
	}

	public CommerceProductDefinitionOptionValueRel
			updateCommerceProductDefinitionOptionValueRel(
				long commerceProductDefinitionOptionValueRelId,
				Map<Locale, String> titleMap, int priority,
				ServiceContext serviceContext)
		throws PortalException {

		CommerceProductDefinitionPermission.
			checkCommerceProductDefinitionOptionValueRel(
				getPermissionChecker(),
				commerceProductDefinitionOptionValueRelId, ActionKeys.UPDATE);

		return commerceProductDefinitionOptionValueRelLocalService.
			updateCommerceProductDefinitionOptionValueRel(
				commerceProductDefinitionOptionValueRelId, titleMap, priority,
				serviceContext);
	}

}