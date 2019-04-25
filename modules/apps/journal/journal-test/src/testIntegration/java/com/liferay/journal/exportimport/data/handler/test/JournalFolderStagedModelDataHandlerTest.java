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

package com.liferay.journal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
public class JournalFolderStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testDoubleExportImport() throws Exception {
		Map<String, List<StagedModel>> dependentStagedModelsMap =
			addDependentStagedModelsMap(stagingGroup);

		StagedModel stagedModel = addStagedModel(
			stagingGroup, dependentStagedModelsMap);

		ExportImportThreadLocal.setPortletImportInProcess(true);

		try {
			exportImportStagedModel(stagedModel);
		}
		finally {
			ExportImportThreadLocal.setPortletImportInProcess(false);
		}

		StagedModel importedStagedModel = getStagedModel(
			stagedModel.getUuid(), liveGroup);

		Assert.assertNotNull(importedStagedModel);

		ExportImportThreadLocal.setPortletImportInProcess(true);

		try {
			exportImportStagedModel(stagedModel);
		}
		finally {
			ExportImportThreadLocal.setPortletImportInProcess(false);
		}

		importedStagedModel = getStagedModel(stagedModel.getUuid(), liveGroup);

		Assert.assertNotNull(importedStagedModel);
	}

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		JournalFolder folder = JournalTestUtil.addFolder(
			group.getGroupId(), RandomTestUtil.randomString());

		addDependentStagedModel(
			dependentStagedModelsMap, JournalFolder.class, folder);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			JournalFolder.class.getSimpleName());

		JournalFolder folder = (JournalFolder)dependentStagedModels.get(0);

		return JournalTestUtil.addFolder(
			group.getGroupId(), folder.getFolderId(),
			RandomTestUtil.randomString());
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return JournalFolderLocalServiceUtil.getJournalFolderByUuidAndGroupId(
			uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return JournalFolder.class;
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			JournalFolder.class.getSimpleName());

		Assert.assertEquals(
			dependentStagedModels.toString(), 1, dependentStagedModels.size());

		JournalFolder folder = (JournalFolder)dependentStagedModels.get(0);

		JournalFolderLocalServiceUtil.getJournalFolderByUuidAndGroupId(
			folder.getUuid(), group.getGroupId());
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		Assert.assertTrue(
			stagedModel.getCreateDate() + " " +
				importedStagedModel.getCreateDate(),
			DateUtil.equals(
				stagedModel.getCreateDate(),
				importedStagedModel.getCreateDate()));
		Assert.assertEquals(
			stagedModel.getUuid(), importedStagedModel.getUuid());

		JournalFolder folder = (JournalFolder)stagedModel;
		JournalFolder importedFolder = (JournalFolder)importedStagedModel;

		Assert.assertEquals(folder.getName(), importedFolder.getName());
		Assert.assertEquals(
			folder.getDescription(), importedFolder.getDescription());
	}

}