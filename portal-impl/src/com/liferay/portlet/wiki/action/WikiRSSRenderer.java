/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.rss.DefaultRSSRenderer;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.util.WikiUtil;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Sierra Andrés
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 */
public class WikiRSSRenderer extends DefaultRSSRenderer {

	public WikiRSSRenderer(
		HttpServletRequest request, List<WikiPage> pagesToExport,
		boolean diff) {

		super(request);

		_pages = pagesToExport;
		_diff = diff;
		_themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);
		_request = request;
		_nodeId = ParamUtil.getLong(request, "nodeId");
	}

	@Override
	public String getFeedURL() throws PortalException, SystemException {
		String layoutFullURL =PortalUtil.getLayoutFullURL(
			_themeDisplay.getScopeGroupId(), PortletKeys.WIKI);

		StringBundler sb = new StringBundler(4);

		sb.append(layoutFullURL);
		sb.append(Portal.FRIENDLY_URL_SEPARATOR);
		sb.append("wiki/");
		sb.append(_nodeId);

		return sb.toString();
	}

	@Override
	public String getRSSName() {
		return ParamUtil.getString(_request, "title");
	}

	@Override
	public void populateFeedEntries(List<? super SyndEntry> syndEntries)
		throws PortalException, SystemException {

		WikiPage latestPage = null;

		StringBundler sb = new StringBundler(6);

		String entryURL = getFeedURL() + StringPool.SLASH + getRSSName();

		Locale locale = _themeDisplay.getLocale();

		String attachmentURLPrefix = WikiUtil.getAttachmentURLPrefix(
			_themeDisplay.getPathMain(), _themeDisplay.getPlid(), _nodeId,
			getRSSName());

		String displayStyle = ParamUtil.getString(
			_request, "displayStyle", RSSUtil.DISPLAY_STYLE_DEFAULT);

		for (WikiPage page : _pages) {
			SyndEntry syndEntry = new SyndEntryImpl();

			String author = PortalUtil.getUserName(page);

			syndEntry.setAuthor(author);

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.ENTRY_TYPE_DEFAULT);

			sb.setIndex(0);

			sb.append(entryURL);

			if (entryURL.endsWith(StringPool.SLASH)) {
				sb.append(HttpUtil.encodeURL(page.getTitle()));
			}

			if (_diff) {
				if ((latestPage != null) || (_pages.size() == 1)) {
					sb.append(StringPool.QUESTION);
					sb.append(PortalUtil.getPortletNamespace(PortletKeys.WIKI));
					sb.append("version=");
					sb.append(page.getVersion());

					String value = null;

					if (latestPage == null) {
						value =
							WikiUtil.convert(
								page, null, null, attachmentURLPrefix);
					}
					else {
						try {
							value =
								WikiUtil.diffHtml(
									latestPage, page, null, null,
									attachmentURLPrefix);
						}
						catch (Exception e) {
							throw new PortalException(e);
						}
					}

					syndContent.setValue(value);

					syndEntry.setDescription(syndContent);

					syndEntries.add(syndEntry);
				}
			}
			else {
				String value = null;

				if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT)) {
					value = StringUtil.shorten(
						HtmlUtil.extractText(page.getContent()),
						PropsValues.WIKI_RSS_ABSTRACT_LENGTH, StringPool.BLANK);
				}
				else if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE)) {
					value = StringPool.BLANK;
				}
				else {
					value = WikiUtil.convert(
						page, null, null, attachmentURLPrefix);
				}

				syndContent.setValue(value);

				syndEntry.setDescription(syndContent);

				syndEntries.add(syndEntry);
			}

			syndEntry.setLink(sb.toString());
			syndEntry.setPublishedDate(page.getCreateDate());

			String title =
				page.getTitle() + StringPool.SPACE + page.getVersion();

			if (page.isMinorEdit()) {
				title +=
					StringPool.SPACE + StringPool.OPEN_PARENTHESIS +
						LanguageUtil.get(locale, "minor-edit") +
							StringPool.CLOSE_PARENTHESIS;
			}

			syndEntry.setTitle(title);

			syndEntry.setUpdatedDate(page.getModifiedDate());
			syndEntry.setUri(sb.toString());

			latestPage = page;
		}
	}

	private boolean _diff;
	private long _nodeId;
	private List<WikiPage> _pages;
	private HttpServletRequest _request;
	private ThemeDisplay _themeDisplay;

}