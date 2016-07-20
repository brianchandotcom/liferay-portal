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

package com.liferay.portal.verify;

import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.kernel.service.BlogsEntryLocalServiceUtil;
import com.liferay.message.boards.kernel.model.MBDiscussion;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.blogs.linkback.LinkbackConsumer;
import com.liferay.portlet.blogs.linkback.LinkbackConsumerUtil;

import java.util.List;

/**
 * <p>
 * This class looks at every blog comment to see if it is a trackback and
 * verifies that the source URL is a valid URL. Do not run this unless you want
 * to do this.
 * </p>
 *
 * @author Alexander Chow
 */
public class VerifyBlogsTrackbacks extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyMBDiscussions();
	}

	protected void verifyMBDiscussions() {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<MBDiscussion> mbDiscussions =
				MBMessageLocalServiceUtil.getDiscussions(
					BlogsEntry.class.getName());

			for (MBDiscussion mbDiscussion : mbDiscussions) {
				try {
					BlogsEntry entry = BlogsEntryLocalServiceUtil.getBlogsEntry(
						mbDiscussion.getClassPK());

					List<MBMessage> mbMessages =
						MBMessageLocalServiceUtil.getThreadMessages(
							mbDiscussion.getThreadId(),
							WorkflowConstants.STATUS_APPROVED);

					for (MBMessage mbMessage : mbMessages) {
						_verifyPost(entry, mbMessage);
					}
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
		}
	}

	private void _verifyPost(BlogsEntry entry, MBMessage mbMessage)
		throws PortalException {

		String entryURL =
			Portal.FRIENDLY_URL_SEPARATOR + "blogs/" + entry.getUrlTitle();
		String body = mbMessage.getBody();
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
			long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
				mbMessage.getCompanyId());

			if (mbMessage.getUserId() == defaultUserId) {
				LinkbackConsumerUtil.verifyTrackback(
					mbMessage.getMessageId(), url, entryURL);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyBlogsTrackbacks.class);

}