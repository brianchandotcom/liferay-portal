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

package com.liferay.portal.mobile.device.rulegroup.action.impl;

import com.liferay.portal.kernel.mobile.device.rulegroup.action.ActionHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Edward Han
 */
public class LayoutTemplateModificationActionHandler implements ActionHandler {

	public static String getHandlerType() {
		return LayoutTemplateModificationActionHandler.class.getName();
	}

	@Override
	public void applyAction(
		MDRAction mdrAction, HttpServletRequest request,
		HttpServletResponse response) {

		UnicodeProperties mdrActionTypeSettingsProperties =
			mdrAction.getTypeSettingsProperties();

		String layoutTemplateId = GetterUtil.getString(
			mdrActionTypeSettingsProperties.getProperty("layoutTemplateId"));

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout.isTypePortlet()) {
			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			layoutTypePortlet.setLayoutTemplateId(0, layoutTemplateId, false);
		}
	}

	@Override
	public Collection<String> getPropertyNames() {
		return _propertyNames;
	}

	@Override
	public String getType() {
		return getHandlerType();
	}

	private static Collection<String> _propertyNames;

	static {
		_propertyNames = new ArrayList<String>(1);

		_propertyNames.add("layoutTemplateId");

		_propertyNames = Collections.unmodifiableCollection(_propertyNames);
	}

}