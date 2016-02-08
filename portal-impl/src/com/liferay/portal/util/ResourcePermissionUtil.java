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

package com.liferay.portal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Resource;
import com.liferay.portal.kernel.model.ResourceBlock;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;

import java.util.List;

/**
 * @author Juan Fernández
 * @author Sergio González
 */
public class ResourcePermissionUtil {

	public static void populateResourcePermissionActionIds(
			long groupId, Role role, Resource resource, List<String> actions,
			List<String> individualActions, List<String> groupActions,
			List<String> groupTemplateActions, List<String> companyActions)
		throws PortalException {

		if (ResourceBlockLocalServiceUtil.isSupported(resource.getName())) {
			ResourceBlock resourceBlock =
				ResourceBlockLocalServiceUtil.getResourceBlock(
					resource.getName(), Long.valueOf(resource.getPrimKey()));

			// Individual actions are not stored separately, so
			// individualActions will include group and company actions as well

			individualActions.addAll(
				ResourceBlockLocalServiceUtil.getPermissions(
					resourceBlock, role.getRoleId()));
			groupActions.addAll(
				ResourceBlockLocalServiceUtil.getGroupScopePermissions(
					resourceBlock, role.getRoleId()));

			// Resource blocks do not distinguish between company scope and
			// group template scope permissions, so the distinction must be
			// simulated here

			if (role.getType() == RoleConstants.TYPE_REGULAR) {
				companyActions.addAll(
					ResourceBlockLocalServiceUtil.getCompanyScopePermissions(
						resourceBlock, role.getRoleId()));
			}
			else {
				groupTemplateActions.addAll(
					ResourceBlockLocalServiceUtil.getCompanyScopePermissions(
						resourceBlock, role.getRoleId()));
			}
		}
		else {
			individualActions.addAll(
				ResourcePermissionLocalServiceUtil.
					getAvailableResourcePermissionActionIds(
						resource.getCompanyId(), resource.getName(),
						resource.getScope(), resource.getPrimKey(),
						role.getRoleId(), actions));

			groupActions.addAll(
				ResourcePermissionLocalServiceUtil.
					getAvailableResourcePermissionActionIds(
						resource.getCompanyId(), resource.getName(),
						ResourceConstants.SCOPE_GROUP, String.valueOf(groupId),
						role.getRoleId(), actions));

			groupTemplateActions.addAll(
				ResourcePermissionLocalServiceUtil.
					getAvailableResourcePermissionActionIds(
						resource.getCompanyId(), resource.getName(),
						ResourceConstants.SCOPE_GROUP_TEMPLATE, "0",
						role.getRoleId(), actions));

			companyActions.addAll(
				ResourcePermissionLocalServiceUtil.
					getAvailableResourcePermissionActionIds(
						resource.getCompanyId(), resource.getName(),
						ResourceConstants.SCOPE_COMPANY,
						String.valueOf(resource.getCompanyId()),
						role.getRoleId(), actions));
		}
	}

}