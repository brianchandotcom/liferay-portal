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

package com.liferay.wiki.util;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.diff.DiffHtmlUtil;
import com.liferay.portal.kernel.diff.DiffVersion;
import com.liferay.portal.kernel.diff.DiffVersionsInfo;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortletURLUtil;
import com.liferay.taglib.servlet.PipingServletResponse;
import com.liferay.wiki.engine.WikiEngine;
import com.liferay.wiki.engine.impl.WikiEngineTracker;
import com.liferay.wiki.exception.PageContentException;
import com.liferay.wiki.exception.WikiFormatException;
import com.liferay.wiki.importer.WikiImporter;
import com.liferay.wiki.importer.impl.WikiImporterTracker;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageDisplay;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.service.permission.WikiNodePermissionChecker;
import com.liferay.wiki.util.comparator.PageVersionComparator;

import java.io.IOException;
import java.io.Writer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class WikiUtil {

	public static String convert(
			WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
			String attachmentURLPrefix)
		throws PageContentException, WikiFormatException {

		return _instance._convert(
			page, viewPageURL, editPageURL, attachmentURLPrefix);
	}

	public static String diffHtml(
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

	public static List<WikiPage> filterOrphans(List<WikiPage> pages)
		throws PortalException {

		List<Map<String, Boolean>> pageTitles = new ArrayList<>();

		for (WikiPage page : pages) {
			pageTitles.add(WikiCacheUtil.getOutgoingLinks(page));
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

	public static String getAttachmentURLPrefix(
		String mainPath, long plid, long nodeId, String title) {

		StringBundler sb = new StringBundler(8);

		sb.append(mainPath);
		sb.append("/wiki/get_page_attachment?p_l_id=");
		sb.append(plid);
		sb.append("&nodeId=");
		sb.append(nodeId);
		sb.append("&title=");
		sb.append(HttpUtil.encodeURL(title));
		sb.append("&fileName=");

		return sb.toString();
	}

	public static DiffVersionsInfo getDiffVersionsInfo(
		long nodeId, String title, double sourceVersion, double targetVersion,
		HttpServletRequest request) {

		double previousVersion = 0;
		double nextVersion = 0;

		List<WikiPage> pages = WikiPageLocalServiceUtil.getPages(
			nodeId, title, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new PageVersionComparator(true));

		for (WikiPage page : pages) {
			if ((page.getVersion() < sourceVersion) &&
				(page.getVersion() > previousVersion)) {

				previousVersion = page.getVersion();
			}

			if ((page.getVersion() > targetVersion) &&
				((page.getVersion() < nextVersion) || (nextVersion == 0))) {

				nextVersion = page.getVersion();
			}
		}

		List<DiffVersion> diffVersions = new ArrayList<>();

		for (WikiPage page : pages) {
			String extraInfo = StringPool.BLANK;

			if (page.isMinorEdit()) {
				extraInfo = LanguageUtil.get(request, "minor-edit");
			}

			DiffVersion diffVersion = new DiffVersion(
				page.getUserId(), page.getVersion(), page.getModifiedDate(),
				page.getSummary(), extraInfo);

			diffVersions.add(diffVersion);
		}

		return new DiffVersionsInfo(diffVersions, nextVersion, previousVersion);
	}

	public static String getFormatLabel(String format, Locale locale)
		throws WikiFormatException {

		return _instance._getFormatLabel(format, locale);
	}

	public static Collection<String> getFormats() {
		WikiEngineTracker wikiEngineTracker = _getWikiEngineTracker();

		return wikiEngineTracker.getFormats();
	}

	public static String getFormattedContent(
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
			WikiPageDisplay pageDisplay = WikiCacheUtil.getDisplay(
				page.getNodeId(), title, curViewPageURL, curEditPageURL,
				attachmentURLPrefix);

			if (pageDisplay != null) {
				return pageDisplay.getFormattedContent();
			}
		}

		return convert(
			page, curViewPageURL, curEditPageURL, attachmentURLPrefix);
	}

	public static Collection<String> getImporters() {
		WikiImporterTracker wikiImporterTracker = _getWikiImporterTracker();

		return wikiImporterTracker.getImporters();
	}

	public static Map<String, Boolean> getLinks(WikiPage page)
		throws PageContentException {

		return _instance._getLinks(page);
	}

	public static List<String> getNodeNames(List<WikiNode> nodes) {
		List<String> nodeNames = new ArrayList<>(nodes.size());

		for (WikiNode node : nodes) {
			nodeNames.add(node.getName());
		}

		return nodeNames;
	}

	public static List<WikiNode> getNodes(
		List<WikiNode> nodes, String[] hiddenNodes,
		PermissionChecker permissionChecker) {

		nodes = ListUtil.copy(nodes);

		Arrays.sort(hiddenNodes);

		Iterator<WikiNode> itr = nodes.iterator();

		while (itr.hasNext()) {
			WikiNode node = itr.next();

			if (!(Arrays.binarySearch(hiddenNodes, node.getName()) < 0) ||
				!WikiNodePermissionChecker.contains(
					permissionChecker, node, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return nodes;
	}

	public static WikiImporter getWikiImporter(String importer) {
		WikiImporterTracker wikiImporterTracker = _getWikiImporterTracker();

		WikiImporter wikiImporter = wikiImporterTracker.getWikiImporter(
			importer);

		if (wikiImporter == null) {
			throw new SystemException(
				"Unable to instantiate wiki importer with name " + importer);
		}

		return wikiImporter;
	}

	public static String getWikiImporterPage(String format) {
		WikiImporterTracker wikiImporterTracker = _getWikiImporterTracker();

		return wikiImporterTracker.getProperty(format, "page");
	}

	public static List<WikiNode> orderNodes(
		List<WikiNode> nodes, String[] visibleNodeNames) {

		if (ArrayUtil.isEmpty(visibleNodeNames)) {
			return nodes;
		}

		nodes = ListUtil.copy(nodes);

		List<WikiNode> orderedNodes = new ArrayList<>(nodes.size());

		for (String visibleNodeName : visibleNodeNames) {
			for (WikiNode node : nodes) {
				if (node.getName().equals(visibleNodeName)) {
					orderedNodes.add(node);

					nodes.remove(node);

					break;
				}
			}
		}

		orderedNodes.addAll(nodes);

		return orderedNodes;
	}

	public static String processContent(String content) {
		content = StringUtil.replace(content, "</p>", "</p>\n");
		content = StringUtil.replace(content, "</br>", "</br>\n");
		content = StringUtil.replace(content, "</div>", "</div>\n");

		return content;
	}

	public static void renderEditPageHTML(
			String format, PageContext pageContext, WikiPage page)
		throws IOException, ServletException {

		WikiEngineTracker wikiEngineTracker = _getWikiEngineTracker();

		WikiEngine wikiEngine = wikiEngineTracker.getWikiEngine(format);

		HttpServletResponse response =
			(HttpServletResponse)pageContext.getResponse();

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			response, unsyncStringWriter);

		wikiEngine.renderEditPage(
			pageContext.getRequest(), pipingServletResponse, page);

		Writer writer = pageContext.getOut();

		StringBundler sb = unsyncStringWriter.getStringBundler();

		writer.write(sb.toString());
	}

	public static boolean validate(long nodeId, String content, String format)
		throws WikiFormatException {

		return _instance._validate(nodeId, content, format);
	}

	private static WikiEngineTracker _getWikiEngineTracker() {
		return _wikiEngineServiceTracker.getService();
	}

	private static WikiImporterTracker _getWikiImporterTracker() {
		return _wikiImporterServiceTracker.getService();
	}

	private String _convert(
			WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
			String attachmentURLPrefix)
		throws PageContentException, WikiFormatException {

		LiferayPortletURL liferayViewPageURL = (LiferayPortletURL)viewPageURL;
		LiferayPortletURL liferayEditPageURL = (LiferayPortletURL)editPageURL;

		WikiEngine wikiEngine = _getWikiEngine(page.getFormat());

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

	private String _getFormatLabel(String format, Locale locale)
		throws WikiFormatException {

		WikiEngine wikiEngine = _getWikiEngine(format);

		return wikiEngine.getFormatLabel(locale);
	}

	private Map<String, Boolean> _getLinks(WikiPage page)
		throws PageContentException {

		try {
			WikiEngine wikiEngine = _getWikiEngine(page.getFormat());

			return wikiEngine.getOutgoingLinks(page);
		}
		catch (WikiFormatException wfe) {
			return Collections.emptyMap();
		}
	}

	private WikiEngine _getWikiEngine(String format)
		throws WikiFormatException {

		WikiEngineTracker wikiEngineTracker = _getWikiEngineTracker();

		WikiEngine wikiEngine = wikiEngineTracker.getWikiEngine(format);

		if (wikiEngine == null) {
			throw new WikiFormatException("Unknown wiki format " + format);
		}

		return wikiEngine;
	}

	private String _replaceAttachments(
		String content, String title, String attachmentURLPrefix) {

		content = StringUtil.replace(content, "[$WIKI_PAGE_NAME$]", title);

		content = StringUtil.replace(
			content, "[$ATTACHMENT_URL_PREFIX$]", attachmentURLPrefix);

		return content;
	}

	private boolean _validate(long nodeId, String content, String format)
		throws WikiFormatException {

		return _getWikiEngine(format).validate(nodeId, content);
	}

	private static final Log _log = LogFactoryUtil.getLog(WikiUtil.class);

	private static final WikiUtil _instance = new WikiUtil();

	private static final Pattern _editPageURLPattern = Pattern.compile(
		"\\[\\$BEGIN_PAGE_TITLE_EDIT\\$\\](.*?)" +
			"\\[\\$END_PAGE_TITLE_EDIT\\$\\]");
	private static final Pattern _viewPageURLPattern = Pattern.compile(
		"\\[\\$BEGIN_PAGE_TITLE\\$\\](.*?)\\[\\$END_PAGE_TITLE\\$\\]");
	private static final ServiceTracker<WikiEngineTracker, WikiEngineTracker>
		_wikiEngineServiceTracker;
	private static final ServiceTracker
		<WikiImporterTracker, WikiImporterTracker> _wikiImporterServiceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(WikiUtil.class);

		_wikiEngineServiceTracker = ServiceTrackerFactory.open(
			bundle, WikiEngineTracker.class);

		_wikiImporterServiceTracker = ServiceTrackerFactory.open(
			bundle, WikiImporterTracker.class);
	}

}