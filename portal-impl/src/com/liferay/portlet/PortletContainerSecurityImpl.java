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

import com.liferay.portal.kernel.portlet.ActionResult;
import com.liferay.portal.kernel.portlet.PortletContainer;
import com.liferay.portal.kernel.portlet.PortletContainerException;
import com.liferay.portal.kernel.portlet.PortletContainerSecurity;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.util.PropsValues;

import javax.portlet.Event;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@DoPrivileged
public class PortletContainerSecurityImpl implements PortletContainer,
	PortletContainerSecurity {

	public PortletContainerSecurityImpl(PortletContainer portletContainer){

		this._portletContainer = portletContainer;

		// Portlet add default resource check white list

		resetPortletAddDefaultResourceCheckWhitelist();
		resetPortletAddDefaultResourceCheckWhitelistActions();
	}

	public void preparePortlet(HttpServletRequest request, Portlet portlet)
		throws PortletContainerException {

		_portletContainer.preparePortlet(request, portlet);
	}

	public ActionResult processAction(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		return _portletContainer.processAction(request, response, portlet);
	}

	public List<Event> processEvent(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, Layout layout, Event event)
		throws PortletContainerException {

		return _portletContainer.processEvent(
			request, response, portlet, layout, event);
	}

	public void render(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		_portletContainer.render(request, response, portlet);
	}

	public void serveResource(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		_portletContainer.serveResource(request, response, portlet);
	}

	public Set<String> getPortletAddDefaultResourceCheckWhitelist() {
		return _portletAddDefaultResourceCheckWhitelist;
	}

	public Set<String> getPortletAddDefaultResourceCheckWhitelistActions() {
		return _portletAddDefaultResourceCheckWhitelistActions;
	}

	public Set<String> resetPortletAddDefaultResourceCheckWhitelist() {
		_portletAddDefaultResourceCheckWhitelist = SetUtil.fromArray(
			PropsValues.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST);

		_portletAddDefaultResourceCheckWhitelist = Collections.unmodifiableSet(
			_portletAddDefaultResourceCheckWhitelist);

		return _portletAddDefaultResourceCheckWhitelist;
	}

	public Set<String> resetPortletAddDefaultResourceCheckWhitelistActions() {
		_portletAddDefaultResourceCheckWhitelistActions = SetUtil.fromArray(
			PropsValues.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST_ACTIONS);

		_portletAddDefaultResourceCheckWhitelistActions =
			Collections.unmodifiableSet(
				_portletAddDefaultResourceCheckWhitelistActions);

		return _portletAddDefaultResourceCheckWhitelistActions;
	}

	private Set<String> _portletAddDefaultResourceCheckWhitelist;
	private Set<String> _portletAddDefaultResourceCheckWhitelistActions;
	private PortletContainer _portletContainer;
}