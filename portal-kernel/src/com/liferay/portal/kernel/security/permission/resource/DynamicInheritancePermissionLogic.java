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

package com.liferay.portal.kernel.security.permission.resource;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class DynamicInheritancePermissionLogic
	<C extends GroupedModel, P extends GroupedModel>
		implements ModelResourcePermissionLogic<C> {

	public DynamicInheritancePermissionLogic(
		ModelResourcePermission<P> parentModelResourcePermission,
		UnsafeFunction<C, P, ? extends PortalException>
			fetchParentUnsafeFunction,
		boolean checkParentAccess) {

		_parentModelResourcePermission = Objects.requireNonNull(
			parentModelResourcePermission);
		_portletResourcePermission = Objects.requireNonNull(
			parentModelResourcePermission.getPortletResourcePermission());
		_fetchParentUnsafeFunction = Objects.requireNonNull(
			fetchParentUnsafeFunction);
		_checkParentAccess = checkParentAccess;
	}

	@Override
	public Boolean contains(
			PermissionChecker permissionChecker, String name, C child,
			String actionId)
		throws PortalException {

		if (!actionId.equals(ActionKeys.VIEW)) {
			return null;
		}

		P parent = _fetchParentUnsafeFunction.apply(child);

		if (parent == null) {
			if (_portletResourcePermission.contains(
					permissionChecker, child.getGroupId(), ActionKeys.VIEW)) {

				return null;
			}

			return false;
		}

		if (_checkParentAccess &&
			_parentModelResourcePermission.contains(
				permissionChecker, parent, ActionKeys.ACCESS)) {

			return null;
		}

		if (_parentModelResourcePermission.contains(
				permissionChecker, parent, ActionKeys.VIEW)) {

			return null;
		}

		return false;
	}

	private final boolean _checkParentAccess;
	private final UnsafeFunction<C, P, ? extends PortalException>
		_fetchParentUnsafeFunction;
	private final ModelResourcePermission<P> _parentModelResourcePermission;
	private final PortletResourcePermission _portletResourcePermission;

}