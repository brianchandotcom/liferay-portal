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