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

package com.liferay.portlet.blogs.rss;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.rss.RSSRenderer;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
public abstract class BlogsBaseRSSRenderer implements RSSRenderer {

	public BlogsBaseRSSRenderer(
		List<BlogsEntry> blogsEntries, HttpServletRequest request) {

		_blogsEntries = blogsEntries;
		_request = request;
		_themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);
		_displayStyle = ParamUtil.getString(
			request, "displayStyle", RSSUtil.DISPLAY_STYLE_DEFAULT);;
	}

	@Override
	public String getAlternateURL() throws PortalException, SystemException {
		String feedURL = getFeedURL();

		if (feedURL.endsWith("/-/blogs/rss")) {
			return feedURL.substring(0, feedURL.length() - 12);
		}

		return PortalUtil.getLayoutFullURL(_themeDisplay);
	}

	abstract protected String getEntryURL()
		throws PortalException, SystemException;

	/**
	 * @throws PortalException
	 * @throws SystemException
	 */
	@Override
	public String getFeedURL() throws PortalException, SystemException {
		return _themeDisplay.getPortalURL() + _themeDisplay.getPathMain() +
			"/blogs/find_entry?";
	}

	/**
	 * @throws PortalException
	 * @throws SystemException
	 */
	@Override
	public Date getPublicationDate() throws PortalException, SystemException {
		return new Date();
	}

	public HttpServletRequest getRequest() {
		return _request;
	}

	@Override
	public String getRSSDescription() throws PortalException, SystemException {
		return getRSSName();
	}

	@Override
	public String getRSSFeedType() throws PortalException, SystemException {
		String type = ParamUtil.getString(
			getRequest(), "type", RSSUtil.FORMAT_DEFAULT);

		return RSSUtil.getFeedType(type, getRSSVersion());
	}

	/**
	 * @throws PortalException
	 * @throws SystemException
	 */
	public double getRSSVersion() throws PortalException, SystemException {
		return ParamUtil.getDouble(
			_request, "version", RSSUtil.VERSION_DEFAULT);
	}

	public ThemeDisplay getThemeDisplay() {
		return _themeDisplay;
	}

	@Override
	abstract public String getRSSName() throws PortalException, SystemException;

	@Override
	public void populateFeedEntries(List<? super SyndEntry> syndEntries)
		throws PortalException, SystemException {

		for (BlogsEntry entry : _blogsEntries) {
			SyndEntry syndEntry = new SyndEntryImpl();

			String author = PortalUtil.getUserName(entry);

			syndEntry.setAuthor(author);

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.ENTRY_TYPE_DEFAULT);

			String value = null;

			if (_displayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT)) {
				String summary = entry.getDescription();

				if (Validator.isNull(summary)) {
					summary = entry.getContent();
				}

				value = StringUtil.shorten(
					HtmlUtil.extractText(summary),
					PropsValues.BLOGS_RSS_ABSTRACT_LENGTH, StringPool.BLANK);
			}
			else if (_displayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE)) {
				value = StringPool.BLANK;
			}
			else {
				value = StringUtil.replace(
					entry.getContent(),
					new String[] {
						"href=\"/", "src=\"/"
					},
					new String[] {
						"href=\"" + _themeDisplay.getURLPortal() + "/",
						"src=\"" + _themeDisplay.getURLPortal() + "/"
					});
			}

			syndContent.setValue(value);

			syndEntry.setDescription(syndContent);

			StringBundler sb = new StringBundler(4);

			String entryURL = getEntryURL();

			if (entryURL.endsWith("/blogs/rss")) {
				sb.append(entryURL.substring(0, entryURL.length() - 3));
				sb.append(entry.getUrlTitle());
			}
			else {
				sb.append(entryURL);

				if (!entryURL.endsWith(StringPool.QUESTION)) {
					sb.append(StringPool.AMPERSAND);
				}

				sb.append("entryId=");
				sb.append(entry.getEntryId());
			}

			String link = sb.toString();

			syndEntry.setLink(link);

			syndEntry.setPublishedDate(entry.getDisplayDate());
			syndEntry.setTitle(entry.getTitle());
			syndEntry.setUpdatedDate(entry.getModifiedDate());
			syndEntry.setUri(link);

			syndEntries.add(syndEntry);
		}

	}

	private List<BlogsEntry> _blogsEntries;
	private String _displayStyle;
	private HttpServletRequest _request;
	private ThemeDisplay _themeDisplay;

}