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

package com.liferay.user.associated.data.web.internal.export;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.user.associated.data.web.internal.export.background.task.UADExportBackgroundTaskExecutor;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pei-Jung Lan
 */
public class UADExporter {

	public static long exportApplicationDataInBackground(
			String applicationKey, long userId, long groupId)
		throws PortalException {

		Map<String, Serializable> taskContextMap = new HashMap<>();

		taskContextMap.put("applicationKey", applicationKey);

		PortletFileRepositoryUtil.addPortletRepository(
			groupId, PortletKeys.BACKGROUND_TASK, new ServiceContext());

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
			CompanyThreadLocal.getCompanyId());

		BackgroundTask backgroundTask =
			BackgroundTaskManagerUtil.addBackgroundTask(
				defaultUserId, groupId, String.valueOf(userId),
				UADExportBackgroundTaskExecutor.class.getName(), taskContextMap,
				new ServiceContext());

		return backgroundTask.getBackgroundTaskId();
	}

}