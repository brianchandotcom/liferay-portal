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

package com.liferay.wiki.engine.impl;

import com.liferay.portal.kernel.diff.DiffHtmlUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.servlet.PipingServletResponse;
import com.liferay.wiki.engine.WikiEngine;
import com.liferay.wiki.exception.PageContentException;
import com.liferay.wiki.exception.WikiFormatException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageDisplay;
import com.liferay.wiki.util.WikiCacheHelper;

import java.io.IOException;
import java.io.Writer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = WikiEngineRenderer.class)
public class WikiEngineRenderer {

	public String convert(
			WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
			String attachmentURLPrefix)
		throws PageContentException, WikiFormatException {

		LiferayPortletURL liferayViewPageURL = (LiferayPortletURL)viewPageURL;
		LiferayPortletURL liferayEditPageURL = (LiferayPortletURL)editPageURL;

		WikiEngine wikiEngine = fetchWikiEngine(page.getFormat());

		if (wikiEngine == null) {
			throw new WikiFormatException();
		}

		String content = wikiEngine.convert(
			page, viewPageURL, editPageURL, attachmentURLPrefix);

		String editPageURLString = StringPool.BLANK;

		if (editPageURL != null) {
			liferayEditPageURL.setParameter("title", "__REPLACEMENT__", false);

			editPageURLString = editPageURL.toString();

			editPageURLString = StringUtil.replace(
				editPageURLString, "__REPLACEMENT__", "$1");
		}

		Matcher matcher = _editPageURLPattern.matcher(content);

		content = _convertURLs(editPageURLString, matcher);

		String viewPageURLString = StringPool.BLANK;

		if (viewPageURL != null) {
			liferayViewPageURL.setParameter("title", "__REPLACEMENT__", false);

			viewPageURLString = viewPageURL.toString();

			viewPageURLString = StringUtil.replace(
				viewPageURLString, "__REPLACEMENT__", "$1");
		}

		matcher = _viewPageURLPattern.matcher(content);

		content = _convertURLs(viewPageURLString, matcher);

		content = _replaceAttachments(
			content, page.getTitle(), attachmentURLPrefix);

		return content;
	}

	public String diffHtml(
			WikiPage sourcePage, WikiPage targetPage, PortletURL viewPageURL,
			PortletURL editPageURL, String attachmentURLPrefix)
		throws Exception {

		String sourceContent = StringPool.BLANK;
		String targetContent = StringPool.BLANK;

		if (sourcePage != null) {
			sourceContent = convert(
				sourcePage, viewPageURL, editPageURL, attachmentURLPrefix);
		}

		if (targetPage != null) {
			targetContent = convert(
				targetPage, viewPageURL, editPageURL, attachmentURLPrefix);
		}

		return DiffHtmlUtil.diff(
			new UnsyncStringReader(sourceContent),
			new UnsyncStringReader(targetContent));
	}

	public WikiEngine fetchWikiEngine(String format) {
		return _wikiEngineTracker.getWikiEngine(format);
	}

	public List<WikiPage> filterOrphans(List<WikiPage> pages)
		throws PortalException {

		List<Map<String, Boolean>> pageTitles = new ArrayList<>();

		for (WikiPage page : pages) {
			pageTitles.add(_wikiCacheHelper.getOutgoingLinks(page, this));
		}

		Set<WikiPage> notOrphans = new HashSet<>();

		for (WikiPage page : pages) {
			for (Map<String, Boolean> pageTitle : pageTitles) {
				String pageTitleLowerCase = page.getTitle();

				pageTitleLowerCase = StringUtil.toLowerCase(pageTitleLowerCase);

				if (pageTitle.get(pageTitleLowerCase) != null) {
					notOrphans.add(page);

					break;
				}
			}
		}

		List<WikiPage> orphans = new ArrayList<>();

		for (WikiPage page : pages) {
			if (!notOrphans.contains(page)) {
				orphans.add(page);
			}
		}

		orphans = ListUtil.sort(orphans);

		return orphans;
	}

	public String getFormatLabel(String format, Locale locale) {
		WikiEngine wikiEngine = fetchWikiEngine(format);

		if (wikiEngine != null) {
			return wikiEngine.getFormatLabel(locale);
		}
		else {
			return StringPool.BLANK;
		}
	}

	public Collection<String> getFormats() {
		return _wikiEngineTracker.getFormats();
	}

	public String getFormattedContent(
			RenderRequest renderRequest, RenderResponse renderResponse,
			WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
			String title, boolean preview)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		double version = ParamUtil.getDouble(renderRequest, "version");

		PortletURL curViewPageURL = PortletURLUtil.clone(
			viewPageURL, renderResponse);
		PortletURL curEditPageURL = PortletURLUtil.clone(
			editPageURL, renderResponse);

		StringBundler sb = new StringBundler(8);

		sb.append(themeDisplay.getPathMain());
		sb.append("/wiki/get_page_attachment?p_l_id=");
		sb.append(themeDisplay.getPlid());
		sb.append("&nodeId=");
		sb.append(page.getNodeId());
		sb.append("&title=");
		sb.append(HttpUtil.encodeURL(page.getTitle()));
		sb.append("&fileName=");

		String attachmentURLPrefix = sb.toString();

		if (!preview && (version == 0)) {
			WikiPageDisplay pageDisplay = _wikiCacheHelper.getDisplay(
				page.getNodeId(), title, curViewPageURL, curEditPageURL,
				attachmentURLPrefix);

			if (pageDisplay != null) {
				return pageDisplay.getFormattedContent();
			}
		}

		return convert(
			page, curViewPageURL, curEditPageURL, attachmentURLPrefix);
	}

	public void renderEditPageHTML(
			String format, PageContext pageContext, WikiNode node,
			WikiPage page)
		throws IOException, ServletException {

		WikiEngine wikiEngine = _wikiEngineTracker.getWikiEngine(format);

		HttpServletResponse response =
			(HttpServletResponse)pageContext.getResponse();

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			response, unsyncStringWriter);

		wikiEngine.renderEditPage(
			pageContext.getRequest(), pipingServletResponse, node, page);

		Writer writer = pageContext.getOut();

		StringBundler sb = unsyncStringWriter.getStringBundler();

		writer.write(sb.toString());
	}

	@Reference(unbind = "-")
	protected void setWikiCacheHelper(WikiCacheHelper wikiCacheHelper) {
		_wikiCacheHelper = wikiCacheHelper;
	}

	@Reference(unbind = "-")
	protected void setWikiEngineTracker(WikiEngineTracker wikiEngineTracker) {
		_wikiEngineTracker = wikiEngineTracker;
	}

	private String _convertURLs(String url, Matcher matcher) {
		StringBuffer sb = new StringBuffer();

		while (matcher.find()) {
			String replacement = null;

			if (matcher.groupCount() >= 1) {
				String encodedTitle = HttpUtil.encodeURL(
					HtmlUtil.unescape(matcher.group(1)));

				replacement = url.replace("$1", encodedTitle);
			}
			else {
				replacement = url;
			}

			matcher.appendReplacement(sb, replacement);
		}

		return matcher.appendTail(sb).toString();
	}

	private String _replaceAttachments(
		String content, String title, String attachmentURLPrefix) {

		content = StringUtil.replace(content, "[$WIKI_PAGE_NAME$]", title);

		content = StringUtil.replace(
			content, "[$ATTACHMENT_URL_PREFIX$]", attachmentURLPrefix);

		return content;
	}

	private static final Pattern _editPageURLPattern = Pattern.compile(
		"\\[\\$BEGIN_PAGE_TITLE_EDIT\\$\\](.*?)" +
			"\\[\\$END_PAGE_TITLE_EDIT\\$\\]");
	private static final Pattern _viewPageURLPattern = Pattern.compile(
		"\\[\\$BEGIN_PAGE_TITLE\\$\\](.*?)\\[\\$END_PAGE_TITLE\\$\\]");

	private WikiCacheHelper _wikiCacheHelper;
	private WikiEngineTracker _wikiEngineTracker;

}