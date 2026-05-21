/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.admin.web.internal.portlet.action;

import com.liferay.oauth.client.constants.OAuthClientAdminPortletKeys;
import com.liferay.oauth.client.persistence.service.OAuthClientPRLocalMetadataService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;
import jakarta.portlet.PortletException;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge García Jiménez
 */
@Component(
	property = {
		"jakarta.portlet.name=" + OAuthClientAdminPortletKeys.OAUTH_CLIENT_ADMIN,
		"mvc.command.name=/oauth_client_admin/delete_oauth_client_pr_local_metadata"
	},
	service = MVCActionCommand.class
)
public class DeleteOAuthClientPRLocalMetadataMVCActionCommand
	implements MVCActionCommand {

	@Override
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		try {
			long oAuthClientPRLocalMetadataId = ParamUtil.getLong(
				actionRequest, "oAuthClientPRLocalMetadataId");

			long[] oAuthClientPRLocalMetadataIds = null;

			if (oAuthClientPRLocalMetadataId > 0) {
				oAuthClientPRLocalMetadataIds = new long[] {
					oAuthClientPRLocalMetadataId
				};
			}
			else {
				oAuthClientPRLocalMetadataIds = StringUtil.split(
					ParamUtil.getString(
						actionRequest, "oAuthClientPRLocalMetadataIds"),
					0L);
			}

			for (long id : oAuthClientPRLocalMetadataIds) {
				_oAuthClientPRLocalMetadataService.
					deleteOAuthClientPRLocalMetadata(id);
			}
		}
		catch (PortalException portalException) {
			if (_log.isInfoEnabled()) {
				_log.info(portalException);
			}

			SessionErrors.add(actionRequest, portalException.getClass());
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		try {
			if (Validator.isNotNull(redirect)) {
				actionResponse.sendRedirect(redirect);
			}
			else {
				actionResponse.setRenderParameter(
					"navigation", "oauth-client-pr-local-metadata");
			}
		}
		catch (IOException ioException) {
			throw new PortletException(ioException);
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeleteOAuthClientPRLocalMetadataMVCActionCommand.class);

	@Reference
	private OAuthClientPRLocalMetadataService
		_oAuthClientPRLocalMetadataService;

}