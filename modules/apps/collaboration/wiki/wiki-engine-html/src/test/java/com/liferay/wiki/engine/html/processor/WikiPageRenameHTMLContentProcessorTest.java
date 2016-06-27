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

package com.liferay.wiki.engine.html.processor;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public class WikiPageRenameHTMLContentProcessorTest {

	@Test
	public void testImage() {
		String content =
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL_NAME&fileName=image.jpeg\">";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			content, "ORIGINAL_NAME", "FINAL_NAME", 0);

		Assert.assertEquals(
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=FINAL_NAME&fileName=image.jpeg\">",
			content);
	}

	@Test
	public void testLink() {
		String content =
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL_NAME&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			content, "ORIGINAL_NAME", "FINAL_NAME", 0);

		Assert.assertEquals(
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=FINAL_NAME&fileName=image.jpeg\"/>",
			content);
	}

	private final WikiPageRenameHTMLContentProcessor
		_wikiPageRenameHTMLContentProcessor =
			new WikiPageRenameHTMLContentProcessorStub();

	private class WikiPageRenameHTMLContentProcessorStub
		extends WikiPageRenameHTMLContentProcessor {

		public WikiPageRenameHTMLContentProcessorStub() {
			activate();
		}

	}

}