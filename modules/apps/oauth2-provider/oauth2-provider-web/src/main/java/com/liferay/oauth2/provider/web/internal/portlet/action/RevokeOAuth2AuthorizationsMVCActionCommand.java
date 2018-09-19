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

import com.liferay.oauth2.provider.service.OAuth2AuthorizationService;
import com.liferay.oauth2.provider.web.internal.constants.OAuth2ProviderPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 * @author Stian Sigvartsen
 */
@Component(
	property = {
		"javax.portlet.name=" + OAuth2ProviderPortletKeys.OAUTH2_ADMIN,
		"javax.portlet.name=" + OAuth2ProviderPortletKeys.OAUTH2_CONNECTED_APPLICATIONS,
		"mvc.command.name=/admin/revoke_oauth2_authorizations",
		"mvc.command.name=/connected_applications/revoke_oauth2_authorizations"
	},
	service = MVCActionCommand.class
)
public class RevokeOAuth2AuthorizationsMVCActionCommand
	implements MVCActionCommand {

	@Override
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

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
				_log.debug(pe, pe);
			}

			SessionErrors.add(actionRequest, pe.getClass());
		}

		String backURL = ParamUtil.get(
			actionRequest, "backURL", StringPool.BLANK);

		if (Validator.isNotNull(backURL)) {
			actionResponse.setRenderParameter("redirect", backURL);
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RevokeOAuth2AuthorizationsMVCActionCommand.class);

	@Reference
	private OAuth2AuthorizationService _oAuth2AuthorizationService;

}