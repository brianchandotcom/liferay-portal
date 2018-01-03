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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.WorkflowedModel;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermission;

import java.util.Objects;
import java.util.function.ToLongFunction;

/**
 * @author Preston Crary
 */
public class WorkflowedModelPermissionLogic<T extends GroupedModel>
	implements ModelResourcePermissionLogic<T> {

	public WorkflowedModelPermissionLogic(
		WorkflowPermission workflowPermission,
		ModelResourcePermission<T> modelResourcePermission,
		ToLongFunction<T> primKeyToLongFunction) {

		_workflowPermission = Objects.requireNonNull(workflowPermission);
		_modelResourcePermission = Objects.requireNonNull(
			modelResourcePermission);
		_primKeyToLongFunction = Objects.requireNonNull(primKeyToLongFunction);
	}

	@Override
	public Boolean contains(
			PermissionChecker permissionChecker, String name, T model,
			String actionId)
		throws PortalException {

		WorkflowedModel workflowedModel = (WorkflowedModel)model;

		if (workflowedModel.isDraft() || workflowedModel.isScheduled()) {
			if (actionId.equals(ActionKeys.VIEW) &&
				!_modelResourcePermission.contains(
					permissionChecker, model, ActionKeys.UPDATE)) {

				return false;
			}
		}
		else if (workflowedModel.isPending()) {
			return _workflowPermission.hasPermission(
				permissionChecker, model.getGroupId(), name,
				_primKeyToLongFunction.applyAsLong(model), actionId);
		}

		return null;
	}

	private final ModelResourcePermission<T> _modelResourcePermission;
	private final ToLongFunction<T> _primKeyToLongFunction;
	private final WorkflowPermission _workflowPermission;

}