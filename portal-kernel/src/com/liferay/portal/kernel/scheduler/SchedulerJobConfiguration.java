/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.scheduler;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;

/**
 * @author Tina Tian
 */
public interface SchedulerJobConfiguration {

	public default UnsafeConsumer<Long, Exception>
		getCompanyJobExecutorUnsafeConsumer() {

		return companyId -> {
			UnsafeRunnable<Exception> unsafeRunnable =
				getJobExecutorUnsafeRunnable();

			unsafeRunnable.run();
		};
	}

	public default String getDestinationName() {
		return DestinationNames.SCHEDULER_DISPATCH;
	}

	public default UnsafeConsumer<Message, Exception>
		getJobExecutorUnsafeConsumer() {

		return null;
	}

	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable();

	public default String getName() {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	public TriggerConfiguration getTriggerConfiguration();

}