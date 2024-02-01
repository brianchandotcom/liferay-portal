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
		File rootDir = temporaryFolder.getRoot();

		File tomcatDir = new File(rootDir, "tomcat");

		tomcatDir.mkdir();

		AppServer appServer = AppServer.getAppServer(
			new File(rootDir.getCanonicalPath()), "tomcat");

		_assertAppServer(
			appServer, tomcatDir, "bin", "lib",
			"webapps" + File.separator + "ROOT", "tomcat");

		tomcatDir.delete();

		tomcatDir = new File(rootDir, "tomcat-9.0.80");

		tomcatDir.mkdirs();

		appServer = AppServer.getAppServer(
			new File(rootDir.getCanonicalPath()), "tomcat");

		_assertAppServer(
			appServer, tomcatDir, "bin", "lib",
			"webapps" + File.separator + "ROOT", "tomcat");
	}

	@Test
	public void testNewAppServer() {
		File appServerDir = new File(
			temporaryFolder.getRoot(), RandomTestUtil.randomString());

		appServerDir.mkdir();

		String extraDirLibName = RandomTestUtil.randomString();
		String globalDirLibName = RandomTestUtil.randomString();
		String portalDirName = RandomTestUtil.randomString();
		String serverDetectorServerId = RandomTestUtil.randomString();

		AppServer appServer = new AppServer(
			appServerDir.getAbsolutePath(), extraDirLibName, globalDirLibName,
			portalDirName, serverDetectorServerId);

		_assertAppServer(
			appServer, appServerDir, extraDirLibName, globalDirLibName,
			portalDirName, serverDetectorServerId);

		appServerDir = new File(
			temporaryFolder.getRoot(), RandomTestUtil.randomString());

		appServerDir.mkdir();

		extraDirLibName = RandomTestUtil.randomString();
		globalDirLibName = RandomTestUtil.randomString();
		portalDirName = RandomTestUtil.randomString();

		appServer.setDirName(appServerDir.getAbsolutePath());
		appServer.setPortalDirName(portalDirName);
		appServer.setExtraLibDirNames(extraDirLibName);
		appServer.setGlobalLibDirName(globalDirLibName);

		_assertAppServer(
			appServer, appServerDir, extraDirLibName, globalDirLibName,
			portalDirName, serverDetectorServerId);
	}

	private void _assertAppServer(
		AppServer appServer, File appServerDir, String extraDirLibName,
		String globalDirLibName, String portalDirName,
		String serverDetectorServerId) {

		Assert.assertEquals(appServerDir, appServer.getDir());

		File extraLibDir = new File(appServerDir, extraDirLibName);

		Assert.assertEquals(
			extraLibDir,
			appServer.getExtraLibDirs(
			).get(
				0
			));

		File globalLibDir = new File(appServerDir, globalDirLibName);

		Assert.assertEquals(globalLibDir, appServer.getGlobalLibDir());

		File portalDir = new File(appServerDir, portalDirName);

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

		Assert.assertEquals(
			serverDetectorServerId, appServer.getServerDetectorServerId());
	}

}