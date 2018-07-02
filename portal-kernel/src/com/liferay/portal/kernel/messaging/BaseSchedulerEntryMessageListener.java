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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.Trigger;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of Judson (7.1.x)
 */
@Deprecated
public abstract class BaseSchedulerEntryMessageListener
	extends BaseMessageListener {

	public BaseSchedulerEntryMessageListener() {
		Class<?> clazz = getClass();

		schedulerEntryImpl.setEventListenerClass(clazz.getName());
	}

	public String getDescription() {
		return schedulerEntryImpl.getDescription();
	}

	public String getEventListenerClass() {
		return schedulerEntryImpl.getEventListenerClass();
	}

	public Trigger getTrigger() {
		return schedulerEntryImpl.getTrigger();
	}

	protected SchedulerEntryImpl schedulerEntryImpl = new SchedulerEntryImpl();

}