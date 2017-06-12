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

package com.liferay.roles.admin.demo.data.creator.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.roles.admin.demo.data.creator.RoleDemoDataCreator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(property = {"role.type=site"}, service = RoleDemoDataCreator.class)
public class SiteRoleDemoDataCreatorImpl
	extends BaseRoleDemoDataCreator implements RoleDemoDataCreator {

	@Override
	public Role create(long companyId, String permissionsXML)
		throws PortalException {

		return create(companyId, StringUtil.randomString(), permissionsXML);
	}

	@Override
	public Role create(long companyId, String roleName, String permissionsXML)
		throws PortalException {

		Role role = createRole(companyId, roleName, RoleConstants.TYPE_SITE);

		if (Validator.isNotNull(permissionsXML)) {
			addPermissions(
				role, permissionsXML, ResourceConstants.SCOPE_GROUP_TEMPLATE,
				String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID));
		}

		return roleLocalService.getRole(role.getRoleId());
	}

}