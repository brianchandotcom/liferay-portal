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

package com.liferay.wiki.engine.mediawiki;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.engine.WikiEngine;
import com.liferay.wiki.engine.input.editor.common.BaseInputEditorWikiEngine;
import com.liferay.wiki.engine.mediawiki.matchers.DirectTagMatcher;
import com.liferay.wiki.engine.mediawiki.matchers.DirectURLMatcher;
import com.liferay.wiki.engine.mediawiki.matchers.EditURLMatcher;
import com.liferay.wiki.engine.mediawiki.matchers.ImageTagMatcher;
import com.liferay.wiki.engine.mediawiki.matchers.ImageURLMatcher;
import com.liferay.wiki.engine.mediawiki.matchers.ViewURLMatcher;
import com.liferay.wiki.exception.PageContentException;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalService;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.ServletContext;

import org.jamwiki.Environment;
import org.jamwiki.model.WikiUser;
import org.jamwiki.parser.ParserException;
import org.jamwiki.parser.ParserInput;
import org.jamwiki.parser.ParserOutput;
import org.jamwiki.parser.ParserUtil;
import org.jamwiki.parser.TableOfContents;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jonathan Potter
 */
@Component(service = WikiEngine.class)
public class MediaWikiEngine extends BaseInputEditorWikiEngine {

	@Override
	public String convert(
			WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
			String attachmentURLPrefix)
		throws PageContentException {

		return parsePage(
			page, new ParserOutput(), viewPageURL, editPageURL,
			attachmentURLPrefix);
	}

	@Override
	public String getEditorName() {
		return _wikiGroupServiceConfiguration.getMediaWikiEditor();
	}

	@Override
	public String getFormat() {
		return "mediawiki";
	}

	@Override
	public String getHelpURL() {
		return "http://www.mediawiki.org/wiki/Help:Formatting";
	}

	@Override
	public Map<String, Boolean> getOutgoingLinks(WikiPage page)
		throws PageContentException {

		ParserOutput parserOutput = getParserOutput(page);

		Map<String, Boolean> outgoingLinks = new HashMap<>();

		for (String title : parserOutput.getLinks()) {
			Boolean existsObj = outgoingLinks.get(title);

			if (existsObj == null) {
				int pagesCount = 0;

				try {
					pagesCount = _wikiPageLocalService.getPagesCount(
						page.getNodeId(), title, true);
				}
				catch (SystemException se) {
					throw new PageContentException(se);
				}

				if (pagesCount > 0) {
					title = StringUtil.toLowerCase(title);

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

	@Activate
	protected void activate() {
		Environment.setValue(
			Environment.PROP_BASE_FILE_DIR,
			SystemProperties.get(SystemProperties.TMP_DIR));
	}

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	@Override
	protected ServletContext getHelpPageServletContext() {
		return _servletContext;
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

		ParserOutput parserOutput = null;

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(getClassLoader());

		try {
			ParserInput parserInput = getParserInput(
				page.getNodeId(), page.getTitle());

			parserOutput = ParserUtil.parseMetadata(
				parserInput, page.getContent());
		}
		catch (ParserException pe) {
			throw new PageContentException(pe);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}

		return parserOutput;
	}

	protected String parsePage(
			WikiPage page, ParserOutput parserOutput, PortletURL viewPageURL,
			PortletURL editPageURL, String attachmentURLPrefix)
		throws PageContentException {

		String content = StringPool.BLANK;

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(getClassLoader());

		try {
			ParserInput parserInput = getParserInput(
				page.getNodeId(), page.getTitle());

			content = page.getContent();

			DirectTagMatcher directTagMatcher = new DirectTagMatcher(page);

			content = directTagMatcher.replaceMatches(content);

			ImageTagMatcher imageTagMatcher = new ImageTagMatcher();

			content = ParserUtil.parse(
				parserInput, parserOutput,
				imageTagMatcher.replaceMatches(content));
		}
		catch (ParserException pe) {
			throw new PageContentException(pe);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}

		// Post parse

		if (attachmentURLPrefix != null) {
			DirectURLMatcher attachmentURLMatcher = new DirectURLMatcher(
				page, attachmentURLPrefix);

			content = attachmentURLMatcher.replaceMatches(content);

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

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.wiki.engine.mediawiki)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Reference
	protected void setWikiGroupServiceConfiguration(
		WikiGroupServiceConfiguration wikiGroupServiceConfiguration) {

		_wikiGroupServiceConfiguration = wikiGroupServiceConfiguration;
	}

	@Reference(unbind = "-")
	protected void setWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {

		_wikiPageLocalService = wikiPageLocalService;
	}

	protected void unsetWikiGroupServiceConfiguration(
		WikiGroupServiceConfiguration wikiGroupServiceConfiguration) {

		_wikiGroupServiceConfiguration = null;
	}

	private volatile ServletContext _servletContext;
	private volatile WikiGroupServiceConfiguration _wikiGroupServiceConfiguration;
	private volatile WikiPageLocalService _wikiPageLocalService;

}