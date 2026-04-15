/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.security.audit.AuditMessageProcessor;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Eric Yan
 */
@RunWith(Arquillian.class)
public class SchedulerEngineHelperImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testScriptingJob() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			SchedulerEngineHelperImplTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		CountDownLatch countDownLatch = new CountDownLatch(1);

		ServiceRegistration<?> serviceRegistration1 =
			bundleContext.registerService(
				AuditMessageProcessor.class,
				auditMessage -> {
					if (Objects.equals(
							TriggerState.COMPLETE.name(),
							auditMessage.getMessage())) {

						countDownLatch.countDown();
					}
				},
				HashMapDictionaryBuilder.<String, Object>put(
					"eventTypes", SchedulerEngine.SCHEDULER
				).build());

		ServiceRegistration<?> serviceRegistration2 =
			bundleContext.registerService(
				Destination.class,
				_destinationFactory.createDestination(
					DestinationConfiguration.
						createSynchronousDestinationConfiguration(
							DestinationNames.SCHEDULER_SCRIPTING)),
				HashMapDictionaryBuilder.<String, Object>put(
					"destination.name", DestinationNames.SCHEDULER_SCRIPTING
				).put(
					"service.ranking", Integer.MAX_VALUE
				).build());

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.portal.scheduler.internal.configuration." +
						"SchedulerEngineHelperConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"auditSchedulerJobEnabled", true
					).build());
			LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				SchedulerEngineHelperImplTest.class.getName(),
				LoggerTestUtil.ERROR)) {

			String jobName =
				"com.liferay.portal.scheduler.internal." +
					"SchedulerEngineHelperImpl$1";
			String message = RandomTestUtil.randomString();
			Date startDate = new Date();

			_schedulerEngineHelper.addScriptingJob(
				_triggerFactory.createTrigger(
					jobName, jobName, startDate,
					new Date(startDate.getTime() + 1000), 1, TimeUnit.DAY),
				StorageType.MEMORY, null, "groovy",
				StringBundler.concat(
					"com.liferay.portal.kernel.log.LogFactoryUtil.getLog(\"",
					SchedulerEngineHelperImplTest.class.getName(),
					"\").error(\"", message, "\")"));

			Assert.assertTrue(
				countDownLatch.await(1, java.util.concurrent.TimeUnit.SECONDS));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(message, logEntry.getMessage());
		}
		finally {
			serviceRegistration1.unregister();
			serviceRegistration2.unregister();
		}
	}

	@Inject
	private DestinationFactory _destinationFactory;

	@Inject
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Inject
	private TriggerFactory _triggerFactory;

}