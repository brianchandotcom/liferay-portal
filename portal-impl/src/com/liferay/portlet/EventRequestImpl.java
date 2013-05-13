/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class EventRequestImpl
	extends PortletRequestImpl implements EventRequest {

	@Override
	public Event getEvent() {
		return _event;
	}

	@Override
	public String getLifecycle() {
		return PortletRequest.EVENT_PHASE;
	}

	public void setEvent(Event event) {
		_event = event;
	}

	private Event _event;

}