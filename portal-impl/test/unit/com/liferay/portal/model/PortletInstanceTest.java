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

import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.PortletInstance;
import com.liferay.portlet.util.test.PortletKeys;

import java.security.InvalidParameterException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class PortletInstanceTest {

	@Test
	public void testGetInstanceId1() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(PortletKeys.TEST);

		Assert.assertNull(portletInstance.getInstanceId());
	}

	@Test
	public void testGetInstanceId2() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(PortletKeys.TEST, "1234"));

		Assert.assertEquals("1234", portletInstance.getInstanceId());
	}

	@Test
	public void testGetInstanceId3() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(PortletKeys.TEST, 1234));

		Assert.assertNull(portletInstance.getInstanceId());
	}

	@Test
	public void testGetInstanceId4() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(PortletKeys.TEST, 1234, "5678"));

		Assert.assertEquals("5678", portletInstance.getInstanceId());
	}

	@Test
	public void testGetInstanceId5() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(getPortletInstanceKey());

		Assert.assertNull(portletInstance.getInstanceId());
	}

	@Test
	public void testGetInstanceId6() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(getPortletInstanceKey(), "1234"));

		Assert.assertEquals("1234", portletInstance.getInstanceId());
	}

	@Test
	public void testGetInstanceId7() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(getPortletInstanceKey(), 1234));

		Assert.assertNull(portletInstance.getInstanceId());
	}

	@Test
	public void testGetInstanceId8() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(getPortletInstanceKey(), 1234, "5678"));

		Assert.assertEquals("5678", portletInstance.getInstanceId());
	}

	@Test(expected = InvalidParameterException.class)
	public void testGetPortletInstanceKey() {
		PortletInstance.fromPortletInstanceKey(
			getPortletInstanceKey("1234_INSTANCE_asdf", 1234, "5678"));
	}

	@Test
	public void testGetPortletName1() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(PortletKeys.TEST);

		Assert.assertEquals(PortletKeys.TEST, portletInstance.getPortletName());
	}

	@Test
	public void testGetPortletName2() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(PortletKeys.TEST, "1234"));

		Assert.assertEquals(PortletKeys.TEST, portletInstance.getPortletName());
	}

	@Test
	public void testGetPortletName3() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(PortletKeys.TEST, 1234));

		Assert.assertEquals(PortletKeys.TEST, portletInstance.getPortletName());
	}

	@Test
	public void testGetPortletName4() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(PortletKeys.TEST, 1234, "5678"));

		Assert.assertEquals(PortletKeys.TEST, portletInstance.getPortletName());
	}

	@Test
	public void testGetPortletName5() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(getPortletInstanceKey());

		Assert.assertEquals(
			getPortletInstanceKey(), portletInstance.getPortletName());
	}

	@Test
	public void testGetPortletName6() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(getPortletInstanceKey(), "1234"));

		Assert.assertEquals(
			getPortletInstanceKey(), portletInstance.getPortletName());
	}

	@Test
	public void testGetPortletName7() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(getPortletInstanceKey(), 1234));

		Assert.assertEquals(
			getPortletInstanceKey(), portletInstance.getPortletName());
	}

	@Test
	public void testGetPortletName8() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(getPortletInstanceKey(), 1234, "5678"));

		Assert.assertEquals(
			getPortletInstanceKey(), portletInstance.getPortletName());
	}

	@Test
	public void testGetUserId1() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(PortletKeys.TEST);

		Assert.assertEquals(0, portletInstance.getUserId());
	}

	@Test
	public void testGetUserId2() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(PortletKeys.TEST, "1234"));

		Assert.assertEquals(0, portletInstance.getUserId());
	}

	@Test
	public void testGetUserId3() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(PortletKeys.TEST, 1234));

		Assert.assertEquals(1234, portletInstance.getUserId());
	}

	@Test
	public void testGetUserId4() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(PortletKeys.TEST, 1234, "5678"));

		Assert.assertEquals(1234, portletInstance.getUserId());
	}

	@Test
	public void testGetUserId5() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(getPortletInstanceKey());

		Assert.assertEquals(0, portletInstance.getUserId());
	}

	@Test
	public void testGetUserId6() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(getPortletInstanceKey(), "1234"));

		Assert.assertEquals(0, portletInstance.getUserId());
	}

	@Test
	public void testGetUserId7() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(getPortletInstanceKey(), 1234));

		Assert.assertEquals(1234, portletInstance.getUserId());
	}

	@Test
	public void testGetUserId8() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(getPortletInstanceKey(), 1234, "5678"));

		Assert.assertEquals(1234, portletInstance.getUserId());
	}

	@Test
	public void testGetUserId9() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(PortletKeys.TEST);

		Assert.assertFalse(portletInstance.hasUserId());
	}

	@Test
	public void testGetUserId10() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(PortletKeys.TEST, "1234"));

		Assert.assertFalse(portletInstance.hasUserId());
	}

	@Test
	public void testGetUserId11() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(PortletKeys.TEST, 1234));

		Assert.assertTrue(portletInstance.hasUserId());
	}

	@Test
	public void testGetUserId12() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(PortletKeys.TEST, 1234, "5678"));

		Assert.assertTrue(portletInstance.hasUserId());
	}

	@Test
	public void testGetUserId13() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(getPortletInstanceKey());

		Assert.assertFalse(portletInstance.hasUserId());
	}

	@Test
	public void testGetUserId14() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(getPortletInstanceKey(), "1234"));

		Assert.assertFalse(portletInstance.hasUserId());
	}

	@Test
	public void testGetUserId15() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(getPortletInstanceKey(), 1234));

		Assert.assertTrue(portletInstance.hasUserId());
	}

	@Test
	public void testGetUserId16() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(getPortletInstanceKey(), 1234, "5678"));

		Assert.assertTrue(portletInstance.hasUserId());
	}

	@Test
	public void testHasInstanceId1() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(PortletKeys.TEST);

		Assert.assertFalse(portletInstance.hasInstanceId());
	}

	@Test
	public void testHasInstanceId2() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(PortletKeys.TEST, "1234"));

		Assert.assertTrue(portletInstance.hasInstanceId());
	}

	@Test
	public void testHasInstanceId3() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(PortletKeys.TEST, 1234));

		Assert.assertFalse(portletInstance.hasInstanceId());
	}

	@Test
	public void testHasInstanceId4() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(PortletKeys.TEST, 1234, "5678"));

		Assert.assertTrue(portletInstance.hasInstanceId());
	}

	@Test
	public void testHasInstanceId5() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(getPortletInstanceKey());

		Assert.assertFalse(portletInstance.hasInstanceId());
	}

	@Test
	public void testHasInstanceId6() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(getPortletInstanceKey(), "1234"));

		Assert.assertTrue(portletInstance.hasInstanceId());
	}

	@Test
	public void testHasInstanceId7() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(getPortletInstanceKey(), 1234));

		Assert.assertFalse(portletInstance.hasInstanceId());
	}

	@Test
	public void testHasInstanceId8() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				getPortletInstanceKey(getPortletInstanceKey(), 1234, "5678"));

		Assert.assertTrue(portletInstance.hasInstanceId());
	}

	protected String getPortletInstanceKey() {
		return PortletKeys.TEST + PortletConstants.WAR_SEPARATOR + "context";
	}

	protected String getPortletInstanceKey(String portletName, long userId) {
		PortletInstance portletInstance = new PortletInstance(
			portletName, userId, null);

		return portletInstance.getPortletInstanceKey();
	}

	protected String getPortletInstanceKey(
		String portletName, long userId, String instanceId) {

		PortletInstance portletInstance = new PortletInstance(
			portletName, userId, instanceId);

		return portletInstance.getPortletInstanceKey();
	}

	protected String getPortletInstanceKey(
		String portletName, String instanceId) {

		PortletInstance portletInstance = new PortletInstance(
			portletName, instanceId);

		return portletInstance.getPortletInstanceKey();
	}

}