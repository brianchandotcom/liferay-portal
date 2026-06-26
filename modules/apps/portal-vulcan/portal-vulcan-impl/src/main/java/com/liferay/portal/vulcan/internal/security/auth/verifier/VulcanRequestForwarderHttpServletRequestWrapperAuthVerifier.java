/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.security.auth.verifier;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.internal.http.VulcanRequestForwarderHttpServletRequestWrapper;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Properties;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "auth.verifier.VulcanRequestForwarderHttpServletRequestWrapperAuthVerifier.urls.includes=/o/*",
	service = AuthVerifier.class
)
public class VulcanRequestForwarderHttpServletRequestWrapperAuthVerifier
	implements AuthVerifier {

	@Override
	public String getAuthType() {
		Class<?> clazz = getClass();

		return clazz.getSimpleName();
	}

	@Override
	public AuthVerifierResult verify(
		AccessControlContext accessControlContext, Properties properties) {

		AuthVerifierResult authVerifierResult = new AuthVerifierResult();

		HttpServletRequest httpServletRequest =
			accessControlContext.getRequest();

		Object value = httpServletRequest.getAttribute(
			VulcanRequestForwarderHttpServletRequestWrapper.class.getName());

		if (value == null) {
			return authVerifierResult;
		}

		User user = _userLocalService.fetchUser(
			GetterUtil.getLong(
				httpServletRequest.getAttribute(WebKeys.USER_ID)));

		if ((user == null) || user.isGuestUser()) {
			return authVerifierResult;
		}

		authVerifierResult.setState(AuthVerifierResult.State.SUCCESS);
		authVerifierResult.setUserId(user.getUserId());

		return authVerifierResult;
	}

	@Reference
	private UserLocalService _userLocalService;

}