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

package com.liferay.journal.web.messaging;

import com.liferay.journal.web.configuration.JournalWebConfigurationValues;
import com.liferay.journal.web.constants.JournalPortletKeys;
import com.liferay.journal.web.portlet.JournalPortlet;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Tina Tian
 */
@Component(
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL
	},
	service = SchedulerEntry.class
)
public class CheckArticleMessageListener
	extends BaseMessageListener implements SchedulerEntry {

	@Override
	public String getDescription() {
		return _schedulerEntry.getDescription();
	}

	@Override
	public String getEventListenerClass() {
		return _schedulerEntry.getEventListenerClass();
	}

	@Override
	public TimeUnit getTimeUnit() {
		return _schedulerEntry.getTimeUnit();
	}

	@Override
	public Trigger getTrigger() throws SchedulerException {
		return _schedulerEntry.getTrigger();
	}

	@Override
	public TriggerType getTriggerType() {
		return _schedulerEntry.getTriggerType();
	}

	@Override
	public String getTriggerValue() {
		return _schedulerEntry.getTriggerValue();
	}

	@Override
	public void setDescription(String description) {
		_schedulerEntry.setDescription(description);
	}

	@Override
	public void setEventListenerClass(String eventListenerClass) {
		_schedulerEntry.setEventListenerClass(eventListenerClass);
	}

	@Override
	public void setTimeUnit(TimeUnit timeUnit) {
		_schedulerEntry.setTimeUnit(timeUnit);
	}

	@Override
	public void setTriggerType(TriggerType triggerType) {
		_schedulerEntry.setTriggerType(triggerType);
	}

	@Override
	public void setTriggerValue(int triggerValue) {
		_schedulerEntry.setTriggerValue(triggerValue);
	}

	@Override
	public void setTriggerValue(long triggerValue) {
		_schedulerEntry.setTriggerValue(triggerValue);
	}

	@Override
	public void setTriggerValue(String triggerValue) {
		_schedulerEntry.setTriggerValue(triggerValue);
	}

	@Activate
	protected void activate() {
		_schedulerEntry = new SchedulerEntryImpl();

		_schedulerEntry.setEventListenerClass(
			CheckArticleMessageListener.class.getName());
		_schedulerEntry.setTimeUnit(TimeUnit.MINUTE);
		_schedulerEntry.setTriggerType(TriggerType.SIMPLE);
		_schedulerEntry.setTriggerValue(
			JournalWebConfigurationValues.CHECK_INTERVAL);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		JournalArticleLocalServiceUtil.checkArticles();
	}

	@Reference
	protected void setJournalPortlet(JournalPortlet journalPortlet) {
	}

	private SchedulerEntry _schedulerEntry;

}