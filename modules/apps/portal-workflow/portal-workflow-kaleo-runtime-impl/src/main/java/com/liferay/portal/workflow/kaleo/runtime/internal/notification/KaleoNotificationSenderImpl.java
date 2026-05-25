/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.internal.notification;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.kaleo.definition.ExecutionType;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoNotification;
import com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.notification.KaleoNotificationSender;
import com.liferay.portal.workflow.kaleo.runtime.notification.NotificationMessageGenerator;
import com.liferay.portal.workflow.kaleo.runtime.notification.NotificationSender;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNotificationLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNotificationRecipientLocalService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = KaleoNotificationSender.class)
public class KaleoNotificationSenderImpl implements KaleoNotificationSender {

	@Override
	public void sendNotifications(
			String kaleoClassName, long kaleoClassPK,
			ExecutionType executionType, ExecutionContext executionContext)
		throws PortalException {

		for (KaleoNotification kaleoNotification :
				_getKaleoNotifications(
					kaleoClassName, kaleoClassPK, executionType,
					executionContext)) {

			_sendKaleoNotification(kaleoNotification, executionContext);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_notificationMessageGeneratorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
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
		_notificationSenderServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, NotificationSender.class, null,
				ServiceReferenceMapperFactory.create(
					bundleContext,
					(notificationSender, emitter) -> emitter.emit(
						notificationSender.getNotificationType())));
	}

	@Deactivate
	protected void deactivate() {
		_notificationMessageGeneratorServiceTrackerMap.close();

		_notificationSenderServiceTrackerMap.close();
	}

	private List<KaleoNotification> _getKaleoNotifications(
		String kaleoClassName, long kaleoClassPK, ExecutionType executionType,
		ExecutionContext executionContext) {

		if (!Objects.equals(KaleoNode.class.getName(), kaleoClassName)) {
			return _kaleoNotificationLocalService.getKaleoNotifications(
				kaleoClassName, kaleoClassPK, executionType.getValue());
		}

		KaleoInstanceToken kaleoInstanceToken =
			executionContext.getKaleoInstanceToken();

		KaleoDefinitionVersion kaleoDefinitionVersion =
			_kaleoDefinitionVersionLocalService.fetchKaleoDefinitionVersion(
				kaleoInstanceToken.getKaleoDefinitionVersionId());

		if (kaleoDefinitionVersion == null) {
			return Collections.emptyList();
		}

		return ListUtil.filter(
			kaleoDefinitionVersion.getKaleoNodeKaleoNotifications(kaleoClassPK),
			kaleoNotification -> Objects.equals(
				executionType.getValue(),
				kaleoNotification.getExecutionType()));
	}

	private void _sendKaleoNotification(
			KaleoNotification kaleoNotification,
			ExecutionContext executionContext)
		throws PortalException {

		NotificationMessageGenerator notificationMessageGenerator =
			_notificationMessageGeneratorServiceTrackerMap.getService(
				kaleoNotification.getTemplateLanguage());

		if (notificationMessageGenerator == null) {
			throw new WorkflowException(
				"Invalid template language " +
					kaleoNotification.getTemplateLanguage());
		}

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
			NotificationSender notificationSender =
				_notificationSenderServiceTrackerMap.getService(
					notificationType);

			if (notificationSender == null) {
				throw new WorkflowException(
					"Invalid notification type " + notificationType);
			}

			notificationSender.sendNotification(
				kaleoNotificationRecipient, notificationSubject,
				notificationMessage, executionContext);
		}
	}

	@Reference
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	@Reference
	private KaleoNotificationLocalService _kaleoNotificationLocalService;

	@Reference
	private KaleoNotificationRecipientLocalService
		_kaleoNotificationRecipientLocalService;

	private ServiceTrackerMap<String, NotificationMessageGenerator>
		_notificationMessageGeneratorServiceTrackerMap;
	private ServiceTrackerMap<String, NotificationSender>
		_notificationSenderServiceTrackerMap;

}