/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.security.InvalidParameterException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class PortletIdCodecTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testEncodeDecode() {
		_testEncodeDecode(_TEST_PORTLET_NAME);
		_testEncodeDecode(_TEST_PORTLET_NAME_WAR);
	}

	@Test
	public void testEncodeDecodeUserIdAndInstanceId() {

		// Test 1

		String userIdAndInstanceId = PortletIdCodec.encodeUserIdAndInstanceId(
			0, null);

		Assert.assertNull(userIdAndInstanceId);

		try {
			PortletIdCodec.decodeUserIdAndInstanceId(userIdAndInstanceId);

			Assert.fail();
		}
		catch (InvalidParameterException invalidParameterException) {
			Assert.assertEquals(
				"User ID and instance ID are null",
				invalidParameterException.getMessage());
		}

		// Test 2

		userIdAndInstanceId = PortletIdCodec.encodeUserIdAndInstanceId(0, "");

		Assert.assertNull(userIdAndInstanceId);

		try {
			PortletIdCodec.decodeUserIdAndInstanceId(userIdAndInstanceId);

			Assert.fail();
		}
		catch (InvalidParameterException invalidParameterException) {
			Assert.assertEquals(
				"User ID and instance ID are null",
				invalidParameterException.getMessage());
		}

		// Test 3

		userIdAndInstanceId = PortletIdCodec.encodeUserIdAndInstanceId(
			1234, null);

		Assert.assertEquals("1234_", userIdAndInstanceId);

		ObjectValuePair<Long, String> objectValuePair =
			PortletIdCodec.decodeUserIdAndInstanceId(userIdAndInstanceId);

		Assert.assertEquals(Long.valueOf(1234), objectValuePair.getKey());
		Assert.assertNull(objectValuePair.getValue());

		// Test 4

		userIdAndInstanceId = PortletIdCodec.encodeUserIdAndInstanceId(
			1234, "");

		Assert.assertEquals("1234_", userIdAndInstanceId);

		objectValuePair = PortletIdCodec.decodeUserIdAndInstanceId(
			userIdAndInstanceId);

		Assert.assertEquals(Long.valueOf(1234), objectValuePair.getKey());
		Assert.assertNull(objectValuePair.getValue());

		// Test 5

		userIdAndInstanceId = PortletIdCodec.encodeUserIdAndInstanceId(
			1234, "5678");

		Assert.assertEquals("1234_5678", userIdAndInstanceId);

		objectValuePair = PortletIdCodec.decodeUserIdAndInstanceId(
			userIdAndInstanceId);

		Assert.assertEquals(Long.valueOf(1234), objectValuePair.getKey());
		Assert.assertEquals("5678", objectValuePair.getValue());

		// Test 6

		userIdAndInstanceId = PortletIdCodec.encodeUserIdAndInstanceId(
			0, "5678");

		Assert.assertEquals("5678", userIdAndInstanceId);

		objectValuePair = PortletIdCodec.decodeUserIdAndInstanceId(
			userIdAndInstanceId);

		Assert.assertEquals(Long.valueOf(0), objectValuePair.getKey());
		Assert.assertEquals("5678", objectValuePair.getValue());

		// Test 7

		try {
			PortletIdCodec.decodeUserIdAndInstanceId("/");

			Assert.fail();
		}
		catch (InvalidParameterException invalidParameterException) {
			Assert.assertEquals(
				"User ID and instance ID contain slashes",
				invalidParameterException.getMessage());
		}

		// Test 8

		objectValuePair = PortletIdCodec.decodeUserIdAndInstanceId("");

		Assert.assertEquals(Long.valueOf(0), objectValuePair.getKey());
		Assert.assertNull(objectValuePair.getValue());

		// Test 9

		try {
			PortletIdCodec.decodeUserIdAndInstanceId("1234_5678_");

			Assert.fail();
		}
		catch (InvalidParameterException invalidParameterException) {
			Assert.assertEquals(
				"User ID and instance ID has more than one underscore",
				invalidParameterException.getMessage());
		}

		// Test 10

		try {
			PortletIdCodec.decodeUserIdAndInstanceId("UserId_");

			Assert.fail();
		}
		catch (InvalidParameterException invalidParameterException) {
			Assert.assertEquals(
				"User ID is not a number",
				invalidParameterException.getMessage());
		}
	}

	@Test
	public void testHasInstanceId() {
		_testHasInstanceId(_TEST_PORTLET_NAME);
		_testHasInstanceId(_TEST_PORTLET_NAME_WAR);
	}

	@Test
	public void testHasUserId() {
		_testHasUserId(_TEST_PORTLET_NAME);
		_testHasUserId(_TEST_PORTLET_NAME_WAR);
	}

	@Test
	public void testMisc() {
		Assert.assertNotNull(PortletIdCodec.generateInstanceId());

		new PortletIdCodec();
	}

	@Test
	public void testValidatePortletName() {
		_testValidatePortletName(_TEST_PORTLET_NAME);
		_testValidatePortletName(_TEST_PORTLET_NAME_WAR);
	}

	private void _testEncodeDecode(String portletName) {

		// Test 1

		String encodedPortletId = PortletIdCodec.encode(portletName);

		Assert.assertEquals(
			portletName, PortletIdCodec.decodePortletName(encodedPortletId));
		Assert.assertEquals(0, PortletIdCodec.decodeUserId(encodedPortletId));
		Assert.assertNotNull(PortletIdCodec.decodeInstanceId(encodedPortletId));

		// Test 2

		encodedPortletId = PortletIdCodec.encode(portletName, "1234");

		Assert.assertEquals(
			portletName, PortletIdCodec.decodePortletName(encodedPortletId));
		Assert.assertEquals(0, PortletIdCodec.decodeUserId(encodedPortletId));
		Assert.assertEquals(
			"1234", PortletIdCodec.decodeInstanceId(encodedPortletId));

		// Test 3

		encodedPortletId = PortletIdCodec.encode(portletName, 1234);

		Assert.assertEquals(
			portletName, PortletIdCodec.decodePortletName(encodedPortletId));
		Assert.assertEquals(
			1234, PortletIdCodec.decodeUserId(encodedPortletId));
		Assert.assertNull(PortletIdCodec.decodeInstanceId(encodedPortletId));

		// Test 4

		encodedPortletId = PortletIdCodec.encode(portletName, 1234, "5678");

		Assert.assertEquals(
			portletName, PortletIdCodec.decodePortletName(encodedPortletId));
		Assert.assertEquals(
			1234, PortletIdCodec.decodeUserId(encodedPortletId));
		Assert.assertEquals(
			"5678", PortletIdCodec.decodeInstanceId(encodedPortletId));

		// Test 5

		Assert.assertEquals(
			portletName, PortletIdCodec.decodePortletName(portletName));
		Assert.assertEquals(0, PortletIdCodec.decodeUserId(portletName));
		Assert.assertNull(PortletIdCodec.decodeInstanceId(portletName));
	}

	private void _testHasInstanceId(String portletName) {

		// Test 1

		String encodedPortletId = PortletIdCodec.encode(portletName);

		Assert.assertTrue(PortletIdCodec.hasInstanceId(encodedPortletId));

		// Test 2

		encodedPortletId = PortletIdCodec.encode(portletName, "1234");

		Assert.assertTrue(PortletIdCodec.hasInstanceId(encodedPortletId));

		// Test 3

		encodedPortletId = PortletIdCodec.encode(portletName, 1234);

		Assert.assertFalse(PortletIdCodec.hasInstanceId(encodedPortletId));

		// Test 4

		encodedPortletId = PortletIdCodec.encode(portletName, 1234, "5678");

		Assert.assertTrue(PortletIdCodec.hasInstanceId(encodedPortletId));
	}

	private void _testHasUserId(String portletName) {

		// Test 1

		String encodedPortletId = PortletIdCodec.encode(portletName);

		Assert.assertFalse(PortletIdCodec.hasUserId(encodedPortletId));

		// Test 2

		encodedPortletId = PortletIdCodec.encode(portletName, "1234");

		Assert.assertFalse(PortletIdCodec.hasUserId(encodedPortletId));

		// Test 3

		encodedPortletId = PortletIdCodec.encode(portletName, 1234);

		Assert.assertTrue(PortletIdCodec.hasUserId(encodedPortletId));

		// Test 4

		encodedPortletId = PortletIdCodec.encode(portletName, 1234, "5678");

		Assert.assertTrue(PortletIdCodec.hasUserId(encodedPortletId));
	}

	private void _testValidatePortletName(String portletName) {

		// Test 1

		PortletIdCodec.validatePortletName(portletName);

		// Test 2

		String encodedPortletId = PortletIdCodec.encode(portletName);

		try {
			PortletIdCodec.validatePortletName(encodedPortletId);

			Assert.fail();
		}
		catch (InvalidParameterException invalidParameterException) {
			Assert.assertEquals(
				"The portlet name \"" + encodedPortletId +
					"\" must not contain the keyword _INSTANCE_",
				invalidParameterException.getMessage());
		}

		// Test 3

		encodedPortletId = PortletIdCodec.encode(portletName, "1234");

		try {
			PortletIdCodec.validatePortletName(encodedPortletId);

			Assert.fail();
		}
		catch (InvalidParameterException invalidParameterException) {
			Assert.assertEquals(
				"The portlet name \"" + encodedPortletId +
					"\" must not contain the keyword _INSTANCE_",
				invalidParameterException.getMessage());
		}

		// Test 4

		encodedPortletId = PortletIdCodec.encode(portletName, 1234);

		try {
			PortletIdCodec.validatePortletName(encodedPortletId);

			Assert.fail();
		}
		catch (InvalidParameterException invalidParameterException) {
			Assert.assertEquals(
				"The portlet name \"" + encodedPortletId +
					"\" must not contain the keyword _USER_",
				invalidParameterException.getMessage());
		}

		// Test 5

		encodedPortletId = PortletIdCodec.encode(portletName, 1234, "5678");

		try {
			PortletIdCodec.validatePortletName(encodedPortletId);

			Assert.fail();
		}
		catch (InvalidParameterException invalidParameterException) {
			Assert.assertEquals(
				"The portlet name \"" + encodedPortletId +
					"\" must not contain the keyword _INSTANCE_",
				invalidParameterException.getMessage());
		}
	}

	private static final String _TEST_PORTLET_NAME =
		"com_liferay_test_portlet_TestPortlet";

	private static final String _TEST_PORTLET_NAME_WAR = StringBundler.concat(
		_TEST_PORTLET_NAME, PortletConstants.WAR_SEPARATOR, "context");

}