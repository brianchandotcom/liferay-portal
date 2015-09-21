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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Shuyang Zhou
 */
public class SchedulerEntryImpl implements SchedulerEntry {

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public String getEventListenerClass() {
		return _eventListenerClass;
	}

	@Override
	public TimeUnit getTimeUnit() {
		return _timeUnit;
	}

	@Override
	public Trigger getTrigger() throws SchedulerException {
		if (_trigger != null) {
			return _trigger;
		}

		if (_triggerType.equals(TriggerType.CRON)) {
			_trigger = TriggerFactoryUtil.createTrigger(
				_eventListenerClass, _eventListenerClass, _triggerValue);
		}
		else if (_triggerType.equals(TriggerType.SIMPLE)) {
			int interval = GetterUtil.getInteger(_triggerValue);

			_trigger = TriggerFactoryUtil.createTrigger(
				_eventListenerClass, _eventListenerClass, interval, _timeUnit);
		}
		else {
			throw new SchedulerException(
				"Unsupport trigger type " + _triggerType);
		}

		return _trigger;
	}

	@Override
	public TriggerType getTriggerType() {
		return _triggerType;
	}

	@Override
	public String getTriggerValue() {
		return _triggerValue;
	}

	@Override
	public void setDescription(String description) {
		_description = description;
	}

	@Override
	public void setEventListenerClass(String eventListenerClass) {
		_eventListenerClass = eventListenerClass;
	}

	@Override
	public void setTimeUnit(TimeUnit timeUnit) {
		_timeUnit = timeUnit;
	}

	@Override
	public void setTriggerType(TriggerType triggerType) {
		_triggerType = triggerType;
	}

	@Override
	public void setTriggerValue(int triggerValue) {
		_triggerValue = String.valueOf(triggerValue);
	}

	@Override
	public void setTriggerValue(String triggerValue) {
		_triggerValue = triggerValue;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append(", description=");
		sb.append(_description);
		sb.append(", eventListenerClass=");
		sb.append(_eventListenerClass);
		sb.append(", timeUnit=");
		sb.append(_timeUnit);
		sb.append(", trigger=");
		sb.append(_trigger);
		sb.append(", triggerType=");
		sb.append(_triggerType);
		sb.append(", triggerValue=");
		sb.append(_triggerValue);
		sb.append("}");

		return sb.toString();
	}

	private String _description = StringPool.BLANK;
	private String _eventListenerClass = StringPool.BLANK;
	private TimeUnit _timeUnit;
	private Trigger _trigger;
	private TriggerType _triggerType;
	private String _triggerValue = StringPool.BLANK;

}