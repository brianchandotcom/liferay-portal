/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.internal.linkback;

import com.liferay.blogs.linkback.LinkbackConsumer;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 * @author André de Oliveira
 */
@Component(service = LinkbackConsumer.class)
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
			String result = _http.URLtoString(url);

			if (result.contains(entryURL)) {
				return;
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		try {
			_commentManager.deleteComment(commentId);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to delete trackback comment " + commentId, exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LinkbackConsumerImpl.class);

	@Reference
	private CommentManager _commentManager;

	@Reference
	private Http _http;

	private final List<Tuple> _trackbacks = Collections.synchronizedList(
		new ArrayList<Tuple>());

}