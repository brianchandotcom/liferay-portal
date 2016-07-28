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

package com.liferay.knowledge.base.markdown.converter;

import com.liferay.knowledge.base.markdown.converter.factory.MarkdownConverterFactoryUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andy Wu
 */
public class MarkdownConverterTest {

	@Test
	public void testLiferayToHtmlSerializer() throws Exception {
		String markdownString =
			"### The liferay-ui:logo-selector Tag " +
				"Requires Parameter Changes [](id=the-liferay-uilogo-selector" +
					"-tag-requires-parameter-changes)";

		String html =
			MarkdownConverterFactoryUtil.create().convert(markdownString);

		int pos = html.indexOf(
			"id=\"the-liferay-uilogo-selector-tag-" +
				"requires-parameter-changes\"");

		Assert.assertNotEquals(-1, pos);
	}

}