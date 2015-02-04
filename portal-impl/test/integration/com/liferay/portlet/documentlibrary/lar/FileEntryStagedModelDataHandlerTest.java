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

import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.lar.test.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Mate Thurzo
 */
public class FileEntryStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			TransactionalTestRule.INSTANCE);

	@Test
	public void testCompanyScopeDependencies() throws Exception {
		Map<String, List<StagedModel>> dependentStagedModelsMap =
			addCompanyDependencies();

		StagedModel stagedModel = addStagedModel(
			stagingGroup, dependentStagedModelsMap);

		exportImportStagedModel(stagedModel);

		validateCompanyDependenciesImport(dependentStagedModelsMap, liveGroup);
	}

	@Test
	public void testExportImportFileExtension() throws Exception {
		String sourceFileName = RandomTestUtil.randomString() + ".pdf";

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, sourceFileName);

		exportImportStagedModel(fileEntry);

		FileEntry importedFileEntry =
			DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
				fileEntry.getUuid(), liveGroup.getGroupId());

		Assert.assertEquals(importedFileEntry.getExtension(), "pdf");

		String title = RandomTestUtil.randomString() + ".awesome";

		DLAppTestUtil.updateFileEntry(
			stagingGroup.getGroupId(), fileEntry.getFileEntryId(),
			StringPool.BLANK, title);

		exportImportStagedModel(fileEntry);

		importedFileEntry = DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
			fileEntry.getUuid(), liveGroup.getGroupId());

		Assert.assertEquals(importedFileEntry.getExtension(), "pdf");
	}

	protected Map<String, List<StagedModel>> addCompanyDependencies()
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		Company company = CompanyLocalServiceUtil.fetchCompany(
			stagingGroup.getCompanyId());

		Group companyGroup = company.getGroup();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			companyGroup.getGroupId(), DLFileEntryType.class.getName());

		addDependentStagedModel(
			dependentStagedModelsMap, DDMStructure.class, ddmStructure);

		DLFileEntryType dlFileEntryType = DLAppTestUtil.addDLFileEntryType(
			companyGroup.getGroupId(), ddmStructure.getStructureId());

		addDependentStagedModel(
			dependentStagedModelsMap, DLFileEntryType.class, dlFileEntryType);

		Folder folder = DLAppTestUtil.addFolder(
			stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		addDependentStagedModel(
			dependentStagedModelsMap, DLFolder.class, folder);

		return dependentStagedModelsMap;
	}

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DLFileEntryType.class.getName());

		addDependentStagedModel(
			dependentStagedModelsMap, DDMStructure.class, ddmStructure);

		DLFileEntryType dlFileEntryType = DLAppTestUtil.addDLFileEntryType(
			group.getGroupId(), ddmStructure.getStructureId());

		addDependentStagedModel(
			dependentStagedModelsMap, DLFileEntryType.class, dlFileEntryType);

		Folder folder = DLAppTestUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		addDependentStagedModel(
			dependentStagedModelsMap, DLFolder.class, folder);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> folderDependentStagedModels =
			dependentStagedModelsMap.get(DLFolder.class.getSimpleName());

		Folder folder = (Folder)folderDependentStagedModels.get(0);

		List<StagedModel> fileEntryTypeDependentStagedModels =
			dependentStagedModelsMap.get(DLFileEntryType.class.getSimpleName());

		DLFileEntryType dlFileEntryType =
			(DLFileEntryType)fileEntryTypeDependentStagedModels.get(0);

		return DLAppTestUtil.addFileEntry(
			group.getGroupId(), folder.getFolderId(),
			RandomTestUtil.randomString(),
			dlFileEntryType.getFileEntryTypeId());
	}

	protected void exportImportStagedModel(StagedModel stagedModel)
		throws Exception {

		initExport();

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, stagedModel);

		initImport();

		StagedModel exportedStagedModel = readExportedStagedModel(stagedModel);

		Assert.assertNotNull(exportedStagedModel);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedStagedModel);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
				uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return DLFileEntry.class;
	}

	@Override
	protected boolean isCommentableStagedModel() {
		return true;
	}

	protected void validateCompanyDependenciesImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> ddmStructureDependentStagedModels =
			dependentStagedModelsMap.get(DDMStructure.class.getSimpleName());

		Assert.assertEquals(1, ddmStructureDependentStagedModels.size());

		DDMStructure ddmStructure =
			(DDMStructure)ddmStructureDependentStagedModels.get(0);

		Assert.assertNull(
			"Company DDM structure dependency should not be imported",
			DDMStructureLocalServiceUtil.fetchDDMStructureByUuidAndGroupId(
				ddmStructure.getUuid(), group.getGroupId()));

		List<StagedModel> dlFileEntryTypesDependentStagedModels =
			dependentStagedModelsMap.get(DLFileEntryType.class.getSimpleName());

		Assert.assertEquals(1, dlFileEntryTypesDependentStagedModels.size());

		DLFileEntryType dlFileEntryType =
			(DLFileEntryType)dlFileEntryTypesDependentStagedModels.get(0);

		Assert.assertNull(
			"Company DL file entry dependency should not be imported",
			DLFileEntryTypeLocalServiceUtil.
				fetchDLFileEntryTypeByUuidAndGroupId(
					dlFileEntryType.getUuid(), group.getGroupId()));
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> ddmStructureDependentStagedModels =
			dependentStagedModelsMap.get(DDMStructure.class.getSimpleName());

		Assert.assertEquals(1, ddmStructureDependentStagedModels.size());

		DDMStructure ddmStructure =
			(DDMStructure)ddmStructureDependentStagedModels.get(0);

		DDMStructureLocalServiceUtil.getDDMStructureByUuidAndGroupId(
			ddmStructure.getUuid(), group.getGroupId());

		List<StagedModel> dlFileEntryTypesDependentStagedModels =
			dependentStagedModelsMap.get(DLFileEntryType.class.getSimpleName());

		Assert.assertEquals(1, dlFileEntryTypesDependentStagedModels.size());

		DLFileEntryType dlFileEntryType =
			(DLFileEntryType)dlFileEntryTypesDependentStagedModels.get(0);

		DLFileEntryTypeLocalServiceUtil.getDLFileEntryTypeByUuidAndGroupId(
			dlFileEntryType.getUuid(), group.getGroupId());

		List<StagedModel> foldersDependentStagedModels =
			dependentStagedModelsMap.get(DLFolder.class.getSimpleName());

		Assert.assertEquals(1, foldersDependentStagedModels.size());

		Folder folder = (Folder)foldersDependentStagedModels.get(0);

		DLFolderLocalServiceUtil.getDLFolderByUuidAndGroupId(
			folder.getUuid(), group.getGroupId());
	}

}