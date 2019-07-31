/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.document.library.opener.onedrive.web.internal.servlet;

import com.liferay.document.library.opener.oauth.OAuth2State;
import com.liferay.document.library.opener.onedrive.web.internal.oauth.OAuth2Manager;
import com.liferay.document.library.opener.onedrive.web.internal.oauth.OAuth2StateUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Optional;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.document.library.opener.onedrive.web.internal.servlet.OneDriveOAuth2Servlet",
		"osgi.http.whiteboard.servlet.pattern=/document_library/onedrive/oauth2",
		"servlet.init.httpMethods=GET,POST"
	},
	service = Servlet.class
)
public class OneDriveOAuth2Servlet extends HttpServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		Optional<OAuth2State> oAuth2StateOptional =
			OAuth2StateUtil.getOAuth2StateOptional(
				_portal.getOriginalServletRequest(httpServletRequest));

		OAuth2State oAuth2State = oAuth2StateOptional.orElseThrow(
			() -> new IllegalStateException(
				"Authorization state is not initialized"));

		if (!OAuth2StateUtil.isValid(oAuth2State, httpServletRequest)) {
			OAuth2StateUtil.cleanUp(httpServletRequest);

			httpServletResponse.sendRedirect(oAuth2State.getFailureURL());
		}
		else {
			_requestAccessToken(
				httpServletRequest, httpServletResponse, oAuth2State);
		}
	}

	@Override
	protected void doPost(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		doGet(httpServletRequest, httpServletResponse);
	}

	private void _requestAccessToken(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, OAuth2State oAuth2State)
		throws IOException {

		String code = ParamUtil.getString(httpServletRequest, "code");

		if (Validator.isNull(code)) {
			OAuth2StateUtil.cleanUp(httpServletRequest);
			httpServletResponse.sendRedirect(oAuth2State.getFailureURL());
		}
		else {
			try {
				_oAuth2Manager.createAccessToken(
					_portal.getCompanyId(httpServletRequest),
					oAuth2State.getUserId(), code,
					_portal.getPortalURL(httpServletRequest));
			}
			catch (Exception pe) {
				throw new IOException(pe);
			}

			OAuth2StateUtil.cleanUp(httpServletRequest);

			httpServletResponse.sendRedirect(oAuth2State.getSuccessURL());
		}
	}

	private static final long serialVersionUID = 7759897747401129852L;

	@Reference
	private OAuth2Manager _oAuth2Manager;

	@Reference
	private Portal _portal;

}