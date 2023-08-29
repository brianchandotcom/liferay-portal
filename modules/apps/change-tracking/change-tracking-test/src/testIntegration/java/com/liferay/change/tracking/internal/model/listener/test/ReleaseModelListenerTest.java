/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

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
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, ReleaseModelListenerTest.class.getSimpleName(), null);

		_ctPreferences = _ctPreferencesLocalService.getCTPreferences(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		_ctPreferences.setCtCollectionId(_ctCollection.getCtCollectionId());

		_ctPreferences = _ctPreferencesLocalService.updateCTPreferences(
			_ctPreferences);
	}

	@Test
	public void testStalePublishIsRejected() throws Exception {
		Assert.assertTrue(
			_ctSchemaVersionLocalService.isLatestCTSchemaVersion(
				_ctCollection.getSchemaVersionId()));

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, _ctCollection.getStatus());

		_releaseLocalService.updateRelease(
			ReleaseModelListenerTest.class.getSimpleName(), "1.1.0", "1.0.0");

		Assert.assertFalse(
			_ctSchemaVersionLocalService.isLatestCTSchemaVersion(
				_ctCollection.getSchemaVersionId()));

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.getCTPreferences(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Assert.assertEquals(
			CTConstants.CT_COLLECTION_ID_PRODUCTION,
			ctPreferences.getCtCollectionId());

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.background.task.internal.messaging." +
					"BackgroundTaskMessageListener",
				LoggerTestUtil.ERROR)) {

			_ctProcessLocalService.addCTProcess(
				TestPropsValues.getUserId(), _ctCollection.getCtCollectionId());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertNotNull(throwable);

			String message = throwable.getMessage();

			Assert.assertTrue(message, message.startsWith("Unable to publish"));
		}

		_ctCollection = _ctCollectionLocalService.getCTCollection(
			_ctCollection.getCtCollectionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_EXPIRED, _ctCollection.getStatus());
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