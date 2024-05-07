/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.exception;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.DuplicateLockException;
import com.liferay.portal.kernel.lock.Lock;

/**
 * @author Marco Galluzzi
 */
public class LockedKBArticleException extends PortalException {

	public LockedKBArticleException() {
	}

	public LockedKBArticleException(String msg) {
		super(msg);
	}

	public LockedKBArticleException(String msg, Throwable throwable) {
		super(msg, throwable);

		_lock = _getLock(throwable);
	}

	public LockedKBArticleException(Throwable throwable) {
		super(throwable);

		_lock = _getLock(throwable);
	}

	public String getActionURL() {
		return _actionURL;
	}

	public String getCmd() {
		return _cmd;
	}

	public Lock getLock() {
		return _lock;
	}

	public String getUserName() {
		if (_lock != null) {
			return _lock.getUserName();
		}

		return null;
	}

	public void setActionURL(String actionURL) {
		_actionURL = actionURL;
	}

	public void setCmd(String cmd) {
		_cmd = cmd;
	}

	public void setLock(Lock lock) {
		_lock = lock;
	}

	private Lock _getLock(Throwable throwable) {
		if (throwable instanceof DuplicateLockException) {
			DuplicateLockException duplicateLockException =
				(DuplicateLockException)throwable;

			return duplicateLockException.getLock();
		}

		return null;
	}

	private String _actionURL;
	private String _cmd;
	private Lock _lock;

}