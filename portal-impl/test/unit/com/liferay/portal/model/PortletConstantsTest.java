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

package com.liferay.portal.model;

import com.liferay.portlet.util.test.PortletKeys;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class PortletConstantsTest {

	@Test
	public void testInstanceId1() {
		String portletId = PortletKeys.TEST;

		Assert.assertNull(PortletConstants.getInstanceId(portletId));
	}

	@Test
	public void testInstanceId2() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.TEST, "1234");

		Assert.assertEquals("1234", PortletConstants.getInstanceId(portletId));
	}

	@Test
	public void testInstanceId3() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.TEST, 1234);

		Assert.assertNull(PortletConstants.getInstanceId(portletId));
	}

	@Test
	public void testInstanceId4() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.TEST, 1234, "5678");

		Assert.assertEquals("5678", PortletConstants.getInstanceId(portletId));
	}

	@Test
	public void testInstanceId5() {
		String portletId = getWarPortletId();

		Assert.assertNull(PortletConstants.getInstanceId(portletId));
	}

	@Test
	public void testInstanceId6() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), "1234");

		Assert.assertEquals("1234", PortletConstants.getInstanceId(portletId));
	}

	@Test
	public void testInstanceId7() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234);

		Assert.assertNull(PortletConstants.getInstanceId(portletId));
	}

	@Test
	public void testInstanceId8() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234, "5678");

		Assert.assertEquals("5678", PortletConstants.getInstanceId(portletId));
	}

	@Test
	public void testInstanceId9() {
		String portletId = PortletKeys.TEST;

		Assert.assertFalse(PortletConstants.hasInstanceId(portletId));
	}

	@Test
	public void testInstanceId10() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.TEST, "1234");

		Assert.assertTrue(PortletConstants.hasInstanceId(portletId));
	}

	@Test
	public void testInstanceId11() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.TEST, 1234);

		Assert.assertFalse(PortletConstants.hasInstanceId(portletId));
	}

	@Test
	public void testInstanceId12() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.TEST, 1234, "5678");

		Assert.assertTrue(PortletConstants.hasInstanceId(portletId));
	}

	@Test
	public void testInstanceId13() {
		String portletId = getWarPortletId();

		Assert.assertFalse(PortletConstants.hasInstanceId(portletId));
	}

	@Test
	public void testInstanceId14() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), "1234");

		Assert.assertTrue(PortletConstants.hasInstanceId(portletId));
	}

	@Test
	public void testInstanceId15() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234);

		Assert.assertFalse(PortletConstants.hasInstanceId(portletId));
	}

	@Test
	public void testInstanceId16() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234, "5678");

		Assert.assertTrue(PortletConstants.hasInstanceId(portletId));
	}

	@Test
	public void testRootPortletId1() {
		String portletId = PortletKeys.TEST;

		Assert.assertEquals(
			PortletKeys.TEST, PortletConstants.getRootPortletId(portletId));
	}

	@Test
	public void testRootPortletId2() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.TEST, "1234");

		Assert.assertEquals(
			PortletKeys.TEST, PortletConstants.getRootPortletId(portletId));
	}

	@Test
	public void testRootPortletId3() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.TEST, 1234);

		Assert.assertEquals(
			PortletKeys.TEST, PortletConstants.getRootPortletId(portletId));
	}

	@Test
	public void testRootPortletId4() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.TEST, 1234, "5678");

		Assert.assertEquals(
			PortletKeys.TEST, PortletConstants.getRootPortletId(portletId));
	}

	@Test
	public void testRootPortletId5() {
		String portletId = getWarPortletId();

		Assert.assertEquals(
			getWarPortletId(), PortletConstants.getRootPortletId(portletId));
	}

	@Test
	public void testRootPortletId6() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), "1234");

		Assert.assertEquals(
			getWarPortletId(), PortletConstants.getRootPortletId(portletId));
	}

	@Test
	public void testRootPortletId7() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234);

		Assert.assertEquals(
			getWarPortletId(), PortletConstants.getRootPortletId(portletId));
	}

	@Test
	public void testRootPortletId8() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234, "5678");

		Assert.assertEquals(
			getWarPortletId(), PortletConstants.getRootPortletId(portletId));
	}

	@Test
	public void testUserId1() {
		String portletId = PortletKeys.TEST;

		Assert.assertEquals(0, PortletConstants.getUserId(portletId));
	}

	@Test
	public void testUserId2() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.TEST, "1234");

		Assert.assertEquals(0, PortletConstants.getUserId(portletId));
	}

	@Test
	public void testUserId3() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.TEST, 1234);

		Assert.assertEquals(1234, PortletConstants.getUserId(portletId));
	}

	@Test
	public void testUserId4() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.TEST, 1234, "5678");

		Assert.assertEquals(1234, PortletConstants.getUserId(portletId));
	}

	@Test
	public void testUserId5() {
		String portletId = getWarPortletId();

		Assert.assertEquals(0, PortletConstants.getUserId(portletId));
	}

	@Test
	public void testUserId6() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), "1234");

		Assert.assertEquals(0, PortletConstants.getUserId(portletId));
	}

	@Test
	public void testUserId7() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234);

		Assert.assertEquals(1234, PortletConstants.getUserId(portletId));
	}

	@Test
	public void testUserId8() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234, "5678");

		Assert.assertEquals(1234, PortletConstants.getUserId(portletId));
	}

	@Test
	public void testUserId9() {
		String portletId = PortletKeys.TEST;

		Assert.assertFalse(PortletConstants.hasUserId(portletId));
	}

	@Test
	public void testUserId10() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.TEST, "1234");

		Assert.assertFalse(PortletConstants.hasUserId(portletId));
	}

	@Test
	public void testUserId11() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.TEST, 1234);

		Assert.assertTrue(PortletConstants.hasUserId(portletId));
	}

	@Test
	public void testUserId12() {
		String portletId = PortletConstants.assemblePortletId(
			PortletKeys.TEST, 1234, "5678");

		Assert.assertTrue(PortletConstants.hasUserId(portletId));
	}

	@Test
	public void testUserId13() {
		String portletId = getWarPortletId();

		Assert.assertFalse(PortletConstants.hasUserId(portletId));
	}

	@Test
	public void testUserId14() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), "1234");

		Assert.assertFalse(PortletConstants.hasUserId(portletId));
	}

	@Test
	public void testUserId15() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234);

		Assert.assertTrue(PortletConstants.hasUserId(portletId));
	}

	@Test
	public void testUserId16() {
		String portletId = PortletConstants.assemblePortletId(
			getWarPortletId(), 1234, "5678");

		Assert.assertTrue(PortletConstants.hasUserId(portletId));
	}

	protected String getWarPortletId() {
		return PortletKeys.TEST + PortletConstants.WAR_SEPARATOR + "context";
	}

}