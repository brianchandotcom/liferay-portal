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
public class SessionMessagesTest extends BaseSessionMapsTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testHttpSession() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		Assert.assertNull(SessionMessages.get(httpSession, KEY1));
		Assert.assertFalse(SessionMessages.contains(httpSession, KEY1));
		Assert.assertTrue(SessionMessages.isEmpty(httpSession));
		Assert.assertEquals(
			Collections.emptySet(), SessionMessages.keySet(httpSession));
		Assert.assertEquals(
			Collections.emptyIterator(), SessionMessages.iterator(httpSession));
		Assert.assertEquals(0, SessionMessages.size(httpSession));

		SessionMessages.print(httpSession);

		SessionMessages.clear(httpSession);

		// Add

		SessionMessages.add(httpSession, KEY1);
		SessionMessages.add(httpSession, KEY2, VALUE2);
		SessionMessages.add(httpSession, SessionMessagesTest.class);
		SessionMessages.add(
			httpSession, HttpSessionInvocationHandler.class, VALUE3);

		// Get

		Assert.assertEquals(KEY1, SessionMessages.get(httpSession, KEY1));
		Assert.assertEquals(VALUE2, SessionMessages.get(httpSession, KEY2));
		Assert.assertEquals(
			SessionMessagesTest.class.getName(),
			SessionMessages.get(httpSession, SessionMessagesTest.class));
		Assert.assertEquals(
			VALUE3,
			SessionMessages.get(
				httpSession, HttpSessionInvocationHandler.class));
		Assert.assertNull(SessionMessages.get(httpSession, KEY3));

		SessionMessages.add(httpSession, KEY1, VALUE1);

		Assert.assertEquals(VALUE1, SessionMessages.get(httpSession, KEY1));

		// Update

		SessionErrors.add(httpSession, KEY2, VALUE3);

		Assert.assertEquals(VALUE3, SessionErrors.get(httpSession, KEY2));

		// Contains

		Assert.assertTrue(SessionMessages.contains(httpSession, KEY1));
		Assert.assertTrue(SessionMessages.contains(httpSession, KEY2));
		Assert.assertTrue(
			SessionMessages.contains(httpSession, SessionMessagesTest.class));
		Assert.assertTrue(
			SessionMessages.contains(
				httpSession,
				new Class<?>[] {
					SessionMessagesTest.class,
					HttpSessionInvocationHandler.class
				}));
		Assert.assertFalse(SessionMessages.contains(httpSession, KEY3));

		// isEmpty

		Assert.assertFalse(SessionMessages.isEmpty(httpSession));

		// Key set and iterator

		Set<String> addedKeys = new HashSet<>();

		addedKeys.add(KEY1);
		addedKeys.add(KEY2);
		addedKeys.add(SessionMessagesTest.class.getName());
		addedKeys.add(HttpSessionInvocationHandler.class.getName());

		Assert.assertEquals(addedKeys, SessionMessages.keySet(httpSession));

		Iterator<String> iterator = SessionMessages.iterator(httpSession);

		while (iterator.hasNext()) {
			addedKeys.remove(iterator.next());
		}

		Assert.assertTrue(addedKeys.isEmpty());

		// Print

		SessionMessages.print(httpSession);

		// Size

		Assert.assertEquals(4, SessionMessages.size(httpSession));

		// Remove

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setSession(httpSession);

		SessionMessages.remove(
			mockHttpServletRequest, SessionMessagesTest.class);

		Assert.assertEquals(3, SessionMessages.size(mockHttpServletRequest));
		Assert.assertFalse(
			SessionMessages.contains(
				mockHttpServletRequest, SessionMessagesTest.class));

		// Clear

		SessionMessages.clear(httpSession);

		Assert.assertTrue(SessionMessages.isEmpty(httpSession));
	}

}