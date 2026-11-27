/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.security.permission.contributor;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.workflow.security.permission.WorkflowTaskPermissionContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(service = WorkflowTaskPermissionContributor.class)
public class CMSWorkflowTaskPermissionContributor
	implements WorkflowTaskPermissionContributor {

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, WorkflowTask workflowTask,
		long groupId) {

		if (!permissionChecker.isGroupAdmin(groupId)) {
			return false;
		}

		DepotEntry groupDepotEntry =
			_depotEntryLocalService.fetchGroupDepotEntry(groupId);

		if ((groupDepotEntry == null) ||
			(groupDepotEntry.getType() != DepotConstants.TYPE_SPACE)) {

			return false;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
				permissionChecker.getCompanyId(),
				MapUtil.getString(
					workflowTask.getOptionalAttributes(),
					WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME));

		if ((objectDefinition == null) || !objectDefinition.isCMS()) {
			return false;
		}

		return true;
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}