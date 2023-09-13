/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.security.permission.resource.logic;

import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(
	property = "background.task.executor.class.name=com.liferay.exportimport.internal.background.task.LayoutExportBackgroundTaskExecutor",
	service = ModelResourcePermissionLogic.class
)
public class LayoutExportBackgroundTaskExecutorModelResourcePermissionLogic
	implements ModelResourcePermissionLogic<BackgroundTask> {

	@Override
	public Boolean contains(
			PermissionChecker permissionChecker, String name,
			BackgroundTask backgroundTask, String actionId)
		throws PortalException {

		return GroupPermissionUtil.contains(
			permissionChecker, backgroundTask.getGroupId(),
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);
	}

}