/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.content.type;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Leonardo Barros
 */
public interface DataDefinitionContentType {

	public default boolean allowEmptyDataDefinition() {
		return false;
	}

	public default boolean allowInvalidAvailableLocalesForProperty() {
		return false;
	}

	public default boolean allowReferencedDataDefinitionDeletion() {
		return false;
	}

	public long getClassNameId();

	public default String getContentType() {
		return "default";
	}

	public default String getPortletResourceName() {
		return "com.liferay.data.engine";
	}

	public default boolean hasPermission(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String resourceName, long primKey, long userId, String actionId)
		throws PortalException {

		if (permissionChecker.hasOwnerPermission(
				companyId, resourceName, primKey, userId, actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			groupId, resourceName, primKey, actionId);
	}

	public default boolean hasPortletPermission(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		return permissionChecker.hasPermission(
			groupId, getPortletResourceName(), groupId, actionId);
	}

	public default boolean isDataRecordCollectionPermissionCheckingEnabled() {
		return false;
	}

}