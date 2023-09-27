/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		_fetchParentUnsafeFunction = Objects.requireNonNull(
			fetchParentUnsafeFunction);
		_checkParentAccess = checkParentAccess;

		_portletResourcePermission = Objects.requireNonNull(
			parentModelResourcePermission.getPortletResourcePermission());
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

		if ((_checkParentAccess &&
			 _parentModelResourcePermission.contains(
				 permissionChecker, parent, ActionKeys.ACCESS)) ||
			_parentModelResourcePermission.contains(
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