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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.upgrade.MockPortletPreferences;
import com.liferay.portal.kernel.util.StringPool;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Iván Zaera
 */
public class UpgradeMessageBoardsTest {

	@Test
	public void testUpgradeEmailSignatureWithHtmlFormat() throws Exception {
		_portletPreferences.setValue("emailHtmlFormat", String.valueOf(true));

		_portletPreferences.setValue("messageBody", "the body");

		_portletPreferences.setValue("messageSignature", "the signature");

		_upgradeMessageBoards.upgradeEmailSignature(
			_portletPreferences, "messageBody", "messageSignature");

		String messageBody = _portletPreferences.getValue(
			"messageBody", StringPool.BLANK);

		String messageSignature = _portletPreferences.getValue(
			"messageSignature", StringPool.BLANK);

		Assert.assertEquals("the body<br />--<br />the signature", messageBody);

		Assert.assertEquals(messageSignature, StringPool.BLANK);
	}

	@Test
	public void testUpgradeEmailSignatureWithNonHtmlFormat() throws Exception {
		_portletPreferences.setValue("emailHtmlFormat", String.valueOf(false));

		_portletPreferences.setValue("messageBody", "the body");

		_portletPreferences.setValue("messageSignature", "the signature");

		_upgradeMessageBoards.upgradeEmailSignature(
			_portletPreferences, "messageBody", "messageSignature");

		String messageBody = _portletPreferences.getValue(
			"messageBody", StringPool.BLANK);

		String messageSignature = _portletPreferences.getValue(
			"messageSignature", StringPool.BLANK);

		Assert.assertEquals("the body\n--\nthe signature", messageBody);

		Assert.assertEquals(messageSignature, StringPool.BLANK);
	}

	@Test
	public void testUpgradeThreadPriorities() throws Exception {
		_portletPreferences.setValues(
			"priorities", new String[] {
				"Urgent,/message_boards/priority_urgent.png,3.0",
				"Sticky,/message_boards/priority_sticky.png,2.0",
				"Announcement,/message_boards/priority_announcement.png,1.0"
			}
		);

		_upgradeMessageBoards.upgradeThreadPriorities(_portletPreferences);

		String[] values = _portletPreferences.getValues(
			"priorities", StringPool.EMPTY_ARRAY);

		Assert.assertArrayEquals(
			new String[] {
				"Urgent|/message_boards/priority_urgent.png|3.0",
				"Sticky|/message_boards/priority_sticky.png|2.0",
				"Announcement|/message_boards/priority_announcement.png|1.0"
			}, values);
	}

	private PortletPreferences _portletPreferences =
		new MockPortletPreferences();
	private UpgradeMessageBoards _upgradeMessageBoards =
		new UpgradeMessageBoards();

}