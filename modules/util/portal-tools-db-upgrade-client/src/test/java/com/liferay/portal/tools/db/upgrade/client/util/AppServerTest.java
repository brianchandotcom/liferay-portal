/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.upgrade.client.util;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.tools.db.upgrade.client.AppServer;

import java.io.File;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Jorge Avalos
 */
public class AppServerTest {

	@ClassRule
	public static TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Test
	public void testGetAppServer() throws Exception {
		File tempFile = temporaryFolder.getRoot();

		File dirFile = new File(tempFile, "tomcat");

		dirFile.mkdir();

		AppServer appServer = AppServer.getAppServer(
			new File(tempFile.getCanonicalPath()), "tomcat");

		_assertAppServer(
			appServer, dirFile, "bin", "lib",
			"webapps" + File.separator + "ROOT", "tomcat");

		dirFile.delete();

		dirFile = new File(tempFile, "tomcat-9.0.80");

		dirFile.mkdirs();

		appServer = AppServer.getAppServer(
			new File(tempFile.getCanonicalPath()), "tomcat");

		_assertAppServer(
			appServer, dirFile, "bin", "lib",
			"webapps" + File.separator + "ROOT", "tomcat");
	}

	@Test
	public void testNewAppServer() {
		File dir = new File(
			temporaryFolder.getRoot(), RandomTestUtil.randomString());

		dir.mkdir();

		String extraDirLibName = RandomTestUtil.randomString();
		String globalDirLibName = RandomTestUtil.randomString();
		String portalDirName = RandomTestUtil.randomString();
		String serverDetectorServerId = RandomTestUtil.randomString();

		AppServer appServer = new AppServer(
			dir.getAbsolutePath(), extraDirLibName, globalDirLibName,
			portalDirName, serverDetectorServerId);

		_assertAppServer(
			appServer, dir, extraDirLibName, globalDirLibName, portalDirName,
			serverDetectorServerId);

		dir = new File(
			temporaryFolder.getRoot(), RandomTestUtil.randomString());

		dir.mkdir();

		extraDirLibName = RandomTestUtil.randomString();
		globalDirLibName = RandomTestUtil.randomString();
		portalDirName = RandomTestUtil.randomString();

		appServer.setDirName(dir.getAbsolutePath());
		appServer.setPortalDirName(portalDirName);
		appServer.setExtraLibDirNames(extraDirLibName);
		appServer.setGlobalLibDirName(globalDirLibName);

		_assertAppServer(
			appServer, dir, extraDirLibName, globalDirLibName, portalDirName,
			serverDetectorServerId);
	}

	private void _assertAppServer(
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