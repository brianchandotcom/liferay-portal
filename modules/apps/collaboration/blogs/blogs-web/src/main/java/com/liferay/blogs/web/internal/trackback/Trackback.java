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

package com.liferay.blogs.web.internal.trackback;

import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.linkback.LinkbackConsumer;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 * @author André de Oliveira
 */
@Component(service = Trackback.class)
public class Trackback {

	public void addTrackback(
			BlogsEntry entry, ThemeDisplay themeDisplay, String excerpt,
			String url, String blogName, String title,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		long userId = _userLocalService.getDefaultUserId(
			themeDisplay.getCompanyId());
		long groupId = entry.getGroupId();
		String className = BlogsEntry.class.getName();
		long classPK = entry.getEntryId();

		String body = buildBody(themeDisplay, excerpt, url);

		long commentId = _commentManager.addComment(
			userId, groupId, className, classPK, blogName, title, body,
			serviceContextFunction);

		String entryURL = buildEntryURL(entry, themeDisplay);

		_linkbackConsumer.addNewTrackback(commentId, url, entryURL);
	}

	protected String buildBBCodeBody(
		ThemeDisplay themeDisplay, String excerpt, String url) {

		url = StringUtil.replace(
			url, new char[] {CharPool.CLOSE_BRACKET, CharPool.OPEN_BRACKET},
			new String[] {"%5D", "%5B"});

		StringBundler sb = new StringBundler(7);

		sb.append("[...] ");
		sb.append(excerpt);
		sb.append(" [...] [url=");
		sb.append(url);
		sb.append("]");
		sb.append(themeDisplay.translate("read-more"));
		sb.append("[/url]");

		return sb.toString();
	}

	protected String buildBody(
		ThemeDisplay themeDisplay, String excerpt, String url) {

		if (PropsValues.DISCUSSION_COMMENTS_FORMAT.equals("bbcode")) {
			return buildBBCodeBody(themeDisplay, excerpt, url);
		}

		return buildHTMLBody(themeDisplay, excerpt, url);
	}

	protected String buildEntryURL(BlogsEntry entry, ThemeDisplay themeDisplay)
		throws PortalException {

		StringBundler sb = new StringBundler(4);

		sb.append(PortalUtil.getLayoutFullURL(themeDisplay));
		sb.append(Portal.FRIENDLY_URL_SEPARATOR);
		sb.append("blogs/");
		sb.append(entry.getUrlTitle());

		return sb.toString();
	}

	protected String buildHTMLBody(
		ThemeDisplay themeDisplay, String excerpt, String url) {

		StringBundler sb = new StringBundler(7);

		sb.append("[...] ");
		sb.append(excerpt);
		sb.append(" [...] <a href=\"");
		sb.append(url);
		sb.append("\">");
		sb.append(themeDisplay.translate("read-more"));
		sb.append("</a>");

		return sb.toString();
	}

	@Reference
	private CommentManager _commentManager;

	@Reference
	private LinkbackConsumer _linkbackConsumer;

	@Reference
	private UserLocalService _userLocalService;

}