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

import com.liferay.commerce.account.constants.CommerceAccountActionKeys;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.service.base.CommerceAccountGroupServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountGroupServiceImpl
	extends CommerceAccountGroupServiceBaseImpl {

	@Override
	public CommerceAccountGroup addCommerceAccountGroup(
			long companyId, String name, int type, String externalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceAccountGroupModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceAccountActionKeys.ADD_ACCOUNT_GROUP);

		return commerceAccountGroupLocalService.addCommerceAccountGroup(
			companyId, name, type, false, externalReferenceCode,
			serviceContext);
	}

	@Override
	public void deleteCommerceAccountGroup(long commerceAccountGroupId)
		throws PortalException {

		_commerceAccountGroupModelResourcePermission.check(
			getPermissionChecker(), commerceAccountGroupId, ActionKeys.DELETE);

		commerceAccountGroupLocalService.deleteCommerceAccountGroup(
			commerceAccountGroupId);
	}

	@Override
	public CommerceAccountGroup fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		CommerceAccountGroup commerceAccountGroup =
			commerceAccountGroupLocalService.fetchByExternalReferenceCode(
				companyId, externalReferenceCode);

		if (commerceAccountGroup != null) {
			_commerceAccountGroupModelResourcePermission.check(
				getPermissionChecker(), commerceAccountGroup, ActionKeys.VIEW);
		}

		return commerceAccountGroup;
	}

	@Override
	public CommerceAccountGroup getCommerceAccountGroup(
			long commerceAccountGroupId)
		throws PortalException {

		_commerceAccountGroupModelResourcePermission.check(
			getPermissionChecker(), commerceAccountGroupId, ActionKeys.VIEW);

		return commerceAccountGroupLocalService.getCommerceAccountGroup(
			commerceAccountGroupId);
	}

	@Override
	public List<CommerceAccountGroup> getCommerceAccountGroups(
			long companyId, int start, int end,
			OrderByComparator<CommerceAccountGroup> orderByComparator)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceAccountGroupModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceAccountActionKeys.VIEW_COMMERCE_ACCOUNT_GROUPS);

		return commerceAccountGroupLocalService.getCommerceAccountGroups(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceAccountGroupsCount(long companyId)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceAccountGroupModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceAccountActionKeys.VIEW_COMMERCE_ACCOUNT_GROUPS);

		return commerceAccountGroupLocalService.getCommerceAccountGroupsCount(
			companyId);
	}

	@Override
	public List<CommerceAccountGroup> searchCommerceAccountGroups(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceAccountGroupModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceAccountActionKeys.VIEW_COMMERCE_ACCOUNT_GROUPS);

		return commerceAccountGroupLocalService.searchCommerceAccountGroups(
			companyId, keywords, start, end, sort);
	}

	@Override
	public int searchCommerceAccountsGroupCount(long companyId, String keywords)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceAccountGroupModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceAccountActionKeys.VIEW_COMMERCE_ACCOUNT_GROUPS);

		return commerceAccountGroupLocalService.
			searchCommerceAccountsGroupCount(companyId, keywords);
	}

	@Override
	public CommerceAccountGroup updateCommerceAccountGroup(
			long commerceAccountGroupId, String name,
			ServiceContext serviceContext)
		throws PortalException {

		_commerceAccountGroupModelResourcePermission.check(
			getPermissionChecker(), commerceAccountGroupId, ActionKeys.UPDATE);

		return commerceAccountGroupLocalService.updateCommerceAccountGroup(
			commerceAccountGroupId, name, serviceContext);
	}

	private static volatile ModelResourcePermission<CommerceAccountGroup>
		_commerceAccountGroupModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceAccountGroupServiceImpl.class,
				"_commerceAccountGroupModelResourcePermission",
				CommerceAccountGroup.class);

}