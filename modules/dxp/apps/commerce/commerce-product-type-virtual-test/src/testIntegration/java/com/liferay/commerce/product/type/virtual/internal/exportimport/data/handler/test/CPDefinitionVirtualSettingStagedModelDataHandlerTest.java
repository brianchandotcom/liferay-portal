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

package com.liferay.commerce.product.type.virtual.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.product.type.virtual.constants.VirtualCPTypeConstants;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingLocalServiceUtil;
import com.liferay.commerce.product.type.virtual.test.util.VirtualCPTypeTestUtil;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.lar.test.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Andrea Di Giorgi
 */
@RunWith(Arquillian.class)
@Sync
public class CPDefinitionVirtualSettingStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		long groupId = group.getGroupId();

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			groupId, VirtualCPTypeConstants.NAME);

		addDependentStagedModel(
			dependentStagedModelsMap, CPDefinition.class, cpDefinition);

		FileEntry fileEntry = CPTestUtil.addFileEntry(groupId);

		addDependentStagedModel(
			dependentStagedModelsMap, FileEntry.class, fileEntry);

		FileEntry sampleFileEntry = CPTestUtil.addFileEntry(groupId);

		addDependentStagedModel(
			dependentStagedModelsMap, FileEntry.class, sampleFileEntry);

		JournalArticle journalArticle = VirtualCPTypeTestUtil.addJournalArticle(
			groupId);

		addDependentStagedModel(
			dependentStagedModelsMap, JournalArticle.class, journalArticle);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> cpDefinitionDependentStagedModels =
			dependentStagedModelsMap.get(CPDefinition.class.getSimpleName());

		CPDefinition cpDefinition =
			(CPDefinition)cpDefinitionDependentStagedModels.get(0);

		List<StagedModel> fileEntryDependentStagedModels =
			dependentStagedModelsMap.get(FileEntry.class.getSimpleName());

		FileEntry fileEntry = (FileEntry)fileEntryDependentStagedModels.get(0);
		FileEntry sampleFileEntry =
			(FileEntry)fileEntryDependentStagedModels.get(1);

		List<StagedModel> journalArticleDependentStagedModels =
			dependentStagedModelsMap.get(JournalArticle.class.getSimpleName());

		JournalArticle termsOfUseJournalArticle =
			(JournalArticle)journalArticleDependentStagedModels.get(0);

		return VirtualCPTypeTestUtil.addCPDefinitionVirtualSetting(
			group.getGroupId(), cpDefinition.getCPDefinitionId(),
			fileEntry.getFileEntryId(), sampleFileEntry.getFileEntryId(),
			termsOfUseJournalArticle.getResourcePrimKey());
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return CPDefinitionVirtualSettingLocalServiceUtil.
				getCPDefinitionVirtualSettingByUuidAndGroupId(
					uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return CPDefinitionVirtualSetting.class;
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		long groupId = group.getGroupId();

		List<StagedModel> cpDefinitionDependentStagedModels =
			dependentStagedModelsMap.get(CPDefinition.class.getSimpleName());

		Assert.assertEquals(
			cpDefinitionDependentStagedModels.toString(), 1,
			cpDefinitionDependentStagedModels.size());

		CPDefinition cpDefinition =
			(CPDefinition)cpDefinitionDependentStagedModels.get(0);

		CPDefinitionLocalServiceUtil.getCPDefinitionByUuidAndGroupId(
			cpDefinition.getUuid(), groupId);

		List<StagedModel> fileEntryDependentStagedModels =
			dependentStagedModelsMap.get(FileEntry.class.getSimpleName());

		Assert.assertEquals(
			fileEntryDependentStagedModels.toString(), 2,
			fileEntryDependentStagedModels.size());

		for (StagedModel stagedModel : fileEntryDependentStagedModels) {
			FileEntry fileEntry = (FileEntry)stagedModel;

			DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
				fileEntry.getUuid(), fileEntry.getGroupId());
		}

		List<StagedModel> journalArticleDependentStagedModels =
			dependentStagedModelsMap.get(JournalArticle.class.getSimpleName());

		Assert.assertEquals(
			journalArticleDependentStagedModels.toString(), 1,
			journalArticleDependentStagedModels.size());

		JournalArticle journalArticle =
			(JournalArticle)journalArticleDependentStagedModels.get(0);

		JournalArticleLocalServiceUtil.getJournalArticleByUuidAndGroupId(
			journalArticle.getUuid(), groupId);
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			(CPDefinitionVirtualSetting)stagedModel;
		CPDefinitionVirtualSetting importedCPDefinitionVirtualSetting =
			(CPDefinitionVirtualSetting)importedStagedModel;

		Assert.assertEquals(
			cpDefinitionVirtualSetting.isUseUrl(),
			importedCPDefinitionVirtualSetting.isUseUrl());
		Assert.assertEquals(
			cpDefinitionVirtualSetting.getUrl(),
			importedCPDefinitionVirtualSetting.getUrl());
		Assert.assertEquals(
			cpDefinitionVirtualSetting.getActivationStatus(),
			importedCPDefinitionVirtualSetting.getActivationStatus());
		Assert.assertEquals(
			cpDefinitionVirtualSetting.getDuration(),
			importedCPDefinitionVirtualSetting.getDuration());
		Assert.assertEquals(
			cpDefinitionVirtualSetting.getMaxUsages(),
			importedCPDefinitionVirtualSetting.getMaxUsages());
		Assert.assertEquals(
			cpDefinitionVirtualSetting.isUseSample(),
			importedCPDefinitionVirtualSetting.isUseSample());
		Assert.assertEquals(
			cpDefinitionVirtualSetting.isUseSampleUrl(),
			importedCPDefinitionVirtualSetting.isUseSampleUrl());
		Assert.assertEquals(
			cpDefinitionVirtualSetting.getSampleUrl(),
			importedCPDefinitionVirtualSetting.getSampleUrl());
		Assert.assertEquals(
			cpDefinitionVirtualSetting.isTermsOfUseRequired(),
			importedCPDefinitionVirtualSetting.isTermsOfUseRequired());
		Assert.assertEquals(
			cpDefinitionVirtualSetting.getTermsOfUseContentMap(),
			importedCPDefinitionVirtualSetting.getTermsOfUseContentMap());
	}

}