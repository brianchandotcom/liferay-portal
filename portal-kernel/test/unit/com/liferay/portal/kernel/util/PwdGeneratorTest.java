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