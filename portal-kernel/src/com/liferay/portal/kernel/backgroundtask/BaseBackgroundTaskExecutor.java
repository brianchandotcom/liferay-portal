/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Locale;

/**
 * @author Michael C. Han
 */
public abstract class BaseBackgroundTaskExecutor
	implements BackgroundTaskExecutor {

	@Override
	public abstract BackgroundTaskExecutor clone();

	@Override
	public String generateLockKey(BackgroundTask backgroundTask) {
		return backgroundTask.getTaskExecutorClassName() + StringPool.POUND +
			getIsolationLevel();
	}

	@Override
	public BackgroundTaskStatusMessageTranslator
		getBackgroundTaskStatusMessageTranslator() {

		return _backgroundTaskStatusMessageTranslator;
	}

	@Override
	public int getIsolationLevel() {
		return _isolationLevel;
	}

	@Override
	public String handleException(
		BackgroundTask backgroundTask, Exception exception) {

		return "Unable to execute background task: " + exception.getMessage();
	}

	@Override
	public boolean isSerial() {
		if (_isolationLevel ==
				BackgroundTaskConstants.ISOLATION_LEVEL_NOT_ISOLATED) {

			return false;
		}

		return true;
	}

	protected Locale getLocale(BackgroundTask backgroundTask) {
		long userId = MapUtil.getLong(
			backgroundTask.getTaskContextMap(), "userId");

		if (userId > 0) {
			try {
				User user = UserLocalServiceUtil.fetchUser(userId);

				if (user != null) {
					return user.getLocale();
				}
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to get the user's locale", exception);
				}
			}
		}

		return LocaleUtil.getDefault();
	}

	protected void setBackgroundTaskStatusMessageTranslator(
		BackgroundTaskStatusMessageTranslator
			backgroundTaskStatusMessageTranslator) {

		_backgroundTaskStatusMessageTranslator =
			backgroundTaskStatusMessageTranslator;
	}

	protected void setIsolationLevel(int isolationLevel) {
		_isolationLevel = isolationLevel;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseBackgroundTaskExecutor.class);

	private BackgroundTaskStatusMessageTranslator
		_backgroundTaskStatusMessageTranslator;
	private int _isolationLevel =
		BackgroundTaskConstants.ISOLATION_LEVEL_NOT_ISOLATED;

}