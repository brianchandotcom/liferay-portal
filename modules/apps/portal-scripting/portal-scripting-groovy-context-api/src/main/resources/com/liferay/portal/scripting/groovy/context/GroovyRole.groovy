/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scripting.groovy.context;

import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;

/**
 * @author Michael C. Han
 */
class GroovyRole {

	static GroovyRole organizationRole(String name, String description) {
		GroovyRole groovyRole = new GroovyRole();

		groovyRole.name = name;
		groovyRole.description = description;
		groovyRole.type = RoleConstants.TYPE_ORGANIZATION;

		return groovyRole;
	}

	static GroovyRole portalRole(String name, String description) {
		GroovyRole groovyRole = new GroovyRole();

		groovyRole.name = name;
		groovyRole.description = description;
		groovyRole.type = RoleConstants.TYPE_REGULAR;

		return groovyRole;
	}

	static GroovyRole siteRole(String name, String description) {
		GroovyRole groovyRole = new GroovyRole();

		groovyRole.name = name;
		groovyRole.description = description;
		groovyRole.type = RoleConstants.TYPE_SITE;

		return groovyRole;
	}

	void create(GroovyScriptingContext groovyScriptingContext) {
		role = RoleLocalServiceUtil.fetchRole(
			groovyScriptingContext.companyId, name);

		if (role != null) {
			return;
		}

		role = RoleLocalServiceUtil.addRole(
			groovyScriptingContext.guestUserId, null, 0, name,
			GroovyScriptingContext.getLocalizationMap(name),
			GroovyScriptingContext.getLocalizationMap(description), type, null,
			groovyScriptingContext.serviceContext);
	}

	void updatePermissions(
		String resourceName, String[] actionIds, boolean add,
		GroovyScriptingContext groovyScriptingContext) {

		int scope = ResourceConstants.SCOPE_COMPANY;

		if ((role.getType() == RoleConstants.TYPE_ORGANIZATION) ||
			(role.getType() == RoleConstants.TYPE_SITE)) {

			scope = ResourceConstants.SCOPE_GROUP_TEMPLATE;
		}

		for (String actionId : actionIds) {
			if (add) {
				if (scope == ResourceConstants.SCOPE_COMPANY) {
					ResourcePermissionLocalServiceUtil.
						addResourcePermission(
							groovyScriptingContext.companyId, resourceName,
							scope, String.valueOf(role.getCompanyId()),
							role.getRoleId(), actionId);
				}
				else if (scope == ResourceConstants.SCOPE_GROUP_TEMPLATE) {
					ResourcePermissionLocalServiceUtil.
						addResourcePermission(
							groovyScriptingContext.companyId, resourceName,
							scope,
							String.valueOf(
								GroupConstants.DEFAULT_PARENT_GROUP_ID),
							role.getRoleId(), actionId);
				}
			}
			else {
				ResourcePermissionLocalServiceUtil.
					removeResourcePermissions(
						groovyScriptingContext.companyId, resourceName, scope,
						role.getRoleId(), actionId);
			}
		}
	}

	String description;
	String name;
	Role role;
	int type;

}