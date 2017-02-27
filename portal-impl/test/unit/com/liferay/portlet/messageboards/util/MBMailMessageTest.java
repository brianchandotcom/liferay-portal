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

package com.liferay.portlet.messageboards.util;

import com.liferay.portal.kernel.util.ObjectValuePair;

import java.io.InputStream;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Eduardo Perez
 */
@RunWith(PowerMockRunner.class)
public class MBMailMessageTest {

	@Test
	public void testAddBytes() throws Exception {
		byte[] bytes = new byte[0];
		String expected = "Tílde.txt";
		String fileName = "=?UTF-8?Q?T=C3=83=C2=ADlde.txt?=";

		MBMailMessage mbMailMessage = new MBMailMessage();

		mbMailMessage.addBytes(fileName, bytes);

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			mbMailMessage.getInputStreamOVPs();

		ObjectValuePair<String, InputStream> inputStreamOVP =
			inputStreamOVPs.get(0);

		String result = inputStreamOVP.getKey();

		Assert.assertEquals(expected, result);
	}

}