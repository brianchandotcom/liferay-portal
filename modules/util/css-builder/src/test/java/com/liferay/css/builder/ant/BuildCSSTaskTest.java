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

package com.liferay.css.builder.ant;

import com.liferay.css.builder.BaseCSSBuilderTestCase;
import com.liferay.css.builder.StringPrintStream;
import com.liferay.css.builder.util.StringTestUtil;

import java.io.File;

import java.net.URL;

import java.nio.file.Path;

import org.apache.tools.ant.BuildFileRule;
import org.apache.tools.ant.Project;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;

/**
 * @author Andrea Di Giorgi
 */
public class BuildCSSTaskTest extends BaseCSSBuilderTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		URL url = BuildCSSTaskTest.class.getResource("dependencies/build.xml");

		File buildXmlFile = new File(url.toURI());

		Assert.assertTrue(buildXmlFile.isFile());

		buildFileRule.configureProject(buildXmlFile.getAbsolutePath());
	}

	@Rule
	public final BuildFileRule buildFileRule = new BuildFileRule();

	@Override
	protected String executeCSSBuilder(
			Path baseDirPath, String dirName, String[] excludes,
			boolean generateSourceMap, Path importDirPath, String outputDirName,
			int precision, String[] rtlExcludedPathRegexps,
			String sassCompilerClassName)
		throws Exception {

		Project project = buildFileRule.getProject();

		project.setProperty(
			"build.css.base.dir", String.valueOf(baseDirPath.toAbsolutePath()));
		project.setProperty("build.css.dir.names", dirName);
		project.setProperty(
			"build.css.excludes", StringTestUtil.merge(excludes));
		project.setProperty(
			"build.css.generate.source.maps",
			String.valueOf(generateSourceMap));
		project.setProperty(
			"build.css.import.dir",
			String.valueOf(importDirPath.toAbsolutePath()));
		project.setProperty(
			"build.css.output.dir.name", String.valueOf(outputDirName));
		project.setProperty("build.css.precision", String.valueOf(precision));
		project.setProperty(
			"build.css.rtl.excluded.path.regexps",
			StringTestUtil.merge(rtlExcludedPathRegexps));
		project.setProperty(
			"build.css.sass.compiler.class.name", sassCompilerClassName);

		StringPrintStream output = new StringPrintStream();

		System.setOut(output);

		project.executeTarget("build-css");

		return output.toString();
	}

}