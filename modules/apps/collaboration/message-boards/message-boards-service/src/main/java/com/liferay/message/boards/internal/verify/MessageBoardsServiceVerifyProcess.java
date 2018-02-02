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

package com.liferay.message.boards.internal.verify;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.message.boards.internal.verify.model.MBBanVerifiableModel;
import com.liferay.message.boards.internal.verify.model.MBCategoryVerifiableModel;
import com.liferay.message.boards.internal.verify.model.MBDiscussionVerifiableModel;
import com.liferay.message.boards.internal.verify.model.MBMessageVerifiableModel;
import com.liferay.message.boards.internal.verify.model.MBThreadFlagVerifiableModel;
import com.liferay.message.boards.internal.verify.model.MBThreadVerifiableModel;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.verify.VerifyAuditedModel;
import com.liferay.portal.verify.VerifyGroupedModel;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.VerifyResourcePermissions;
import com.liferay.portal.verify.VerifyUUID;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Zsolt Berentey
 */
@Component(
	immediate = true,
	property = {"verify.process.name=com.liferay.message.boards.service"},
	service = {VerifyProcess.class}
)
public class MessageBoardsServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyAuditedModels();
		verifyGroupedModels();
		verifyResourcedModels();
		verifyStatisticsForCategories();
		verifyStatisticsForThreads();
		verifyAssetsForMessages();
		verifyAssetsForThreads();
		verifyUUIDModels();
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.message.boards.service)(release.schema.version=1.0.1))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	protected void verifyAssetsForMessages() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<MBMessage> messages =
				_mbMessageLocalService.getNoAssetMessages();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Processing " + messages.size() +
						" messages with no asset");
			}

			for (MBMessage message : messages) {
				try {
					_mbMessageLocalService.updateAsset(
						message.getUserId(), message, null, null, null);

					if (message.getStatus() == WorkflowConstants.STATUS_DRAFT) {
						boolean visible = false;

						if (message.isApproved() &&
							((message.getClassNameId() == 0) ||
							 (message.getParentMessageId() != 0))) {

							visible = true;
						}

						_assetEntryLocalService.updateEntry(
							message.getWorkflowClassName(),
							message.getMessageId(), null, null, true, visible);
					}
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Unable to update asset for message ",
								String.valueOf(message.getMessageId()), ": ",
								e.getMessage()));
					}
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Assets verified for messages");
			}
		}
	}

	protected void verifyAssetsForThreads() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<MBThread> threads = _mbThreadLocalService.getNoAssetThreads();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Processing " + threads.size() + " threads with no asset");
			}

			for (MBThread thread : threads) {
				try {
					_assetEntryLocalService.updateEntry(
						thread.getRootMessageUserId(), thread.getGroupId(),
						thread.getStatusDate(), thread.getLastPostDate(),
						MBThread.class.getName(), thread.getThreadId(), null, 0,
						new long[0], new String[0], true, false, null, null,
						null, null, null,
						String.valueOf(thread.getRootMessageId()), null, null,
						null, null, 0, 0, null);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Unable to update asset for thread ",
								String.valueOf(thread.getThreadId()), ": ",
								e.getMessage()));
					}
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Assets verified for threads");
			}
		}
	}

	protected void verifyAuditedModels() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_verifyAuditedModel.verify(
				new MBDiscussionVerifiableModel(),
				new MBThreadVerifiableModel(),
				new MBThreadFlagVerifiableModel());
		}
	}

	protected void verifyGroupedModels() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_verifyGroupedModel.verify(
				new MBDiscussionVerifiableModel(),
				new MBThreadFlagVerifiableModel());
		}
	}

	protected void verifyResourcedModels() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_verifyResourcePermissions.verify(
				new MBCategoryVerifiableModel(),
				new MBMessageVerifiableModel());
		}
	}

	protected void verifyStatisticsForCategories() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Processing categories for statistics accuracy");
			}

			StringBundler sb = new StringBundler(6);

			sb.append("update MBCategory set threadCount = (select count(*) ");
			sb.append("from MBThread where (MBCategory.groupId = ");
			sb.append("MBThread.groupId) and (MBCategory.categoryId = ");
			sb.append("MBThread.categoryId) and (MBThread.status = ");
			sb.append(WorkflowConstants.STATUS_APPROVED);
			sb.append("))");

			runSQL(sb.toString());

			sb.setIndex(0);

			sb.append("update MBCategory set messageCount = (select count(*) ");
			sb.append("from MBMessage where (MBCategory.groupId = ");
			sb.append("MBMessage.groupId) and (MBCategory.categoryId = ");
			sb.append("MBMessage.categoryId) and (MBMessage.status = ");
			sb.append(WorkflowConstants.STATUS_APPROVED);
			sb.append("))");

			runSQL(sb.toString());

			if (_log.isDebugEnabled()) {
				_log.debug("Statistics verified for categories");
			}
		}
	}

	protected void verifyStatisticsForThreads() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Processing threads for statistics accuracy");
			}

			StringBundler sb = new StringBundler(5);

			sb.append("update MBThread set messageCount = (select count(*) ");
			sb.append("from MBMessage where (MBThread.threadId = ");
			sb.append("MBMessage.threadId) and (MBMessage.status = ");
			sb.append(WorkflowConstants.STATUS_APPROVED);
			sb.append("))");

			runSQL(sb.toString());

			if (_log.isDebugEnabled()) {
				_log.debug("Statistics verified for threads");
			}
		}
	}

	protected void verifyUUIDModels() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			VerifyUUID.verify(
				new MBBanVerifiableModel(), new MBDiscussionVerifiableModel(),
				new MBThreadVerifiableModel(),
				new MBThreadFlagVerifiableModel());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MessageBoardsServiceVerifyProcess.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	private final VerifyAuditedModel _verifyAuditedModel =
		new VerifyAuditedModel();
	private final VerifyGroupedModel _verifyGroupedModel =
		new VerifyGroupedModel();
	private final VerifyResourcePermissions _verifyResourcePermissions =
		new VerifyResourcePermissions();

}