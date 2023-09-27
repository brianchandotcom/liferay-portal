/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.util;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class InputStreamUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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