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

import java.io.File;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class BaseSourceProcessorTest {

	@Before
	public void setUp() {
		setUpBaseSourceProcessor();
	}

	@Test
	public void testGetModuleLangPath() {
		Assert.assertEquals(
			"./modules/apps/business-productivity/dynamic-data-mapping/" +
				"dynamic-data-mapping-lang",
			_baseSourceProcessor.getModuleLangDir(
				"./modules/apps/business-productivity/dynamic-data-mapping/" +
					"dynamic-data-mapping-web"));
		Assert.assertEquals(
			"./modules/apps/web-experience-management/staging/staging-lang",
			_baseSourceProcessor.getModuleLangDir(
				"./modules/apps/web-experience-management/staging" +
					"/staging-bar-web"));
		Assert.assertEquals(
			"./modules/apps/workflow/workflow-lang",
			_baseSourceProcessor.getModuleLangDir(
				"./modules/apps/workflow/workflow-definition-web"));
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
	}

	private BaseSourceProcessor _baseSourceProcessor;

}