/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

/**
 * @author Raymond Augé
 */
public class PortletConstantsTest extends TestCase {

	public void testRootPortletId_1() {
		String portletId = PortletKeys.JOURNAL_CONTENT;

		assertEquals(
			PortletKeys.JOURNAL_CONTENT,
			PortletConstants.getRootPortletId(portletId));
	}

	public void testRootPortletId_2() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, "1234");

		assertEquals(
			PortletKeys.JOURNAL_CONTENT,
			PortletConstants.getRootPortletId(portletId));
	}

	public void testRootPortletId_3() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, 1234);

		assertEquals(
			PortletKeys.JOURNAL_CONTENT,
			PortletConstants.getRootPortletId(portletId));
	}

	public void testRootPortletId_4() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, 1234, "5678");

		assertEquals(
			PortletKeys.JOURNAL_CONTENT,
			PortletConstants.getRootPortletId(portletId));
	}

	public void testRootPortletId_5() {
		String portletId = getWarPortletId();

		assertEquals(
			getWarPortletId(), PortletConstants.getRootPortletId(portletId));
	}

	public void testRootPortletId_6() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), "1234");

		assertEquals(
			getWarPortletId(), PortletConstants.getRootPortletId(portletId));
	}

	public void testRootPortletId_7() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234);

		assertEquals(
			getWarPortletId(), PortletConstants.getRootPortletId(portletId));
	}

	public void testRootPortletId_8() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234, "5678");

		assertEquals(
			getWarPortletId(), PortletConstants.getRootPortletId(portletId));
	}

	public void testInstanceId_1() {
		String portletId = PortletKeys.JOURNAL_CONTENT;

		assertNull(PortletConstants.getInstanceId(portletId));
	}

	public void testInstanceId_2() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, "1234");

		assertEquals("1234", PortletConstants.getInstanceId(portletId));
	}

	public void testInstanceId_3() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, 1234);

		assertNull(PortletConstants.getInstanceId(portletId));
	}

	public void testInstanceId_4() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, 1234, "5678");

		assertEquals("5678", PortletConstants.getInstanceId(portletId));
	}

	public void testInstanceId_5() {
		String portletId = getWarPortletId();

		assertNull(PortletConstants.getInstanceId(portletId));
	}

	public void testInstanceId_6() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), "1234");

		assertEquals("1234", PortletConstants.getInstanceId(portletId));
	}

	public void testInstanceId_7() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234);

		assertNull(PortletConstants.getInstanceId(portletId));
	}

	public void testInstanceId_8() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234, "5678");

		assertEquals("5678", PortletConstants.getInstanceId(portletId));
	}

	public void testInstanceId_9() {
		String portletId = PortletKeys.JOURNAL_CONTENT;

		assertFalse(PortletConstants.hasInstanceId(portletId));
	}

	public void testInstanceId_10() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, "1234");

		assertTrue(PortletConstants.hasInstanceId(portletId));
	}

	public void testInstanceId_11() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, 1234);

		assertFalse(PortletConstants.hasInstanceId(portletId));
	}

	public void testInstanceId_12() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, 1234, "5678");

		assertTrue(PortletConstants.hasInstanceId(portletId));
	}

	public void testInstanceId_13() {
		String portletId = getWarPortletId();

		assertFalse(PortletConstants.hasInstanceId(portletId));
	}

	public void testInstanceId_14() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), "1234");

		assertTrue(PortletConstants.hasInstanceId(portletId));
	}

	public void testInstanceId_15() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234);

		assertFalse(PortletConstants.hasInstanceId(portletId));
	}

	public void testInstanceId_16() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234, "5678");

		assertTrue(PortletConstants.hasInstanceId(portletId));
	}

	public void testUserId_1() {
		String portletId = PortletKeys.JOURNAL_CONTENT;

		assertEquals(0, PortletConstants.getUserId(portletId));
	}

	public void testUserId_2() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, "1234");

		assertEquals(0, PortletConstants.getUserId(portletId));
	}

	public void testUserId_3() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, 1234);

		assertEquals(1234, PortletConstants.getUserId(portletId));
	}

	public void testUserId_4() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, 1234, "5678");

		assertEquals(1234, PortletConstants.getUserId(portletId));
	}

	public void testUserId_5() {
		String portletId = getWarPortletId();

		assertEquals(0, PortletConstants.getUserId(portletId));
	}

	public void testUserId_6() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), "1234");

		assertEquals(0, PortletConstants.getUserId(portletId));
	}

	public void testUserId_7() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234);

		assertEquals(1234, PortletConstants.getUserId(portletId));
	}

	public void testUserId_8() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234, "5678");

		assertEquals(1234, PortletConstants.getUserId(portletId));
	}

	public void testUserId_9() {
		String portletId = PortletKeys.JOURNAL_CONTENT;

		assertFalse(PortletConstants.hasUserId(portletId));
	}

	public void testUserId_10() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, "1234");

		assertFalse(PortletConstants.hasUserId(portletId));
	}

	public void testUserId_11() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, 1234);

		assertTrue(PortletConstants.hasUserId(portletId));
	}

	public void testUserId_12() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.JOURNAL_CONTENT, 1234, "5678");

		assertTrue(PortletConstants.hasUserId(portletId));
	}

	public void testUserId_13() {
		String portletId = getWarPortletId();

		assertFalse(PortletConstants.hasUserId(portletId));
	}

	public void testUserId_14() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), "1234");

		assertFalse(PortletConstants.hasUserId(portletId));
	}

	public void testUserId_15() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234);

		assertTrue(PortletConstants.hasUserId(portletId));
	}

	public void testUserId_16() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234, "5678");

		assertTrue(PortletConstants.hasUserId(portletId));
	}

	protected String getWarPortletId() {
		return PortletKeys.JOURNAL_CONTENT + PortletConstants.WAR_SEPARATOR +
			"context";
	}

}