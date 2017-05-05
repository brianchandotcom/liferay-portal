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
import com.liferay.commerce.product.model.CPMediaType;
import com.liferay.commerce.product.service.base.CPMediaTypeServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CPMediaTypePermission;
import com.liferay.commerce.product.service.permission.CPPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPMediaTypeServiceImpl extends CPMediaTypeServiceBaseImpl {

	@Override
	public CPMediaType addCPMediaType(
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			int priority, ServiceContext serviceContext)
		throws PortalException {

		CPPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CPActionKeys.ADD_COMMERCE_PRODUCT_MEDIA_TYPE);

		return cpMediaTypeLocalService.addCPMediaType(
			titleMap, descriptionMap, priority, serviceContext);
	}

	@Override
	public CPMediaType deleteCPMediaType(CPMediaType cpMediaType)
		throws PortalException {

		CPMediaTypePermission.check(
			getPermissionChecker(), cpMediaType, ActionKeys.DELETE);

		return cpMediaTypeLocalService.deleteCPMediaType(cpMediaType);
	}

	@Override
	public CPMediaType deleteCPMediaType(long cpMediaTypeId)
		throws PortalException {

		CPMediaTypePermission.check(
			getPermissionChecker(), cpMediaTypeId, ActionKeys.DELETE);

		return cpMediaTypeLocalService.deleteCPMediaType(cpMediaTypeId);
	}

	@Override
	public CPMediaType fetchCPMediaType(long cpMediaTypeId)
		throws PortalException {

		CPMediaType cpMediaType = cpMediaTypeLocalService.fetchCPMediaType(
			cpMediaTypeId);

		if (cpMediaType != null) {
			CPMediaTypePermission.check(
				getPermissionChecker(), cpMediaType, ActionKeys.VIEW);
		}

		return cpMediaType;
	}

	@Override
	public CPMediaType getCPMediaType(long cpMediaTypeId)
		throws PortalException {

		CPMediaTypePermission.check(
			getPermissionChecker(), cpMediaTypeId, ActionKeys.VIEW);

		return cpMediaTypeLocalService.getCPMediaType(cpMediaTypeId);
	}

	@Override
	public List<CPMediaType> getCPMediaTypes(long groupId, int start, int end) {
		return cpMediaTypePersistence.filterFindByGroupId(groupId, start, end);
	}

	@Override
	public List<CPMediaType> getCPMediaTypes(
		long groupId, int start, int end,
		OrderByComparator<CPMediaType> orderByComparator) {

		return cpMediaTypePersistence.filterFindByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCPMediaTypesCount(long groupId) {
		return cpMediaTypePersistence.filterCountByGroupId(groupId);
	}

	@Override
	public CPMediaType updateCPMediaType(
			long cpMediaTypeId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, int priority,
			ServiceContext serviceContext)
		throws PortalException {

		CPMediaTypePermission.check(
			getPermissionChecker(), cpMediaTypeId, ActionKeys.UPDATE);

		return cpMediaTypeLocalService.updateCPMediaType(
			cpMediaTypeId, titleMap, descriptionMap, priority, serviceContext);
	}

}