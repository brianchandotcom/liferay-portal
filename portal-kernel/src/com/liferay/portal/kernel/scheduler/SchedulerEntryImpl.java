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

	public SchedulerEntryImpl(String eventListenerClass, Trigger trigger) {
		this(eventListenerClass, trigger, StringPool.BLANK);
	}

	public SchedulerEntryImpl(
		String eventListenerClass, Trigger trigger, String description) {

		_eventListenerClass = eventListenerClass;
		_trigger = trigger;
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
	public Trigger getTrigger() {
		return _trigger;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append(", description=, eventListenerClass=");
		sb.append(_eventListenerClass);
		sb.append(", trigger=");
		sb.append(_trigger);
		sb.append("}");

		return sb.toString();
	}

	private String _description;
	private String _eventListenerClass;
	private Trigger _trigger;

}