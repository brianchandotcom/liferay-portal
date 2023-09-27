/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.permission;

import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;

/**
 * @author Michael C. Han
 */
public interface PermissionConversionFilter {

	public boolean accept(Role role, ResourcePermission resourcePermission);

}