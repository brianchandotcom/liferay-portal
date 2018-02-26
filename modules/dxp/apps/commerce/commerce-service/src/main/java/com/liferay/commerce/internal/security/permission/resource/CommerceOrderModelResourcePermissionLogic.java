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

package com.liferay.commerce.internal.security.permission.resource;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderModelResourcePermissionLogic
	implements ModelResourcePermissionLogic<CommerceOrder> {

	public CommerceOrderModelResourcePermissionLogic(
		GroupLocalService groupLocalService,
		PortletResourcePermission portletResourcePermission) {

		_groupLocalService = groupLocalService;
		_portletResourcePermission = portletResourcePermission;
	}

	@Override
	public Boolean contains(
			PermissionChecker permissionChecker, String name,
			CommerceOrder commerceOrder, String actionId)
		throws PortalException {

		if (actionId.equals(CommerceActionKeys.CHECKOUT)) {
			return _containsCheckoutPermission(
				permissionChecker, commerceOrder);
		}

		if (actionId.equals(ActionKeys.DELETE)) {
			return _containsDeletePermission(permissionChecker, commerceOrder);
		}
		else if (actionId.equals(ActionKeys.UPDATE)) {
			return _containsUpdatePermission(permissionChecker, commerceOrder);
		}
		else if (actionId.equals(ActionKeys.VIEW)) {
			return _containsViewPermission(permissionChecker, commerceOrder);
		}

		return false;
	}

	private boolean _containsCheckoutPermission(
		PermissionChecker permissionChecker, CommerceOrder commerceOrder) {

		return _portletResourcePermission.contains(
			permissionChecker, commerceOrder.getGroupId(),
			CommerceActionKeys.CHECKOUT_OPEN_ORDERS);
	}

	private boolean _containsDeletePermission(
		PermissionChecker permissionChecker, CommerceOrder commerceOrder) {

		if (commerceOrder.isOpen() &&
			_hasOwnerPermission(permissionChecker, commerceOrder)) {

			return true;
		}

		return _portletResourcePermission.contains(
			permissionChecker, commerceOrder.getSiteGroupId(),
			CommerceActionKeys.DELETE_COMMERCE_ORDERS);
	}

	private boolean _containsUpdatePermission(
			PermissionChecker permissionChecker, CommerceOrder commerceOrder)
		throws PortalException {

		if (commerceOrder.isOpen()) {
			if (commerceOrder.isDraft()) {
				return _hasOwnerPermission(permissionChecker, commerceOrder);
			}

			return _hasPermission(
				permissionChecker, commerceOrder.getGroupId(),
				CommerceActionKeys.APPROVE_OPEN_COMMERCE_ORDERS,
				CommerceActionKeys.VIEW_OPEN_COMMERCE_ORDERS);
		}

		if (_hasPermission(
				permissionChecker, commerceOrder.getSiteGroupId(),
				CommerceActionKeys.MANAGE_COMMERCE_ORDERS)) {

			return true;
		}

		return _hasAncestorPermission(
			permissionChecker, commerceOrder.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_ORDERS);
	}

	private boolean _containsViewPermission(
			PermissionChecker permissionChecker, CommerceOrder commerceOrder)
		throws PortalException {

		if (commerceOrder.isOpen()) {
			if (commerceOrder.isDraft()) {
				return _hasOwnerPermission(permissionChecker, commerceOrder);
			}

			return _hasPermission(
				permissionChecker, commerceOrder.getGroupId(),
				CommerceActionKeys.APPROVE_OPEN_COMMERCE_ORDERS,
				CommerceActionKeys.VIEW_OPEN_COMMERCE_ORDERS);
		}

		String[] actionIds = {
			CommerceActionKeys.MANAGE_COMMERCE_ORDERS,
			CommerceActionKeys.VIEW_COMMERCE_ORDERS
		};

		if (_hasPermission(
				permissionChecker, commerceOrder.getSiteGroupId(), actionIds)) {

			return true;
		}

		return _hasAncestorPermission(
			permissionChecker, commerceOrder.getGroupId(), actionIds);
	}

	private boolean _hasAncestorPermission(
			PermissionChecker permissionChecker, long groupId,
			String... actionIds)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		List<Group> groups = group.getAncestors();

		groups.add(group);

		for (Group curGroup : groups) {
			if (_hasPermission(
					permissionChecker, curGroup.getGroupId(), actionIds)) {

				return true;
			}
		}

		return false;
	}

	private boolean _hasOwnerPermission(
		PermissionChecker permissionChecker, CommerceOrder commerceOrder) {

		long userId = permissionChecker.getUserId();

		if ((userId == commerceOrder.getUserId()) ||
			(userId == commerceOrder.getOrderUserId())) {

			return true;
		}

		return false;
	}

	private boolean _hasPermission(
		PermissionChecker permissionChecker, long groupId,
		String... actionIds) {

		for (String actionId : actionIds) {
			if (_portletResourcePermission.contains(
					permissionChecker, groupId, actionId)) {

				return true;
			}
		}

		return false;
	}

	private final GroupLocalService _groupLocalService;
	private final PortletResourcePermission _portletResourcePermission;

}