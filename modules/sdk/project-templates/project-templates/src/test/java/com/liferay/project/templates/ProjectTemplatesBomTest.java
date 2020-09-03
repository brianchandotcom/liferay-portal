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

		Version v = Version.parseVersion(_BOM_VERSION.replaceAll("-", "."));

		String liferayVersion = v.getMajor() + "." + v.getMinor();

		File workspaceDir = buildWorkspace(temporaryFolder, liferayVersion);

		writeGradlePropertiesInWorkspace(
			workspaceDir,
			"liferay.workspace.target.platform.version=" + _BOM_VERSION);

		File modulesDir = new File(workspaceDir, "modules");

		String template = "api";

		File apiProjectDir = buildTemplateWithGradle(
			modulesDir, template, template + "test");

		testOutput(apiProjectDir, template, workspaceDir);

		template = "control-menu-entry";

		File controlMenuEntryProjectDir = buildTemplateWithGradle(
			modulesDir, template, template + "test");

		testOutput(controlMenuEntryProjectDir, template, workspaceDir);

		template = "mvc-portlet";

		File mvcPortletProjectDir = buildTemplateWithGradle(
			modulesDir, template, template + "test");

		testOutput(mvcPortletProjectDir, template, workspaceDir);

		template = "npm-react-portlet";

		File npmReactPortletProjectDir = buildTemplateWithGradle(
			modulesDir, template, template + "test");

		testOutput(npmReactPortletProjectDir, template, workspaceDir);

		template = "panel-app";

		File panelAppProjectDir = buildTemplateWithGradle(
			modulesDir, template, template + "test");

		testOutput(panelAppProjectDir, template, workspaceDir);

		template = "portlet-configuration-icon";

		File portletConfigurationIconProjectDir = buildTemplateWithGradle(
			modulesDir, template, template + "test");

		testOutput(portletConfigurationIconProjectDir, template, workspaceDir);

		template = "portlet-provider";

		File portletProviderProjectDir = buildTemplateWithGradle(
			modulesDir, template, template + "test");

		testOutput(portletProviderProjectDir, template, workspaceDir);

		template = "portlet-toolbar-contributor";

		File portletToolbarContributorProjectDir = buildTemplateWithGradle(
			modulesDir, template, template + "test");

		testOutput(portletToolbarContributorProjectDir, template, workspaceDir);

		template = "service-builder";

		File serviceBuilderProjectDir = buildTemplateWithGradle(
			modulesDir, template, template + "test");

		String apiProjectName = template + "test-api";

		String serviceProjectName = template + "test-service";

		executeGradle(
			workspaceDir, _gradleDistribution,
			":modules:service-buildertest:" + serviceProjectName +
				GRADLE_TASK_PATH_BUILD_SERVICE);

		testOutput(serviceBuilderProjectDir, apiProjectName, workspaceDir);
		testOutput(serviceBuilderProjectDir, serviceProjectName, workspaceDir);

		template = "service-wrapper";

		File serviceWrapperProjectDir = buildTemplateWithGradle(
			modulesDir, template, template + "test", "--service",
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper");

		testOutput(serviceWrapperProjectDir, template, workspaceDir);

		template = "simulation-panel-entry";

		File simulationPanelEntryProjectDir = buildTemplateWithGradle(
			modulesDir, template, template + "test");

		testOutput(simulationPanelEntryProjectDir, template, workspaceDir);

		template = "template-context-contributor";

		File templateContextContributorProjectDir = buildTemplateWithGradle(
			modulesDir, template, template + "test");

		testOutput(
			templateContextContributorProjectDir, template, workspaceDir);

		template = "war-hook";

		File warHookProjectDir = buildTemplateWithGradle(
			modulesDir, template, template + "test");

		testOutput(warHookProjectDir, template, workspaceDir);

		template = "war-mvc-portlet";

		File warMvcPortletProjectDir = buildTemplateWithGradle(
			modulesDir, template, template + "test");

		testOutput(warMvcPortletProjectDir, template, workspaceDir);
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

	private static final String _BOM_VERSION = System.getProperty(
		"project.templates.bom.version");

	private static URI _gradleDistribution;

}