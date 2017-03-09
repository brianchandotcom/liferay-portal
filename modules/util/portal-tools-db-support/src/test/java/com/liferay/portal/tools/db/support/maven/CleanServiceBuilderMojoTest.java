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

package com.liferay.portal.tools.db.support.maven;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.portal.tools.db.support.commands.CleanServiceBuilderCommandTest;
import com.liferay.portal.tools.db.support.util.FileTestUtil;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.junit.Assert;
import org.junit.ClassRule;

/**
 * @author Andrea Di Giorgi
 */
public class CleanServiceBuilderMojoTest
	extends CleanServiceBuilderCommandTest {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	public CleanServiceBuilderMojoTest(String mode) throws IOException {
		super(mode);
	}

	@Override
	protected void cleanServiceBuilder(
			File serviceXmlFile, String servletContextName, String url)
		throws Exception {

		File projectDir = temporaryFolder.newFolder("maven");

		_preparePomXml(projectDir, serviceXmlFile, servletContextName, url);

		MavenExecutor.Result result = mavenExecutor.execute(
			projectDir, "db-support:clean-service-builder");

		Assert.assertEquals(result.output, 0, result.exitCode);
	}

	private static void _preparePomXml(
			File projectDir, File serviceXmlFile, String servletContextName,
			String url)
		throws IOException {

		String content = FileTestUtil.read(
			CleanServiceBuilderMojoTest.class, "dependencies/pom_xml.tmpl");

		content = _replace(
			content, "[$DB_SUPPORT_VERSION$]", _DB_SUPPORT_VERSION);
		content = _replace(content, "[$H2_VERSION$]", _H2_VERSION);

		content = _replace(
			content, "[$DB_SUPPORT_SERVICE_XML_FILE$]", serviceXmlFile);
		content = _replace(
			content, "[$DB_SUPPORT_SERVLET_CONTEXT_NAME$]", servletContextName);
		content = _replace(content, "[$DB_SUPPORT_URL$]", url);

		File pomXmlFile = new File(projectDir, "pom.xml");

		Files.write(
			pomXmlFile.toPath(), content.getBytes(StandardCharsets.UTF_8));
	}

	private static String _replace(String s, String key, File file) {
		String value = null;

		if (file != null) {
			value = file.getAbsolutePath();
		}

		return _replace(s, key, value);
	}

	private static String _replace(String s, String key, String value) {
		if (value == null) {
			value = "";
		}

		return s.replace(key, value);
	}

	private static final String _DB_SUPPORT_VERSION = System.getProperty(
		"db.support.version");

	private static final String _H2_VERSION = System.getProperty("h2.version");

}