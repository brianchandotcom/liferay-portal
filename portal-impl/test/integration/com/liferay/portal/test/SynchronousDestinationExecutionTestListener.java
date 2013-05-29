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

package com.liferay.portal.test;

import com.liferay.portal.kernel.annotation.AnnotationLocator;
import com.liferay.portal.kernel.messaging.BaseDestination;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.kernel.test.AbstractExecutionTestListener;
import com.liferay.portal.kernel.test.TestContext;

import java.lang.reflect.Method;

import java.util.Collection;

/**
 * @author Miguel Pastor
 */
public class SynchronousDestinationExecutionTestListener
	extends AbstractExecutionTestListener {

	@Override
	public void runAfterClass(TestContext testContext) {
		_classSyncHandler.restorePreviousSync();
	}

	@Override
	public void runAfterTest(TestContext testContext) {
		_methodSyncHandler.restorePreviousSync();
	}

	@Override
	public void runBeforeClass(TestContext testContext) {
		Class<?> testClass = testContext.getClazz();

		Sync sync = AnnotationLocator.locate(testClass, Sync.class);

		_classSyncHandler.setSync(sync);
		_classSyncHandler.setForceSync(ProxyModeThreadLocal.isForceSync());

		_classSyncHandler.enableSync();
	}

	@Override
	public void runBeforeTest(TestContext testContext) {
		Method method = testContext.getMethod();
		Class<?> testClass = testContext.getClazz();

		Sync sync = AnnotationLocator.locate(method, testClass, Sync.class);

		_methodSyncHandler.setForceSync(ProxyModeThreadLocal.isForceSync());
		_methodSyncHandler.setSync(sync);

		_methodSyncHandler.enableSync();
	}

	private SyncHandler _classSyncHandler = new SyncHandler();
	private SyncHandler _methodSyncHandler = new SyncHandler();

	private class SyncHandler {

		public void enableSync() {
			if (_sync == null) {
				return;
			}

			ProxyModeThreadLocal.setForceSync(true);

			MessageBus messageBus = MessageBusUtil.getMessageBus();

			if (messageBus.hasDestination(_TARGET_ASYNC_DESTINATION_NAME)) {
				return;
			}

			_oldAsyncDestination = (BaseDestination)messageBus.getDestination(
				DestinationNames.ASYNC_SERVICE);

			MessageBusUtil.addDestination(
				new SynchronizedAsyncDestination(
					_TARGET_ASYNC_DESTINATION_NAME));

			_oldAsyncDestination.setName(_TARGET_ASYNC_DESTINATION_NAME);

			MessageBusUtil.addDestination(_oldAsyncDestination);
		}

		public void restorePreviousSync() {
			if (_sync == null) {
				return;
			}

			ProxyModeThreadLocal.setForceSync(_forceSync);

			MessageBus messageBus = MessageBusUtil.getMessageBus();

			if ((_oldAsyncDestination == null) ||
				!messageBus.hasDestination(_TARGET_ASYNC_DESTINATION_NAME)) {

				return;
			}

			Collection<Destination> destinations = messageBus.getDestinations();

			destinations.remove(_oldAsyncDestination);

			MessageBusUtil.removeDestination(DestinationNames.ASYNC_SERVICE);

			_oldAsyncDestination.setName(DestinationNames.ASYNC_SERVICE);

			MessageBusUtil.addDestination(_oldAsyncDestination);
		}

		public void setForceSync(boolean forceSync) {
			_forceSync = forceSync;
		}

		public void setSync(Sync sync) {
			_sync = sync;
		}

		private String _TARGET_ASYNC_DESTINATION_NAME =
			DestinationNames.ASYNC_SERVICE + "_orig";

		private boolean _forceSync;
		private BaseDestination _oldAsyncDestination;
		private Sync _sync;

	}

}