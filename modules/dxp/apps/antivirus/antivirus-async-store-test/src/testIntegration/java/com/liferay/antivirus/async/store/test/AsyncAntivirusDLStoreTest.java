/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.antivirus.async.store.test;

import com.liferay.antivirus.async.store.configuration.AntivirusAsyncConfiguration;
import com.liferay.antivirus.async.store.constants.AntivirusAsyncDestinationNames;
import com.liferay.antivirus.async.store.event.AntivirusAsyncEvent;
import com.liferay.antivirus.async.store.event.AntivirusAsyncEventListener;
import com.liferay.antivirus.async.store.jmx.AntivirusAsyncStatisticsManagerMBean;
import com.liferay.antivirus.async.store.retry.AntivirusAsyncRetryScheduler;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.antivirus.AntivirusScanner;
import com.liferay.document.library.kernel.antivirus.AntivirusScannerException;
import com.liferay.document.library.kernel.antivirus.AntivirusVirusFoundException;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.test.util.DLTestUtil;
import com.liferay.petra.concurrent.NoticeableThreadPoolExecutor;
import com.liferay.petra.concurrent.ThreadPoolHandlerAdapter;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
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
public class AsyncAntivirusDLStoreTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(
			AsyncAntivirusDLStoreTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	@Test
	public void testEventMissing() throws Exception {
		AtomicBoolean calledScan = new AtomicBoolean();

		AtomicBoolean firedEventMissing = new AtomicBoolean();

		_registerService(
			AntivirusAsyncEventListener.class,
			_create(
				HashMapBuilder.<AntivirusAsyncEvent, Runnable>put(
					AntivirusAsyncEvent.MISSING,
					() -> firedEventMissing.set(true)
				).build()),
			null);

		_registerService(
			AntivirusScanner.class,
			new MockAntivirusScanner(() -> calledScan.set(true)), null);

		_withAsyncAntivirusConfiguration(
			1, "0 0/1 * * * ?", true,
			() -> {
				_messageBus.sendMessage(
					AntivirusAsyncDestinationNames.ANTIVIRUS,
					new Message() {
						{
							put("companyId", 0);
							put("fileName", RandomTestUtil.randomString());
							put("repositoryId", 0);
							put("versionLabel", RandomTestUtil.randomString());
						}
					});

				Assert.assertFalse(calledScan.get());
				Assert.assertTrue(firedEventMissing.get());
			});
	}

	@Test
	public void testEventProcessingError() throws Exception {
		AtomicBoolean calledSchedule = new AtomicBoolean();

		AtomicBoolean firedEventPrepare = new AtomicBoolean();
		AtomicBoolean firedEventProcessingError = new AtomicBoolean();

		_registerService(
			AntivirusAsyncEventListener.class,
			_create(
				HashMapBuilder.<AntivirusAsyncEvent, Runnable>put(
					AntivirusAsyncEvent.PREPARE,
					() -> firedEventPrepare.set(true)
				).put(
					AntivirusAsyncEvent.PROCESSING_ERROR,
					() -> firedEventProcessingError.set(true)
				).build()),
			MapUtil.singletonDictionary(Constants.SERVICE_RANKING, -100));

		_registerService(
			AntivirusAsyncRetryScheduler.class,
			new AntivirusAsyncRetryScheduler() {

				@Override
				public void schedule(Message message) {
					calledSchedule.set(true);
				}

				@Override
				public void unschedule(Message message) {
				}

			},
			MapUtil.singletonDictionary(Constants.SERVICE_RANKING, 100));
		_registerService(
			AntivirusScanner.class,
			new MockAntivirusScanner(
				() -> {
					throw new AntivirusScannerException(
						AntivirusScannerException.PROCESS_FAILURE);
				}),
			null);

		_withAsyncAntivirusConfiguration(
			1, "0 0/1 * * * ?", true,
			() -> {
				DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

				DLTestUtil.addDLFileEntry(dlFolder.getFolderId());

				Assert.assertTrue(calledSchedule.get());
				Assert.assertTrue(firedEventPrepare.get());
				Assert.assertTrue(firedEventProcessingError.get());
			});
	}

	@Test
	public void testEventSizeExceeded() throws Exception {
		AtomicBoolean calledScan = new AtomicBoolean();

		AtomicBoolean firedEventPrepare = new AtomicBoolean();
		AtomicBoolean firedEventSizeExceeded = new AtomicBoolean();

		_registerService(
			AntivirusAsyncEventListener.class,
			_create(
				HashMapBuilder.<AntivirusAsyncEvent, Runnable>put(
					AntivirusAsyncEvent.PREPARE,
					() -> firedEventPrepare.set(true)
				).put(
					AntivirusAsyncEvent.SIZE_EXCEEDED,
					() -> firedEventSizeExceeded.set(true)
				).build()),
			null);

		_registerService(
			AntivirusScanner.class,
			new MockAntivirusScanner(
				() -> {
					calledScan.set(true);

					throw new AntivirusScannerException(
						AntivirusScannerException.SIZE_LIMIT_EXCEEDED);
				}),
			null);

		_withAsyncAntivirusConfiguration(
			1, "0 0/1 * * * ?", true,
			() -> {
				DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

				DLTestUtil.addDLFileEntry(dlFolder.getFolderId());

				Assert.assertTrue(calledScan.get());
				Assert.assertTrue(firedEventPrepare.get());
				Assert.assertTrue(firedEventSizeExceeded.get());
			});
	}

	@Test
	public void testEventSuccess() throws Exception {
		AtomicBoolean calledScan = new AtomicBoolean();

		AtomicBoolean firedEventPrepare = new AtomicBoolean();
		AtomicBoolean firedEventSuccess = new AtomicBoolean();

		_registerService(
			AntivirusAsyncEventListener.class,
			_create(
				HashMapBuilder.<AntivirusAsyncEvent, Runnable>put(
					AntivirusAsyncEvent.PREPARE,
					() -> firedEventPrepare.set(true)
				).put(
					AntivirusAsyncEvent.SUCCESS,
					() -> firedEventSuccess.set(true)
				).build()),
			null);

		_registerService(
			AntivirusScanner.class,
			new MockAntivirusScanner(() -> calledScan.set(true)), null);

		_withAsyncAntivirusConfiguration(
			1, "0 0/1 * * * ?", true,
			() -> {
				DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

				DLTestUtil.addDLFileEntry(dlFolder.getFolderId());

				Assert.assertTrue(calledScan.get());
				Assert.assertTrue(firedEventPrepare.get());
				Assert.assertTrue(firedEventSuccess.get());
			});
	}

	@Test
	public void testEventVirusFound() throws Exception {
		AtomicBoolean calledScan = new AtomicBoolean();

		AtomicBoolean firedEventPrepare = new AtomicBoolean();
		AtomicBoolean firedEventVirusFound = new AtomicBoolean();

		_registerService(
			AntivirusAsyncEventListener.class,
			_create(
				HashMapBuilder.<AntivirusAsyncEvent, Runnable>put(
					AntivirusAsyncEvent.PREPARE,
					() -> firedEventPrepare.set(true)
				).put(
					AntivirusAsyncEvent.VIRUS_FOUND,
					() -> firedEventVirusFound.set(true)
				).build()),
			null);

		_registerService(
			AntivirusScanner.class,
			new MockAntivirusScanner(
				() -> {
					calledScan.set(true);

					throw new AntivirusVirusFoundException(
						RandomTestUtil.randomString(),
						RandomTestUtil.randomString());
				}),
			null);

		_withAsyncAntivirusConfiguration(
			1, "0 0/1 * * * ?", true,
			() -> {
				DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

				DLTestUtil.addDLFileEntry(dlFolder.getFolderId());

				Assert.assertTrue(calledScan.get());
				Assert.assertTrue(firedEventPrepare.get());
				Assert.assertTrue(firedEventVirusFound.get());
			});
	}

	@Test
	public void testQueueOverflow() throws Exception {
		AtomicInteger calledSchedule = new AtomicInteger();

		AtomicInteger firedEventPrepare = new AtomicInteger();

		_registerService(
			AntivirusAsyncEventListener.class,
			_create(
				HashMapBuilder.<AntivirusAsyncEvent, Runnable>put(
					AntivirusAsyncEvent.PREPARE,
					firedEventPrepare::incrementAndGet
				).put(
					AntivirusAsyncEvent.SUCCESS,
					() -> {
					}
				).build()),
			MapUtil.singletonDictionary(Constants.SERVICE_RANKING, -100));

		_registerService(
			AntivirusAsyncRetryScheduler.class,
			new AntivirusAsyncRetryScheduler() {

				@Override
				public void schedule(Message message) {
					calledSchedule.incrementAndGet();
				}

				@Override
				public void unschedule(Message message) {
				}

			},
			MapUtil.singletonDictionary(Constants.SERVICE_RANKING, 100));
		_registerService(
			AntivirusScanner.class,
			new MockAntivirusScanner(
				() -> {
					try {
						Thread.sleep(Long.MAX_VALUE);
					}
					catch (InterruptedException interruptedException) {
					}
				}),
			null);

		_withAsyncAntivirusConfiguration(
			1, "0 0/10 * * * ?", false,
			() -> {
				DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

				int count = 10;

				try (LogCapture logCapture =
						LoggerTestUtil.configureLog4JLogger(
							"com.liferay.antivirus.async.store.internal." +
								"messaging.AntivirusAsyncMessageListener",
							LoggerTestUtil.DEBUG)) {

					for (int i = count; i > 0; i--) {
						DLTestUtil.addDLFileEntry(dlFolder.getFolderId());
					}

					List<LogEntry> logEntries = logCapture.getLogEntries();

					for (LogEntry logEntry : logEntries) {
						String message = logEntry.getMessage();

						Assert.assertTrue(
							message.contains(
								"into persistent storage because the async " +
									"antivirus queue is overflowing"));
					}
				}

				Assert.assertTrue(
					String.valueOf(calledSchedule.get()),
					calledSchedule.get() > 0);
				Assert.assertEquals(count, firedEventPrepare.get());
			});
	}

	@Test
	public void testStatistics() throws Exception {
		AtomicInteger firedEventPrepare = new AtomicInteger();
		AtomicInteger firedEventProcessingError = new AtomicInteger();
		AtomicInteger firedEventSizeExceeded = new AtomicInteger();
		AtomicInteger firedEventSuccess = new AtomicInteger();
		AtomicInteger firedEventVirusFound = new AtomicInteger();

		Random random = new Random();

		_registerService(
			AntivirusAsyncEventListener.class,
			_create(
				HashMapBuilder.<AntivirusAsyncEvent, Runnable>put(
					AntivirusAsyncEvent.PREPARE,
					firedEventPrepare::incrementAndGet
				).put(
					AntivirusAsyncEvent.PROCESSING_ERROR,
					firedEventProcessingError::incrementAndGet
				).put(
					AntivirusAsyncEvent.SIZE_EXCEEDED,
					firedEventSizeExceeded::incrementAndGet
				).put(
					AntivirusAsyncEvent.SUCCESS,
					firedEventSuccess::incrementAndGet
				).put(
					AntivirusAsyncEvent.VIRUS_FOUND,
					firedEventVirusFound::incrementAndGet
				).build()),
			MapUtil.singletonDictionary(Constants.SERVICE_RANKING, -100));
		_registerService(
			AntivirusAsyncRetryScheduler.class,
			ProxyFactory.newDummyInstance(AntivirusAsyncRetryScheduler.class),
			MapUtil.singletonDictionary(Constants.SERVICE_RANKING, 100));
		_registerService(
			AntivirusScanner.class,
			new MockAntivirusScanner(
				() -> {
					int choice = random.nextInt(4);

					if (choice == 1) {
						throw new AntivirusVirusFoundException(
							RandomTestUtil.randomString(),
							RandomTestUtil.randomString());
					}
					else if (choice == 2) {
						throw new AntivirusScannerException(
							AntivirusScannerException.SIZE_LIMIT_EXCEEDED);
					}
					else if (choice == 3) {
						throw new AntivirusScannerException(
							AntivirusScannerException.PROCESS_FAILURE);
					}
				}),
			MapUtil.singletonDictionary(Constants.SERVICE_RANKING, 100));

		_withAsyncAntivirusConfiguration(
			5, "0 0/10 * * * ?", true,
			() -> {
				AntivirusAsyncStatisticsManagerMBean
					antivirusAsyncStatisticsManagerMBean =
						_bundleContext.getService(
							_bundleContext.getServiceReference(
								AntivirusAsyncStatisticsManagerMBean.class));

				Assert.assertNotNull(antivirusAsyncStatisticsManagerMBean);

				antivirusAsyncStatisticsManagerMBean.refresh();

				int count = 100;

				DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

				for (int i = count; i > 0; i--) {
					DLTestUtil.addDLFileEntry(dlFolder.getFolderId());
				}

				Assert.assertEquals(count, firedEventPrepare.get());
				Assert.assertEquals(
					firedEventProcessingError.get(),
					antivirusAsyncStatisticsManagerMBean.
						getProcessingErrorCount());
				Assert.assertEquals(
					firedEventSizeExceeded.get(),
					antivirusAsyncStatisticsManagerMBean.
						getSizeExceededCount());
				Assert.assertEquals(
					firedEventSuccess.get() + firedEventVirusFound.get(),
					antivirusAsyncStatisticsManagerMBean.
						getTotalScannedCount());
				Assert.assertEquals(
					firedEventVirusFound.get(),
					antivirusAsyncStatisticsManagerMBean.getVirusFoundCount());
			});
	}

	private AntivirusAsyncEventListener _create(
		Map<AntivirusAsyncEvent, Runnable> runnables) {

		return message -> {
			AntivirusAsyncEvent antivirusAsyncEvent =
				(AntivirusAsyncEvent)message.get("antivirusAsyncEvent");

			Runnable runnable = runnables.get(antivirusAsyncEvent);

			runnable.run();
		};
	}

	private <S> void _registerService(
		Class<S> clazz, S service, Dictionary<String, ?> properties) {

		ServiceRegistration<?> serviceRegistration =
			_bundleContext.registerService(clazz, service, properties);

		_serviceRegistrations.add(serviceRegistration);
	}

	private SafeCloseable _sync() {
		Destination destination = MessageBusUtil.getDestination(
			AntivirusAsyncDestinationNames.ANTIVIRUS);

		Object originalNoticeableThreadPoolExecutor =
			ReflectionTestUtil.getAndSetFieldValue(
				destination, "_noticeableThreadPoolExecutor",
				_syncNoticeableThreadPoolExecutor);

		return new SafeCloseable() {

			@Override
			public void close() {
				ReflectionTestUtil.setFieldValue(
					destination, "_noticeableThreadPoolExecutor",
					originalNoticeableThreadPoolExecutor);
			}

		};
	}

	private void _withAsyncAntivirusConfiguration(
			int maximumQueueSize, String retryCronExpression, boolean sync,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					AntivirusAsyncConfiguration.class.getName(),
					HashMapDictionaryBuilder.<String, Object>put(
						"maximumQueueSize", maximumQueueSize
					).put(
						"retryCronExpression", retryCronExpression
					).build())) {

			if (sync) {
				try (SafeCloseable safeCloseable = _sync()) {
					unsafeRunnable.run();
				}
			}
			else {
				unsafeRunnable.run();
			}
		}
	}

	private static BundleContext _bundleContext;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MessageBus _messageBus;

	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

	private final NoticeableThreadPoolExecutor
		_syncNoticeableThreadPoolExecutor = new NoticeableThreadPoolExecutor(
			1, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1),
			Executors.defaultThreadFactory(),
			new ThreadPoolExecutor.AbortPolicy(),
			new ThreadPoolHandlerAdapter()) {

			@Override
			public void execute(Runnable runnable) {
				runnable.run();
			}

		};

	private class MockAntivirusScanner implements AntivirusScanner {

		public MockAntivirusScanner(
			UnsafeRunnable<AntivirusScannerException> unsafeRunnable) {

			_unsafeRunnable = unsafeRunnable;
		}

		@Override
		public boolean isActive() {
			return true;
		}

		@Override
		public void scan(byte[] bytes) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void scan(File file) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void scan(InputStream inputStream)
			throws AntivirusScannerException {

			_unsafeRunnable.run();
		}

		private final UnsafeRunnable<AntivirusScannerException> _unsafeRunnable;

	}

}