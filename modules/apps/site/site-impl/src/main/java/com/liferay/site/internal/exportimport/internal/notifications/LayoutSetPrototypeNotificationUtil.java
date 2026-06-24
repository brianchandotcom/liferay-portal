/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.internal.exportimport.internal.notifications;

import com.liferay.layout.set.prototype.constants.LayoutSetPrototypePortletKeys;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Carlos Correa
 */
public class LayoutSetPrototypeNotificationUtil {

	public static final String NOTIFIED = "notified";

	public static final String STATUS_COMPLETED_WITH_ERRORS =
		"completed-with-errors";

	public static final String STATUS_FAILED = "failed";

	public static final String STATUS_SUCCESSFUL = "successful";

	public static void sendMergeCompletedNotification(
		Set<Integer> backgroundTaskStatuses,
		LayoutSetPrototype layoutSetPrototype, long userId) {

		NotificationEvent notificationEvent = new NotificationEvent(
			System.currentTimeMillis(),
			LayoutSetPrototypePortletKeys.LAYOUT_SET_PROTOTYPE,
			JSONUtil.put(
				"layoutSetPrototypeNameMap",
				_toJSONObject(layoutSetPrototype.getNameMap())
			).put(
				"result", _toResult(backgroundTaskStatuses)
			));

		notificationEvent.setDeliveryType(
			UserNotificationDeliveryConstants.TYPE_WEBSITE);

		try {
			UserNotificationEventLocalServiceUtil.addUserNotificationEvent(
				userId, notificationEvent);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to post merge notification for user " + userId,
				portalException);
		}
	}

	private static JSONObject _toJSONObject(Map<Locale, String> nameMap) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (nameMap != null) {
			for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
				jsonObject.put(
					LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
			}
		}

		return jsonObject;
	}

	private static String _toResult(Set<Integer> backgroundTaskStatuses) {
		if (SetUtil.isEmpty(backgroundTaskStatuses)) {
			return STATUS_SUCCESSFUL;
		}

		if (backgroundTaskStatuses.contains(
				BackgroundTaskConstants.STATUS_FAILED)) {

			return STATUS_FAILED;
		}

		if (backgroundTaskStatuses.contains(
				BackgroundTaskConstants.STATUS_COMPLETED_WITH_ERRORS)) {

			return STATUS_COMPLETED_WITH_ERRORS;
		}

		return STATUS_SUCCESSFUL;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetPrototypeNotificationUtil.class);

}