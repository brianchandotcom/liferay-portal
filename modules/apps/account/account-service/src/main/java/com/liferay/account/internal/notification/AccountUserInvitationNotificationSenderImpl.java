/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.notification;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.notification.AccountUserInvitationNotificationSender;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.notification.context.NotificationContextBuilder;
import com.liferay.notification.exception.NoSuchNotificationTemplateException;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.notification.type.NotificationType;
import com.liferay.notification.type.NotificationTypeServiceTracker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.HashMapBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(service = AccountUserInvitationNotificationSender.class)
public class AccountUserInvitationNotificationSenderImpl
	implements AccountUserInvitationNotificationSender {

	@Override
	public void sendNotification(
			long accountEntryId, String callbackURL, String emailAddress,
			String notificationTemplateExternalReferenceCode, User user)
		throws PortalException {

		NotificationTemplate notificationTemplate =
			_notificationTemplateLocalService.
				fetchNotificationTemplateByExternalReferenceCode(
					notificationTemplateExternalReferenceCode,
					user.getCompanyId());

		if (notificationTemplate == null) {
			throw new NoSuchNotificationTemplateException(
				"No notification template found with external reference code " +
					notificationTemplateExternalReferenceCode);
		}

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			accountEntryId);

		NotificationType notificationType =
			_notificationTypeServiceTracker.getNotificationType(
				notificationTemplate.getType());

		notificationType.sendNotification(
			new NotificationContextBuilder(
			).className(
				AccountEntry.class.getName()
			).classPK(
				accountEntryId
			).companyId(
				user.getCompanyId()
			).notificationTemplate(
				notificationTemplate
			).termValues(
				HashMapBuilder.<String, Object>put(
					"[%ACCOUNT_NAME%]", accountEntry.getName()
				).put(
					"[%CREATE_ACCOUNT_URL%]", callbackURL
				).put(
					"[%INVITE_SENDER_NAME%]", user.getFullName()
				).put(
					"[%TO%]", emailAddress
				).put(
					"currentUserId", user.getUserId()
				).build()
			).userId(
				user.getUserId()
			).build());
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private NotificationTemplateLocalService _notificationTemplateLocalService;

	@Reference
	private NotificationTypeServiceTracker _notificationTypeServiceTracker;

}