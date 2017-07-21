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

package com.liferay.commerce.product.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueLocalServiceUtil;
import com.liferay.commerce.product.service.CPOptionCategoryLocalServiceUtil;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
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
public class CPDefinitionSpecificationOptionValueStagedModelDataHandlerTest
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

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		long groupId = group.getGroupId();

		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(groupId);

		addDependentStagedModel(
			dependentStagedModelsMap, CPDefinition.class, cpDefinition);

		CPOptionCategory cpOptionCategory = CPTestUtil.addCPOptionCategory(
			groupId);

		addDependentStagedModel(
			dependentStagedModelsMap, CPOptionCategory.class, cpOptionCategory);

		CPSpecificationOption cpSpecificationOption =
			CPTestUtil.addCPSpecificationOption(
				groupId, cpOptionCategory.getCPOptionCategoryId());

		addDependentStagedModel(
			dependentStagedModelsMap, CPSpecificationOption.class,
			cpSpecificationOption);

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

		List<StagedModel> cpSpecificationOptionDependentStagedModels =
			dependentStagedModelsMap.get(
				CPSpecificationOption.class.getSimpleName());

		CPSpecificationOption cpSpecificationOption =
			(CPSpecificationOption)
				cpSpecificationOptionDependentStagedModels.get(0);

		List<StagedModel> cpOptionCategoryDependentStagedModels =
			dependentStagedModelsMap.get(
				CPOptionCategory.class.getSimpleName());

		CPOptionCategory cpOptionCategory =
			(CPOptionCategory)cpOptionCategoryDependentStagedModels.get(0);

		return CPTestUtil.addCPDefinitionSpecificationOptionValue(
			group.getGroupId(), cpDefinition.getCPDefinitionId(),
			cpSpecificationOption.getCPSpecificationOptionId(),
			cpOptionCategory.getCPOptionCategoryId());
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return CPDefinitionSpecificationOptionValueLocalServiceUtil.
				getCPDefinitionSpecificationOptionValueByUuidAndGroupId(
					uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return CPDefinitionSpecificationOptionValue.class;
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

		List<StagedModel> cpSpecificationOptionDependentStagedModels =
			dependentStagedModelsMap.get(
				CPSpecificationOption.class.getSimpleName());

		Assert.assertEquals(
			cpSpecificationOptionDependentStagedModels.toString(), 1,
			cpSpecificationOptionDependentStagedModels.size());

		CPSpecificationOption cpSpecificationOption =
			(CPSpecificationOption)
				cpSpecificationOptionDependentStagedModels.get(0);

		CPSpecificationOptionLocalServiceUtil.
			getCPSpecificationOptionByUuidAndGroupId(
				cpSpecificationOption.getUuid(), groupId);

		List<StagedModel> cpOptionCategoryDependentStagedModels =
			dependentStagedModelsMap.get(
				CPOptionCategory.class.getSimpleName());

		Assert.assertEquals(
			cpOptionCategoryDependentStagedModels.toString(), 1,
			cpOptionCategoryDependentStagedModels.size());

		CPOptionCategory cpOptionCategory =
			(CPOptionCategory)cpOptionCategoryDependentStagedModels.get(0);

		CPOptionCategoryLocalServiceUtil.getCPOptionCategoryByUuidAndGroupId(
			cpOptionCategory.getUuid(), groupId);
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				(CPDefinitionSpecificationOptionValue)stagedModel;
		CPDefinitionSpecificationOptionValue
			importedCPDefinitionSpecificationOptionValue =
				(CPDefinitionSpecificationOptionValue)importedStagedModel;

		Assert.assertEquals(
			cpDefinitionSpecificationOptionValue.getValueMap(),
			importedCPDefinitionSpecificationOptionValue.getValueMap());
		Assert.assertEquals(
			cpDefinitionSpecificationOptionValue.getPriority(),
			importedCPDefinitionSpecificationOptionValue.getPriority(), 0.01);
	}

}