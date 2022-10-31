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

package com.liferay.notification.internal.type.users.provider;

import com.liferay.notification.constants.NotificationRecipientConstants;
import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.term.contributor.NotificationTermContributor;
import com.liferay.notification.term.contributor.NotificationTermContributorRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	immediate = true,
	property = "recipient.type=" + NotificationRecipientConstants.TYPE_TERM,
	service = UsersProvider.class
)
public class TermUsersProvider implements UsersProvider {

	@Override
	public List<User> provide(NotificationContext notificationContext)
		throws PortalException {

		NotificationTemplate notificationTemplate =
			notificationContext.getNotificationTemplate();

		NotificationRecipient notificationRecipient =
			notificationTemplate.getNotificationRecipient();

		List<String> screenNames = new ArrayList<>();
		List<String> terms = new ArrayList<>();

		for (NotificationRecipientSetting notificationRecipientSetting :
				notificationRecipient.getNotificationRecipientSettings()) {

			Matcher matcher = _pattern.matcher(
				notificationRecipientSetting.getValue());

			if (matcher.find()) {
				terms.add(notificationRecipientSetting.getValue());
			}
			else {
				screenNames.add(notificationRecipientSetting.getValue());
			}
		}

		List<User> users = new ArrayList<>();

		for (String screenName : screenNames) {
			users.add(
				_userLocalService.getUserByScreenName(
					notificationRecipient.getCompanyId(), screenName));
		}

		List<NotificationTermContributor> notificationTermContributors =
			_notificationTermContributorRegistry.
				getNotificationTermContributorsByNotificationTypeKey(
					notificationContext.getClassName());

		for (NotificationTermContributor notificationTermContributor :
				notificationTermContributors) {

			for (String term : terms) {
				users.add(
					_userLocalService.getUser(
						GetterUtil.getLong(
							notificationTermContributor.getTermValue(
								null, notificationContext.getTermValues(),
								term))));
			}
		}

		return users;
	}

	@Override
	public String recipientType() {
		return NotificationRecipientConstants.TYPE_TERM;
	}

	private static final Pattern _pattern = Pattern.compile(
		"\\[%[^\\[%]+%\\]", Pattern.CASE_INSENSITIVE);

	@Reference
	private NotificationTermContributorRegistry
		_notificationTermContributorRegistry;

	@Reference
	private UserLocalService _userLocalService;

}