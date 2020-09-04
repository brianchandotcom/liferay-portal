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

		Version version = Version.parseVersion(
			_BOM_VERSION.replaceAll("-", "."));

		File workspaceDir = buildWorkspace(
			temporaryFolder, version.getMajor() + "." + version.getMinor());

		writeGradlePropertiesInWorkspace(
			workspaceDir,
			"liferay.workspace.target.platform.version=" + _BOM_VERSION);

		File modulesDir = new File(workspaceDir, "modules");

		_buildTemplateTestOutput(modulesDir, "api", workspaceDir);

		_buildTemplateTestOutput(
			modulesDir, "control-menu-entry", workspaceDir);

		_buildTemplateTestOutput(modulesDir, "mvc-portlet", workspaceDir);

		_buildTemplateTestOutput(modulesDir, "npm-react-portlet", workspaceDir);

		_buildTemplateTestOutput(modulesDir, "panel-app", workspaceDir);

		_buildTemplateTestOutput(
			modulesDir, "portlet-configuration-icon", workspaceDir);

		_buildTemplateTestOutput(modulesDir, "portlet-provider", workspaceDir);

		_buildTemplateTestOutput(
			modulesDir, "portlet-toolbar-contributor", workspaceDir);

		String template = "service-builder";

		File serviceBuilderProjectDir = buildTemplateWithGradle(
			modulesDir, template, template + "test");

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
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper");

		testOutput(serviceWrapperProjectDir, template, workspaceDir);

		_buildTemplateTestOutput(
			modulesDir, "simulation-panel-entry", workspaceDir);

		_buildTemplateTestOutput(
			modulesDir, "template-context-contributor", workspaceDir);

		_buildTemplateTestOutput(modulesDir, "war-hook", workspaceDir);

		_buildTemplateTestOutput(modulesDir, "war-mvc-portlet", workspaceDir);
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

	private static boolean _isBomTest() {
		if (Validator.isNotNull(_BOM_VERSION)) {
			return true;
		}

		return false;
	}

	private void _buildTemplateTestOutput(
			File modulesDir, String template, File workspaceDir)
		throws Exception {

		File apiProjectDir = buildTemplateWithGradle(
			modulesDir, template, template + "test");

		testOutput(apiProjectDir, template, workspaceDir);
	}

	private static final String _BOM_VERSION = System.getProperty(
		"project.templates.bom.version");

	private static URI _gradleDistribution;

}