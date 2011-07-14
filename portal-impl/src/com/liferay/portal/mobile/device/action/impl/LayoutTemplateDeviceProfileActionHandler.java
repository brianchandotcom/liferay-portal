/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.mobile.device.action.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.mobile.device.action.DeviceProfileActionHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.mobile.model.DeviceProfileAction;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Edward Han
 */
public class LayoutTemplateDeviceProfileActionHandler
	implements DeviceProfileActionHandler {

	public void applyDeviceAction(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			DeviceProfileAction deviceProfileAction)
		throws PortalException, SystemException {

		UnicodeProperties deviceProfileTypeSettings = deviceProfileAction.
			getTypeSettingsProperties();

		String layoutTemplateId = GetterUtil.get(
			deviceProfileTypeSettings.getProperty(
				LAYOUT_TEMPLATE_ID), StringPool.BLANK);

		ThemeDisplay themeDisplay = (ThemeDisplay) httpServletRequest.
			getAttribute(WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		UnicodeProperties layoutTypeSettings =
			layout.getTypeSettingsProperties();

		layoutTypeSettings.setProperty(
			LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, layoutTemplateId);

		layout.setTypeSettingsProperties(layoutTypeSettings);
	}

	public Collection<String> getPropertyNames() {
		return _propertyNames;
	}

	public String getType() {
		return LayoutTemplateDeviceProfileActionHandler.class.getName();
	}

	private static final String LAYOUT_TEMPLATE_ID = "layoutTemplateId";

	private static Collection<String> _propertyNames;

	static {
		_propertyNames = new ArrayList<String>(1);

		_propertyNames.add(LAYOUT_TEMPLATE_ID);

		_propertyNames = Collections.unmodifiableCollection(_propertyNames);
	}
}