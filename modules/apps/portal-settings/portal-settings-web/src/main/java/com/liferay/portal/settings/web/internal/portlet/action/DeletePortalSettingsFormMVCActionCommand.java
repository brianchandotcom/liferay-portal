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

package com.liferay.portal.settings.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.settings.portlet.action.PortalSettingsFormContributor;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * @author Michael C. Han
 */
public class DeletePortalSettingsFormMVCActionCommand
	extends BasePortalSettingsFormMVCActionCommand {

	public DeletePortalSettingsFormMVCActionCommand(
		PortletPreferencesLocalService portletPreferencesLocalService,
		PortalSettingsFormContributor portalSettingsFormContributor) {

		super(portalSettingsFormContributor);

		_portletPreferencesLocalService = portletPreferencesLocalService;
	}

	protected void deleteSettings(ThemeDisplay themeDisplay)
		throws PortalException {

		_portletPreferencesLocalService.deletePortletPreferences(
			themeDisplay.getCompanyId(), PortletKeys.PREFS_OWNER_TYPE_COMPANY,
			0, getSettingsId());
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			if (!hasPermissions(actionRequest, actionResponse, themeDisplay)) {
				return;
			}

			deleteSettings(themeDisplay);
		}
		catch (PortalException pe) {
			SessionErrors.add(actionRequest, pe.getClass(), pe);

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (Validator.isNotNull(redirect)) {
				actionResponse.sendRedirect(redirect);
			}
		}
	}

	@Override
	protected void doValidateForm(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
	}

	private final PortletPreferencesLocalService
		_portletPreferencesLocalService;

}