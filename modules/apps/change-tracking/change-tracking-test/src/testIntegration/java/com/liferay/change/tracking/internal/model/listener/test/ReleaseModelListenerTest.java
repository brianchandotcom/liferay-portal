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

package com.liferay.change.tracking.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.change.tracking.service.CTSchemaVersionLocalService;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class ReleaseModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_release = _releaseLocalService.addRelease(
			ReleaseModelListenerTest.class.getSimpleName(), "1.0.0");

		_ctCollection = _ctCollectionLocalService.addCTCollection(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			ReleaseModelListenerTest.class.getSimpleName(), null);

		_ctPreferences = _ctPreferencesLocalService.getCTPreferences(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		_ctPreferences.setCtCollectionId(_ctCollection.getCtCollectionId());

		_ctPreferences = _ctPreferencesLocalService.updateCTPreferences(
			_ctPreferences);
	}

	@Test
	public void testStalePublishIsRejected() throws Exception {
		Assert.assertTrue(
			_ctSchemaVersionLocalService.isLatestSchemaVersion(
				_ctCollection.getSchemaVersionId()));

		_releaseLocalService.updateRelease(
			ReleaseModelListenerTest.class.getSimpleName(), "1.1.0", "1.0.0");

		Assert.assertFalse(
			_ctSchemaVersionLocalService.isLatestSchemaVersion(
				_ctCollection.getSchemaVersionId()));

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.getCTPreferences(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Assert.assertEquals(
			CTConstants.CT_COLLECTION_ID_PRODUCTION,
			ctPreferences.getCtCollectionId());

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.background.task.internal.messaging." +
						"BackgroundTaskMessageListener",
					Level.ERROR)) {

			_ctProcessLocalService.addCTProcess(
				TestPropsValues.getUserId(), _ctCollection.getCtCollectionId());

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			ThrowableInformation throwableInformation =
				loggingEvent.getThrowableInformation();

			Assert.assertNotNull(throwableInformation);

			Throwable throwable = throwableInformation.getThrowable();

			Assert.assertNotNull(throwable);

			String message = throwable.getMessage();

			Assert.assertTrue(message, message.startsWith("Unable to publish"));
		}
	}

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTPreferencesLocalService _ctPreferencesLocalService;

	@Inject
	private static CTProcessLocalService _ctProcessLocalService;

	@Inject
	private static CTSchemaVersionLocalService _ctSchemaVersionLocalService;

	@Inject
	private static ReleaseLocalService _releaseLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@DeleteAfterTestRun
	private CTPreferences _ctPreferences;

	@DeleteAfterTestRun
	private Release _release;

}