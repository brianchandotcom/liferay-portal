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

package com.liferay.portal.background.task.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BackgroundTaskService}.
 *
 * @author Brian Wing Shun Chan
 * @see BackgroundTaskService
 * @generated
 */
@ProviderType
public class BackgroundTaskServiceWrapper implements BackgroundTaskService,
	ServiceWrapper<BackgroundTaskService> {
	public BackgroundTaskServiceWrapper(
		BackgroundTaskService backgroundTaskService) {
		_backgroundTaskService = backgroundTaskService;
	}

	@Override
	public java.lang.String getBackgroundTaskStatusJSON(long backgroundTaskId) {
		return _backgroundTaskService.getBackgroundTaskStatusJSON(backgroundTaskId);
	}

	@Override
	public int getBackgroundTasksCount(long groupId,
		java.lang.String taskExecutorClassName, java.lang.String completed) {
		return _backgroundTaskService.getBackgroundTasksCount(groupId,
			taskExecutorClassName, completed);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
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