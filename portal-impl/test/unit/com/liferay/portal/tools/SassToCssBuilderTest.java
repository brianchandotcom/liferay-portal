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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Eduardo Garcia
 */
public class SassToCssBuilderTest {

	@After
	public void tearDown() throws Exception {
		FileUtil.deltree(_DOCROOT_DIR_NAME + _DIR_NAME + _SASS_CACHE_DIR_NAME);
	}

	@Test
	public void testSassToCssBuilder() throws Exception {
		SassToCssBuilder sassToCssBuilder = new SassToCssBuilder(
			_DOCROOT_DIR_NAME, "portal-web/docroot/html/css/common",
			"jni");

		sassToCssBuilder.execute(ListUtil.fromArray(new String[] { _DIR_NAME}));

		String expectedCacheContent = FileUtil.read(
			_DOCROOT_DIR_NAME + _EXPECTED_DIR_NAME + _FILE_NAME);
		String actualCacheContent = FileUtil.read(
			_DOCROOT_DIR_NAME + _DIR_NAME + _SASS_CACHE_DIR_NAME + _FILE_NAME);

		Assert.assertEquals(expectedCacheContent, actualCacheContent);

		String expectedRtlCacheContent = FileUtil.read(
			_DOCROOT_DIR_NAME + _EXPECTED_DIR_NAME + _RTL_FILE_NAME);
		String actualRtlCacheContent = FileUtil.read(
			_DOCROOT_DIR_NAME + _DIR_NAME + _SASS_CACHE_DIR_NAME +
				_RTL_FILE_NAME);

		Assert.assertEquals(expectedRtlCacheContent, actualRtlCacheContent);
	}

	private static final String _DIR_NAME = "/css";

	private static final String _DOCROOT_DIR_NAME =
		"portal-impl/test/integration/com/liferay/portal/tools/dependencies";

	private static final String _EXPECTED_DIR_NAME = "/expected";

	private static final String _FILE_NAME = "/test.css";

	private static final String _RTL_FILE_NAME = "/test_rtl.css";

	private static final String _SASS_CACHE_DIR_NAME = "/.sass-cache";

}