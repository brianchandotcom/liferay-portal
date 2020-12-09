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

package com.liferay.change.tracking.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.test.util.JournalFolderFixture;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.portal.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
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
public class CTEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_journalFolderClassNameId = _classNameLocalService.getClassNameId(
			JournalFolder.class);
		_journalFolderFixture = new JournalFolderFixture(
			_journalFolderLocalService);
	}

	@Test
	public void testGetCTRowCTCollectionId() throws Exception {
		CTCollection ctCollection1 = _createCTCollection();

		JournalFolder journalFolder = null;

		String folderName1 = "Test folder name 1";

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					ctCollection1.getCtCollectionId())) {

			journalFolder = _journalFolderFixture.addFolder(
				_group.getGroupId(), folderName1);
		}

		CTEntry ctEntry1 = _ctEntryLocalService.fetchCTEntry(
			ctCollection1.getCtCollectionId(), _journalFolderClassNameId,
			journalFolder.getFolderId());

		_assertCTRowCTCollectionId(
			ctCollection1.getCtCollectionId(), ctEntry1,
			journalFolder.getFolderId(), folderName1);

		_ctProcessLocalService.addCTProcess(
			TestPropsValues.getUserId(), ctCollection1.getCtCollectionId());

		_assertCTRowCTCollectionId(
			CTConstants.CT_COLLECTION_ID_PRODUCTION, ctEntry1,
			journalFolder.getFolderId(), folderName1);

		CTCollection ctCollection2 = _createCTCollection();

		CTEntry ctEntry2 = null;

		String folderName2 = "Test folder name 2";

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					ctCollection2.getCtCollectionId())) {

			journalFolder = _journalFolderLocalService.fetchFolder(
				journalFolder.getPrimaryKey());

			_assertCTRowCTCollectionId(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, ctEntry1,
				journalFolder.getFolderId(), folderName1);

			journalFolder.setName(folderName2);

			journalFolder = _journalFolderLocalService.updateJournalFolder(
				journalFolder);

			_assertCTRowCTCollectionId(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, ctEntry1,
				journalFolder.getFolderId(), folderName1);

			ctEntry2 = _ctEntryLocalService.fetchCTEntry(
				ctCollection2.getCtCollectionId(), _journalFolderClassNameId,
				journalFolder.getFolderId());

			_assertCTRowCTCollectionId(
				ctCollection2.getCtCollectionId(), ctEntry2,
				journalFolder.getFolderId(), folderName2);
		}

		_assertCTRowCTCollectionId(
			CTConstants.CT_COLLECTION_ID_PRODUCTION, ctEntry1,
			journalFolder.getFolderId(), folderName1);

		_ctProcessLocalService.addCTProcess(
			TestPropsValues.getUserId(), ctCollection2.getCtCollectionId());

		_assertCTRowCTCollectionId(
			ctCollection2.getCtCollectionId(), ctEntry1,
			journalFolder.getFolderId(), folderName1);

		_assertCTRowCTCollectionId(
			CTConstants.CT_COLLECTION_ID_PRODUCTION, ctEntry2,
			journalFolder.getFolderId(), folderName2);

		CTCollection ctCollection3 = _createCTCollection();

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					ctCollection3.getCtCollectionId())) {

			_assertCTRowCTCollectionId(
				ctCollection2.getCtCollectionId(), ctEntry1,
				journalFolder.getFolderId(), folderName1);

			_journalFolderLocalService.deleteFolder(
				journalFolder.getFolderId());

			_assertCTRowCTCollectionId(
				ctCollection2.getCtCollectionId(), ctEntry1,
				journalFolder.getFolderId(), folderName1);
		}

		_ctProcessLocalService.addCTProcess(
			TestPropsValues.getUserId(), ctCollection3.getCtCollectionId());

		_assertCTRowCTCollectionId(
			ctCollection2.getCtCollectionId(), ctEntry1,
			journalFolder.getFolderId(), folderName1);

		_assertCTRowCTCollectionId(
			ctCollection3.getCtCollectionId(), ctEntry2,
			journalFolder.getFolderId(), folderName2);
	}

	private void _assertCTRowCTCollectionId(
			long ctCollectionId, CTEntry ctEntry, long folderId,
			String folderName)
		throws Exception {

		Assert.assertEquals(
			ctCollectionId,
			_ctEntryLocalService.getCTRowCTCollectionId(ctEntry));

		try (SafeClosable safeClosable1 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId);
			SafeClosable safeClosable2 = CTSQLModeThreadLocal.setCTSQLMode(
				CTSQLModeThreadLocal.CTSQLMode.CT_ONLY)) {

			JournalFolder journalFolder =
				_journalFolderLocalService.getJournalFolder(folderId);

			Assert.assertEquals(folderName, journalFolder.getName());
		}
	}

	private CTCollection _createCTCollection() throws Exception {
		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		_ctCollections.add(ctCollection);

		return ctCollection;
	}

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTEntryLocalService _ctEntryLocalService;

	@Inject
	private static CTProcessLocalService _ctProcessLocalService;

	@Inject
	private static JournalFolderLocalService _journalFolderLocalService;

	@DeleteAfterTestRun
	private final List<CTCollection> _ctCollections = new ArrayList<>();

	@DeleteAfterTestRun
	private Group _group;

	private long _journalFolderClassNameId;
	private JournalFolderFixture _journalFolderFixture;

}