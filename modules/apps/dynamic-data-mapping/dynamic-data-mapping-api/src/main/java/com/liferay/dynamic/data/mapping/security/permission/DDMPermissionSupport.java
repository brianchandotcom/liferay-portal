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

package com.liferay.dynamic.data.mapping.security.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Rafael Praxedes
 */
public interface DDMPermissionSupport {

	public void checkAddStructurePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId)
		throws PortalException;

	public void checkAddTemplatePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId,
			long resourceClassNameId)
		throws PortalException;

	public void checkAddTemplatePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId,
			String resourceClassName)
		throws PortalException;

	public boolean containsAddStructurePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId)
		throws PortalException;

	public boolean containsAddTemplatePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId,
			long resourceClassNameId)
		throws PortalException;

	public boolean containsAddTemplatePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId,
			String resourceClassName)
		throws PortalException;

	public String getStructureModelResourceName(long classNameId)
		throws PortalException;

	public String getStructureModelResourceName(String className)
		throws PortalException;

	public String getTemplateModelResourceName(long resourceClassNameId)
		throws PortalException;

	public String getTemplateModelResourceName(String resourceClassName)
		throws PortalException;

}