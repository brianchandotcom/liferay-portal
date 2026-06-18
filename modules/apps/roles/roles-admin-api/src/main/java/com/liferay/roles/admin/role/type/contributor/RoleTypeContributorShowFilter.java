/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.roles.admin.role.type.contributor;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Adolfo Pérez
 */
public interface RoleTypeContributorShowFilter {

	public boolean isShow(
			PermissionChecker permissionChecker,
			RoleTypeContributor roleTypeContributor)
		throws PortalException;

}