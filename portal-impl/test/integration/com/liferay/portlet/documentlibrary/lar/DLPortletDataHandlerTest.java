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

package com.liferay.portlet.documentlibrary.lar;

import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.lar.BasePortletDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderUtil;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.util.DDMStructureTestUtil;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLPortletDataHandlerTest extends BasePortletDataHandlerTestCase {

	@Test
	@Transactional
	public void testCustomRepositoryEntriesExport() throws Exception {
		initExport();

		addRepositoryEntries();

		portletDataContext.setEndDate(getEndDate());

		portletDataHandler.prepareManifestSummary(portletDataContext);

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		Map<String, LongWrapper> modelAdditionCounters =
			manifestSummary.getModelAdditionCounters();

		LongWrapper fileEntryModelAdditionCounter = modelAdditionCounters.get(
			FileEntry.class.getName());

		Assert.assertEquals(0, fileEntryModelAdditionCounter.getValue());

		LongWrapper folderModelAdditionCounter = modelAdditionCounters.get(
			Folder.class.getName());

		Assert.assertEquals(0, folderModelAdditionCounter.getValue());

		modelAdditionCounters.clear();

		portletDataHandler.exportData(
			portletDataContext, portletId, new PortletPreferencesImpl());

		manifestSummary = portletDataContext.getManifestSummary();

		modelAdditionCounters = manifestSummary.getModelAdditionCounters();

		fileEntryModelAdditionCounter = modelAdditionCounters.get(
			FileEntry.class.getName());

		Assert.assertEquals(0, fileEntryModelAdditionCounter.getValue());

		folderModelAdditionCounter = modelAdditionCounters.get(
			Folder.class.getName());

		Assert.assertEquals(0, folderModelAdditionCounter.getValue());
	}

	@Test
	@Transactional
	public void testDeleteAllFolders() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Folder parentFolder = DLAppTestUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"parent");

		Folder childFolder = DLAppTestUtil.addFolder(
			group.getGroupId(), parentFolder.getFolderId(), "child");

		DLAppServiceUtil.moveFolderToTrash(childFolder.getFolderId());

		DLAppServiceUtil.moveFolderToTrash(parentFolder.getFolderId());

		DLFolderLocalServiceUtil.deleteFolder(parentFolder.getFolderId());

		GroupLocalServiceUtil.deleteGroup(group);

		Assert.assertEquals(0, DLFolderUtil.countByGroupId(group.getGroupId()));
	}

	protected void addRepositoryEntries() throws Exception {
		long classNameId = PortalUtil.getClassNameId(
			LiferayRepository.class.getName());

		Repository repository = DLAppTestUtil.addRepository(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(), classNameId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			PortletKeys.BACKGROUND_TASK, StringPool.BLANK,
			PortletKeys.BACKGROUND_TASK, new UnicodeProperties(), true,
			ServiceTestUtil.getServiceContext());

		Folder folder = DLAppTestUtil.addFolder(
			stagingGroup.getGroupId(), repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceTestUtil.randomString());

		DLAppTestUtil.addFileEntry(
			stagingGroup.getGroupId(), repository.getRepositoryId(),
			folder.getFolderId(), ServiceTestUtil.randomString());
	}

	@Override
	protected void addStagedModels() throws Exception {
		Folder folder = DLAppTestUtil.addFolder(
			stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceTestUtil.randomString());

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			stagingGroup.getGroupId(), DLFileEntryType.class.getName());

		portletDataContext.isPathProcessed(
			ExportImportPathUtil.getModelPath(ddmStructure));

		DLFileEntryType dlFileEntryType = DLAppTestUtil.addDLFileEntryType(
			stagingGroup.getGroupId(), ddmStructure.getStructureId());

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			stagingGroup.getGroupId(), folder.getFolderId(),
			ServiceTestUtil.randomString(),
			dlFileEntryType.getFileEntryTypeId());

		DLAppTestUtil.addDLFileShortcut(
			fileEntry, stagingGroup.getGroupId(), folder.getFolderId());
	}

	@Override
	protected PortletDataHandler createPortletDataHandler() {
		return new DLPortletDataHandler();
	}

	@Override
	protected String getPortletId() {
		return PortletKeys.DOCUMENT_LIBRARY;
	}

}