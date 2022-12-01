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

package com.liferay.project.templates.workspace;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.BaseProjectTemplatesTestCase;
import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.util.FileTestUtil;

import java.io.File;

import java.net.URI;

import java.util.Arrays;
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
public class ProjectTemplatesWorkspaceProductKeyTest
	implements BaseProjectTemplatesTestCase {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	@Parameterized.Parameters(name = "Testcase-{index}: testing {1} {0}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{"dxp", "7.0.10.17"}, {"dxp", "7.1.10.7"}, {"dxp", "7.2.10.7"},
				{"portal", "7.3.7"}, {"portal", "7.4.3.36"}
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

	public ProjectTemplatesWorkspaceProductKeyTest(
		String liferayProduct, String liferayVersion) {

		_liferayProduct = liferayProduct;
		_liferayVersion = liferayVersion;
	}

	@Test
	public void testBuildTemplateWorkspaceProductKey() throws Exception {
		File workspaceProjectDir = buildWorkspace(
			temporaryFolder, "gradle", "foows", _liferayVersion, mavenExecutor);

		setProductVersion(workspaceProjectDir, _liferayVersion);

		if (isBuildProjects()) {
			String name = "foo-portlet";

			buildTemplateWithGradle(
				new File(workspaceProjectDir, "modules"), "mvc-portlet", name,
				"--liferay-product", _liferayProduct, "--liferay-version",
				_liferayVersion);

			String gradleOutput = String.valueOf(
				executeGradle(
					workspaceProjectDir, true, _gradleDistribution,
					":modules:" + name + GRADLE_TASK_PATH_BUILD));

			if (_liferayVersion.startsWith("7.0")) {
				Assert.assertTrue(
					gradleOutput.contains(
						"release.dxp.bom:" + _liferayVersion));
			}
			else if (_liferayVersion.startsWith("7.1")) {
				Assert.assertTrue(
					gradleOutput.contains(
						"release.dxp.bom:" + _liferayVersion));
			}
			else if (_liferayVersion.startsWith("7.2")) {
				Assert.assertTrue(
					gradleOutput.contains(
						"release.dxp.bom:" + _liferayVersion));
			}
			else if (_liferayVersion.startsWith("7.3")) {
				Assert.assertTrue(
					gradleOutput.contains(
						"release.portal.bom:" + _liferayVersion));
			}
			else {
				Assert.assertTrue(
					gradleOutput.contains(
						"release.portal.bom:" + _liferayVersion));
			}
		}
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static URI _gradleDistribution;

	private final String _liferayProduct;
	private final String _liferayVersion;

}