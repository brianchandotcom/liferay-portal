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

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Mate Thurzo
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLFileShortcutStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<String, List<StagedModel>>();

		Folder folder = DLAppTestUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceTestUtil.randomString());

		addDependentStagedModel(dependentStagedModelsMap, Folder.class, folder);

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceTestUtil.randomString());

		addDependentStagedModel(
			dependentStagedModelsMap, FileEntry.class, fileEntry);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> folderDependentStagedModels =
			dependentStagedModelsMap.get(Folder.class.getSimpleName());

		Folder folder = (Folder)folderDependentStagedModels.get(0);

		List<StagedModel> fileEntryDependentStagedModels =
			dependentStagedModelsMap.get(FileEntry.class.getSimpleName());

		FileEntry fileEntry = (FileEntry)fileEntryDependentStagedModels.get(0);

		return DLAppTestUtil.addDLFileShortcut(
			fileEntry, group.getGroupId(), folder.getFolderId());
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return DLFileShortcutLocalServiceUtil.
				getDLFileShortcutByUuidAndGroupId(uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return DLFileShortcut.class;
	}

	@Override
	protected void validateAssets(
			String classUuid, StagedModelAssets stagedModelAssets, Group group)
		throws Exception {

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			group.getGroupId(), classUuid);

		List<AssetCategory> assetCategories =
			AssetCategoryLocalServiceUtil.getEntryCategories(
				assetEntry.getEntryId());

		Assert.assertEquals(1, assetCategories.size());

		AssetCategory assetCategory = stagedModelAssets.getAssetCategory();
		AssetCategory importedAssetCategory = assetCategories.get(0);

		Assert.assertEquals(
			assetCategory.getUuid(), importedAssetCategory.getUuid());

		AssetVocabulary assetVocabulary =
			stagedModelAssets.getAssetVocabulary();
		AssetVocabulary importedAssetVocabulary =
			AssetVocabularyLocalServiceUtil.getVocabulary(
				importedAssetCategory.getVocabularyId());

		Assert.assertEquals(
			assetVocabulary.getUuid(), importedAssetVocabulary.getUuid());
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> fileEntryDependentStagedModels =
			dependentStagedModelsMap.get(FileEntry.class.getSimpleName());

		Assert.assertEquals(1, fileEntryDependentStagedModels.size());

		FileEntry fileEntry = (FileEntry)fileEntryDependentStagedModels.get(0);

		DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
			fileEntry.getUuid(), group.getGroupId());

		List<StagedModel> folderDependentStagedModels =
			dependentStagedModelsMap.get(Folder.class.getSimpleName());

		Assert.assertEquals(1, folderDependentStagedModels.size());

		Folder folder = (Folder)folderDependentStagedModels.get(0);

		DLFolderLocalServiceUtil.getDLFolderByUuidAndGroupId(
			folder.getUuid(), group.getGroupId());
	}

}