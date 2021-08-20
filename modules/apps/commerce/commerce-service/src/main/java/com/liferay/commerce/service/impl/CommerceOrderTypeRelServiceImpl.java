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
import com.liferay.commerce.model.CommerceOrderTypeRel;
import com.liferay.commerce.service.base.CommerceOrderTypeRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderTypeRelServiceImpl
	extends CommerceOrderTypeRelServiceBaseImpl {

	@Override
	public CommerceOrderTypeRel addCommerceOrderTypeRel(
			String className, long classPK, long commerceOrderTypeId,
			ServiceContext serviceContext)
		throws PortalException {

		_commerceOrderTypeModelResourcePermission.check(
			getPermissionChecker(), commerceOrderTypeId, ActionKeys.UPDATE);

		return commerceOrderTypeRelLocalService.addCommerceOrderTypeRel(
			getUserId(), className, classPK, commerceOrderTypeId,
			serviceContext);
	}

	@Override
	public CommerceOrderTypeRel deleteCommerceOrderTypeRel(
			long commerceOrderTypeRelId)
		throws PortalException {

		CommerceOrderTypeRel commerceOrderTypeRel =
			commerceOrderTypeRelLocalService.getCommerceOrderTypeRel(
				commerceOrderTypeRelId);

		_commerceOrderTypeModelResourcePermission.check(
			getPermissionChecker(),
			commerceOrderTypeRel.getCommerceOrderTypeId(), ActionKeys.UPDATE);

		return commerceOrderTypeRelLocalService.deleteCommerceOrderTypeRel(
			commerceOrderTypeRelId);
	}

	@Override
	public void deleteCommerceOrderTypeRels(
			String className, long commerceOrderTypeId)
		throws PortalException {

		_commerceOrderTypeModelResourcePermission.check(
			getPermissionChecker(), commerceOrderTypeId, ActionKeys.UPDATE);

		commerceOrderTypeRelLocalService.deleteCommerceOrderTypeRels(
			className, commerceOrderTypeId);
	}

	@Override
	public List<CommerceOrderTypeRel> getCommerceOrderTypeCommerceChannelRels(
			long commerceOrderTypeId, String keywords, int start, int end)
		throws PortalException {

		_commerceOrderTypeModelResourcePermission.check(
			getPermissionChecker(), commerceOrderTypeId, ActionKeys.VIEW);

		return commerceOrderTypeRelLocalService.
			getCommerceOrderTypeCommerceChannelRels(
				commerceOrderTypeId, keywords, start, end);
	}

	@Override
	public int getCommerceOrderTypeCommerceChannelRelsCount(
			long commerceOrderTypeId, String keywords)
		throws PortalException {

		_commerceOrderTypeModelResourcePermission.check(
			getPermissionChecker(), commerceOrderTypeId, ActionKeys.VIEW);

		return commerceOrderTypeRelLocalService.
			getCommerceOrderTypeCommerceChannelRelsCount(
				commerceOrderTypeId, keywords);
	}

	@Override
	public CommerceOrderTypeRel getCommerceOrderTypeRel(
			long commerceOrderTypeRelId)
		throws PortalException {

		CommerceOrderTypeRel commerceOrderTypeRel =
			commerceOrderTypeRelLocalService.getCommerceOrderTypeRel(
				commerceOrderTypeRelId);

		_commerceOrderTypeModelResourcePermission.check(
			getPermissionChecker(),
			commerceOrderTypeRel.getCommerceOrderTypeId(), ActionKeys.VIEW);

		return commerceOrderTypeRelLocalService.getCommerceOrderTypeRel(
			commerceOrderTypeRelId);
	}

	@Override
	public List<CommerceOrderTypeRel> getCommerceOrderTypeRels(
			String className, long classPK, int start, int end,
			OrderByComparator<CommerceOrderTypeRel> orderByComparator)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceOrderTypeModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceOrderActionKeys.VIEW_COMMERCE_ORDER_TYPES);

		return commerceOrderTypeRelLocalService.getCommerceOrderTypeRels(
			className, classPK, start, end, orderByComparator);
	}

	@Override
	public int getCommerceOrderTypeRelsCount(String className, long classPK)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceOrderTypeModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceOrderActionKeys.VIEW_COMMERCE_ORDER_TYPES);

		return commerceOrderTypeRelLocalService.getCommerceOrderTypeRelsCount(
			className, classPK);
	}

	private static volatile ModelResourcePermission<CommerceOrderType>
		_commerceOrderTypeModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceOrderTypeRelServiceImpl.class,
				"_commerceOrderTypeModelResourcePermission",
				CommerceOrderType.class);

}