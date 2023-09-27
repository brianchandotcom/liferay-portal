/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.lock;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Date;

/**
 * @author Zsolt Berentey
 */
public class LockProtectedAction<T> {

	public LockProtectedAction(
		Class<?> clazz, String lockKey, long timeout, long retryDelay) {

		_lockKey = lockKey;
		_timeout = timeout;
		_retryDelay = retryDelay;

		_className = clazz.getName();
	}

	public T getReturnValue() {
		return _returnValue;
	}

	public void performAction() throws PortalException {
		Lock lock = LockManagerUtil.lock(_className, _lockKey, _lockKey);

		if (lock.isNew()) {
			try {
				_returnValue = performProtectedAction();
			}
			finally {
				LockManagerUtil.unlock(_className, _lockKey, _lockKey);
			}

			return;
		}

		Date createDate = lock.getCreateDate();

		if ((System.currentTimeMillis() - createDate.getTime()) >= _timeout) {
			LockManagerUtil.unlock(_className, _lockKey, lock.getOwner());

			if (_log.isWarnEnabled()) {
				_log.warn("Removed lock " + lock + " due to timeout");
			}
		}
		else {
			try {
				Thread.sleep(_retryDelay);
			}
			catch (InterruptedException interruptedException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Interrupted while waiting to reacquire lock",
						interruptedException);
				}
			}
		}
	}

	protected T performProtectedAction() throws PortalException {
		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LockProtectedAction.class);

	private final String _className;
	private final String _lockKey;
	private final long _retryDelay;
	private T _returnValue;
	private final long _timeout;

}