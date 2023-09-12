/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.type;

import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationQueueEntryConstants;
import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.internal.type.users.provider.UsersProvider;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.type.BaseNotificationType;
import com.liferay.notification.type.NotificationType;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(service = NotificationType.class)
public class UserNotificationType extends BaseNotificationType {

	@Override
	public NotificationQueueEntry createNotificationQueueEntry(
		User user, String body, NotificationContext notificationContext,
		String subject) {

		NotificationQueueEntry notificationQueueEntry =
			super.createNotificationQueueEntry(
				user, body, notificationContext, subject);

		notificationQueueEntry.setStatus(
			NotificationQueueEntryConstants.STATUS_SENT);

		return notificationQueueEntry;
	}

	@Override
	public String getRecipientSummary(
		NotificationQueueEntry notificationQueueEntry) {

		NotificationRecipient notificationRecipient =
			notificationQueueEntry.getNotificationRecipient();

		List<String> values = new ArrayList<>();

		for (NotificationRecipientSetting notificationRecipientSetting :
				notificationRecipient.getNotificationRecipientSettings()) {

			values.add(notificationRecipientSetting.getValue());
		}

		return ListUtil.toString(
			values, (String)null, StringPool.COMMA_AND_SPACE);
	}

	@Override
	public String getType() {
		return NotificationConstants.TYPE_USER_NOTIFICATION;
	}

	@Override
	public String getTypeLanguageKey() {
		return "user-notification";
	}

	@Override
	public void sendNotification(NotificationContext notificationContext)
		throws PortalException {

		List<Map<String, String>> notificationRecipientSettings =
			new ArrayList<>();

		NotificationTemplate notificationTemplate =
			notificationContext.getNotificationTemplate();

		UsersProvider usersProvider = _serviceTrackerMap.getService(
			notificationTemplate.getRecipientType());

		for (User user : usersProvider.provide(notificationContext)) {
			if (!_objectEntryService.hasModelResourcePermission(
					user, notificationContext.getClassPK(), ActionKeys.VIEW)) {

				continue;
			}

			siteDefaultLocale = portal.getSiteDefaultLocale(user.getGroupId());
			userLocale = user.getLocale();

			_userNotificationEventLocalService.sendUserNotificationEvents(
				user.getUserId(), notificationContext.getPortletId(),
				UserNotificationDeliveryConstants.TYPE_WEBSITE,
				JSONUtil.put(
					"className", notificationContext.getClassName()
				).put(
					"classPK", notificationContext.getClassPK()
				).put(
					"externalReferenceCode",
					notificationContext.getExternalReferenceCode()
				).put(
					"notificationMessage",
					formatLocalizedContent(
						notificationTemplate.getSubjectMap(),
						notificationContext)
				).put(
					"portletId", notificationContext.getPortletId()
				));

			notificationRecipientSettings.add(
				HashMapBuilder.put(
					"userFullName", user.getFullName()
				).build());
		}

		User user = userLocalService.getUser(notificationContext.getUserId());

		siteDefaultLocale = portal.getSiteDefaultLocale(user.getGroupId());
		userLocale = user.getLocale();

		prepareNotificationContext(
			user, null, notificationContext, notificationRecipientSettings,
			formatLocalizedContent(
				notificationTemplate.getSubjectMap(), notificationContext));

		notificationQueueEntryLocalService.addNotificationQueueEntry(
			notificationContext);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, UsersProvider.class, "recipient.type");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Reference
	private ObjectEntryService _objectEntryService;

	private ServiceTrackerMap<String, UsersProvider> _serviceTrackerMap;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}