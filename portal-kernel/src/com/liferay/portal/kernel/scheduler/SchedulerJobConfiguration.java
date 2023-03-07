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

package com.liferay.portal.kernel.scheduler;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.messaging.DestinationNames;

/**
 * @author Tina Tian
 */
public interface SchedulerJobConfiguration {

	public default UnsafeConsumer<Long, Exception> getCompanyJobExecutor() {
		return companyId -> {
			UnsafeRunnable<Exception> unsafeRunnable = getJobExecutor();

			unsafeRunnable.run();
		};
	}

	public default String getDestinationName() {
		return DestinationNames.SCHEDULER_DISPATCH;
	}

	public UnsafeRunnable<Exception> getJobExecutor();

	public default String getName() {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	public TriggerConfiguration getTriggerConfiguration();

}