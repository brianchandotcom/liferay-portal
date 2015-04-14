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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Zsolt Berentey
 */
public class VerifyMessageBoards extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyStatisticsForCategories();
		verifyStatisticsForThreads();
		verifyAssetsForMessages();
		verifyAssetsForThreads();
	}

	protected void verifyAssetsForMessages() throws Exception {
		List<MBMessage> messages =
			MBMessageLocalServiceUtil.getNoAssetMessages();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + messages.size() + " messages with no asset");
		}

		for (MBMessage message : messages) {
			try {
				MBMessageLocalServiceUtil.updateAsset(
					message.getUserId(), message, null, null, null);

				if (message.getStatus() == WorkflowConstants.STATUS_DRAFT) {
					boolean visible = false;

					if (message.isApproved() &&
						((message.getClassNameId() == 0) ||
						 (message.getParentMessageId() != 0))) {

						visible = true;
					}

					AssetEntryLocalServiceUtil.updateEntry(
						message.getWorkflowClassName(), message.getMessageId(),
						null, visible);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to update asset for message " +
							message.getMessageId() + ": " + e.getMessage());
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Assets verified for messages");
		}
	}

	protected void verifyAssetsForThreads() throws Exception {
		List<MBThread> threads = MBThreadLocalServiceUtil.getNoAssetThreads();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + threads.size() + " threads with no asset");
		}

		for (MBThread thread : threads) {
			try {
				AssetEntryLocalServiceUtil.updateEntry(
					thread.getRootMessageUserId(), thread.getGroupId(),
					thread.getStatusDate(), thread.getLastPostDate(),
					MBThread.class.getName(), thread.getThreadId(), null, 0,
					new long[0], new String[0], false, null, null, null, null,
					String.valueOf(thread.getRootMessageId()), null, null, null,
					null, 0, 0, null);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to update asset for thread " +
							thread.getThreadId() + ": " + e.getMessage());
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Assets verified for threads");
		}
	}

	protected void verifyStatisticsForCategories() throws Exception {
		List<MBCategory> categories =
			MBCategoryLocalServiceUtil.getMBCategories(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + categories.size() +
					" categories for statistics accuracy");
		}

		for (MBCategory category : categories) {
			int threadCount = MBThreadLocalServiceUtil.getCategoryThreadsCount(
				category.getGroupId(), category.getCategoryId(),
				WorkflowConstants.STATUS_APPROVED);
			int messageCount =
				MBMessageLocalServiceUtil.getCategoryMessagesCount(
					category.getGroupId(), category.getCategoryId(),
					WorkflowConstants.STATUS_APPROVED);

			if ((category.getThreadCount() != threadCount) ||
				(category.getMessageCount() != messageCount)) {

				category.setThreadCount(threadCount);
				category.setMessageCount(messageCount);

				MBCategoryLocalServiceUtil.updateMBCategory(category);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Statistics verified for categories");
		}
	}

	protected void verifyStatisticsForThreads() throws Exception {
		List<MBThread> threads = MBThreadLocalServiceUtil.getMBThreads(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + threads.size() +
					" threads for statistics accuracy");
		}

		for (MBThread thread : threads) {
			int messageCount = MBMessageLocalServiceUtil.getThreadMessagesCount(
				thread.getThreadId(), WorkflowConstants.STATUS_APPROVED);

			if (thread.getMessageCount() != messageCount) {
				thread.setMessageCount(messageCount);

				MBThreadLocalServiceUtil.updateMBThread(thread);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Statistics verified for threads");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyMessageBoards.class);

}