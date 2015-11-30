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

package com.liferay.document.library.lar.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.document.library.web.lar.DLPortletDataHandler;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.lar.test.BasePortletDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.randomizerbumpers.TikaSafeRandomizerBumper;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;
import com.liferay.portlet.dynamicdatamapping.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.ManifestSummary;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@RunWith(Arquillian.class)
@Sync
public class DLPortletDataHandlerTest extends BasePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();
	}

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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		Folder parentFolder = DLAppServiceUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"parent", RandomTestUtil.randomString(), serviceContext);

		Folder childFolder = DLAppServiceUtil.addFolder(
			group.getGroupId(), parentFolder.getFolderId(), "child",
			RandomTestUtil.randomString(), serviceContext);

		DLAppServiceUtil.moveFolderToTrash(childFolder.getFolderId());

		DLAppServiceUtil.moveFolderToTrash(parentFolder.getFolderId());

		DLAppServiceUtil.deleteFolder(parentFolder.getFolderId());

		GroupLocalServiceUtil.deleteGroup(group);

		int foldersCount = DLFolderLocalServiceUtil.getFoldersCount(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(0, foldersCount);
	}

	@Override
	protected void addParameters(Map<String, String[]> parameterMap) {
		addBooleanParameter(
			parameterMap, DLPortletDataHandler.NAMESPACE, "repositories", true);
	}

	protected void addRepositoryEntries() throws Exception {
		long classNameId = PortalUtil.getClassNameId(
			LiferayRepository.class.getName());

		Repository repository = RepositoryLocalServiceUtil.addRepository(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(), classNameId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			PortletKeys.BACKGROUND_TASK, StringPool.BLANK,
			PortletKeys.BACKGROUND_TASK, new UnicodeProperties(), true,
			ServiceContextTestUtil.getServiceContext());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId(), TestPropsValues.getUserId());

		Folder folder = DLAppServiceUtil.addFolder(
			repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), repository.getRepositoryId(),
			folder.getFolderId(), RandomTestUtil.randomString() + ".txt",
			ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE),
			serviceContext);
	}

	@Override
	protected void addStagedModels() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId(), TestPropsValues.getUserId());

		Folder folder = DLAppServiceUtil.addFolder(
			stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			stagingGroup.getGroupId(), DLFileEntryMetadata.class.getName());

		portletDataContext.isPathProcessed(
			ExportImportPathUtil.getModelPath(ddmStructure));

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.addFileEntryType(
				TestPropsValues.getUserId(), stagingGroup.getGroupId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				new long[] {ddmStructure.getStructureId()}, serviceContext);

		DLAppTestUtil.populateServiceContext(
			serviceContext, dlFileEntryType.getFileEntryTypeId());

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			folder.getFolderId(), RandomTestUtil.randomString() + ".txt",
			ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE),
			serviceContext);

		DLAppLocalServiceUtil.addFileShortcut(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			folder.getFolderId(), fileEntry.getFileEntryId(), serviceContext);
	}

	@Override
	protected String getPortletId() {
		return DLPortletKeys.DOCUMENT_LIBRARY;
	}

}