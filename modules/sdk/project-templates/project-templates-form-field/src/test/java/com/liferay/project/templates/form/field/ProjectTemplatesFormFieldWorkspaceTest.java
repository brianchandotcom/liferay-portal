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

package com.liferay.project.templates.form.field;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.BaseProjectTemplatesTestCase;
import com.liferay.project.templates.extensions.util.ProjectTemplatesUtil;
import com.liferay.project.templates.extensions.util.Validator;
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

	@Parameterized.Parameters(name = "Testcase-{index}: testing {0}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {{"7.2.1"}, {"7.3.6"}, {"7.4.0"}});
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

	public ProjectTemplatesFormFieldWorkspaceTest(String liferayVersion) {
		_liferayVersion = liferayVersion;
	}

	@Test
	public void testBuildTemplateFormFieldPortlet() throws Exception {
		String name = "foobar";
		String template = "form-field";

		File workspaceDir = buildWorkspace(
			temporaryFolder, "gradle", "gradleWS", _liferayVersion,
			mavenExecutor);

		File gradleProjectDir = buildTemplateWithGradle(
			new File(workspaceDir, "modules"), template, name,
			"--liferay-version", _liferayVersion);

		testContains(
			gradleProjectDir, "bnd.bnd", "Provide-Capability:", "soy;",
			"type:String=\"LiferayFormField\"");
		testContains(
			gradleProjectDir, "build.gradle",
			"compileOnly group: \"com.liferay\", name: " +
				"\"com.liferay.dynamic.data.mapping.api\"",
			"compileOnly group: \"com.liferay\", name: " +
				"\"com.liferay.frontend.js.loader.modules.extender.api\"",
			"jsCompile group: \"com.liferay\", name: " +
				"\"com.liferay.dynamic.data.mapping.form.field.type\"",
			DEPENDENCY_PORTAL_KERNEL);
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

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static URI _gradleDistribution;

	private final String _liferayVersion;

}