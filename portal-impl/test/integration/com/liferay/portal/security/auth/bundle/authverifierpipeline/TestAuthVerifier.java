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

package com.liferay.portal.security.auth.bundle.authverifierpipeline;

import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"service.ranking:Integer=" + Integer.MAX_VALUE,
		"urls.includes=/TestAuthVerifier/*,/TestAuthVerifierTest/*"
	}
)
public class TestAuthVerifier implements AuthVerifier {

	@Override
	public String getAuthType() {
		return HttpServletRequest.BASIC_AUTH;
	}

	@Override
	public AuthVerifierResult verify(
		AccessControlContext accessControlContext, Properties properties) {

		AuthVerifierResult authVerifierResult = new AuthVerifierResult();

		authVerifierResult.setPassword("best_password_ever");

		Map<String, Object> settings = new HashMap<>();

		settings.put("auth.type", HttpServletRequest.BASIC_AUTH);

		authVerifierResult.setSettings(settings);

		authVerifierResult.setState(AuthVerifierResult.State.SUCCESS);
		authVerifierResult.setUserId(1);

		return authVerifierResult;
	}

}