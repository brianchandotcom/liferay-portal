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
import com.liferay.antivirus.async.store.jmx.AntivirusAsyncStatisticsManagerMBean;
import com.liferay.antivirus.async.store.retry.AntivirusAsyncRetryScheduler;
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
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

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
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class AsyncAntivirusStatisticsTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(
			AsyncAntivirusStatisticsTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testStatistics() throws Exception {
		int numberOfFilesToProcess = 100;

		CountDownLatch countDownLatch = new CountDownLatch(
			numberOfFilesToProcess);

		AtomicInteger prepareEventFired = new AtomicInteger();
		AtomicInteger processingErrorEventFired = new AtomicInteger();
		AtomicInteger retryScheduled = new AtomicInteger();
		AtomicInteger sizeExceededEventFired = new AtomicInteger();
		AtomicInteger successEventFired = new AtomicInteger();
		AtomicInteger virusFoundEventFired = new AtomicInteger();

		Random random = new Random();

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
					new MockAntivirusScanner.Builder().inputStreamConsumer(
						inputStream -> {

							// Add some delay so the queue will reliably
							// overflow

							try {
								Thread.sleep(50);
							}
							catch (InterruptedException interruptedException) {

								// Ignore this

							}

							int choice = random.nextInt(4);

							if (choice == 1) {
								throw new AntivirusVirusFoundException(
									"Virus detected in stream", "foo.virus");
							}
							else if (choice == 2) {
								throw new AntivirusScannerException(
									AntivirusScannerException.
										SIZE_LIMIT_EXCEEDED);
							}
							else if (choice == 3) {
								throw new AntivirusScannerException(
									AntivirusScannerException.PROCESS_FAILURE);
							}
						}
					).build(),
					HashMapDictionaryBuilder.<String, Object>put(
						Constants.SERVICE_RANKING, 100
					).build());

		@SuppressWarnings("unchecked")
		ServiceRegistration<BiConsumer<String, Map.Entry<Message, Object[]>>>
			eventListenerServiceRegistration = _bundleContext.registerService(
				(Class<BiConsumer<String, Map.Entry<Message, Object[]>>>)
					(Class<?>)BiConsumer.class,
				new MockEventListener.Builder().prepareConsumer(
					(n, p) -> prepareEventFired.incrementAndGet()
				).processingErrorConsumer(
					(n, p) -> processingErrorEventFired.incrementAndGet()
				).sizeExceededConsumer(
					(n, p) -> {
						sizeExceededEventFired.incrementAndGet();
						countDownLatch.countDown();
					}
				).successConsumer(
					(n, p) -> {
						successEventFired.incrementAndGet();
						countDownLatch.countDown();
					}
				).virusFoundConsumer(
					(n, p) -> {
						virusFoundEventFired.incrementAndGet();
						countDownLatch.countDown();
					}
				).build(),
				HashMapDictionaryBuilder.<String, Object>put(
					TestConstants.ANTIVIRUS_ASYNC_EVENT,
					new String[] {
						AntivirusAsyncEvent.PREPARE.name(),
						AntivirusAsyncEvent.PROCESSING_ERROR.name(),
						AntivirusAsyncEvent.SIZE_EXCEEDED.name(),
						AntivirusAsyncEvent.SUCCESS.name(),
						AntivirusAsyncEvent.VIRUS_FOUND.name()
					}
				).put(
					Constants.SERVICE_RANKING, -100
				).build());

		try {

			// This configuration activates the AntivirusAsyncDLStore
			// implementation which replaces the built in DLStoreImpl

			_withAsyncAntivirusConfiguration(
				5, 10,
				() -> {
					AntivirusAsyncStatisticsManagerMBean
						antivirusAsyncStatisticsManagerMBean =
							_getAntivirusAsyncStatisticsManager();

					antivirusAsyncStatisticsManagerMBean.refresh();

					// Add a file to the store so that it triggers an async
					// antivirus scan

					DLFolder dlFolder = DLTestUtil.addDLFolder(
						_group.getGroupId());

					try (LogCapture logCapture =
							LoggerTestUtil.configureLog4JLogger(
								StringBundler.concat(
									"com.liferay.antivirus.async.web.internal.",
									"notifications.",
									"AntivirusAsyncNotificationEventListener"),
								LoggerTestUtil.ERROR)) {

						for (int i = numberOfFilesToProcess; i > 0; i--) {
							DLTestUtil.addDLFileEntry(dlFolder.getFolderId());
						}

						// Wait for the terminating operation. The mock scanner
						// will throw a AntivirusScannerException indicating a
						// processing error (like would result from network
						// errors) triggering the PROCESSING_ERROR event so
						// count down the latch

						countDownLatch.await();
					}

					// The first event is PREPARE which is triggered before the
					// message is sent. Ensure it was called the correct number
					// of times

					Assert.assertEquals(
						numberOfFilesToProcess, prepareEventFired.get());

					Assert.assertEquals(
						successEventFired.get() + virusFoundEventFired.get(),
						antivirusAsyncStatisticsManagerMBean.
							getTotalScannedCount());

					Assert.assertEquals(
						processingErrorEventFired.get(),
						antivirusAsyncStatisticsManagerMBean.
							getProcessingErrorCount());

					Assert.assertEquals(
						sizeExceededEventFired.get(),
						antivirusAsyncStatisticsManagerMBean.
							getSizeExceededCount());

					Assert.assertEquals(
						virusFoundEventFired.get(),
						antivirusAsyncStatisticsManagerMBean.
							getVirusFoundCount());
				});
		}
		finally {
			antivirusScannerServiceRegistration.unregister();
			eventListenerServiceRegistration.unregister();
			schedulerHelperServiceRegistration.unregister();
		}
	}

	private AntivirusAsyncStatisticsManagerMBean
		_getAntivirusAsyncStatisticsManager() {

		ServiceReference<AntivirusAsyncStatisticsManagerMBean>
			serviceReference = _bundleContext.getServiceReference(
				AntivirusAsyncStatisticsManagerMBean.class);

		AntivirusAsyncStatisticsManagerMBean
			antivirusAsyncStatisticsManagerMBean = _bundleContext.getService(
				serviceReference);

		Assert.assertNotNull(antivirusAsyncStatisticsManagerMBean);

		return antivirusAsyncStatisticsManagerMBean;
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