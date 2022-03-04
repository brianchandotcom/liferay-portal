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

package com.liferay.document.library.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.test.util.DLAppTestUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManagerUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.RepositoryLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.DateTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.repository.portletrepository.PortletRepository;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Máté Thurzó
 */
@RunWith(Arquillian.class)
public class FileEntryStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

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
	public void testExportImportCheckedOutFileEntryAfterInitialPublication()
		throws Exception {

		ExportImportThreadLocal.setPortletStagingInProcess(true);

		try {
			String fileName = "PDF_Test.pdf";

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					liveGroup.getGroupId(), TestPropsValues.getUserId());

			FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
				null, TestPropsValues.getUserId(), liveGroup.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
				ContentTypes.APPLICATION_PDF,
				FileUtil.getBytes(getClass(), "dependencies/" + fileName), null,
				null, serviceContext);

			DLFileEntryLocalServiceUtil.checkOutFileEntry(
				TestPropsValues.getUserId(), fileEntry.getFileEntryId(),
				serviceContext);

			exportImportStagedModelFromLiveToStaging(fileEntry);

			FileEntry stagingGroupFileEntry =
				DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
					fileEntry.getUuid(), stagingGroup.getGroupId());

			exportImportStagedModel(stagingGroupFileEntry);
		}
		finally {
			ExportImportThreadLocal.setPortletStagingInProcess(false);
		}
	}

	@Test
	public void testExportImportFileExtension() throws Exception {
		String fileName = "PDF_Test.pdf";

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
			ContentTypes.APPLICATION_PDF,
			FileUtil.getBytes(getClass(), "dependencies/" + fileName), null,
			null, serviceContext);

		exportImportStagedModel(fileEntry);

		FileEntry importedFileEntry =
			DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
				fileEntry.getUuid(), liveGroup.getGroupId());

		Assert.assertEquals("pdf", importedFileEntry.getExtension());

		String title = RandomTestUtil.randomString() + ".awesome";

		DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), StringPool.BLANK,
			ContentTypes.TEXT_PLAIN, title, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, DLVersionNumberIncrease.MINOR, (byte[])null, null,
			null, serviceContext);

		exportImportStagedModel(fileEntry);

		importedFileEntry = DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
			fileEntry.getUuid(), liveGroup.getGroupId());

		Assert.assertEquals("pdf", importedFileEntry.getExtension());
	}

	@Test
	public void testExportImportRequiredFieldInDocumentType() throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			liveGroup.getGroupId(), DLFileEntryMetadata.class.getName());

		DLFileEntryType dlFileEntryType = addDLFileEntryType(
			liveGroup.getGroupId(), ddmStructure.getStructureId());

		ExportImportThreadLocal.setPortletStagingInProcess(true);

		try {
			String fileName = "PDF_Test.pdf";

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					liveGroup.getGroupId(), TestPropsValues.getUserId());

			serviceContext.setAttribute(
				"fileEntryTypeId", dlFileEntryType.getFileEntryTypeId());

			FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
				null, TestPropsValues.getUserId(), liveGroup.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
				ContentTypes.APPLICATION_PDF,
				FileUtil.getBytes(getClass(), "dependencies/" + fileName), null,
				null, serviceContext);

			DDMForm ddmForm = ddmStructure.getDDMForm();

			List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

			ddmFormFields.forEach(
				ddmFormField -> ddmFormField.setRequired(true));

			DDMStructureLocalServiceUtil.updateStructure(
				ddmStructure.getUserId(), ddmStructure.getStructureId(),
				ddmForm, ddmStructure.getDDMFormLayout(), serviceContext);

			exportImportStagedModelFromLiveToStaging(fileEntry);

			FileEntry stagingGroupFileEntry =
				DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
					fileEntry.getUuid(), stagingGroup.getGroupId());

			exportImportStagedModel(stagingGroupFileEntry);
		}
		finally {
			ExportImportThreadLocal.setPortletStagingInProcess(false);
		}
	}

	@Test
	public void testExportsTheVersionAfterDeletingOnStaging() throws Exception {
		ExportImportThreadLocal.setPortletStagingInProcess(true);

		try {
			FileEntry fileEntry = addStagedModel(
				stagingGroup, addCompanyDependencies());

			exportImportStagedModel(fileEntry = addVersion(fileEntry));
			exportImportStagedModel(fileEntry = addVersion(fileEntry));
			exportImportStagedModel(fileEntry = _deleteLastVersion(fileEntry));

			FileEntry importedFileEntry = getStagedModel(
				fileEntry.getUuid(), liveGroup);

			Assert.assertEquals(
				fileEntry.getVersion(), importedFileEntry.getVersion());
		}
		finally {
			ExportImportThreadLocal.setPortletStagingInProcess(false);
		}
	}

	@Test
	public void testExportsTheVersionOnStaging() throws Exception {
		ExportImportThreadLocal.setPortletStagingInProcess(true);

		try {
			FileEntry fileEntry = addStagedModel(
				stagingGroup, addCompanyDependencies());

			fileEntry = addVersion(fileEntry);
			fileEntry = addVersion(fileEntry);
			fileEntry = addVersion(fileEntry);

			exportImportStagedModel(fileEntry);

			FileEntry importedFileEntry = getStagedModel(
				fileEntry.getUuid(), liveGroup);

			Assert.assertEquals(
				fileEntry.getVersion(), importedFileEntry.getVersion());
		}
		finally {
			ExportImportThreadLocal.setPortletStagingInProcess(false);
		}
	}

	@Test
	public void testsExportImportNondefaultRepository() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId(), TestPropsValues.getUserId());

		Repository repository = RepositoryLocalServiceUtil.addRepository(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			PortalUtil.getClassNameId(PortletRepository.class.getName()),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "com.liferay.blogs",
			"test repository", DLPortletKeys.DOCUMENT_LIBRARY,
			new UnicodeProperties(), true, serviceContext);

		Folder folder = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), repository.getRepositoryId(),
			repository.getDlFolderId(), "testFolder", "test folder",
			serviceContext);

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), repository.getRepositoryId(),
			folder.getFolderId(), "test.txt", "text/plain", new byte[] {0, 1},
			null, null, serviceContext);

		exportImportStagedModel(fileEntry);

		FileEntry importedFileEntry =
			DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
				fileEntry.getUuid(), liveGroup.getGroupId());

		Folder importedFolder = DLAppLocalServiceUtil.getFolder(
			importedFileEntry.getFolderId());

		Repository importedRepository =
			RepositoryLocalServiceUtil.getRepository(
				importedFileEntry.getRepositoryId());

		Folder repositoryFolder = DLAppLocalServiceUtil.getFolder(
			importedRepository.getDlFolderId());

		Assert.assertEquals(
			importedRepository.getDlFolderId(),
			importedFolder.getParentFolderId());
		Assert.assertEquals(
			repositoryFolder.getRepositoryId(),
			importedRepository.getRepositoryId());
	}

	protected Map<String, List<StagedModel>> addCompanyDependencies()
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		Company company = CompanyLocalServiceUtil.fetchCompany(
			stagingGroup.getCompanyId());

		Group companyGroup = company.getGroup();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			companyGroup.getGroupId(), DLFileEntryMetadata.class.getName());

		addDependentStagedModel(
			dependentStagedModelsMap,
			DDMStructureManagerUtil.getDDMStructureModelClass(), ddmStructure);

		DLFileEntryType dlFileEntryType = addDLFileEntryType(
			companyGroup.getGroupId(), ddmStructure.getStructureId());

		addDependentStagedModel(
			dependentStagedModelsMap, DLFileEntryType.class, dlFileEntryType);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId(), TestPropsValues.getUserId());

		Folder folder = DLAppServiceUtil.addFolder(
			stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		addDependentStagedModel(
			dependentStagedModelsMap, DLFolder.class, folder);

		return dependentStagedModelsMap;
	}

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new LinkedHashMap<>();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DLFileEntryMetadata.class.getName());

		DLFileEntryType dlFileEntryType = addDLFileEntryType(
			group.getGroupId(), ddmStructure.getStructureId());

		addDependentStagedModel(
			dependentStagedModelsMap, DLFileEntryType.class, dlFileEntryType);

		addDependentStagedModel(
			dependentStagedModelsMap, DDMStructure.class, ddmStructure);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		Folder folder = DLAppServiceUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		addDependentStagedModel(
			dependentStagedModelsMap, DLFolder.class, folder);

		return dependentStagedModelsMap;
	}

	protected DLFileEntryType addDLFileEntryType(
			long groupId, long ddmStructureId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId());

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.addFileEntryType(
				TestPropsValues.getUserId(), groupId,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				new long[] {ddmStructureId}, serviceContext);

		DDMStructureManagerUtil.updateStructureKey(
			ddmStructureId, DLUtil.getDDMStructureKey(dlFileEntryType));

		return dlFileEntryType;
	}

	@Override
	protected FileEntry addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> folderDependentStagedModels =
			dependentStagedModelsMap.get(DLFolder.class.getSimpleName());

		Folder folder = (Folder)folderDependentStagedModels.get(0);

		List<StagedModel> dlFileEntryTypeDependentStagedModels =
			dependentStagedModelsMap.get(DLFileEntryType.class.getSimpleName());

		DLFileEntryType dlFileEntryType =
			(DLFileEntryType)dlFileEntryTypeDependentStagedModels.get(0);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		DLAppTestUtil.populateServiceContext(
			serviceContext, dlFileEntryType.getFileEntryTypeId());

		return DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			folder.getFolderId(), RandomTestUtil.randomString() + ".txt",
			ContentTypes.TEXT_PLAIN, TestDataConstants.TEST_BYTE_ARRAY, null,
			null, serviceContext);
	}

	@Override
	protected FileEntry addVersion(StagedModel stagedModel) throws Exception {
		FileEntry fileEntry = (FileEntry)stagedModel;

		return DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), StringPool.BLANK,
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MINOR, (byte[])null, null, null,
			ServiceContextThreadLocal.getServiceContext());
	}

	@Override
	protected FileEntry getStagedModel(String uuid, Group group)
		throws PortalException {

		return DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
			uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return DLFileEntry.class;
	}

	@Override
	protected boolean isCommentableStagedModel() {
		return true;
	}

	@Override
	protected boolean isVersionableStagedModel() {
		return true;
	}

	protected void validateCompanyDependenciesImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		Class<?> ddmStructureClass =
			DDMStructureManagerUtil.getDDMStructureModelClass();

		List<StagedModel> ddmStructureDependentStagedModels =
			dependentStagedModelsMap.get(ddmStructureClass.getSimpleName());

		Assert.assertEquals(
			ddmStructureDependentStagedModels.toString(), 1,
			ddmStructureDependentStagedModels.size());

		DDMStructure ddmStructure =
			(DDMStructure)ddmStructureDependentStagedModels.get(0);

		Assert.assertNull(
			"Company DDM structure dependency should not be imported",
			DDMStructureManagerUtil.fetchStructureByUuidAndGroupId(
				ddmStructure.getUuid(), group.getGroupId()));

		List<StagedModel> dlFileEntryTypesDependentStagedModels =
			dependentStagedModelsMap.get(DLFileEntryType.class.getSimpleName());

		Assert.assertEquals(
			dlFileEntryTypesDependentStagedModels.toString(), 1,
			dlFileEntryTypesDependentStagedModels.size());

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

		Class<?> ddmStructureClass =
			DDMStructureManagerUtil.getDDMStructureModelClass();

		List<StagedModel> ddmStructureDependentStagedModels =
			dependentStagedModelsMap.get(ddmStructureClass.getSimpleName());

		Assert.assertEquals(
			ddmStructureDependentStagedModels.toString(), 1,
			ddmStructureDependentStagedModels.size());

		DDMStructure ddmStructure =
			(DDMStructure)ddmStructureDependentStagedModels.get(0);

		DDMStructureManagerUtil.getStructureByUuidAndGroupId(
			ddmStructure.getUuid(), group.getGroupId());

		List<StagedModel> dlFileEntryTypesDependentStagedModels =
			dependentStagedModelsMap.get(DLFileEntryType.class.getSimpleName());

		Assert.assertEquals(
			dlFileEntryTypesDependentStagedModels.toString(), 1,
			dlFileEntryTypesDependentStagedModels.size());

		DLFileEntryType dlFileEntryType =
			(DLFileEntryType)dlFileEntryTypesDependentStagedModels.get(0);

		DLFileEntryTypeLocalServiceUtil.getDLFileEntryTypeByUuidAndGroupId(
			dlFileEntryType.getUuid(), group.getGroupId());

		List<StagedModel> foldersDependentStagedModels =
			dependentStagedModelsMap.get(DLFolder.class.getSimpleName());

		Assert.assertEquals(
			foldersDependentStagedModels.toString(), 1,
			foldersDependentStagedModels.size());

		Folder folder = (Folder)foldersDependentStagedModels.get(0);

		DLFolderLocalServiceUtil.getDLFolderByUuidAndGroupId(
			folder.getUuid(), group.getGroupId());
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		DateTestUtil.assertEquals(
			stagedModel.getCreateDate(), importedStagedModel.getCreateDate());

		Assert.assertEquals(
			stagedModel.getUuid(), importedStagedModel.getUuid());

		FileEntry fileEntry = (FileEntry)stagedModel;
		FileEntry importedFileEntry = (FileEntry)importedStagedModel;

		Assert.assertEquals(
			fileEntry.getExternalReferenceCode(),
			importedFileEntry.getExternalReferenceCode());
		Assert.assertEquals(
			fileEntry.getFileName(), importedFileEntry.getFileName());
		Assert.assertEquals(
			fileEntry.getExtension(), importedFileEntry.getExtension());
		Assert.assertEquals(
			fileEntry.getMimeType(), importedFileEntry.getMimeType());
		Assert.assertEquals(fileEntry.getTitle(), importedFileEntry.getTitle());
		Assert.assertEquals(
			fileEntry.getDescription(), importedFileEntry.getDescription());
		Assert.assertEquals(fileEntry.getSize(), importedFileEntry.getSize());

		FileVersion latestFileVersion = null;

		try {
			latestFileVersion = fileEntry.getLatestFileVersion();
		}
		catch (NoSuchFileEntryException noSuchFileEntryException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchFileEntryException);
			}

			return;
		}

		FileVersion importedLatestFileVersion =
			importedFileEntry.getLatestFileVersion();

		Assert.assertEquals(
			latestFileVersion.getUuid(), importedLatestFileVersion.getUuid());
		Assert.assertEquals(
			latestFileVersion.getFileName(),
			importedLatestFileVersion.getFileName());
		Assert.assertEquals(
			latestFileVersion.getExtension(),
			importedLatestFileVersion.getExtension());
		Assert.assertEquals(
			latestFileVersion.getMimeType(),
			importedLatestFileVersion.getMimeType());
		Assert.assertEquals(
			latestFileVersion.getTitle(), importedLatestFileVersion.getTitle());
		Assert.assertEquals(
			latestFileVersion.getDescription(),
			importedLatestFileVersion.getDescription());
		Assert.assertEquals(
			latestFileVersion.getSize(), importedLatestFileVersion.getSize());
		Assert.assertEquals(
			latestFileVersion.getStatus(),
			importedLatestFileVersion.getStatus());
	}

	private FileEntry _deleteLastVersion(FileEntry fileEntry) throws Exception {
		DLFileEntryLocalServiceUtil.deleteFileVersion(
			TestPropsValues.getUserId(), fileEntry.getFileEntryId(),
			fileEntry.getVersion());

		return DLAppLocalServiceUtil.getFileEntry(fileEntry.getFileEntryId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileEntryStagedModelDataHandlerTest.class);

}