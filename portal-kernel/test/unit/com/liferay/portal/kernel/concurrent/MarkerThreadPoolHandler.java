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