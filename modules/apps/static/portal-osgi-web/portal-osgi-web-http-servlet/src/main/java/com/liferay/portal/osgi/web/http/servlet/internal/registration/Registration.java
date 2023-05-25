/*******************************************************************************
 * Copyright (c) 2005, 2014 Cognos Incorporated, IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cognos Incorporated - initial API and implementation
 *     IBM Corporation - bug fixes and enhancements
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 *******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.registration;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.osgi.dto.DTO;

/**
 * @author Cognos Incorporated
 * @author IBM Corporation
 * @author Raymond Augé
 */
public abstract class Registration<T, D extends DTO> {

	public Registration(T t, D d) {
		_t = t;
		_d = d;

		ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

		_readLock = readWriteLock.readLock();

		_writeLock = readWriteLock.writeLock();

		_condition = _writeLock.newCondition();
	}

	public void addReference() {
		_readLock.lock();

		try {
			referenceCount.incrementAndGet();
		}
		finally {
			_readLock.unlock();
		}
	}

	public void destroy() {
		boolean interrupted = false;

		_writeLock.lock();

		_destroyed = true;

		try {
			while (referenceCount.get() != 0) {
				try {
					_condition.await();
				}
				catch (InterruptedException interruptedException) {
					if (_log.isDebugEnabled()) {
						_log.debug(interruptedException);
					}

					interrupted = true;
				}
			}
		}
		finally {
			_writeLock.unlock();

			if (interrupted) {
				Thread currentThread = Thread.currentThread();

				currentThread.interrupt();
			}
		}
	}

	public D getD() {
		return _d;
	}

	public T getT() {
		return _t;
	}

	public void removeReference() {
		_readLock.lock();

		try {
			if ((referenceCount.decrementAndGet() == 0) && _destroyed) {
				_readLock.unlock();

				_writeLock.lock();

				try {
					_condition.signalAll();
				}
				finally {
					_writeLock.unlock();

					_readLock.lock();
				}
			}
		}
		finally {
			_readLock.unlock();
		}
	}

	@Override
	public String toString() {
		return getD().toString();
	}

	protected final AtomicInteger referenceCount = new AtomicInteger();

	private static final Log _log = LogFactoryUtil.getLog(
		Registration.class.getName());

	private final Condition _condition;
	private final D _d;
	private volatile boolean _destroyed;
	private final Lock _readLock;
	private final T _t;
	private final Lock _writeLock;

}