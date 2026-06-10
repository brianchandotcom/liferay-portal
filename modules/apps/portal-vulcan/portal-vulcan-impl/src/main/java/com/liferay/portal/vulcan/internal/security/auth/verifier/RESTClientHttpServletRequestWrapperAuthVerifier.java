/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.security.auth.verifier;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.internal.template.servlet.RESTClientHttpServletRequestWrapper;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Properties;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "auth.verifier.RESTClientHttpServletRequestWrapperAuthVerifier.urls.includes=/o/*",
	service = AuthVerifier.class
)
public class RESTClientHttpServletRequestWrapperAuthVerifier
	implements AuthVerifier {

	@Override
	public String getAuthType() {
		return RESTClientHttpServletRequestWrapper.class.getName();
	}

	@Override
	public AuthVerifierResult verify(
		AccessControlContext accessControlContext, Properties properties) {

		AuthVerifierResult authVerifierResult = new AuthVerifierResult();

		HttpServletRequest httpServletRequest =
			accessControlContext.getRequest();

		Object attribute = httpServletRequest.getAttribute(
			RESTClientHttpServletRequestWrapper.class.getName());

		if (attribute == null) {
			return authVerifierResult;
		}

		User user = (User)httpServletRequest.getAttribute(WebKeys.USER);

		if ((user == null) || user.isGuestUser()) {
			return authVerifierResult;
		}

		authVerifierResult.setState(AuthVerifierResult.State.SUCCESS);
		authVerifierResult.setUserId(user.getUserId());

		return authVerifierResult;
	}

}