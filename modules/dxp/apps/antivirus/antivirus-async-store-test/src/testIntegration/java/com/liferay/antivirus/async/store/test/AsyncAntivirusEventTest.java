/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.antivirus.async.store.test;

import com.liferay.antivirus.async.store.configuration.AntivirusAsyncConfiguration;
import com.liferay.antivirus.async.store.constants.AntivirusAsyncConstants;
import com.liferay.antivirus.async.store.events.AntivirusAsyncEvent;
import com.liferay.antivirus.async.store.events.AntivirusAsyncEventListener;
import com.liferay.antivirus.async.store.retry.AntivirusAsyncRetryScheduler;
import com.liferay.antivirus.async.store.test.constants.TestConstants;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.antivirus.AntivirusScanner;
import com.liferay.document.library.kernel.antivirus.AntivirusScannerException;
import com.liferay.document.library.kernel.antivirus.AntivirusVirusFoundException;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.test.util.DLTestUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class AsyncAntivirusEventTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(AsyncAntivirusEventTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testMissing() throws Exception {
		CountDownLatch countDownLatch = new CountDownLatch(1);

		AtomicBoolean scannerWasCalled = new AtomicBoolean();

		ServiceRegistration<AntivirusScanner>
			antivirusScannerServiceRegistration =
				_bundleContext.registerService(
					AntivirusScanner.class,
					new MockAntivirusScanner(() -> scannerWasCalled.set(true)),
					null);

		ServiceRegistration<AntivirusAsyncEventListener>
			eventListenerServiceRegistration = _bundleContext.registerService(
				AntivirusAsyncEventListener.class,
				new MockEventListener.Builder().missingConsumer(
					message -> countDownLatch.countDown()
				).build(),
				HashMapDictionaryBuilder.<String, Object>put(
					TestConstants.ANTIVIRUS_ASYNC_EVENT,
					new String[] {AntivirusAsyncEvent.MISSING.name()}
				).build());

		try {

			// This configuration activates the AntivirusAsyncDLStore
			// implementation which replaces the built in DLStoreImpl

			_withAsyncAntivirusConfiguration(
				1, 1,
				() -> {

					// It's possible for a file to be removed between the time
					// it is added and the time it is scanned.

					Message dummyAntivirusMessage =
						_createDummyAntivirusMessage();

					_messageBus.sendMessage(
						AntivirusAsyncConstants.ANTIVIRUS_DESTINATION,
						dummyAntivirusMessage);

					// Wait for the terminating operation. We know the file
					// being added does not exist which triggers the MISSING
					// event so count down the latch

					countDownLatch.await();

					// Finally ensure the scanner was not called

					Assert.assertFalse(scannerWasCalled.get());
				});
		}
		finally {
			eventListenerServiceRegistration.unregister();
			antivirusScannerServiceRegistration.unregister();
		}
	}

	@Test
	public void testProcessingError() throws Exception {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		AtomicBoolean prepareEventFired = new AtomicBoolean();
		AtomicBoolean processingErrorEventFired = new AtomicBoolean();

		AtomicBoolean retryScheduled = new AtomicBoolean();

		ServiceRegistration<AntivirusAsyncRetryScheduler>
			schedulerHelperServiceRegistration = _bundleContext.registerService(
				AntivirusAsyncRetryScheduler.class,
				message -> retryScheduled.set(true),
				HashMapDictionaryBuilder.<String, Object>put(
					Constants.SERVICE_RANKING, 100
				).build());

		ServiceRegistration<AntivirusScanner>
			antivirusScannerServiceRegistration =
				_bundleContext.registerService(
					AntivirusScanner.class,
					new MockAntivirusScanner(
						() -> {
							throw new AntivirusScannerException(
								AntivirusScannerException.PROCESS_FAILURE);
						}),
					null);

		ServiceRegistration<AntivirusAsyncEventListener>
			eventListenerServiceRegistration = _bundleContext.registerService(
				AntivirusAsyncEventListener.class,
				new MockEventListener.Builder().prepareConsumer(
					message -> prepareEventFired.set(true)
				).processingErrorConsumer(
					message -> {
						processingErrorEventFired.set(true);
						countDownLatch.countDown();
					}
				).build(),
				HashMapDictionaryBuilder.<String, Object>put(
					Constants.SERVICE_RANKING, -100
				).put(
					TestConstants.ANTIVIRUS_ASYNC_EVENT,
					new String[] {
						AntivirusAsyncEvent.PREPARE.name(),
						AntivirusAsyncEvent.PROCESSING_ERROR.name()
					}
				).build());

		try {

			// This configuration activates the AntivirusAsyncDLStore
			// implementation which replaces the built in DLStoreImpl

			_withAsyncAntivirusConfiguration(
				1, 1,
				() -> {

					// Add a file to the store so that it triggers an async
					// antivirus scan

					DLFolder dlFolder = DLTestUtil.addDLFolder(
						_group.getGroupId());

					try (LogCapture logCapture =
							LoggerTestUtil.configureLog4JLogger(
								StringBundler.concat(
									"com.liferay.antivirus.async.web.internal.",
									"notifications.",
									"AntivirusAsyncNotification",
									"EventListener"),
								LoggerTestUtil.ERROR)) {

						DLTestUtil.addDLFileEntry(dlFolder.getFolderId());

						// Wait for the terminating operation. The mock scanner
						// will throw a AntivirusScannerException indicating a
						// processing error (like would result from network
						// errors) triggering the PROCESSING_ERROR event so
						// count down the latch

						countDownLatch.await();
					}

					// The first event is PREPARE which is triggered before the
					// message is sent but indicates that the
					// AntivirusAsyncDLStore was actually called

					Assert.assertTrue(prepareEventFired.get());

					// The first time through the scanner an processing error is
					// thrown triggering a PROCESSING_ERROR event

					Assert.assertTrue(processingErrorEventFired.get());

					// When the scheduled event finally fires it will cause a
					// second scan which will succeed and trigger a SUCCESS
					// event (which will also unschedule the retry job)

					Assert.assertTrue(retryScheduled.get());
				});
		}
		finally {
			antivirusScannerServiceRegistration.unregister();
			eventListenerServiceRegistration.unregister();
			schedulerHelperServiceRegistration.unregister();
		}
	}

	@Test
	public void testSizeExceeded() throws Exception {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		AtomicBoolean prepareEventFired = new AtomicBoolean();

		AtomicBoolean scannerWasCalled = new AtomicBoolean();

		ServiceRegistration<AntivirusScanner>
			antivirusScannerServiceRegistration =
				_bundleContext.registerService(
					AntivirusScanner.class,
					new MockAntivirusScanner(
						() -> {
							scannerWasCalled.set(true);

							throw new AntivirusScannerException(
								AntivirusScannerException.SIZE_LIMIT_EXCEEDED);
						}),
					null);

		ServiceRegistration<AntivirusAsyncEventListener>
			eventListenerServiceRegistration = _bundleContext.registerService(
				AntivirusAsyncEventListener.class,
				new MockEventListener.Builder().prepareConsumer(
					message -> prepareEventFired.set(true)
				).sizeExceededConsumer(
					message -> countDownLatch.countDown()
				).build(),
				HashMapDictionaryBuilder.<String, Object>put(
					TestConstants.ANTIVIRUS_ASYNC_EVENT,
					new String[] {
						AntivirusAsyncEvent.PREPARE.name(),
						AntivirusAsyncEvent.SIZE_EXCEEDED.name()
					}
				).build());

		try {

			// This configuration activates the AntivirusAsyncDLStore
			// implementation which replaces the built in DLStoreImpl

			_withAsyncAntivirusConfiguration(
				1, 1,
				() -> {

					// Add a file to the store so that it triggers an async
					// antivirus scan

					DLFolder dlFolder = DLTestUtil.addDLFolder(
						_group.getGroupId());

					DLTestUtil.addDLFileEntry(dlFolder.getFolderId());

					// Wait for the terminating operation. The mock scanner will
					// throw a AntivirusScannerException indicating the size
					// exceeds the maximum triggering the SIZE_EXCEEDED event so
					// count down the latch

					countDownLatch.await();

					// The first event is PREPARE which is triggered before the
					// message is sent but indicates that the
					// AntivirusAsyncDLStore was actually called

					Assert.assertTrue(prepareEventFired.get());

					// Finally ensure the scanner was called

					Assert.assertTrue(scannerWasCalled.get());
				});
		}
		finally {
			eventListenerServiceRegistration.unregister();
			antivirusScannerServiceRegistration.unregister();
		}
	}

	@Test
	public void testSuccess() throws Exception {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		AtomicBoolean prepareEventFired = new AtomicBoolean();

		AtomicBoolean scannerWasCalled = new AtomicBoolean();

		ServiceRegistration<AntivirusScanner>
			antivirusScannerServiceRegistration =
				_bundleContext.registerService(
					AntivirusScanner.class,
					new MockAntivirusScanner(() -> scannerWasCalled.set(true)),
					null);

		ServiceRegistration<AntivirusAsyncEventListener>
			eventListenerServiceRegistration = _bundleContext.registerService(
				AntivirusAsyncEventListener.class,
				new MockEventListener.Builder().prepareConsumer(
					message -> prepareEventFired.set(true)
				).successConsumer(
					message -> countDownLatch.countDown()
				).build(),
				HashMapDictionaryBuilder.<String, Object>put(
					TestConstants.ANTIVIRUS_ASYNC_EVENT,
					new String[] {
						AntivirusAsyncEvent.PREPARE.name(),
						AntivirusAsyncEvent.SUCCESS.name()
					}
				).build());

		try {

			// This configuration activates the AntivirusAsyncDLStore
			// implementation which replaces the built in DLStoreImpl

			_withAsyncAntivirusConfiguration(
				1, 1,
				() -> {

					// Add a file to the store so that it triggers an async
					// antivirus scan

					DLFolder dlFolder = DLTestUtil.addDLFolder(
						_group.getGroupId());

					DLTestUtil.addDLFileEntry(dlFolder.getFolderId());

					// Wait for the terminating operation. We know the file
					// being added does not contain a virus so count down the
					// latch on SUCCESS

					countDownLatch.await();

					// The first event is PREPARE which is triggered before the
					// message is sent but indicates that the
					// AntivirusAsyncDLStore was actually called

					Assert.assertTrue(prepareEventFired.get());

					// Finally check if the scanner was actually called

					Assert.assertTrue(scannerWasCalled.get());
				});
		}
		finally {
			eventListenerServiceRegistration.unregister();
			antivirusScannerServiceRegistration.unregister();
		}
	}

	@Test
	public void testVirusFound() throws Exception {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		AtomicBoolean prepareEventFired = new AtomicBoolean();

		AtomicBoolean scannerWasCalled = new AtomicBoolean();

		ServiceRegistration<AntivirusScanner>
			antivirusScannerServiceRegistration =
				_bundleContext.registerService(
					AntivirusScanner.class,
					new MockAntivirusScanner(
						() -> {
							scannerWasCalled.set(true);

							throw new AntivirusVirusFoundException(
								"Virus detected in stream", "foo.virus");
						}),
					null);

		ServiceRegistration<AntivirusAsyncEventListener>
			eventListenerServiceRegistration = _bundleContext.registerService(
				AntivirusAsyncEventListener.class,
				new MockEventListener.Builder().prepareConsumer(
					message -> prepareEventFired.set(true)
				).virusFoundConsumer(
					message -> countDownLatch.countDown()
				).build(),
				HashMapDictionaryBuilder.<String, Object>put(
					TestConstants.ANTIVIRUS_ASYNC_EVENT,
					new String[] {
						AntivirusAsyncEvent.PREPARE.name(),
						AntivirusAsyncEvent.VIRUS_FOUND.name()
					}
				).build());

		try {

			// This configuration activates the AntivirusAsyncDLStore
			// implementation which replaces the built in DLStoreImpl

			_withAsyncAntivirusConfiguration(
				1, 1,
				() -> {

					// Add a file to the store so that it triggers an async
					// antivirus scan

					DLFolder dlFolder = DLTestUtil.addDLFolder(
						_group.getGroupId());

					DLTestUtil.addDLFileEntry(dlFolder.getFolderId());

					// Wait for the terminating operation. The mock scanner will
					// throw a AntivirusVirusFoundException triggering the
					// VIRUS_FOUND event so count down the latch

					countDownLatch.await();

					// The first event is PREPARE which is triggered before the
					// message is sent but indicates that the
					// AntivirusAsyncDLStore was actually called

					Assert.assertTrue(prepareEventFired.get());

					// Finally ensure the scanner was called

					Assert.assertTrue(scannerWasCalled.get());
				});
		}
		finally {
			eventListenerServiceRegistration.unregister();
			antivirusScannerServiceRegistration.unregister();
		}
	}

	private Message _createDummyAntivirusMessage() {
		Message message = new Message();

		message.put("companyId", 0);
		message.put("fileName", "test");
		message.put("repositoryId", 0);
		message.put("versionLabel", "test");

		return message;
	}

	private void _withAsyncAntivirusConfiguration(
			int maximumQueueSize, int retryInterval,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				"maximumQueueSize", maximumQueueSize
			).put(
				"retryInterval", retryInterval
			).build();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					AntivirusAsyncConfiguration.class.getName(), dictionary)) {

			unsafeRunnable.run();
		}
	}

	private static BundleContext _bundleContext;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MessageBus _messageBus;

}