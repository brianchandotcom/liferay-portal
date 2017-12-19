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

package com.liferay.message.boards.service.impl;

import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.service.base.MBDiscussionLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.util.List;

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

		User user = userLocalService.getUser(userId);

		long discussionId = counterLocalService.increment();

		MBDiscussion discussion = mbDiscussionPersistence.create(discussionId);

		discussion.setUuid(serviceContext.getUuid());
		discussion.setGroupId(groupId);
		discussion.setCompanyId(serviceContext.getCompanyId());
		discussion.setUserId(userId);
		discussion.setUserName(user.getFullName());
		discussion.setClassNameId(classNameId);
		discussion.setClassPK(classPK);
		discussion.setThreadId(threadId);

		mbDiscussionPersistence.update(discussion);

		return discussion;
	}

	@Override
	public MBDiscussion fetchDiscussion(long discussionId) {
		return mbDiscussionPersistence.fetchByPrimaryKey(discussionId);
	}

	@Override
	public MBDiscussion fetchDiscussion(long classNameId, long classPK) {
		return mbDiscussionPersistence.fetchByC_C(classNameId, classPK);
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
	public List<MBDiscussion> getDiscussions(String className) {
		long classNameId = classNameLocalService.getClassNameId(className);

		return mbDiscussionPersistence.findByClassNameId(classNameId);
	}

	@Override
	public MBDiscussion getThreadDiscussion(long threadId)
		throws PortalException {

		return mbDiscussionPersistence.findByThreadId(threadId);
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

	@ServiceReference(type = SubscriptionLocalService.class)
	protected SubscriptionLocalService subscriptionLocalService;

}