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

package com.liferay.portlet.wiki.subscription;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.model.User;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.service.WikiNodeLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	service = CleanUpSubscriptionMessageListener.class
)
public class CleanUpSubscriptionMessageListener extends BaseMessageListener {

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

	protected void processSubscription(
			Subscription subscription, long groupId, long[] groupIds)
		throws PortalException {

		String className = subscription.getClassName();

		if (className.equals(WikiNode.class.getName())) {
			processWikiNode(subscription, groupId, groupIds);
		}
	}

	protected void processUser(User user, long groupId) throws PortalException {

		// Get the list of groups the current user is still a member of and
		// verify that subscriptions outside those groups are automatically
		// removed as well

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
					StringBundler sb = new StringBundler(8);

					sb.append("Subscription was not removed for class name ");
					sb.append(subscription.getClassName());
					sb.append(" with class PK ");
					sb.append(subscription.getClassPK());
					sb.append(" in group ");
					sb.append(groupId);
					sb.append(" for user ");
					sb.append(subscription.getUserId());

					_log.warn(sb.toString());
				}
			}
		}
	}

	protected void processWikiNode(
			Subscription subscription, long groupId, long[] groupIds)
		throws PortalException {

		WikiNode wikiNode = _wiWikiNodeLocalService.fetchWikiNode(
			subscription.getClassPK());

		if ((wikiNode != null) &&
			((wikiNode.getGroupId() == groupId) ||
			 !ArrayUtil.contains(groupIds, wikiNode.getGroupId()))) {

			SubscriptionLocalServiceUtil.deleteSubscription(
				subscription.getSubscriptionId());
		}
	}

	@Activate
	protected void registerMessageListener() {
		_messageBus.registerMessageListener(
			DestinationNames.SUBSCRIPTION_CLEAN_UP, this);
	}

	@Reference
	protected void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	@Reference
	protected void setWikiNodeLocalService(
		WikiNodeLocalService wiWikiNodeLocalService) {

		_wiWikiNodeLocalService = wiWikiNodeLocalService;
	}

	private static Log _log = LogFactoryUtil.getLog(
		CleanUpSubscriptionMessageListener.class);

	private MessageBus _messageBus;
	private WikiNodeLocalService _wiWikiNodeLocalService;

}