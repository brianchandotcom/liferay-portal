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

package com.liferay.user.associated.data.web.internal.security.permission.resource.logic;

import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;

import org.osgi.service.component.annotations.Component;

/**
 * @author Samuel Trong Tran
 */
@Component(
	property = "background.task.executor.class.name=com.liferay.user.associated.data.web.internal.export.background.task.UADExportBackgroundTaskExecutor",
	service = ModelResourcePermissionLogic.class
)
public class UADExportBackgroundTaskExecutorModelResourcePermissionLogic
	implements ModelResourcePermissionLogic<BackgroundTask> {

	@Override
	public Boolean contains(
			PermissionChecker permissionChecker, String name,
			BackgroundTask backgroundTask, String actionId)
		throws PortalException {

		return permissionChecker.isCompanyAdmin();
	}

}