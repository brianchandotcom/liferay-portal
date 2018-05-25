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

package com.liferay.oauth2.provider.web.internal.portlet.action;

import com.liferay.oauth2.provider.service.OAuth2ApplicationService;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationService;
import com.liferay.oauth2.provider.web.constants.ClientProfile;
import com.liferay.oauth2.provider.web.constants.OAuth2ProviderPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 * @author Stian Sigvartsen
 */
@Component(
	configurationPid = "com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration",
	property = {
		"javax.portlet.name=" + OAuth2ProviderPortletKeys.OAUTH2_ADMIN,
		"mvc.command.name=/admin/revoke_oauth2_authorizations"
	}
)
public class RevokeAuthorizationsMVCActionCommand implements MVCActionCommand {

	@Override
	public boolean processAction(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		long[] oAuth2AuthorizationIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "oAuth2AuthorizationIds"), 0L);

		try {
			for (long oAuth2AuthorizationId : oAuth2AuthorizationIds) {
				_oAuth2AuthorizationService.revokeOAuth2Authorization(
					oAuth2AuthorizationId);
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe);
			}

			SessionErrors.add(actionRequest, pe.getClass());
		}

		String backURL = ParamUtil.get(
			actionRequest, "uRLBack", StringPool.BLANK);

		actionResponse.setRenderParameter("redirect", backURL);

		return true;
	}

	protected ClientProfile getClientProfile(int clientProfileId) {
		for (ClientProfile clientProfile : ClientProfile.values()) {
			if (clientProfile.id() == clientProfileId) {
				return clientProfile;
			}
		}

		throw new IllegalArgumentException(
			"No ClientProfile enum constant found with ID " + clientProfileId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RevokeAuthorizationsMVCActionCommand.class);

	@Reference
	private OAuth2ApplicationService _oAuth2ApplicationService;

	@Reference
	private OAuth2AuthorizationService _oAuth2AuthorizationService;

}