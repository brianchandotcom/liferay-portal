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

package com.liferay.commerce.user.web.internal.display.context;

import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.user.service.CommerceUserService;
import com.liferay.commerce.user.util.CommerceRoleRegistry;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceUserPermissionsDisplayContext
	extends BaseCommerceUserDisplayContext {

	public CommerceUserPermissionsDisplayContext(
		CommerceOrganizationHelper commerceOrganizationHelper,
		CommerceRoleRegistry commerceRoleRegistry,
		CommerceUserService commerceUserService,
		HttpServletRequest httpServletRequest, Portal portal,
		UserGroupRoleLocalService userGroupRoleLocalService) {

		super(commerceUserService, httpServletRequest, portal);

		_commerceOrganizationHelper = commerceOrganizationHelper;
		_commerceRoleRegistry = commerceRoleRegistry;
		_userGroupRoleLocalService = userGroupRoleLocalService;
	}

	public List<Role> getRoles() {
		List<Role> roles = _commerceRoleRegistry.getRoles(
			commerceUserRequestHelper.getCompanyId());

		return roles;
	}

	public boolean hasUserGroupRole(long userId, long roleId) throws Exception {
		Organization organization =
			_commerceOrganizationHelper.getCurrentOrganization(
				commerceUserRequestHelper.getRequest());

		if (organization == null) {
			return false;
		}

		return _userGroupRoleLocalService.hasUserGroupRole(
			userId, organization.getGroupId(), roleId);
	}

	private final CommerceOrganizationHelper _commerceOrganizationHelper;
	private final CommerceRoleRegistry _commerceRoleRegistry;
	private final UserGroupRoleLocalService _userGroupRoleLocalService;

}