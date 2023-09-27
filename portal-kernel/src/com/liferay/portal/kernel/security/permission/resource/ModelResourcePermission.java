/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Preston Crary
 */
public interface ModelResourcePermission<T extends ClassedModel> {

	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, T model, String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, T model, String actionId)
		throws PortalException;

	public String getModelName();

	public PortletResourcePermission getPortletResourcePermission();

}