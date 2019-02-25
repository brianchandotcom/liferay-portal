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

package com.liferay.document.library.change.tracking.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.CTEngineManager;
import com.liferay.change.tracking.CTManager;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.ByteArrayInputStream;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luiz Marins
 */
@RunWith(Arquillian.class)
public class DLFileEntryChangesRegisterCTEntityTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_userId = TestPropsValues.getUserId();

		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), _userId);

		_ctEngineManager.createCTCollection(
			_userId, _CT_COLLECTION_NAME, StringPool.BLANK);
	}

	@After
	public void tearDown() throws PortalException {
		_deleteCTCollections();

		_ctEngineManager.disableChangeTracking(TestPropsValues.getCompanyId());
	}

	@Test
	public void testAddFileEntryCreatesCTEntry() throws PortalException {
		FileEntry fileEntry = _addFileEntry();

		List<CTEntry> modelChangeCTEntries = _ctManager.getModelChangeCTEntries(
			_userId, fileEntry.getPrimaryKey());

		Assert.assertEquals(
			"Amount of model change CTEntry is incorrect", 1,
			modelChangeCTEntries.size());

		_assertCTEntry(
			modelChangeCTEntries.get(0), fileEntry, "1.0",
			CTConstants.CT_CHANGE_TYPE_ADDITION);
	}

	@Test
	public void testDeleteFileEntryDeletesAllCTEntries()
		throws PortalException {

		FileEntry fileEntry = _addFileEntry();

		fileEntry = _dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), StringPool.BLANK,
			ContentTypes.TEXT_PLAIN, "file updated.txt", StringPool.BLANK,
			StringPool.BLANK, DLVersionNumberIncrease.AUTOMATIC, null, 0,
			_getServiceContext());

		_dlAppService.deleteFileEntry(fileEntry.getFileEntryId());

		List<CTEntry> modelChangeCTEntries = _ctManager.getModelChangeCTEntries(
			_userId, fileEntry.getPrimaryKey());

		Assert.assertEquals(
			"Amount of model change CTEntry is incorrect", 0,
			modelChangeCTEntries.size());
	}

	@Test
	public void testUpdateAssetTagRegisterChange() throws PortalException {
		FileEntry fileEntry = _addFileEntry();

		ServiceContext serviceContext = _getServiceContext();

		serviceContext.setAssetTagNames(new String[] {"tag1"});

		fileEntry = _dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), StringPool.BLANK,
			ContentTypes.TEXT_PLAIN, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, DLVersionNumberIncrease.AUTOMATIC, null, 0,
			serviceContext);

		List<CTEntry> modelChangeCTEntries = _ctManager.getModelChangeCTEntries(
			_userId, fileEntry.getPrimaryKey());

		Assert.assertEquals(
			"Amount of model change CTEntry is incorrect", 2,
			modelChangeCTEntries.size());
	}

	@Test
	public void testUpdateFileEntryCreatesCTEntry() throws PortalException {
		FileEntry fileEntry = _addFileEntry();

		fileEntry = _dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), StringPool.BLANK,
			ContentTypes.TEXT_PLAIN, "file updated.txt", StringPool.BLANK,
			StringPool.BLANK, DLVersionNumberIncrease.AUTOMATIC, null, 0,
			_getServiceContext());

		List<CTEntry> modelChangeCTEntries = _ctManager.getModelChangeCTEntries(
			_userId, fileEntry.getPrimaryKey());

		Assert.assertEquals(
			"Amount of model change CTEntry is incorrect", 2,
			modelChangeCTEntries.size());

		_assertCTEntry(
			modelChangeCTEntries.get(0), fileEntry, "1.0",
			CTConstants.CT_CHANGE_TYPE_ADDITION);

		_assertCTEntry(
			modelChangeCTEntries.get(1), fileEntry, "1.1",
			CTConstants.CT_CHANGE_TYPE_MODIFICATION);
	}

	private FileEntry _addFileEntry() throws PortalException {
		String content = StringUtil.randomString();

		return _dlAppService.addFileEntry(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"file.txt", ContentTypes.TEXT_PLAIN, "file.txt", StringPool.BLANK,
			StringPool.BLANK, new ByteArrayInputStream(content.getBytes()), 0,
			_getServiceContext());
	}

	private void _assertCTEntry(
			CTEntry ctEntry, FileEntry fileEntry, String version,
			int changeType)
		throws PortalException {

		Assert.assertEquals(
			"Incorrect change type", changeType, ctEntry.getChangeType());

		Assert.assertEquals(
			"Incorrect resource primary key", fileEntry.getFileEntryId(),
			ctEntry.getResourcePrimKey());

		Assert.assertEquals(
			"Incorrect classPK",
			fileEntry.getFileVersion(version).getFileVersionId(),
			ctEntry.getClassPK());
	}

	private void _deleteCTCollections() throws PortalException {
		List<CTCollection> ctCollections = _ctEngineManager.getCTCollections(
			TestPropsValues.getCompanyId());

		for (CTCollection c : ctCollections) {
			_ctEngineManager.deleteCTCollection(c.getCtCollectionId());
		}
	}

	private ServiceContext _getServiceContext() throws PortalException {
		return ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), _userId);
	}

	private static final String _CT_COLLECTION_NAME = "CTCollection";

	@Inject
	private CTEngineManager _ctEngineManager;

	@Inject
	private CTManager _ctManager;

	@Inject
	private DLAppService _dlAppService;

	@DeleteAfterTestRun
	private Group _group;

	private long _userId;

}