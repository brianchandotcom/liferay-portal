/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.permission.resource;

import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.WorkflowedModel;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermissionUtil;

import java.util.Objects;
import java.util.function.ToLongFunction;

/**
 * @author Preston Crary
 */
public class WorkflowedModelPermissionLogic<T extends GroupedModel>
	implements ModelResourcePermissionLogic<T> {

	public WorkflowedModelPermissionLogic(
		ModelResourcePermission<T> modelResourcePermission,
		GroupLocalService groupLocalService,
		ToLongFunction<T> primKeyToLongFunction) {

		_modelResourcePermission = Objects.requireNonNull(
			modelResourcePermission);
		_groupLocalService = groupLocalService;
		_primKeyToLongFunction = Objects.requireNonNull(primKeyToLongFunction);
	}

	@Override
	public Boolean contains(
			PermissionChecker permissionChecker, String name, T model,
			String actionId)
		throws PortalException {

		WorkflowedModel workflowedModel = (WorkflowedModel)model;

		if (workflowedModel.isDraft() || workflowedModel.isScheduled()) {
			if (!actionId.equals(ActionKeys.VIEW) ||
				_modelResourcePermission.contains(
					permissionChecker, model, ActionKeys.UPDATE)) {

				return null;
			}

			if ((model.getGroupId() == GroupConstants.DEFAULT_LIVE_GROUP_ID) ||
				!(model instanceof StagedModel)) {

				return false;
			}

			Group group = _groupLocalService.getGroup(model.getGroupId());

			if (!group.isStaged() || group.isStagingGroup() ||
				group.isStagedRemotely()) {

				return false;
			}

			StagedModelDataHandler<?> stagedModelDataHandler =
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					model.getModelClassName());

			StagedModel liveStagedModel = (StagedModel)model;

			Group stagingGroup = group.getStagingGroup();

			StagedModel stagingStagedModel =
				stagedModelDataHandler.fetchStagedModelByUuidAndGroupId(
					liveStagedModel.getUuid(), stagingGroup.getGroupId());

			if (!actionId.equals(ActionKeys.VIEW) ||
				(stagingStagedModel == null) ||
				_modelResourcePermission.contains(
					permissionChecker, (T)stagingStagedModel,
					ActionKeys.UPDATE)) {

				return null;
			}

			return false;
		}
		else if (workflowedModel.isPending()) {
			return WorkflowPermissionUtil.hasPermission(
				permissionChecker, model.getGroupId(), name,
				_primKeyToLongFunction.applyAsLong(model), actionId);
		}

		return null;
	}

	private final GroupLocalService _groupLocalService;
	private final ModelResourcePermission<T> _modelResourcePermission;
	private final ToLongFunction<T> _primKeyToLongFunction;

}