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

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermission;

import java.util.Objects;
import java.util.function.ToLongFunction;

/**
 * @author Marco Leo
 */
public class CommerceOrderWorkflowedModelPermissionLogic
	implements ModelResourcePermissionLogic<CommerceOrder> {

	public CommerceOrderWorkflowedModelPermissionLogic(
		WorkflowPermission workflowPermission,
		ModelResourcePermission<CommerceOrder> modelResourcePermission,
		ToLongFunction<CommerceOrder> primKeyToLongFunction) {

		_workflowPermission = Objects.requireNonNull(workflowPermission);
		_modelResourcePermission = Objects.requireNonNull(
			modelResourcePermission);
		_primKeyToLongFunction = Objects.requireNonNull(primKeyToLongFunction);
	}

	@Override
	public Boolean contains(
			PermissionChecker permissionChecker, String name,
			CommerceOrder commerceOrder, String actionId)
		throws PortalException {

		if (commerceOrder.isDraft() || commerceOrder.isScheduled()) {
			if (!actionId.equals(ActionKeys.VIEW) ||
				_modelResourcePermission.contains(
					permissionChecker, commerceOrder, ActionKeys.UPDATE)) {

				return null;
			}

			return false;
		}
		else if (commerceOrder.isPending()) {
			return _workflowPermission.hasPermission(
				permissionChecker, commerceOrder.getGroupId(), name,
				_primKeyToLongFunction.applyAsLong(commerceOrder), actionId);
		}

		return null;
	}

	private final ModelResourcePermission<CommerceOrder>
		_modelResourcePermission;
	private final ToLongFunction<CommerceOrder> _primKeyToLongFunction;
	private final WorkflowPermission _workflowPermission;

}