/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates;

import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.extensions.util.VersionUtil;
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

		File workspaceDir = buildWorkspace(temporaryFolder, _BOM_VERSION);

		writeGradlePropertiesInWorkspace(
			workspaceDir,
			"liferay.workspace.target.platform.version=" + _BOM_VERSION);

		String liferayProduct = "portal";

		if (VersionUtil.getMicroVersion(_BOM_VERSION) >= 10) {
			liferayProduct = "dxp";
		}

		File modulesDir = new File(workspaceDir, "modules");

		_buildTemplateTestOutput(
			modulesDir, "api", workspaceDir, liferayProduct);

		_buildTemplateTestOutput(
			modulesDir, "control-menu-entry", workspaceDir, liferayProduct);

		_buildTemplateTestOutput(
			modulesDir, "mvc-portlet", workspaceDir, liferayProduct);

		_buildTemplateTestOutput(
			modulesDir, "npm-react-portlet", workspaceDir, liferayProduct);

		_buildTemplateTestOutput(
			modulesDir, "panel-app", workspaceDir, liferayProduct);

		_buildTemplateTestOutput(
			modulesDir, "portlet-configuration-icon", workspaceDir,
			liferayProduct);

		_buildTemplateTestOutput(
			modulesDir, "portlet-provider", workspaceDir, liferayProduct);

		_buildTemplateTestOutput(
			modulesDir, "portlet-toolbar-contributor", workspaceDir,
			liferayProduct);

		String template = "service-builder";

		File serviceBuilderProjectDir = buildTemplateWithGradle(
			modulesDir, template, template + "test", "--liferay-product",
			liferayProduct, "--liferay-version", _BOM_VERSION);

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
			modulesDir, template, template + "test", "--liferay-product",
			liferayProduct, "--liferay-version", _BOM_VERSION, "--service",
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper");

		testOutput(serviceWrapperProjectDir, template, workspaceDir);

		_buildTemplateTestOutput(
			modulesDir, "simulation-panel-entry", workspaceDir, liferayProduct);

		_buildTemplateTestOutput(
			modulesDir, "template-context-contributor", workspaceDir,
			liferayProduct);

		_buildTemplateTestOutput(
			modulesDir, "war-hook", workspaceDir, liferayProduct);

		_buildTemplateTestOutput(
			modulesDir, "war-mvc-portlet", workspaceDir, liferayProduct);
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
			File modulesDir, String template, File workspaceDir,
			String liferayProduct)
		throws Exception {

		File projectDir = buildTemplateWithGradle(
			modulesDir, template, template + "test", "--liferay-product",
			liferayProduct, "--liferay-version", _BOM_VERSION);

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