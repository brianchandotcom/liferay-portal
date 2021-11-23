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
import com.liferay.antivirus.async.store.events.AntivirusAsyncEvent;
import com.liferay.antivirus.async.store.events.AntivirusAsyncEventListener;
import com.liferay.antivirus.async.store.retry.AntivirusAsyncRetryScheduler;
import com.liferay.antivirus.async.store.test.constants.TestConstants;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.antivirus.AntivirusScanner;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.test.util.DLTestUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

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
public class AsyncAntivirusQueueOverflowTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(
			AsyncAntivirusQueueOverflowTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testQueueOverflow() throws Exception {
		int numberOfFilesToProcess = 10;

		CountDownLatch countDownLatch = new CountDownLatch(
			numberOfFilesToProcess);

		AtomicInteger prepareEventFired = new AtomicInteger();
		AtomicInteger retryScheduled = new AtomicInteger();
		AtomicInteger successEventFired = new AtomicInteger();

		ServiceRegistration<AntivirusAsyncRetryScheduler>
			schedulerHelperServiceRegistration = _bundleContext.registerService(
				AntivirusAsyncRetryScheduler.class,
				message -> {
					retryScheduled.incrementAndGet();
					countDownLatch.countDown();
				},
				HashMapDictionaryBuilder.<String, Object>put(
					Constants.SERVICE_RANKING, 100
				).build());

		ServiceRegistration<AntivirusScanner>
			antivirusScannerServiceRegistration =
				_bundleContext.registerService(
					AntivirusScanner.class,
					new MockAntivirusScanner(
						() -> {

							// Add some delay so the queue will reliably
							// overflow

							try {
								Thread.sleep(200);
							}
							catch (InterruptedException interruptedException) {

								// Ignore this

							}
						}),
					null);

		ServiceRegistration<AntivirusAsyncEventListener>
			eventListenerServiceRegistration = _bundleContext.registerService(
				AntivirusAsyncEventListener.class,
				new MockEventListener.Builder().prepareConsumer(
					message -> prepareEventFired.incrementAndGet()
				).successConsumer(
					message -> {
						successEventFired.incrementAndGet();
						countDownLatch.countDown();
					}
				).build(),
				HashMapDictionaryBuilder.<String, Object>put(
					Constants.SERVICE_RANKING, -100
				).put(
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
				1, 10,
				() -> {

					// Add a file to the store so that it triggers an async
					// antivirus scan

					DLFolder dlFolder = DLTestUtil.addDLFolder(
						_group.getGroupId());

					for (int i = numberOfFilesToProcess; i > 0; i--) {
						DLTestUtil.addDLFileEntry(dlFolder.getFolderId());
					}

					// Wait for the terminating operation. The mock scanner
					// will throw a AntivirusScannerException indicating a
					// processing error (like would result from network
					// errors) triggering the PROCESSING_ERROR event so
					// count down the latch

					countDownLatch.await();

					// The first event is PREPARE which is triggered before the
					// message is sent. Ensure it was called 10 times

					Assert.assertEquals(
						numberOfFilesToProcess, prepareEventFired.get());

					// At least one SUCCESS event should have resulted

					Assert.assertTrue(
						String.valueOf(successEventFired.get()),
						successEventFired.get() > 0);

					// When the number of incoming scan requests overflows the
					// the queue they are persisted into the scheduler as retry
					// jobs and should have triggered RETRY_SCHEDULED event

					Assert.assertTrue(
						String.valueOf(retryScheduled.get()),
						retryScheduled.get() > 0);
				});
		}
		finally {
			antivirusScannerServiceRegistration.unregister();
			eventListenerServiceRegistration.unregister();
			schedulerHelperServiceRegistration.unregister();
		}
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

	@Inject
	private SchedulerEngineHelper _schedulerEngineHelper;

}