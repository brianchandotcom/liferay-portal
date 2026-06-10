/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.standalone.site.initializer.internal.role.type.contributor;

import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.roles.admin.role.type.contributor.RoleTypeContributor;
import com.liferay.roles.admin.role.type.contributor.RoleTypeContributorShowFilter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(service = RoleTypeContributorShowFilter.class)
public class CMSRoleTypeContributorShowFilter
	implements RoleTypeContributorShowFilter {

	@Override
	public boolean isShow(
		RoleTypeContributor roleTypeContributor,
		PermissionChecker permissionChecker) {

		if ((roleTypeContributor.getType() == RoleConstants.TYPE_ACCOUNT) ||
			(roleTypeContributor.getType() == RoleConstants.TYPE_SITE)) {

			return false;
		}

		return true;
	}

}