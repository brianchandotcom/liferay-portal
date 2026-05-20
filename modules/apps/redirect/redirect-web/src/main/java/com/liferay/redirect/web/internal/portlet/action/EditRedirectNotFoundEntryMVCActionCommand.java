/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalService;
import com.liferay.redirect.web.internal.constants.RedirectPortletKeys;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"jakarta.portlet.name=" + RedirectPortletKeys.REDIRECT,
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

		long[] editRedirectNotFoundEntryIds = null;

		if (redirectNotFoundEntryId > 0) {
			editRedirectNotFoundEntryIds = new long[] {redirectNotFoundEntryId};
		}
		else {
			editRedirectNotFoundEntryIds = ParamUtil.getLongValues(
				actionRequest, "rowIds");
		}

		boolean ignored = ParamUtil.getBoolean(actionRequest, "ignored");

		for (long editRedirectNotFoundEntryId : editRedirectNotFoundEntryIds) {
			RedirectNotFoundEntry redirectNotFoundEntry =
				_redirectNotFoundEntryLocalService.fetchRedirectNotFoundEntry(
					editRedirectNotFoundEntryId);

			if (redirectNotFoundEntry == null) {
				continue;
			}

			if (redirectNotFoundEntry.getGroupId() !=
					themeDisplay.getScopeGroupId()) {

				throw new PrincipalException.MustHavePermission(
					themeDisplay.getPermissionChecker(),
					Portlet.class.getName(), RedirectPortletKeys.REDIRECT,
					ActionKeys.ACCESS_IN_CONTROL_PANEL);
			}

			_redirectNotFoundEntryLocalService.updateRedirectNotFoundEntry(
				editRedirectNotFoundEntryId, ignored);
		}
	}

	@Reference
	private RedirectNotFoundEntryLocalService
		_redirectNotFoundEntryLocalService;

}