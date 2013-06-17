/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.jaas.PortalPrincipal;
import com.liferay.portal.kernel.security.jaas.PortalRole;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.security.jaas.JAASHelper;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.servlet.MainServlet;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.TestPropsValues;

import java.lang.reflect.Field;

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
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Raymond Augé
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class JAASTest extends MainServletExecutionTestListener {

	@Before
	public void setUp() throws Exception {
		_jaasAuthTypeField = ReflectionUtil.getDeclaredField(
			PropsValues.class, "PORTAL_JAAS_AUTH_TYPE");

		_jaasAuthType = (String)_jaasAuthTypeField.get(null);

		_jaasEnabledField = ReflectionUtil.getDeclaredField(
			PropsValues.class, "PORTAL_JAAS_ENABLE");

		_jaasEnabled = (Boolean)_jaasEnabledField.get(null);

		_jaasEnabledField.set(null, true);

		Configuration.setConfiguration(new JAASConfiguration());

		_user = TestPropsValues.getUser();
	}

	@After
	public void tearDown() throws Exception {
		Configuration.setConfiguration(null);

		_jaasAuthTypeField.set(null, _jaasAuthType);
		_jaasEnabledField.set(null, _jaasEnabled);
	}

	@Test
	public void testLoginEmailAddressWithEmailAddress() throws Exception {
		_jaasAuthTypeField.set(null, "emailAddress");

		LoginContext loginContext = getLoginContext(
			_user.getEmailAddress(), _user.getPassword());

		try {
			loginContext.login();
		}
		catch (Exception e) {
			Assert.fail();
		}

		validateSubject(loginContext.getSubject(), _user.getEmailAddress());
	}

	@Test
	public void testLoginEmailAddressWithLogin() throws Exception {
		_jaasAuthTypeField.set(null, "login");

		LoginContext loginContext = getLoginContext(
			_user.getEmailAddress(), _user.getPassword());

		try {
			loginContext.login();
		}
		catch (Exception e) {
			Assert.fail();
		}

		validateSubject(loginContext.getSubject(), _user.getEmailAddress());
	}

	@Test
	public void testLoginEmailAddressWithScreenName() throws Exception {
		_jaasAuthTypeField.set(null, "screenName");

		LoginContext loginContext = getLoginContext(
			_user.getEmailAddress(), _user.getPassword());

		try {
			loginContext.login();

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testLoginEmailAddressWithUserId() throws Exception {
		_jaasAuthTypeField.set(null, "userId");

		LoginContext loginContext = getLoginContext(
			_user.getEmailAddress(), _user.getPassword());

		try {
			loginContext.login();

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testLoginScreenNameWithEmailAddress() throws Exception {
		_jaasAuthTypeField.set(null, "emailAddress");

		LoginContext loginContext = getLoginContext(
			_user.getScreenName(), _user.getPassword());

		try {
			loginContext.login();

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testLoginScreenNameWithLogin() throws Exception {
		_jaasAuthTypeField.set(null, "login");

		LoginContext loginContext = getLoginContext(
			_user.getScreenName(), _user.getPassword());

		try {
			loginContext.login();

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testLoginScreenNameWithScreenName() throws Exception {
		_jaasAuthTypeField.set(null, "screenName");

		LoginContext loginContext = getLoginContext(
			_user.getScreenName(), _user.getPassword());

		try {
			loginContext.login();
		}
		catch (Exception e) {
			Assert.fail();
		}

		validateSubject(loginContext.getSubject(), _user.getScreenName());
	}

	@Test
	public void testLoginScreenNameWithUserId() throws Exception {
		_jaasAuthTypeField.set(null, "userId");

		LoginContext loginContext = getLoginContext(
			_user.getScreenName(), _user.getPassword());

		try {
			loginContext.login();

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testLoginUserIdWithEmailAddress() throws Exception {
		_jaasAuthTypeField.set(null, "emailAddress");

		LoginContext loginContext = getLoginContext(
			String.valueOf(_user.getUserId()), _user.getPassword());

		try {
			loginContext.login();

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testLoginUserIdWithLogin() throws Exception {
		_jaasAuthTypeField.set(null, "login");

		LoginContext loginContext = getLoginContext(
			String.valueOf(_user.getUserId()), _user.getPassword());

		try {
			loginContext.login();

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testLoginUserIdWithScreenName() throws Exception {
		_jaasAuthTypeField.set(null, "screenName");

		LoginContext loginContext = getLoginContext(
			String.valueOf(_user.getUserId()), _user.getPassword());

		try {
			loginContext.login();

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testLoginUserIdWithUserId() throws Exception {
		_jaasAuthTypeField.set(null, "userId");

		LoginContext loginContext = getLoginContext(
			String.valueOf(_user.getUserId()), _user.getPassword());

		try {
			loginContext.login();
		}
		catch (Exception e) {
			Assert.fail();
		}

		validateSubject(
			loginContext.getSubject(), String.valueOf(_user.getUserId()));
	}

	@Test
	public void testProcessLoginEvents() throws Exception {
		final IntegerWrapper counter = new IntegerWrapper();

		JAASHelper jaasHelper = JAASHelper.getInstance();

		JAASHelper.setInstance(
			new JAASHelper() {

				@Override
				protected long doGetJaasUserId(long companyId, String name)
					throws PortalException, SystemException {

					try {
						return super.doGetJaasUserId(companyId, name);
					}
					finally {
						counter.increment();
					}
				}

			}
		);

		_mockServletContext = new AutoDeployMockServletContext(
			getResourceBasePath(), new FileSystemResourceLoader());

		MockServletConfig mockServletConfig = new MockServletConfig(
			_mockServletContext);

		mainServlet = new MainServlet();

		try {
			mainServlet.init(mockServletConfig);
		}
		catch (ServletException se) {
			throw new RuntimeException(
				"The main servlet could not be initialized");
		}

		Date lastLoginDate = _user.getLastLoginDate();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(
				_mockServletContext, HttpMethods.GET, StringPool.SLASH);

		mockHttpServletRequest.setRemoteUser(String.valueOf(_user.getUserId()));

		JAASAction preJAASAction = new JAASAction();
		JAASAction postJAASAction = new JAASAction();

		try {
			EventsProcessorUtil.registerEvent(
				PropsKeys.LOGIN_EVENTS_PRE, preJAASAction);
			EventsProcessorUtil.registerEvent(
				PropsKeys.LOGIN_EVENTS_POST, postJAASAction);

			mainServlet.service(
				mockHttpServletRequest, new MockHttpServletResponse());

			Assert.assertEquals(2, counter.getValue());
			Assert.assertTrue(preJAASAction.isRan());
			Assert.assertTrue(postJAASAction.isRan());

			_user = UserLocalServiceUtil.getUser(_user.getUserId());

			Assert.assertFalse(lastLoginDate.after(_user.getLastLoginDate()));
		}
		finally {
			EventsProcessorUtil.unregisterEvent(
				PropsKeys.LOGIN_EVENTS_PRE, postJAASAction);
			EventsProcessorUtil.unregisterEvent(
				PropsKeys.LOGIN_EVENTS_POST, postJAASAction);

			JAASHelper.setInstance(jaasHelper);
		}
	}

	protected LoginContext getLoginContext(String name, String password)
		throws Exception {

		return new LoginContext(
			"PortalRealm", new JAASCallbackHandler(name, password));
	}

	protected void validateSubject(Subject subject, String userIdString) {
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

	private String _jaasAuthType;
	private Field _jaasAuthTypeField;
	private Boolean _jaasEnabled;
	private Field _jaasEnabledField;
	private MockServletContext _mockServletContext;
	private User _user;

	private class JAASAction extends Action {

		@Override
		public void run(
			HttpServletRequest request, HttpServletResponse response) {

			_ran = true;
		}

		public boolean isRan() {
			return _ran;
		}

		private boolean _ran;

	}

	private class JAASCallbackHandler implements CallbackHandler {

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

		private String _name;
		private String _password;

	}

	private class JAASConfiguration extends Configuration {

		@Override
		public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
			AppConfigurationEntry[] appConfigurationEntries =
				new AppConfigurationEntry[1];

			Map<String, Object> options = new HashMap<String, Object>();

			options.put("debug", Boolean.TRUE);

			appConfigurationEntries[0] = new AppConfigurationEntry(
				"com.liferay.portal.kernel.security.jaas.PortalLoginModule",
				LoginModuleControlFlag.REQUIRED, options);

			return appConfigurationEntries;
		}

	}

}