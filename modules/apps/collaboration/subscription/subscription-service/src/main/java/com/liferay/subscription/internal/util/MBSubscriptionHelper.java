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

package com.liferay.subscription.internal.util;

import com.liferay.message.boards.kernel.model.MBCategory;
import com.liferay.message.boards.kernel.model.MBCategoryConstants;
import com.liferay.message.boards.kernel.service.MBMessageLocalService;
import com.liferay.message.boards.kernel.service.MBThreadLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.subscription.model.Subscription;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = MBSubscriptionHelper.class)
public class MBSubscriptionHelper {

	public List<MBCategory> addSubscribedRootCategory(
			long groupId, long userId, List<MBCategory> categories)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		Subscription subscription = _subscriptionLocalService.fetchSubscription(
			group.getCompanyId(), userId, MBCategory.class.getName(), groupId);

		if (subscription != null) {
			int threadCount = _mbThreadLocalService.getCategoryThreadsCount(
				groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED);
			int messageCount = _mbMessageLocalService.getCategoryMessagesCount(
				groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED);

			MBCategory category = new MBCategoryImpl();

			category.setGroupId(group.getGroupId());
			category.setCompanyId(group.getCompanyId());
			category.setName(group.getDescriptiveName());
			category.setDescription(group.getDescription());
			category.setThreadCount(threadCount);
			category.setMessageCount(messageCount);

			List<MBCategory> list = new ArrayList<>(categories);

			list.add(category);

			return Collections.unmodifiableList(list);
		}

		return categories;
	}

	public int addSubscribedRootCategoryCount(
			long groupId, long userId, int count)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		Subscription subscription = _subscriptionLocalService.fetchSubscription(
			group.getCompanyId(), userId, MBCategory.class.getName(), groupId);

		if (subscription != null) {
			count++;
		}

		return count;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setMbMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	@Reference(unbind = "-")
	protected void setMbThreadLocalService(
		MBThreadLocalService mbThreadLocalService) {

		_mbThreadLocalService = mbThreadLocalService;
	}

	@Reference(unbind = "-")
	protected void setSubscriptionLocalService(
		SubscriptionLocalService subscriptionLocalService) {

		_subscriptionLocalService = subscriptionLocalService;
	}

	private GroupLocalService _groupLocalService;
	private MBMessageLocalService _mbMessageLocalService;
	private MBThreadLocalService _mbThreadLocalService;
	private SubscriptionLocalService _subscriptionLocalService;

}