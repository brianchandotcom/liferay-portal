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

import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerType;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseSchedulerEntryMessageListener
	extends BaseMessageListener implements SchedulerEntry {

	public BaseSchedulerEntryMessageListener() {
		Class<?> clazz = getClass();

		schedulerEntry.setEventListenerClass(clazz.getName());
	}

	@Override
	public String getDescription() {
		return schedulerEntry.getDescription();
	}

	@Override
	public String getEventListenerClass() {
		return schedulerEntry.getEventListenerClass();
	}

	@Override
	public TimeUnit getTimeUnit() {
		return schedulerEntry.getTimeUnit();
	}

	@Override
	public Trigger getTrigger() throws SchedulerException {
		return schedulerEntry.getTrigger();
	}

	@Override
	public TriggerType getTriggerType() {
		return schedulerEntry.getTriggerType();
	}

	@Override
	public String getTriggerValue() {
		return schedulerEntry.getTriggerValue();
	}

	@Override
	public void setDescription(String description) {
		schedulerEntry.setDescription(description);
	}

	@Override
	public void setEventListenerClass(String eventListenerClass) {
		schedulerEntry.setEventListenerClass(eventListenerClass);
	}

	@Override
	public void setTimeUnit(TimeUnit timeUnit) {
		schedulerEntry.setTimeUnit(timeUnit);
	}

	@Override
	public void setTriggerType(TriggerType triggerType) {
		schedulerEntry.setTriggerType(triggerType);
	}

	@Override
	public void setTriggerValue(int triggerValue) {
		schedulerEntry.setTriggerValue(triggerValue);
	}

	@Override
	public void setTriggerValue(String triggerValue) {
		schedulerEntry.setTriggerValue(triggerValue);
	}

	protected SchedulerEntry schedulerEntry = new SchedulerEntryImpl();

}