/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.css.builder.maven;

import com.liferay.css.builder.BaseCSSBuilderJniTestCase;
import com.liferay.maven.executor.MavenExecutor;

import java.nio.file.Path;

import org.junit.Assert;
import org.junit.ClassRule;

/**
 * @author Andrea Di Giorgi
 */
public class BuildCSSMojoTest extends BaseCSSBuilderJniTestCase {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	@Override
	protected String executeCSSBuilder(
			Path baseDirPath, String dirName, String[] excludes,
			boolean generateSourceMap, Path importDirPath, String outputDirName,
			int precision, String[] rtlExcludedPathRegexps,
			String sassCompilerClassName)
		throws Exception {

		BuildCSSMojoTestUtil.preparePomXml(
			baseDirPath, dirName, excludes, generateSourceMap, importDirPath,
			outputDirName, precision, rtlExcludedPathRegexps,
			sassCompilerClassName);

		MavenExecutor.Result result = mavenExecutor.execute(
			baseDirPath.toFile(), "css-builder:build");

		Assert.assertEquals(result.output, 0, result.exitCode);

		return result.output;
	}

}