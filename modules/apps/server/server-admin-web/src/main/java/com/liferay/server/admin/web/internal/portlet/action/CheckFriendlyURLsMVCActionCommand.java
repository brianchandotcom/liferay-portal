/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.server.admin.web.internal.portlet.action;

import com.liferay.friendly.url.checker.FriendlyURLPublicMappingChecker;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.server.admin.web.internal.constants.ServerAdminWebKeys;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(
	property = {
		"jakarta.portlet.name=" + PortletKeys.SERVER_ADMIN,
		"mvc.command.name=/server_admin/check_friendly_urls"
	},
	service = MVCActionCommand.class
)
public class CheckFriendlyURLsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		actionRequest.setAttribute(
			ServerAdminWebKeys.FRIENDLY_URL_PUBLIC_MAPPING_CONFLICTS,
			_friendlyURLPublicMappingChecker.getConflicts(
				themeDisplay.getCompanyId()));

		actionResponse.setRenderParameter(
			"mvcRenderCommandName", "/server_admin/view");
		actionResponse.setRenderParameter("tabs1", "friendly-urls");
	}

	@Reference
	private FriendlyURLPublicMappingChecker _friendlyURLPublicMappingChecker;

}