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

package com.liferay.portal.security.permission;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface PermissionCheckerBag extends UserPermissionCheckerBag {

	public long[] getRoleIds();

	/**
	 * @deprecated As of 6.1.0, renamed to {@link
	 *             #isGroupAdmin(PermissionChecker, Group)}
	 */
	@Deprecated
	public boolean isCommunityAdmin(
			PermissionChecker permissionChecker, Group group)
		throws Exception;

	/**
	 * @deprecated As of 6.1.0, renamed to {@link
	 *             #isGroupOwner(PermissionChecker, Group)}
	 */
	@Deprecated
	public boolean isCommunityOwner(
			PermissionChecker permissionChecker, Group group)
		throws Exception;

	public boolean isContentReviewer(
			PermissionChecker permissionChecker, Group group)
		throws Exception;

	public boolean isGroupAdmin(
			PermissionChecker permissionChecker, Group group)
		throws Exception;

	public boolean isGroupOwner(
			PermissionChecker permissionChecker, Group group)
		throws Exception;

	public boolean isOrganizationAdmin(
			PermissionChecker permissionChecker, Organization organization)
		throws Exception;

	public boolean isOrganizationOwner(
			PermissionChecker permissionChecker, Organization organization)
		throws Exception;

}