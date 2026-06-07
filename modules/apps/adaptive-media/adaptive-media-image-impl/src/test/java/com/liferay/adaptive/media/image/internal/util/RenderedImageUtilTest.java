/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.util;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Akhash Ramprakash
 */
public class RenderedImageUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testReadImage() throws Exception {
		CloseTrackingByteArrayInputStream closeTrackingByteArrayInputStream =
			new CloseTrackingByteArrayInputStream(RandomTestUtil.randomBytes());

		try {
			RenderedImageUtil.readImage(closeTrackingByteArrayInputStream);

			Assert.fail();
		}
		catch (IOException ioException) {
			Assert.assertEquals(
				"Unsupported image type", ioException.getMessage());
		}

		Assert.assertTrue(closeTrackingByteArrayInputStream._closed);
	}

	private static class CloseTrackingByteArrayInputStream
		extends ByteArrayInputStream {

		public CloseTrackingByteArrayInputStream(byte[] bytes) {
			super(bytes);
		}

		@Override
		public void close() throws IOException {
			_closed = true;

			super.close();
		}

		private boolean _closed;

	}

}