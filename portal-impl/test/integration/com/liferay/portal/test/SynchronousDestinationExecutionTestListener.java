/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.test;

import com.liferay.portal.kernel.annotation.AnnotationLocator;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.kernel.test.AbstractExecutionTestListener;
import com.liferay.portal.kernel.test.TestContext;

import java.lang.reflect.Method;

/**
 * @author Miguel Pastor
 */
public class SynchronousDestinationExecutionTestListener
	extends AbstractExecutionTestListener {

	@Override
	public void runAfterClass(TestContext testContext) {
		_classSyncStatus.restorePreviousSyncMode();
	}

	@Override
	public void runAfterTest(TestContext testContext) {
		_methodSyncStatus.restorePreviousSyncMode();
	}

	@Override
	public void runBeforeClass(TestContext testContext) {
		Class<?> testClass = testContext.getClazz();

		Sync sync = AnnotationLocator.locate(testClass, Sync.class);

		_classSyncStatus.setSync(sync);
		_classSyncStatus.setStatus(ProxyModeThreadLocal.isForceSync());

		_classSyncStatus.enableSyncMode();
	}

	@Override
	public void runBeforeTest(TestContext testContext) {
		Method method = testContext.getMethod();
		Class<?> testClass = testContext.getClazz();

		Sync sync = AnnotationLocator.locate(method, testClass, Sync.class);

		_methodSyncStatus.setSync(sync);
		_methodSyncStatus.setStatus(ProxyModeThreadLocal.isForceSync());

		_methodSyncStatus.enableSyncMode();
	}

	private SyncStatus _classSyncStatus = new SyncStatus();
	private SyncStatus _methodSyncStatus = new SyncStatus();

	private class SyncStatus {
		public void enableSyncMode() {
			if (_sync != null) {
				ProxyModeThreadLocal.setForceSync(true);
			}
		}

		public void restorePreviousSyncMode() {
			if (_sync != null) {
				ProxyModeThreadLocal.setForceSync(_status);
			}
		}

		public void setStatus(boolean status) {
			_status = status;
		}

		public void setSync(Sync sync) {
			_sync = sync;
		}

		private boolean _status;
		private Sync _sync;

	}

}