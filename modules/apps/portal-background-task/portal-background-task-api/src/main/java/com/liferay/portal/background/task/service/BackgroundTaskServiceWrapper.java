/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.background.task.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BackgroundTaskService}.
 *
 * @author Brian Wing Shun Chan
 * @see BackgroundTaskService
 * @generated
 */
public class BackgroundTaskServiceWrapper
	implements BackgroundTaskService, ServiceWrapper<BackgroundTaskService> {

	public BackgroundTaskServiceWrapper() {
		this(null);
	}

	public BackgroundTaskServiceWrapper(
		BackgroundTaskService backgroundTaskService) {

		_backgroundTaskService = backgroundTaskService;
	}

	@Override
	public int getBackgroundTasksCount(
		long groupId, String taskExecutorClassName, boolean completed) {

		return _backgroundTaskService.getBackgroundTasksCount(
			groupId, taskExecutorClassName, completed);
	}

	@Override
	public int getBackgroundTasksCount(
		long groupId, String name, String taskExecutorClassName) {

		return _backgroundTaskService.getBackgroundTasksCount(
			groupId, name, taskExecutorClassName);
	}

	@Override
	public String getBackgroundTaskStatusJSON(long backgroundTaskId) {
		return _backgroundTaskService.getBackgroundTaskStatusJSON(
			backgroundTaskId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _backgroundTaskService.getOSGiServiceIdentifier();
	}

	@Override
	public BackgroundTaskService getWrappedService() {
		return _backgroundTaskService;
	}

	@Override
	public void setWrappedService(BackgroundTaskService backgroundTaskService) {
		_backgroundTaskService = backgroundTaskService;
	}

	private BackgroundTaskService _backgroundTaskService;

}