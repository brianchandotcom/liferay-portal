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

package com.liferay.portal.test;

import com.liferay.portal.kernel.annotation.AnnotationLocator;
import com.liferay.portal.kernel.messaging.BaseAsyncDestination;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.SynchronousDestination;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.kernel.test.AbstractExecutionTestListener;
import com.liferay.portal.kernel.test.TestContext;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Pastor
 */
public class SynchronousDestinationExecutionTestListener
	extends AbstractExecutionTestListener {

	@Override
	public void runAfterClass(TestContext testContext) {
		classSyncHandler.restorePreviousSync();
	}

	@Override
	public void runAfterTest(TestContext testContext) {
		methodSyncHandler.restorePreviousSync();
	}

	@Override
	public void runBeforeClass(TestContext testContext) {
		Class<?> testClass = testContext.getClazz();

		Sync sync = AnnotationLocator.locate(testClass, Sync.class);

		classSyncHandler.setSync(sync);
		classSyncHandler.setForceSync(ProxyModeThreadLocal.isForceSync());

		classSyncHandler.enableSync();
	}

	@Override
	public void runBeforeTest(TestContext testContext) {
		Method method = testContext.getMethod();
		Class<?> testClass = testContext.getClazz();

		Sync sync = AnnotationLocator.locate(method, testClass, Sync.class);

		methodSyncHandler.setForceSync(ProxyModeThreadLocal.isForceSync());
		methodSyncHandler.setSync(sync);

		methodSyncHandler.enableSync();
	}

	protected SyncHandler classSyncHandler = new SyncHandler();
	protected SyncHandler methodSyncHandler = new SyncHandler();

	protected class SyncHandler {

		public void enableSync() {
			if (_sync == null) {
				return;
			}

			ProxyModeThreadLocal.setForceSync(true);

			replaceDestination(DestinationNames.ASYNC_SERVICE);
			replaceDestination(DestinationNames.BACKGROUND_TASK);
			replaceDestination(
				DestinationNames.DOCUMENT_LIBRARY_RAW_METADATA_PROCESSOR);
			replaceDestination(
				DestinationNames.DOCUMENT_LIBRARY_SYNC_EVENT_PROCESSOR);
			replaceDestination(DestinationNames.MAIL);
			replaceDestination(DestinationNames.SUBSCRIPTION_SENDER);
		}

		protected void replaceDestination(String destinationName) {
			MessageBus messageBus = MessageBusUtil.getMessageBus();

			Destination destination = messageBus.getDestination(
				destinationName);

			if (destination instanceof BaseAsyncDestination) {
				_asyncServiceDestinations.add(destination);

				SynchronousDestination synchronousDestination =
					new SynchronousDestination();

				synchronousDestination.setName(destinationName);

				messageBus.replace(synchronousDestination);
			}

			if (destination == null) {
				_absentDestinationNames.add(destinationName);

				SynchronousDestination synchronousDestination =
					new SynchronousDestination();

				synchronousDestination.setName(destinationName);

				messageBus.addDestination(synchronousDestination);
			}
		}

		public void restorePreviousSync() {
			if (_sync == null) {
				return;
			}

			ProxyModeThreadLocal.setForceSync(_forceSync);

			MessageBus messageBus = MessageBusUtil.getMessageBus();

			for (Destination destination : _asyncServiceDestinations) {
				messageBus.replace(destination);
			}

			_asyncServiceDestinations.clear();

			for (String absentDestinationName : _absentDestinationNames) {
				messageBus.removeDestination(absentDestinationName);
			}
		}

		public void setForceSync(boolean forceSync) {
			_forceSync = forceSync;
		}

		public void setSync(Sync sync) {
			_sync = sync;
		}

		private List<String> _absentDestinationNames = new ArrayList<String>();
		private List<Destination> _asyncServiceDestinations =
			new ArrayList<Destination>();
		private boolean _forceSync;
		private Sync _sync;

	}

}