/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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