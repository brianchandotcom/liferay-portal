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

package com.liferay.portal.kernel.scheduler.config;

import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.StorageType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shuyang Zhou
 * @author Tina Tian
 */
public abstract class AbstractSchedulingConfigurator
	implements SchedulingConfigurator {

	public void afterPropertiesSet() {
		configure();
	}

	public void setExceptionsMaxSize(int exceptionsMaxSize) {
		this.exceptionsMaxSize = exceptionsMaxSize;
	}

	public void setSchedulerEntries(List<SchedulerEntry> schedulerEntries) {
		this.schedulerEntries = schedulerEntries;
	}

	public void setStorageType(StorageType storageType) {
		this.storageType = storageType;
	}

	protected int exceptionsMaxSize = 0;
	protected Map<String, MessageListener> messageListeners = new HashMap<>();
	protected List<SchedulerEntry> schedulerEntries = Collections.emptyList();
	protected StorageType storageType = StorageType.MEMORY_CLUSTERED;

}