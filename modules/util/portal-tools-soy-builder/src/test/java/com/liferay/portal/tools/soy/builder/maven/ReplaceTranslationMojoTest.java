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

package com.liferay.portal.tools.soy.builder.maven;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.portal.tools.soy.builder.commands.ReplaceTranslationCommandTest;
import com.liferay.portal.tools.soy.builder.util.FileTestUtil;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.junit.Assert;
import org.junit.ClassRule;

/**
 * @author Andrea Di Giorgi
 */
public class ReplaceTranslationMojoTest extends ReplaceTranslationCommandTest {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	public ReplaceTranslationMojoTest(String testDirName) {
		super(testDirName);
	}

	@Override
	protected void testSoyJs(File dir) throws Exception {
		File projectDir = temporaryFolder.newFolder("maven");

		_preparePomXml(projectDir, dir);

		MavenExecutor.Result result = mavenExecutor.execute(
			projectDir, "soy-builder:replace-translation");

		Assert.assertEquals(result.output, 0, result.exitCode);
	}

	private static void _preparePomXml(File projectDir, File dir)
		throws IOException {

		String content = FileTestUtil.read(
			ReplaceTranslationMojoTest.class.getClassLoader(),
			"com/liferay/portal/tools/soy/builder/maven/dependencies" +
				"/pom_xml.tmpl");

		content = content.replace(
			"[$SOY_BUILDER_VERSION$]", _SOY_BUILDER_VERSION);

		content = content.replace("[$SOY_BUILDER_DIR$]", dir.getAbsolutePath());

		File pomXmlFile = new File(projectDir, "pom.xml");

		Files.write(
			pomXmlFile.toPath(), content.getBytes(StandardCharsets.UTF_8));
	}

	private static final String _SOY_BUILDER_VERSION = System.getProperty(
		"soy.builder.version");

}