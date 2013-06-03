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
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.jaas.PortalPrincipal;
import com.liferay.portal.kernel.security.jaas.PortalRole;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.jaas.JAASHelper;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.servlet.MainServlet;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.TestPropsValues;

import java.io.IOException;

import java.lang.reflect.Field;

import java.security.Principal;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public void setup() throws Exception {
		_jaasEnabledField = ReflectionUtil.getDeclaredField(
			PropsValues.class, "PORTAL_JAAS_ENABLE");

		_jaasEnabled = (Boolean)_jaasEnabledField.get(null);

		_jaasEnabledField.set(null, true);

		_jaasAuthTypeField = ReflectionUtil.getDeclaredField(
			PropsValues.class, "PORTAL_JAAS_AUTH_TYPE");

		_jaasAuthType = (String)_jaasAuthTypeField.get(null);

		Configuration.setConfiguration(new JAASConfiguration());
	}

	@Test
	public void login_emailAddress_jaasAuthType_emailAddress()
		throws Exception {

		_jaasAuthTypeField.set(null, "emailAddress");

		User user = TestPropsValues.getUser();
		String emailAddress = user.getEmailAddress();
		String userIdString = String.valueOf(user.getUserId());

		LoginContext loginContext = new LoginContext(
			_PORTAL_REALM, new JAASCallbackHandler(
				emailAddress, user.getPassword()));

		try {
			loginContext.login();
		}
		catch (Exception e) {
			Assert.fail();
		}

		validateSubject(loginContext.getSubject(), userIdString);
	}

	@Test
	public void login_emailAddress_jaasAuthType_login() throws Exception {
		_jaasAuthTypeField.set(null, "login");

		User user = TestPropsValues.getUser();
		String emailAddress = user.getEmailAddress();
		String userIdString = String.valueOf(user.getUserId());

		LoginContext loginContext = new LoginContext(
			_PORTAL_REALM, new JAASCallbackHandler(
				emailAddress, user.getPassword()));

		try {
			loginContext.login();
		}
		catch (Exception e) {
			Assert.fail();
		}

		validateSubject(loginContext.getSubject(), userIdString);
	}

	@Test
	public void login_emailAddress_jaasAuthType_screenName() throws Exception {
		_jaasAuthTypeField.set(null, "screenName");

		User user = TestPropsValues.getUser();
		String emailAddress = user.getEmailAddress();

		LoginContext loginContext = new LoginContext(
			_PORTAL_REALM, new JAASCallbackHandler(
				emailAddress, user.getPassword()));

		try {
			loginContext.login();

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void login_emailAddress_jaasAuthType_userId() throws Exception {
		_jaasAuthTypeField.set(null, "userId");

		User user = TestPropsValues.getUser();
		String emailAddress = user.getEmailAddress();

		LoginContext loginContext = new LoginContext(
			_PORTAL_REALM, new JAASCallbackHandler(
				emailAddress, user.getPassword()));

		try {
			loginContext.login();

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void login_screenName_jaasAuthType_emailAddress() throws Exception {
		_jaasAuthTypeField.set(null, "emailAddress");

		User user = TestPropsValues.getUser();
		String screenName = user.getScreenName();

		LoginContext loginContext = new LoginContext(
			_PORTAL_REALM, new JAASCallbackHandler(
				screenName, user.getPassword()));

		try {
			loginContext.login();

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void login_screenName_jaasAuthType_login() throws Exception {
		_jaasAuthTypeField.set(null, "login");

		User user = TestPropsValues.getUser();
		String screenName = user.getScreenName();

		LoginContext loginContext = new LoginContext(
			_PORTAL_REALM, new JAASCallbackHandler(
				screenName, user.getPassword()));

		try {
			loginContext.login();

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void login_screenName_jaasAuthType_screenName() throws Exception {
		_jaasAuthTypeField.set(null, "screenName");

		User user = TestPropsValues.getUser();
		String screenName = user.getScreenName();
		String userIdString = String.valueOf(user.getUserId());

		LoginContext loginContext = new LoginContext(
			_PORTAL_REALM, new JAASCallbackHandler(
				screenName, user.getPassword()));

		try {
			loginContext.login();
		}
		catch (Exception e) {
			Assert.fail();
		}

		validateSubject(loginContext.getSubject(), userIdString);
	}

	@Test
	public void login_screenName_jaasAuthType_userId() throws Exception {
		_jaasAuthTypeField.set(null, "userId");

		User user = TestPropsValues.getUser();
		String screenName = user.getScreenName();

		LoginContext loginContext = new LoginContext(
			_PORTAL_REALM, new JAASCallbackHandler(
				screenName, user.getPassword()));

		try {
			loginContext.login();

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void login_userId_jaasAuthType_emailAddress() throws Exception {
		_jaasAuthTypeField.set(null, "emailAddress");

		User user = TestPropsValues.getUser();
		String userIdString = String.valueOf(user.getUserId());

		LoginContext loginContext = new LoginContext(
			_PORTAL_REALM, new JAASCallbackHandler(
				userIdString, user.getPassword()));

		try {
			loginContext.login();

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void login_userId_jaasAuthType_login() throws Exception {
		_jaasAuthTypeField.set(null, "login");

		User user = TestPropsValues.getUser();
		String userIdString = String.valueOf(user.getUserId());

		LoginContext loginContext = new LoginContext(
			_PORTAL_REALM, new JAASCallbackHandler(
				userIdString, user.getPassword()));

		try {
			loginContext.login();

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void login_userId_jaasAuthType_screenName() throws Exception {
		_jaasAuthTypeField.set(null, "screenName");

		User user = TestPropsValues.getUser();
		String userIdString = String.valueOf(user.getUserId());

		LoginContext loginContext = new LoginContext(
			_PORTAL_REALM, new JAASCallbackHandler(
				userIdString, user.getPassword()));

		try {
			loginContext.login();

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@Test
	public void login_userId_jaasAuthType_userId() throws Exception {
		_jaasAuthTypeField.set(null, "userId");

		User user = TestPropsValues.getUser();
		String userIdString = String.valueOf(user.getUserId());

		LoginContext loginContext = new LoginContext(
			_PORTAL_REALM, new JAASCallbackHandler(
				userIdString, user.getPassword()));

		try {
			loginContext.login();
		}
		catch (Exception e) {
			Assert.fail();
		}

		validateSubject(loginContext.getSubject(), userIdString);
	}

	@Test
	public void firePostAndPreLoginEvents() throws Exception {
		final Counter counter = new Counter();

		JAASHelper originalInstance = JAASHelper.getInstance();
		JAASHelper.setInstance(
			new JAASHelper() {

				@Override
				protected long _getJaasUserId(long companyId, String name)
					throws PortalException, SystemException {

					try {
						return super._getJaasUserId(companyId, name);
					}
					finally {
						counter.count();
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

		MockHttpServletRequest request = new MockHttpServletRequest(
			_mockServletContext, "GET", "/");
		MockHttpServletResponse response = new MockHttpServletResponse();

		User user = TestPropsValues.getUser();
		Date lastLoginDate = user.getLastLoginDate();
		long userId = user.getUserId();
		String userIdString = String.valueOf(userId);

		request.setRemoteUser(userIdString);

		JAASAction preAction = new JAASAction();
		JAASAction postAction = new JAASAction();

		try {
			EventsProcessorUtil.registerEvent(
				PropsKeys.LOGIN_EVENTS_PRE, preAction);
			EventsProcessorUtil.registerEvent(
				PropsKeys.LOGIN_EVENTS_POST, postAction);

			mainServlet.service(request, response);

			Assert.assertEquals(2, counter.getCount());

			Assert.assertTrue(preAction.wasFired());
			Assert.assertTrue(postAction.wasFired());

			// Last login date

			user = UserLocalServiceUtil.getUser(userId);

			Assert.assertTrue(lastLoginDate.before(user.getLastLoginDate()));
		}
		finally {
			EventsProcessorUtil.unregisterEvent(
				PropsKeys.LOGIN_EVENTS_PRE, postAction);
			EventsProcessorUtil.unregisterEvent(
				PropsKeys.LOGIN_EVENTS_POST, postAction);

			JAASHelper.setInstance(originalInstance);
		}
	}

	@After
	public void tearDown() throws Exception {
		Configuration.setConfiguration(null);

		_jaasAuthTypeField.set(null, _jaasAuthType);
		_jaasEnabledField.set(null, _jaasEnabled);
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

	private static final String _PORTAL_REALM = "PortalRealm";

	private Field _jaasAuthTypeField;
	private String _jaasAuthType;
	private Boolean _jaasEnabled;
	private Field _jaasEnabledField;
	private MockServletContext _mockServletContext;

	private class Counter {

		public void count() {
			_count++;
		}

		public int getCount() {
			return _count;
		}

		private int _count = 0;

	}

	private class JAASAction extends Action {

		@Override
		public void run(
				HttpServletRequest request, HttpServletResponse response)
			throws ActionException {

			_wasFired = true;
		}

		public boolean wasFired() {
			return _wasFired;
		}

		private boolean _wasFired = false;

	}

	private class JAASCallbackHandler implements CallbackHandler {

		public JAASCallbackHandler(String username, String password) {
			_username = username;
			_password = password;
		}

		public void handle(Callback[] callbacks)
			throws IOException, UnsupportedCallbackException {

			for (Callback callback : callbacks) {
				if (callback instanceof NameCallback) {
					NameCallback nameCallback = (NameCallback)callback;

					nameCallback.setName(_username);
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

		private String _password;
		private String _username;

	}

	private class JAASConfiguration extends Configuration {

		@Override
		public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
			AppConfigurationEntry[] entries = new AppConfigurationEntry[1];

			Map<String, Object> options = new HashMap<String, Object>();

			options.put("debug", Boolean.TRUE);

			entries[0] = new AppConfigurationEntry(
				"com.liferay.portal.kernel.security.jaas.PortalLoginModule",
				LoginModuleControlFlag.REQUIRED, options);

			return entries;
		}

	}

}