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

package com.liferay.quick.note.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortletPreferencesException;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.StrictPortletPreferencesImpl;
import com.liferay.quick.note.web.internal.constants.QuickNotePortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;

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
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			PortletPermissionUtil.check(
				themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
				themeDisplay.getPpid(), ActionKeys.CONFIGURATION);

			PortletPreferences portletPreferences =
				PortletPreferencesFactoryUtil.getStrictPortletSetup(
					themeDisplay.getLayout(), themeDisplay.getPpid());

			if (portletPreferences instanceof StrictPortletPreferencesImpl) {
				throw new PortletPreferencesException.MustBeStrict(
					themeDisplay.getPpid());
			}

			String color = ParamUtil.getString(actionRequest, "color");
			String data = ParamUtil.getString(actionRequest, "data");

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