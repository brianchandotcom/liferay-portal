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

package com.liferay.scheduler.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SchedulerProcessService}.
 *
 * @author Alessio Antonio Rendina
 * @see SchedulerProcessService
 * @generated
 */
public class SchedulerProcessServiceWrapper
	implements SchedulerProcessService,
			   ServiceWrapper<SchedulerProcessService> {

	public SchedulerProcessServiceWrapper(
		SchedulerProcessService schedulerProcessService) {

		_schedulerProcessService = schedulerProcessService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _schedulerProcessService.getOSGiServiceIdentifier();
	}

	@Override
	public SchedulerProcessService getWrappedService() {
		return _schedulerProcessService;
	}

	@Override
	public void setWrappedService(
		SchedulerProcessService schedulerProcessService) {

		_schedulerProcessService = schedulerProcessService;
	}

	private SchedulerProcessService _schedulerProcessService;

}