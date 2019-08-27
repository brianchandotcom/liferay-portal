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
import com.liferay.message.boards.util.MBUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.message.boards.model.MBDiscussion",
	service = AopService.class
)
public class MBDiscussionLocalServiceImpl
	extends MBDiscussionLocalServiceBaseImpl {

	@Override
	public MBDiscussion addDiscussion(
			long userId, long groupId, long classNameId, long classPK,
			long threadId, ServiceContext serviceContext)
		throws PortalException {

		Group group = groupLocalService.getGroup(groupId);

		User user = userLocalService.fetchUser(
			_portal.getValidUserId(group.getCompanyId(), userId));

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
		return mbDiscussionPersistence.fetchByC_C(
			classNameLocalService.getClassNameId(className), classPK);
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

		return mbDiscussionPersistence.findByC_C(
			classNameLocalService.getClassNameId(className), classPK);
	}

	@Override
	public List<MBDiscussion> getDiscussions(String className) {
		return mbDiscussionPersistence.findByClassNameId(
			classNameLocalService.getClassNameId(className));
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

		_subscriptionLocalService.addSubscription(
			userId, groupId, MBUtil.getSubscriptionClassName(className),
			classPK);
	}

	@Override
	public void unsubscribeDiscussion(
			long userId, String className, long classPK)
		throws PortalException {

		_subscriptionLocalService.deleteSubscription(
			userId, MBUtil.getSubscriptionClassName(className), classPK);
	}

	@Reference
	private Portal _portal;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}