/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.workflow.kaleo.definition.ExecutionType;
import com.liferay.portal.workflow.kaleo.definition.Notification;
import com.liferay.portal.workflow.kaleo.definition.NotificationReceptionType;
import com.liferay.portal.workflow.kaleo.definition.NotificationType;
import com.liferay.portal.workflow.kaleo.definition.Recipient;
import com.liferay.portal.workflow.kaleo.definition.TemplateLanguage;
import com.liferay.portal.workflow.kaleo.model.KaleoNotification;
import com.liferay.portal.workflow.kaleo.service.KaleoNotificationRecipientLocalService;
import com.liferay.portal.workflow.kaleo.service.base.KaleoNotificationLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.portal.workflow.kaleo.model.KaleoNotification",
	service = AopService.class
)
public class KaleoNotificationLocalServiceImpl
	extends KaleoNotificationLocalServiceBaseImpl {

	@Override
	public KaleoNotification addKaleoNotification(
			String kaleoClassName, long kaleoClassPK, long kaleoDefinitionId,
			long kaleoDefinitionVersionId, String kaleoNodeName,
			Notification notification, ServiceContext serviceContext)
		throws PortalException {

		// Kaleo notification

		User user = _userLocalService.getUser(
			serviceContext.getGuestOrUserId());
		Date date = new Date();

		long kaleoNotificationId = counterLocalService.increment();

		KaleoNotification kaleoNotification =
			kaleoNotificationPersistence.create(kaleoNotificationId);

		kaleoNotification.setCompanyId(user.getCompanyId());
		kaleoNotification.setUserId(user.getUserId());
		kaleoNotification.setUserName(user.getFullName());
		kaleoNotification.setCreateDate(date);
		kaleoNotification.setModifiedDate(date);
		kaleoNotification.setKaleoClassName(kaleoClassName);
		kaleoNotification.setKaleoClassPK(kaleoClassPK);
		kaleoNotification.setKaleoDefinitionId(kaleoDefinitionId);
		kaleoNotification.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
		kaleoNotification.setKaleoNodeName(kaleoNodeName);
		kaleoNotification.setName(notification.getName());
		kaleoNotification.setDescription(notification.getDescription());

		ExecutionType executionType = notification.getExecutionType();

		kaleoNotification.setExecutionType(executionType.getValue());

		kaleoNotification.setTemplate(notification.getTemplate());

		TemplateLanguage templateLanguage = notification.getTemplateLanguage();

		kaleoNotification.setTemplateLanguage(templateLanguage.getValue());

		Set<NotificationType> notificationTypes =
			notification.getNotificationTypes();

		if (!notificationTypes.isEmpty()) {
			StringBundler sb = new StringBundler(notificationTypes.size() * 2);

			for (NotificationType notificationType : notificationTypes) {
				sb.append(notificationType.getValue());
				sb.append(StringPool.COMMA);
			}

			sb.setIndex(sb.index() - 1);

			kaleoNotification.setNotificationTypes(sb.toString());
		}

		kaleoNotification = kaleoNotificationPersistence.update(
			kaleoNotification);

		// Kaleo notification recipients

		Map<NotificationReceptionType, Set<Recipient>> recipientsMap =
			notification.getRecipientsMap();

		for (Set<Recipient> recipients : recipientsMap.values()) {
			for (Recipient recipient : recipients) {
				_kaleoNotificationRecipientLocalService.
					addKaleoNotificationRecipient(
						kaleoDefinitionId, kaleoDefinitionVersionId,
						kaleoNotificationId, recipient, serviceContext);
			}
		}

		return kaleoNotification;
	}

	@Override
	public void deleteCompanyKaleoNotifications(long companyId) {

		// Kaleo notifications

		kaleoNotificationPersistence.removeByCompanyId(companyId);

		// Kaleo notification recipients

		_kaleoNotificationRecipientLocalService.
			deleteCompanyKaleoNotificationRecipients(companyId);
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoNotifications(
		long kaleoDefinitionVersionId) {

		// Kaleo notifications

		kaleoNotificationPersistence.removeByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);

		// Kaleo notification recipients

		_kaleoNotificationRecipientLocalService.
			deleteKaleoDefinitionVersionKaleoNotificationRecipients(
				kaleoDefinitionVersionId);
	}

	@Override
	public List<KaleoNotification> getKaleoNotifications(
		String kaleoClassName, long kaleoClassPK) {

		return kaleoNotificationPersistence.findByKCN_KCPK(
			kaleoClassName, kaleoClassPK);
	}

	@Override
	public List<KaleoNotification> getKaleoNotifications(
		String kaleoClassName, long kaleoClassPK, String executionType) {

		return kaleoNotificationPersistence.findByKCN_KCPK_ET(
			kaleoClassName, kaleoClassPK, executionType);
	}

	@Reference
	private KaleoNotificationRecipientLocalService
		_kaleoNotificationRecipientLocalService;

	@Reference
	private UserLocalService _userLocalService;

}