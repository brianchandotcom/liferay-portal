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

package com.liferay.commerce.account.service.impl;

import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.model.CommerceAccountGroupRel;
import com.liferay.commerce.account.service.base.CommerceAccountGroupRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountGroupRelServiceImpl
	extends CommerceAccountGroupRelServiceBaseImpl {

	@Override
	public CommerceAccountGroupRel addCommerceAccountGroupRel(
			String className, long classPK, long commerceAccountGroupId,
			ServiceContext serviceContext)
		throws PortalException {

		_commerceAccountGroupModelResourcePermission.check(
			getPermissionChecker(), commerceAccountGroupId, ActionKeys.UPDATE);

		return commerceAccountGroupRelLocalService.addCommerceAccountGroupRel(
			className, classPK, commerceAccountGroupId, serviceContext);
	}

	@Override
	public void deleteCommerceAccountGroupRel(long commerceAccountGroupRelId)
		throws PortalException {

		commerceAccountGroupRelLocalService.deleteCommerceAccountGroupRel(
			commerceAccountGroupRelId);
	}

	@Override
	public void deleteCommerceAccountGroupRels(String className, long classPK) {
		commerceAccountGroupRelLocalService.deleteCommerceAccountGroupRels(
			className, classPK);
	}

	@Override
	public CommerceAccountGroupRel getCommerceAccountGroupRel(
			long commerceAccountGroupRelId)
		throws PortalException {

		return commerceAccountGroupRelLocalService.getCommerceAccountGroupRel(
			commerceAccountGroupRelId);
	}

	@Override
	public List<CommerceAccountGroupRel> getCommerceAccountGroupRels(
			long commerceAccountGroupId, int start, int end,
			OrderByComparator<CommerceAccountGroupRel> orderByComparator)
		throws PortalException {

		_commerceAccountGroupModelResourcePermission.check(
			getPermissionChecker(), commerceAccountGroupId, ActionKeys.VIEW);

		return commerceAccountGroupRelLocalService.getCommerceAccountGroupRels(
			commerceAccountGroupId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceAccountGroupRel> getCommerceAccountGroupRels(
			String className, long classPK, int start, int end,
			OrderByComparator<CommerceAccountGroupRel> orderByComparator)
		throws PortalException {

		return commerceAccountGroupRelLocalService.getCommerceAccountGroupRels(
			className, classPK, start, end, orderByComparator);
	}

	@Override
	public int getCommerceAccountGroupRelsCount(long commerceAccountGroupId)
		throws PortalException {

		_commerceAccountGroupModelResourcePermission.check(
			getPermissionChecker(), commerceAccountGroupId, ActionKeys.VIEW);

		return commerceAccountGroupRelLocalService.
			getCommerceAccountGroupRelsCount(commerceAccountGroupId);
	}

	@Override
	public int getCommerceAccountGroupRelsCount(String className, long classPK)
		throws PortalException {

		return commerceAccountGroupRelLocalService.
			getCommerceAccountGroupRelsCount(className, classPK);
	}

	private static volatile ModelResourcePermission<CommerceAccountGroup>
		_commerceAccountGroupModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceAccountGroupRelServiceImpl.class,
				"_commerceAccountGroupModelResourcePermission",
				CommerceAccountGroup.class);

}