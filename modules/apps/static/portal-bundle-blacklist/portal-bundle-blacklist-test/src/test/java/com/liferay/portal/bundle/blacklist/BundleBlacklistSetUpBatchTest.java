/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.bundle.blacklist;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.lpkg.deployer.test.util.LPKGTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class BundleBlacklistSetUpBatchTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCreateAndBlacklistTestBundles() throws Exception {
		String liferayHome = SystemProperties.get("liferay.home");

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

		String configBody = StringBundler.concat(
			"blacklistBundleSymbolicNames=\"", _JAR_BUNDLE_SYMBOLIC_NAME,
			StringPool.COMMA, _WAR_BUNDLE_SYMBOLIC_NAME, StringPool.QUOTE);

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