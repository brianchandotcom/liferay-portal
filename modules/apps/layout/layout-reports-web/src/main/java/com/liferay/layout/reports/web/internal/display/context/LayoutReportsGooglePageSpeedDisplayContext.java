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

package com.liferay.layout.reports.web.internal.display.context;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import javax.portlet.PortletRequest;

/**
 * @author Cristina González
 */
public class LayoutReportsGooglePageSpeedDisplayContext {

	public LayoutReportsGooglePageSpeedDisplayContext(
		boolean enable, PortletRequest portletRequest) {

		_enable = enable;

		_portletRequest = portletRequest;

		_unicodeProperties = _getUnicodeProperties(_portletRequest);
	}

	public boolean isEnabled() {
		return PropertiesParamUtil.getBoolean(
			_unicodeProperties, _portletRequest, "googlePageSpeedEnabled",
			_enable);
	}

	private UnicodeProperties _getUnicodeProperties(
		PortletRequest portletRequest) {

		Group group = (Group)portletRequest.getAttribute("site.liveGroup");

		if (group != null) {
			return group.getTypeSettingsProperties();
		}

		return new UnicodeProperties();
	}

	private final boolean _enable;
	private final PortletRequest _portletRequest;
	private final UnicodeProperties _unicodeProperties;

}