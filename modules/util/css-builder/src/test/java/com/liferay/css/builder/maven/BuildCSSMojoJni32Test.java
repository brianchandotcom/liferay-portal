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

package com.liferay.css.builder.maven;

import com.liferay.css.builder.BaseCSSBulderJni32TestCase;
import com.liferay.maven.executor.MavenExecutor;

import java.nio.file.Path;

import org.junit.Assert;
import org.junit.ClassRule;

/**
 * @author Andrea Di Giorgi
 */
public class BuildCSSMojoJni32Test extends BaseCSSBulderJni32TestCase {

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