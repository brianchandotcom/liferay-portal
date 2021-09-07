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

		assertSetCount(0);

		SessionMessages.clear(httpSession);

		assertSetCount(0);

		// Add

		SessionMessages.add(httpSession, KEY1);
		SessionMessages.add(httpSession, KEY2, VALUE2);
		SessionMessages.add(httpSession, SessionMessagesTest.class);
		SessionMessages.add(
			httpSession, HttpSessionInvocationHandler.class, VALUE3);

		assertSetCount(4);

		// Get

		Assert.assertSame(KEY1, SessionMessages.get(httpSession, KEY1));
		Assert.assertSame(VALUE2, SessionMessages.get(httpSession, KEY2));
		Assert.assertEquals(
			SessionMessagesTest.class.getName(),
			SessionMessages.get(httpSession, SessionMessagesTest.class));
		Assert.assertSame(
			VALUE3,
			SessionMessages.get(
				httpSession, HttpSessionInvocationHandler.class));
		Assert.assertNull(SessionMessages.get(httpSession, KEY3));

		assertSetCount(4);

		SessionMessages.add(httpSession, KEY1, VALUE1);

		Assert.assertSame(VALUE1, SessionMessages.get(httpSession, KEY1));

		assertSetCount(5);

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

		assertSetCount(5);

		// isEmpty

		Assert.assertFalse(SessionMessages.isEmpty(httpSession));

		assertSetCount(5);

		// KeySet and iterator

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

		assertSetCount(5);

		// Print

		SessionMessages.print(httpSession);

		assertSetCount(5);

		// Size

		Assert.assertEquals(4, SessionMessages.size(httpSession));

		assertSetCount(5);

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

		assertSetCount(6);

		// Clear

		SessionMessages.clear(httpSession);

		assertSetCount(7);

		Assert.assertTrue(SessionMessages.isEmpty(httpSession));
	}

}