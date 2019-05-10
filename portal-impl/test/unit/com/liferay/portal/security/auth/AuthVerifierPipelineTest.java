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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Peter Fellwock
 */
public class AuthVerifierPipelineTest {

	@Test
	public void testVerifyRequest() throws PortalException {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		Registry registry = RegistryUtil.getRegistry();

		AuthVerifierResult authVerifierResult = new AuthVerifierResult();

		authVerifierResult.setSettings(new HashMap<>());
		authVerifierResult.setState(AuthVerifierResult.State.SUCCESS);

		ServiceRegistration<AuthVerifier> serviceRegistration =
			registry.registerService(
				AuthVerifier.class,
				(AuthVerifier)ProxyUtil.newProxyInstance(
					AuthVerifier.class.getClassLoader(),
					new Class<?>[] {AuthVerifier.class},
					(proxy, method, args) -> {
						if (Objects.equals(method.getName(), "verify")) {
							return authVerifierResult;
						}

						return null;
					}),
				Collections.singletonMap("urls.includes", _BASE_URL + "/*"));

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(new MockServletContext());

		mockHttpServletRequest.setRequestURI(_BASE_URL + "/Hello");

		AccessControlContext accessControlContext = new AccessControlContext();

		accessControlContext.setRequest(mockHttpServletRequest);

		try {
			Assert.assertSame(
				authVerifierResult,
				AuthVerifierPipeline.verifyRequest(accessControlContext));
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	private static final String _BASE_URL = "/TestAuthVerifier";

}