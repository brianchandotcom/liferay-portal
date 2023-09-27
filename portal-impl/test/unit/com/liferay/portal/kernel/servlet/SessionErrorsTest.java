/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PortalImpl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Dante Wang
 */
public class SessionErrorsTest extends BaseSessionMapsTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testHttpSession() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		Assert.assertNull(SessionErrors.get(httpSession, KEY1));
		Assert.assertFalse(SessionErrors.contains(httpSession, KEY1));
		Assert.assertTrue(SessionErrors.isEmpty(httpSession));
		Assert.assertEquals(
			Collections.emptySet(), SessionErrors.keySet(httpSession));
		Assert.assertEquals(
			Collections.emptyIterator(), SessionErrors.iterator(httpSession));
		Assert.assertEquals(0, SessionErrors.size(httpSession));

		SessionErrors.print(httpSession);

		SessionErrors.clear(httpSession);

		// Add

		SessionErrors.add(httpSession, KEY1);
		SessionErrors.add(httpSession, KEY2, VALUE2);
		SessionErrors.add(httpSession, SessionErrorsTest.class);
		SessionErrors.add(
			httpSession, HttpSessionInvocationHandler.class, VALUE3);

		// Get

		Assert.assertEquals(KEY1, SessionErrors.get(httpSession, KEY1));
		Assert.assertEquals(VALUE2, SessionErrors.get(httpSession, KEY2));
		Assert.assertEquals(
			SessionErrorsTest.class.getName(),
			SessionErrors.get(httpSession, SessionErrorsTest.class));
		Assert.assertEquals(
			VALUE3,
			SessionErrors.get(httpSession, HttpSessionInvocationHandler.class));
		Assert.assertNull(SessionErrors.get(httpSession, KEY3));

		SessionErrors.add(httpSession, KEY1, VALUE1);

		Assert.assertEquals(VALUE1, SessionErrors.get(httpSession, KEY1));

		// Update

		SessionErrors.add(httpSession, KEY2, VALUE3);

		Assert.assertEquals(VALUE3, SessionErrors.get(httpSession, KEY2));

		// Contains

		Assert.assertTrue(SessionErrors.contains(httpSession, KEY1));
		Assert.assertTrue(SessionErrors.contains(httpSession, KEY2));
		Assert.assertTrue(
			SessionErrors.contains(httpSession, SessionErrorsTest.class));
		Assert.assertTrue(
			SessionErrors.contains(
				httpSession,
				new Class<?>[] {
					SessionErrorsTest.class, HttpSessionInvocationHandler.class
				}));
		Assert.assertFalse(SessionErrors.contains(httpSession, KEY3));

		// isEmpty

		Assert.assertFalse(SessionErrors.isEmpty(httpSession));

		// Key set and iterator

		Set<String> addedKeys = new HashSet<>();

		addedKeys.add(KEY1);
		addedKeys.add(KEY2);
		addedKeys.add(SessionErrorsTest.class.getName());
		addedKeys.add(HttpSessionInvocationHandler.class.getName());

		Assert.assertEquals(addedKeys, SessionErrors.keySet(httpSession));

		Iterator<String> iterator = SessionErrors.iterator(httpSession);

		while (iterator.hasNext()) {
			addedKeys.remove(iterator.next());
		}

		Assert.assertTrue(addedKeys.isEmpty());

		// Print

		SessionErrors.print(httpSession);

		// Size

		Assert.assertEquals(4, SessionErrors.size(httpSession));

		// Remove

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setSession(httpSession);

		SessionErrors.remove(mockHttpServletRequest, SessionErrorsTest.class);

		Assert.assertEquals(3, SessionErrors.size(mockHttpServletRequest));
		Assert.assertFalse(
			SessionErrors.contains(
				mockHttpServletRequest, SessionErrorsTest.class));

		// Clear

		SessionErrors.clear(httpSession);

		Assert.assertTrue(SessionErrors.isEmpty(httpSession));
	}

}