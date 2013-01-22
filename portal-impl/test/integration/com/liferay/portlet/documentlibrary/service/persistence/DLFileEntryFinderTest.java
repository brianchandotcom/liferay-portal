/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.testng.Assert;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class DLFileEntryFinderTest {

	@Before
	public void setUp() throws Exception {
		_group = ServiceTestUtil.addGroup();

		_folder = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Folder A", "",
			ServiceTestUtil.getServiceContext(_group.getGroupId()));

		DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_folder.getFolderId(), "Folder B", "",
			ServiceTestUtil.getServiceContext(_group.getGroupId()));

		Folder folder = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_folder.getFolderId(), "Folder C", "",
			ServiceTestUtil.getServiceContext(_group.getGroupId()));

		DLAppServiceUtil.moveFolderToTrash(folder.getFolderId());

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), _folder.getFolderId(), false, "FE1.txt",
			"FE1.txt");

		DLFileEntry dlFileEntry =
			((LiferayFileEntry)fileEntry).getDLFileEntry();

		dlFileEntry.setUserId(1234);
		dlFileEntry.setSmallImageId(6789);
		dlFileEntry.setExtraSettings("Extra Settings");

		dlFileEntry = DLFileEntryLocalServiceUtil.updateDLFileEntry(
			dlFileEntry);

		_fileVersion = dlFileEntry.getFileVersion();

		_fileVersion.setUserId(1234);
		_fileVersion.setStatus(WorkflowConstants.STATUS_APPROVED);

		DLFileVersionLocalServiceUtil.updateDLFileVersion(_fileVersion);

		DLAppTestUtil.addFileEntry(
			_group.getGroupId(), _folder.getFolderId(),
			ContentTypes.APPLICATION_PDF, "FE2.pdf", "FE2.pdf");

		fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), _folder.getFolderId(), false, "FE3.txt",
			"FE3.txt");

		fileEntry = DLAppTestUtil.updateFileEntry(
			_group.getGroupId(), fileEntry.getFileEntryId(), "FE3.txt",
			"FE3.txt");

		dlFileEntry = ((LiferayFileEntry)fileEntry).getDLFileEntry();

		dlFileEntry.setDescription("FE3.txt");

		DLFileEntryLocalServiceUtil.updateDLFileEntry(dlFileEntry);

		FinderCacheUtil.clearCache();

		LiferayFileVersion fileVersion =
			(LiferayFileVersion)fileEntry.getFileVersion();

		DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();

		dlFileVersion.setExtraSettings("Extra Settings");

		DLFileVersionLocalServiceUtil.updateDLFileVersion(dlFileVersion);

		DLAppServiceUtil.moveFileEntryToTrash(fileEntry.getFileEntryId());
	}

	@Test
	public void testCountByExtraSettings() throws Exception {
		int count = DLFileEntryFinderUtil.countByExtraSettings();

		Assert.assertEquals(count, 2);
	}

	@Test
	public void testCountByG_U_F_M() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(doCountBy_G_U_F_M(false, null, queryDefinition), 2);
		Assert.assertEquals(doCountBy_G_U_F_M(true, null, queryDefinition), 1);
		Assert.assertEquals(
			doCountBy_G_U_F_M(false, _TEXT_PLAIN, queryDefinition), 1);
		Assert.assertEquals(
			doCountBy_G_U_F_M(true, _TEXT_PLAIN, queryDefinition), 0);

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(doCountBy_G_U_F_M(false, null, queryDefinition), 3);
		Assert.assertEquals(doCountBy_G_U_F_M(true, null, queryDefinition), 2);
		Assert.assertEquals(
			doCountBy_G_U_F_M(false, _TEXT_PLAIN, queryDefinition), 2);
		Assert.assertEquals(
			doCountBy_G_U_F_M(true, _TEXT_PLAIN, queryDefinition), 1);

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(doCountBy_G_U_F_M(false, null, queryDefinition), 2);
		Assert.assertEquals(doCountBy_G_U_F_M(true, null, queryDefinition), 1);
		Assert.assertEquals(
			doCountBy_G_U_F_M(false, _TEXT_PLAIN, queryDefinition), 1);
		Assert.assertEquals(
			doCountBy_G_U_F_M(true, _TEXT_PLAIN, queryDefinition), 0);
	}

	@Test
	public void testFindByAnyImageId() throws Exception {
		DLFileEntry dlFileEntry = DLFileEntryFinderUtil.findByAnyImageId(6789);

		Assert.assertEquals(dlFileEntry.getTitle(), "FE1.txt");
	}

	@Test
	public void testFindByG_U_F_M() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		List<DLFileEntry> list = doFindBy_G_U_F_M(
			false, _TEXT_PLAIN, queryDefinition);

		Assert.assertEquals(list.size(), 1);

		DLFileEntry dlFileEntry = list.get(0);

		Assert.assertEquals(dlFileEntry.getTitle(), "FE1.txt");

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		list =  doFindBy_G_U_F_M(true, _TEXT_PLAIN, queryDefinition);

		Assert.assertEquals(list.size(), 1);

		dlFileEntry = list.get(0);

		Assert.assertEquals(dlFileEntry.getDescription(), "FE3.txt");

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		list =  doFindBy_G_U_F_M(true, null, queryDefinition);

		Assert.assertEquals(list.size(), 1);

		dlFileEntry = list.get(0);

		Assert.assertEquals(dlFileEntry.getTitle(), "FE2.pdf");

	}

	@Test
	public void testFindByMisversioned() throws Exception {
		_fileVersion.setFileEntryId(1111);

		DLFileVersionLocalServiceUtil.updateDLFileVersion(_fileVersion);

		List<DLFileEntry> list = DLFileEntryFinderUtil.findByMisversioned();

		Assert.assertEquals(list.size(), 1);

		DLFileEntry dlFileEntry = list.get(0);

		Assert.assertEquals(dlFileEntry.getTitle(), "FE1.txt");
	}

	@Test
	public void testFindByNoAssets() throws Exception {
		AssetEntryLocalServiceUtil.deleteEntry(
			DLFileEntry.class.getName(), _fileVersion.getFileEntryId());

		AssetEntryLocalServiceUtil.fetchEntry(
			DLFileEntry.class.getName(), _fileVersion.getFileEntryId());

		List<DLFileEntry> list = DLFileEntryFinderUtil.findByNoAssets();

		Assert.assertEquals(list.size(), 1);

		DLFileEntry dlFileEntry = list.get(0);

		Assert.assertEquals(dlFileEntry.getTitle(), "FE1.txt");
	}

	protected int doCountBy_G_U_F_M(
			boolean withUserId, String mimeType,
			QueryDefinition queryDefinition)
		throws Exception {

		List<Long> folderIds = ListUtil.toList(
			new long[] {_folder.getFolderId()});

		String[] mimeTypes = null;

		if (mimeType != null) {
			mimeTypes = new String[] {mimeType};
		}

		long userId = 0;

		if (withUserId) {
			userId = _folder.getUserId();
		}

		return DLFileEntryFinderUtil.countByG_U_F_M(
			_group.getGroupId(), userId, folderIds, mimeTypes, queryDefinition);
	}

	protected List<DLFileEntry> doFindBy_G_U_F_M(
			boolean withUserId, String mimeType,
			QueryDefinition queryDefinition)
		throws Exception {

		List<Long> folderIds = ListUtil.toList(
			new long[] {_folder.getFolderId()});

		String[] mimeTypes = null;

		if (mimeType != null) {
			mimeTypes = new String[] {mimeType};
		}

		long userId = 0;

		if (withUserId) {
			userId = _folder.getUserId();
		}

		return DLFileEntryFinderUtil.findByG_U_F_M(
			_group.getGroupId(), userId, folderIds, mimeTypes, queryDefinition);
	}

	private static final String _TEXT_PLAIN = ContentTypes.TEXT_PLAIN;

	private DLFileVersion _fileVersion;
	private Folder _folder;
	private Group _group;

}