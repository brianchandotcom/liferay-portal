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

package com.liferay.document.library.document.conversion.internal.security.auth.verifier;

import com.liferay.document.library.document.conversion.internal.ImageRequestTokenUtil;
import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Daniel Sanz
 * @author István András Dézsi
 * @author Tomas Polesovsky
 */
public class ImageRequestAuthVerifier implements AuthVerifier {

	@Override
	public String getAuthType() {
		Class<?> clazz = getClass();

		return clazz.getSimpleName();
	}

	@Override
	public AuthVerifierResult verify(
			AccessControlContext accessControlContext, Properties properties)
		throws AuthException {

		AuthVerifierResult authVerifierResult = new AuthVerifierResult();

		HttpServletRequest httpServletRequest =
			accessControlContext.getRequest();

		try {
			String token = ParamUtil.getString(
				httpServletRequest, "auth_token");

			if (Validator.isBlank(token)) {
				return authVerifierResult;
			}

			long userId = ImageRequestTokenUtil.getUserId(token);

			if (userId != 0) {
				authVerifierResult.setState(AuthVerifierResult.State.SUCCESS);
				authVerifierResult.setUserId(userId);
			}
			else {
				authVerifierResult.setState(
					AuthVerifierResult.State.INVALID_CREDENTIALS);
			}

			return authVerifierResult;
		}
		catch (Exception e) {
			throw new AuthException(e);
		}
	}

}