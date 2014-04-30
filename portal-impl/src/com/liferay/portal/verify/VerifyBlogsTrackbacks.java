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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.util.LinkbackConsumer;
import com.liferay.portlet.blogs.util.LinkbackConsumerUtil;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

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
		List<MBDiscussion> discussions =
			MBMessageLocalServiceUtil.getDiscussions(
				BlogsEntry.class.getName());

		for (MBDiscussion discussion : discussions) {
			long entryId = discussion.getClassPK();
			long threadId = discussion.getThreadId();

			try {
				BlogsEntry entry = BlogsEntryLocalServiceUtil.getBlogsEntry(
					entryId);

				List<MBMessage> messages =
					MBMessageLocalServiceUtil.getThreadMessages(
						threadId, WorkflowConstants.STATUS_APPROVED);

				for (MBMessage message : messages) {
					LinkbackConsumer linkbackConsumer =
						LinkbackConsumerUtil.getLinkbackConsumer();

					linkbackConsumer.verifyPost(entry, message);
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		VerifyBlogsTrackbacks.class);

}