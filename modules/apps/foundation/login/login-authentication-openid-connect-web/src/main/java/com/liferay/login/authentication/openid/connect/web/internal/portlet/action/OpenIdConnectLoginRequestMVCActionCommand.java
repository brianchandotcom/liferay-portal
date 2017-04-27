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

package com.liferay.login.authentication.openid.connect.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnect;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceHandler;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectWebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"auth.token.ignore.mvc.action=true",
		"javax.portlet.name=" + PortletKeys.FAST_LOGIN,
		"javax.portlet.name=" + PortletKeys.LOGIN,
		"mvc.command.name=" + OpenIdConnectWebKeys.OPEN_ID_CONNECT_REQUEST_ACTION_NAME
	},
	service = MVCActionCommand.class

)
public class OpenIdConnectLoginRequestMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	public void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			String openIdConnectProviderName = ParamUtil.getString(
				actionRequest,
				OpenIdConnectWebKeys.OPEN_ID_CONNECT_PROVIDER_NAME);

			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(actionRequest);

			httpServletRequest = _portal.getOriginalServletRequest(
				httpServletRequest);

			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(actionResponse);

			_openIdConnectServiceHandler.requestAuthentication(
				openIdConnectProviderName, httpServletRequest,
				httpServletResponse);
		}
		catch (Exception e) {
			if (e instanceof OpenIdConnectServiceException) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Unable to communicate with OpenID Connect provider: " +
							e.getMessage());
				}

				SessionErrors.add(actionRequest, e.getClass());
			}
			else if (e instanceof
						UserEmailAddressException.MustNotBeDuplicate) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				_log.error("Unable to process the OpenID login", e);

				_portal.sendError(e, actionRequest, actionResponse);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectLoginRequestMVCActionCommand.class);

	@Reference
	private OpenIdConnect _openIdConnect;

	@Reference
	private OpenIdConnectServiceHandler _openIdConnectServiceHandler;

	@Reference
	private Portal _portal;

}