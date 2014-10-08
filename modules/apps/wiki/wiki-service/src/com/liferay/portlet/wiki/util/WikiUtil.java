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

package com.liferay.portlet.wiki.util;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.diff.DiffHtmlUtil;
import com.liferay.portal.kernel.diff.DiffVersion;
import com.liferay.portal.kernel.diff.DiffVersionsInfo;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.wiki.PageContentException;
import com.liferay.portlet.wiki.WikiFormatException;
import com.liferay.portlet.wiki.WikiPortletInstanceSettings;
import com.liferay.portlet.wiki.engines.WikiEngine;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageDisplay;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;
import com.liferay.portlet.wiki.util.comparator.PageCreateDateComparator;
import com.liferay.portlet.wiki.util.comparator.PageTitleComparator;
import com.liferay.portlet.wiki.util.comparator.PageVersionComparator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

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

	public static String escapeName(String name) {
		return StringUtil.replace(name, _UNESCAPED_CHARS, _ESCAPED_CHARS);
	}

	public static List<WikiPage> filterOrphans(List<WikiPage> pages)
		throws PortalException {

		List<Map<String, Boolean>> pageTitles =
			new ArrayList<Map<String, Boolean>>();

		for (WikiPage page : pages) {
			pageTitles.add(WikiCacheUtil.getOutgoingLinks(page));
		}

		Set<WikiPage> notOrphans = new HashSet<WikiPage>();

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

		List<WikiPage> orphans = new ArrayList<WikiPage>();

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

		List<DiffVersion> diffVersions = new ArrayList<DiffVersion>();

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

	public static String getEditPage(String format) {
		return _instance._getEditPage(format);
	}

	public static Map<String, String> getEmailFromDefinitionTerms(
		RenderRequest request) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> definitionTerms =
			new LinkedHashMap<String, String>();

		definitionTerms.put(
			"[$COMPANY_ID$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-id-associated-with-the-wiki"));
		definitionTerms.put(
			"[$COMPANY_MX$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-mx-associated-with-the-wiki"));
		definitionTerms.put(
			"[$COMPANY_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-name-associated-with-the-wiki"));
		definitionTerms.put(
			"[$PAGE_USER_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-email-address-of-the-user-who-added-the-page"));
		definitionTerms.put(
			"[$PAGE_USER_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-user-who-added-the-page"));
		definitionTerms.put(
			"[$PORTLET_NAME$]", PortalUtil.getPortletTitle(request));
		definitionTerms.put(
			"[$SITE_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-site-name-associated-with-the-wiki"));

		return definitionTerms;
	}

	public static Map<String, String> getEmailNotificationDefinitionTerms(
		RenderRequest request, String emailFromAddress, String emailFromName) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> definitionTerms =
			new LinkedHashMap<String, String>();

		definitionTerms.put(
			"[$COMPANY_ID$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-id-associated-with-the-wiki"));
		definitionTerms.put(
			"[$COMPANY_MX$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-mx-associated-with-the-wiki"));
		definitionTerms.put(
			"[$COMPANY_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-name-associated-with-the-wiki"));
		definitionTerms.put(
			"[$DIFFS_URL$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-url-of-the-page-comparing-this-page-content-with-the-" +
					"previous-version"));
		definitionTerms.put(
			"[$FROM_ADDRESS$]", HtmlUtil.escape(emailFromAddress));
		definitionTerms.put("[$FROM_NAME$]", HtmlUtil.escape(emailFromName));
		definitionTerms.put(
			"[$NODE_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-node-in-which-the-page-was-added"));
		definitionTerms.put(
			"[$PAGE_CONTENT$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-page-content"));
		definitionTerms.put(
			"[$PAGE_DATE_UPDATE$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-date-of-the-modifications"));
		definitionTerms.put(
			"[$PAGE_DIFFS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-page-content-compared-with-the-previous-version-page-" +
					"content"));
		definitionTerms.put(
			"[$PAGE_ID$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-page-id"));
		definitionTerms.put(
			"[$PAGE_SUMMARY$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-summary-of-the-page-or-the-modifications"));
		definitionTerms.put(
			"[$PAGE_TITLE$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-page-title"));
		definitionTerms.put(
			"[$PAGE_URL$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-page-url"));
		definitionTerms.put(
			"[$PAGE_USER_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-email-address-of-the-user-who-added-the-page"));
		definitionTerms.put(
			"[$PAGE_USER_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-user-who-added-the-page"));

		Company company = themeDisplay.getCompany();

		definitionTerms.put("[$PORTAL_URL$]", company.getVirtualHostname());

		definitionTerms.put(
			"[$PORTLET_NAME$]", PortalUtil.getPortletTitle(request));
		definitionTerms.put(
			"[$SITE_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-site-name-associated-with-the-wiki"));
		definitionTerms.put(
			"[$TO_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-address-of-the-email-recipient"));
		definitionTerms.put(
			"[$TO_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-name-of-the-email-recipient"));

		return definitionTerms;
	}

	public static List<Object> getEntries(Hits hits) {
		List<Object> entries = new ArrayList<Object>();

		for (Document document : hits.getDocs()) {
			String entryClassName = GetterUtil.getString(
				document.get(Field.ENTRY_CLASS_NAME));
			long entryClassPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			try {
				Object obj = null;

				if (entryClassName.equals(DLFileEntry.class.getName())) {
					long classPK = GetterUtil.getLong(
						document.get(Field.CLASS_PK));

					WikiPageLocalServiceUtil.getPage(classPK);

					obj = DLFileEntryLocalServiceUtil.getDLFileEntry(
						entryClassPK);
				}
				else if (entryClassName.equals(MBMessage.class.getName())) {
					long classPK = GetterUtil.getLong(
						document.get(Field.CLASS_PK));

					WikiPageLocalServiceUtil.getPage(classPK);

					obj = MBMessageLocalServiceUtil.getMessage(entryClassPK);
				}
				else if (entryClassName.equals(WikiPage.class.getName())) {
					obj = WikiPageLocalServiceUtil.getPage(entryClassPK);
				}

				entries.add(obj);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Wiki search index is stale and contains entry " +
							"{className=" + entryClassName + ", classPK=" +
								entryClassPK + "}");
				}
			}
		}

		return entries;
	}

	public static WikiNode getFirstNode(PortletRequest portletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
		long groupId = themeDisplay.getScopeGroupId();
		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		List<WikiNode> nodes = WikiNodeLocalServiceUtil.getNodes(groupId);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		WikiPortletInstanceSettings wikiPortletInstanceSettings =
			WikiPortletInstanceSettings.getInstance(
				themeDisplay.getLayout(), portletDisplay.getId());

		String[] visibleNodeNames =
			wikiPortletInstanceSettings.getVisibleNodes();

		nodes = orderNodes(nodes, visibleNodeNames);

		String[] hiddenNodes = wikiPortletInstanceSettings.getHiddenNodes();
		Arrays.sort(hiddenNodes);

		for (WikiNode node : nodes) {
			if ((Arrays.binarySearch(hiddenNodes, node.getName()) < 0) &&
				WikiNodePermission.contains(
					permissionChecker, node, ActionKeys.VIEW)) {

				return node;
			}
		}

		return null;
	}

	public static String getFormattedContent(
			RenderRequest renderRequest, RenderResponse renderResponse,
			WikiPage wikiPage, PortletURL viewPageURL, PortletURL editPageURL,
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
		sb.append(wikiPage.getNodeId());
		sb.append("&title=");
		sb.append(HttpUtil.encodeURL(wikiPage.getTitle()));
		sb.append("&fileName=");

		String attachmentURLPrefix = sb.toString();

		if (!preview && (version == 0)) {
			WikiPageDisplay pageDisplay = WikiCacheUtil.getDisplay(
				wikiPage.getNodeId(), title, curViewPageURL, curEditPageURL,
				attachmentURLPrefix);

			if (pageDisplay != null) {
				return pageDisplay.getFormattedContent();
			}
		}

		return convert(
			wikiPage, curViewPageURL, curEditPageURL, attachmentURLPrefix);
	}

	public static String getHelpPage(String format) {
		return _instance._getHelpPage(format);
	}

	public static String getHelpURL(String format) {
		return _instance._getHelpURL(format);
	}

	public static Map<String, Boolean> getLinks(WikiPage page)
		throws PageContentException {

		return _instance._getLinks(page);
	}

	public static List<String> getNodeNames(List<WikiNode> nodes) {
		List<String> nodeNames = new ArrayList<String>(nodes.size());

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
				!WikiNodePermission.contains(
					permissionChecker, node, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return nodes;
	}

	public static OrderByComparator<WikiPage> getPageOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<WikiPage> orderByComparator = null;

		if (orderByCol.equals("modifiedDate")) {
			orderByComparator = new PageCreateDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("title")) {
			orderByComparator = new PageTitleComparator(orderByAsc);
		}
		else if (orderByCol.equals("version")) {
			orderByComparator = new PageVersionComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static List<WikiNode> orderNodes(
		List<WikiNode> nodes, String[] visibleNodeNames) {

		if (ArrayUtil.isEmpty(visibleNodeNames)) {
			return nodes;
		}

		nodes = ListUtil.copy(nodes);

		List<WikiNode> orderedNodes = new ArrayList<WikiNode>(nodes.size());

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

	public static String unescapeName(String name) {
		return StringUtil.replace(name, _ESCAPED_CHARS, _UNESCAPED_CHARS);
	}

	public static boolean validate(long nodeId, String content, String format)
		throws WikiFormatException {

		return _instance._validate(nodeId, content, format);
	}

	private String _convert(
			WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
			String attachmentURLPrefix)
		throws PageContentException, WikiFormatException {

		LiferayPortletURL liferayViewPageURL = (LiferayPortletURL)viewPageURL;
		LiferayPortletURL liferayEditPageURL = (LiferayPortletURL)editPageURL;

		WikiEngine engine = _getEngine(page.getFormat());

		String content = engine.convert(
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

	private String _getEditPage(String format) {
		return PropsUtil.get(
			PropsKeys.WIKI_FORMATS_EDIT_PAGE, new Filter(format));
	}

	private WikiEngine _getEngine(String format) throws WikiFormatException {
		WikiEngine engine = _engines.get(format);

		if (engine != null) {
			return engine;
		}

		synchronized (_engines) {
			engine = _engines.get(format);

			if (engine != null) {
				return engine;
			}

			try {
				String engineClassName = PropsUtil.get(
					PropsKeys.WIKI_FORMATS_ENGINE, new Filter(format));

				if (engineClassName == null) {
					throw new WikiFormatException(format);
				}

				Class<?> clazz = getClass();

				engine = (WikiEngine)InstanceFactory.newInstance(
					clazz.getClassLoader(), engineClassName);

				engine.setInterWikiConfiguration(
					_readConfigurationFile(
						PropsKeys.WIKI_FORMATS_CONFIGURATION_INTERWIKI,
						format));
				engine.setMainConfiguration(
					_readConfigurationFile(
						PropsKeys.WIKI_FORMATS_CONFIGURATION_MAIN, format));

				_engines.put(format, engine);

				return engine;
			}
			catch (Exception e) {
				throw new WikiFormatException(e);
			}
		}
	}

	private String _getHelpPage(String format) {
		return PropsUtil.get(
			PropsKeys.WIKI_FORMATS_HELP_PAGE, new Filter(format));
	}

	private String _getHelpURL(String format) {
		return PropsUtil.get(
			PropsKeys.WIKI_FORMATS_HELP_URL, new Filter(format));
	}

	private Map<String, Boolean> _getLinks(WikiPage page)
		throws PageContentException {

		try {
			return _getEngine(page.getFormat()).getOutgoingLinks(page);
		}
		catch (WikiFormatException wfe) {
			return Collections.emptyMap();
		}
	}

	private String _readConfigurationFile(String propertyName, String format)
		throws IOException {

		ClassLoader classLoader = getClass().getClassLoader();

		String configurationFile = PropsUtil.get(
			propertyName, new Filter(format));

		if (Validator.isNotNull(configurationFile)) {
			return HttpUtil.URLtoString(
				classLoader.getResource(configurationFile));
		}
		else {
			return StringPool.BLANK;
		}
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

		return _getEngine(format).validate(nodeId, content);
	}

	private static final String[] _ESCAPED_CHARS = new String[] {
		"<PLUS>", "<QUESTION>", "<SLASH>"
	};

	private static final String[] _UNESCAPED_CHARS = new String[] {
		StringPool.PLUS, StringPool.QUESTION, StringPool.SLASH
	};

	private static final Log _log = LogFactoryUtil.getLog(WikiUtil.class);

	private static final WikiUtil _instance = new WikiUtil();

	private static final Pattern _editPageURLPattern = Pattern.compile(
		"\\[\\$BEGIN_PAGE_TITLE_EDIT\\$\\](.*?)" +
			"\\[\\$END_PAGE_TITLE_EDIT\\$\\]");
	private static final Pattern _viewPageURLPattern = Pattern.compile(
		"\\[\\$BEGIN_PAGE_TITLE\\$\\](.*?)\\[\\$END_PAGE_TITLE\\$\\]");

	private final Map<String, WikiEngine> _engines =
		new ConcurrentHashMap<String, WikiEngine>();

}