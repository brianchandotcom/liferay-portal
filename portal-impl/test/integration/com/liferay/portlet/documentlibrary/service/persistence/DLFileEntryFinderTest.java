/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.spring.hibernate.LastSessionRecorderUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;
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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		_folder = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Folder A",
			StringPool.BLANK, serviceContext);

		DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_folder.getFolderId(), "Folder B", StringPool.BLANK,
			serviceContext);

		Folder folder = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_folder.getFolderId(), "Folder C", StringPool.BLANK,
			serviceContext);

		DLAppServiceUtil.moveFolderToTrash(folder.getFolderId());

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), _group.getGroupId());

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			user.getUserId(), _group.getGroupId(), _folder.getFolderId(),
			"FE1.txt", ContentTypes.TEXT_PLAIN, "FE1.txt", null,
			WorkflowConstants.ACTION_PUBLISH);

		LiferayFileEntry liferayFileEntry = (LiferayFileEntry)fileEntry;

		DLFileEntry dlFileEntry = liferayFileEntry.getDLFileEntry();

		dlFileEntry.setExtraSettings("extra.settings=true");
		dlFileEntry.setSmallImageId(_SMALL_IMAGE_ID);

		dlFileEntry = DLFileEntryLocalServiceUtil.updateDLFileEntry(
			dlFileEntry);

		_dlFileVersion = dlFileEntry.getFileVersion();

		DLAppTestUtil.addFileEntry(
			_group.getGroupId(), _folder.getFolderId(), "FE2.pdf",
			ContentTypes.APPLICATION_PDF, "FE2.pdf");

		fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), _folder.getFolderId(), false, "FE3.txt",
			"FE3.txt");

		fileEntry = DLAppTestUtil.updateFileEntry(
			_group.getGroupId(), fileEntry.getFileEntryId(), "FE3.txt",
			"FE3.txt");

		dlFileEntry = ((LiferayFileEntry)fileEntry).getDLFileEntry();

		dlFileEntry.setDescription("FE3.txt");

		DLFileEntryLocalServiceUtil.updateDLFileEntry(dlFileEntry);

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		dlFileVersion.setExtraSettings("extra.settings=true");

		DLFileVersionLocalServiceUtil.updateDLFileVersion(dlFileVersion);

		FinderCacheUtil.clearCache();

		DLAppServiceUtil.moveFileEntryToTrash(fileEntry.getFileEntryId());
	}

	@Test
	public void testCountByExtraSettings() throws Exception {
		Assert.assertEquals(2, DLFileEntryFinderUtil.countByExtraSettings());
	}

	@Test
	public void testCountByG_U_F_M() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(3, doCountBy_G_U_F_M(0, null, queryDefinition));
		Assert.assertEquals(
			2, doCountBy_G_U_F_M(_folder.getUserId(), null, queryDefinition));
		Assert.assertEquals(
			2, doCountBy_G_U_F_M(0, ContentTypes.TEXT_PLAIN, queryDefinition));
		Assert.assertEquals(
			1,
			doCountBy_G_U_F_M(
				_folder.getUserId(), ContentTypes.TEXT_PLAIN, queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(2, doCountBy_G_U_F_M(0, null, queryDefinition));
		Assert.assertEquals(
			1, doCountBy_G_U_F_M(_folder.getUserId(), null, queryDefinition));
		Assert.assertEquals(
			1,
			doCountBy_G_U_F_M(0, ContentTypes.TEXT_PLAIN, queryDefinition));
		Assert.assertEquals(
			0,
			doCountBy_G_U_F_M(
				_folder.getUserId(), ContentTypes.TEXT_PLAIN, queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(2, doCountBy_G_U_F_M(0, null, queryDefinition));
		Assert.assertEquals(
			1, doCountBy_G_U_F_M(_folder.getUserId(), null, queryDefinition));
		Assert.assertEquals(
			1,
			doCountBy_G_U_F_M(0, ContentTypes.TEXT_PLAIN, queryDefinition));
		Assert.assertEquals(
			0,
			doCountBy_G_U_F_M(
				_folder.getUserId(), ContentTypes.TEXT_PLAIN, queryDefinition));

	}

	@Test
	public void testFindByAnyImageId() throws Exception {
		DLFileEntry dlFileEntry = DLFileEntryFinderUtil.findByAnyImageId(
			_SMALL_IMAGE_ID);

		Assert.assertEquals("FE1.txt", dlFileEntry.getTitle());
	}

	@Test
	public void testFindByG_U_F_M() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		List<DLFileEntry> dlFileEntries = doFindBy_G_U_F_M(
			_folder.getUserId(), ContentTypes.TEXT_PLAIN, queryDefinition);

		Assert.assertEquals(1, dlFileEntries.size());

		DLFileEntry dlFileEntry = dlFileEntries.get(0);

		Assert.assertEquals("FE3.txt", dlFileEntry.getDescription());

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		dlFileEntries = doFindBy_G_U_F_M(
			_folder.getUserId(), null, queryDefinition);

		Assert.assertEquals(dlFileEntries.size(), 1);

		dlFileEntry = dlFileEntries.get(0);

		Assert.assertEquals("FE2.pdf", dlFileEntry.getTitle());

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		dlFileEntries = doFindBy_G_U_F_M(
			0, ContentTypes.TEXT_PLAIN, queryDefinition);

		Assert.assertEquals(1, dlFileEntries.size());

		dlFileEntry = dlFileEntries.get(0);

		Assert.assertEquals("FE1.txt", dlFileEntry.getTitle());
	}

	@Test
	public void testFindByMisversioned() throws Exception {
		_dlFileVersion.setFileEntryId(ServiceTestUtil.randomLong());

		DLFileVersionLocalServiceUtil.updateDLFileVersion(_dlFileVersion);

		List<DLFileEntry> dlFileEntries =
			DLFileEntryFinderUtil.findByMisversioned();

		Assert.assertEquals(1, dlFileEntries.size());

		DLFileEntry dlFileEntry = dlFileEntries.get(0);

		Assert.assertEquals("FE1.txt", dlFileEntry.getTitle());
	}

	@Test
	public void testFindByNoAssets() throws Exception {
		AssetEntryLocalServiceUtil.deleteEntry(
			DLFileEntry.class.getName(), _dlFileVersion.getFileEntryId());

		LastSessionRecorderUtil.syncLastSessionState();

		List<DLFileEntry> dlFileEntries =
			DLFileEntryFinderUtil.findByNoAssets();

		Assert.assertEquals(1, dlFileEntries.size());

		DLFileEntry dlFileEntry = dlFileEntries.get(0);

		Assert.assertEquals("FE1.txt", dlFileEntry.getTitle());
	}

	protected int doCountBy_G_U_F_M(
			long userId, String mimeType, QueryDefinition queryDefinition)
		throws Exception {

		List<Long> folderIds = ListUtil.toList(
			new long[] {_folder.getFolderId()});

		String[] mimeTypes = null;

		if (mimeType != null) {
			mimeTypes = new String[] {mimeType};
		}

		return DLFileEntryFinderUtil.countByG_U_F_M(
			_group.getGroupId(), userId, folderIds, mimeTypes, queryDefinition);
	}

	protected List<DLFileEntry> doFindBy_G_U_F_M(
			long userId, String mimeType, QueryDefinition queryDefinition)
		throws Exception {

		List<Long> folderIds = ListUtil.toList(
			new long[] {_folder.getFolderId()});

		String[] mimeTypes = null;

		if (mimeType != null) {
			mimeTypes = new String[] {mimeType};
		}

		return DLFileEntryFinderUtil.findByG_U_F_M(
			_group.getGroupId(), userId, folderIds, mimeTypes, queryDefinition);
	}

	private static final long _SMALL_IMAGE_ID = 1234L;

	private DLFileVersion _dlFileVersion;
	private Folder _folder;
	private Group _group;

}