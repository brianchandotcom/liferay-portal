/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.form.field;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.BaseProjectTemplatesTestCase;
import com.liferay.project.templates.extensions.util.ProjectTemplatesUtil;
import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.extensions.util.VersionUtil;
import com.liferay.project.templates.util.FileTestUtil;

import java.io.File;

import java.net.URI;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Lawrence Lee
 */
@RunWith(Parameterized.class)
public class ProjectTemplatesFormFieldWorkspaceTest
	implements BaseProjectTemplatesTestCase {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	@Parameterized.Parameters(name = "Testcase-{index}: testing {1} {0}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{"dxp", "7.2.10.7"}, {"portal", "7.3.7"}, {"portal", "7.4.3.56"}
			});
	}

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

	public ProjectTemplatesFormFieldWorkspaceTest(
		String liferayProduct, String liferayVersion) {

		_liferayProduct = liferayProduct;
		_liferayVersion = liferayVersion;
	}

	@Test
	public void testBuildTemplateFormFieldNpm() throws Exception {
		String name = "foobar";
		String template = "form-field";

		File gradleWorkspaceDir = buildWorkspace(
			temporaryFolder, "gradle", "gradleWS", _liferayVersion,
			mavenExecutor);

		String liferayWorkspaceProduct = getLiferayWorkspaceProduct(
			_liferayVersion);

		if (liferayWorkspaceProduct != null) {
			writeGradlePropertiesInWorkspace(
				gradleWorkspaceDir,
				"liferay.workspace.product=" + liferayWorkspaceProduct);
		}

		writeGradlePropertiesInWorkspace(
			gradleWorkspaceDir, "liferay.workspace.node.package.manager=npm");

		File gradleProjectDir = buildTemplateWithGradle(
			new File(gradleWorkspaceDir, "modules"), template, name,
			"--liferay-product", _liferayProduct, "--liferay-version",
			_liferayVersion);

		testContains(
			gradleProjectDir, "package.json",
			"--soyDeps \\\"./node_modules/clay-*/src/**/*.soy\\\"",
			"\\\"./node_modules" +
				"/com.liferay.dynamic.data.mapping.form.field.type/META-INF/");

		if (isBuildProjects()) {
			executeGradle(
				gradleWorkspaceDir, _gradleDistribution,
				":modules:" + name + GRADLE_TASK_PATH_BUILD);

			File gradleOutputDir = new File(gradleProjectDir, "build/libs");

			Path gradleOutputPath = FileTestUtil.getFile(
				gradleOutputDir.toPath(), OUTPUT_FILE_NAME_GLOB_REGEX, 1);

			Assert.assertNotNull(gradleOutputPath);

			Assert.assertTrue(Files.exists(gradleOutputPath));
		}
	}

	@Test
	public void testBuildTemplateFormFieldPortlet() throws Exception {
		String name = "foobar";
		String template = "form-field";

		File workspaceDir = buildWorkspace(
			temporaryFolder, "gradle", "gradleWS", _liferayVersion,
			mavenExecutor);

		String liferayWorkspaceProduct = getLiferayWorkspaceProduct(
			_liferayVersion);

		if (liferayWorkspaceProduct != null) {
			writeGradlePropertiesInWorkspace(
				workspaceDir,
				"liferay.workspace.product=" + liferayWorkspaceProduct);
		}

		File gradleProjectDir = buildTemplateWithGradle(
			new File(workspaceDir, "modules"), template, name,
			"--liferay-product", _liferayProduct, "--liferay-version",
			_liferayVersion);

		testContains(
			gradleProjectDir, "bnd.bnd", "Provide-Capability:", "soy;",
			"type:String=\"LiferayFormField\"");
		testContains(
			gradleProjectDir, "package.json", "\"@babel/cli\": \"^7.2.3\"",
			"\"@liferay/portal-" + _liferayVersion.substring(0, 3) +
				"\": \"*\"",
			"\"metal-tools-soy\": \"4.3.2\"");

		testContains(
			gradleProjectDir, "build.gradle",
			"jsCompile group: \"com.liferay\", name: " +
				"\"com.liferay.dynamic.data.mapping.form.field.type\"");

		if (VersionUtil.getMinorVersion(_liferayVersion) < 3) {
			testContains(
				gradleProjectDir, "build.gradle", DEPENDENCY_RELEASE_DXP_API);
		}
		else {
			testContains(
				gradleProjectDir, "build.gradle",
				DEPENDENCY_RELEASE_PORTAL_API);
		}

		testContains(
			gradleProjectDir,
			"src/main/java/foobar/form/field/FoobarDDMFormFieldType.java",
			"com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;",
			"org.osgi.service.component.annotations.Reference;",
			"ddm.form.field.type.description=foobar-description",
			"ddm.form.field.type.display.order:Integer=13",
			"ddm.form.field.type.group=customized",
			"public String getModuleName()",
			"public boolean isCustomDDMFormFieldType()",
			"private NPMResolver _npmResolver;");
		testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar.soy",
			"{template .content}", "ddm-field-foobar", "form-control foobar");
		testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar.es.js",
			"'dynamic-data-mapping-form-field-type/FieldBase/FieldBase.es';",
			"import './foobarRegister.soy.js';",
			"import {Config} from 'metal-state'",
			"import templates from './foobar.soy.js';", "* Foobar Component",
			"class Foobar extends Component", "Foobar.STATE",
			"Soy.register(Foobar, templates);");

		testNotContains(
			gradleProjectDir, "build.gradle", true, "^repositories \\{.*");
		testNotContains(gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		if (isBuildProjects()) {
			executeGradle(
				workspaceDir, _gradleDistribution,
				":modules:" + name + GRADLE_TASK_PATH_BUILD);

			File gradleOutputDir = new File(gradleProjectDir, "build/libs");

			Path gradleOutputPath = FileTestUtil.getFile(
				gradleOutputDir.toPath(), OUTPUT_FILE_NAME_GLOB_REGEX, 1);

			Assert.assertNotNull(gradleOutputPath);

			Assert.assertTrue(Files.exists(gradleOutputPath));

			String groupId = "com.test";

			File mavenWorkspaceDir = buildWorkspace(
				temporaryFolder, "maven", "mavenWS", _liferayVersion,
				mavenExecutor);

			List<String> completeArgs = new ArrayList<>();

			completeArgs.add("archetype:generate");
			completeArgs.add("--batch-mode");

			String archetypeArtifactId =
				"com.liferay.project.templates." + template.replace('-', '.');

			completeArgs.add("-DarchetypeArtifactId=" + archetypeArtifactId);

			String projectTemplateVersion =
				ProjectTemplatesUtil.getArchetypeVersion(archetypeArtifactId);

			Assert.assertTrue(
				"Unable to get project template version",
				Validator.isNotNull(projectTemplateVersion));

			completeArgs.add("-DarchetypeGroupId=com.liferay");
			completeArgs.add("-DarchetypeVersion=" + projectTemplateVersion);
			completeArgs.add("-DartifactId=" + name);
			completeArgs.add("-Dauthor=" + System.getProperty("user.name"));
			completeArgs.add("-DclassName=FooBar");
			completeArgs.add("-DgroupId=" + groupId);
			completeArgs.add("-DliferayProduct=" + _liferayProduct);
			completeArgs.add("-DliferayVersion=" + _liferayVersion);

			String mavenOutput = executeMaven(
				mavenWorkspaceDir, true, mavenExecutor,
				completeArgs.toArray(new String[0]));

			Assert.assertTrue(
				mavenOutput,
				mavenOutput.contains(
					"Form Field project in Maven is only supported in 7.0 " +
						"and 7.1"));
		}
	}

	@Test
	public void testBuildTemplateFormFieldYarn() throws Exception {
		String name = "foobar";
		String template = "form-field";

		File gradleWorkspaceDir = buildWorkspace(
			temporaryFolder, "gradle", "gradleWS", _liferayVersion,
			mavenExecutor);

		String liferayWorkspaceProduct = getLiferayWorkspaceProduct(
			_liferayVersion);

		if (liferayWorkspaceProduct != null) {
			writeGradlePropertiesInWorkspace(
				gradleWorkspaceDir,
				"liferay.workspace.product=" + liferayWorkspaceProduct);
		}

		File gradleProjectDir = buildTemplateWithGradle(
			new File(gradleWorkspaceDir, "modules"), template, name,
			"--liferay-product", _liferayProduct, "--liferay-version",
			_liferayVersion);

		testContains(
			gradleProjectDir, "package.json",
			"--soyDeps \\\"../../node_modules/clay-*/src/**/*.soy\\\"",
			"\\\"../../node_modules" +
				"/com.liferay.dynamic.data.mapping.form.field.type/META-INF/");

		if (isBuildProjects()) {
			executeGradle(
				gradleWorkspaceDir, _gradleDistribution,
				":modules:" + name + GRADLE_TASK_PATH_BUILD);

			File gradleOutputDir = new File(gradleProjectDir, "build/libs");

			Path gradleOutputPath = FileTestUtil.getFile(
				gradleOutputDir.toPath(), OUTPUT_FILE_NAME_GLOB_REGEX, 1);

			Assert.assertNotNull(gradleOutputPath);
			Assert.assertTrue(Files.exists(gradleOutputPath));
		}
	}

	@Test
	public void testBuildTemplateFormFieldYarnCustomModules() throws Exception {
		String name = "foobar";
		String template = "form-field";

		File gradleWorkspaceDir = buildWorkspace(
			temporaryFolder, "gradle", "gradleWS", _liferayVersion,
			mavenExecutor);

		String liferayWorkspaceProduct = getLiferayWorkspaceProduct(
			_liferayVersion);

		if (liferayWorkspaceProduct != null) {
			writeGradlePropertiesInWorkspace(
				gradleWorkspaceDir,
				"liferay.workspace.product=" + liferayWorkspaceProduct);
		}

		File gradleProjectDir = buildTemplateWithGradle(
			new File(gradleWorkspaceDir, "modules/test"), template, name,
			"--liferay-product", _liferayProduct, "--liferay-version",
			_liferayVersion);

		testContains(
			gradleProjectDir, "package.json",
			"--soyDeps \\\"../../../node_modules/clay-*/src/**/*.soy\\\"",
			"\\\"../../../node_modules" +
				"/com.liferay.dynamic.data.mapping.form.field.type/META-INF/");

		if (isBuildProjects()) {
			executeGradle(
				gradleWorkspaceDir, _gradleDistribution,
				":modules:test:" + name + GRADLE_TASK_PATH_BUILD);

			File gradleOutputDir = new File(gradleProjectDir, "build/libs");

			Path gradleOutputPath = FileTestUtil.getFile(
				gradleOutputDir.toPath(), OUTPUT_FILE_NAME_GLOB_REGEX, 1);

			Assert.assertNotNull(gradleOutputPath);
			Assert.assertTrue(Files.exists(gradleOutputPath));
		}
	}

	@Test
	public void testBuildTemplateFormFieldYarnUnsetModulesDir()
		throws Exception {

		String name = "foobar";
		String template = "form-field";

		File gradleWorkspaceDir = buildWorkspace(
			temporaryFolder, "gradle", "gradleWS", _liferayVersion,
			mavenExecutor);

		String liferayWorkspaceProduct = getLiferayWorkspaceProduct(
			_liferayVersion);

		if (liferayWorkspaceProduct != null) {
			writeGradlePropertiesInWorkspace(
				gradleWorkspaceDir,
				"liferay.workspace.product=" + liferayWorkspaceProduct);
		}

		File gradleProjectDir = buildTemplateWithGradle(
			gradleWorkspaceDir, template, name, "--liferay-version",
			_liferayVersion, "--liferay-product", _liferayProduct);

		removeGradlePropertiesInWorkspace(
			gradleWorkspaceDir, "liferay.workspace.modules.dir=modules");

		testContains(
			gradleProjectDir, "package.json",
			"--soyDeps \\\"../node_modules/clay-*/src/**/*.soy\\\"",
			"\\\"../node_modules" +
				"/com.liferay.dynamic.data.mapping.form.field.type/META-INF/");

		if (isBuildProjects()) {
			executeGradle(
				gradleWorkspaceDir, _gradleDistribution,
				":" + name + GRADLE_TASK_PATH_BUILD);

			File gradleOutputDir = new File(gradleProjectDir, "build/libs");

			Path gradleOutputPath = FileTestUtil.getFile(
				gradleOutputDir.toPath(), OUTPUT_FILE_NAME_GLOB_REGEX, 1);

			Assert.assertNotNull(gradleOutputPath);
			Assert.assertTrue(Files.exists(gradleOutputPath));
		}
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static URI _gradleDistribution;

	private final String _liferayProduct;
	private final String _liferayVersion;

}