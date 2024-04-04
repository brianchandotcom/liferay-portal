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
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@DataGuard(scope = DataGuard.Scope.NONE)
@RunWith(Arquillian.class)
public class OracleDBCTTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_ctCollection1 = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);
		_ctCollection2 = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);
		_ctCollection3 = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		_ctCollectionLocalService.deleteCTCollection(_ctCollection1);
		_ctCollectionLocalService.deleteCTCollection(_ctCollection2);
		_ctCollectionLocalService.deleteCTCollection(_ctCollection3);

		GroupTestUtil.deleteGroup(_group);
	}

	@Test
	public void testMoveAndDiscardCTEntryWithOver1000Entries()
		throws Exception {

		JournalFolder journalFolder = null;

		try (LoggingTimer loggingTimer = new LoggingTimer();
			SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection1.getCtCollectionId())) {

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
				_ctCollection1.getCtCollectionId(),
				_ctCollection2.getCtCollectionId(),
				_classNameLocalService.getClassNameId(JournalFolder.class),
				journalFolder.getFolderId());
		}

		Assert.assertEquals(
			0,
			_ctEntryLocalService.getCTCollectionCTEntriesCount(
				_ctCollection1.getCtCollectionId()));

		Assert.assertNotEquals(
			0,
			_ctEntryLocalService.getCTCollectionCTEntriesCount(
				_ctCollection2.getCtCollectionId()));

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection2.getCtCollectionId())) {

			journalFolder = _journalFolderLocalService.getJournalFolder(
				journalFolder.getFolderId());
		}

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_ctCollectionService.discardCTEntry(
				_ctCollection2.getCtCollectionId(),
				_classNameLocalService.getClassNameId(JournalFolder.class),
				journalFolder.getFolderId());
		}

		Assert.assertEquals(
			0,
			_ctEntryLocalService.getCTCollectionCTEntriesCount(
				_ctCollection2.getCtCollectionId()));
	}

	@Test
	public void testPublishCTCollectionWithOver1000Entries() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection1.getCtCollectionId())) {

			for (int i = 0; i < _BATCH_SIZE; i++) {
				_journalFolderFixture.addFolder(
					_group.getGroupId(), RandomTestUtil.randomString());
			}

			_ctCollectionService.publishCTCollection(
				TestPropsValues.getUserId(),
				_ctCollection1.getCtCollectionId());
		}

		_ctCollection1 = _ctCollectionLocalService.getCTCollection(
			_ctCollection1.getCtCollectionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, _ctCollection1.getStatus());

		try (LoggingTimer loggingTimer = new LoggingTimer();
			SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection2.getCtCollectionId())) {

			for (JournalFolder journalFolder :
					_journalFolderLocalService.getFolders(
						_group.getGroupId())) {

				journalFolder.setName(RandomTestUtil.randomString());

				_journalFolderLocalService.updateJournalFolder(journalFolder);
			}

			_ctCollectionService.publishCTCollection(
				TestPropsValues.getUserId(),
				_ctCollection2.getCtCollectionId());
		}

		_ctCollection2 = _ctCollectionLocalService.getCTCollection(
			_ctCollection2.getCtCollectionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, _ctCollection2.getStatus());

		try (LoggingTimer loggingTimer = new LoggingTimer();
			SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection3.getCtCollectionId())) {

			for (JournalFolder journalFolder :
					_journalFolderLocalService.getFolders(
						_group.getGroupId())) {

				_journalFolderLocalService.deleteFolder(journalFolder);
			}

			_ctCollectionService.publishCTCollection(
				TestPropsValues.getUserId(),
				_ctCollection3.getCtCollectionId());
		}

		_ctCollection3 = _ctCollectionLocalService.getCTCollection(
			_ctCollection3.getCtCollectionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, _ctCollection3.getStatus());
	}

	private static final int _BATCH_SIZE = 1;

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTCollectionService _ctCollectionService;

	@Inject
	private static JournalFolderLocalService _journalFolderLocalService;

	private CTCollection _ctCollection1;
	private CTCollection _ctCollection2;
	private CTCollection _ctCollection3;

	@Inject
	private CTEntryLocalService _ctEntryLocalService;

	private Group _group;
	private final JournalFolderFixture _journalFolderFixture =
		new JournalFolderFixture(_journalFolderLocalService);

}