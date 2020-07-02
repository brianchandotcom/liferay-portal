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

package com.liferay.blogs.internal.util;

import com.liferay.blogs.configuration.BlogsGroupServiceConfiguration;
import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.GroupSubscriptionCheckSubscriptionSender;

/**
 * @author István András Dézsi
 */
public class BlogsSubscriptionSender
	extends GroupSubscriptionCheckSubscriptionSender {

	public BlogsSubscriptionSender(String resourceName) {
		super(resourceName);
	}

	@Override
	protected void sendNotification(User user, boolean notifyImmediately)
		throws Exception {

		BlogsGroupServiceConfiguration blogsGroupServiceConfiguration =
			_getBlogsGroupServiceConfiguration(user.getGroupId());

		if ((currentUserId == user.getUserId()) &&
			!blogsGroupServiceConfiguration.
				sendNotificationsToBlogsEntryCreator()) {

			if (_log.isDebugEnabled()) {
				_log.debug("Skip user " + currentUserId);
			}

			return;
		}

		if (notifyImmediately) {
			sendEmailNotification(user);
		}

		sendUserNotification(user, notifyImmediately);
	}

	private BlogsGroupServiceConfiguration _getBlogsGroupServiceConfiguration(
			long groupId)
		throws Exception {

		return ConfigurationProviderUtil.getConfiguration(
			BlogsGroupServiceConfiguration.class,
			new GroupServiceSettingsLocator(
				groupId, BlogsConstants.SERVICE_NAME));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsSubscriptionSender.class);

}