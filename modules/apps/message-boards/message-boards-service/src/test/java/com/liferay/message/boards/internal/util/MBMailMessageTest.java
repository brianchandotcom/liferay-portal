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

package com.liferay.message.boards.internal.util;

import com.liferay.portal.kernel.util.ObjectValuePair;

import java.io.InputStream;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Eduardo Pérez
 */
@RunWith(PowerMockRunner.class)
public class MBMailMessageTest {

	@Test
	public void testAddBytes() throws Exception {
		MBMailMessage mbMailMessage = new MBMailMessage();

		mbMailMessage.addBytes("=?UTF-8?Q?T=C3=ADlde.txt?=", new byte[0]);

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			mbMailMessage.getInputStreamOVPs();

		ObjectValuePair<String, InputStream> inputStreamOVP =
			inputStreamOVPs.get(0);

		Assert.assertEquals("T\u00EDlde.txt", inputStreamOVP.getKey());
	}

}