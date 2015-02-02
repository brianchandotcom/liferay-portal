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

package com.liferay.portal.kernel.test.rule;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.BaseAsyncDestination;
import com.liferay.portal.kernel.messaging.BaseDestination;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.SynchronousDestination;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule.SyncHandler;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.junit.runner.Description;

/**
 * @author Miguel Pastor
 * @author Shuyang Zhou
 */
public class SynchronousDestinationTestRule
	extends BaseTestRule<SyncHandler, SyncHandler> {

	public static final SynchronousDestinationTestRule INSTANCE =
		new SynchronousDestinationTestRule();

	protected SynchronousDestinationTestRule() {
	}

	@Override
	protected void afterMethod(
		Description description, SyncHandler syncHandler) {

		syncHandler.restorePreviousSync();
	}

	@Override
	protected SyncHandler beforeMethod(Description description) {
		Sync sync = description.getAnnotation(Sync.class);

		if (sync == null) {
			Class<?> testClass = description.getTestClass();

			sync = testClass.getAnnotation(Sync.class);
		}

		SyncHandler syncHandler = new SyncHandler();

		syncHandler.setForceSync(ProxyModeThreadLocal.isForceSync());
		syncHandler.setSync(sync);

		syncHandler.enableSync();

		return syncHandler;
	}

	protected static class SyncHandler {

		public BaseDestination createSynchronousDestination(
			String destinationName) {

			SynchronousDestination synchronousDestination;

			if ((_sync != null) && _sync.cleanTransaction()) {
				synchronousDestination =
					new CleanTransactionSynchronousDestination();
			}
			else {
				synchronousDestination = new SynchronousDestination();
			}

			synchronousDestination.setName(destinationName);

			return synchronousDestination;
		}

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
			replaceDestination(DestinationNames.SEARCH_READER);
			replaceDestination(DestinationNames.SEARCH_WRITER);
			replaceDestination(DestinationNames.SUBSCRIPTION_SENDER);
		}

		public void replaceDestination(String destinationName) {
			MessageBus messageBus = MessageBusUtil.getMessageBus();

			Destination destination = messageBus.getDestination(
				destinationName);

			if (destination instanceof BaseAsyncDestination) {
				_asyncServiceDestinations.add(destination);

				messageBus.replace(
					createSynchronousDestination(destinationName));
			}

			if (destination == null) {
				_absentDestinationNames.add(destinationName);

				messageBus.addDestination(
					createSynchronousDestination(destinationName));
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

		private final List<String> _absentDestinationNames = new ArrayList<>();
		private final List<Destination> _asyncServiceDestinations =
			new ArrayList<>();
		private boolean _forceSync;
		private Sync _sync;

	}

	private static final TransactionAttribute _transactionAttribute;

	static {
		TransactionAttribute.Builder builder =
			new TransactionAttribute.Builder();

		builder.setPropagation(Propagation.NOT_SUPPORTED);
		builder.setRollbackForClasses(
			PortalException.class, SystemException.class);

		_transactionAttribute = builder.build();
	}

	private static class CleanTransactionSynchronousDestination
		extends SynchronousDestination {

		@Override
		public void send(final Message message) {
			try {
				TransactionInvokerUtil.invoke(
					_transactionAttribute, new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						CleanTransactionSynchronousDestination.super.send(
							message);

						return null;
					}
				});
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

	}

}