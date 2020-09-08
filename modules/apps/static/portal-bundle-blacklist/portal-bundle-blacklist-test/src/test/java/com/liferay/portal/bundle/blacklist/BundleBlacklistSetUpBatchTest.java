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

package com.liferay.portal.bundle.blacklist;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.lpkg.deployer.test.util.LPKGTestUtil;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class BundleBlacklistSetUpBatchTest {

	@Test
	public void testCreateAndBlacklistTestBundles() throws Exception {
		String liferayHome = System.getProperty("liferay.home");

		Assert.assertNotNull(
			"Missing system property \"liferay.home\"", liferayHome);

		String blacklistConfigName = System.getProperty(
			"blacklist.config.name");

		Assert.assertNotNull(
			"Missing system property \"blacklist.config.name\"",
			blacklistConfigName);

		try (OutputStream outputStream = new FileOutputStream(
				StringBundler.concat(
					liferayHome, "/osgi/portal/", _JAR_BUNDLE_SYMBOLIC_NAME,
					".jar"))) {

			StreamUtil.transfer(
				LPKGTestUtil.createJAR(_JAR_BUNDLE_SYMBOLIC_NAME),
				outputStream);
		}

		try (OutputStream outputStream = new FileOutputStream(
				StringBundler.concat(
					liferayHome, "/osgi/war/", _WAR_BUNDLE_SYMBOLIC_NAME,
					".war"))) {

			StreamUtil.transfer(
				LPKGTestUtil.createWAR(_WAR_BUNDLE_SYMBOLIC_NAME),
				outputStream);
		}

		StringBundler sb = new StringBundler(6);

		sb.append("blacklistBundleSymbolicNames=");
		sb.append(StringPool.QUOTE);
		sb.append(_JAR_BUNDLE_SYMBOLIC_NAME);
		sb.append(StringPool.COMMA);
		sb.append(_WAR_BUNDLE_SYMBOLIC_NAME);
		sb.append(StringPool.QUOTE);

		String configBody = sb.toString();

		try (OutputStream outputStream = new FileOutputStream(
				liferayHome + "/osgi/configs/" + blacklistConfigName)) {

			outputStream.write(configBody.getBytes());
		}
	}

	private static final String _JAR_BUNDLE_SYMBOLIC_NAME =
		"com.liferay.portal.bundle.blacklist.test.bundle";

	private static final String _WAR_BUNDLE_SYMBOLIC_NAME =
		_JAR_BUNDLE_SYMBOLIC_NAME.concat(".war");

}