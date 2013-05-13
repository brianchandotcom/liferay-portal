/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.util.concurrent.CountDownLatch;

/**
 * @author Shuyang Zhou
 */
public class MarkerBlockingJob implements Runnable {

	public MarkerBlockingJob() {
		this(false);
	}

	public MarkerBlockingJob(boolean blocking) {
		this(blocking, false);
	}

	public MarkerBlockingJob(boolean blocking, boolean throwException) {
		_blocking = blocking;
		_throwException = throwException;
	}

	public Thread getRunThread() {
		return _runThread;
	}

	public boolean isEnded() {
		return _ended;
	}

	public boolean isInterrupted() {
		return _interrupted;
	}

	public boolean isStarted() {
		return _started;
	}

	@Override
	public void run() {
		_runThread = Thread.currentThread();

		if (_started) {
			throw new IllegalStateException("Job already started");
		}

		_started = true;

		if (_blocking) {
			_waitBlockingLatch.countDown();

			try {
				_blockingLatch.await();
			}
			catch (InterruptedException ie) {
				_interrupted = true;
			}
		}

		if (_throwException) {
			throw new RuntimeException();
		}

		_ended = true;

		_endedLatch.countDown();
	}

	public void unBlock() {
		_blockingLatch.countDown();
	}

	public void waitUntilBlock() throws InterruptedException {
		if (!_blocking) {
			throw new IllegalStateException("Blocking is not enabled");
		}

		_waitBlockingLatch.await();
	}

	public void waitUntilEnded() throws InterruptedException {
		_endedLatch.await();
	}

	private final boolean _blocking;
	private final CountDownLatch _blockingLatch = new CountDownLatch(1);
	private volatile boolean _ended;
	private final CountDownLatch _endedLatch = new CountDownLatch(1);
	private volatile boolean _interrupted;
	private volatile Thread _runThread;
	private volatile boolean _started;
	private final boolean _throwException;
	private final CountDownLatch _waitBlockingLatch = new CountDownLatch(1);

}