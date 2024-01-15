/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.upgrade.client.util;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.tools.db.upgrade.client.AppServer;

import java.io.File;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Jorge Avalos
 */
public class AppServerTest {

	@ClassRule
	public static TemporaryFolder temporaryFolder = new TemporaryFolder();

	@BeforeClass
	public static void setUpClass() {
		File jarLocation = new File(
			temporaryFolder.getRoot(), "tools/upgradeclient");

		jarLocation.mkdirs();

		ReflectionTestUtil.setFieldValue(
			AppServer.class, "_jarDir", jarLocation);
	}

	@Test
	public void testGetCustomAppServer() {
		File dirFile = new File(
			temporaryFolder.getRoot(), RandomTestUtil.randomString());

		dirFile.mkdir();

		String extraDirLibNames = RandomTestUtil.randomString();
		String globalDirLibNames = RandomTestUtil.randomString();
		String portalDirName = RandomTestUtil.randomString();
		String serverDetectorServerId = RandomTestUtil.randomString();

		AppServer appServer = new AppServer(
			dirFile.getAbsolutePath(), extraDirLibNames, globalDirLibNames,
			portalDirName, serverDetectorServerId);

		_assert(
			appServer, dirFile, extraDirLibNames, globalDirLibNames,
			portalDirName, serverDetectorServerId);

		dirFile = new File(
			temporaryFolder.getRoot(), RandomTestUtil.randomString());

		dirFile.mkdir();

		extraDirLibNames = RandomTestUtil.randomString();
		globalDirLibNames = RandomTestUtil.randomString();
		portalDirName = RandomTestUtil.randomString();

		appServer.setDirName(dirFile.getAbsolutePath());
		appServer.setPortalDirName(portalDirName);
		appServer.setExtraLibDirNames(extraDirLibNames);
		appServer.setGlobalLibDirName(globalDirLibNames);

		_assert(
			appServer, dirFile, extraDirLibNames, globalDirLibNames,
			portalDirName, serverDetectorServerId);
	}

	@Test
	public void testGetTomcatAppServer() {
		File dirFile = new File(temporaryFolder.getRoot(), "tomcat");

		dirFile.mkdir();

		AppServer appServer = AppServer.getTomcatAppServer();

		_assert(
			appServer, dirFile, "bin", "lib",
			"webapps" + File.separator + "ROOT", "tomcat");

		dirFile.delete();

		dirFile = new File(temporaryFolder.getRoot(), "tomcat-9.0.80");

		dirFile.mkdirs();

		appServer = AppServer.getTomcatAppServer();

		_assert(
			appServer, dirFile, "bin", "lib",
			"webapps" + File.separator + "ROOT", "tomcat");
	}

	private void _assert(
		AppServer appServer, File dirFile, String extraDirLibName,
		String globalDirLibName, String portalDirName,
		String serverDetectorServerId) {

		Assert.assertEquals(dirFile, appServer.getDir());

		File portalDir = new File(dirFile, portalDirName);

		Assert.assertEquals(portalDir, appServer.getPortalDir());

		File portalClassesDir = new File(
			portalDir, "WEB-INF" + File.separator + "classes");

		Assert.assertEquals(portalClassesDir, appServer.getPortalClassesDir());

		File portalLibDir = new File(
			portalDir, "WEB-INF" + File.separator + "lib");

		Assert.assertEquals(portalLibDir, appServer.getPortalLibDir());

		File portalShieldContainerLibDir = new File(
			portalDir, "WEB-INF" + File.separator + "shielded-container-lib");

		Assert.assertEquals(
			portalShieldContainerLibDir,
			appServer.getPortalShieldedContainerLibDir());

		File extraLibDir = new File(dirFile, extraDirLibName);

		Assert.assertEquals(
			extraLibDir,
			appServer.getExtraLibDirs(
			).get(
				0
			));

		File globalLibDir = new File(dirFile, globalDirLibName);

		Assert.assertEquals(globalLibDir, appServer.getGlobalLibDir());

		Assert.assertEquals(
			serverDetectorServerId, appServer.getServerDetectorServerId());
	}

}