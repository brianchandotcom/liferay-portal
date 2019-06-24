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

package com.liferay.exportimport.internal.security.permission.resource.logic;

import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.service.permission.GroupPermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	property = "background.task.executor.class.name=com.liferay.exportimport.internal.background.task.PortletExportBackgroundTaskExecutor",
	service = ModelResourcePermissionLogic.class
)
public class PortletExportBackgroundTaskExecutorModelResourcePermissionLogic
	implements ModelResourcePermissionLogic<BackgroundTask> {

	@Override
	public Boolean contains(
			PermissionChecker permissionChecker, String name,
			BackgroundTask backgroundTask, String actionId)
		throws PortalException {

		return _groupPermission.contains(
			permissionChecker, backgroundTask.getGroupId(),
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);
	}

	@Reference
	private GroupPermission _groupPermission;

}