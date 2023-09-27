/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Brian Wing Shun Chan
 */
public class PwdGeneratorTest {

	@Test
	public void testGetPassword() {
		long start = System.currentTimeMillis();

		for (int i = 0; i < 100000; i++) {
			PwdGenerator.getPassword();
		}

		long end = System.currentTimeMillis();

		long delta = end - start;

		if (_log.isInfoEnabled()) {
			_log.info(
				"Generated 100 thousand secure passwords in " + delta + " ms");
		}

		Assert.assertTrue(delta < 2000);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PwdGeneratorTest.class);

}