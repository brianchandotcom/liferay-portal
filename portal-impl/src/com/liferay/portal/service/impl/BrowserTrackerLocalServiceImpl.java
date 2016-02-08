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
			browserTrackerPersistence.update(browserTracker);
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn("Add failed, fetch {userId=" + userId + "}");
			}

			browserTracker = browserTrackerPersistence.fetchByUserId(
				userId, false);

			if (browserTracker == null) {
				throw se;
			}
		}

		return browserTracker;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BrowserTrackerLocalServiceImpl.class);

}