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

package com.liferay.project.templates.war.core.ext;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.BaseProjectTemplatesTestCase;

import java.io.File;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Lawrence Lee
 */
public class ProjectTemplatesWarCoreExtTest
	implements BaseProjectTemplatesTestCase {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	@Test
	public void testBuildTemplateWarCoreExt() throws Exception {
		File workspaceDir = buildWorkspace(
			temporaryFolder, "gradle", "testWorkspace",
			getDefaultLiferayVersion(), mavenExecutor);

		setProductVersion(workspaceDir, getDefaultLiferayVersion());

		File modulesDir = new File(workspaceDir, "modules");

		File projectDir = buildTemplateWithGradle(
			modulesDir, "war-core-ext", "test-war-core-ext");

		testNotContains(
			projectDir, "build.gradle", true, "^repositories \\{.*");
		testNotContains(
			projectDir, "build.gradle", "buildscript",
			"com.liferay.ext.plugin");
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

}