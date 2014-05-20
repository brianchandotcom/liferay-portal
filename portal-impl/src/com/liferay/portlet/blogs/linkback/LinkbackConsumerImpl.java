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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexander Chow
 * @author André de Oliveira
 */
public class LinkbackConsumerImpl implements LinkbackConsumer {

	public LinkbackConsumerImpl() {
		_comments = new CommentsImpl();
	}

	@Override
	public void addNewTrackback(long messageId, String url, String entryURL) {
		_trackbacks.add(new Tuple(messageId, url, entryURL));
	}

	@Override
	public void verifyNewTrackbacks() {
		Tuple tuple = null;

		while (!_trackbacks.isEmpty()) {
			synchronized (_trackbacks) {
				tuple = _trackbacks.remove(0);
			}

			long messageId = (Long)tuple.getObject(0);
			String url = (String)tuple.getObject(1);
			String entryUrl = (String)tuple.getObject(2);

			verifyTrackback(messageId, url, entryUrl);
		}
	}

	@Override
	public void verifyTrackback(long messageId, String url, String entryURL) {
		try {
			String result = HttpUtil.URLtoString(url);

			if (result.contains(entryURL)) {
				return;
			}
		}
		catch (Exception e) {
		}

		try {
			_comments.deleteComment(messageId);
		}
		catch (Exception e) {
			_log.error(
				"Error trying to delete trackback message " + messageId, e);
		}
	}

	protected LinkbackConsumerImpl(Comments comments) {
		_comments = comments;
	}

	private static Log _log = LogFactoryUtil.getLog(LinkbackConsumerImpl.class);

	private Comments _comments;
	private List<Tuple> _trackbacks = Collections.synchronizedList(
		new ArrayList<Tuple>());

}