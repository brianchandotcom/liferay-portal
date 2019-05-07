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

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayEventResponse;

import javax.portlet.EventRequest;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Neil Griffin
 */
public class EventResponseImpl
	extends StateAwareResponseImpl implements LiferayEventResponse {

	@Override
	public String getLifecycle() {
		return PortletRequest.EVENT_PHASE;
	}

	public void init(
			PortletRequestImpl portletRequestImpl,
			HttpServletResponse httpServletResponse, User user, Layout layout)
		throws PortletModeException, WindowStateException {

		init(portletRequestImpl, httpServletResponse, user, layout, false);
	}

	@Override
	public void setRenderParameters(EventRequest eventRequest) {
		if (eventRequest == null) {
			throw new IllegalArgumentException();
		}

		setRenderParameters(eventRequest.getParameterMap());
	}

}