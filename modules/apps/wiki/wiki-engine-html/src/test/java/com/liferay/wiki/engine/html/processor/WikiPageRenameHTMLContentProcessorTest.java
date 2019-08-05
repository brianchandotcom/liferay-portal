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

import com.liferay.petra.string.StringBundler;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public class WikiPageRenameHTMLContentProcessorTest {

	@Test
	public void testProcessContentImage() {
		String content =
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL_NAME&fileName=image.jpeg\">";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			0, "ORIGINAL_NAME", "FINAL_NAME", content);

		Assert.assertEquals(
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=FINAL_NAME&fileName=image.jpeg\">",
			content);
	}

	@Test
	public void testProcessContentImageDoNotChangeOtherImages() {
		String content = StringBundler.concat(
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234",
			"&title=ORIGINAL_NAME1&fileName=image.jpeg\"/> <img ",
			"src=\"wiki/get_page_attachment?p_l_id=1234\"",
			"&title=ORIGINAL_NAME2&fileName=image.jpeg\"/>");

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			0, "ORIGINAL_NAME1", "FINAL_NAME1", content);

		Assert.assertEquals(
			StringBundler.concat(
				"This is a test <img src=\"wiki/get_page_attachment?",
				"p_l_id=1234&title=FINAL_NAME1&fileName=image.jpeg\"/> <img ",
				"src=\"wiki/get_page_attachment?p_l_id=1234\"",
				"&title=ORIGINAL_NAME2&fileName=image.jpeg\"/>"),
			content);
	}

	@Test
	public void testProcessContentImageWithComplexTitle() {
		String content =
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=Complex.%2C%28%29+original+%26+title&fileName=" +
					"image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			0, "Complex.,() original & title", "Complex.,() final & title",
			content);

		Assert.assertEquals(
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=Complex.%2C%28%29+final+%26+title&fileName=" +
					"image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentImageWithCurlyBracketsInTitle() {
		String content =
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=%7BORIGINAL_NAME%7D&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			0, "{ORIGINAL_NAME}", "{FINAL_NAME}", content);

		Assert.assertEquals(
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=%7BFINAL_NAME%7D&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentImageWithNumbersInTitle() {
		String content =
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL_NAME123456&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			0, "ORIGINAL_NAME123456", "FINAL_NAME123456", content);

		Assert.assertEquals(
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=FINAL_NAME123456&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentImageWithParenthesisInTitle() {
		String content =
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=%28ORIGINAL_NAME%29&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			0, "(ORIGINAL_NAME)", "(FINAL_NAME)", content);

		Assert.assertEquals(
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=%28FINAL_NAME%29&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentImageWithSpaceInTitle() {
		String content =
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL+NAME+PAGE&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			0, "ORIGINAL NAME PAGE", "FINAL NAME PAGE", content);

		Assert.assertEquals(
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=FINAL+NAME+PAGE&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentImageWithTitleAsFirstParameterDoNotChange() {
		String content =
			"This is a test <img src=\"wiki/get_page_attachment?" +
				"title=ORIGINAL_NAME&fileName=image.jpeg\">";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			0, "ORIGINAL_NAME", "FINAL_NAME", content);

		Assert.assertEquals(
			"This is a test <img src=\"wiki/get_page_attachment?" +
				"title=ORIGINAL_NAME&fileName=image.jpeg\">",
			content);
	}

	@Test
	public void testProcessContentImageWithTitleAsLastParameterDoNotChange() {
		String content =
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL_NAME\">";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			0, "ORIGINAL_NAME", "FINAL_NAME", content);

		Assert.assertEquals(
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL_NAME\">",
			content);
	}

	@Test
	public void testProcessContentLink() {
		String content =
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL_NAME&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			0, "ORIGINAL_NAME", "FINAL_NAME", content);

		Assert.assertEquals(
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=FINAL_NAME&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentLinkDoNotChangeOtherLinks() {
		String content = StringBundler.concat(
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234",
			"&title=ORIGINAL_NAME1&fileName=image.jpeg\"/> <a ",
			"href=\"wiki/get_page_attachment?p_l_id=1234\"",
			"&title=ORIGINAL_NAME2&fileName=image.jpeg\"/>");

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			0, "ORIGINAL_NAME1", "FINAL_NAME1", content);

		Assert.assertEquals(
			StringBundler.concat(
				"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234",
				"&title=FINAL_NAME1&fileName=image.jpeg\"/> <a ",
				"href=\"wiki/get_page_attachment?p_l_id=1234\"",
				"&title=ORIGINAL_NAME2&fileName=image.jpeg\"/>"),
			content);
	}

	@Test
	public void testProcessContentLinkWithComplexTitle() {
		String content =
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=Complex.%2C%28%29+original+%26+title&fileName=" +
					"image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			0, "Complex.,() original & title", "Complex.,() final & title",
			content);

		Assert.assertEquals(
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=Complex.%2C%28%29+final+%26+title&fileName=" +
					"image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentLinkWithCurlyBracketsInTitle() {
		String content =
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=%7BORIGINAL_NAME%7D&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			0, "{ORIGINAL_NAME}", "{FINAL_NAME}", content);

		Assert.assertEquals(
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=%7BFINAL_NAME%7D&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentLinkWithNumbersInTitle() {
		String content =
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL_NAME123456&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			0, "ORIGINAL_NAME123456", "FINAL_NAME123456", content);

		Assert.assertEquals(
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=FINAL_NAME123456&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentLinkWithParenthesisInTitle() {
		String content =
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=%28ORIGINAL_NAME%29&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			0, "(ORIGINAL_NAME)", "(FINAL_NAME)", content);

		Assert.assertEquals(
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=%28FINAL_NAME%29&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentLinkWithSpaceInTitle() {
		String content =
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL+NAME+PAGE&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			0, "ORIGINAL NAME PAGE", "FINAL NAME PAGE", content);

		Assert.assertEquals(
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=FINAL+NAME+PAGE&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentLinkWithTitleAsFirstParameterDoNotChange() {
		String content =
			"This is a test <a href=\"wiki/get_page_attachment?" +
				"title=ORIGINAL_NAME&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			0, "ORIGINAL_NAME", "FINAL_NAME", content);

		Assert.assertEquals(
			"This is a test <a href=\"wiki/get_page_attachment?" +
				"title=ORIGINAL_NAME&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentLinkWithTitleAsLastParameterDoNotChange() {
		String content =
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL_NAME\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			0, "ORIGINAL_NAME", "FINAL_NAME", content);

		Assert.assertEquals(
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL_NAME\"/>",
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