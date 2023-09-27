/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.roles.admin.internal.exportimport.data.handler;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.PermissionConversionFilter;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;

/**
 * @author Michael C. Han
 */
public class ImportExportPermissionConversionFilter
	implements PermissionConversionFilter {

	@Override
	public boolean accept(Role role, ResourcePermission resourcePermission) {
		int scope = resourcePermission.getScope();

		if ((scope == ResourceConstants.SCOPE_COMPANY) ||
			(scope == ResourceConstants.SCOPE_GROUP_TEMPLATE)) {

			return true;
		}
		else if (resourcePermission.getScope() ==
					ResourceConstants.SCOPE_GROUP) {

			Group group = GroupLocalServiceUtil.fetchGroup(
				Long.valueOf(resourcePermission.getPrimKey()));

			if (group.isCompany() || group.isUserPersonalSite()) {
				return true;
			}
		}

		return false;
	}

}