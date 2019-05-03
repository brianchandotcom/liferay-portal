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

package com.liferay.document.library.internal.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class InputStreamUtilTest {

	@Test
	public void testBufferedInputStreamIsNotWrapped() {
		InputStream inputStream = new BufferedInputStream(
			new ByteArrayInputStream(new byte[0]));

		Assert.assertSame(
			inputStream, InputStreamUtil.toBufferedInputStream(inputStream));
	}

	@Test
	public void testUnbufferedInputStreamIsWrapped() {
		InputStream inputStream = new ByteArrayInputStream(new byte[0]);

		Assert.assertNotSame(
			inputStream, InputStreamUtil.toBufferedInputStream(inputStream));
	}

}