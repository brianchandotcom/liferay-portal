/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.concurrent;

/**
 * @author Shuyang Zhou
 */
public class MarkerThreadPoolHandler implements ThreadPoolHandler {

	@Override
	public void afterExecute(Runnable runnable, Throwable throwable) {
		_afterExecute = true;
	}

	@Override
	public void beforeExecute(Thread thread, Runnable runnable) {
		_beforeExecute = true;
	}

	@Override
	public void beforeThreadEnd(Thread thread) {
		_beforeThreadEnd = true;
	}

	@Override
	public void beforeThreadStart(Thread thread) {
		_beforeThreadStart = true;
	}

	public boolean isAfterExecuteRan() {
		return _afterExecute;
	}

	public boolean isBeforeExecuteRan() {
		return _beforeExecute;
	}

	public boolean isBeforeThreadEndRan() {
		return _beforeThreadEnd;
	}

	public boolean isBeforeThreadStartRan() {
		return _beforeThreadStart;
	}

	public boolean isTerminatedRan() {
		return _terminated;
	}

	@Override
	public void terminated() {
		_terminated = true;
	}

	private volatile boolean _afterExecute;
	private volatile boolean _beforeExecute;
	private volatile boolean _beforeThreadEnd;
	private volatile boolean _beforeThreadStart;
	private volatile boolean _terminated;

}