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

package com.liferay.users.admin.demo.data.creator.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.users.admin.demo.data.creator.CompanyAdminUserDemoDataCreator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = CompanyAdminUserDemoDataCreator.class)
public class CompanyAdminUserDemoDataCreatorImpl
	extends BaseUserDemoDataCreator
	implements CompanyAdminUserDemoDataCreator {

	@Override
	public User create(long companyId) throws PortalException {
		return create(companyId, null);
	}

	@Override
	public User create(long companyId, String emailAddress)
		throws PortalException {

		User user = createUser(companyId, emailAddress);

		Role role = _roleLocalService.getRole(
			companyId, RoleConstants.ADMINISTRATOR);

		userLocalService.addRoleUser(role.getRoleId(), user);

		return userLocalService.getUser(user.getUserId());
	}

	@Reference
	private RoleLocalService _roleLocalService;

}