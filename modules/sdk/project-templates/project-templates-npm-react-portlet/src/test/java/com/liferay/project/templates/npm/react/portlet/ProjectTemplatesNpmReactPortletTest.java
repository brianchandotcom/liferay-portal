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

package com.liferay.project.templates.npm.react.portlet;

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
public class ProjectTemplatesNpmReactPortletTest
	implements BaseProjectTemplatesTestCase {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	@Parameterized.Parameters(
		name = "Testcase-{index}: testing {0}, {1}, {2}, {3}"
	)
	public static Iterable<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{"foo", "foo", "Foo", "7.0.6"}, {"foo", "foo", "Foo", "7.1.3"},
				{"foo", "foo", "Foo", "7.2.1"}, {"foo", "foo", "Foo", "7.3.2"},
				{"foo-bar", "foo.bar", "FooBar", "7.0.6"},
				{"foo-bar", "foo.bar", "FooBar", "7.1.3"},
				{"foo-bar", "foo.bar", "FooBar", "7.2.1"},
				{"foo-bar", "foo.bar", "FooBar", "7.3.2"}
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

	public ProjectTemplatesNpmReactPortletTest(
		String name, String packageName, String className,
		String liferayVersion) {

		_name = name;
		_packageName = packageName;
		_className = className;
		_liferayVersion = liferayVersion;
	}

	@Test
	public void testBuildTemplateNpmReactPortlet() throws Exception {
		String template = "npm-react-portlet";

		testBuildTemplateNpm(
			temporaryFolder, mavenExecutor, template, _name, _packageName,
			_className, _liferayVersion, _gradleDistribution);
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static URI _gradleDistribution;

	private final String _className;
	private final String _liferayVersion;
	private final String _name;
	private final String _packageName;

}