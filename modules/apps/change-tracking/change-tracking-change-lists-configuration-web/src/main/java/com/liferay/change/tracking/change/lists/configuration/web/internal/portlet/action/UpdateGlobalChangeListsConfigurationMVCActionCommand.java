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

package com.liferay.change.tracking.change.lists.configuration.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CTPortletKeys.CHANGE_LISTS_CONFIGURATION,
		"mvc.command.name=/change_lists/update_global_change_lists_configuration"
	},
	service = MVCActionCommand.class
)
public class UpdateGlobalChangeListsConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		boolean enableChangeLists = ParamUtil.getBoolean(
			actionRequest, "enableChangeLists");

		if (enableChangeLists) {
			_ctPreferencesLocalService.getCTPreferences(
				themeDisplay.getCompanyId(), 0);
		}
		else {
			CTPreferences ctPreferences =
				_ctPreferencesLocalService.fetchCTPreferences(
					themeDisplay.getCompanyId(), 0);

			if (ctPreferences != null) {
				_ctPreferencesLocalService.deleteCTPreferences(ctPreferences);
			}
		}

		boolean redirectToOverview = ParamUtil.getBoolean(
			actionRequest, "redirectToOverview");

		if (redirectToOverview) {
			hideDefaultSuccessMessage(actionRequest);

			SessionMessages.add(
				_portal.getHttpServletRequest(actionRequest),
				"requestProcessed",
				LanguageUtil.get(
					themeDisplay.getLocale(),
					"the-configuration-has-been-saved"));

			PortletURL portletURL = PortletURLFactoryUtil.create(
				actionRequest, CTPortletKeys.CHANGE_LISTS,
				PortletRequest.RENDER_PHASE);

			sendRedirect(actionRequest, actionResponse, portletURL.toString());
		}
		else {
			SessionMessages.add(
				actionRequest, "requestProcessed",
				LanguageUtil.get(
					themeDisplay.getLocale(),
					"the-configuration-has-been-saved"));
		}
	}

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private Portal _portal;

}