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

import com.liferay.portal.kernel.comment.CommentManagerUtil;
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
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.blogs.linkback.LinkbackConsumer)}
 */
@Deprecated
public class LinkbackConsumerImpl implements LinkbackConsumer {

	@Override
	public void addNewTrackback(long commentId, String url, String entryURL) {
		_trackbacks.add(new Tuple(commentId, url, entryURL));
	}

	@Override
	public void verifyNewTrackbacks() {
		Tuple tuple = null;

		while (!_trackbacks.isEmpty()) {
			synchronized (_trackbacks) {
				tuple = _trackbacks.remove(0);
			}

			long commentId = (Long)tuple.getObject(0);
			String url = (String)tuple.getObject(1);
			String entryUrl = (String)tuple.getObject(2);

			verifyTrackback(commentId, url, entryUrl);
		}
	}

	@Override
	public void verifyTrackback(long commentId, String url, String entryURL) {
		try {
			String result = HttpUtil.URLtoString(url);

			if (result.contains(entryURL)) {
				return;
			}
		}
		catch (Exception e) {
		}

		try {
			CommentManagerUtil.deleteComment(commentId);
		}
		catch (Exception e) {
			_log.error("Unable to delete trackback comment " + commentId, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LinkbackConsumerImpl.class);

	private final List<Tuple> _trackbacks = Collections.synchronizedList(
		new ArrayList<Tuple>());

}