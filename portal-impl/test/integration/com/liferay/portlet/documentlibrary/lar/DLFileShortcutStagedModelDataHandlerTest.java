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

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.lar.test.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Mate Thurzo
 */
public class DLFileShortcutStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			TransactionalTestRule.INSTANCE);

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		Folder folder = DLAppTestUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		addDependentStagedModel(
			dependentStagedModelsMap, DLFolder.class, folder);

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());

		addDependentStagedModel(
			dependentStagedModelsMap, DLFileEntry.class, fileEntry);

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

		List<StagedModel> fileEntryDependentStagedModels =
			dependentStagedModelsMap.get(DLFileEntry.class.getSimpleName());

		FileEntry fileEntry = (FileEntry)fileEntryDependentStagedModels.get(0);

		return DLAppTestUtil.addDLFileShortcut(
			fileEntry, group.getGroupId(), folder.getFolderId());
	}

	@Override
	protected AssetEntry fetchAssetEntry(StagedModel stagedModel, Group group)
		throws Exception {

		return null;
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
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> fileEntryDependentStagedModels =
			dependentStagedModelsMap.get(DLFileEntry.class.getSimpleName());

		Assert.assertEquals(1, fileEntryDependentStagedModels.size());

		FileEntry fileEntry = (FileEntry)fileEntryDependentStagedModels.get(0);

		DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
			fileEntry.getUuid(), group.getGroupId());

		List<StagedModel> folderDependentStagedModels =
			dependentStagedModelsMap.get(DLFolder.class.getSimpleName());

		Assert.assertEquals(1, folderDependentStagedModels.size());

		Folder folder = (Folder)folderDependentStagedModels.get(0);

		DLFolderLocalServiceUtil.getDLFolderByUuidAndGroupId(
			folder.getUuid(), group.getGroupId());
	}

}