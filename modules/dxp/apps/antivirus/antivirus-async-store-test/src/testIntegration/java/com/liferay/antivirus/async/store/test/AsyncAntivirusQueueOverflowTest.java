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
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.antivirus.AntivirusScanner;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.test.util.DLTestUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;
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

		AtomicInteger prepareEventFired = new AtomicInteger();

		AtomicInteger retryScheduled = new AtomicInteger();

		ServiceRegistration<AntivirusAsyncRetryScheduler>
			schedulerHelperServiceRegistration = _bundleContext.registerService(
				AntivirusAsyncRetryScheduler.class,
				message -> retryScheduled.incrementAndGet(),
				MapUtil.singletonDictionary(Constants.SERVICE_RANKING, 100));

		ServiceRegistration<AntivirusScanner>
			antivirusScannerServiceRegistration =
				_bundleContext.registerService(
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

		ServiceRegistration<AntivirusAsyncEventListener>
			eventListenerServiceRegistration = _bundleContext.registerService(
				AntivirusAsyncEventListener.class,
				new EventListenerBuilder().register(
					AntivirusAsyncEvent.PREPARE,
					prepareEventFired::incrementAndGet
				).register(
					AntivirusAsyncEvent.SUCCESS,
					() -> {
					}
				).build(),
				MapUtil.singletonDictionary(Constants.SERVICE_RANKING, -100));

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

					// The first event is PREPARE which is triggered before the
					// message is sent. Ensure it was called 10 times

					Assert.assertEquals(
						numberOfFilesToProcess, prepareEventFired.get());

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

}