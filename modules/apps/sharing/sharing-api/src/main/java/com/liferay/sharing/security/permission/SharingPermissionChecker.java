/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.security.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Collection;

/**
 * Defines whether the user has permission to share a resource with another user
 * via {@link SharingEntryAction}s.
 *
 * <p>
 * Implementations of this interface typically map the sharing entry actions
 * with the resource actions, and delegate the permission check to the
 * corresponding {@code
 * com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission}.
 * </p>
 *
 * <p>
 * Implementations of this interface must be registered as OSGi components using
 * the service {@code SharingPermissionChecker}. The {@code model.class.name}
 * property defines the class name that the permission checker can handle.
 * </p>
 *
 * @author Sergio González
 */
public interface SharingPermissionChecker {

	/**
	 * Returns {@code true} if the user has permission to share the resource
	 * with another user via sharing entry actions.
	 *
	 * @param  permissionChecker the permission checker of the user sharing the
	 *         resource
	 * @param  classPK the class primary key of the shared resource
	 * @param  groupId the primary key of the shared resource's group
	 * @param  sharingEntryActions the collection of sharing entry actions to
	 *         check for permission
	 * @return {@code true} if the user has permission to share the resource via
	 *         sharing entry actions; {@code false} otherwise
	 */
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, long groupId,
			Collection<SharingEntryAction> sharingEntryActions)
		throws PortalException;

}