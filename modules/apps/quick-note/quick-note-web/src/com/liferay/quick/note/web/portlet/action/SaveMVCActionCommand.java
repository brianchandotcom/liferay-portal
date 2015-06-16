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

package com.liferay.quick.note.web.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.StrictPortletPreferencesImpl;
import com.liferay.quick.note.web.constants.QuickNotePortletKeys;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alexander Chow
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + QuickNotePortletKeys.QUICK_NOTE,
		"mvc.command.name=save"
	},
	service = MVCActionCommand.class
)
public class SaveMVCActionCommand implements MVCActionCommand {

	@Override
	public boolean processCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			PortletPermissionUtil.check(
				themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
				themeDisplay.getPpid(), ActionKeys.CONFIGURATION);

			PortletPreferences portletPreferences =
				PortletPreferencesFactoryUtil.getStrictPortletSetup(
					themeDisplay.getLayout(), themeDisplay.getPpid());

			if (portletPreferences instanceof StrictPortletPreferencesImpl) {
				throw new PrincipalException();
			}

			String color = ParamUtil.getString(portletRequest, "color");
			String data = ParamUtil.getString(portletRequest, "data");

			if (Validator.isNotNull(color)) {
				portletPreferences.setValue("color", color);
			}

			if (Validator.isNotNull(data)) {
				portletPreferences.setValue("data", data);
			}

			portletPreferences.store();
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		return true;
	}

}