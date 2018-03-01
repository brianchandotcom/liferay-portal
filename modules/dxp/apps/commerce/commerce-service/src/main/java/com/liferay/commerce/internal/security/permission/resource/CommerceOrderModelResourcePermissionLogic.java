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

import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderModelResourcePermissionLogic
	implements ModelResourcePermissionLogic<CommerceOrder> {

	public CommerceOrderModelResourcePermissionLogic(
		GroupLocalService groupLocalService,
		PortletResourcePermission portletResourcePermission,
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService) {

		_groupLocalService = groupLocalService;
		_portletResourcePermission = portletResourcePermission;
		_workflowDefinitionLinkLocalService =
			workflowDefinitionLinkLocalService;
	}

	@Override
	public Boolean contains(
			PermissionChecker permissionChecker, String name,
			CommerceOrder commerceOrder, String actionId)
		throws PortalException {

		if (actionId.equals(ActionKeys.ADD_DISCUSSION) ||
			actionId.equals(ActionKeys.DELETE_DISCUSSION) ||
			actionId.equals(ActionKeys.UPDATE_DISCUSSION)) {

			return _hasAncestorPermission(
				permissionChecker, commerceOrder.getGroupId(),
				CommerceOrderActionKeys.MANAGE_COMMERCE_ORDERS);
		}

		if (actionId.equals(CommerceOrderActionKeys.APPROVE_COMMERCE_ORDER)) {
			return _hasAncestorPermission(
				permissionChecker, commerceOrder.getGroupId(),
				CommerceOrderActionKeys.APPROVE_OPEN_COMMERCE_ORDERS);
		}

		if (actionId.equals(CommerceOrderActionKeys.CHECKOUT_COMMERCE_ORDER)) {
			return _containsCheckoutPermission(
				permissionChecker, commerceOrder);
		}

		if (actionId.equals(ActionKeys.DELETE)) {
			return _containsDeletePermission(permissionChecker, commerceOrder);
		}

		if (actionId.equals(ActionKeys.UPDATE)) {
			return _containsUpdatePermission(permissionChecker, commerceOrder);
		}

		if (actionId.equals(ActionKeys.VIEW)) {
			return _containsViewPermission(permissionChecker, commerceOrder);
		}

		return false;
	}

	private boolean _containsCheckoutPermission(
		PermissionChecker permissionChecker, CommerceOrder commerceOrder) {

		return _portletResourcePermission.contains(
			permissionChecker, commerceOrder.getGroupId(),
			CommerceOrderActionKeys.CHECKOUT_OPEN_COMMERCE_ORDERS);
	}

	private boolean _containsDeletePermission(
		PermissionChecker permissionChecker, CommerceOrder commerceOrder) {

		if (commerceOrder.isOpen()) {
			if (commerceOrder.isDraft()) {
				return _hasOwnerPermission(permissionChecker, commerceOrder);
			}

			if (_hasOwnerPermission(permissionChecker, commerceOrder)) {
				return true;
			}
		}

		return _portletResourcePermission.contains(
			permissionChecker, commerceOrder.getSiteGroupId(),
			CommerceOrderActionKeys.DELETE_COMMERCE_ORDERS);
	}

	private boolean _containsUpdatePermission(
			PermissionChecker permissionChecker, CommerceOrder commerceOrder)
		throws PortalException {

		if (commerceOrder.isOpen()) {
			if (commerceOrder.isDraft()) {
				return _hasOwnerPermission(permissionChecker, commerceOrder);
			}

			if (_workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(
					commerceOrder.getCompanyId(),
					commerceOrder.getSiteGroupId(),
					CommerceOrder.class.getName(), 0,
					CommerceOrderConstants.TYPE_PK_APPROVAL)) {

				return _hasPermission(
					permissionChecker, commerceOrder.getGroupId(),
					CommerceOrderActionKeys.APPROVE_OPEN_COMMERCE_ORDERS);
			}

			if (_hasOwnerPermission(permissionChecker, commerceOrder)) {
				return true;
			}
		}

		return _hasAncestorPermission(
			permissionChecker, commerceOrder.getGroupId(),
			CommerceOrderActionKeys.MANAGE_COMMERCE_ORDERS);
	}

	private boolean _containsViewPermission(
			PermissionChecker permissionChecker, CommerceOrder commerceOrder)
		throws PortalException {

		if (_hasOwnerPermission(permissionChecker, commerceOrder)) {
			return true;
		}

		if (commerceOrder.isOpen()) {
			if (commerceOrder.isDraft()) {
				return false;
			}

			return _hasPermission(
				permissionChecker, commerceOrder.getGroupId(),
				CommerceOrderActionKeys.APPROVE_OPEN_COMMERCE_ORDERS,
				CommerceOrderActionKeys.VIEW_OPEN_COMMERCE_ORDERS);
		}

		return _hasAncestorPermission(
			permissionChecker, commerceOrder.getGroupId(),
			CommerceOrderActionKeys.MANAGE_COMMERCE_ORDERS,
			CommerceOrderActionKeys.VIEW_COMMERCE_ORDERS);
	}

	private boolean _hasAncestorPermission(
			PermissionChecker permissionChecker, long groupId,
			String... actionIds)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		List<Group> groups = ListUtil.copy(group.getAncestors());

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
	private final WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}