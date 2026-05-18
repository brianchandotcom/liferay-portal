/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.set.prototype.internal.sync;

import com.liferay.layout.set.prototype.constants.LayoutSetPrototypePortletKeys;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = LayoutSetPrototypeSyncSessionManager.class)
public class LayoutSetPrototypeSyncSessionManagerImpl
	implements LayoutSetPrototypeSyncSessionManager {

	@Override
	public void openSession(
		int expectedCount, String siteTemplateName, String syncSessionId,
		long userId) {

		if (expectedCount < 0) {
			return;
		}

		SyncSession syncSession = new SyncSession(
			expectedCount, siteTemplateName, userId);

		if (expectedCount == 0) {
			_postNotification(syncSessionId, syncSession);
		}
		else {
			_syncSessions.put(syncSessionId, syncSession);
		}
	}

	@Override
	public void recordBackgroundTaskStatus(
		int backgroundTaskStatus, String syncSessionId) {

		SyncSession syncSession = _syncSessions.get(syncSessionId);

		if (syncSession == null) {
			return;
		}

		syncSession._backgroundTaskStatuses.add(backgroundTaskStatus);

		if (syncSession._remaining.decrementAndGet() > 0) {
			return;
		}

		_syncSessions.remove(syncSessionId);

		_postNotification(syncSessionId, syncSession);
	}

	private void _postNotification(
		String syncSessionId, SyncSession syncSession) {

		try {
			Set<Integer> backgroundTaskStatuses =
				syncSession._backgroundTaskStatuses;

			String result;

			if (backgroundTaskStatuses.contains(
					BackgroundTaskConstants.STATUS_CANCELLED) ||
				backgroundTaskStatuses.contains(
					BackgroundTaskConstants.STATUS_FAILED)) {

				result = "failed";
			}
			else if (backgroundTaskStatuses.contains(
						BackgroundTaskConstants.STATUS_COMPLETED_WITH_ERRORS)) {

				result = "completed_with_errors";
			}
			else {
				result = "successful";
			}

			NotificationEvent notificationEvent = new NotificationEvent(
				System.currentTimeMillis(),
				LayoutSetPrototypePortletKeys.LAYOUT_SET_PROTOTYPE,
				JSONUtil.put(
					"result", result
				).put(
					"siteTemplateName", syncSession._siteTemplateName
				));

			notificationEvent.setDeliveryType(
				UserNotificationDeliveryConstants.TYPE_WEBSITE);

			_userNotificationEventLocalService.addUserNotificationEvent(
				syncSession._userId, notificationEvent);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to post sync completion notification for " +
					syncSessionId,
				exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetPrototypeSyncSessionManagerImpl.class);

	private final ConcurrentMap<String, SyncSession> _syncSessions =
		new ConcurrentHashMap<>();

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

	private static class SyncSession {

		private SyncSession(
			int expectedCount, String siteTemplateName, long userId) {

			_siteTemplateName = siteTemplateName;
			_userId = userId;

			_remaining = new AtomicInteger(expectedCount);
		}

		private final Set<Integer> _backgroundTaskStatuses =
			ConcurrentHashMap.newKeySet();
		private final AtomicInteger _remaining;
		private final String _siteTemplateName;
		private final long _userId;

	}

}