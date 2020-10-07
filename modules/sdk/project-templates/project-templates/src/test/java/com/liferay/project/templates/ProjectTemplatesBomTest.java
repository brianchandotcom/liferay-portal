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

package com.liferay.project.templates;

import aQute.bnd.version.Version;

import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.util.FileTestUtil;

import java.io.File;
import java.io.IOException;

import java.net.URI;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Properties;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Lawrence Lee
 */
public class ProjectTemplatesBomTest implements BaseProjectTemplatesTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		String gradleDistribution = System.getProperty("gradle.distribution");

		if (Validator.isNull(gradleDistribution)) {
			Properties properties = FileTestUtil.readProperties(
				"gradle-wrapper/gradle/wrapper/gradle-wrapper.properties");

			gradleDistribution = properties.getProperty("distributionUrl");
		}

		Assert.assertTrue(gradleDistribution.contains(GRADLE_WRAPPER_VERSION));

		_gradleDistribution = URI.create(gradleDistribution);
	}

	@Test
	public void testBomVersion() throws Exception {
		Assume.assumeTrue(_isBomTest());

		String normalizedBomVersion = _BOM_VERSION.replaceAll("-", ".");

		IntStream intStream = normalizedBomVersion.chars();

		long componentCount = intStream.filter(
			components -> components == '.'
		).count();

		if (componentCount > 3) {
			normalizedBomVersion = normalizedBomVersion.substring(
				0, normalizedBomVersion.lastIndexOf("."));
		}

		Version version = Version.parseVersion(normalizedBomVersion);

		File workspaceDir = buildWorkspace(
			temporaryFolder, version.getMajor() + "." + version.getMinor());

		writeGradlePropertiesInWorkspace(
			workspaceDir,
			"liferay.workspace.target.platform.version=" + _BOM_VERSION);

		String product = "portal";

		if (version.getMicro() >= 10) {
			product = "dxp";
		}

		File modulesDir = new File(workspaceDir, "modules");

		_buildTemplateTestOutput(modulesDir, "api", workspaceDir, product);

		_buildTemplateTestOutput(
			modulesDir, "control-menu-entry", workspaceDir, product);

		_buildTemplateTestOutput(
			modulesDir, "mvc-portlet", workspaceDir, product);

		_buildTemplateTestOutput(
			modulesDir, "npm-react-portlet", workspaceDir, product);

		_buildTemplateTestOutput(
			modulesDir, "panel-app", workspaceDir, product);

		_buildTemplateTestOutput(
			modulesDir, "portlet-configuration-icon", workspaceDir, product);

		_buildTemplateTestOutput(
			modulesDir, "portlet-provider", workspaceDir, product);

		_buildTemplateTestOutput(
			modulesDir, "portlet-toolbar-contributor", workspaceDir, product);

		String template = "service-builder";

		File serviceBuilderProjectDir = buildTemplateWithGradle(
			modulesDir, template, template + "test", "--product", product,
			"--liferay-version", _BOM_VERSION);

		String serviceProjectName = template + "test-service";

		executeGradle(
			workspaceDir, _gradleDistribution,
			":modules:service-buildertest:" + serviceProjectName +
				GRADLE_TASK_PATH_BUILD_SERVICE);

		testOutput(
			serviceBuilderProjectDir, template + "test-api", workspaceDir);
		testOutput(serviceBuilderProjectDir, serviceProjectName, workspaceDir);

		template = "service-wrapper";

		File serviceWrapperProjectDir = buildTemplateWithGradle(
			modulesDir, template, template + "test", "--service",
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper",
			"--product", product, "--liferay-version", _BOM_VERSION);

		testOutput(serviceWrapperProjectDir, template, workspaceDir);

		_buildTemplateTestOutput(
			modulesDir, "simulation-panel-entry", workspaceDir, product);

		_buildTemplateTestOutput(
			modulesDir, "template-context-contributor", workspaceDir, product);

		_buildTemplateTestOutput(modulesDir, "war-hook", workspaceDir, product);

		_buildTemplateTestOutput(
			modulesDir, "war-mvc-portlet", workspaceDir, product);
	}

	public void testOutput(
			File projectDir, String projectName, File workspaceDir)
		throws IOException {

		File projectOutputDir;

		if (projectName.contains("service-builder")) {
			executeGradle(
				workspaceDir, _gradleDistribution,
				":modules:service-buildertest:" + projectName +
					GRADLE_TASK_PATH_BUILD);

			projectOutputDir = new File(
				projectDir, projectName + "/build/libs");
		}
		else {
			executeGradle(
				workspaceDir, _gradleDistribution,
				":modules:" + projectName + "test" + GRADLE_TASK_PATH_BUILD);

			projectOutputDir = new File(projectDir, "build/libs");
		}

		Path projectOutputPath = FileTestUtil.getFile(
			projectOutputDir.toPath(), OUTPUT_FILE_NAME_GLOB_REGEX, 1);

		Assert.assertNotNull(projectOutputPath);

		Assert.assertTrue(Files.exists(projectOutputPath));
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private void _buildTemplateTestOutput(
			File modulesDir, String template, File workspaceDir, String product)
		throws Exception {

		File projectDir = buildTemplateWithGradle(
			modulesDir, template, template + "test", "--product", product,
			"--liferay-version", _BOM_VERSION);

		testOutput(projectDir, template, workspaceDir);

		if (!template.contains("war")) {
			_resolveProject(template, workspaceDir);
		}
	}

	private boolean _isBomTest() {
		if (Validator.isNotNull(_BOM_VERSION)) {
			return true;
		}

		return false;
	}

	private void _resolveProject(String projectName, File workspaceDir)
		throws Exception {

		executeGradle(
			workspaceDir, _gradleDistribution,
			":modules:" + projectName + "test" + _GRADLE_TASK_PATH_RESOLVE);
	}

	private static final String _BOM_VERSION = System.getProperty(
		"project.templates.bom.version");

	private static final String _GRADLE_TASK_PATH_RESOLVE = ":resolve";

	private static URI _gradleDistribution;

}