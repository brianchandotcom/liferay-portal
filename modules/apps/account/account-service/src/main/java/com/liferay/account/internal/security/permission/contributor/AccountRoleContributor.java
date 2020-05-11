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

package com.liferay.account.internal.security.permission.contributor;

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.contributor.RoleCollection;
import com.liferay.portal.kernel.security.permission.contributor.RoleContributor;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = RoleContributor.class)
public class AccountRoleContributor implements RoleContributor {

	@Override
	public void contribute(RoleCollection roleCollection) {
		try {
			if (roleCollection.getGroupId() <= 0) {
				return;
			}

			Group group = _groupLocalService.getGroup(
				roleCollection.getGroupId());

			if (!Objects.equals(
					AccountEntry.class.getName(), group.getClassName())) {

				return;
			}

			User user = roleCollection.getUser();

			if (_accountEntryUserRelLocalService.hasAccountEntryUserRel(
					group.getClassPK(), user.getUserId())) {

				_addRoleId(
					roleCollection,
					AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MEMBER);
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	private void _addRoleId(RoleCollection roleCollection, String roleName)
		throws PortalException {

		Role role = _roleLocalService.getRole(
			roleCollection.getCompanyId(), roleName);

		roleCollection.addRoleId(role.getRoleId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountRoleContributor.class);

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}