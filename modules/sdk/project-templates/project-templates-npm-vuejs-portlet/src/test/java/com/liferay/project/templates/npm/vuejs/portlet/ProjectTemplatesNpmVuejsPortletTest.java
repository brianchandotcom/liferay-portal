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

package com.liferay.project.templates.npm.vuejs.portlet;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.BaseProjectTemplatesTestCase;
import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.util.FileTestUtil;

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
public class ProjectTemplatesNpmVuejsPortletTest
	implements BaseProjectTemplatesTestCase {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	@Parameterized.Parameters(
		name = "Testcase-{index}: testing {0}, {1}, {2}, {3}, {4}"
	)
	public static Iterable<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{"foo", "foo", "Foo", "dxp", "7.0.10.17", "yarn"},
				{"foo", "foo", "Foo", "dxp", "7.1.10.7", "yarn"},
				{"foo", "foo", "Foo", "dxp", "7.2.10.7", "yarn"},
				{"foo", "foo", "Foo", "portal", "7.3.7", "yarn"},
				{"foo", "foo", "Foo", "portal", "7.4.3.36", "yarn"},
				{"foo-bar", "foo.bar", "FooBar", "dxp", "7.0.10.17", "yarn"},
				{"foo-bar", "foo.bar", "FooBar", "dxp", "7.1.10.7", "yarn"},
				{"foo-bar", "foo.bar", "FooBar", "dxp", "7.2.10.7", "yarn"},
				{"foo-bar", "foo.bar", "FooBar", "portal", "7.3.7", "yarn"},
				{"foo-bar", "foo.bar", "FooBar", "portal", "7.4.3.36", "yarn"},
				{"foo", "foo", "Foo", "dxp", "7.0.10.17", "npm"},
				{"foo", "foo", "Foo", "dxp", "7.1.10.7", "npm"},
				{"foo", "foo", "Foo", "dxp", "7.2.10.7", "npm"},
				{"foo", "foo", "Foo", "portal", "7.3.7", "npm"},
				{"foo", "foo", "Foo", "portal", "7.4.3.36", "npm"},
				{"foo-bar", "foo.bar", "FooBar", "dxp", "7.0.10.17", "npm"},
				{"foo-bar", "foo.bar", "FooBar", "dxp", "7.1.10.7", "npm"},
				{"foo-bar", "foo.bar", "FooBar", "dxp", "7.2.10.7", "npm"},
				{"foo-bar", "foo.bar", "FooBar", "portal", "7.3.7", "npm"},
				{"foo-bar", "foo.bar", "FooBar", "portal", "7.4.3.36", "npm"}
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

	public ProjectTemplatesNpmVuejsPortletTest(
		String name, String packageName, String className,
		String liferayProduct, String liferayVersion,
		String nodePackageManager) {

		_name = name;
		_packageName = packageName;
		_className = className;
		_liferayProduct = liferayProduct;
		_liferayVersion = liferayVersion;
		_nodePackageManager = nodePackageManager;
	}

	@Test
	public void testBuildTemplateNpmVuejsPortlet() throws Exception {
		String template = "npm-vuejs-portlet";

		testBuildTemplateNpm(
			temporaryFolder, mavenExecutor, template, _name, _packageName,
			_className, _liferayProduct, _liferayVersion, _nodePackageManager,
			_gradleDistribution);
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static URI _gradleDistribution;

	private final String _className;
	private final String _liferayProduct;
	private final String _liferayVersion;
	private final String _name;
	private final String _nodePackageManager;
	private final String _packageName;

}