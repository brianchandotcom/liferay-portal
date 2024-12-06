/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.internal.recommendation.messaging;

import com.liferay.analytics.machine.learning.internal.recommendation.constants.RecommendationConstants;
import com.liferay.analytics.machine.learning.internal.recommendation.constants.RecommendationDestinationNames;
import com.liferay.analytics.machine.learning.internal.recommendation.notifications.RecommendationNotificationType;
import com.liferay.dispatch.executor.DispatchTaskStatus;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.service.DispatchLogLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PortletKeys;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	property = "destination.name=" + RecommendationDestinationNames.NOTIFY_RECOMMENDATIONS_JOBS_STATUS_CHANGED,
	service = MessageListener.class
)
public class RecommendationJobsStatusChangeMessageListener
	extends BaseMessageListener {

	@Activate
	protected void activate(BundleContext bundleContext) {
		Destination destination = _destinationFactory.createDestination(
			DestinationConfiguration.createSerialDestinationConfiguration(
				RecommendationDestinationNames.
					NOTIFY_RECOMMENDATIONS_JOBS_STATUS_CHANGED));

		_serviceRegistration = bundleContext.registerService(
			Destination.class, destination,
			HashMapDictionaryBuilder.<String, Object>put(
				"destination.name", destination.getName()
			).build());
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		JSONObject jsonObject = _jsonFactory.createJSONObject(
			(String)message.getPayload());

		long dispatchTriggerId = jsonObject.getLong("dispatchTriggerId");

		DispatchLog dispatchLog =
			_dispatchLogLocalService.fetchLatestDispatchLog(dispatchTriggerId);

		if ((dispatchLog == null) ||
			(dispatchLog.getStatus() ==
				DispatchTaskStatus.IN_PROGRESS.getStatus())) {

			return;
		}

		try {
			_sendUserNotiticationEvent(
				dispatchLog.getCompanyId(),
				jsonObject.getInt("recommendationCode"),
				dispatchLog.getStatus());
		}
		catch (Throwable throwable) {
			throw new Exception(throwable);
		}
		finally {
			_schedulerEngineHelper.delete(
				jsonObject.getString("jobName"),
				jsonObject.getString("groupName"), StorageType.PERSISTED);
		}
	}

	private JSONObject _getNotificationEventJSONObject(
		int recommendationCode, int status) {

		JSONObject jsonObject = null;

		if (recommendationCode ==
				RecommendationConstants.
					MOST_VIEWED_CONTENT_RECOMMENDATION_CODE) {

			if (status == DispatchTaskStatus.SUCCESSFUL.getStatus()) {
				jsonObject = JSONUtil.put(
					"notificationTypeCode",
					RecommendationNotificationType.
						CONTENT_RECOMMENDER_MOST_POPULAR_ITEMS_ENABLED.
							getNotificationTypeCode());
			}
			else {
				jsonObject = JSONUtil.put(
					"notificationTypeCode",
					RecommendationNotificationType.
						CONTENT_RECOMMENDER_MOST_POPULAR_ITEMS_FAILED.
							getNotificationTypeCode());
			}
		}
		else if (recommendationCode ==
					RecommendationConstants.USER_CONTENT_RECOMMENDATION_CODE) {

			if (status == DispatchTaskStatus.SUCCESSFUL.getStatus()) {
				jsonObject = JSONUtil.put(
					"notificationTypeCode",
					RecommendationNotificationType.
						CONTENT_RECOMMENDER_USER_PERSONALIZATION_ENABLED.
							getNotificationTypeCode());
			}
			else {
				jsonObject = JSONUtil.put(
					"notificationTypeCode",
					RecommendationNotificationType.
						CONTENT_RECOMMENDER_USER_PERSONALIZATION_FAILED.
							getNotificationTypeCode());
			}
		}

		return jsonObject;
	}

	private void _sendUserNotiticationEvent(
			long companyId, int recommendationCode, int status)
		throws Throwable {

		Role role = _roleLocalService.getRole(
			companyId, RoleConstants.ADMINISTRATOR);

		long[] userIds = _userLocalService.getRoleUserIds(role.getRoleId());

		TransactionInvokerUtil.invoke(
			_transactionConfig,
			() -> {
				for (long userId : userIds) {
					_userNotificationEventLocalService.
						sendUserNotificationEvents(
							userId, PortletKeys.RECOMMENDATIONS,
							UserNotificationDeliveryConstants.TYPE_WEBSITE,
							_getNotificationEventJSONObject(
								recommendationCode, status));
				}

				return null;
			});
	}

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRES_NEW, new Class<?>[] {Exception.class});

	@Reference
	private DestinationFactory _destinationFactory;

	@Reference
	private DispatchLogLocalService _dispatchLogLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	private ServiceRegistration<Destination> _serviceRegistration;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}