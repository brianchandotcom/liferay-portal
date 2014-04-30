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

package com.liferay.portlet.blogs.linkback;

import com.liferay.portal.comments.CommentsImpl;
import com.liferay.portal.kernel.comments.Comments;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.messageboards.model.MBMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexander Chow
 * @author André de Oliveira
 */
public class LinkbackConsumerImpl implements LinkbackConsumer {

	@Override
	public void addNewTrackback(long messageId, String url, String entryURL) {
		_trackbacks.add(new Tuple(messageId, url, entryURL));
	}

	@Override
	public void verifyNewTrackbacks(Comments comments) {
		Tuple tuple = null;

		while (!_trackbacks.isEmpty()) {
			synchronized (_trackbacks) {
				tuple = _trackbacks.remove(0);
			}

			long messageId = (Long)tuple.getObject(0);
			String url = (String)tuple.getObject(1);
			String entryUrl = (String)tuple.getObject(2);

			_verifyTrackback(messageId, url, entryUrl, comments);
		}
	}

	@Override
	public void verifyPost(BlogsEntry entry, MBMessage message)
		throws PortalException, SystemException {

		long messageId = message.getMessageId();
		String entryURL =
			Portal.FRIENDLY_URL_SEPARATOR + "blogs/" + entry.getUrlTitle();
		String body = message.getBody();
		String url = null;

		int start = body.indexOf("[url=");

		if (start > -1) {
			start += "[url=".length();

			int end = body.indexOf("]", start);

			if (end > -1) {
				url = body.substring(start, end);
			}
		}

		if (Validator.isNotNull(url)) {
			long companyId = message.getCompanyId();
			long userId = message.getUserId();
			long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
				companyId);

			if (userId == defaultUserId) {
				_verifyTrackback(messageId, url, entryURL, new CommentsImpl());
			}
		}
	}

	private static void _verifyTrackback(
		long messageId, String url, String entryURL, Comments comments) {

		try {
			String result = HttpUtil.URLtoString(url);

			if (result.contains(entryURL)) {
				return;
			}
		}
		catch (Exception e) {
		}

		try {
			comments.deleteComment(messageId);
		}
		catch (Exception e) {
			_log.error(
				"Error trying to delete trackback message " + messageId, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LinkbackConsumerImpl.class);

	private List<Tuple> _trackbacks = Collections.synchronizedList(
		new ArrayList<Tuple>());

}