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

package com.liferay.asset.publisher.web.messaging;

import com.liferay.asset.publisher.web.configuration.AssetPublisherWebConfigurationValues;
import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.web.portlet.AssetPublisherPortlet;
import com.liferay.asset.publisher.web.util.AssetPublisherUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerType;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Diaz
 * @author Sergio González
 */
@Component(
	property = {
		"javax.portlet.name=" + AssetPublisherPortletKeys.ASSET_PUBLISHER
	},
	service = SchedulerEntry.class
)
public class CheckAssetEntryMessageListener
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
			CheckAssetEntryMessageListener.class.getName());
		_schedulerEntry.setTimeUnit(TimeUnit.HOUR);
		_schedulerEntry.setTriggerType(TriggerType.SIMPLE);
		_schedulerEntry.setTriggerValue(
			AssetPublisherWebConfigurationValues.CHECK_INTERVAL);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		AssetPublisherUtil.checkAssetEntries();
	}

	@Reference
	protected void setPortlet(AssetPublisherPortlet assetPublisherPortlet) {
	}

	private SchedulerEntry _schedulerEntry;

}