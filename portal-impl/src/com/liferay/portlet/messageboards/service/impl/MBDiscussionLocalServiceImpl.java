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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.base.MBDiscussionLocalServiceBaseImpl;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.RatingsStats;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class MBDiscussionLocalServiceImpl
	extends MBDiscussionLocalServiceBaseImpl {

	@Override
	public MBDiscussion addDiscussion(
			long userId, long groupId, long classNameId, long classPK,
			long threadId, ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		long discussionId = counterLocalService.increment();

		return addDiscussion(
			serviceContext.getUuid(), discussionId, groupId,
			serviceContext.getCompanyId(), userId, user.getFullName(),
			serviceContext.getCreateDate(now),
			serviceContext.getModifiedDate(now), classNameId, classPK,
			threadId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #addDiscussion(long, long,
	 *             long, long, long, ServiceContext)}
	 */
	@Deprecated
	@Override
	public MBDiscussion addDiscussion(
			long userId, long classNameId, long classPK, long threadId,
			ServiceContext serviceContext)
		throws PortalException {

		return addDiscussion(
			userId, serviceContext.getScopeGroupId(), classNameId, classPK,
			threadId, serviceContext);
	}

	@Override
	public MBDiscussion fetchDiscussion(long discussionId) {
		return mbDiscussionPersistence.fetchByPrimaryKey(discussionId);
	}

	@Override
	public MBDiscussion fetchDiscussion(String className, long classPK) {
		long classNameId = classNameLocalService.getClassNameId(className);

		return mbDiscussionPersistence.fetchByC_C(classNameId, classPK);
	}

	@Override
	public MBDiscussion fetchThreadDiscussion(long threadId) {
		return mbDiscussionPersistence.fetchByThreadId(threadId);
	}

	@Override
	public MBDiscussion getDiscussion(long discussionId)
		throws PortalException {

		return mbDiscussionPersistence.findByPrimaryKey(discussionId);
	}

	@Override
	public MBDiscussion getDiscussion(String className, long classPK)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);

		return mbDiscussionPersistence.findByC_C(classNameId, classPK);
	}

	@Override
	public MBDiscussion getThreadDiscussion(long threadId)
		throws PortalException {

		return mbDiscussionPersistence.findByThreadId(threadId);
	}

	@Override
	public void importMBDiscussion(
			String fromClassName, long fromClassPK, String toClassName,
			long toClassPK)
		throws PortalException {

		long fromClassNameId = classNameLocalService.getClassNameId(
			fromClassName);

		MBDiscussion mbDiscussion = mbDiscussionPersistence.fetchByC_C(
			fromClassNameId, fromClassPK);

		if (mbDiscussion == null) {
			return;
		}

		long toClassNameId = classNameLocalService.getClassNameId(toClassName);

		long threadId = importMBThread(
			mbDiscussion.getThreadId(), toClassNameId, toClassPK);

		addDiscussion(
			PortalUUIDUtil.generate(), counterLocalService.increment(),
			mbDiscussion.getGroupId(), mbDiscussion.getCompanyId(),
			mbDiscussion.getUserId(), mbDiscussion.getUserName(),
			mbDiscussion.getCreateDate(), mbDiscussion.getModifiedDate(),
			toClassNameId, toClassPK, threadId);
	}

	@Override
	public void subscribeDiscussion(
			long userId, long groupId, String className, long classPK)
		throws PortalException {

		subscriptionLocalService.addSubscription(
			userId, groupId, className, classPK);
	}

	@Override
	public void unsubscribeDiscussion(
			long userId, String className, long classPK)
		throws PortalException {

		subscriptionLocalService.deleteSubscription(userId, className, classPK);
	}

	protected MBDiscussion addDiscussion(
		String uuid, long discussionId, long groupId, long companyId,
		long userId, String userName, Date createDate, Date modifiedDate,
		long classNameId, long classPK, long threadId) {

		MBDiscussion mbDiscussion = mbDiscussionPersistence.create(
			discussionId);

		mbDiscussion.setUuid(uuid);
		mbDiscussion.setGroupId(groupId);
		mbDiscussion.setCompanyId(companyId);
		mbDiscussion.setUserId(userId);
		mbDiscussion.setUserName(userName);
		mbDiscussion.setCreateDate(createDate);
		mbDiscussion.setModifiedDate(modifiedDate);
		mbDiscussion.setClassNameId(classNameId);
		mbDiscussion.setClassPK(classPK);
		mbDiscussion.setThreadId(threadId);

		return mbDiscussionPersistence.update(mbDiscussion);
	}

	protected void addMBMessage(
			String uuid, long messageId, long groupId, long companyId,
			long userId, String userName, Date createDate, Date modifiedDate,
			long classNameId, long classPK, long categoryId, long threadId,
			long rootMessageId, long parentMessageId, String subject,
			String body, String format, boolean anonymous, double priority,
			boolean allowPingbacks, boolean answer, int status,
			long statusByUserId, String statusByUserName, Date statusDate,
			Map<Long, Long> mbMessageIds)
		throws PortalException {

		if (parentMessageId == MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) {
			rootMessageId = messageId;
		}
		else {
			rootMessageId = importMBMessage(
				rootMessageId, threadId, classNameId, classPK, mbMessageIds);

			parentMessageId = importMBMessage(
				parentMessageId, threadId, classNameId, classPK, mbMessageIds);
		}

		MBMessage mbMessage = mbMessagePersistence.create(messageId);

		mbMessage.setUuid(uuid);
		mbMessage.setGroupId(groupId);
		mbMessage.setCompanyId(companyId);
		mbMessage.setUserId(userId);
		mbMessage.setUserName(userName);
		mbMessage.setCreateDate(createDate);
		mbMessage.setModifiedDate(modifiedDate);
		mbMessage.setClassNameId(classNameId);
		mbMessage.setClassPK(classPK);
		mbMessage.setCategoryId(categoryId);
		mbMessage.setThreadId(threadId);
		mbMessage.setRootMessageId(rootMessageId);
		mbMessage.setParentMessageId(parentMessageId);
		mbMessage.setSubject(subject);
		mbMessage.setBody(body);
		mbMessage.setFormat(format);
		mbMessage.setAnonymous(anonymous);
		mbMessage.setPriority(priority);
		mbMessage.setAllowPingbacks(allowPingbacks);
		mbMessage.setAnswer(answer);
		mbMessage.setStatus(status);
		mbMessage.setStatusByUserId(statusByUserId);
		mbMessage.setStatusByUserName(statusByUserName);
		mbMessage.setStatusDate(statusDate);

		mbMessagePersistence.update(mbMessage);
	}

	protected void addMBThread(
		String uuid, long threadId, long groupId, long companyId, long userId,
		String userName, Date createDate, Date modifiedDate, long categoryId,
		long rootMessageId, long rootMessageUserId, int messageCount,
		int viewCount, long lastPostByUserId, Date lastPostDate,
		double priority, boolean question, int status, long statusByUserId,
		String statusByUserName, Date statusDate) {

		MBThread mbThread = mbThreadPersistence.create(threadId);

		mbThread.setUuid(uuid);
		mbThread.setGroupId(groupId);
		mbThread.setCompanyId(companyId);
		mbThread.setUserId(userId);
		mbThread.setUserName(userName);
		mbThread.setCreateDate(createDate);
		mbThread.setModifiedDate(modifiedDate);
		mbThread.setCategoryId(categoryId);
		mbThread.setRootMessageId(rootMessageId);
		mbThread.setRootMessageUserId(rootMessageUserId);
		mbThread.setMessageCount(messageCount);
		mbThread.setViewCount(viewCount);
		mbThread.setLastPostByUserId(lastPostByUserId);
		mbThread.setLastPostDate(lastPostDate);
		mbThread.setPriority(priority);
		mbThread.setQuestion(question);
		mbThread.setStatus(status);
		mbThread.setStatusByUserId(statusByUserId);
		mbThread.setStatusByUserName(statusByUserName);
		mbThread.setStatusDate(statusDate);

		mbThreadPersistence.update(mbThread);
	}

	protected RatingsEntry addRatingsEntry(
		long entryId, long companyId, long userId, String userName,
		Date createDate, Date modifiedDate, long classNameId, long classPK,
		double score) {

		RatingsEntry ratingsEntry = ratingsEntryPersistence.create(entryId);

		ratingsEntry.setCompanyId(companyId);
		ratingsEntry.setUserId(userId);
		ratingsEntry.setUserName(userName);
		ratingsEntry.setCreateDate(createDate);
		ratingsEntry.setModifiedDate(modifiedDate);
		ratingsEntry.setClassNameId(classNameId);
		ratingsEntry.setClassPK(classPK);
		ratingsEntry.setScore(score);

		return ratingsEntryPersistence.update(ratingsEntry);
	}

	protected RatingsStats addRatingsStats(
		long statsId, long classNameId, long classPK, int totalEntries,
		double totalScore, double averageScore) {

		RatingsStats ratingsStats = ratingsStatsPersistence.create(statsId);

		ratingsStats.setClassNameId(classNameId);
		ratingsStats.setClassPK(classPK);
		ratingsStats.setTotalEntries(totalEntries);
		ratingsStats.setTotalScore(totalScore);
		ratingsStats.setAverageScore(averageScore);

		return ratingsStatsPersistence.update(ratingsStats);
	}

	protected long importMBMessage(
			long messageId, long threadId, long toClassNameId, long toClassPK,
			Map<Long, Long> mbMessageIds)
		throws PortalException {

		MBMessage mbMessage = mbMessagePersistence.findByPrimaryKey(messageId);

		return importMBMessage(
			mbMessage, threadId, toClassNameId, toClassPK, mbMessageIds);
	}

	protected long importMBMessage(
			MBMessage mbMessage, long threadId, long toClassNameId,
			long toClassPK,
			Map<Long, Long> mbMessageIds)
		throws PortalException {

		Long messageId = mbMessageIds.get(mbMessage.getMessageId());

		if (messageId != null) {
			return messageId;
		}

		messageId = counterLocalService.increment();

		addMBMessage(
			PortalUUIDUtil.generate(), messageId, mbMessage.getGroupId(),
			mbMessage.getCompanyId(), mbMessage.getUserId(),
			mbMessage.getUserName(), mbMessage.getCreateDate(),
			mbMessage.getModifiedDate(), toClassNameId, toClassPK,
			mbMessage.getCategoryId(), threadId, mbMessage.getRootMessageId(),
			mbMessage.getParentMessageId(), mbMessage.getSubject(),
			mbMessage.getBody(), mbMessage.getFormat(), mbMessage.isAnonymous(),
			mbMessage.getPriority(), mbMessage.getAllowPingbacks(),
			mbMessage.isAnswer(), mbMessage.getStatus(),
			mbMessage.getStatusByUserId(), mbMessage.getStatusByUserName(),
			mbMessage.getStatusDate(), mbMessageIds);

		long mbDiscussionClassNameId = classNameLocalService.getClassNameId(
			MBDiscussion.class.getName());

		importRatings(
			mbDiscussionClassNameId, mbMessage.getMessageId(),
			mbDiscussionClassNameId, messageId);

		mbMessageIds.put(mbMessage.getMessageId(), messageId);

		return messageId;
	}

	protected long importMBThread(
			long threadId, long toClassNameId, long toClassPK)
		throws PortalException {

		MBThread mbThread = mbThreadPersistence.findByPrimaryKey(threadId);

		return importMBThread(mbThread, toClassNameId, toClassPK);
	}

	protected long importMBThread(
			MBThread mbThread, long toClassNameId, long toClassPK)
		throws PortalException {

		long threadId = counterLocalService.increment();

		addMBThread(
			PortalUUIDUtil.generate(), threadId, mbThread.getGroupId(),
			mbThread.getCompanyId(), mbThread.getUserId(),
			mbThread.getUserName(), mbThread.getCreateDate(),
			mbThread.getModifiedDate(), mbThread.getCategoryId(), 0,
			mbThread.getRootMessageUserId(), mbThread.getMessageCount(),
			mbThread.getViewCount(), mbThread.getLastPostByUserId(),
			mbThread.getLastPostDate(), mbThread.getPriority(),
			mbThread.isQuestion(), mbThread.getStatus(),
			mbThread.getStatusByUserId(), mbThread.getStatusByUserName(),
			mbThread.getStatusDate());

		Map<Long, Long> mbMessageIds = new HashMap<>();

		List<MBMessage> mbMessages = mbMessagePersistence.findByThreadId(
			mbThread.getThreadId());

		for (MBMessage mbMessage : mbMessages) {
			importMBMessage(
				mbMessage, threadId, toClassNameId, toClassPK, mbMessageIds);
		}

		updateMBThreadRootMessageId(
			threadId, mbMessageIds.get(mbThread.getRootMessageId()));

		return threadId;
	}

	protected void importRatings(
		long oldClassNameId, long oldClassPK, long classNameId, long classPK) {

		List<RatingsEntry> ratingsEntries = ratingsEntryPersistence.findByC_C(
			oldClassNameId, oldClassPK);

		for (RatingsEntry ratingsEntry : ratingsEntries) {
			addRatingsEntry(
				counterLocalService.increment(), ratingsEntry.getCompanyId(),
				ratingsEntry.getUserId(), ratingsEntry.getUserName(),
				ratingsEntry.getCreateDate(), ratingsEntry.getModifiedDate(),
				classNameId, classPK, ratingsEntry.getScore());
		}

		RatingsStats ratingsStats = ratingsStatsPersistence.fetchByC_C(
			oldClassNameId, oldClassPK);

		if (ratingsStats == null) {
			return;
		}

		addRatingsStats(
			counterLocalService.increment(), classNameId, classPK,
			ratingsStats.getTotalEntries(), ratingsStats.getTotalScore(),
			ratingsStats.getAverageScore());
	}

	protected void updateMBThreadRootMessageId(
			long threadId, long rootMessageId)
		throws PortalException {

		MBThread mbThread = mbThreadPersistence.findByPrimaryKey(threadId);

		mbThread.setRootMessageId(rootMessageId);

		mbThreadPersistence.update(mbThread);
	}

}