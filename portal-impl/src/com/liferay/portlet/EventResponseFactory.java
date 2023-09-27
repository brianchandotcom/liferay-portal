/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayEventResponse;
import com.liferay.portlet.internal.EventRequestImpl;
import com.liferay.portlet.internal.EventResponseImpl;

import javax.portlet.EventRequest;
import javax.portlet.PortletModeException;
import javax.portlet.WindowStateException;
import javax.portlet.filter.EventRequestWrapper;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Neil Griffin
 */
public class EventResponseFactory {

	public static LiferayEventResponse create(
			EventRequest eventRequest, HttpServletResponse httpServletResponse,
			User user, Layout layout)
		throws PortletModeException, WindowStateException {

		while (eventRequest instanceof EventRequestWrapper) {
			EventRequestWrapper eventRequestWrapper =
				(EventRequestWrapper)eventRequest;

			eventRequest = eventRequestWrapper.getRequest();
		}

		EventResponseImpl eventResponseImpl = new EventResponseImpl();

		eventResponseImpl.init(
			(EventRequestImpl)eventRequest, httpServletResponse, user, layout);

		return eventResponseImpl;
	}

}