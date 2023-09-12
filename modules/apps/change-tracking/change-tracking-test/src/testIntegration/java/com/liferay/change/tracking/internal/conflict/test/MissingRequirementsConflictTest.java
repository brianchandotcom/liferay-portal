/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.conflict.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.conflict.ConflictInfo;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
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
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class MissingRequirementsConflictTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testMissingParentFolderProduction() throws Exception {
		_testMissingConflicts(false);
	}

	@Test
	public void testMissingParentFolderPublication() throws Exception {
		_testMissingConflicts(true);
	}

	private CTCollection _addCTCollection(String name) throws Exception {
		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0,
			MissingRequirementsConflictTest.class.getSimpleName() + " " + name,
			null);

		_ctCollections.add(ctCollection);

		return ctCollection;
	}

	private void _testMissingConflicts(boolean publication) throws Exception {
		CTCollection ctCollection1 = _addCTCollection("1");

		JournalFolder rootFolder = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection1.getCtCollectionId())) {

			rootFolder = _journalFolderFixture.addFolder(
				_group.getGroupId(), "Root Folder");
		}

		_ctProcessLocalService.addCTProcess(
			ctCollection1.getUserId(), ctCollection1.getCtCollectionId());

		CTCollection ctCollection2 = _addCTCollection("2");

		JournalFolder childFolder = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection2.getCtCollectionId())) {

			childFolder = _journalFolderFixture.addFolder(
				_group.getGroupId(), rootFolder.getFolderId(), "Child Folder");
		}

		Map<Long, List<ConflictInfo>> conflictsMap =
			_ctCollectionLocalService.checkConflicts(ctCollection2);

		Assert.assertTrue(conflictsMap.toString(), conflictsMap.isEmpty());

		if (publication) {
			CTCollection ctCollection3 = _addCTCollection("3");

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						ctCollection3.getCtCollectionId())) {

				_journalFolderLocalService.deleteFolder(
					rootFolder.getFolderId());
			}

			conflictsMap = _ctCollectionLocalService.checkConflicts(
				ctCollection2);

			Assert.assertTrue(conflictsMap.toString(), conflictsMap.isEmpty());

			_ctProcessLocalService.addCTProcess(
				ctCollection3.getUserId(), ctCollection3.getCtCollectionId());
		}
		else {
			_journalFolderLocalService.deleteFolder(rootFolder.getFolderId());
		}

		conflictsMap = _ctCollectionLocalService.checkConflicts(ctCollection2);

		Assert.assertFalse(conflictsMap.toString(), conflictsMap.isEmpty());

		List<ConflictInfo> conflictInfos = conflictsMap.remove(
			_classNameLocalService.getClassNameId(JournalFolder.class));

		Assert.assertNotNull(conflictInfos);

		Assert.assertEquals(conflictInfos.toString(), 1, conflictInfos.size());

		ConflictInfo conflictInfo = conflictInfos.get(0);

		Assert.assertEquals(
			childFolder.getFolderId(), conflictInfo.getSourcePrimaryKey());

		Assert.assertFalse(conflictInfo.isResolved());

		Assert.assertTrue(conflictsMap.toString(), conflictsMap.isEmpty());
	}

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTProcessLocalService _ctProcessLocalService;

	@Inject
	private static JournalFolderLocalService _journalFolderLocalService;

	@DeleteAfterTestRun
	private final List<CTCollection> _ctCollections = new ArrayList<>();

	@DeleteAfterTestRun
	private Group _group;

	private final JournalFolderFixture _journalFolderFixture =
		new JournalFolderFixture(_journalFolderLocalService);

}