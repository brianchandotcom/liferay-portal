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

package com.liferay.blogs.verify;

import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.message.boards.kernel.model.MBDiscussion;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.service.MBMessageLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portlet.blogs.linkback.LinkbackConsumerUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * <p>
 * This class looks at every blog comment to see if it is a trackback and
 * verifies that the source URL is a valid URL. Do not run this unless you want
 * to do this.
 * </p>
 *
 * @author Alexander Chow
 */
@Component(
	immediate = true,
	property = {"verify.process.name=com.liferay.blogs.trackbacks"},
	service = VerifyProcess.class
)
public class VerifyBlogsTrackbacks extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyMBDiscussions();
	}

	@Reference(unbind = "-")
	protected void setBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	protected void verifyMBDiscussions() {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<MBDiscussion> mbDiscussions =
				_mbMessageLocalService.getDiscussions(
					BlogsEntry.class.getName());

			for (MBDiscussion mbDiscussion : mbDiscussions) {
				try {
					BlogsEntry entry = _blogsEntryLocalService.getEntry(
						mbDiscussion.getClassPK());

					List<MBMessage> mbMessages =
						_mbMessageLocalService.getThreadMessages(
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
			long defaultUserId = _userLocalService.getDefaultUserId(
				mbMessage.getCompanyId());

			if (mbMessage.getUserId() == defaultUserId) {
				LinkbackConsumerUtil.verifyTrackback(
					mbMessage.getMessageId(), url, entryURL);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyBlogsTrackbacks.class);

	private BlogsEntryLocalService _blogsEntryLocalService;
	private MBMessageLocalService _mbMessageLocalService;
	private UserLocalService _userLocalService;

}