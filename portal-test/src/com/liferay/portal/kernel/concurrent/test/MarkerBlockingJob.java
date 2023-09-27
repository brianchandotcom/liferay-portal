/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.concurrent.test;

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
			catch (InterruptedException interruptedException) {
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