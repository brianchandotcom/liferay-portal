/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.bundle.support.ant;

import com.liferay.portal.tools.bundle.support.commands.BundleSupportCommandsTest;

import java.io.File;

import java.net.URL;

import org.apache.tools.ant.BuildFileRule;
import org.apache.tools.ant.Project;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;

/**
 * @author David Truong
 */
public class BundleSupportAntTest extends BundleSupportCommandsTest {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		URL url = BundleSupportAntTest.class.getResource(
			"dependencies/build.xml");

		File buildXmlFile = new File(url.toURI());

		Assert.assertTrue(buildXmlFile.isFile());

		buildFileRule.configureProject(buildXmlFile.getAbsolutePath());
	}

	@Rule
	public final BuildFileRule buildFileRule = new BuildFileRule();

	@Override
	protected void clean(String fileName, File liferayHomeDir)
		throws Exception {

		Project project = buildFileRule.getProject();

		project.setProperty("bundle.support.file.name", fileName);
		project.setProperty(
			"bundle.support.liferay.home.dir",
			_getAbsolutePath(liferayHomeDir));

		project.executeTarget("clean");
	}

	@Override
	protected void createToken(
			String emailAddress, boolean force, String password,
			File passwordFile, File tokenFile, URL tokenURL)
		throws Exception {

		Project project = buildFileRule.getProject();

		project.setProperty("bundle.support.email.address", emailAddress);
		project.setProperty("bundle.support.force", String.valueOf(force));
		project.setProperty("bundle.support.password", password);
		project.setProperty(
			"bundle.support.password.file", _getAbsolutePath(passwordFile));
		project.setProperty(
			"bundle.support.token.file", _getAbsolutePath(tokenFile));
		project.setProperty("bundle.support.token.url", tokenURL.toString());

		project.executeTarget("create-token");
	}

	@Override
	protected void deploy(File file, File liferayHomeDir, String outputFileName)
		throws Exception {

		Project project = buildFileRule.getProject();

		project.setProperty("bundle.support.file", _getAbsolutePath(file));
		project.setProperty(
			"bundle.support.liferay.home.dir",
			_getAbsolutePath(liferayHomeDir));
		project.setProperty("bundle.support.output.file.name", outputFileName);

		project.executeTarget("deploy");
	}

	@Override
	protected void distBundle(
			String format, File liferayHomeDir, File outputFile)
		throws Exception {

		Project project = buildFileRule.getProject();

		project.setProperty("bundle.support.format", format);
		project.setProperty("bundle.support.include.folder", "true");
		project.setProperty(
			"bundle.support.liferay.home.dir",
			_getAbsolutePath(liferayHomeDir));
		project.setProperty(
			"bundle.support.output.file", _getAbsolutePath(outputFile));

		project.executeTarget("dist-bundle");
	}

	@Override
	protected void initBundle(
			File cacheDir, File configsDir, String environment,
			File liferayHomeDir, String password, int stripComponents, URL url,
			String userName)
		throws Exception {

		Project project = buildFileRule.getProject();

		project.setProperty(
			"bundle.support.cache.dir", _getAbsolutePath(cacheDir));
		project.setProperty(
			"bundle.support.configs.dir", _getAbsolutePath(configsDir));
		project.setProperty("bundle.support.environment", environment);
		project.setProperty(
			"bundle.support.liferay.home.dir",
			_getAbsolutePath(liferayHomeDir));
		project.setProperty("bundle.support.password", password);
		project.setProperty(
			"bundle.support.strip.components", String.valueOf(stripComponents));
		project.setProperty("bundle.support.url", url.toString());
		project.setProperty("bundle.support.username", userName);

		project.executeTarget("init-bundle");
	}

	private String _getAbsolutePath(File file) {
		if (file == null) {
			return null;
		}

		return file.getAbsolutePath();
	}

}