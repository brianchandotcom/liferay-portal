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

package com.liferay.vldap.server.internal.portal.security.auth;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.Authenticator;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.vldap.server.internal.BaseVLDAPTestCase;

import java.lang.reflect.Method;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Jonathan McCann
 */
@RunWith(PowerMockRunner.class)
public class SambaAuthTest extends BaseVLDAPTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpPortalUtil();

		_authenticator = new SambaAuth();

		_expandoBridge = mock(ExpandoBridge.class);

		Class<?> clazz = Class.forName(SambaAuth.class.getName());

		Method setUserLocalServiceMethod = clazz.getDeclaredMethod(
			"setUserLocalService", UserLocalService.class);

		setUserLocalServiceMethod.setAccessible(true);

		setUserLocalServiceMethod.invoke(_authenticator, userLocalService);
	}

	@Test
	public void testAuthenticateByEmailAddress() throws Exception {
		setUpUser();

		int authResult = _authenticator.authenticateByEmailAddress(
			PRIMARY_KEY, "test@liferay.com", "password",
			new HashMap<String, String[]>(), new HashMap<String, String[]>());

		Assert.assertEquals(1, authResult);

		Mockito.verify(
			_expandoBridge, Mockito.times(1)
		).setAttribute(
			"sambaLMPassword", "E52CAC67419A9A224A3B108F3FA6CB6D", false
		);

		Mockito.verify(
			_expandoBridge, Mockito.times(1)
		).setAttribute(
			"sambaNTPassword", "8846F7EAEE8FB117AD06BDD830B7586C", false
		);
	}

	@Test
	public void testAuthenticateByEmailAddressWithNullUser() throws Exception {
		int authResult = _authenticator.authenticateByEmailAddress(
			PRIMARY_KEY, "test@liferay.com", "password",
			new HashMap<String, String[]>(), new HashMap<String, String[]>());

		Assert.assertEquals(1, authResult);

		Mockito.verify(
			_expandoBridge, Mockito.times(0)
		).setAttribute(
			Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean()
		);
	}

	@Test
	public void testAuthenticateByScreenName() throws Exception {
		setUpUser();

		int authResult = _authenticator.authenticateByScreenName(
			PRIMARY_KEY, "test", "password", new HashMap<String, String[]>(),
			new HashMap<String, String[]>());

		Assert.assertEquals(1, authResult);

		Mockito.verify(
			_expandoBridge, Mockito.times(1)
		).setAttribute(
			"sambaLMPassword", "E52CAC67419A9A224A3B108F3FA6CB6D", false
		);

		Mockito.verify(
			_expandoBridge, Mockito.times(1)
		).setAttribute(
			"sambaNTPassword", "8846F7EAEE8FB117AD06BDD830B7586C", false
		);
	}

	@Test
	public void testAuthenticateByScreenNameWithNullUser() throws Exception {
		int authResult = _authenticator.authenticateByScreenName(
			PRIMARY_KEY, "test", "password", new HashMap<String, String[]>(),
			new HashMap<String, String[]>());

		Assert.assertEquals(1, authResult);

		Mockito.verify(
			_expandoBridge, Mockito.times(0)
		).setAttribute(
			Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean()
		);
	}

	@Test
	public void testAuthenticateByUserId() throws Exception {
		setUpUser();

		int authResult = _authenticator.authenticateByUserId(
			PRIMARY_KEY, PRIMARY_KEY, "password",
			new HashMap<String, String[]>(), new HashMap<String, String[]>());

		Assert.assertEquals(1, authResult);

		Mockito.verify(
			_expandoBridge, Mockito.times(1)
		).setAttribute(
			"sambaLMPassword", "E52CAC67419A9A224A3B108F3FA6CB6D", false
		);

		Mockito.verify(
			_expandoBridge, Mockito.times(1)
		).setAttribute(
			"sambaNTPassword", "8846F7EAEE8FB117AD06BDD830B7586C", false
		);
	}

	@Test
	public void testAuthenticateByUserIdWithNullUser() throws Exception {
		int authResult = _authenticator.authenticateByUserId(
			PRIMARY_KEY, PRIMARY_KEY, "password",
			new HashMap<String, String[]>(), new HashMap<String, String[]>());

		Assert.assertEquals(1, authResult);

		Mockito.verify(
			_expandoBridge, Mockito.times(0)
		).setAttribute(
			Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean()
		);
	}

	@Override
	protected void setUpPortalUtil() {
		Portal portal = mock(Portal.class);

		when(
			portal.getCompanyIds()
		).thenReturn(
			new long[0]
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(portal);
	}

	protected void setUpUser() {
		User user = mock(User.class);

		when(
			userLocalService.fetchUserByEmailAddress(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			user
		);

		when(
			userLocalService.fetchUserByScreenName(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			user
		);

		when(
			userLocalService.fetchUserById(Mockito.anyLong())
		).thenReturn(
			user
		);

		when(
			user.getExpandoBridge()
		).thenReturn(
			_expandoBridge
		);
	}

	private static Authenticator _authenticator;
	private static ExpandoBridge _expandoBridge;

}