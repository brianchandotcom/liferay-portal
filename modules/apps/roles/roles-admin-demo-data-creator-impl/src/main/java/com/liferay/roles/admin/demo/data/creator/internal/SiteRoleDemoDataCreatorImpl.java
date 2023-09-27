/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.roles.admin.demo.data.creator.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.roles.admin.demo.data.creator.RoleDemoDataCreator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(property = "role.type=site", service = RoleDemoDataCreator.class)
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

		return role;
	}

}