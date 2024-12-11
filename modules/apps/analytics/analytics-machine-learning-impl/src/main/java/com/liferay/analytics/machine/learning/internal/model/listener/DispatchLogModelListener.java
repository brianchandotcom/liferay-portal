/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.internal.model.listener;

import com.liferay.analytics.machine.learning.internal.recommendation.notifications.RecommendationNotificationType;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.dispatch.executor.DispatchTaskStatus;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchLogLocalService;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(service = ModelListener.class)
public class DispatchLogModelListener extends BaseModelListener<DispatchLog> {

	@Override
	public void onAfterUpdate(
		DispatchLog originalDispatchLog, DispatchLog dispatchLog) {

		DispatchTrigger dispatchTrigger =
			_dispatchTriggerLocalService.fetchDispatchTrigger(
				dispatchLog.getDispatchTriggerId());

		if (!StringUtil.equals(
				dispatchTrigger.getName(),
				"analytics-download-most-viewed-content-recommendation") &&
			!StringUtil.equals(
				dispatchTrigger.getName(),
				"analytics-download-user-content-recommendation")) {

			return;
		}

		int dispatchLogsCount = _dispatchLogLocalService.getDispatchLogsCount(
			dispatchLog.getDispatchTriggerId());

		if (dispatchLogsCount > 1) {
			return;
		}

		try {
			if (dispatchLog.getStatus() ==
					DispatchTaskStatus.FAILED.getStatus()) {

				Map<String, Object> map = new HashMap<>();

				if (StringUtil.equals(
						dispatchTrigger.getName(),
						"analytics-download-most-viewed-content-" +
							"recommendation")) {

					map.put("contentRecommenderMostPopularItemsEnabled", false);
				}
				else if (StringUtil.equals(
							dispatchTrigger.getName(),
							"analytics-download-user-content-recommendation")) {

					map.put(
						"contentRecommenderUserPersonalizationEnabled", false);
				}

				if (!map.isEmpty()) {
					_analyticsSettingsManager.updateCompanyConfiguration(
						dispatchLog.getCompanyId(), map);
				}
			}

			_sendUserNotiticationEvent(
				dispatchLog.getCompanyId(), dispatchTrigger.getName(),
				dispatchLog.getStatus());
		}
		catch (Throwable throwable) {
			_log.error(throwable);
		}
	}

	private JSONObject _getNotificationEventJSONObject(
		String name, int status) {

		JSONObject jsonObject = null;

		if (StringUtil.equals(
				name,
				"analytics-download-most-viewed-content-recommendation")) {

			if (status == DispatchTaskStatus.SUCCESSFUL.getStatus()) {
				jsonObject = JSONUtil.put(
					"notificationTypeCode",
					RecommendationNotificationType.
						CONTENT_RECOMMENDER_MOST_POPULAR_ITEMS_ENABLED.
							getNotificationTypeCode());
			}
			else if (status == DispatchTaskStatus.FAILED.getStatus()) {
				jsonObject = JSONUtil.put(
					"notificationTypeCode",
					RecommendationNotificationType.
						CONTENT_RECOMMENDER_MOST_POPULAR_ITEMS_FAILED.
							getNotificationTypeCode());
			}
		}
		else if (StringUtil.equals(
					name, "analytics-download-user-content-recommendation")) {

			if (status == DispatchTaskStatus.SUCCESSFUL.getStatus()) {
				jsonObject = JSONUtil.put(
					"notificationTypeCode",
					RecommendationNotificationType.
						CONTENT_RECOMMENDER_USER_PERSONALIZATION_ENABLED.
							getNotificationTypeCode());
			}
			else if (status == DispatchTaskStatus.FAILED.getStatus()) {
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
			long companyId, String name, int status)
		throws Throwable {

		JSONObject notificationEventJSONObject =
			_getNotificationEventJSONObject(name, status);

		if (notificationEventJSONObject == null) {
			return;
		}

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
							notificationEventJSONObject);
				}

				return null;
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DispatchLogModelListener.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRES_NEW, new Class<?>[] {Exception.class});

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Reference
	private DispatchLogLocalService _dispatchLogLocalService;

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}