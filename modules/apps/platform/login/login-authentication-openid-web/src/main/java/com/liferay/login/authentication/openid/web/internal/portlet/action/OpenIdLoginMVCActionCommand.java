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

package com.liferay.login.authentication.openid.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.openid.OpenId;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.sso.openid.OpenIdServiceException;
import com.liferay.portal.security.sso.openid.OpenIdServiceHandler;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Enables the Sign In portlet to process an OpenID login attempt. When invoked,
 * the following steps are carried out.
 *
 * <ol>
 * <li>
 * Discover the OpenID provider's XRDS document by performing HTTP GET on
 * the user's OpenID URL. This document tells Liferay what the URL of the OpenID
 * provider is.
 * </li>
 * <li>
 * Retrieve the OpenID provider's authentication URL which is provided by
 * the XRDS document and prepare an OpenID authorization request URL. This URL
 * includes a return URL parameter (encoded) which points back to this
 * MVCActionRequest with an additional parameter <code>cmd = read</code> (used
 * in step 7).
 * </li>
 * <li>
 * Search for an existing Liferay Portal user with the user provided OpenID.
 * </li>
 * <li>
 * If found, redirect the browser to the OpenID authentication request URL
 * and wait for the browser to be redirected back to Liferay Portal when all
 * steps repeat. Otherwise, ...
 * </li>
 * <li>
 * Generate a valid Liferay Portal user screen name based on the OpenID
 * and search for an existing Liferay Portal user with a matching screen name.
 * If found, then update the Liferay Portal user’s OpenID to match and redirect
 * the browser to the OpenID authentication request URL. Otherwise, ...
 * </li>
 * <li>
 * Enrich the OpenID authentication request URL with a request for specific
 * attributes (the user's <code>fullname</code> and <code>email</code>). Then
 * redirect the browser to the enriched OpenID authentication request URL.
 * </li>
 * <li>
 * Upon returning from the OpenID provider’s authentication process, the
 * MVCActionCommand finds the URL parameter <code>cmd</code> set to
 * <code>read</code> (see step 2).
 * </li>
 * <li>
 * The request is verified as being from the same OpenID provider.
 * </li>
 * <li>
 * If the attributes requested in step 6 are not found, then the web browser
 * is redirected to the Create Account page where the missing information must
 * be entered before a Liferay Portal user can be created. Otherwise, ...
 * </li>
 * <li>
 * The attributes are used to create a Liferay Portal user and the HTTP
 * session attribute <code>OPEN_ID_LOGIN</code> is set equal to the Liferay
 * Portal user's ID.
 * </li>
 * </ol>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PortletKeys.FAST_LOGIN,
		"javax.portlet.name=" + PortletKeys.LOGIN,
		"mvc.command.name=/login/openid"
	},
	service = MVCActionCommand.class

)
public class OpenIdLoginMVCActionCommand extends BaseMVCActionCommand {

	@Override
	public void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!_openId.isEnabled(themeDisplay.getCompanyId())) {
			throw new PrincipalException.MustBeEnabled(
				themeDisplay.getCompanyId(), OpenId.class.getName());
		}

		if (actionRequest.getRemoteUser() != null) {
			actionResponse.sendRedirect(themeDisplay.getPathMain());

			return;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.READ)) {
				String redirect = _openIdServiceHandler.readResponse(
					themeDisplay, actionRequest);

				if (Validator.isNull(redirect)) {
					redirect = themeDisplay.getURLSignIn();
				}

				redirect = PortalUtil.escapeRedirect(redirect);

				actionResponse.sendRedirect(redirect);
			}
			else {
				_openIdServiceHandler.sendRequest(
					themeDisplay, actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof OpenIdServiceException) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Error communicating with OpenID provider: " +
							e.getMessage());
				}

				SessionErrors.add(actionRequest, e.getClass());
			}
			else if (e instanceof
						UserEmailAddressException.MustNotBeDuplicate) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				_log.error("Error processing the OpenID login", e);

				PortalUtil.sendError(e, actionRequest, actionResponse);
			}
		}
	}

	@Reference(unbind = "-")
	protected void setOpenId(OpenId openId) {
		_openId = openId;
	}

	@Reference(unbind = "-")
	protected void setOpenIdServiceHandler(
		OpenIdServiceHandler openIdServiceHandler) {

		_openIdServiceHandler = openIdServiceHandler;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdLoginMVCActionCommand.class);

	private OpenId _openId;
	private OpenIdServiceHandler _openIdServiceHandler;

}