/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.db.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.test.util.JournalFolderFixture;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
public class OracleDBCTTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testDiscardCTEntryWithOver1000Entries() throws Exception {
		CTCollection ctCollection = _ctCollectionService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		JournalFolder journalFolder = null;

		try (LoggingTimer loggingTimer = new LoggingTimer();
			SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			journalFolder = _journalFolderFixture.addFolder(
				_group.getGroupId(), RandomTestUtil.randomString());

			for (int i = 0; i < _BATCH_SIZE; i++) {
				_journalFolderFixture.addFolder(
					_group.getGroupId(), journalFolder.getFolderId(),
					RandomTestUtil.randomString());
			}
		}

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_ctCollectionService.discardCTEntry(
				ctCollection.getCtCollectionId(),
				_classNameLocalService.getClassNameId(JournalFolder.class),
				journalFolder.getFolderId());
		}

		Assert.assertEquals(
			0,
			_ctEntryLocalService.getCTCollectionCTEntriesCount(
				ctCollection.getCtCollectionId()));
	}

	@Test
	public void testMoveCTEntryWithOver1000Entries() throws Exception {
		CTCollection fromCTCollection = _ctCollectionService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		CTCollection toCTCollection = _ctCollectionService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		JournalFolder journalFolder = null;

		try (LoggingTimer loggingTimer = new LoggingTimer();
			SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					fromCTCollection.getCtCollectionId())) {

			journalFolder = _journalFolderFixture.addFolder(
				_group.getGroupId(), RandomTestUtil.randomString());

			for (int i = 0; i < _BATCH_SIZE; i++) {
				_journalFolderFixture.addFolder(
					_group.getGroupId(), journalFolder.getFolderId(),
					RandomTestUtil.randomString());
			}
		}

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_ctCollectionService.moveCTEntry(
				fromCTCollection.getCtCollectionId(),
				toCTCollection.getCtCollectionId(),
				_classNameLocalService.getClassNameId(JournalFolder.class),
				journalFolder.getFolderId());
		}

		Assert.assertEquals(
			0,
			_ctEntryLocalService.getCTCollectionCTEntriesCount(
				fromCTCollection.getCtCollectionId()));

		Assert.assertNotEquals(
			0,
			_ctEntryLocalService.getCTCollectionCTEntriesCount(
				toCTCollection.getCtCollectionId()));
	}

	@Test
	public void testPublishCTCollectionWithOver1000Entries() throws Exception {
		CTCollection ctCollection = _ctCollectionService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		try (LoggingTimer loggingTimer = new LoggingTimer();
			SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			for (int i = 0; i < _BATCH_SIZE; i++) {
				_journalFolderFixture.addFolder(
					_group.getGroupId(), RandomTestUtil.randomString());
			}

			_ctCollectionService.publishCTCollection(
				TestPropsValues.getUserId(), ctCollection.getCtCollectionId());
		}

		ctCollection = _ctCollectionLocalService.getCTCollection(
			ctCollection.getCtCollectionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, ctCollection.getStatus());

		ctCollection = _ctCollectionService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		try (LoggingTimer loggingTimer = new LoggingTimer();
			SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			for (JournalFolder journalFolder :
					_journalFolderLocalService.getFolders(
						_group.getGroupId())) {

				journalFolder.setName(RandomTestUtil.randomString());

				_journalFolderLocalService.updateJournalFolder(journalFolder);
			}

			_ctCollectionService.publishCTCollection(
				TestPropsValues.getUserId(), ctCollection.getCtCollectionId());
		}

		ctCollection = _ctCollectionLocalService.getCTCollection(
			ctCollection.getCtCollectionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, ctCollection.getStatus());

		ctCollection = _ctCollectionService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		try (LoggingTimer loggingTimer = new LoggingTimer();
			SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			for (JournalFolder journalFolder :
					_journalFolderLocalService.getFolders(
						_group.getGroupId())) {

				_journalFolderLocalService.deleteFolder(journalFolder);
			}

			_ctCollectionService.publishCTCollection(
				TestPropsValues.getUserId(), ctCollection.getCtCollectionId());
		}

		ctCollection = _ctCollectionLocalService.getCTCollection(
			ctCollection.getCtCollectionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, ctCollection.getStatus());
	}

	private static final int _BATCH_SIZE = 1001;

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTCollectionService _ctCollectionService;

	@Inject
	private static JournalFolderLocalService _journalFolderLocalService;

	@Inject
	private CTEntryLocalService _ctEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private final JournalFolderFixture _journalFolderFixture =
		new JournalFolderFixture(_journalFolderLocalService);

}