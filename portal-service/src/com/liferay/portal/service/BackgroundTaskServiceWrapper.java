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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

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

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _backgroundTaskService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_backgroundTaskService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public int getBackgroundTasksCount(long groupId,
		java.lang.String taskExecutorClassName, java.lang.String completed) {
		return _backgroundTaskService.getBackgroundTasksCount(groupId,
			taskExecutorClassName, completed);
	}

	@Override
	public java.lang.String getBackgroundTaskStatusJSON(long backgroundTaskId) {
		return _backgroundTaskService.getBackgroundTaskStatusJSON(backgroundTaskId);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public BackgroundTaskService getWrappedBackgroundTaskService() {
		return _backgroundTaskService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedBackgroundTaskService(
		BackgroundTaskService backgroundTaskService) {
		_backgroundTaskService = backgroundTaskService;
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