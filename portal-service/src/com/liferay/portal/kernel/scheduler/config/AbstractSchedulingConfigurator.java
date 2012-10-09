/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.scheduler.config;

import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.StorageType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shuyang Zhou
 * @author Tina Tian
 */
public abstract class AbstractSchedulingConfigurator
	implements SchedulingConfigurator {

	public void afterPropertiesSet() {
		execute();
	}

	public void setExceptionsMaxSize(int exceptionsMaxSize) {
		_exceptionsMaxSize = exceptionsMaxSize;
	}

	public void setSchedulerEntries(List<SchedulerEntry> schedulerEntries) {
		_schedulerEntries = schedulerEntries;
	}

	public void setStorageType(StorageType storageType) {
		_storageType = storageType;
	}

	protected int _exceptionsMaxSize = 0;
	protected List<SchedulerEntry> _schedulerEntries =
		new ArrayList<SchedulerEntry>();
	protected StorageType _storageType = StorageType.MEMORY_CLUSTERED;

}