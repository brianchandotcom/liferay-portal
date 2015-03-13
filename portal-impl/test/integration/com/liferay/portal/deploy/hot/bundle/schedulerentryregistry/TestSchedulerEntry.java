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

package com.liferay.portal.deploy.hot.bundle.schedulerentryregistry;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.CronTrigger;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.StorageTypeAware;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerType;

import java.util.Date;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Manuel de la Peña
 */
@Component(
	immediate = true,
	property = {
		"service.ranking:Integer=" + Integer.MAX_VALUE,
		"javax.portlet.name:String=testportlet"
	},
	service = SchedulerEntry.class
)
public class TestSchedulerEntry extends BaseMessageListener
	implements SchedulerEntry, StorageTypeAware {

	@Override
	public String getDescription() {
		return TestSchedulerEntry.class.getName();
	}

	@Override
	public String getEventListenerClass() {
		return getClass().getName();
	}

	@Override
	public StorageType getStorageType() {
		return StorageType.MEMORY;
	}

	@Override
	public TimeUnit getTimeUnit() {
		return TimeUnit.MINUTE;
	}

	@Override
	public Trigger getTrigger() throws SchedulerException {
		return new CronTrigger(
			"JOB_NAME", "GROUP_NAME", new Date(), "0 0 12 * * ?");
	}

	@Override
	public TriggerType getTriggerType() {
		return TriggerType.CRON;
	}

	@Override
	public String getTriggerValue() {
		return "11000";
	}

	@Override
	public void setDescription(String description) {
	}

	@Override
	public void setEventListenerClass(String eventListenerClass) {
	}

	@Override
	public void setTimeUnit(TimeUnit timeUnit) {
	}

	@Override
	public void setTriggerType(TriggerType triggerType) {
	}

	@Override
	public void setTriggerValue(int triggerValue) {
	}

	@Override
	public void setTriggerValue(long triggerValue) {
	}

	@Override
	public void setTriggerValue(String triggerValue) {
	}

	@Override
	protected void doReceive(Message message) throws Exception {
	}

	@Reference(target = "(javax.portlet.name=testportlet)")
	protected void setPortlet(Portlet portlet) {
	}

}