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

package com.liferay.project.templates.constants;

import com.liferay.project.templates.util.StringTestUtil;

import java.net.URI;

import java.util.Arrays;

import javax.xml.xpath.XPathExpression;

/**
 * @author Lawrence Lee
 */
public class ProjectTemplatesTestConstants {

	public static final String BUILD_PROJECTS = System.getProperty(
		"project.templates.test.builds");

	public static final String BUNDLES_DIFF_IGNORES = StringTestUtil.merge(
		Arrays.asList(
			"*.js.map", "*manifest.json", "*pom.properties", "*pom.xml",
			"*package.json", "Archiver-Version", "Build-Jdk", "Built-By",
			"Javac-Debug", "Javac-Deprecation", "Javac-Encoding"),
		',');

	public static final String DEPENDENCY_MODULES_EXTENDER_API =
		"compileOnly group: \"com.liferay\", name: " +
			"\"com.liferay.frontend.js.loader.modules.extender.api\"";

	public static final String GRADLE_TASK_PATH_BUILD = ":build";

	public static final String[] GRADLE_WRAPPER_FILE_NAMES = {
		"gradlew", "gradlew.bat", "gradle/wrapper/gradle-wrapper.jar",
		"gradle/wrapper/gradle-wrapper.properties"
	};

	public static final String GRADLE_WRAPPER_VERSION = "4.10.2";

	public static final String MAVEN_GOAL_PACKAGE = "package";

	public static final String[] MAVEN_WRAPPER_FILE_NAMES = {
		"mvnw", "mvnw.cmd", ".mvn/wrapper/maven-wrapper.jar",
		".mvn/wrapper/maven-wrapper.properties"
	};

	public static final String OUTPUT_FILENAME_GLOB_REGEX = "*.{jar,war}";

	public static final String REPOSITORY_CDN_URL =
		"https://repository-cdn.liferay.com/nexus/content/groups/public";

	public static final boolean TEST_DEBUG_BUNDLE_DIFFS = Boolean.getBoolean(
		"test.debug.bundle.diffs");

	public static URI gradleDistribution;
	public static XPathExpression pomXmlNpmInstallXPathExpression;

}