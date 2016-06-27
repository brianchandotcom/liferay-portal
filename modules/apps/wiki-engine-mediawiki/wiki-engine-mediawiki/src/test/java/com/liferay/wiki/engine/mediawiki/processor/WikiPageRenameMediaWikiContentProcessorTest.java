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

package com.liferay.wiki.engine.mediawiki.processor;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public class WikiPageRenameMediaWikiContentProcessorTest {

	@Test
	public void testImage() {
		String content = "This is a test [[Image:ORIGINAL_NAME/image.jpg]]";

		content = _wikiPageRenameMediaWikiContentProcessor.processContent(
			content, "ORIGINAL_NAME", "FINAL_NAME", 0);

		Assert.assertEquals(
			"This is a test [[Image:FINAL_NAME/image.jpg]]", content);
	}

	private final WikiPageRenameMediaWikiContentProcessor
		_wikiPageRenameMediaWikiContentProcessor =
			new WikiPageRenameMediaWikiContentProcessorStub();

	private class WikiPageRenameMediaWikiContentProcessorStub
		extends WikiPageRenameMediaWikiContentProcessor {

		public WikiPageRenameMediaWikiContentProcessorStub() {
			activate();
		}

	}

}