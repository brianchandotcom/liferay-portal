/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.form.field;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.BaseProjectTemplatesTestCase;
import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.util.FileTestUtil;

import java.io.File;

import java.net.URI;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Arrays;
import java.util.Objects;
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
public class ProjectTemplatesFormFieldWorkspaceReactTest
	implements BaseProjectTemplatesTestCase {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	@Parameterized.Parameters(name = "Testcase-{index}: testing {0}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {{"7.3.7"}, {"7.4.3.56"}});
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

	public ProjectTemplatesFormFieldWorkspaceReactTest(String liferayVersion) {
		_liferayVersion = liferayVersion;
	}

	@Test
	public void testBuildTemplateFormFieldReactPortlet() throws Exception {
		String name = "foobar";

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
			new File(workspaceDir, "modules"), "form-field", name,
			"--js-framework", "react", "--liferay-version", _liferayVersion);

		if (Objects.equals(_liferayVersion, "7.2.1-1")) {
			testContains(
				gradleProjectDir, "build.gradle",
				"compileOnly group: \"com.liferay\", name: " +
					"\"com.liferay.dynamic.data.mapping.api\"",
				"compileOnly group: \"com.liferay\", name: " +
					"\"com.liferay.frontend.js.loader.modules.extender.api\"",
				"jsCompile group: \"com.liferay\", name: " +
					"\"com.liferay.dynamic.data.mapping.form.field.type\"",
				DEPENDENCY_PORTAL_KERNEL);
		}
		else {
			testContains(
				gradleProjectDir, "build.gradle",
				"compileOnly group: \"com.liferay.portal\", name: " +
					"\"release.portal.api\"",
				"jsCompile group: \"com.liferay\", name: " +
					"\"com.liferay.dynamic.data.mapping.form.field.type\"",
				DEPENDENCY_RELEASE_PORTAL_API);
		}

		testContains(
			gradleProjectDir, "package.json", "\"@babel/cli\": \"^7.2.3\"",
			"\"@liferay/portal-" + _liferayVersion.substring(0, 3) +
				"\": \"*\"");
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

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static URI _gradleDistribution;

	private final String _liferayVersion;

}