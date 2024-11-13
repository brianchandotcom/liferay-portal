/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.configuration.CTConflictConfiguration;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.change.tracking.service.CTSchemaVersionLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.test.util.JournalFolderFixture;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
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
	public void testNewReleaseDoesNotExpirePublication() throws Exception {
		Assert.assertTrue(
			_ctSchemaVersionLocalService.isLatestCTSchemaVersion(
				_ctCollection.getSchemaVersionId()));

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, _ctCollection.getStatus());

		_releaseLocalService.addRelease(RandomTestUtil.randomString(), "1.0.0");

		_ctCollection = _ctCollectionLocalService.getCTCollection(
			_ctCollection.getCtCollectionId());

		Assert.assertTrue(
			_ctSchemaVersionLocalService.isLatestCTSchemaVersion(
				_ctCollection.getSchemaVersionId()));

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.getCTPreferences(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Assert.assertEquals(
			_ctCollection.getCtCollectionId(),
			ctPreferences.getCtCollectionId());

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			DDMStructureTestUtil.addStructure(
				TestPropsValues.getGroupId(), JournalArticle.class.getName());
		}

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.background.task.internal.messaging." +
					"BackgroundTaskMessageListener",
				LoggerTestUtil.ERROR)) {

			_ctProcessLocalService.addCTProcess(
				TestPropsValues.getUserId(), _ctCollection.getCtCollectionId());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 0, logEntries.size());
		}

		_ctCollection = _ctCollectionLocalService.getCTCollection(
			_ctCollection.getCtCollectionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, _ctCollection.getStatus());
	}

	@Test
	public void testSchemaVersionCheckDisabled() throws Exception {
		Assert.assertTrue(
			_ctSchemaVersionLocalService.isLatestCTSchemaVersion(
				_ctCollection.getSchemaVersionId()));

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, _ctCollection.getStatus());

		Group group = GroupTestUtil.addGroup();

		JournalFolder productionJournalFolder = _journalFolderFixture.addFolder(
			group.getGroupId(), RandomTestUtil.randomString());

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			_journalFolderFixture.addFolder(
				group.getGroupId(), productionJournalFolder.getFolderId(),
				RandomTestUtil.randomString());

			DDMStructureTestUtil.addStructure(
				TestPropsValues.getGroupId(), JournalArticle.class.getName());
		}

		_journalFolderLocalService.deleteFolder(
			productionJournalFolder.getFolderId());

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						CTConflictConfiguration.class.getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"schemaVersionCheckEnabled", false
						).build())) {

			_releaseLocalService.updateRelease(
				ReleaseModelListenerTest.class.getSimpleName(), "1.1.0",
				"1.0.0");

			_ctCollection = _ctCollectionLocalService.getCTCollection(
				_ctCollection.getCtCollectionId());

			Assert.assertTrue(
				_ctSchemaVersionLocalService.isLatestCTSchemaVersion(
					_ctCollection.getSchemaVersionId()));
			Assert.assertEquals(
				WorkflowConstants.STATUS_DRAFT, _ctCollection.getStatus());
		}
	}

	@Test
	public void testSchemaVersionCheckEnabled() throws Exception {
		Assert.assertTrue(
			_ctSchemaVersionLocalService.isLatestCTSchemaVersion(
				_ctCollection.getSchemaVersionId()));

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, _ctCollection.getStatus());

		Group group = GroupTestUtil.addGroup();

		JournalFolder productionJournalFolder = _journalFolderFixture.addFolder(
			group.getGroupId(), RandomTestUtil.randomString());

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			_journalFolderFixture.addFolder(
				group.getGroupId(), productionJournalFolder.getFolderId(),
				RandomTestUtil.randomString());

			DDMStructureTestUtil.addStructure(
				TestPropsValues.getGroupId(), JournalArticle.class.getName());
		}

		_journalFolderLocalService.deleteFolder(
			productionJournalFolder.getFolderId());

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						CTConflictConfiguration.class.getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"schemaVersionCheckEnabled", true
						).build())) {

			_releaseLocalService.updateRelease(
				ReleaseModelListenerTest.class.getSimpleName(), "1.1.0",
				"1.0.0");

			_ctCollection = _ctCollectionLocalService.getCTCollection(
				_ctCollection.getCtCollectionId());

			Assert.assertFalse(
				_ctSchemaVersionLocalService.isLatestCTSchemaVersion(
					_ctCollection.getSchemaVersionId()));

			CTPreferences ctPreferences =
				_ctPreferencesLocalService.getCTPreferences(
					TestPropsValues.getCompanyId(),
					TestPropsValues.getUserId());

			Assert.assertEquals(
				CTConstants.CT_COLLECTION_ID_PRODUCTION,
				ctPreferences.getCtCollectionId());

			try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.background.task.internal.messaging." +
						"BackgroundTaskMessageListener",
					LoggerTestUtil.ERROR)) {

				_ctProcessLocalService.addCTProcess(
					TestPropsValues.getUserId(),
					_ctCollection.getCtCollectionId());

				List<LogEntry> logEntries = logCapture.getLogEntries();

				Assert.assertEquals(
					logEntries.toString(), 1, logEntries.size());

				LogEntry logEntry = logEntries.get(0);

				Throwable throwable = logEntry.getThrowable();

				Assert.assertNotNull(throwable);

				String message = throwable.getMessage();

				Assert.assertTrue(
					message, message.startsWith("Unable to publish"));
			}

			_ctCollection = _ctCollectionLocalService.getCTCollection(
				_ctCollection.getCtCollectionId());

			Assert.assertEquals(
				WorkflowConstants.STATUS_EXPIRED, _ctCollection.getStatus());
		}
	}

	@Test
	public void testUpdatedReleaseWithoutConflictDoesNotExpirePublication()
		throws Exception {

		Assert.assertTrue(
			_ctSchemaVersionLocalService.isLatestCTSchemaVersion(
				_ctCollection.getSchemaVersionId()));

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, _ctCollection.getStatus());

		_releaseLocalService.updateRelease(
			ReleaseModelListenerTest.class.getSimpleName(), "1.1.0", "1.0.0");

		_ctCollection = _ctCollectionLocalService.getCTCollection(
			_ctCollection.getCtCollectionId());

		Assert.assertTrue(
			_ctSchemaVersionLocalService.isLatestCTSchemaVersion(
				_ctCollection.getSchemaVersionId()));

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.getCTPreferences(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Assert.assertEquals(
			_ctCollection.getCtCollectionId(),
			ctPreferences.getCtCollectionId());

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			DDMStructureTestUtil.addStructure(
				TestPropsValues.getGroupId(), JournalArticle.class.getName());
		}

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.background.task.internal.messaging." +
					"BackgroundTaskMessageListener",
				LoggerTestUtil.ERROR)) {

			_ctProcessLocalService.addCTProcess(
				TestPropsValues.getUserId(), _ctCollection.getCtCollectionId());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 0, logEntries.size());
		}

		_ctCollection = _ctCollectionLocalService.getCTCollection(
			_ctCollection.getCtCollectionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, _ctCollection.getStatus());
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
	private static DLFolderLocalService _dlFolderLocalService;

	@Inject
	private static JournalFolderLocalService _journalFolderLocalService;

	@Inject
	private static ReleaseLocalService _releaseLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@DeleteAfterTestRun
	private CTPreferences _ctPreferences;

	private final JournalFolderFixture _journalFolderFixture =
		new JournalFolderFixture(_journalFolderLocalService);

	@DeleteAfterTestRun
	private Release _release;

}