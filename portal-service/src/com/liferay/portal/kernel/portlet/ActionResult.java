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

package com.liferay.portal.kernel.portlet;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;

import javax.portlet.Event;

/**
 * @author Shuyang Zhou
 */
public class ActionResult implements Serializable {

	public static final ActionResult EMPTY_ACTION_RESULT = new ActionResult(
		Collections.<Event>emptyList(), null);

	public ActionResult(List<Event> events, String location) {
		_events = events;
		_location = location;
	}

	public List<Event> getEvents() {
		return _events;
	}

	public String getLocation() {
		return _location;
	}

	private final List<Event> _events;
	private final String _location;

}