/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTRemote;
import com.liferay.change.tracking.service.CTRemoteLocalService;
import com.liferay.change.tracking.web.internal.constants.CTWebKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	property = {
		"jakarta.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/edit_ct_remote"
	},
	service = MVCRenderCommand.class
)
public class EditCTRemoteMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long ctRemoteId = ParamUtil.getLong(renderRequest, "ctRemoteId");

		CTRemote ctRemote = _ctRemoteLocalService.fetchCTRemote(ctRemoteId);

		try {
			if (ctRemote != null) {
				_ctRemoteModelResourcePermission.check(
					themeDisplay.getPermissionChecker(), ctRemote,
					ActionKeys.VIEW);
			}
		}
		catch (Exception exception) {
			SessionErrors.add(renderRequest, exception.getClass());

			return "/publications/error.jsp";
		}

		renderRequest.setAttribute(CTWebKeys.CT_REMOTE, ctRemote);

		return "/publications/edit_ct_remote.jsp";
	}

	@Reference
	private CTRemoteLocalService _ctRemoteLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.change.tracking.model.CTRemote)"
	)
	private ModelResourcePermission<CTRemote> _ctRemoteModelResourcePermission;

}