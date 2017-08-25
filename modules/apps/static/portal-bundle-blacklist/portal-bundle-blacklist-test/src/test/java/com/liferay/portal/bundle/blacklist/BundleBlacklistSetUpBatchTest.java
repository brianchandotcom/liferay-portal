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

import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.lpkg.deployer.test.util.LPKGTestUtil;

import java.io.File;
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

		String blacklistCfgName = System.getProperty("blacklist.cfg.name");

		Assert.assertNotNull(
			"Missing system property \"cfg.name\"", blacklistCfgName);

		try (OutputStream outputStream = new FileOutputStream(
				new File(
					liferayHome + "/osgi/portal/" + _SYMBOLIC_NAME + ".jar"))) {

			StreamUtil.transfer(
				LPKGTestUtil.createJAR(_SYMBOLIC_NAME), outputStream);
		}

		try (OutputStream outputStream = new FileOutputStream(
				new File(
					liferayHome + "/deploy/" + _WAR_SYMBOLIC_NAME + ".war"))) {

			StreamUtil.transfer(
				LPKGTestUtil.createWAR(_WAR_SYMBOLIC_NAME), outputStream);
		}

		StringBundler sb = new StringBundler(4);

		sb.append("blacklistBundleSymbolicNames=");
		sb.append(_SYMBOLIC_NAME);
		sb.append(StringPool.COMMA);
		sb.append(_WAR_SYMBOLIC_NAME);

		String cfgBody = sb.toString();

		try (OutputStream outputStream = new FileOutputStream(
				new File(liferayHome + "/osgi/configs/" + blacklistCfgName))) {

			outputStream.write(cfgBody.getBytes());
		}
	}

	private static final String _SYMBOLIC_NAME =
		"com.liferay.portal.bundle.blacklist.test.bundle";

	private static final String _WAR_SYMBOLIC_NAME = _SYMBOLIC_NAME.concat(
		".war");

}