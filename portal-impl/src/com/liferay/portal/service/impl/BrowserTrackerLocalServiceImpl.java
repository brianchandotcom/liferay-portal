/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BrowserTracker;
import com.liferay.portal.service.base.BrowserTrackerLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class BrowserTrackerLocalServiceImpl
	extends BrowserTrackerLocalServiceBaseImpl {

	@Override
	public void deleteUserBrowserTracker(long userId) {
		BrowserTracker browserTracker = browserTrackerPersistence.fetchByUserId(
			userId);

		if (browserTracker != null) {
			browserTrackerPersistence.remove(browserTracker);
		}
	}

	@Override
	public BrowserTracker getBrowserTracker(long userId, long browserKey) {
		BrowserTracker browserTracker = browserTrackerPersistence.fetchByUserId(
			userId);

		if (browserTracker == null) {
			browserTracker = browserTrackerLocalService.updateBrowserTracker(
				userId, browserKey);
		}

		return browserTracker;
	}

	@Override
	public BrowserTracker updateBrowserTracker(long userId, long browserKey) {
		BrowserTracker browserTracker = browserTrackerPersistence.fetchByUserId(
			userId);

		if (browserTracker == null) {
			long browserTrackerId = counterLocalService.increment();

			browserTracker = browserTrackerPersistence.create(browserTrackerId);

			browserTracker.setUserId(userId);
		}

		browserTracker.setBrowserKey(browserKey);

		try {
			browserTracker = browserTrackerPersistence.update(browserTracker);
		}
		catch (SystemException systemException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Add failed, fetch {userId=" + userId + "}");
			}

			browserTracker = browserTrackerPersistence.fetchByUserId(
				userId, false);

			if (browserTracker == null) {
				throw systemException;
			}
		}

		return browserTracker;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BrowserTrackerLocalServiceImpl.class);

}