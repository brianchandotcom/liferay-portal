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

package com.liferay.portal.tools.bundle.support.ant;

import com.liferay.portal.tools.bundle.support.BundleSupportTest;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import org.apache.tools.ant.BuildFileRule;
import org.apache.tools.ant.Project;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author David Truong
 */
public class BundleSupportAntTaskTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BundleSupportTest.setUpClass();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		BundleSupportTest.tearDownClass();
	}

	@Before
	public void setUp() throws Exception {
		URL url = BundleSupportAntTaskTest.class.getResource(
			"dependencies/build.xml");

		File buildXmlFile = new File(url.toURI());

		Assert.assertTrue(buildXmlFile.isFile());

		buildFileRule.configureProject(buildXmlFile.getAbsolutePath());
	}

	@Test
	public void testCleanTask() throws Exception {
		File warFolder = temporaryFolder.newFolder("bundles", "osgi", "war");

		File testWar = new File(warFolder, "test.war");

		testWar.createNewFile();

		File jarFolder = temporaryFolder.newFolder(
			"bundles", "osgi", "modules");

		File testJar = new File(jarFolder, "test.jar");

		testJar.createNewFile();

		File liferayHomeDir = new File(temporaryFolder.getRoot(), "bundles");

		Project project = buildFileRule.getProject();

		project.setProperty("bundle.support.file.name", "test.war");
		project.setProperty(
			"bundle.support.liferay.home.dir",
			liferayHomeDir.getAbsolutePath());

		project.executeTarget("clean");

		Assert.assertFalse(testWar.exists());

		Assert.assertTrue(testJar.exists());
	}

	@Test
	public void testDeployTask() throws IOException {
		File liferayHomeDir = temporaryFolder.newFolder("bundles");

		File deployFile = temporaryFolder.newFile("test-1.0.0.jar");

		Project project = buildFileRule.getProject();

		project.setProperty(
			"bundle.support.file", deployFile.getAbsolutePath());
		project.setProperty(
			"bundle.support.liferay.home.dir",
			liferayHomeDir.getAbsolutePath());
		project.setProperty("bundle.support.output.file.name", "test.jar");

		project.executeTarget("deploy");

		File deployedJarFile = new File(
			liferayHomeDir, "osgi/modules/test.jar");

		Assert.assertTrue(deployedJarFile.exists());
	}

	@Test
	public void testDistBundleTask() throws IOException {
		File liferayHomeDir = temporaryFolder.newFolder("bundles");

		File warFolder = temporaryFolder.newFolder("bundles", "osgi", "war");

		File testWar = new File(warFolder, "test.war");

		testWar.createNewFile();

		File outputFile = new File(temporaryFolder.getRoot(), "out.zip");

		Assert.assertFalse(outputFile.exists());

		Project project = buildFileRule.getProject();

		project.setProperty("bundle.support.format", "zip");
		project.setProperty("bundle.support.include.folder", "true");
		project.setProperty(
			"bundle.support.liferay.home.dir",
			liferayHomeDir.getAbsolutePath());
		project.setProperty(
			"bundle.support.output.file", outputFile.getAbsolutePath());

		project.executeTarget("dist-bundle");

		Assert.assertTrue(outputFile.exists());
	}

	@Test
	public void testInitBundleTask() throws IOException {
		File liferayHomeDir = temporaryFolder.newFolder("bundles");

		Project project = buildFileRule.getProject();

		project.setProperty("bundle.support.configs.dir", null);
		project.setProperty("bundle.support.environment", "local");
		project.setProperty(
			"bundle.support.liferay.home.dir",
			liferayHomeDir.getAbsolutePath());
		project.setProperty("bundle.support.password", "test");
		project.setProperty("bundle.support.proxy.host", null);
		project.setProperty("bundle.support.proxy.password", null);
		project.setProperty("bundle.support.proxy.port", "0");
		project.setProperty("bundle.support.proxy.protocol", null);
		project.setProperty("bundle.support.proxy.username", null);
		project.setProperty("bundle.support.strip.components", "0");
		project.setProperty(
			"bundle.support.url", "http://localhost:8888/test.tar.gz");
		project.setProperty("bundle.support.username", "test");

		project.executeTarget("init-bundle");

		Assert.assertTrue(liferayHomeDir.listFiles().length > 0);

		File readmeFile = new File(liferayHomeDir, "README.markdown");

		Assert.assertTrue(readmeFile.exists());
	}

	@Rule
	public final BuildFileRule buildFileRule = new BuildFileRule();

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

}