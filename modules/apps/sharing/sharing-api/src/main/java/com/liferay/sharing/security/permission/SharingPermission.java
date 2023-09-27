/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.security.permission;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Collection;

/**
 * @author Adolfo Pérez
 */
public interface SharingPermission {

	public void check(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			long groupId, Collection<SharingEntryAction> sharingEntryActions)
		throws PortalException;

	public void checkManageCollaboratorsPermission(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			long groupId)
		throws PortalException;

	public default void checkSharePermission(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			long groupId)
		throws PortalException {

		throw new PrincipalException(
			StringBundler.concat(
				"User ", permissionChecker.getUserId(),
				" does not have permission to share ", classNameId,
				StringPool.SPACE, classPK));
	}

	public boolean contains(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			long groupId, Collection<SharingEntryAction> sharingEntryActions)
		throws PortalException;

	public boolean containsManageCollaboratorsPermission(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			long groupId)
		throws PortalException;

	public default boolean containsSharePermission(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			long groupId)
		throws PortalException {

		return false;
	}

}