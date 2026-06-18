/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.admin.web.internal.portlet.action;

import com.liferay.oauth.client.constants.OAuthClientAdminPortletKeys;
import com.liferay.oauth.client.persistence.service.OAuthClientPRLocalMetadataService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

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
	extends BaseTransactionalMVCActionCommand {

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] deleteOAuthClientPRLocalMetadataIds = null;

		long oAuthClientPRLocalMetadataId = ParamUtil.getLong(
			actionRequest, "oAuthClientPRLocalMetadataId");

		if (oAuthClientPRLocalMetadataId > 0) {
			deleteOAuthClientPRLocalMetadataIds = new long[] {
				oAuthClientPRLocalMetadataId
			};
		}
		else {
			deleteOAuthClientPRLocalMetadataIds = ParamUtil.getLongValues(
				actionRequest, "oAuthClientPRLocalMetadataIds");
		}

		for (long deleteOAuthClientPRLocalMetadataId :
				deleteOAuthClientPRLocalMetadataIds) {

			_oAuthClientPRLocalMetadataService.deleteOAuthClientPRLocalMetadata(
				deleteOAuthClientPRLocalMetadataId);
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			sendRedirect(actionRequest, actionResponse, redirect);
		}
		else {
			actionResponse.setRenderParameter(
				"navigation", "oauth-client-pr-local-metadata");
		}
	}

	@Reference
	private OAuthClientPRLocalMetadataService
		_oAuthClientPRLocalMetadataService;

}