/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.lpkg.deployer.persistence;

import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.lpkg.deployer.test.util.LPKGTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class LPKGPersistenceTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testLPKGPersistenceDeploy() throws Exception {
		String liferayHome = SystemProperties.get("liferay.home");

		Assert.assertNotNull(
			"Missing system property \"liferay.home\"", liferayHome);

		Path path = Paths.get(
			liferayHome, "osgi/marketplace/Liferay Persistence Test.lpkg");

		Files.createFile(path);

		LPKGTestUtil.createLPKG(path, _SYMBOLIC_NAME, false);
	}

	private static final String _SYMBOLIC_NAME = "lpkg.persistence.test";

}