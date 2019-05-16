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

package com.liferay.batch.engine.core.launch;

import com.liferay.batch.engine.core.BatchJob;
import com.liferay.batch.engine.exception.JobExecutionAlreadyRunningException;
import com.liferay.batch.engine.exception.JobInstanceAlreadyCompleteException;
import com.liferay.batch.engine.exception.JobRestartException;
import com.liferay.batch.engine.model.BatchJobExecution;
import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Ivica Cardic
 */
public interface BatchJobLauncher {

	public BatchJobExecution run(
			BatchJob batchJob, UnicodeProperties jobSettingsProperties)
		throws JobExecutionAlreadyRunningException,
			   JobInstanceAlreadyCompleteException, JobRestartException;

	public BatchJobExecution runAsync(
			BatchJob batchJob, UnicodeProperties jobSettingsProperties)
		throws JobExecutionAlreadyRunningException,
			   JobInstanceAlreadyCompleteException, JobRestartException;

}