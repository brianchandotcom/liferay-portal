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

package com.liferay.portlet.blogs.trackback;

import com.liferay.portal.comments.CommentsImpl;
import com.liferay.portal.kernel.comments.Comments;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.blogs.linkback.LinkbackConsumer;
import com.liferay.portlet.blogs.linkback.LinkbackConsumerUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;

/**
 * @author Alexander Chow
 * @author André de Oliveira
 */
public class TrackbackImpl implements Trackback {

	public TrackbackImpl() {
		_comments = new CommentsImpl();
		_linkbackConsumer = LinkbackConsumerUtil.getLinkbackConsumer();
	}

	@Override
	public void addTrackback(
			BlogsEntry entry, ThemeDisplay themeDisplay, String excerpt,
			String url, String blogName, String title,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException, SystemException {

		long userId = UserLocalServiceUtil.getDefaultUserId(
			themeDisplay.getCompanyId());
		long groupId = entry.getGroupId();
		String className = BlogsEntry.class.getName();
		long classPK = entry.getEntryId();

		String body = buildBody(themeDisplay, excerpt, url);

		long commentId = _comments.addComment(
			userId, groupId, className, classPK, blogName, title, body,
			serviceContextFunction);

		String entryURL = buildEntryURL(entry, themeDisplay);

		_linkbackConsumer.addNewTrackback(commentId, url, entryURL);
	}

	protected TrackbackImpl(
		Comments comments, LinkbackConsumer linkbackConsumer) {

		_comments = comments;
		_linkbackConsumer = linkbackConsumer;
	}

	protected String buildBody(
		ThemeDisplay themeDisplay, String excerpt, String url) {

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

	protected String buildEntryURL(BlogsEntry entry, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		StringBundler sb = new StringBundler(4);

		sb.append(PortalUtil.getLayoutFullURL(themeDisplay));
		sb.append(Portal.FRIENDLY_URL_SEPARATOR);
		sb.append("blogs/");
		sb.append(entry.getUrlTitle());

		return sb.toString();
	}

	private Comments _comments;
	private LinkbackConsumer _linkbackConsumer;

}