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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierConfiguration;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.security.auth.registry.AuthVerifierRegistry;
import com.liferay.portal.service.impl.UserLocalServiceImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.registry.collections.ServiceTrackerMapFactory;
import com.liferay.registry.collections.ServiceTrackerMapFactoryUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Peter Fellwock
 */
public class AuthVerifierPipelineTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testVerifyRequest() throws PortalException {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		Registry registry = RegistryUtil.getRegistry();

		ReflectionTestUtil.setFieldValue(
			UserLocalServiceUtil.class, "_service",
			new UserLocalServiceImpl() {

				@Override
				public User fetchUser(long userId) {
					User user = new UserImpl();

					user.setStatus(WorkflowConstants.STATUS_APPROVED);

					return user;
				}

			});

		AuthVerifierResult authVerifierResult = new AuthVerifierResult();

		authVerifierResult.setSettings(new HashMap<>());
		authVerifierResult.setState(AuthVerifierResult.State.SUCCESS);

		AuthVerifierConfiguration authVerifierConfiguration =
			new AuthVerifierConfiguration();

		AuthVerifier authVerifier = (AuthVerifier)ProxyUtil.newProxyInstance(
			AuthVerifier.class.getClassLoader(),
			new Class<?>[] {AuthVerifier.class},
			(proxy, method, args) -> {
				if (Objects.equals(method.getName(), "verify")) {
					return authVerifierResult;
				}

				return null;
			});

		Class<? extends AuthVerifier> authVerifierClass =
			authVerifier.getClass();

		Map<String, Object> propertyMap = Collections.singletonMap(
			"urls.includes",
			StringBundler.concat(
				_BASE_URL, "/regular/*,", _BASE_URL, "/legacy*"));

		Properties properties = new Properties();

		for (Map.Entry<String, Object> entry : propertyMap.entrySet()) {
			properties.setProperty(
				entry.getKey(), String.valueOf(entry.getValue()));
		}

		authVerifierConfiguration.setAuthVerifierClassName(
			authVerifierClass.getName());
		authVerifierConfiguration.setProperties(properties);

		AuthVerifierPipeline authVerifierPipeline = new AuthVerifierPipeline(
			Collections.singletonList(authVerifierConfiguration), "");

		ServiceTrackerMapFactoryUtil.setServiceTrackerMapFactory(
			(ServiceTrackerMapFactory)ProxyUtil.newProxyInstance(
				AuthVerifierPipeline.class.getClassLoader(),
				new Class<?>[] {ServiceTrackerMapFactory.class},
				(proxy, method, args) -> null));

		ReflectionTestUtil.setFieldValue(
			AuthVerifierRegistry.class, "_serviceTrackerMap",
			new ServiceTrackerMap<String, AuthVerifier>() {

				@Override
				public void close() {
				}

				@Override
				public boolean containsKey(String key) {
					return false;
				}

				@Override
				public AuthVerifier getService(String key) {
					if (key.equals(
							authVerifierConfiguration.
								getAuthVerifierClassName())) {

						return authVerifier;
					}

					return null;
				}

				@Override
				public Set<String> keySet() {
					return null;
				}

			});

		ServiceRegistration<AuthVerifier> serviceRegistration =
			registry.registerService(
				AuthVerifier.class, authVerifier, propertyMap);

		AccessControlContext accessControlContext = new AccessControlContext();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(new MockServletContext());

		try {
			mockHttpServletRequest.setRequestURI(_BASE_URL + "/legacy/Hello");

			accessControlContext.setRequest(mockHttpServletRequest);

			Assert.assertSame(
				authVerifierResult,
				authVerifierPipeline.verifyRequest(accessControlContext));

			mockHttpServletRequest.setRequestURI(_BASE_URL + "/regular/Hello");

			accessControlContext.setRequest(mockHttpServletRequest);

			Assert.assertSame(
				authVerifierResult,
				authVerifierPipeline.verifyRequest(accessControlContext));
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	private static final String _BASE_URL = "/TestAuthVerifier";

}