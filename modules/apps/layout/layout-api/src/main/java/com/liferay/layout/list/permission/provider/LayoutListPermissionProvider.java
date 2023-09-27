/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.list.permission.provider;

import com.liferay.layout.list.retriever.ListObjectReference;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Lourdes Fernández Besada
 */
public interface LayoutListPermissionProvider<T extends ListObjectReference> {

	public boolean hasPermission(
		PermissionChecker permissionChecker, T t, String actionId);

}