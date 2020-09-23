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

package com.liferay.change.tracking.internal.reference.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.conflict.ConflictInfo;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.test.util.JournalFolderFixture;
import com.liferay.petra.lang.SafeClosable;
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
	public void testMissingParentFolder() throws Exception {
		CTCollection ctCollection1 = _addCTCollection("1");

		JournalFolder rootFolder = null;

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					ctCollection1.getCtCollectionId())) {

			rootFolder = _journalFolderFixture.addFolder(
				_group.getGroupId(), "Root Folder");
		}

		_ctProcessLocalService.addCTProcess(
			ctCollection1.getUserId(), ctCollection1.getCtCollectionId());

		CTCollection ctCollection2 = _addCTCollection("2");

		JournalFolder childFolder = null;

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					ctCollection2.getCtCollectionId())) {

			childFolder = _journalFolderFixture.addFolder(
				_group.getGroupId(), rootFolder.getFolderId(), "Child Folder");
		}

		Map<Long, List<ConflictInfo>> conflictsMap =
			_ctCollectionLocalService.checkConflicts(ctCollection2);

		Assert.assertTrue(conflictsMap.toString(), conflictsMap.isEmpty());

		_journalFolderLocalService.deleteFolder(rootFolder.getFolderId());

		conflictsMap = _ctCollectionLocalService.checkConflicts(ctCollection2);

		List<ConflictInfo> conflictInfos = conflictsMap.remove(
			_classNameLocalService.getClassNameId(JournalFolder.class));

		Assert.assertNotNull(conflictInfos);

		Assert.assertEquals(conflictInfos.toString(), 1, conflictInfos.size());

		ConflictInfo conflictInfo = conflictInfos.get(0);

		Assert.assertEquals(
			childFolder.getFolderId(), conflictInfo.getSourcePrimaryKey());

		Assert.assertTrue(conflictsMap.toString(), conflictsMap.isEmpty());
	}

	private CTCollection _addCTCollection(String name) throws Exception {
		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			MissingRequirementsConflictTest.class.getSimpleName() + " " + name,
			null);

		_ctCollections.add(ctCollection);

		return ctCollection;
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