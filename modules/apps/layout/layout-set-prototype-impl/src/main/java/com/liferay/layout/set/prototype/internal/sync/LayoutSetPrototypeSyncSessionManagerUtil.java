/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.set.prototype.internal.sync;

import com.liferay.layout.set.prototype.constants.LayoutSetPrototypeConstants;
import com.liferay.layout.set.prototype.constants.LayoutSetPrototypePortletKeys;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Carlos Correa
 */
public class LayoutSetPrototypeSyncSessionManagerUtil {

	public static final String KEY_LAYOUT_SET_GROUP_IDS = "layoutSetGroupIds";

	public static final String KEY_LAYOUT_SET_PROTOTYPE_ID =
		"layoutSetPrototypeId";

	public static final String KEY_NOTIFIED = "notified";

	public static final String KEY_SYNC_USER_ID = "syncUserId";

	public static void contribute(BackgroundTask backgroundTask) {
		SyncSessionContext syncSessionContext = _syncSessionContext.get();

		if (syncSessionContext == null) {
			return;
		}

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		taskContextMap.put(
			KEY_LAYOUT_SET_GROUP_IDS, syncSessionContext._layoutSetGroupIds);
		taskContextMap.put(
			KEY_LAYOUT_SET_PROTOTYPE_ID,
			syncSessionContext._layoutSetPrototypeId);
		taskContextMap.put(
			LayoutSetPrototypeConstants.KEY_SYNC_SESSION_ID,
			syncSessionContext._syncSessionId);
		taskContextMap.put(KEY_SYNC_USER_ID, syncSessionContext._userId);
	}

	public static SafeCloseable openSession(
		List<LayoutSet> layoutSets, LayoutSetPrototype layoutSetPrototype,
		long userId) {

		if (layoutSets.isEmpty()) {
			postNotification(
				Collections.singleton(
					BackgroundTaskConstants.STATUS_SUCCESSFUL),
				layoutSetPrototype.getNameMap(), userId);

			return () -> {
			};
		}

		_syncSessionContext.set(
			new SyncSessionContext(
				TransformUtil.transformToArray(
					layoutSets, LayoutSet::getGroupId, Long.class),
				layoutSetPrototype.getLayoutSetPrototypeId(),
				PortalUUIDUtil.generate(), userId));

		return _syncSessionContext::remove;
	}

	public static void postNotification(
		Set<Integer> backgroundTaskStatuses, Map<Locale, String> nameMap,
		long userId) {

		try {
			NotificationEvent notificationEvent = new NotificationEvent(
				System.currentTimeMillis(),
				LayoutSetPrototypePortletKeys.LAYOUT_SET_PROTOTYPE,
				JSONUtil.put(
					"layoutSetPrototypeNameMap", _toJSONObject(nameMap)
				).put(
					"result", _toResult(backgroundTaskStatuses)
				));

			notificationEvent.setDeliveryType(
				UserNotificationDeliveryConstants.TYPE_WEBSITE);

			UserNotificationEventLocalServiceUtil.addUserNotificationEvent(
				userId, notificationEvent);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to post sync completion notification for user " +
					userId,
				exception);
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
		if (backgroundTaskStatuses.contains(
				BackgroundTaskConstants.STATUS_CANCELLED) ||
			backgroundTaskStatuses.contains(
				BackgroundTaskConstants.STATUS_FAILED)) {

			return LayoutSetPrototypeConstants.STATUS_FAILED;
		}

		if (backgroundTaskStatuses.contains(
				BackgroundTaskConstants.STATUS_COMPLETED_WITH_ERRORS)) {

			return LayoutSetPrototypeConstants.STATUS_COMPLETED_WITH_ERRORS;
		}

		return LayoutSetPrototypeConstants.STATUS_SUCCESSFUL;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetPrototypeSyncSessionManagerUtil.class);

	private static final ThreadLocal<SyncSessionContext> _syncSessionContext =
		new CentralizedThreadLocal<>(
			LayoutSetPrototypeSyncSessionManagerUtil.class +
				"._syncSessionContext",
			() -> null);

	private static class SyncSessionContext {

		private SyncSessionContext(
			Long[] layoutSetGroupIds, long layoutSetPrototypeId,
			String syncSessionId, long userId) {

			_layoutSetGroupIds = layoutSetGroupIds;
			_layoutSetPrototypeId = layoutSetPrototypeId;
			_syncSessionId = syncSessionId;
			_userId = userId;
		}

		private final Long[] _layoutSetGroupIds;
		private final long _layoutSetPrototypeId;
		private final String _syncSessionId;
		private final long _userId;

	}

}