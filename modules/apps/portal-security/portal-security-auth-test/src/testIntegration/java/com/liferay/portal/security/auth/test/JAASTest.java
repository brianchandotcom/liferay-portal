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

package com.liferay.portal.security.auth.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.jaas.PortalPrincipal;
import com.liferay.portal.kernel.security.jaas.PortalRole;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.security.jaas.JAASHelper;
import com.liferay.portal.servlet.filters.absoluteredirects.AbsoluteRedirectsResponse;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.security.Principal;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class JAASTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_jaasAuthType = PropsValues.PORTAL_JAAS_AUTH_TYPE;
		_jaasEnabled = PropsValues.PORTAL_JAAS_ENABLE;

		PropsValues.PORTAL_JAAS_ENABLE = true;

		Configuration.setConfiguration(new JAASConfiguration());
	}

	@AfterClass
	public static void tearDownClass() {
		Configuration.setConfiguration(null);

		PropsValues.PORTAL_JAAS_ENABLE = _jaasEnabled;
	}

	@Before
	public void setUp() throws Exception {
		_user = TestPropsValues.getUser();
	}

	@After
	public void tearDown() {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "PORTAL_JAAS_AUTH_TYPE", _jaasAuthType);
	}

	@Test
	public void testGetUser() throws Exception {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "PORTAL_JAAS_AUTH_TYPE", "screenName");

		final IntegerWrapper counter = new IntegerWrapper();

		JAASHelper jaasHelper = JAASHelper.getInstance();

		JAASHelper.setInstance(
			new JAASHelper() {

				@Override
				protected long doGetJaasUserId(long companyId, String name)
					throws PortalException {

					try {
						return super.doGetJaasUserId(companyId, name);
					}
					finally {
						counter.increment();
					}
				}

			});

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(
				ServletContextPool.get(StringPool.BLANK), HttpMethods.GET,
				StringPool.SLASH);

		mockHttpServletRequest.setRemoteUser(
			String.valueOf(_user.getScreenName()));

		try {
			User user = PortalUtil.getUser(mockHttpServletRequest);

			Assert.assertEquals(1, counter.getValue());
			Assert.assertEquals(_user.getUserId(), user.getUserId());

			user = PortalUtil.getUser(mockHttpServletRequest);

			Assert.assertEquals(1, counter.getValue());
			Assert.assertEquals(_user.getUserId(), user.getUserId());
		}
		finally {
			JAASHelper.setInstance(jaasHelper);
		}
	}

	@Test
	public void testLoginEmailAddressWithEmailAddress() throws Exception {
		_testLogin(_user.getEmailAddress(), "emailAddress");
	}

	@Test
	public void testLoginEmailAddressWithLogin() throws Exception {
		_testLogin(_user.getEmailAddress(), "login");
	}

	@Test
	public void testLoginEmailAddressWithScreenName() throws Exception {
		_testLoginFail(_user.getEmailAddress(), "screenName");
	}

	@Test
	public void testLoginEmailAddressWithUserId() throws Exception {
		_testLoginFail(_user.getEmailAddress(), "userId");
	}

	@Test
	public void testLoginScreenNameWithEmailAddress() throws Exception {
		_testLoginFail(_user.getScreenName(), "emailAddress");
	}

	@Test
	public void testLoginScreenNameWithLogin() throws Exception {
		_testLoginFail(_user.getScreenName(), "login");
	}

	@Test
	public void testLoginScreenNameWithScreenName() throws Exception {
		_testLogin(_user.getScreenName(), "screenName");
	}

	@Test
	public void testLoginScreenNameWithUserId() throws Exception {
		_testLoginFail(_user.getScreenName(), "userId");
	}

	@Test
	public void testLoginUserIdWithEmailAddress() throws Exception {
		_testLoginFail(String.valueOf(_user.getUserId()), "emailAddress");
	}

	@Test
	public void testLoginUserIdWithLogin() throws Exception {
		_testLoginFail(String.valueOf(_user.getUserId()), "login");
	}

	@Test
	public void testLoginUserIdWithScreenName() throws Exception {
		_testLoginFail(String.valueOf(_user.getUserId()), "screenName");
	}

	@Test
	public void testLoginUserIdWithUserId() throws Exception {
		_testLogin(String.valueOf(_user.getUserId()), "userId");
	}

	@Test
	public void testProcessLoginEvents() throws Exception {
		Date lastLoginDate = _user.getLastLoginDate();

		ServletContext servletContext = ServletContextPool.get(
			StringPool.BLANK);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(
				servletContext, HttpMethods.GET, StringPool.SLASH);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		mockHttpServletRequest.setRemoteUser(String.valueOf(_user.getUserId()));
		mockHttpServletRequest.setAttribute(
			AbsoluteRedirectsResponse.class.getName(), new Object());

		JAASAction preJAASAction = new JAASAction();
		JAASAction postJAASAction = new JAASAction();

		try {
			EventsProcessorUtil.registerEvent(
				PropsKeys.LOGIN_EVENTS_PRE, preJAASAction);
			EventsProcessorUtil.registerEvent(
				PropsKeys.LOGIN_EVENTS_POST, postJAASAction);

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher("/c");

			requestDispatcher.include(
				mockHttpServletRequest, mockHttpServletResponse);

			Assert.assertTrue(preJAASAction.isRan());
			Assert.assertTrue(postJAASAction.isRan());

			_user = _userLocalService.getUser(_user.getUserId());

			Assert.assertFalse(lastLoginDate.after(_user.getLastLoginDate()));
		}
		finally {
			EventsProcessorUtil.unregisterEvent(
				PropsKeys.LOGIN_EVENTS_PRE, postJAASAction);
			EventsProcessorUtil.unregisterEvent(
				PropsKeys.LOGIN_EVENTS_POST, postJAASAction);
		}
	}

	private LoginContext _getLoginContext(String name, String password)
		throws Exception {

		return new LoginContext(
			"PortalRealm", new JAASCallbackHandler(name, password));
	}

	private void _testLogin(String name, String authType) throws Exception {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "PORTAL_JAAS_AUTH_TYPE", authType);

		LoginContext loginContext = _getLoginContext(name, _user.getPassword());

		loginContext.login();

		_validateSubject(loginContext.getSubject(), name);
	}

	private void _testLoginFail(String name, String authType) throws Exception {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "PORTAL_JAAS_AUTH_TYPE", authType);

		LoginContext loginContext = _getLoginContext(name, _user.getPassword());

		try {
			loginContext.login();

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	private void _validateSubject(Subject subject, String userIdString) {
		Assert.assertNotNull(subject);

		Set<Principal> userPrincipals = subject.getPrincipals();

		Assert.assertNotNull(userPrincipals);

		Iterator<Principal> iterator = userPrincipals.iterator();

		Assert.assertTrue(iterator.hasNext());

		while (iterator.hasNext()) {
			Principal principal = iterator.next();

			if (principal instanceof PortalRole) {
				PortalRole portalRole = (PortalRole)principal;

				Assert.assertEquals("users", portalRole.getName());
			}
			else {
				PortalPrincipal portalPrincipal = (PortalPrincipal)principal;

				Assert.assertEquals(userIdString, portalPrincipal.getName());
			}
		}
	}

	private static String _jaasAuthType;
	private static Boolean _jaasEnabled;

	private User _user;

	@Inject
	private UserLocalService _userLocalService;

	private static class JAASAction extends Action {

		public boolean isRan() {
			return _ran;
		}

		@Override
		public void run(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

			_ran = true;
		}

		private boolean _ran;

	}

	private static class JAASCallbackHandler implements CallbackHandler {

		public JAASCallbackHandler(String name, String password) {
			_name = name;
			_password = password;
		}

		@Override
		public void handle(Callback[] callbacks)
			throws UnsupportedCallbackException {

			for (Callback callback : callbacks) {
				if (callback instanceof NameCallback) {
					NameCallback nameCallback = (NameCallback)callback;

					nameCallback.setName(_name);
				}
				else if (callback instanceof PasswordCallback) {
					String password = GetterUtil.getString(_password);

					PasswordCallback passwordCallback =
						(PasswordCallback)callback;

					passwordCallback.setPassword(password.toCharArray());
				}
				else {
					throw new UnsupportedCallbackException(callback);
				}
			}
		}

		private final String _name;
		private final String _password;

	}

	private static class JAASConfiguration extends Configuration {

		@Override
		public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
			AppConfigurationEntry[] appConfigurationEntries =
				new AppConfigurationEntry[1];

			Map<String, Object> options = new HashMap<>();

			options.put("debug", Boolean.TRUE);

			appConfigurationEntries[0] = new AppConfigurationEntry(
				"com.liferay.portal.kernel.security.jaas.PortalLoginModule",
				LoginModuleControlFlag.REQUIRED, options);

			return appConfigurationEntries;
		}

	}

}