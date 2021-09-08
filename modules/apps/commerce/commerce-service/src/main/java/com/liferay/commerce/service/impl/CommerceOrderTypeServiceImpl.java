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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.service.base.CommerceOrderTypeServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderTypeServiceImpl
	extends CommerceOrderTypeServiceBaseImpl {

	@Override
	public CommerceOrderType addCommerceOrderType(
			String externalReferenceCode, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, boolean active,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int displayOrder,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceOrderTypeModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceOrderActionKeys.ADD_COMMERCE_ORDER_TYPE);

		return commerceOrderTypeLocalService.addCommerceOrderType(
			externalReferenceCode, getUserId(), nameMap, descriptionMap, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, displayOrder, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public CommerceOrderType deleteCommerceOrderType(long commerceOrderTypeId)
		throws PortalException {

		_commerceOrderTypeModelResourcePermission.check(
			getPermissionChecker(), commerceOrderTypeId, ActionKeys.DELETE);

		return commerceOrderTypeLocalService.deleteCommerceOrderType(
			commerceOrderTypeId);
	}

	@Override
	public CommerceOrderType fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		CommerceOrderType commerceOrderType =
			commerceOrderTypeLocalService.fetchByExternalReferenceCode(
				externalReferenceCode, companyId);

		if (commerceOrderType != null) {
			_commerceOrderTypeModelResourcePermission.check(
				getPermissionChecker(), commerceOrderType, ActionKeys.VIEW);
		}

		return commerceOrderType;
	}

	@Override
	public CommerceOrderType fetchCommerceOrderType(long commerceOrderTypeId)
		throws PortalException {

		CommerceOrderType commerceOrderType =
			commerceOrderTypeLocalService.fetchCommerceOrderType(
				commerceOrderTypeId);

		if (commerceOrderType != null) {
			_commerceOrderTypeModelResourcePermission.check(
				getPermissionChecker(), commerceOrderTypeId, ActionKeys.VIEW);
		}

		return commerceOrderType;
	}

	@Override
	public CommerceOrderType getCommerceOrderType(long commerceOrderTypeId)
		throws PortalException {

		_commerceOrderTypeModelResourcePermission.check(
			getPermissionChecker(), commerceOrderTypeId, ActionKeys.VIEW);

		return commerceOrderTypeLocalService.getCommerceOrderType(
			commerceOrderTypeId);
	}

	@Override
	public List<CommerceOrderType> getCommerceOrderTypes(
			String className, long classPK, boolean active, int start, int end)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		return commerceOrderTypeLocalService.getCommerceOrderTypes(
			permissionChecker.getCompanyId(), className, classPK, active, start,
			end);
	}

	@Override
	public int getCommerceOrderTypesCount(
			String className, long classPK, boolean active)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		return commerceOrderTypeLocalService.getCommerceOrderTypesCount(
			permissionChecker.getCompanyId(), className, classPK, active);
	}

	@Override
	public CommerceOrderType updateCommerceOrderType(
			String externalReferenceCode, long commerceOrderTypeId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			boolean active, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int displayOrder, int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		_commerceOrderTypeModelResourcePermission.check(
			getPermissionChecker(), commerceOrderTypeId, ActionKeys.UPDATE);

		return commerceOrderTypeLocalService.updateCommerceOrderType(
			externalReferenceCode, getUserId(), commerceOrderTypeId, nameMap,
			descriptionMap, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute, displayOrder,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public CommerceOrderType updateCommerceOrderTypeExternalReferenceCode(
			String externalReferenceCode, long commerceOrderTypeId)
		throws PortalException {

		_commerceOrderTypeModelResourcePermission.check(
			getPermissionChecker(), commerceOrderTypeId, ActionKeys.UPDATE);

		return commerceOrderTypeLocalService.
			updateCommerceOrderTypeExternalReferenceCode(
				externalReferenceCode, commerceOrderTypeId);
	}

	private static volatile ModelResourcePermission<CommerceOrderType>
		_commerceOrderTypeModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceOrderTypeServiceImpl.class,
				"_commerceOrderTypeModelResourcePermission",
				CommerceOrderType.class);

}