/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.subscription;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Raymond Augé
 */
public class CleanUpSubscriptionMessageListener
	extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		long groupId = (Long)message.get("groupId");
		long[] userIds = (long[])message.get("userIds");

		for (long userId : userIds) {
			User user = UserLocalServiceUtil.getUser(userId);

			processUser(user, groupId);
		}
	}

	protected long[] getGroupIds(List<Group> groups) {
		long[] groupIds = new long[groups.size()];

		for (int i = 0; i < groups.size(); i++) {
			Group group = groups.get(i);

			groupIds[i] = group.getGroupId();
		}

		return groupIds;
	}

	protected void processAssetEntry(
			Subscription subscription, long groupId, long[] groupIds)
		throws PortalException, SystemException {

		String className = subscription.getClassName();

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassName(className);

		if (assetRendererFactory != null) {
			AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
				className, subscription.getClassPK());

			if ((assetEntry.getGroupId() == groupId) ||
				!ArrayUtil.contains(
					groupIds, assetEntry.getGroupId())) {

				SubscriptionLocalServiceUtil.deleteSubscription(
					subscription.getSubscriptionId());
			}

			return;
		}

		// This means the subscription was not handled by any other case.

		throw new PortalException();
	}

	protected void processLayout(
			Subscription subscription, long groupId, long[] groupIds)
		throws PortalException, SystemException {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(
			subscription.getClassPK());

		if ((layout != null) &&
			((layout.getGroupId() == groupId) ||
			 !ArrayUtil.contains(groupIds, layout.getGroupId()))) {

			SubscriptionLocalServiceUtil.deleteSubscription(
				subscription.getSubscriptionId());
		}
	}

	protected void processMBCategory(
			Subscription subscription, long groupId, long[] groupIds)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryLocalServiceUtil.fetchMBCategory(
			subscription.getClassPK());

		if ((category != null) &&
			((category.getGroupId() == groupId) ||
			 !ArrayUtil.contains(groupIds, category.getGroupId()))) {

			SubscriptionLocalServiceUtil.deleteSubscription(
				subscription.getSubscriptionId());

			return;
		}

		// Perhaps this is a registration of message board categories for the
		// group.

		Group group = GroupLocalServiceUtil.fetchGroup(
			subscription.getClassPK());

		if ((group != null) &&
			((group.getGroupId() == groupId) ||
			 !ArrayUtil.contains(groupIds, group.getGroupId()))){

			SubscriptionLocalServiceUtil.deleteSubscription(
				subscription.getSubscriptionId());
		}
	}

	protected void processMBThread(
			Subscription subscription, long groupId, long[] groupIds)
		throws PortalException, SystemException {

		MBThread thread = MBThreadLocalServiceUtil.fetchThread(
			subscription.getClassPK());

		if ((thread != null) &&
			((thread.getGroupId() == groupId) ||
			 !ArrayUtil.contains(groupIds, thread.getGroupId()))) {

			SubscriptionLocalServiceUtil.deleteSubscription(
				subscription.getSubscriptionId());
		}
	}

	protected void processSubscription(
			Subscription subscription, long groupId, long[] groupIds)
		throws PortalException, SystemException {

		String className = subscription.getClassName();

		if (className.equals(Layout.class.getName())) {
			processLayout(subscription, groupId, groupIds);
		}
		else if (className.equals(MBCategory.class.getName())) {
			processMBCategory(subscription, groupId, groupIds);
		}
		else if (className.equals(MBThread.class.getName())) {
			processMBThread(subscription, groupId, groupIds);
		}
		else if (className.equals(WikiNode.class.getName())) {
			processWikiNode(subscription, groupId, groupIds);
		}
		else if (className.equals(WorkflowInstance.class.getName())) {
			processWorkflowInstance(subscription, groupId, groupIds);
		}
		else {
			processAssetEntry(subscription, groupId, groupIds);
		}
	}

	protected void processUser(User user, long groupId)
		throws PortalException, SystemException {

		// Get the list of groups the current user is still a member of and
		// verify that subscriptions outside those groups are automatically
		// removed as well.

		List<Group> groups = user.getMySiteGroups(true, QueryUtil.ALL_POS);

		long[] groupIds = getGroupIds(groups);

		List<Subscription> subscriptions =
			SubscriptionLocalServiceUtil.getUserSubscriptions(
				user.getUserId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (Subscription subscription : subscriptions) {
			try {
				processSubscription(subscription, groupId, groupIds);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					StringBundler sb = new StringBundler(9);

					sb.append("Subscription was not removed for {className=");
					sb.append(subscription.getClassName());
					sb.append(", classPK=");
					sb.append(subscription.getClassPK());
					sb.append(", userId=");
					sb.append(subscription.getUserId());
					sb.append(", groupId=");
					sb.append(groupId);
					sb.append("}");

					_log.warn(sb.toString());
				}
			}
		}
	}

	protected void processWorkflowInstance(
			Subscription subscription, long groupId, long[] groupIds)
		throws PortalException, SystemException {

		WorkflowInstance workflowInstance =
			WorkflowInstanceManagerUtil.getWorkflowInstance(
				subscription.getCompanyId(), subscription.getClassPK());

		Map<String, Serializable> workflowContext =
			workflowInstance.getWorkflowContext();

		long instanceGroupId = GetterUtil.getLong(
			(String)workflowContext.get(WorkflowConstants.CONTEXT_GROUP_ID));

		if ((instanceGroupId > 0) &&
			((instanceGroupId == groupId) ||
			 !ArrayUtil.contains(groupIds, instanceGroupId))) {

			SubscriptionLocalServiceUtil.deleteSubscription(
				subscription.getSubscriptionId());
		}
	}

	protected void processWikiNode(
			Subscription subscription, long groupId, long[] groupIds)
		throws PortalException, SystemException {

		WikiNode node = WikiNodeLocalServiceUtil.fetchWikiNode(
			subscription.getClassPK());

		if ((node != null) &&
			((node.getGroupId() == groupId) ||
			 !ArrayUtil.contains(groupIds, node.getGroupId()))) {

			SubscriptionLocalServiceUtil.deleteSubscription(
				subscription.getSubscriptionId());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CleanUpSubscriptionMessageListener.class);

}