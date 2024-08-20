/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.auth;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierConfiguration;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.security.auth.registry.AuthVerifierRegistry;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PortalImpl;

import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

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

	@Before
	public void setUp() throws Exception {
		_setUpAuthVerifiers();
		_setUpAuthVerifiersConfiguration();
		_setUpAuthVerifierRegistry();
		_setUpPortalUtil();
		_setUpUserLocalServiceUtil();
	}

	@After
	public void tearDown() {
		_authVerifierRegistryMockedStatic.close();
		_userLocalServiceUtilMockedStatic.close();
	}

	@Test
	public void testVerifyRequest() throws PortalException {
		String contextPath = "";
		String includeURLs = StringBundler.concat(
			_BASE_URL, "/regular/*,", _BASE_URL, "/legacy*");

		String legacyRequestURI = contextPath + _BASE_URL + "/legacy/Hello";
		String regularRequestURI = contextPath + _BASE_URL + "/regular/Hello";

		AuthVerifierResult.State expectedState =
			AuthVerifierResult.State.SUCCESS;

		_assertAuthVerifierResult(
			contextPath, includeURLs, legacyRequestURI, expectedState);
		_assertAuthVerifierResult(
			contextPath, includeURLs, regularRequestURI, expectedState);
	}

	@Test
	public void testVerifyRequestConsidersAuthVerifiersOrder()
		throws PortalException {

		String contextPath = "";
		String includeURLs = StringBundler.concat(
			_BASE_URL, "/regular/*,", _BASE_URL, "/legacy*");

		String legacyRequestURI = contextPath + _BASE_URL + "/legacy/Hello";
		String regularRequestURI = contextPath + _BASE_URL + "/regular/Hello";

		AuthVerifierResult.State expectedState =
			AuthVerifierResult.State.UNSUCCESSFUL;

		Mockito.when(
			AuthVerifierRegistry.getAuthVerifiersByPriority()
		).thenReturn(
			ListUtil.fromArray(_authVerifier2, _authVerifier1)
		);

		_assertAuthVerifierResult(
			contextPath, includeURLs, legacyRequestURI, expectedState);
		_assertAuthVerifierResult(
			contextPath, includeURLs, regularRequestURI, expectedState);
	}

	@Test
	public void testVerifyRequestWithContextPath() throws PortalException {
		String contextPath = "/abc";
		String includeURLs = StringBundler.concat(
			_BASE_URL, "/regular/*,", _BASE_URL, "/legacy*");

		String requestURI = contextPath + _BASE_URL + "/regular/Hello";

		AuthVerifierResult.State expectedState =
			AuthVerifierResult.State.SUCCESS;

		_assertAuthVerifierResult(
			contextPath, includeURLs, requestURI, expectedState);
	}

	@Test
	public void testVerifyRequestWithContextPathNotAffectedByPortalProxyPath()
		throws PortalException {

		String contextPath = "/abc";
		String includeURLs = StringBundler.concat(
			_BASE_URL, "/regular/*,", _BASE_URL, "/legacy*");

		String requestURI = contextPath + _BASE_URL + "/regular/Hello";

		AuthVerifierResult.State expectedState =
			AuthVerifierResult.State.SUCCESS;

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"PORTAL_PROXY_PATH", "/proxy")) {

			_setUpPortalUtil();

			_assertAuthVerifierResult(
				contextPath, includeURLs, requestURI, expectedState);
		}
	}

	@Test
	public void testVerifyRequestWithNonmatchingRequestURI()
		throws PortalException {

		String contextPath = "";
		String includeURLs = StringBundler.concat(
			_BASE_URL, "/regular/*,", _BASE_URL, "/legacy*");

		String requestURI = contextPath + _BASE_URL + "/non/matching";

		AuthVerifierResult.State expectedState =
			AuthVerifierResult.State.UNSUCCESSFUL;

		_assertAuthVerifierResult(
			contextPath, includeURLs, requestURI, expectedState);
	}

	private void _assertAuthVerifierResult(
			String contextPath, String includeURLs, String requestURI,
			AuthVerifierResult.State expectedState)
		throws PortalException {

		AuthVerifierResult authVerifierResult = _verifyRequest(
			contextPath, includeURLs, requestURI);

		Assert.assertSame(expectedState, authVerifierResult.getState());
	}

	private AuthVerifier _newAuthVerifier(
		AuthVerifierResult.State state, int identifier) {

		AuthVerifierResult authVerifierResult = new AuthVerifierResult();

		authVerifierResult.setSettings(new HashMap<>());
		authVerifierResult.setState(state);

		return (AuthVerifier)ProxyUtil.newProxyInstance(
			AuthVerifier.class.getClassLoader(),
			new Class<?>[] {AuthVerifier.class},
			(proxy, method, args) -> {
				if (Objects.equals(method.getName(), "equals")) {
					return proxy.hashCode() == args[0].hashCode();
				}
				else if (Objects.equals(method.getName(), "hashCode")) {
					return identifier;
				}
				else if (Objects.equals(method.getName(), "verify")) {
					return authVerifierResult;
				}

				return null;
			});
	}

	private void _setUpAuthVerifierRegistry() {
		Mockito.when(
			AuthVerifierRegistry.getAuthVerifier(
				_authVerifierConfiguration1.getAuthVerifierClassName())
		).thenReturn(
			_authVerifier1
		);

		Mockito.when(
			AuthVerifierRegistry.getAuthVerifier(
				_authVerifierConfiguration2.getAuthVerifierClassName())
		).thenReturn(
			_authVerifier2
		);

		Mockito.when(
			AuthVerifierRegistry.getAuthVerifiersByPriority()
		).thenReturn(
			ListUtil.fromArray(_authVerifier1, _authVerifier2)
		);
	}

	private void _setUpAuthVerifiers() {
		_authVerifier1 = _newAuthVerifier(AuthVerifierResult.State.SUCCESS, 1);
		_authVerifier2 = _newAuthVerifier(
			AuthVerifierResult.State.UNSUCCESSFUL, 2);
	}

	private void _setUpAuthVerifiersConfiguration() {
		_authVerifierConfiguration1 = new AuthVerifierConfiguration() {
			{
				setAuthVerifierClassName("classname1");
			}
		};

		_authVerifierConfiguration2 = new AuthVerifierConfiguration() {
			{
				setAuthVerifierClassName("classname2");
			}
		};
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(
			new PortalImpl() {

				@Override
				public long getCompanyId(
					HttpServletRequest httpServletRequest) {

					return 0;
				}

			});
	}

	private void _setUpUserLocalServiceUtil() throws Exception {
		User user = new UserImpl();

		user.setStatus(WorkflowConstants.STATUS_APPROVED);

		Mockito.when(
			UserLocalServiceUtil.fetchUser(Mockito.anyLong())
		).thenReturn(
			user
		);

		Mockito.when(
			UserLocalServiceUtil.getGuestUserId(Mockito.anyLong())
		).thenReturn(
			user.getUserId()
		);
	}

	private AuthVerifierResult _verifyRequest(
			String contextPath, String includeURLs, String requestURI)
		throws PortalException {

		Properties properties = new Properties();

		properties.put("urls.includes", includeURLs);

		_authVerifierConfiguration1.setProperties(properties);
		_authVerifierConfiguration2.setProperties(properties);

		AuthVerifierPipeline authVerifierPipeline = new AuthVerifierPipeline(
			ListUtil.fromArray(
				_authVerifierConfiguration1, _authVerifierConfiguration2),
			contextPath);

		AccessControlContext accessControlContext = new AccessControlContext();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(new MockServletContext());

		mockHttpServletRequest.setRequestURI(requestURI);

		accessControlContext.setRequest(mockHttpServletRequest);

		return authVerifierPipeline.verifyRequest(accessControlContext);
	}

	private static final String _BASE_URL = "/TestAuthVerifier";

	private AuthVerifier _authVerifier1;
	private AuthVerifier _authVerifier2;
	private AuthVerifierConfiguration _authVerifierConfiguration1;
	private AuthVerifierConfiguration _authVerifierConfiguration2;
	private final MockedStatic<AuthVerifierRegistry>
		_authVerifierRegistryMockedStatic = Mockito.mockStatic(
			AuthVerifierRegistry.class);
	private final MockedStatic<UserLocalServiceUtil>
		_userLocalServiceUtilMockedStatic = Mockito.mockStatic(
			UserLocalServiceUtil.class);

}