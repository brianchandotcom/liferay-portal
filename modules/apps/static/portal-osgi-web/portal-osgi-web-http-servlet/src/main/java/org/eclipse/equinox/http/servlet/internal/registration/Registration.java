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
package org.eclipse.equinox.http.servlet.internal.registration;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.osgi.dto.DTO;

public abstract class Registration<T, D extends DTO> {

	private final D d;
	private final T t;

	protected final AtomicInteger referenceCount = new AtomicInteger();

	public Registration(T t, D d) {
		this.t = t;
		this.d = d;
	}

	public void addReference() {
		readLock.lock();

		try {
			referenceCount.incrementAndGet();
		}
		finally {
			readLock.unlock();
		}
	}

	public void removeReference() {
		readLock.lock();

		try {
			if (referenceCount.decrementAndGet() == 0 && destroyed) {
				readLock.unlock();

				writeLock.lock();

				try {
					condition.signalAll();
				}
				finally {
					writeLock.unlock();

					readLock.lock();
				}
			}
		}
		finally {
			readLock.unlock();
		}
	}

	public void destroy() {
		boolean interrupted = false;

		writeLock.lock();

		destroyed = true;

		try {
			while (referenceCount.get() != 0) {
				try {
					condition.await();
				}
				catch (InterruptedException ie) {
					interrupted = true;
				}
			}
		}
		finally {
			writeLock.unlock();

			if (interrupted) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private volatile boolean destroyed;

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	private final Lock readLock = readWriteLock.readLock();
	private final Lock writeLock = readWriteLock.writeLock();
	private final Condition condition = writeLock.newCondition();

	public D getD() {
		return d;
	}

	public T getT() {
		return t;
	}

	@Override
	public String toString() {
		return getD().toString();
	}

}
