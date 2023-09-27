/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.bundle.support.maven;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.portal.tools.bundle.support.commands.BundleSupportCommandsTest;
import com.liferay.portal.tools.bundle.support.util.FileTestUtil;

import java.io.File;

import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class BundleSupportMavenTest extends BundleSupportCommandsTest {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	@BeforeClass
	public static void setUpClass() throws Exception {
		BundleSupportCommandsTest.setUpClass();

		_pomXml = FileTestUtil.read(
			BundleSupportMavenTest.class, "dependencies/pom_xml.tmpl");
	}

	@Ignore
	@Override
	@Test
	public void testDistBundleTar() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testDistBundleZip() throws Exception {
	}

	@Override
	protected void clean(String fileName, File liferayHomeDir)
		throws Exception {

		_execute(
			"clean", null, null, null, null, null, fileName, false,
			liferayHomeDir, null, null, null, 0, null, null, null, null);
	}

	@Override
	protected void createToken(
			String emailAddress, boolean force, String password,
			File passwordFile, File tokenFile, URL tokenURL)
		throws Exception {

		_execute(
			"create-token", null, null, null, emailAddress, null, null, force,
			null, null, password, passwordFile, 0, tokenFile, tokenURL, null,
			null);
	}

	@Override
	protected void deploy(File file, File liferayHomeDir, String outputFileName)
		throws Exception {

		_execute(
			"deploy", null, null, file, null, null, null, false, liferayHomeDir,
			outputFileName, null, null, 0, null, null, null, null);
	}

	@Override
	protected void initBundle(
			File cacheDir, File configsDir, String environment,
			File liferayHomeDir, String password, int stripComponents, URL url,
			String userName)
		throws Exception {

		_execute(
			"init", cacheDir, configsDir, null, null, environment, null, false,
			liferayHomeDir, null, password, null, stripComponents, null, null,
			url, userName);
	}

	private static String _replace(String s, String key, File file) {
		String value = null;

		if (file != null) {
			value = file.getAbsolutePath();
		}

		return _replace(s, key, value);
	}

	private static String _replace(String s, String key, Object value) {
		if (value == null) {
			value = "";
		}

		return s.replace(key, value.toString());
	}

	private void _execute(
			String goalName, File cacheDir, File configsDir, File deployFile,
			String emailAddress, String environment, String fileName,
			boolean force, File liferayHomeDir, String outputFileName,
			String password, File passwordFile, int stripComponents,
			File tokenFile, URL tokenURL, URL url, String userName)
		throws Exception {

		File projectDir = new File(temporaryFolder.getRoot(), "maven");

		if (projectDir.exists()) {
			projectDir.delete();
		}

		projectDir.mkdir();

		String configs = FileTestUtil.relativize(configsDir, projectDir);

		String pomXml = _pomXml;

		pomXml = _replace(
			pomXml, "[$BUNDLE_SUPPORT_VERSION$]", _BUNDLE_SUPPORT_VERSION);

		pomXml = _replace(pomXml, "[$BUNDLE_SUPPORT_CACHE_DIR$]", cacheDir);
		pomXml = _replace(pomXml, "[$BUNDLE_SUPPORT_CONFIGS$]", configs);
		pomXml = _replace(pomXml, "[$BUNDLE_SUPPORT_DEPLOY_FILE$]", deployFile);
		pomXml = _replace(
			pomXml, "[$BUNDLE_SUPPORT_EMAIL_ADDRESS$]", emailAddress);
		pomXml = _replace(
			pomXml, "[$BUNDLE_SUPPORT_ENVIRONMENT$]", environment);
		pomXml = _replace(pomXml, "[$BUNDLE_SUPPORT_FILE_NAME$]", fileName);
		pomXml = _replace(
			pomXml, "[$BUNDLE_SUPPORT_FORCE$]", String.valueOf(force));
		pomXml = _replace(
			pomXml, "[$BUNDLE_SUPPORT_LIFERAY_HOME$]", liferayHomeDir);
		pomXml = _replace(
			pomXml, "[$BUNDLE_SUPPORT_OUTPUT_FILE_NAME$]", outputFileName);
		pomXml = _replace(pomXml, "[$BUNDLE_SUPPORT_PASSWORD$]", password);
		pomXml = _replace(
			pomXml, "[$BUNDLE_SUPPORT_PASSWORD_FILE$]", passwordFile);
		pomXml = _replace(
			pomXml, "[$BUNDLE_SUPPORT_STRIP_COMPONENTS$]", stripComponents);
		pomXml = _replace(pomXml, "[$BUNDLE_SUPPORT_TOKEN_FILE$]", tokenFile);
		pomXml = _replace(pomXml, "[$BUNDLE_SUPPORT_TOKEN_URL$]", tokenURL);
		pomXml = _replace(pomXml, "[$BUNDLE_SUPPORT_URL$]", url);
		pomXml = _replace(pomXml, "[$BUNDLE_SUPPORT_USER_NAME$]", userName);

		File pomXmlFile = new File(projectDir, "pom.xml");

		Files.write(
			pomXmlFile.toPath(), pomXml.getBytes(StandardCharsets.UTF_8));

		MavenExecutor.Result result = mavenExecutor.execute(
			projectDir, "bundle-support:" + goalName);

		Assert.assertEquals(result.output, 0, result.exitCode);
	}

	private static final String _BUNDLE_SUPPORT_VERSION = System.getProperty(
		"bundle.support.version");

	private static String _pomXml;

}