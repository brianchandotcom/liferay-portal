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

package com.liferay.portal.kernel.test.rule.callback;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.BaseAsyncDestination;
import com.liferay.portal.kernel.messaging.BaseDestination;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.InvokerMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.SynchronousDestination;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.callback.SynchronousDestinationTestCallback.SyncHandler;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.dependency.ServiceDependencyManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.junit.runner.Description;

/**
 * @author Shuyang Zhou
 */
public class SynchronousDestinationTestCallback
	implements TestCallback<SyncHandler, SyncHandler> {

	public static final SynchronousDestinationTestCallback INSTANCE =
		new SynchronousDestinationTestCallback();

	@Override
	public void afterClass(Description description, SyncHandler syncHandler)
		throws Exception {

		if (syncHandler != null) {
			syncHandler.restorePreviousSync();
		}
	}

	@Override
	public void afterMethod(
		Description description, SyncHandler syncHandler, Object target) {

		if (syncHandler != null) {
			syncHandler.restorePreviousSync();
		}
	}

	@Override
	public SyncHandler beforeClass(Description description) throws Throwable {
		Class<?> testClass = description.getTestClass();

		return _createSyncHandler(testClass.getAnnotation(Sync.class));
	}

	@Override
	public SyncHandler beforeMethod(Description description, Object target) {
		Class<?> testClass = description.getTestClass();

		Sync sync = testClass.getAnnotation(Sync.class);

		if (sync != null) {
			return null;
		}

		sync = description.getAnnotation(Sync.class);

		if (sync == null) {
			return null;
		}

		return _createSyncHandler(sync);
	}

	public static class SyncHandler {

		public BaseDestination createSynchronousDestination(
			String destinationName) {

			SynchronousDestination synchronousDestination = null;

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
			ServiceDependencyManager serviceDependencyManager =
				new ServiceDependencyManager();

			Filter asyncFilter = _registerDestinationFilter(
				DestinationNames.ASYNC_SERVICE);
			Filter backgroundTaskFilter = _registerDestinationFilter(
				DestinationNames.BACKGROUND_TASK);
			Filter backgroundTaskStatusFilter = _registerDestinationFilter(
				DestinationNames.BACKGROUND_TASK_STATUS);
			Filter mailFilter = _registerDestinationFilter(
				DestinationNames.MAIL);
			Filter pdfProcessorFilter = _registerDestinationFilter(
				DestinationNames.DOCUMENT_LIBRARY_PDF_PROCESSOR);
			Filter rawMetaDataProcessorFilter = _registerDestinationFilter(
				DestinationNames.DOCUMENT_LIBRARY_RAW_METADATA_PROCESSOR);
			Filter subscrpitionSenderFilter = _registerDestinationFilter(
				DestinationNames.SUBSCRIPTION_SENDER);

			serviceDependencyManager.registerDependencies(
				asyncFilter, backgroundTaskFilter, backgroundTaskStatusFilter,
				mailFilter, pdfProcessorFilter, rawMetaDataProcessorFilter,
				subscrpitionSenderFilter);

			boolean schedulerEnabled = GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.SCHEDULER_ENABLED));

			if (schedulerEnabled) {
				Filter kaleoGraphWalkerFilter = _registerDestinationFilter(
					"liferay/kaleo_graph_walker");

				serviceDependencyManager.registerDependencies(
					kaleoGraphWalkerFilter);
			}

			serviceDependencyManager.waitForDependencies();

			ProxyModeThreadLocal.setForceSync(true);

			replaceDestination(DestinationNames.ASYNC_SERVICE);
			replaceDestination(DestinationNames.BACKGROUND_TASK);
			replaceDestination(DestinationNames.BACKGROUND_TASK_STATUS);
			replaceDestination(DestinationNames.DOCUMENT_LIBRARY_PDF_PROCESSOR);
			replaceDestination(
				DestinationNames.DOCUMENT_LIBRARY_RAW_METADATA_PROCESSOR);
			replaceDestination(
				DestinationNames.DOCUMENT_LIBRARY_SYNC_EVENT_PROCESSOR);
			replaceDestination(DestinationNames.MAIL);
			replaceDestination(DestinationNames.SCHEDULER_ENGINE);
			replaceDestination(DestinationNames.SUBSCRIPTION_SENDER);
			replaceDestination("liferay/adaptive_media_processor");
			replaceDestination("liferay/report_request");
			replaceDestination("liferay/reports_admin");

			if (_sync != null) {
				for (String name : _sync.destinationNames()) {
					replaceDestination(name);
				}
			}

			if (schedulerEnabled) {
				replaceDestination("liferay/kaleo_graph_walker");
			}

			for (String searchEngineId :
					SearchEngineHelperUtil.getSearchEngineIds()) {

				replaceDestination(
					SearchEngineHelperUtil.getSearchReaderDestinationName(
						searchEngineId));
				replaceDestination(
					SearchEngineHelperUtil.getSearchWriterDestinationName(
						searchEngineId));
			}

			MessageBus messageBus = MessageBusUtil.getMessageBus();

			BaseAsyncDestination schedulerDestination =
				(BaseAsyncDestination)messageBus.getDestination(
					DestinationNames.SCHEDULER_DISPATCH);

			if (schedulerDestination == null) {
				return;
			}

			for (MessageListener messageListener :
					schedulerDestination.getMessageListeners()) {

				InvokerMessageListener invokerMessageListener =
					(InvokerMessageListener)messageListener;

				MessageListener schedulerMessageListener =
					invokerMessageListener.getMessageListener();

				schedulerDestination.unregister(schedulerMessageListener);

				_schedulerInvokerMessageListeners.add(invokerMessageListener);
			}

			CountDownLatch startCountDownLatch = new CountDownLatch(
				schedulerDestination.getWorkersMaxSize());

			CountDownLatch endCountDownLatch = new CountDownLatch(1);

			Message countDownMessage = new Message();

			MessageListener messageListener = message -> {
				if (countDownMessage == message) {
					startCountDownLatch.countDown();

					try {
						endCountDownLatch.await();
					}
					catch (InterruptedException ie) {
						ReflectionUtil.throwException(ie);
					}
				}
			};

			schedulerDestination.register(messageListener);

			for (int i = 0; i < schedulerDestination.getWorkersMaxSize(); i++) {
				schedulerDestination.send(countDownMessage);
			}

			try {
				startCountDownLatch.await();
			}
			catch (InterruptedException ie) {
				ReflectionUtil.throwException(ie);
			}

			schedulerDestination.unregister(messageListener);

			endCountDownLatch.countDown();
		}

		public void replaceDestination(String destinationName) {
			MessageBus messageBus = MessageBusUtil.getMessageBus();

			Destination destination = messageBus.getDestination(
				destinationName);

			if (destination instanceof BaseAsyncDestination) {
				_asyncServiceDestinations.add(destination);

				messageBus.replace(
					createSynchronousDestination(destinationName), false);
			}

			if (destination == null) {
				_absentDestinationNames.add(destinationName);

				messageBus.addDestination(
					createSynchronousDestination(destinationName));
			}
		}

		public void restorePreviousSync() {
			ProxyModeThreadLocal.setForceSync(_forceSync);

			MessageBus messageBus = MessageBusUtil.getMessageBus();

			for (Destination destination : _asyncServiceDestinations) {
				messageBus.replace(destination);
			}

			_asyncServiceDestinations.clear();

			for (String absentDestinationName : _absentDestinationNames) {
				messageBus.removeDestination(absentDestinationName);
			}

			Destination destination = messageBus.getDestination(
				DestinationNames.SCHEDULER_DISPATCH);

			if (destination == null) {
				return;
			}

			for (InvokerMessageListener invokerMessageListener :
					_schedulerInvokerMessageListeners) {

				destination.register(
					invokerMessageListener.getMessageListener(),
					invokerMessageListener.getClassLoader());
			}
		}

		public void setForceSync(boolean forceSync) {
			_forceSync = forceSync;
		}

		public void setSync(Sync sync) {
			_sync = sync;
		}

		private Filter _registerDestinationFilter(String destinationName) {
			Registry registry = RegistryUtil.getRegistry();

			return registry.getFilter(
				StringBundler.concat(
					"(&(destination.name=", destinationName, ")(objectClass=",
					Destination.class.getName(), "))"));
		}

		private final List<String> _absentDestinationNames = new ArrayList<>();
		private final List<Destination> _asyncServiceDestinations =
			new ArrayList<>();
		private boolean _forceSync;
		private final List<InvokerMessageListener>
			_schedulerInvokerMessageListeners = new ArrayList<>();
		private Sync _sync;

	}

	protected SynchronousDestinationTestCallback() {
	}

	private SyncHandler _createSyncHandler(Sync sync) {
		SyncHandler syncHandler = new SyncHandler();

		syncHandler.setForceSync(ProxyModeThreadLocal.isForceSync());
		syncHandler.setSync(sync);

		syncHandler.enableSync();

		return syncHandler;
	}

	private static final TransactionConfig _transactionConfig;

	static {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setPropagation(Propagation.NOT_SUPPORTED);
		builder.setRollbackForClasses(
			PortalException.class, SystemException.class);

		_transactionConfig = builder.build();
	}

	private static class CleanTransactionSynchronousDestination
		extends SynchronousDestination {

		@Override
		public void send(final Message message) {
			try {
				TransactionInvokerUtil.invoke(
					_transactionConfig,
					new Callable<Void>() {

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