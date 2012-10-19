/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.engines.mediawiki;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.wiki.PageContentException;
import com.liferay.portlet.wiki.engines.WikiEngine;
import com.liferay.portlet.wiki.engines.mediawiki.matchers.DirectURLMatcher;
import com.liferay.portlet.wiki.engines.mediawiki.matchers.EditURLMatcher;
import com.liferay.portlet.wiki.engines.mediawiki.matchers.ImageTagMatcher;
import com.liferay.portlet.wiki.engines.mediawiki.matchers.ImageURLMatcher;
import com.liferay.portlet.wiki.engines.mediawiki.matchers.ViewURLMatcher;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletURL;

import org.jamwiki.model.WikiUser;
import org.jamwiki.parser.ParserException;
import org.jamwiki.parser.ParserInput;
import org.jamwiki.parser.ParserOutput;
import org.jamwiki.parser.ParserUtil;
import org.jamwiki.parser.TableOfContents;

/**
 * @author Jonathan Potter
 */
public class MediaWikiEngine implements WikiEngine {

	public String convert(
			WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
			String attachmentURLPrefix)
		throws PageContentException {

		return parsePage(
			page, new ParserOutput(), viewPageURL, editPageURL,
			attachmentURLPrefix);
	}

	public Map<String, Boolean> getOutgoingLinks(WikiPage page)
		throws PageContentException {

		ParserOutput parserOutput = getParserOutput(page);

		Map<String, Boolean> outgoingLinks = new HashMap<String, Boolean>();

		for (String title : parserOutput.getLinks()) {
			Boolean existsObj = outgoingLinks.get(title);

			if (existsObj == null) {
				int pagesCount = 0;

				try {
					pagesCount = WikiPageLocalServiceUtil.getPagesCount(
						page.getNodeId(), title, true);
				}
				catch (SystemException se) {
					throw new PageContentException(se);
				}

				if (pagesCount > 0) {
					existsObj = Boolean.TRUE;
				}
				else {
					existsObj = Boolean.FALSE;

					// JAMWiki turns images into links. The postProcess method
					// turns them back to images, but the getOutgoingLinks does
					// not call postProcess, so we must manual process this
					// case.

					if (StringUtil.startsWith(title, "image:")) {
						continue;
					}
				}

				outgoingLinks.put(title, existsObj);
			}
		}

		return outgoingLinks;
	}

	public void setInterWikiConfiguration(String interWikiConfiguration) {
	}

	public void setMainConfiguration(String mainConfiguration) {
	}

	public boolean validate(long nodeId, String content) {
		return true;
	}

	protected ParserInput getParserInput(long nodeId, String topicName) {
		ParserInput parserInput = new ParserInput(
			"Special:Node:" + nodeId, topicName);

		// Dummy values

		parserInput.setContext("/wiki");
		parserInput.setLocale(LocaleUtil.getDefault());
		parserInput.setUserDisplay("0.0.0.0");
		parserInput.setWikiUser(new WikiUser("DummyUser"));

		// Useful values

		parserInput.setAllowSectionEdit(false);

		// Table of contents

		TableOfContents tableOfContents = new TableOfContents();

		tableOfContents.setForceTOC(true);

		parserInput.setTableOfContents(tableOfContents);

		return parserInput;
	}

	protected ParserOutput getParserOutput(WikiPage page)
		throws PageContentException {

		ParserInput parserInput = getParserInput(
			page.getNodeId(), page.getTitle());

		ParserOutput parserOutput = null;

		try {
			parserOutput = ParserUtil.parseMetadata(
				parserInput, page.getContent());
		}
		catch (ParserException pe) {
			throw new PageContentException(pe);
		}

		return parserOutput;
	}

	protected String parsePage(
			WikiPage page, ParserOutput parserOutput, PortletURL viewPageURL,
			PortletURL editPageURL, String attachmentURLPrefix)
		throws PageContentException {

		ParserInput parserInput = getParserInput(
			page.getNodeId(), page.getTitle());

		String content = StringPool.BLANK;

		try {
			content = page.getContent();

			ImageTagMatcher imageTagMatcher = new ImageTagMatcher();

			content = ParserUtil.parse(
				parserInput, parserOutput,
				imageTagMatcher.replaceMatches(content));
		}
		catch (ParserException pe) {
			throw new PageContentException(pe);
		}

		// Post parse

		if (attachmentURLPrefix != null) {
			DirectURLMatcher attachmentURLMatcher =
				new DirectURLMatcher(page, attachmentURLPrefix);

			String result = attachmentURLMatcher.replaceMatches(content);

			if (result != null) {
				content = result;
			}

			ImageURLMatcher imageURLMatcher = new ImageURLMatcher(
				attachmentURLPrefix);

			content = imageURLMatcher.replaceMatches(content);
		}

		if (editPageURL != null) {
			EditURLMatcher editURLMatcher = new EditURLMatcher(editPageURL);

			content = editURLMatcher.replaceMatches(content);
		}

		if (viewPageURL != null) {
			ViewURLMatcher viewURLMatcher = new ViewURLMatcher(viewPageURL);

			content = viewURLMatcher.replaceMatches(content);
		}

		return content;
	}

}