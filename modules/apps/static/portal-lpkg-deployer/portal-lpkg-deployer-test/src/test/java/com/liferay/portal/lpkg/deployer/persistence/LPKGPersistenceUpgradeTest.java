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

package com.liferay.portal.lpkg.deployer.persistence;

import com.liferay.portal.lpkg.deployer.test.util.LPKGTestUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

import org.osgi.framework.Version;

/**
 * @author Matthew Tambara
 */
public class LPKGPersistenceUpgradeTest {

	@Test
	public void testLPKGPersistenceUpgrade() throws Exception {
		String liferayHome = System.getProperty("liferay.home");

		Assert.assertNotNull(
			"Missing system property \"liferay.home\"", liferayHome);

		Path path = Paths.get(
			liferayHome, "osgi/marketplace/Liferay Persistence Test.lpkg");

		Files.delete(path);

		Files.createFile(path);

		Version version = new Version(2, 0, 0);

		LPKGTestUtil.createLPKG(path, _SYMBOLIC_NAME, false, version, version);
	}

	private static final String _SYMBOLIC_NAME = "lpkg.persistence.test";

}