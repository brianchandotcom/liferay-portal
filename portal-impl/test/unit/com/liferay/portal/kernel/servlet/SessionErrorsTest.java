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

		assertSetCount(0);

		SessionErrors.clear(httpSession);

		assertSetCount(0);

		// Add

		SessionErrors.add(httpSession, KEY1);
		SessionErrors.add(httpSession, KEY2, VALUE2);
		SessionErrors.add(httpSession, SessionErrorsTest.class);
		SessionErrors.add(
			httpSession, HttpSessionInvocationHandler.class, VALUE3);

		assertSetCount(4);

		// Get

		Assert.assertSame(KEY1, SessionErrors.get(httpSession, KEY1));
		Assert.assertSame(VALUE2, SessionErrors.get(httpSession, KEY2));
		Assert.assertEquals(
			SessionErrorsTest.class.getName(),
			SessionErrors.get(httpSession, SessionErrorsTest.class));
		Assert.assertSame(
			VALUE3,
			SessionErrors.get(httpSession, HttpSessionInvocationHandler.class));
		Assert.assertNull(SessionErrors.get(httpSession, KEY3));

		assertSetCount(4);

		SessionErrors.add(httpSession, KEY1, VALUE1);

		Assert.assertSame(VALUE1, SessionErrors.get(httpSession, KEY1));

		assertSetCount(5);

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

		assertSetCount(5);

		// isEmpty

		Assert.assertFalse(SessionErrors.isEmpty(httpSession));

		assertSetCount(5);

		// KeySet and iterator

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

		assertSetCount(5);

		// Print

		SessionErrors.print(httpSession);

		assertSetCount(5);

		// Size

		Assert.assertEquals(4, SessionErrors.size(httpSession));

		assertSetCount(5);

		// Remove

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setSession(httpSession);

		SessionErrors.remove(mockHttpServletRequest, SessionErrorsTest.class);

		Assert.assertEquals(3, SessionErrors.size(mockHttpServletRequest));
		Assert.assertFalse(
			SessionErrors.contains(
				mockHttpServletRequest, SessionErrorsTest.class));

		assertSetCount(6);

		// Clear

		SessionErrors.clear(httpSession);

		assertSetCount(7);

		Assert.assertTrue(SessionErrors.isEmpty(httpSession));
	}

}