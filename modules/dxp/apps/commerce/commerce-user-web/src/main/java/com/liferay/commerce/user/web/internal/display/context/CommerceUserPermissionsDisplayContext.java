/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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