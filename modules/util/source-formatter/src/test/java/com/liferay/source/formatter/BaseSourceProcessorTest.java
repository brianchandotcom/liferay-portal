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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 * @author Hugo Huijser
 */
public class BaseSourceProcessorTest {

	@Before
	public void setUp() {
		setUpBaseSourceProcessor();
	}

	@Test
	public void testGetModuleLangPath() throws Exception {
		testGetModuleLangPath(
			"modules/apps/forms-and-workflow/dynamic-data-lists" +
				"/dynamic-data-lists-form-web",
			"modules/apps/forms-and-workflow/dynamic-data-lists" +
				"/dynamic-data-lists-form-web/../../dynamic-data-mapping" +
					"/dynamic-data-mapping-lang/src/main/resources/content",
			"modules/apps/forms-and-workflow/dynamic-data-lists" +
				"/dynamic-data-lists-form-web/src/main/resources/content");
		testGetModuleLangPath(
			"modules/apps/forms-and-workflow/dynamic-data-mapping" +
				"/dynamic-data-mapping-web",
			"modules/apps/forms-and-workflow/dynamic-data-mapping" +
				"/dynamic-data-mapping-lang/src/main/resources/content");
		testGetModuleLangPath(
			"modules/apps/forms-and-workflow/portal-workflow" +
				"/portal-workflow-definition-web",
			"modules/apps/forms-and-workflow/portal-workflow" +
				"/portal-workflow-lang/src/main/resources/content");
		testGetModuleLangPath(
			"modules/apps/web-experience/staging/staging-bar-web",
			"modules/apps/web-experience/staging/staging-lang/src/main" +
				"/resources/content");
	}

	protected void setUpBaseSourceProcessor() {
		_baseSourceProcessor = new BaseSourceProcessor() {

			@Override
			public String[] getIncludes() {
				return null;
			}

			@Override
			protected String doFormat(
					File file, String fileName, String absolutePath,
					String content)
				throws Exception {

				return null;
			}

			@Override
			protected List<String> doGetFileNames() throws Exception {
				return null;
			}

		};

		_baseSourceProcessor.setSourceFormatterArgs(new SourceFormatterArgs());
	}

	protected void testGetModuleLangPath(
			String moduleDirName, String... expectedModuleLangDirNames)
		throws Exception {

		moduleDirName = _TEST_RESOURCES_DIR_NAME + moduleDirName;

		for (int i = 0; i < expectedModuleLangDirNames.length; i++) {
			expectedModuleLangDirNames[i] =
				_TEST_RESOURCES_DIR_NAME + expectedModuleLangDirNames[i];
		}

		String buildGradleContent = StringPool.BLANK;

		File file = _baseSourceProcessor.getFile(
			moduleDirName + "/build.testgradle",
			_baseSourceProcessor.PORTAL_MAX_DIR_LEVEL);

		if (file != null) {
			buildGradleContent = FileUtil.read(file);
		}

		Assert.assertEquals(
			Arrays.asList(expectedModuleLangDirNames),
			_baseSourceProcessor.getModuleLangDirNames(
				moduleDirName, buildGradleContent));
	}

	private static final String _TEST_RESOURCES_DIR_NAME =
		"./modules/util/source-formatter/src/test/resources/";

	private BaseSourceProcessor _baseSourceProcessor;

}