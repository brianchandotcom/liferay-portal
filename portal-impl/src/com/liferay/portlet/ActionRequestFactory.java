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

package com.liferay.portlet;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.portlet.LiferayActionRequest;
import com.liferay.portlet.internal.ActionRequestImpl;

import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Neil Griffin
 */
public class ActionRequestFactory {

	public static LiferayActionRequest create(
		HttpServletRequest httpServletRequest, Portlet portlet,
		InvokerPortlet invokerPortlet, PortletContext portletContext,
		WindowState windowState, PortletMode portletMode,
		PortletPreferences preferences, long plid) {

		ActionRequestImpl actionRequestImpl = new ActionRequestImpl();

		actionRequestImpl.init(
			httpServletRequest, portlet, invokerPortlet, portletContext,
			windowState, portletMode, preferences, plid);

		return actionRequestImpl;
	}

}