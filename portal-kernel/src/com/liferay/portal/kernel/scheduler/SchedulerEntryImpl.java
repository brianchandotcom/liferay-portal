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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

/**
 * @author Shuyang Zhou
 */
public class SchedulerEntryImpl implements SchedulerEntry {

	public SchedulerEntryImpl(
		String eventListenerClass, TriggerConfiguration triggerConfiguration) {

		this(eventListenerClass, triggerConfiguration, StringPool.BLANK);
	}

	public SchedulerEntryImpl(
		String eventListenerClass, TriggerConfiguration triggerConfiguration,
		String description) {

		_eventListenerClass = eventListenerClass;
		_triggerConfiguration = triggerConfiguration;
		_description = description;
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public String getEventListenerClass() {
		return _eventListenerClass;
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return _triggerConfiguration;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			", description=, eventListenerClass=", _eventListenerClass,
			", triggerConfiguration=", _triggerConfiguration, "}");
	}

	private String _description;
	private String _eventListenerClass;
	private TriggerConfiguration _triggerConfiguration;

}