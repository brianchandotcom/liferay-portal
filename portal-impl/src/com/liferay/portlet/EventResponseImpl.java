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

import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;

import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class EventResponseImpl
	extends StateAwareResponseImpl implements EventResponse {

	@Override
	public String getLifecycle() {
		return PortletRequest.EVENT_PHASE;
	}

	@Override
	public void setRenderParameters(EventRequest eventRequest) {
	}

	protected void init(
			PortletRequestImpl portletRequestImpl, HttpServletResponse response,
			String portletName, User user, Layout layout)
		throws PortletModeException, WindowStateException {

		init(
			portletRequestImpl, response, portletName, user, layout, null,
			null);
	}

}