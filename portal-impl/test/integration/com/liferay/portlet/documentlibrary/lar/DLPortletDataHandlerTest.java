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

package com.liferay.portlet.documentlibrary.lar;

import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.lar.test.BasePortletDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Zsolt Berentey
 */
public class DLPortletDataHandlerTest extends BasePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
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
			DLFileEntry.class.getName());

		Assert.assertEquals(0, fileEntryModelAdditionCounter.getValue());

		LongWrapper folderModelAdditionCounter = modelAdditionCounters.get(
			DLFolder.class.getName());

		Assert.assertEquals(0, folderModelAdditionCounter.getValue());

		modelAdditionCounters.clear();

		portletDataHandler.exportData(
			portletDataContext, portletId, new PortletPreferencesImpl());

		manifestSummary = portletDataContext.getManifestSummary();

		modelAdditionCounters = manifestSummary.getModelAdditionCounters();

		fileEntryModelAdditionCounter = modelAdditionCounters.get(
			DLFileEntry.class.getName());

		Assert.assertEquals(0, fileEntryModelAdditionCounter.getValue());

		folderModelAdditionCounter = modelAdditionCounters.get(
			DLFolder.class.getName());

		Assert.assertEquals(0, folderModelAdditionCounter.getValue());
	}

	@Test
	public void testDeleteAllFolders() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Folder parentFolder = DLAppTestUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"parent");

		Folder childFolder = DLAppTestUtil.addFolder(
			group.getGroupId(), parentFolder.getFolderId(), "child");

		DLAppServiceUtil.moveFolderToTrash(childFolder.getFolderId());

		DLAppServiceUtil.moveFolderToTrash(parentFolder.getFolderId());

		DLAppServiceUtil.deleteFolder(parentFolder.getFolderId());

		GroupLocalServiceUtil.deleteGroup(group);

		int foldersCount = DLFolderLocalServiceUtil.getFoldersCount(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(0, foldersCount);
	}

	protected void addRepositoryEntries() throws Exception {
		long classNameId = PortalUtil.getClassNameId(
			LiferayRepository.class.getName());

		Repository repository = DLAppTestUtil.addRepository(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(), classNameId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			PortletKeys.BACKGROUND_TASK, StringPool.BLANK,
			PortletKeys.BACKGROUND_TASK, new UnicodeProperties(), true,
			ServiceContextTestUtil.getServiceContext());

		Folder folder = DLAppTestUtil.addFolder(
			stagingGroup.getGroupId(), repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());

		DLAppTestUtil.addFileEntry(
			stagingGroup.getGroupId(), repository.getRepositoryId(),
			folder.getFolderId());
	}

	@Override
	protected void addStagedModels() throws Exception {
		Folder folder = DLAppTestUtil.addFolder(
			stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			stagingGroup.getGroupId(), DLFileEntryType.class.getName());

		portletDataContext.isPathProcessed(
			ExportImportPathUtil.getModelPath(ddmStructure));

		DLFileEntryType dlFileEntryType = DLAppTestUtil.addDLFileEntryType(
			stagingGroup.getGroupId(), ddmStructure.getStructureId());

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			stagingGroup.getGroupId(), folder.getFolderId(),
			RandomTestUtil.randomString(),
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