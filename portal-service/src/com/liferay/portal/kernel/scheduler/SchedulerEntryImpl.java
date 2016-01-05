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
	public Trigger getTrigger() {
		return _trigger;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setEventListenerClass(String eventListenerClass) {
		_eventListenerClass = eventListenerClass;
	}

	public void setTrigger(Trigger trigger) {
		_trigger = trigger;
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

	private String _description = StringPool.BLANK;
	private String _eventListenerClass = StringPool.BLANK;
	private Trigger _trigger;

}