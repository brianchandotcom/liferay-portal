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

package com.liferay.trash.web.internal.messaging;

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.util.PropsValues;
import com.liferay.trash.service.TrashEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides a scheduled task to empty the Recycly Bin when the maximum Recycle
 * Bin entry age has been exceeded. The maximum Recycle Bin entry age is defined
 * by the <code>trash.entries.max.age</code> property (in minutes). The
 * scheduled task uses the <code>trash.entry.check.interval</code> property to
 * define the execution interval (in minutes).
 *
 * @author Eudaldo Alonso
 */
@Component(service = SchedulerJobConfiguration.class)
public class CheckEntryMessageListener implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutor() {
		return _trashEntryLocalService::checkEntries;
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			PropsValues.TRASH_ENTRY_CHECK_INTERVAL, TimeUnit.MINUTE);
	}

	@Reference
	private TrashEntryLocalService _trashEntryLocalService;

}