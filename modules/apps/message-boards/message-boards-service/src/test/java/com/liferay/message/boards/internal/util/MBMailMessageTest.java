/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.util;

import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.InputStream;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Eduardo Pérez
 */
public class MBMailMessageTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAddBytes() {
		MBMailMessage mbMailMessage = new MBMailMessage();

		mbMailMessage.addBytes("=?UTF-8?Q?T=C3=ADlde.txt?=", new byte[0]);

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			mbMailMessage.getInputStreamOVPs();

		ObjectValuePair<String, InputStream> inputStreamOVP =
			inputStreamOVPs.get(0);

		Assert.assertEquals("T\u00EDlde.txt", inputStreamOVP.getKey());
	}

}