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

package com.liferay.portal.workflow.kaleo.runtime.internal.helper;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.kaleo.definition.ExecutionType;
import com.liferay.portal.workflow.kaleo.model.KaleoNotification;
import com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.notification.NotificationHelper;
import com.liferay.portal.workflow.kaleo.runtime.notification.NotificationMessageGenerator;
import com.liferay.portal.workflow.kaleo.runtime.notification.NotificationSender;
import com.liferay.portal.workflow.kaleo.service.KaleoNotificationLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNotificationRecipientLocalService;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = NotificationHelper.class)
public class NotificationHelperImpl implements NotificationHelper {

	@Override
	public void sendKaleoNotifications(
			String kaleoClassName, long kaleoClassPK,
			ExecutionType executionType, ExecutionContext executionContext)
		throws PortalException {

		List<KaleoNotification> kaleoNotifications =
			_kaleoNotificationLocalService.getKaleoNotifications(
				kaleoClassName, kaleoClassPK, executionType.getValue());

		for (KaleoNotification kaleoNotification : kaleoNotifications) {
			_sendKaleoNotification(kaleoNotification, executionContext);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_notificationSenderServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, NotificationSender.class, null,
				ServiceReferenceMapperFactory.create(
					bundleContext,
					(notificationSender, emitter) -> emitter.emit(
						notificationSender.getNotificationType())));
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, NotificationMessageGenerator.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(notificationMessageGenerator, emitter) -> {
					for (String templateLanguage :
							notificationMessageGenerator.
								getTemplateLanguages()) {

						emitter.emit(templateLanguage);
					}
				}));
	}

	@Deactivate
	protected void deactivate() {
		_notificationSenderServiceTrackerMap.close();

		_serviceTrackerMap.close();
	}

	private NotificationMessageGenerator _getNotificationMessageGenerator(
			String templateLanguage)
		throws WorkflowException {

		NotificationMessageGenerator notificationMessageGenerator =
			_serviceTrackerMap.getService(templateLanguage);

		if (notificationMessageGenerator == null) {
			throw new WorkflowException(
				"Invalid template language " + templateLanguage);
		}

		return notificationMessageGenerator;
	}

	private NotificationSender _getNotificationSender(String notificationType)
		throws WorkflowException {

		NotificationSender notificationSender =
			_notificationSenderServiceTrackerMap.getService(notificationType);

		if (notificationSender == null) {
			throw new WorkflowException(
				"Invalid notification type " + notificationType);
		}

		return notificationSender;
	}

	private void _sendKaleoNotification(
			KaleoNotification kaleoNotification,
			ExecutionContext executionContext)
		throws PortalException {

		NotificationMessageGenerator notificationMessageGenerator =
			_getNotificationMessageGenerator(
				kaleoNotification.getTemplateLanguage());

		String notificationMessage =
			notificationMessageGenerator.generateMessage(
				kaleoNotification.getKaleoClassName(),
				kaleoNotification.getKaleoClassPK(),
				kaleoNotification.getName(),
				kaleoNotification.getTemplateLanguage(),
				kaleoNotification.getTemplate(), executionContext);

		String notificationSubject = StringPool.BLANK;

		if (Validator.isNotNull(kaleoNotification.getDescription())) {
			notificationSubject = notificationMessageGenerator.generateMessage(
				kaleoNotification.getKaleoClassName(),
				kaleoNotification.getKaleoClassPK(),
				kaleoNotification.getName(),
				kaleoNotification.getTemplateLanguage(),
				kaleoNotification.getDescription(), executionContext);
		}

		String[] notificationTypes = StringUtil.split(
			kaleoNotification.getNotificationTypes());

		List<KaleoNotificationRecipient> kaleoNotificationRecipient =
			_kaleoNotificationRecipientLocalService.
				getKaleoNotificationRecipients(
					kaleoNotification.getKaleoNotificationId());

		for (String notificationType : notificationTypes) {
			NotificationSender notificationSender = _getNotificationSender(
				notificationType);

			notificationSender.sendNotification(
				kaleoNotificationRecipient, notificationSubject,
				notificationMessage, executionContext);
		}
	}

	@Reference
	private KaleoNotificationLocalService _kaleoNotificationLocalService;

	@Reference
	private KaleoNotificationRecipientLocalService
		_kaleoNotificationRecipientLocalService;

	private ServiceTrackerMap<String, NotificationSender>
		_notificationSenderServiceTrackerMap;
	private ServiceTrackerMap<String, NotificationMessageGenerator>
		_serviceTrackerMap;

}