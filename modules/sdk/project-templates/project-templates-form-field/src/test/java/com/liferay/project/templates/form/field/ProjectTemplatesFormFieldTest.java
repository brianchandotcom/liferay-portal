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
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Gabriel Ibson
 */
public class ProjectTemplatesFormFieldTest
	implements BaseProjectTemplatesTestCase {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

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
	public void testBuildTemplateFormField70() throws Exception {
		String liferayVersion = "7.0.6";
		String name = "foobar";
		String template = "form-field";

		File gradleWorkspaceDir = buildWorkspace(
			temporaryFolder, liferayVersion);

		writeGradlePropertiesInWorkspace(
			gradleWorkspaceDir, "liferay.workspace.product=portal-7.0-ga7");

		File gradleProjectDir = buildTemplateWithGradle(
			new File(gradleWorkspaceDir, "modules"), template, name,
			"--liferay-version", liferayVersion);

		testContains(
			gradleProjectDir, "bnd.bnd", "Bundle-Name: " + name,
			"Web-ContextPath: /dynamic-data-foobar-form-field");
		testContains(
			gradleProjectDir, "build.gradle", DEPENDENCY_PORTAL_KERNEL);
		testContains(
			gradleProjectDir,
			"src/main/java/foobar/form/field/FoobarDDMFormFieldRenderer.java",
			"property = \"ddm.form.field.type.name=foobar\"",
			"public class FoobarDDMFormFieldRenderer extends " +
				"BaseDDMFormFieldRenderer {",
			"ddm.Foobar", "/META-INF/resources/foobar.soy");
		testContains(
			gradleProjectDir,
			"src/main/java/foobar/form/field/FoobarDDMFormFieldType.java",
			"ddm.form.field.type.js.class.name=Liferay.DDM.Field.Foobar",
			"ddm.form.field.type.js.module=foobar-form-field",
			"ddm.form.field.type.label=foobar-label",
			"ddm.form.field.type.name=foobar",
			"public class FoobarDDMFormFieldType extends BaseDDMFormFieldType",
			"return \"foobar\";");
		testContains(
			gradleProjectDir, "src/main/resources/META-INF/resources/config.js",
			"foobar-group", "'foobar-form-field': {",
			"path: 'foobar_field.js',", "'foobar-form-field-template': {");
		testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar.soy",
			"{namespace ddm}", "{template .Foobar autoescape",
			"<div class=\"form-group foobar-form-field\"");
		testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar_field.js",
			"'foobar-form-field',", "var FoobarField",
			"value: 'foobar-form-field'", "NAME: 'foobar-form-field'",
			"Liferay.namespace('DDM.Field').Foobar = FoobarField;");

		File mavenWorkspaceDir = buildWorkspace(
			temporaryFolder, "maven", "mavenWS", liferayVersion, mavenExecutor);

		File mavenModulesDir = new File(mavenWorkspaceDir, "modules");

		File mavenProjectDir = buildTemplateWithMaven(
			mavenModulesDir, mavenModulesDir, template, name, "com.test",
			mavenExecutor, "-DclassName=Foobar", "-Dpackage=foobar",
			"-DliferayVersion=" + liferayVersion);

		if (isBuildProjects()) {
			File gradleOutputDir = new File(gradleProjectDir, "build/libs");
			File mavenOutputDir = new File(mavenProjectDir, "target");

			buildProjects(
				_gradleDistribution, mavenExecutor, gradleWorkspaceDir,
				mavenProjectDir, gradleOutputDir, mavenOutputDir,
				":modules:" + name + GRADLE_TASK_PATH_BUILD);
		}
	}

	@Test
	public void testBuildTemplateFormField71() throws Exception {
		String liferayVersion = "7.1.3";
		String name = "foobar";
		String template = "form-field";

		File gradleWorkspaceDir = buildWorkspace(
			temporaryFolder, liferayVersion);

		writeGradlePropertiesInWorkspace(
			gradleWorkspaceDir, "liferay.workspace.product=portal-7.1-ga4");

		File gradleProjectDir = buildTemplateWithGradle(
			new File(gradleWorkspaceDir, "modules"), template, name,
			"--liferay-version", liferayVersion);

		testContains(
			gradleProjectDir, "bnd.bnd", "Bundle-Name: " + name,
			"Web-ContextPath: /dynamic-data-foobar-form-field");
		testContains(
			gradleProjectDir, "build.gradle", DEPENDENCY_PORTAL_KERNEL);
		testContains(
			gradleProjectDir, "package.json",
			"\"name\": \"dynamic-data-foobar-form-field\"",
			",foobar_field.js &&");
		testContains(
			gradleProjectDir,
			"src/main/java/foobar/form/field/FoobarDDMFormFieldRenderer.java",
			"property = \"ddm.form.field.type.name=foobar\"",
			"public class FoobarDDMFormFieldRenderer extends " +
				"BaseDDMFormFieldRenderer {",
			"DDMFoobar.render", "/META-INF/resources/foobar.soy");
		testContains(
			gradleProjectDir,
			"src/main/java/foobar/form/field/FoobarDDMFormFieldType.java",
			"ddm.form.field.type.description=foobar-description",
			"ddm.form.field.type.js.class.name=Liferay.DDM.Field.Foobar",
			"ddm.form.field.type.js.module=foobar-form-field",
			"ddm.form.field.type.label=foobar-label",
			"ddm.form.field.type.name=foobar",
			"public class FoobarDDMFormFieldType extends BaseDDMFormFieldType",
			"return \"foobar\";");
		testContains(
			gradleProjectDir, "src/main/resources/META-INF/resources/config.js",
			"field-foobar", "'foobar-form-field': {",
			"path: 'foobar_field.js',");
		testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar.soy",
			"{namespace DDMFoobar}", "variant=\"'foobar'\"",
			"foobar-form-field");
		testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar.es.js",
			"import templates from './foobar.soy';", "* Foobar Component",
			"class Foobar extends Component", "Soy.register(Foobar,",
			"!window.DDMFoobar", "window.DDMFoobar",
			"window.DDMFoobar.render = Foobar;", "export default Foobar;");
		testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar_field.js",
			"'foobar-form-field',", "var FoobarField",
			"value: 'foobar-form-field'", "NAME: 'foobar-form-field'",
			"Liferay.namespace('DDM.Field').Foobar = FoobarField;");

		File mavenWorkspaceDir = buildWorkspace(
			temporaryFolder, "maven", "mavenWS", liferayVersion, mavenExecutor);

		File mavenModulesDir = new File(mavenWorkspaceDir, "modules");

		File mavenProjectDir = buildTemplateWithMaven(
			mavenModulesDir, mavenModulesDir, template, name, "com.test",
			mavenExecutor, "-DclassName=Foobar", "-Dpackage=foobar",
			"-DliferayVersion=" + liferayVersion);

		testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		if (isBuildProjects()) {
			File gradleOutputDir = new File(gradleProjectDir, "build/libs");
			File mavenOutputDir = new File(mavenProjectDir, "target");

			buildProjects(
				_gradleDistribution, mavenExecutor, gradleWorkspaceDir,
				mavenProjectDir, gradleOutputDir, mavenOutputDir,
				":modules:" + name + GRADLE_TASK_PATH_BUILD);
		}
	}

	@Test
	public void testBuildTemplateFormField71WithHyphen() throws Exception {
		String liferayVersion = "7.1.3";
		String name = "foo-bar";
		String template = "form-field";

		File gradleWorkspaceDir = buildWorkspace(
			temporaryFolder, liferayVersion);

		writeGradlePropertiesInWorkspace(
			gradleWorkspaceDir, "liferay.workspace.product=portal-7.1-ga4");

		File gradleProjectDir = buildTemplateWithGradle(
			new File(gradleWorkspaceDir, "modules"), template, name,
			"--liferay-version", liferayVersion);

		testContains(
			gradleProjectDir, "bnd.bnd", "Bundle-Name: " + name,
			"Web-ContextPath: /dynamic-data-foo-bar-form-field");
		testContains(
			gradleProjectDir, "build.gradle", DEPENDENCY_PORTAL_KERNEL);
		testContains(
			gradleProjectDir, "package.json",
			"\"name\": \"dynamic-data-foo-bar-form-field\"",
			",foo-bar_field.js &&");
		testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/form/field/FooBarDDMFormFieldRenderer.java",
			"property = \"ddm.form.field.type.name=fooBar\"",
			"public class FooBarDDMFormFieldRenderer extends " +
				"BaseDDMFormFieldRenderer {",
			"DDMFooBar.render", "/META-INF/resources/foo-bar.soy");
		testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/form/field/FooBarDDMFormFieldType.java",
			"ddm.form.field.type.description=foo-bar-description",
			"ddm.form.field.type.js.class.name=Liferay.DDM.Field.FooBar",
			"ddm.form.field.type.js.module=foo-bar-form-field",
			"ddm.form.field.type.label=foo-bar-label",
			"ddm.form.field.type.name=fooBar",
			"public class FooBarDDMFormFieldType extends BaseDDMFormFieldType",
			"return \"fooBar\";");
		testContains(
			gradleProjectDir, "src/main/resources/META-INF/resources/config.js",
			"field-foo-bar", "'foo-bar-form-field': {",
			"path: 'foo-bar_field.js',");
		testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foo-bar.soy",
			"{namespace DDMFooBar}", "variant=\"'fooBar'\"",
			"foo-bar-form-field");
		testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foo-bar.es.js",
			"import templates from './foo-bar.soy';", "* FooBar Component",
			"class FooBar extends Component", "Soy.register(FooBar,",
			"!window.DDMFooBar", "window.DDMFooBar",
			"window.DDMFooBar.render = FooBar;", "export default FooBar;");
		testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foo-bar_field.js",
			"'foo-bar-form-field',", "var FooBarField",
			"value: 'foo-bar-form-field'", "NAME: 'foo-bar-form-field'",
			"Liferay.namespace('DDM.Field').FooBar = FooBarField;");

		File mavenWorkspaceDir = buildWorkspace(
			temporaryFolder, "maven", "mavenWS", liferayVersion, mavenExecutor);

		File mavenModulesDir = new File(mavenWorkspaceDir, "modules");

		File mavenProjectDir = buildTemplateWithMaven(
			mavenModulesDir, mavenModulesDir, template, name, "com.test",
			mavenExecutor, "-DclassName=FooBar", "-Dpackage=foo.bar",
			"-DliferayVersion=" + liferayVersion);

		testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		if (isBuildProjects()) {
			File gradleOutputDir = new File(gradleProjectDir, "build/libs");
			File mavenOutputDir = new File(mavenProjectDir, "target");

			buildProjects(
				_gradleDistribution, mavenExecutor, gradleWorkspaceDir,
				mavenProjectDir, gradleOutputDir, mavenOutputDir,
				":modules:" + name + GRADLE_TASK_PATH_BUILD);
		}
	}

	@Test
	public void testBuildTemplateFormField72() throws Exception {
		String liferayVersion = "7.2.1";
		String name = "foobar";

		File workspaceDir = buildWorkspace(temporaryFolder, liferayVersion);

		writeGradlePropertiesInWorkspace(
			workspaceDir, "liferay.workspace.product=portal-7.2-ga2");

		File gradleProjectDir = buildTemplateWithGradle(
			new File(workspaceDir, "modules"), "form-field", name,
			"--liferay-version", liferayVersion);

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
		}
	}

	@Test
	public void testBuildTemplateFormField72CustomPackage() throws Exception {
		String liferayVersion = "7.2.1";
		String name = "foobar";

		File workspaceDir = buildWorkspace(temporaryFolder, liferayVersion);

		writeGradlePropertiesInWorkspace(
			workspaceDir, "liferay.workspace.product=portal-7.2-ga2");

		File gradleProjectDir = buildTemplateWithGradle(
			new File(workspaceDir, "modules"), "form-field", name,
			"--liferay-version", liferayVersion, "--package-name",
			"com.liferay.test.form");

		testNotExists(
			gradleProjectDir,
			"src/main/java/com/liferay/test/form/form/field" +
				"/FooBarDDMFormFieldRenderer.java");
	}

	@Test
	public void testBuildTemplateFormField72Maven() throws Exception {
		String groupId = "com.test";
		String liferayVersion = "7.2.1";
		String name = "foobar";
		String template = "form-field";

		File mavenWorkspaceDir = buildWorkspace(
			temporaryFolder, "maven", "mavenWS", liferayVersion, mavenExecutor);

		List<String> completeArgs = new ArrayList<>();

		completeArgs.add("archetype:generate");
		completeArgs.add("--batch-mode");

		if (Validator.isNotNull(System.getenv("JENKINS_HOME"))) {
			completeArgs.add("--settings");

			String content = FileTestUtil.read(
				BaseProjectTemplatesTestCase.class.getClassLoader(),
				"com/liferay/project/templates/dependencies/settings.xml");

			Path tempPath = Files.createTempFile("settings", "xml");

			Files.write(tempPath, content.getBytes());

			completeArgs.add(tempPath.toString());
		}

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
		completeArgs.add("-DliferayVersion=" + liferayVersion);

		String mavenOutput = executeMaven(
			mavenWorkspaceDir, true, mavenExecutor,
			completeArgs.toArray(new String[0]));

		Assert.assertTrue(
			mavenOutput,
			mavenOutput.contains(
				"Form Field project in Maven is only supported in 7.0 and " +
					"7.1"));
	}

	@Test
	public void testBuildTemplateFormField73() throws Exception {
		String liferayVersion = "7.3.5";
		String name = "foobar";

		File workspaceDir = buildWorkspace(temporaryFolder, liferayVersion);

		File gradleProjectDir = buildTemplateWithGradle(
			new File(workspaceDir, "modules"), "form-field", name,
			"--liferay-version", liferayVersion);

		writeGradlePropertiesInWorkspace(
			workspaceDir, "liferay.workspace.product=portal-7.3-ga6");

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
		}
	}

	@Test
	public void testBuildTemplateFormField73WithReactFramework()
		throws Exception {

		String jsFramework = "react";
		String liferayVersion = "7.3.5";
		String name = "foobar";

		File workspaceDir = buildWorkspace(temporaryFolder, liferayVersion);

		writeGradlePropertiesInWorkspace(
			workspaceDir, "liferay.workspace.product=portal-7.3-ga6");

		File gradleProjectDir = buildTemplateWithGradle(
			new File(workspaceDir, "modules"), "form-field", name,
			"--liferay-version", liferayVersion, "--js-framework", jsFramework);

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
			"src/main/resources/META-INF/resources/foobar.es.js",
			"import React from 'react';",
			"import {FieldBase} from 'dynamic-data-mapping-form-field-type" +
				"/FieldBase/ReactFieldBase.es';",
			"import {useSyncValue} from " +
				"'dynamic-data-mapping-form-field-type/hooks/useSyncValue.es';",
			"const Foobar = ({name, onChange, predefinedValue, readOnly, " +
				"value})",
			"const Main = ({label, name, onChange, predefinedValue, " +
				"readOnly, value, ...otherProps})",
			"Main.displayName = 'Foobar';", "export default Main;");

		testNotContains(
			gradleProjectDir, "build.gradle", true, "^repositories \\{.*");
		testNotContains(gradleProjectDir, "build.gradle", "version: \"[0-9].*");
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static URI _gradleDistribution;

}