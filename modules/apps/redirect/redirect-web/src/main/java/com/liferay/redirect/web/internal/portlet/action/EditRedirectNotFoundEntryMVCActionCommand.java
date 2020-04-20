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

package com.liferay.redirect.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalService;
import com.liferay.redirect.web.internal.constants.RedirectPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"javax.portlet.name=" + RedirectPortletKeys.REDIRECT,
		"mvc.command.name=/redirect/edit_redirect_not_found_entry"
	},
	service = MVCActionCommand.class
)
public class EditRedirectNotFoundEntryMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletPermissionUtil.check(
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroupId(),
			0, RedirectPortletKeys.REDIRECT, ActionKeys.ACCESS_IN_CONTROL_PANEL,
			true);

		long redirectNotFoundEntryId = ParamUtil.getLong(
			actionRequest, "redirectNotFoundEntryId");

		if (redirectNotFoundEntryId > 0) {
			_redirectNotFoundEntryLocalService.updateRedirectNotFoundEntry(
				redirectNotFoundEntryId,
				ParamUtil.getBoolean(actionRequest, "ignored"));
		}
		else {
			long[] editRedirectNotFoundEntryIds = ParamUtil.getLongValues(
				actionRequest, "rowIds");

			for (long editRedirectNotFoundEntryId :
					editRedirectNotFoundEntryIds) {

				_redirectNotFoundEntryLocalService.updateRedirectNotFoundEntry(
					editRedirectNotFoundEntryId,
					ParamUtil.getBoolean(actionRequest, "ignored"));
			}
		}
	}

	@Reference
	private RedirectNotFoundEntryLocalService
		_redirectNotFoundEntryLocalService;

}