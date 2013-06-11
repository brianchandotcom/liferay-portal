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

package com.liferay.portal.model;

import com.liferay.portal.kernel.test.TestCase;
import com.liferay.portal.util.PortletKeys;

import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class PortletConstantsTest extends TestCase {

	@Test
	public void testInstanceId1() {
		String portletId = PortletKeys.JOURNAL_CONTENT;

		assertNull(PortletConstants.getInstanceId(portletId));
	}

	@Test
	public void testInstanceId2() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, "1234");

		assertEquals("1234", PortletConstants.getInstanceId(portletId));
	}

	@Test
	public void testInstanceId3() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, 1234);

		assertNull(PortletConstants.getInstanceId(portletId));
	}

	@Test
	public void testInstanceId4() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, 1234, "5678");

		assertEquals("5678", PortletConstants.getInstanceId(portletId));
	}

	@Test
	public void testInstanceId5() {
		String portletId = getWarPortletId();

		assertNull(PortletConstants.getInstanceId(portletId));
	}

	@Test
	public void testInstanceId6() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), "1234");

		assertEquals("1234", PortletConstants.getInstanceId(portletId));
	}

	@Test
	public void testInstanceId7() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234);

		assertNull(PortletConstants.getInstanceId(portletId));
	}

	@Test
	public void testInstanceId8() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234, "5678");

		assertEquals("5678", PortletConstants.getInstanceId(portletId));
	}

	@Test
	public void testInstanceId9() {
		String portletId = PortletKeys.JOURNAL_CONTENT;

		assertFalse(PortletConstants.hasInstanceId(portletId));
	}

	@Test
	public void testInstanceId10() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, "1234");

		assertTrue(PortletConstants.hasInstanceId(portletId));
	}

	@Test
	public void testInstanceId11() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, 1234);

		assertFalse(PortletConstants.hasInstanceId(portletId));
	}

	@Test
	public void testInstanceId12() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, 1234, "5678");

		assertTrue(PortletConstants.hasInstanceId(portletId));
	}

	@Test
	public void testInstanceId13() {
		String portletId = getWarPortletId();

		assertFalse(PortletConstants.hasInstanceId(portletId));
	}

	@Test
	public void testInstanceId14() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), "1234");

		assertTrue(PortletConstants.hasInstanceId(portletId));
	}

	@Test
	public void testInstanceId15() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234);

		assertFalse(PortletConstants.hasInstanceId(portletId));
	}

	@Test
	public void testInstanceId16() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234, "5678");

		assertTrue(PortletConstants.hasInstanceId(portletId));
	}

	@Test
	public void testRootPortletId1() {
		String portletId = PortletKeys.JOURNAL_CONTENT;

		assertEquals(
			PortletKeys.JOURNAL_CONTENT,
			PortletConstants.getRootPortletId(portletId));
	}

	@Test
	public void testRootPortletId2() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, "1234");

		assertEquals(
			PortletKeys.JOURNAL_CONTENT,
			PortletConstants.getRootPortletId(portletId));
	}

	@Test
	public void testRootPortletId3() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, 1234);

		assertEquals(
			PortletKeys.JOURNAL_CONTENT,
			PortletConstants.getRootPortletId(portletId));
	}

	@Test
	public void testRootPortletId4() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, 1234, "5678");

		assertEquals(
			PortletKeys.JOURNAL_CONTENT,
			PortletConstants.getRootPortletId(portletId));
	}

	@Test
	public void testRootPortletId5() {
		String portletId = getWarPortletId();

		assertEquals(
			getWarPortletId(), PortletConstants.getRootPortletId(portletId));
	}

	@Test
	public void testRootPortletId6() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), "1234");

		assertEquals(
			getWarPortletId(), PortletConstants.getRootPortletId(portletId));
	}

	@Test
	public void testRootPortletId7() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234);

		assertEquals(
			getWarPortletId(), PortletConstants.getRootPortletId(portletId));
	}

	@Test
	public void testRootPortletId8() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234, "5678");

		assertEquals(
			getWarPortletId(), PortletConstants.getRootPortletId(portletId));
	}

	@Test
	public void testUserId1() {
		String portletId = PortletKeys.JOURNAL_CONTENT;

		assertEquals(0, PortletConstants.getUserId(portletId));
	}

	@Test
	public void testUserId2() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, "1234");

		assertEquals(0, PortletConstants.getUserId(portletId));
	}

	@Test
	public void testUserId3() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, 1234);

		assertEquals(1234, PortletConstants.getUserId(portletId));
	}

	@Test
	public void testUserId4() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, 1234, "5678");

		assertEquals(1234, PortletConstants.getUserId(portletId));
	}

	@Test
	public void testUserId5() {
		String portletId = getWarPortletId();

		assertEquals(0, PortletConstants.getUserId(portletId));
	}

	@Test
	public void testUserId6() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), "1234");

		assertEquals(0, PortletConstants.getUserId(portletId));
	}

	@Test
	public void testUserId7() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234);

		assertEquals(1234, PortletConstants.getUserId(portletId));
	}

	@Test
	public void testUserId8() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234, "5678");

		assertEquals(1234, PortletConstants.getUserId(portletId));
	}

	@Test
	public void testUserId9() {
		String portletId = PortletKeys.JOURNAL_CONTENT;

		assertFalse(PortletConstants.hasUserId(portletId));
	}

	@Test
	public void testUserId10() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, "1234");

		assertFalse(PortletConstants.hasUserId(portletId));
	}

	@Test
	public void testUserId11() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, 1234);

		assertTrue(PortletConstants.hasUserId(portletId));
	}

	@Test
	public void testUserId12() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, 1234, "5678");

		assertTrue(PortletConstants.hasUserId(portletId));
	}

	@Test
	public void testUserId13() {
		String portletId = getWarPortletId();

		assertFalse(PortletConstants.hasUserId(portletId));
	}

	@Test
	public void testUserId14() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), "1234");

		assertFalse(PortletConstants.hasUserId(portletId));
	}

	@Test
	public void testUserId15() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234);

		assertTrue(PortletConstants.hasUserId(portletId));
	}

	@Test
	public void testUserId16() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234, "5678");

		assertTrue(PortletConstants.hasUserId(portletId));
	}

	protected String getWarPortletId() {
		return PortletKeys.JOURNAL_CONTENT + PortletConstants.WAR_SEPARATOR +
			"context";
	}

}