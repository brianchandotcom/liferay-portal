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

package com.liferay.portal.kernel.model;

import com.liferay.petra.string.StringPool;
import com.liferay.portlet.util.test.PortletKeys;

import java.security.InvalidParameterException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author     Raymond Augé
 * @author     Manuel de la Peña
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class PortletInstanceTest {

	@Test
	public void testFromPortletNameAndUserIdAndInstanceId() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletNameAndUserIdAndInstanceId(
				PortletKeys.TEST, "1234_xyz");

		Assert.assertEquals(
			"1234_xyz", portletInstance.getUserIdAndInstanceId());
		Assert.assertEquals("xyz", portletInstance.getInstanceId());
		Assert.assertEquals(PortletKeys.TEST, portletInstance.getPortletName());
		Assert.assertEquals(1234, portletInstance.getUserId());
	}

	@Test
	public void testFromPortletNameAndUserIdAndInstanceIdEmpty() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletNameAndUserIdAndInstanceId(
				PortletKeys.TEST, StringPool.BLANK);

		Assert.assertEquals(null, portletInstance.getUserIdAndInstanceId());
		Assert.assertEquals(null, portletInstance.getInstanceId());
		Assert.assertEquals(PortletKeys.TEST, portletInstance.getPortletName());
		Assert.assertEquals(0, portletInstance.getUserId());
	}

	@Test
	public void testFromPortletNameAndUserIdAndInstanceIdNull() {
		try {
			PortletInstance.fromPortletNameAndUserIdAndInstanceId(
				PortletKeys.TEST, null);

			Assert.fail();
		}
		catch (InvalidParameterException ipe) {
		}
	}

	@Test
	public void testFromPortletNameAndUserIdAndInstanceIdWithInvalidNumber1() {
		try {
			PortletInstance.fromPortletNameAndUserIdAndInstanceId(
				PortletKeys.TEST, "12a34_xyz");

			Assert.fail();
		}
		catch (InvalidParameterException ipe) {
		}
	}

	@Test
	public void testFromPortletNameAndUserIdAndInstanceIdWithInvalidNumber2() {
		try {
			PortletInstance.fromPortletNameAndUserIdAndInstanceId(
				PortletKeys.TEST, "abcd_xyz");

			Assert.fail();
		}
		catch (InvalidParameterException ipe) {
		}
	}

	@Test
	public void testFromPortletNameAndUserIdAndInstanceIdWithInvalidSlash1() {
		try {
			PortletInstance.fromPortletNameAndUserIdAndInstanceId(
				PortletKeys.TEST, "/1234_xyz");

			Assert.fail();
		}
		catch (InvalidParameterException ipe) {
		}
	}

	@Test
	public void testFromPortletNameAndUserIdAndInstanceIdWithInvalidSlash2() {
		try {
			PortletInstance.fromPortletNameAndUserIdAndInstanceId(
				PortletKeys.TEST, "12/34_xyz");

			Assert.fail();
		}
		catch (InvalidParameterException ipe) {
		}
	}

	@Test
	public void testFromPortletNameAndUserIdAndInstanceIdWithInvalidSlash3() {
		try {
			PortletInstance.fromPortletNameAndUserIdAndInstanceId(
				PortletKeys.TEST, "1234/_xyz");

			Assert.fail();
		}
		catch (InvalidParameterException ipe) {
		}
	}

	@Test
	public void testFromPortletNameAndUserIdAndInstanceIdWithInvalidSlash4() {
		try {
			PortletInstance.fromPortletNameAndUserIdAndInstanceId(
				PortletKeys.TEST, "1234_/xyz");

			Assert.fail();
		}
		catch (InvalidParameterException ipe) {
		}
	}

	@Test
	public void testFromPortletNameAndUserIdAndInstanceIdWithInvalidSlash5() {
		try {
			PortletInstance.fromPortletNameAndUserIdAndInstanceId(
				PortletKeys.TEST, "1234_x/yz");

			Assert.fail();
		}
		catch (InvalidParameterException ipe) {
		}
	}

	@Test
	public void testFromPortletNameAndUserIdAndInstanceIdWithInvalidSlash6() {
		try {
			PortletInstance.fromPortletNameAndUserIdAndInstanceId(
				PortletKeys.TEST, "1234_xyz/");

			Assert.fail();
		}
		catch (InvalidParameterException ipe) {
		}
	}

	@Test
	public void testFromPortletNameAndUserIdAndInstanceIdWithInvalidTokens1() {
		try {
			PortletInstance.fromPortletNameAndUserIdAndInstanceId(
				PortletKeys.TEST, "1234xyz_");

			Assert.fail();
		}
		catch (InvalidParameterException ipe) {
		}
	}

	@Test
	public void testFromPortletNameAndUserIdAndInstanceIdWithInvalidTokens2() {
		try {
			PortletInstance.fromPortletNameAndUserIdAndInstanceId(
				PortletKeys.TEST, "_1234xyz");

			Assert.fail();
		}
		catch (InvalidParameterException ipe) {
		}
	}

	@Test
	public void testFromPortletNameAndUserIdAndInstanceIdWithInvalidTokens3() {
		try {
			PortletInstance.fromPortletNameAndUserIdAndInstanceId(
				PortletKeys.TEST, "__1234xyz");

			Assert.fail();
		}
		catch (InvalidParameterException ipe) {
		}
	}

	@Test
	public void testFromPortletNameAndUserIdAndInstanceIdWithInvalidTokens4() {
		try {
			PortletInstance.fromPortletNameAndUserIdAndInstanceId(
				PortletKeys.TEST, "1234__xyz");

			Assert.fail();
		}
		catch (InvalidParameterException ipe) {
		}
	}

	@Test
	public void testFromPortletNameAndUserIdAndInstanceIdWithInvalidTokens5() {
		try {
			PortletInstance.fromPortletNameAndUserIdAndInstanceId(
				PortletKeys.TEST, "1234xyz__");

			Assert.fail();
		}
		catch (InvalidParameterException ipe) {
		}
	}

	@Test
	public void testFromPortletNameAndUserIdAndInstanceIdWithInvalidTokens6() {
		try {
			PortletInstance.fromPortletNameAndUserIdAndInstanceId(
				PortletKeys.TEST, "12_34_xyz");

			Assert.fail();
		}
		catch (InvalidParameterException ipe) {
		}
	}

	@Test
	public void testFromPortletNameAndUserIdAndInstanceIdWithInvalidTokens7() {
		try {
			PortletInstance.fromPortletNameAndUserIdAndInstanceId(
				PortletKeys.TEST, "1234_x_yz");

			Assert.fail();
		}
		catch (InvalidParameterException ipe) {
		}
	}

	@Test
	public void testFromPortletNameAndUserIdAndInstanceIdWithInvalidTokens8() {
		try {
			PortletInstance.fromPortletNameAndUserIdAndInstanceId(
				PortletKeys.TEST, "12_34_x_yz");

			Assert.fail();
		}
		catch (InvalidParameterException ipe) {
		}
	}

	@Test
	public void testFromPortletNameAndUserIdAndInstanceIdWithOnlyInstanceId() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletNameAndUserIdAndInstanceId(
				PortletKeys.TEST, "xyz");

		Assert.assertEquals("xyz", portletInstance.getUserIdAndInstanceId());
		Assert.assertEquals("xyz", portletInstance.getInstanceId());
		Assert.assertEquals(PortletKeys.TEST, portletInstance.getPortletName());
		Assert.assertEquals(0, portletInstance.getUserId());
	}

	@Test
	public void testFromPortletNameAndUserIdAndInstanceIdWithOnlyUserId() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletNameAndUserIdAndInstanceId(
				PortletKeys.TEST, "1234_");

		Assert.assertEquals("1234_", portletInstance.getUserIdAndInstanceId());
		Assert.assertEquals(null, portletInstance.getInstanceId());
		Assert.assertEquals(PortletKeys.TEST, portletInstance.getPortletName());
		Assert.assertEquals(1234, portletInstance.getUserId());
	}

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
	public void testGetUserIdAndInstanceId() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				PortletKeys.TEST + "_USER_1234_INSTANCE_xyz");

		Assert.assertEquals(
			"1234_xyz", portletInstance.getUserIdAndInstanceId());
		Assert.assertEquals("xyz", portletInstance.getInstanceId());
		Assert.assertEquals(1234, portletInstance.getUserId());
	}

	@Test
	public void testGetUserIdAndInstanceIdEmpty() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(PortletKeys.TEST);

		Assert.assertEquals(null, portletInstance.getUserIdAndInstanceId());
		Assert.assertEquals(null, portletInstance.getInstanceId());
		Assert.assertEquals(0, portletInstance.getUserId());
	}

	@Test
	public void testGetUserIdAndInstanceIdWithOnlyInstanceId() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				PortletKeys.TEST + "_INSTANCE_xyz");

		Assert.assertEquals("xyz", portletInstance.getInstanceId());
		Assert.assertEquals("xyz", portletInstance.getUserIdAndInstanceId());
		Assert.assertEquals(0, portletInstance.getUserId());
	}

	@Test
	public void testGetUserIdAndInstanceIdWithOnlyUserId() {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(
				PortletKeys.TEST + "_USER_1234");

		Assert.assertEquals("1234_", portletInstance.getUserIdAndInstanceId());
		Assert.assertEquals(null, portletInstance.getInstanceId());
		Assert.assertEquals(1234, portletInstance.getUserId());
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