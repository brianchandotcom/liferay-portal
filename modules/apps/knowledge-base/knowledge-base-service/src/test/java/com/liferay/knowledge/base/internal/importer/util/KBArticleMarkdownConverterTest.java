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

package com.liferay.knowledge.base.internal.importer.util;

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.portal.kernel.model.ModelHints;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.HtmlImpl;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Alejandro Tardín
 */
@RunWith(MockitoJUnitRunner.class)
public class KBArticleMarkdownConverterTest {

	@Before
	public void setUp() {
		ModelHintsUtil modelHintsUtil = new ModelHintsUtil();
		FileUtil fileUtil = new FileUtil();
		HtmlUtil htmlUtil = new HtmlUtil();

		modelHintsUtil.setModelHints(_modelHints);
		fileUtil.setFile(new FileImpl());
		htmlUtil.setHtml(new HtmlImpl());

		Mockito.when(
			_modelHints.getMaxLength(KBArticle.class.getName(), "urlTitle")
		).thenReturn(256);
	}

	@Test
	public void testGetSourceURLReplacesBackslashesWithForwardSlashes()
		throws Exception {

		String markdown = "Title [](id=1234)\n=============";
		String fileEntryName = "some\\windows\\file";
		HashMap<String, String> metadata = new HashMap<String, String>() { {
			put("base.source.url", "http://baseUrl");
		}};

		KBArticleMarkdownConverter converter = new KBArticleMarkdownConverter(
			markdown, fileEntryName, metadata);

		Assert.assertEquals(
			"http://baseUrl/some/windows/file", converter.getSourceURL());
	}

	@Mock
	private static ModelHints _modelHints;

}