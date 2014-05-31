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

package com.liferay.portlet.asset.lar;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.util.test.AssetTestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.runner.RunWith;

/**
 * @author Mate Thurzo
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class AssetCategoryStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	public static TransactionalTestRule transactionalTestRule =
		new TransactionalTestRule();

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<String, List<StagedModel>>();

		AssetVocabulary vocabulary = AssetTestUtil.addVocabulary(
			group.getGroupId());

		addDependentStagedModel(
			dependentStagedModelsMap, AssetVocabulary.class, vocabulary);

		AssetCategory category = AssetTestUtil.addCategory(
			group.getGroupId(), vocabulary.getVocabularyId());

		addDependentStagedModel(
			dependentStagedModelsMap, AssetCategory.class, category);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> vocabularyDependentStagedModels =
			dependentStagedModelsMap.get(AssetVocabulary.class.getSimpleName());

		AssetVocabulary vocabulary =
			(AssetVocabulary)vocabularyDependentStagedModels.get(0);

		List<StagedModel> categoryDependentStagedModels =
			dependentStagedModelsMap.get(AssetCategory.class.getSimpleName());

		AssetCategory category =
			(AssetCategory)categoryDependentStagedModels.get(0);

		return AssetTestUtil.addCategory(
			group.getGroupId(), vocabulary.getVocabularyId(),
			category.getCategoryId());
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return AssetCategoryLocalServiceUtil.
				getAssetCategoryByUuidAndGroupId(uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return AssetCategory.class;
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> categoryDependentStagedModels =
			dependentStagedModelsMap.get(AssetCategory.class.getSimpleName());

		Assert.assertEquals(1, categoryDependentStagedModels.size());

		AssetCategory category =
			(AssetCategory)categoryDependentStagedModels.get(0);

		AssetCategoryLocalServiceUtil.getAssetCategoryByUuidAndGroupId(
			category.getUuid(), group.getGroupId());

		List<StagedModel> vocabularyDependentStagedModels =
			dependentStagedModelsMap.get(AssetVocabulary.class.getSimpleName());

		Assert.assertEquals(1, vocabularyDependentStagedModels.size());

		AssetVocabulary vocabulary =
			(AssetVocabulary)vocabularyDependentStagedModels.get(0);

		AssetVocabularyLocalServiceUtil.getAssetVocabularyByUuidAndGroupId(
			vocabulary.getUuid(), group.getGroupId());
	}

}