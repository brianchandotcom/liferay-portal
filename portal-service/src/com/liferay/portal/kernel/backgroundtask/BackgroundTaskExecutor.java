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

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.BackgroundTask;

/**
 * @author Michael C. Han
 */
public interface BackgroundTaskExecutor {

	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception;

	public BackgroundTaskStatusMessageTranslator
		getBackgroundTaskStatusMessageTranslator();

	public String handleException(BackgroundTask backgroundTask, Exception e);

	public boolean isLocked(BackgroundTask backgroundTask)
		throws PortalException;

	public boolean isSerial();

}