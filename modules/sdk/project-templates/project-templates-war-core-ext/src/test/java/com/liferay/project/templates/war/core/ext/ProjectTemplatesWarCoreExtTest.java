/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		String liferayWorkspaceProduct = getLiferayWorkspaceProduct(
			getDefaultLiferayVersion());

		if (liferayWorkspaceProduct != null) {
			writeGradlePropertiesInWorkspace(
				workspaceDir,
				"liferay.workspace.product=" + liferayWorkspaceProduct);
		}

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