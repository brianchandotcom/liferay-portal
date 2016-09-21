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
import java.util.Map;

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
		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());

		ModelHintsUtil modelHintsUtil = new ModelHintsUtil();

		modelHintsUtil.setModelHints(_modelHints);

		Mockito.when(
			_modelHints.getMaxLength(KBArticle.class.getName(), "urlTitle")
		).thenReturn(
			256
		);
	}

	@Test
	public void testGetSourceURLAddsTheMissingSlashInTheBaseURL()
		throws Exception {

		String markdown = "Title [](id=1234)\n=============";

		String fileEntryName = "some/unix/file";

		Map<String, String> metadata = new HashMap<>();

		metadata.put("base.source.url", "http://baseURL");

		KBArticleMarkdownConverter converter = new KBArticleMarkdownConverter(
			markdown, fileEntryName, metadata);

		Assert.assertEquals(
			"http://baseURL/some/unix/file", converter.getSourceURL());
	}

	@Test
	public void testGetSourceURLReplacesBackSlashesWithForwardSlashes()
		throws Exception {

		String markdown = "Title [](id=1234)\n=============";

		String fileEntryName = "some\\windows\\file";

		Map<String, String> metadata = new HashMap<>();

		metadata.put("base.source.url", "http://baseURL");

		KBArticleMarkdownConverter converter = new KBArticleMarkdownConverter(
			markdown, fileEntryName, metadata);

		Assert.assertEquals(
			"http://baseURL/some/windows/file", converter.getSourceURL());
	}

	@Test
	public void testGetSourceURLReturnsNullIfThereIsNoBaseURL()
		throws Exception {

		String markdown = "Title [](id=1234)\n=============";

		String fileEntryName = "some\\windows\\file";

		Map<String, String> metadata = new HashMap<>();

		KBArticleMarkdownConverter converter = new KBArticleMarkdownConverter(
			markdown, fileEntryName, metadata);

		Assert.assertNull(converter.getSourceURL());
	}

	@Test
	public void testGetSourceURLUsesTheSlashInTheBaseURL() throws Exception {
		String markdown = "Title [](id=1234)\n=============";

		String fileEntryName = "some/unix/file";

		Map<String, String> metadata = new HashMap<>();

		metadata.put("base.source.url", "http://baseURL/");

		KBArticleMarkdownConverter converter = new KBArticleMarkdownConverter(
			markdown, fileEntryName, metadata);

		Assert.assertEquals(
			"http://baseURL/some/unix/file", converter.getSourceURL());
	}

	@Mock
	private static ModelHints _modelHints;

}