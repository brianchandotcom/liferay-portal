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

package com.liferay.wiki.engine.jspwiki;

import com.ecyrd.jspwiki.WikiContext;
import com.ecyrd.jspwiki.WikiException;
import com.ecyrd.jspwiki.WikiPage;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.engine.WikiEngine;
import com.liferay.wiki.engine.input.editor.common.BaseInputEditorWikiEngine;
import com.liferay.wiki.exception.PageContentException;
import com.liferay.wiki.service.WikiPageLocalService;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletURL;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(
	property = {"service.ranking:Integer=-1000"},
	service = WikiEngine.class
)
public class JSPWikiEngine extends BaseInputEditorWikiEngine {

	public static String decodeJSPWikiName(String jspWikiName) {
		return StringUtil.replace(
			jspWikiName, _JSP_WIKI_NAME_2, _JSP_WIKI_NAME_1);
	}

	@Override
	public String convert(
			com.liferay.wiki.model.WikiPage page, PortletURL viewPageURL,
			PortletURL editPageURL, String attachmentURLPrefix)
		throws PageContentException {

		try {
			return convert(page);
		}
		catch (WikiException we) {
			throw new PageContentException(we);
		}
	}

	@Override
	public String getEditorName() {
		return _wikiGroupServiceConfiguration.getCreoleEditor();
	}

	@Override
	public String getFormat() {
		return "creole";
	}

	@Override
	public String getHelpURL() {
		return "http://www.wikicreole.org/wiki/Creole1.0";
	}

	@Override
	public Map<String, Boolean> getOutgoingLinks(
			com.liferay.wiki.model.WikiPage page)
		throws PageContentException {

		if (Validator.isNull(page.getContent())) {
			return Collections.emptyMap();
		}

		try {
			LiferayJSPWikiEngine engine = getEngine(page.getNodeId());

			WikiPage jspWikiPage = LiferayPageProvider.toJSPWikiPage(
				page, engine);

			Collection<String> titles = engine.scanWikiLinks(
				jspWikiPage, _encodeJSPWikiContent(page.getContent()));

			Map<String, Boolean> links = new HashMap<>();

			for (String title : titles) {
				if (title.startsWith("[[")) {
					title = title.substring(2);
				}
				else if (title.startsWith("[")) {
					title = title.substring(1);
				}

				if (title.endsWith("]]")) {
					title = title.substring(0, title.length() - 2);
				}
				else if (title.endsWith("]")) {
					title = title.substring(0, title.length() - 1);
				}

				Boolean existsObj = links.get(title);

				if (existsObj == null) {
					if (_wikiPageLocalService.getPagesCount(
							page.getNodeId(), title, true) > 0) {

						existsObj = Boolean.TRUE;
					}
					else {
						existsObj = Boolean.FALSE;
					}

					links.put(title, existsObj);
				}
			}

			return links;
		}
		catch (SystemException se) {
			throw new PageContentException(se);
		}
		catch (WikiException we) {
			throw new PageContentException(we);
		}
	}

	@Activate
	protected void activate() throws IOException {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"com/liferay/wiki/engine/jspwiki/dependencies" +
				"/jspwiki.properties");

		try {
			_properties.load(inputStream);
		}
		finally {
			inputStream.close();
		}
	}

	protected String convert(com.liferay.wiki.model.WikiPage page)
		throws WikiException {

		String content = _encodeJSPWikiContent(page.getContent());

		if (Validator.isNull(content)) {
			return StringPool.BLANK;
		}

		com.ecyrd.jspwiki.WikiEngine engine = getEngine(page.getNodeId());

		WikiPage jspWikiPage = LiferayPageProvider.toJSPWikiPage(page, engine);

		WikiContext wikiContext = new WikiContext(engine, jspWikiPage);

		return _decodeJSPWikiContent(engine.textToHTML(wikiContext, content));
	}

	protected LiferayJSPWikiEngine getEngine(long nodeId) throws WikiException {
		LiferayJSPWikiEngine engine = _engines.get(nodeId);

		if (engine != null) {
			return engine;
		}

		synchronized (_engines) {
			engine = _engines.get(nodeId);

			if (engine != null) {
				return engine;
			}

			Properties nodeProperties = new Properties(_properties);

			nodeProperties.setProperty("nodeId", String.valueOf(nodeId));

			String appName = nodeProperties.getProperty(
				"jspwiki.applicationName");

			nodeProperties.setProperty(
				"jspwiki.applicationName", appName + " for node " + nodeId);

			engine = new LiferayJSPWikiEngine(nodeProperties);

			_engines.put(nodeId, engine);

			return engine;
		}
	}

	@Override
	protected ServletContext getHelpPageServletContext() {
		return _servletContext;
	}

	protected synchronized void setProperties(String configuration) {
		_properties = new Properties();

		InputStream is = new UnsyncByteArrayInputStream(
			configuration.getBytes());

		try {
			_properties.load(is);
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.wiki.engine.jspwiki)",
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

	private static String _decodeJSPWikiContent(String jspWikiContent) {
		return StringUtil.replace(
			jspWikiContent, _JSP_WIKI_NAME_2, _JSP_WIKI_NAME_1);
	}

	private static String _encodeJSPWikiContent(String content) {
		StringBundler encodedContent = new StringBundler();

		Matcher commentMatcher = _wikiCommentPattern.matcher(content);

		int start = 0;
		int end = 0;

		while (commentMatcher.find()) {
			end = commentMatcher.start();

			String oldContent = content.substring(start, end);

			Matcher wikiLinkMatcher = _wikiLinkPattern.matcher(oldContent);

			encodedContent.append(_encodeLink(oldContent, wikiLinkMatcher));
			encodedContent.append(
				content.substring(
					commentMatcher.start(), commentMatcher.end()));

			start = commentMatcher.end();
		}

		if (start < content.length()) {
			content = content.substring(start);

			Matcher wikiLinkMatcher = _wikiLinkPattern.matcher(content);

			encodedContent.append(_encodeLink(content, wikiLinkMatcher));
		}

		return encodedContent.toString();
	}

	private static String _encodeJSPWikiName(String name) {
		if (name == null) {
			return StringPool.BLANK;
		}

		return StringUtil.replace(name, _JSP_WIKI_NAME_1, _JSP_WIKI_NAME_2);
	}

	private static String _encodeLink(String content, Matcher wikiLinkMatcher) {
		while (wikiLinkMatcher.find()) {
			String link = wikiLinkMatcher.group();
			String linkValues = wikiLinkMatcher.group(1);

			String name = linkValues;
			String url = linkValues;

			int pos = linkValues.indexOf(CharPool.PIPE);

			if (pos != -1) {
				name = linkValues.substring(pos + 1);
				url = linkValues.substring(0, pos);
			}

			String newLink =
				"[[" + _encodeJSPWikiName(url) + "|" +
					_encodeJSPWikiName(name) + "]]";

			content = StringUtil.replace(content, link, newLink);
		}

		return content;
	}

	private static final String[] _JSP_WIKI_NAME_1 = {
		StringPool.APOSTROPHE, StringPool.AT, StringPool.CARET,
		StringPool.EXCLAMATION, StringPool.INVERTED_EXCLAMATION,
		StringPool.INVERTED_QUESTION, StringPool.GRAVE_ACCENT,
		StringPool.QUESTION, StringPool.SLASH, StringPool.STAR
	};

	private static final String[] _JSP_WIKI_NAME_2 = {
		"__APO__", "__AT__", "__CAR__", "__EXM__", "__INE__", "__INQ__",
		"__GRA__", "__QUE__", "__SLA__", "__STA__"
	};

	private static final Log _log = LogFactoryUtil.getLog(JSPWikiEngine.class);

	private static final Pattern _wikiCommentPattern = Pattern.compile(
		"[\\{]{3,3}(.*?)[\\}]{3,3}", Pattern.DOTALL);
	private static final Pattern _wikiLinkPattern = Pattern.compile(
		"[\\[]{1,2}(.+?)[\\]]{1,2}", Pattern.DOTALL);

	private final Map<Long, LiferayJSPWikiEngine> _engines =
		new ConcurrentHashMap<>();
	private Properties _properties = new Properties();
	private volatile ServletContext _servletContext;
	private volatile WikiGroupServiceConfiguration
		_wikiGroupServiceConfiguration;
	private volatile WikiPageLocalService _wikiPageLocalService;

}