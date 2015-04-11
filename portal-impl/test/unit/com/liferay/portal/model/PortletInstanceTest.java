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

import java.security.InvalidParameterException;

/**
 * @author Raymond Augé
 */
public class PortletInstanceTest {

	@Test
	public void testInstanceId1() {
		PortletInstance portletInstance = new PortletInstance(PortletKeys.TEST);

		Assert.assertNull(portletInstance.getInstanceId());
	}

	@Test
	public void testInstanceId2() {
		PortletInstance portletInstance = new PortletInstance(
			getId(PortletKeys.TEST, "1234"));

		Assert.assertEquals("1234", portletInstance.getInstanceId());
	}

	@Test
	public void testInstanceId3() {
		PortletInstance portletInstance = new PortletInstance(
			getId(PortletKeys.TEST, 1234));

		Assert.assertNull(portletInstance.getInstanceId());
	}

	@Test
	public void testInstanceId4() {
		PortletInstance portletInstance = new PortletInstance(
			getId(PortletKeys.TEST, 1234, "5678"));

		Assert.assertEquals("5678", portletInstance.getInstanceId());
	}

	@Test
	public void testInstanceId5() {
		PortletInstance portletInstance = new PortletInstance(
			getWarPortletId());

		Assert.assertNull(portletInstance.getInstanceId());
	}

	@Test
	public void testInstanceId6() {
		PortletInstance portletInstance = new PortletInstance(
			getId(getWarPortletId(), "1234"));

		Assert.assertEquals("1234", portletInstance.getInstanceId());
	}

	@Test
	public void testInstanceId7() {
		PortletInstance portletInstance = new PortletInstance(
			getId(getWarPortletId(), 1234));

		Assert.assertNull(portletInstance.getInstanceId());
	}

	@Test
	public void testInstanceId8() {
		PortletInstance portletInstance = new PortletInstance(
			getId(getWarPortletId(), 1234, "5678"));

		Assert.assertEquals("5678", portletInstance.getInstanceId());
	}

	@Test
	public void testInstanceId9() {
		PortletInstance portletInstance = new PortletInstance(PortletKeys.TEST);

		Assert.assertFalse(portletInstance.hasInstanceId());
	}

	@Test
	public void testInstanceId10() {
		PortletInstance portletInstance = new PortletInstance(
			getId(PortletKeys.TEST, "1234"));

		Assert.assertTrue(portletInstance.hasInstanceId());
	}

	@Test
	public void testInstanceId11() {
		PortletInstance portletInstance = new PortletInstance(
			getId(PortletKeys.TEST, 1234));

		Assert.assertFalse(portletInstance.hasInstanceId());
	}

	@Test
	public void testInstanceId12() {
		PortletInstance portletInstance = new PortletInstance(
			getId(PortletKeys.TEST, 1234, "5678"));

		Assert.assertTrue(portletInstance.hasInstanceId());
	}

	@Test
	public void testInstanceId13() {
		PortletInstance portletInstance = new PortletInstance(
			getWarPortletId());

		Assert.assertFalse(portletInstance.hasInstanceId());
	}

	@Test
	public void testInstanceId14() {
		PortletInstance portletInstance = new PortletInstance(
			getId(getWarPortletId(), "1234"));

		Assert.assertTrue(portletInstance.hasInstanceId());
	}

	@Test
	public void testInstanceId15() {
		PortletInstance portletInstance = new PortletInstance(
			getId(getWarPortletId(), 1234));

		Assert.assertFalse(portletInstance.hasInstanceId());
	}

	@Test
	public void testInstanceId16() {
		PortletInstance portletInstance = new PortletInstance(
			getId(getWarPortletId(), 1234, "5678"));

		Assert.assertTrue(portletInstance.hasInstanceId());
	}

	@Test(expected = InvalidParameterException.class)
	public void testInvalidPortletName() {
		new PortletInstance(getId("1234_INSTANCE_asdf", 1234, "5678"));
	}

	@Test
	public void testRootPortletId1() {
		PortletInstance portletInstance = new PortletInstance(PortletKeys.TEST);

		Assert.assertEquals(PortletKeys.TEST, portletInstance.getPortletName());
	}

	@Test
	public void testRootPortletId2() {
		PortletInstance portletInstance = new PortletInstance(
			getId(PortletKeys.TEST, "1234"));

		Assert.assertEquals(PortletKeys.TEST, portletInstance.getPortletName());
	}

	@Test
	public void testRootPortletId3() {
		PortletInstance portletInstance = new PortletInstance(
			getId(PortletKeys.TEST, 1234));

		Assert.assertEquals(PortletKeys.TEST, portletInstance.getPortletName());
	}

	@Test
	public void testRootPortletId4() {
		PortletInstance portletInstance = new PortletInstance(
			getId(PortletKeys.TEST, 1234, "5678"));

		Assert.assertEquals(PortletKeys.TEST, portletInstance.getPortletName());
	}

	@Test
	public void testRootPortletId5() {
		PortletInstance portletInstance = new PortletInstance(
			getWarPortletId());

		Assert.assertEquals(
			getWarPortletId(), portletInstance.getPortletName());
	}

	@Test
	public void testRootPortletId6() {
		PortletInstance portletInstance = new PortletInstance(
			getId(getWarPortletId(), "1234"));

		Assert.assertEquals(
			getWarPortletId(), portletInstance.getPortletName());
	}

	@Test
	public void testRootPortletId7() {
		PortletInstance portletInstance = new PortletInstance(
			getId(getWarPortletId(), 1234));

		Assert.assertEquals(
			getWarPortletId(), portletInstance.getPortletName());
	}

	@Test
	public void testRootPortletId8() {
		PortletInstance portletInstance = new PortletInstance(
			getId(getWarPortletId(), 1234, "5678"));

		Assert.assertEquals(
			getWarPortletId(), portletInstance.getPortletName());
	}

	@Test
	public void testUserId1() {
		PortletInstance portletInstance = new PortletInstance(PortletKeys.TEST);

		Assert.assertEquals(0, portletInstance.getUserId());
	}

	@Test
	public void testUserId2() {
		PortletInstance portletInstance = new PortletInstance(
			getId(PortletKeys.TEST, "1234"));

		Assert.assertEquals(0, portletInstance.getUserId());
	}

	@Test
	public void testUserId3() {
		PortletInstance portletInstance = new PortletInstance(
			getId(PortletKeys.TEST, 1234));

		Assert.assertEquals(1234, portletInstance.getUserId());
	}

	@Test
	public void testUserId4() {
		PortletInstance portletInstance = new PortletInstance(
			getId(PortletKeys.TEST, 1234, "5678"));

		Assert.assertEquals(1234, portletInstance.getUserId());
	}

	@Test
	public void testUserId5() {
		PortletInstance portletInstance = new PortletInstance(
			getWarPortletId());

		Assert.assertEquals(0, portletInstance.getUserId());
	}

	@Test
	public void testUserId6() {
		PortletInstance portletInstance = new PortletInstance(
			getId(getWarPortletId(), "1234"));

		Assert.assertEquals(0, portletInstance.getUserId());
	}

	@Test
	public void testUserId7() {
		PortletInstance portletInstance = new PortletInstance(
			getId(getWarPortletId(), 1234));

		Assert.assertEquals(1234, portletInstance.getUserId());
	}

	@Test
	public void testUserId8() {
		PortletInstance portletInstance = new PortletInstance(
			getId(getWarPortletId(), 1234, "5678"));

		Assert.assertEquals(1234, portletInstance.getUserId());
	}

	@Test
	public void testUserId9() {
		PortletInstance portletInstance = new PortletInstance(PortletKeys.TEST);

		Assert.assertFalse(portletInstance.hasUserId());
	}

	@Test
	public void testUserId10() {
		PortletInstance portletInstance = new PortletInstance(
			getId(PortletKeys.TEST, "1234"));

		Assert.assertFalse(portletInstance.hasUserId());
	}

	@Test
	public void testUserId11() {
		PortletInstance portletInstance = new PortletInstance(
			getId(PortletKeys.TEST, 1234));

		Assert.assertTrue(portletInstance.hasUserId());
	}

	@Test
	public void testUserId12() {
		PortletInstance portletInstance = new PortletInstance(
			getId(PortletKeys.TEST, 1234, "5678"));

		Assert.assertTrue(portletInstance.hasUserId());
	}

	@Test
	public void testUserId13() {
		PortletInstance portletInstance = new PortletInstance(
			getWarPortletId());

		Assert.assertFalse(portletInstance.hasUserId());
	}

	@Test
	public void testUserId14() {
		PortletInstance portletInstance = new PortletInstance(
			getId(getWarPortletId(), "1234"));

		Assert.assertFalse(portletInstance.hasUserId());
	}

	@Test
	public void testUserId15() {
		PortletInstance portletInstance = new PortletInstance(
			getId(getWarPortletId(), 1234));

		Assert.assertTrue(portletInstance.hasUserId());
	}

	@Test
	public void testUserId16() {
		PortletInstance portletInstance = new PortletInstance(
			getId(getWarPortletId(), 1234, "5678"));

		Assert.assertTrue(portletInstance.hasUserId());
	}

	protected String getId(String rootPortletId, long userId) {
		PortletInstance portletInstance = new PortletInstance(
			rootPortletId, userId, null);

		return portletInstance.getPortletInstanceKey();
	}

	protected String getId(
		String rootPortletId, long userId, String instanceKey) {

		PortletInstance portletInstance = new PortletInstance(
			rootPortletId, userId, instanceKey);

		return portletInstance.getPortletInstanceKey();
	}

	protected String getId(String rootPortletId, String instanceKey) {
		PortletInstance portletInstance = new PortletInstance(
			rootPortletId, instanceKey);

		return portletInstance.getPortletInstanceKey();
	}

	protected String getWarPortletId() {
		return PortletKeys.TEST + PortletConstants.WAR_SEPARATOR + "context";
	}

}