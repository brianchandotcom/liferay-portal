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

package com.liferay.project.templates.service.builder;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.BaseProjectTemplatesTestCase;
import com.liferay.project.templates.extensions.util.FileUtil;
import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.extensions.util.WorkspaceUtil;
import com.liferay.project.templates.util.FileTestUtil;

import java.io.File;

import java.net.URI;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Gregory Amerson
 */
public class ProjectTemplatesServiceBuilderTest
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
	public void testBuildTemplateContentDTDVersionServiceBuilder70()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", "foo-bar", "--liferay-version", "7.0.6");

		testContains(
			gradleProjectDir, "foo-bar-service/service.xml",
			"liferay-service-builder_7_0_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionServiceBuilder71()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", "foo-bar", "--liferay-version", "7.1.3");

		testContains(
			gradleProjectDir, "foo-bar-service/service.xml",
			"liferay-service-builder_7_1_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionServiceBuilder72()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", "foo-bar", "--liferay-version", "7.2.1");

		testContains(
			gradleProjectDir, "foo-bar-service/service.xml",
			"liferay-service-builder_7_2_0.dtd");
	}

	@Test
	public void testBuildTemplateServiceBuilder70() throws Exception {
		String name = "guestbook";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferay-version", "7.0.6");

		testContains(
			gradleProjectDir, name + "-api/build.gradle",
			"biz.aQute.bnd.annotation\", version: \"4.3.0",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		testContains(
			gradleProjectDir, name + "-service/build.gradle",
			"biz.aQute.bnd.annotation\", version: \"4.3.0",
			"com.liferay.petra.io\", version: \"1.0.0",
			"com.liferay.portal.spring.extender\", version: \"2.0.0",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"2.42.0");

		testNotContains(
			gradleProjectDir, name + "-api/build.gradle",
			"org.osgi.annotation.versioning");
		testNotContains(
			gradleProjectDir, name + "-service/build.gradle",
			"org.osgi.annotation.versioning");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service-builder", name, "com.test", mavenExecutor,
			"-Dpackage=" + packageName, "-DliferayVersion=7.0.6");

		if (isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, gradleProjectDir, name,
				packageName, "");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilder71() throws Exception {
		String name = "guestbook";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferay-version", "7.1.3");

		testContains(
			gradleProjectDir, name + "-api/build.gradle",
			"biz.aQute.bnd.annotation\", version: \"4.3.0",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		testContains(
			gradleProjectDir, name + "-service/build.gradle",
			"biz.aQute.bnd.annotation\", version: \"4.3.0",
			"com.liferay.petra.io\", version: \"2.0.0",
			"com.liferay.portal.spring.extender.api\", version: \"3.0.0",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		testNotContains(
			gradleProjectDir, name + "-api/build.gradle",
			"org.osgi.annotation.versioning");
		testNotContains(
			gradleProjectDir, name + "-service/build.gradle",
			"org.osgi.annotation.versioning");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service-builder", name, "com.test", mavenExecutor,
			"-Dpackage=" + packageName, "-DliferayVersion=7.1.3");

		_testBuildTemplateServiceBuilder(
			gradleProjectDir, mavenProjectDir, gradleProjectDir, name,
			packageName, "");
	}

	@Test
	public void testBuildTemplateServiceBuilder72() throws Exception {
		String name = "guestbook";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferay-version", "7.2.1");

		testContains(
			gradleProjectDir, name + "-api/build.gradle",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0",
			"org.osgi.annotation.versioning\", version: \"1.1.0");
		testContains(
			gradleProjectDir, name + "-service/build.gradle",
			"com.liferay.petra.io\", version: \"3.0.0",
			"com.liferay.petra.lang\", version: \"3.0.0\"",
			"com.liferay.petra.string\", version: \"3.0.0\"",
			"com.liferay.portal.aop.api\", version: \"1.0.0\"",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0",
			"org.osgi.annotation.versioning\", version: \"1.1.0");
		testContains(
			gradleProjectDir, name + "-service/service.xml",
			"dependency-injector=\"ds\"");
		testContains(
			gradleProjectDir, name + "-service/bnd.bnd",
			"-dsannotations-options: inherit");

		testNotContains(
			gradleProjectDir, name + "-api/build.gradle",
			"biz.aQute.bnd.annotation");
		testNotContains(
			gradleProjectDir, name + "-service/build.gradle",
			"biz.aQute.bnd.annotation", "com.liferay.portal.spring.extender");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service-builder", name, "com.test", mavenExecutor,
			"-Dpackage=" + packageName, "-DliferayVersion=7.2.1");

		if (isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, gradleProjectDir, name,
				packageName, "");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilder73() throws Exception {
		String name = "guestbook";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferay-version", "7.3.0");

		testContains(
			gradleProjectDir, name + "-api/build.gradle",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"5.4.0",
			"org.osgi.annotation.versioning\", version: \"1.1.0");
		testContains(
			gradleProjectDir, name + "-service/build.gradle",
			"com.liferay.petra.io\", version: \"4.0.2",
			"com.liferay.petra.lang\", version: \"4.0.1\"",
			"com.liferay.petra.string\", version: \"4.0.1\"",
			"com.liferay.portal.aop.api\", version: \"2.0.0\"",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"5.4.0",
			"org.osgi.annotation.versioning\", version: \"1.1.0");
		testContains(
			gradleProjectDir, name + "-service/service.xml",
			"dependency-injector=\"ds\"");
		testContains(
			gradleProjectDir, name + "-service/bnd.bnd",
			"-dsannotations-options: inherit");

		testNotContains(
			gradleProjectDir, name + "-api/build.gradle",
			"biz.aQute.bnd.annotation");
		testNotContains(
			gradleProjectDir, name + "-service/build.gradle",
			"biz.aQute.bnd.annotation", "com.liferay.portal.spring.extender");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service-builder", name, "com.test", mavenExecutor,
			"-Dpackage=" + packageName, "-DliferayVersion=7.3.0");

		if (isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, gradleProjectDir, name,
				packageName, "");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilder72Spring() throws Exception {
		String name = "guestbook";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferay-version", "7.2.1", "--dependency-injector", "spring");

		testNotContains(
			gradleProjectDir, name + "-api/build.gradle", "biz.aQute.bnd");
		testNotContains(
			gradleProjectDir, name + "-service/build.gradle", "biz.aQute.bnd");

		testContains(
			gradleProjectDir, name + "-api/build.gradle",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0",
			"org.osgi.annotation.versioning\", version: \"1.1.0");
		testContains(
			gradleProjectDir, name + "-service/build.gradle",
			"com.liferay.petra.io\", version: \"3.0.0",
			"com.liferay.petra.lang\", version: \"3.0.0\"",
			"com.liferay.petra.string\", version: \"3.0.0\"",
			"com.liferay.portal.aop.api\", version: \"1.0.0\"",
			"com.liferay.portal.spring.extender.api\", version: \"3.0.0",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0",
			"org.osgi.annotation.versioning\", version: \"1.1.0");
		testNotContains(
			gradleProjectDir, name + "-service/bnd.bnd",
			"-dsannotations-options: inherit");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service-builder", name, "com.test", mavenExecutor,
			"-Dpackage=" + packageName, "-DliferayVersion=7.2.1",
			"-DdependencyInjector=spring");

		if (isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, gradleProjectDir, name,
				packageName, "");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilder73Spring() throws Exception {
		String name = "guestbook";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferay-version", "7.3.0", "--dependency-injector", "spring");

		testNotContains(
			gradleProjectDir, name + "-api/build.gradle", "biz.aQute.bnd");
		testNotContains(
			gradleProjectDir, name + "-service/build.gradle", "biz.aQute.bnd");

		testContains(
			gradleProjectDir, name + "-api/build.gradle",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"5.4.0",
			"org.osgi.annotation.versioning\", version: \"1.1.0");
		testContains(
			gradleProjectDir, name + "-service/build.gradle",
			"com.liferay.petra.io\", version: \"4.0.2",
			"com.liferay.petra.lang\", version: \"4.0.1\"",
			"com.liferay.petra.string\", version: \"4.0.1\"",
			"com.liferay.portal.aop.api\", version: \"2.0.0\"",
			"com.liferay.portal.spring.extender.api\", version: \"5.0.0",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"5.4.0",
			"org.osgi.annotation.versioning\", version: \"1.1.0");
		testNotContains(
			gradleProjectDir, name + "-service/bnd.bnd",
			"-dsannotations-options: inherit");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service-builder", name, "com.test", mavenExecutor,
			"-Dpackage=" + packageName, "-DliferayVersion=7.3.0",
			"-DdependencyInjector=spring");

		if (isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, gradleProjectDir, name,
				packageName, "");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderCheckExports() throws Exception {
		String name = "guestbook";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferay-version", "7.3.0");

		File gradleServiceXml = new File(
			new File(gradleProjectDir, name + "-service"), "service.xml");

		Consumer<Document> consumer = document -> {
			Element documentElement = document.getDocumentElement();

			documentElement.setAttribute("package-path", "com.liferay.test");
		};

		editXml(gradleServiceXml, consumer);

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service-builder", name, "com.test", mavenExecutor,
			"-Dpackage=" + packageName, "-DliferayVersion=7.3.0");

		File mavenServiceXml = new File(
			new File(mavenProjectDir, name + "-service"), "service.xml");

		editXml(mavenServiceXml, consumer);

		testContains(
			gradleProjectDir, name + "-api/bnd.bnd", "Export-Package:\\",
			packageName + ".exception,\\", packageName + ".model,\\",
			packageName + ".service,\\", packageName + ".service.persistence");

		if (isBuildProjects()) {
			Optional<String> stdOutput = executeGradle(
				gradleProjectDir, false, true, _gradleDistribution,
				name + "-service" + GRADLE_TASK_PATH_BUILD);

			Assert.assertTrue(stdOutput.isPresent());

			String gradleOutput = stdOutput.get();

			Assert.assertTrue(
				"Expected gradle output to include build error. " +
					gradleOutput,
				gradleOutput.contains("Exporting an empty package"));

			String mavenOutput = executeMaven(
				mavenProjectDir, true, mavenExecutor, MAVEN_GOAL_PACKAGE);

			Assert.assertTrue(
				"Expected maven output to include build error. " + mavenOutput,
				mavenOutput.contains("Exporting an empty package"));
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderNestedPath70() throws Exception {
		File workspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "ws-nested-path");

		File destinationDir = new File(
			workspaceProjectDir, "modules/nested/path");

		Assert.assertTrue(destinationDir.mkdirs());

		File gradleProjectDir = buildTemplateWithGradle(
			destinationDir, "service-builder", "sample", "--package-name",
			"com.test.sample", "--liferay-version", "7.0.6");

		testContains(
			gradleProjectDir, "sample-service/build.gradle",
			"compileOnly project(\":modules:nested:path:sample:sample-api\")");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service-builder", "sample", "com.test",
			mavenExecutor, "-Dpackage=com.test.sample",
			"-DliferayVersion=7.0.6");

		if (isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, workspaceProjectDir,
				"sample", "com.test.sample", ":modules:nested:path:sample");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderNestedPath71() throws Exception {
		File workspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "ws-nested-path");

		File destinationDir = new File(
			workspaceProjectDir, "modules/nested/path");

		Assert.assertTrue(destinationDir.mkdirs());

		File gradleProjectDir = buildTemplateWithGradle(
			destinationDir, "service-builder", "sample", "--package-name",
			"com.test.sample", "--liferay-version", "7.1.3");

		testContains(
			gradleProjectDir, "sample-service/build.gradle",
			"compileOnly project(\":modules:nested:path:sample:sample-api\")");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service-builder", "sample", "com.test",
			mavenExecutor, "-Dpackage=com.test.sample",
			"-DliferayVersion=7.1.3");

		if (isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, workspaceProjectDir,
				"sample", "com.test.sample", ":modules:nested:path:sample");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderNestedPath72() throws Exception {
		File workspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "ws-nested-path");

		File destinationDir = new File(
			workspaceProjectDir, "modules/nested/path");

		Assert.assertTrue(destinationDir.mkdirs());

		File gradleProjectDir = buildTemplateWithGradle(
			destinationDir, "service-builder", "sample", "--package-name",
			"com.test.sample", "--liferay-version", "7.2.1");

		testContains(
			gradleProjectDir, "sample-service/build.gradle",
			"compileOnly project(\":modules:nested:path:sample:sample-api\")");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service-builder", "sample", "com.test",
			mavenExecutor, "-Dpackage=com.test.sample",
			"-DliferayVersion=7.2.1");

		if (isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, workspaceProjectDir,
				"sample", "com.test.sample", ":modules:nested:path:sample");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderNestedPath73() throws Exception {
		File workspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "ws-nested-path");

		File destinationDir = new File(
			workspaceProjectDir, "modules/nested/path");

		Assert.assertTrue(destinationDir.mkdirs());

		File gradleProjectDir = buildTemplateWithGradle(
			destinationDir, "service-builder", "sample", "--package-name",
			"com.test.sample", "--liferay-version", "7.3.0");

		testContains(
			gradleProjectDir, "sample-service/build.gradle",
			"compileOnly project(\":modules:nested:path:sample:sample-api\")");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service-builder", "sample", "com.test",
			mavenExecutor, "-Dpackage=com.test.sample",
			"-DliferayVersion=7.3.0");

		if (isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, workspaceProjectDir,
				"sample", "com.test.sample", ":modules:nested:path:sample");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderTargetPlatformEnabled70()
		throws Exception {

		File workspaceDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "workspace");

		enableTargetPlatformInWorkspace(workspaceDir, "7.0.6");

		File modulesDir = new File(workspaceDir, "modules");

		File workspaceProjectDir = buildTemplateWithGradle(
			modulesDir, "service-builder", "foo", "--package-name", "test",
			"--liferay-version", "7.0.6", "--dependency-management-enabled");

		testContains(
			workspaceProjectDir, "foo-api/build.gradle",
			DEPENDENCY_PORTAL_KERNEL, "biz.aQute.bnd.annotation");
		testContains(
			workspaceProjectDir, "foo-service/build.gradle",
			DEPENDENCY_PORTAL_KERNEL, "biz.aQute.bnd.annotation",
			"com.liferay.portal.spring.extender");

		testNotContains(
			workspaceProjectDir, "foo-api/build.gradle",
			"org.osgi.annotation.versioning");
		testNotContains(
			workspaceProjectDir, "foo-service/build.gradle",
			"org.osgi.annotation.versioning");

		if (isBuildProjects()) {
			executeGradle(
				workspaceDir, _gradleDistribution,
				":modules:foo:foo-service" + _GRADLE_TASK_PATH_BUILD_SERVICE);

			executeGradle(
				workspaceDir, _gradleDistribution,
				":modules:foo:foo-api:build");

			executeGradle(
				workspaceDir, _gradleDistribution,
				":modules:foo:foo-service:build");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderTargetPlatformEnabled71()
		throws Exception {

		File workspaceDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "workspace");

		enableTargetPlatformInWorkspace(workspaceDir, "7.1.3");

		File modulesDir = new File(workspaceDir, "modules");

		File workspaceProjectDir = buildTemplateWithGradle(
			modulesDir, "service-builder", "foo", "--package-name", "test",
			"--liferay-version", "7.1.3", "--dependency-management-enabled");

		testContains(
			workspaceProjectDir, "foo-api/build.gradle",
			DEPENDENCY_PORTAL_KERNEL, "biz.aQute.bnd.annotation");
		testContains(
			workspaceProjectDir, "foo-service/build.gradle",
			DEPENDENCY_PORTAL_KERNEL, "biz.aQute.bnd.annotation",
			"com.liferay.portal.spring.extender.api");

		testNotContains(
			workspaceProjectDir, "foo-api/build.gradle",
			"org.osgi.annotation.versioning");
		testNotContains(
			workspaceProjectDir, "foo-service/build.gradle",
			"org.osgi.annotation.versioning");

		if (isBuildProjects()) {
			executeGradle(
				workspaceDir, _gradleDistribution,
				":modules:foo:foo-service" + _GRADLE_TASK_PATH_BUILD_SERVICE);

			executeGradle(
				workspaceDir, _gradleDistribution,
				":modules:foo:foo-api:build");

			executeGradle(
				workspaceDir, _gradleDistribution,
				":modules:foo:foo-service:build");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderTargetPlatformEnabled72()
		throws Exception {

		File workspaceDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "workspace");

		enableTargetPlatformInWorkspace(workspaceDir, "7.2.1");

		File modulesDir = new File(workspaceDir, "modules");

		File workspaceProjectDir = buildTemplateWithGradle(
			modulesDir, "service-builder", "foo", "--package-name", "test",
			"--liferay-version", "7.2.1", "--dependency-management-enabled");

		testContains(
			workspaceProjectDir, "foo-api/build.gradle",
			DEPENDENCY_PORTAL_KERNEL, "org.osgi.annotation.versioning");
		testContains(
			workspaceProjectDir, "foo-service/build.gradle",
			"com.liferay.petra.lang", "com.liferay.petra.string",
			"com.liferay.portal.aop.api", DEPENDENCY_PORTAL_KERNEL,
			"org.osgi.annotation.versioning");

		testNotContains(
			workspaceProjectDir, "foo-api/build.gradle",
			"biz.aQute.bnd.annotation");
		testNotContains(
			workspaceProjectDir, "foo-service/build.gradle",
			"biz.aQute.bnd.annotation", "com.liferay.portal.spring.extender");

		if (isBuildProjects()) {
			executeGradle(
				workspaceDir, _gradleDistribution,
				":modules:foo:foo-service" + _GRADLE_TASK_PATH_BUILD_SERVICE);

			executeGradle(
				workspaceDir, _gradleDistribution,
				":modules:foo:foo-api:build");

			executeGradle(
				workspaceDir, _gradleDistribution,
				":modules:foo:foo-service:build");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderTargetPlatformEnabled73()
		throws Exception {

		File workspaceDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "workspace");

		enableTargetPlatformInWorkspace(workspaceDir, "7.3.0");

		File modulesDir = new File(workspaceDir, "modules");

		File workspaceProjectDir = buildTemplateWithGradle(
			modulesDir, "service-builder", "foo", "--package-name", "test",
			"--liferay-version", "7.3.0", "--dependency-management-enabled");

		testContains(
			workspaceProjectDir, "foo-api/build.gradle",
			DEPENDENCY_PORTAL_KERNEL, "org.osgi.annotation.versioning");
		testContains(
			workspaceProjectDir, "foo-service/build.gradle",
			"com.liferay.petra.lang", "com.liferay.petra.string",
			"com.liferay.portal.aop.api", DEPENDENCY_PORTAL_KERNEL,
			"org.osgi.annotation.versioning");

		testNotContains(
			workspaceProjectDir, "foo-api/build.gradle",
			"biz.aQute.bnd.annotation");
		testNotContains(
			workspaceProjectDir, "foo-service/build.gradle",
			"biz.aQute.bnd.annotation", "com.liferay.portal.spring.extender");

		if (isBuildProjects()) {
			executeGradle(
				workspaceDir, _gradleDistribution,
				":modules:foo:foo-service" + _GRADLE_TASK_PATH_BUILD_SERVICE);

			executeGradle(
				workspaceDir, _gradleDistribution,
				":modules:foo:foo-api:build");

			executeGradle(
				workspaceDir, _gradleDistribution,
				":modules:foo:foo-service:build");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderWithDashes70() throws Exception {
		String name = "backend-integration";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferay-version", "7.0.6");

		testContains(
			gradleProjectDir, name + "-api/build.gradle",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		testContains(
			gradleProjectDir, name + "-service/build.gradle",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"2.42.0");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service-builder", name, "com.test", mavenExecutor,
			"-Dpackage=" + packageName, "-DliferayVersion=7.0.6");

		if (isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, gradleProjectDir, name,
				packageName, "");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderWithDashes71() throws Exception {
		String name = "backend-integration";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferay-version", "7.1.3");

		testContains(
			gradleProjectDir, name + "-api/build.gradle",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		testContains(
			gradleProjectDir, name + "-service/build.gradle",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service-builder", name, "com.test", mavenExecutor,
			"-Dpackage=" + packageName, "-DliferayVersion=7.1.3");

		if (isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, gradleProjectDir, name,
				packageName, "");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderWithDashes72() throws Exception {
		String name = "backend-integration";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferay-version", "7.2.1");

		testContains(
			gradleProjectDir, name + "-api/build.gradle",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");
		testContains(
			gradleProjectDir, name + "-service/build.gradle",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service-builder", name, "com.test", mavenExecutor,
			"-Dpackage=" + packageName, "-DliferayVersion=7.2.1");

		if (isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, gradleProjectDir, name,
				packageName, "");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderWithDashes73() throws Exception {
		String name = "backend-integration";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferay-version", "7.3.0");

		testContains(
			gradleProjectDir, name + "-api/build.gradle",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"5.4.0");
		testContains(
			gradleProjectDir, name + "-service/build.gradle",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"5.4.0");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service-builder", name, "com.test", mavenExecutor,
			"-Dpackage=" + packageName, "-DliferayVersion=7.3.0");

		if (isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, gradleProjectDir, name,
				packageName, "");
		}
	}

	@Test
	public void testCompareServiceBuilderPluginVersions() throws Exception {
		String name = "sample";
		String packageName = "com.test.sample";
		String serviceProjectName = name + "-service";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName);

		Optional<String> gradleResult = executeGradle(
			gradleProjectDir, true, _gradleDistribution,
			":" + serviceProjectName + ":dependencies");

		String gradleServiceBuilderVersion = null;

		Matcher matcher = _serviceBuilderVersionPattern.matcher(
			gradleResult.get());

		if (matcher.matches()) {
			gradleServiceBuilderVersion = matcher.group(1);
		}

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service-builder", name, "com.test", mavenExecutor,
			"-Dpackage=" + packageName);

		String mavenResult = executeMaven(
			new File(mavenProjectDir, serviceProjectName), mavenExecutor,
			MAVEN_GOAL_BUILD_SERVICE);

		matcher = _serviceBuilderVersionPattern.matcher(mavenResult);

		String mavenServiceBuilderVersion = null;

		if (matcher.matches()) {
			mavenServiceBuilderVersion = matcher.group(1);
		}

		Assert.assertEquals(
			"com.liferay.portal.tools.service.builder versions do not match",
			gradleServiceBuilderVersion, mavenServiceBuilderVersion);
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private File _buildTemplateWithGradle(
			String template, String name, String... args)
		throws Exception {

		File destinationDir = temporaryFolder.newFolder("gradle");

		return buildTemplateWithGradle(destinationDir, template, name, args);
	}

	private void _testBuildTemplateServiceBuilder(
			File gradleProjectDir, File mavenProjectDir, final File rootProject,
			String name, String packageName, final String projectPath)
		throws Exception {

		String apiProjectName = name + "-api";
		final String serviceProjectName = name + "-service";

		boolean workspace = WorkspaceUtil.isWorkspace(gradleProjectDir);

		if (!workspace) {
			testContains(
				gradleProjectDir, "settings.gradle",
				"include \"" + apiProjectName + "\", \"" + serviceProjectName +
					"\"");
		}

		testContains(
			gradleProjectDir, apiProjectName + "/bnd.bnd", "Export-Package:\\",
			packageName + ".exception,\\", packageName + ".model,\\",
			packageName + ".service,\\", packageName + ".service.persistence");

		testContains(
			gradleProjectDir, serviceProjectName + "/bnd.bnd",
			"Liferay-Service: true");

		if (!workspace) {
			testContains(
				gradleProjectDir, serviceProjectName + "/build.gradle",
				"compileOnly project(\":" + apiProjectName + "\")");
		}

		if (!isBuildProjects()) {
			return;
		}

		_testChangePortletModelHintsXml(
			gradleProjectDir, serviceProjectName,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					executeGradle(
						rootProject, _gradleDistribution,
						projectPath + ":" + serviceProjectName +
							_GRADLE_TASK_PATH_BUILD_SERVICE);

					return null;
				}

			});

		executeGradle(
			rootProject, _gradleDistribution,
			projectPath + ":" + serviceProjectName + GRADLE_TASK_PATH_BUILD);

		File gradleApiBundleFile = testExists(
			gradleProjectDir,
			apiProjectName + "/build/libs/" + packageName + ".api-1.0.0.jar");

		File gradleServiceBundleFile = testExists(
			gradleProjectDir,
			serviceProjectName + "/build/libs/" + packageName +
				".service-1.0.0.jar");

		_testChangePortletModelHintsXml(
			mavenProjectDir, serviceProjectName,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					executeMaven(
						new File(mavenProjectDir, serviceProjectName),
						mavenExecutor, MAVEN_GOAL_BUILD_SERVICE);

					return null;
				}

			});

		File gradleServicePropertiesFile = new File(
			gradleProjectDir,
			serviceProjectName + "/src/main/resources/service.properties");

		File mavenServicePropertiesFile = new File(
			mavenProjectDir,
			serviceProjectName + "/src/main/resources/service.properties");

		Files.copy(
			gradleServicePropertiesFile.toPath(),
			mavenServicePropertiesFile.toPath(),
			StandardCopyOption.REPLACE_EXISTING);

		executeMaven(mavenProjectDir, mavenExecutor, MAVEN_GOAL_PACKAGE);

		File mavenApiBundleFile = testExists(
			mavenProjectDir,
			apiProjectName + "/target/" + name + "-api-1.0.0.jar");
		File mavenServiceBundleFile = testExists(
			mavenProjectDir,
			serviceProjectName + "/target/" + name + "-service-1.0.0.jar");

		testBundlesDiff(gradleApiBundleFile, mavenApiBundleFile);
		testBundlesDiff(gradleServiceBundleFile, mavenServiceBundleFile);
	}

	private void _testChangePortletModelHintsXml(
			File projectDir, String serviceProjectName,
			Callable<Void> buildServiceCallable)
		throws Exception {

		buildServiceCallable.call();

		File file = testExists(
			projectDir,
			serviceProjectName +
				"/src/main/resources/META-INF/portlet-model-hints.xml");

		Path path = file.toPath();

		String content = FileUtil.read(path);

		String newContent = content.replace(
			"<field name=\"field5\" type=\"String\" />",
			"<field name=\"field5\" type=\"String\">\n\t\t\t<hint-collection " +
				"name=\"CLOB\" />\n\t\t</field>");

		Assert.assertNotEquals("Unexpected " + file, content, newContent);

		Files.write(path, newContent.getBytes(StandardCharsets.UTF_8));

		buildServiceCallable.call();

		Assert.assertEquals(
			"Changes in " + file + " incorrectly overridden", newContent,
			FileUtil.read(path));
	}

	private static final String _GRADLE_TASK_PATH_BUILD_SERVICE =
		":buildService";

	private static URI _gradleDistribution;
	private static final Pattern _serviceBuilderVersionPattern =
		Pattern.compile(
			".*service\\.builder:([0-9]+\\.[0-9]+\\.[0-9]+).*",
			Pattern.DOTALL | Pattern.MULTILINE);

}