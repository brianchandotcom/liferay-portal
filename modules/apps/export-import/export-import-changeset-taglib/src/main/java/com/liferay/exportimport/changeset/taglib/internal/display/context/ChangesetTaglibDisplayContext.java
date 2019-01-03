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

package com.liferay.exportimport.changeset.taglib.internal.display.context;

import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.WorkflowedModel;
import com.liferay.portal.kernel.util.ArrayUtil;

/**
 * @author Máté Thurzó
 */
public class ChangesetTaglibDisplayContext {

	public static boolean isShowExportMenuItem(Group group, String portletId) {
		try {
			if (group.isLayout()) {
				return false;
			}

			if ((group.isStagingGroup() || group.isStagedRemotely()) &&
				group.isStagedPortlet(portletId)) {

				return false;
			}

			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	public static boolean isShowPublishMenuItem(Group group, String portletId) {
		try {
			if (group.isLayout()) {
				return false;
			}

			if ((group.isStagingGroup() || group.isStagedRemotely()) &&
				group.isStagedPortlet(portletId)) {

				return true;
			}

			return false;
		}
		catch (Exception e) {
			return false;
		}
	}

	public static boolean isShowPublishMenuItem(
		Group group, String portletId, String className, String uuid) {

		try {
			StagedModelDataHandler stagedModelDataHandler =
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					className);

			StagedModel stagedModel =
				stagedModelDataHandler.fetchStagedModelByUuidAndGroupId(
					uuid, group.getGroupId());

			if (stagedModel == null) {
				return false;
			}

			if (stagedModel instanceof WorkflowedModel) {
				WorkflowedModel workflowedModel = (WorkflowedModel)stagedModel;

				if (!ArrayUtil.contains(
						stagedModelDataHandler.getExportableStatuses(),
						workflowedModel.getStatus())) {

					return false;
				}
			}

			return isShowPublishMenuItem(group, portletId);
		}
		catch (Exception e) {
			return false;
		}
	}

}