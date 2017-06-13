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
	public void testDistBundleTar() throws Exception {
	}

	@Ignore
	@Override
	public void testDistBundleZip() throws Exception {
	}

	@Override
	protected void clean(String fileName, File liferayHomeDir)
		throws Exception {

		_execute(
			"clean", null, null, null, null, null, fileName, liferayHomeDir,
			null, null, 0, null, null, null, null);
	}

	@Override
	protected void createToken(
			String emailAddress, String password, File tokenFile, URL tokenUrl)
		throws Exception {

		_execute(
			"create-token", null, null, null, emailAddress, null, null, null,
			null, password, 0, tokenFile, tokenUrl, null, null);
	}

	@Override
	protected void deploy(File file, File liferayHomeDir, String outputFileName)
		throws Exception {

		_execute(
			"deploy", null, null, file, null, null, null, liferayHomeDir,
			outputFileName, null, 0, null, null, null, null);
	}

	@Override
	protected void initBundle(
			File cacheDir, File configsDir, String environment,
			File liferayHomeDir, String password, int stripComponents, URL url,
			String userName)
		throws Exception {

		_execute(
			"init", cacheDir, configsDir, null, null, environment, null,
			liferayHomeDir, null, password, stripComponents, null, null, url,
			userName);
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
			File liferayHomeDir, String outputFileName, String password,
			int stripComponents, File tokenFile, URL tokenUrl, URL url,
			String userName)
		throws Exception {

		File projectDir = temporaryFolder.newFolder("maven");

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
			pomXml, "[$BUNDLE_SUPPORT_LIFERAY_HOME$]", liferayHomeDir);
		pomXml = _replace(
			pomXml, "[$BUNDLE_SUPPORT_OUTPUT_FILE_NAME$]", outputFileName);
		pomXml = _replace(pomXml, "[$BUNDLE_SUPPORT_PASSWORD$]", password);
		pomXml = _replace(
			pomXml, "[$BUNDLE_SUPPORT_STRIP_COMPONENTS$]", stripComponents);
		pomXml = _replace(pomXml, "[$BUNDLE_SUPPORT_TOKEN_FILE$]", tokenFile);
		pomXml = _replace(pomXml, "[$BUNDLE_SUPPORT_TOKEN_URL$]", tokenUrl);
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