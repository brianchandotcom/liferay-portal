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

package com.liferay.depot.web.internal.display.context;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.web.internal.constants.DepotAdminWebKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alicia García
 */
public class DepotApplicationDisplayContext {

	public DepotApplicationDisplayContext(
		HttpServletRequest httpServletRequest, Portal portal) {

		_portal = portal;
		_portletRequest = (PortletRequest)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public long getDepotEntryId() {
		DepotEntry depotEntry = (DepotEntry)_portletRequest.getAttribute(
			DepotAdminWebKeys.DEPOT_ENTRY);

		return depotEntry.getDepotEntryId();
	}

	public String getPortletTitle() {
		return _portal.getPortletTitle(_portletId, _themeDisplay.getLocale());
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	private final Portal _portal;
	private String _portletId;
	private final PortletRequest _portletRequest;
	private PortletURL _portletURL;
	private final ThemeDisplay _themeDisplay;

}