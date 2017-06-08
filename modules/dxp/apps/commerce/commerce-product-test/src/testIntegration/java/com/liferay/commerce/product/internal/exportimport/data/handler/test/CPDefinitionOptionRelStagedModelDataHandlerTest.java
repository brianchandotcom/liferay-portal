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
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalServiceUtil;
import com.liferay.commerce.product.service.CPOptionLocalServiceUtil;
import com.liferay.commerce.product.util.test.CPTestUtil;
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
public class CPDefinitionOptionRelStagedModelDataHandlerTest
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

		CPOption cpOption = CPTestUtil.addCPOption(groupId);

		addDependentStagedModel(
			dependentStagedModelsMap, CPOption.class, cpOption);

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

		List<StagedModel> cpOptionDependentStagedModels =
			dependentStagedModelsMap.get(CPOption.class.getSimpleName());

		CPOption cpOption = (CPOption)cpOptionDependentStagedModels.get(0);

		return CPTestUtil.addCPDefinitionOptionRel(
			group.getGroupId(), cpDefinition.getCPDefinitionId(),
			cpOption.getCPOptionId());
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return CPDefinitionOptionRelLocalServiceUtil.
				getCPDefinitionOptionRelByUuidAndGroupId(
					uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return CPDefinitionOptionRel.class;
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

		List<StagedModel> cpOptionDependentStagedModels =
			dependentStagedModelsMap.get(CPOption.class.getSimpleName());

		Assert.assertEquals(
			cpOptionDependentStagedModels.toString(), 1,
			cpOptionDependentStagedModels.size());

		CPOption cpOption = (CPOption)cpOptionDependentStagedModels.get(0);

		CPOptionLocalServiceUtil.getCPOptionByUuidAndGroupId(
			cpOption.getUuid(), groupId);
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		CPDefinitionOptionRel cpDefinitionOptionRel =
			(CPDefinitionOptionRel)stagedModel;
		CPDefinitionOptionRel importedCPDefinitionOptionRel =
			(CPDefinitionOptionRel)importedStagedModel;

		Assert.assertEquals(
			cpDefinitionOptionRel.getName(),
			importedCPDefinitionOptionRel.getName());
		Assert.assertEquals(
			cpDefinitionOptionRel.getTitleMap(),
			importedCPDefinitionOptionRel.getTitleMap());
		Assert.assertEquals(
			cpDefinitionOptionRel.getDescriptionMap(),
			importedCPDefinitionOptionRel.getDescriptionMap());
		Assert.assertEquals(
			cpDefinitionOptionRel.getDDMFormFieldTypeName(),
			importedCPDefinitionOptionRel.getDDMFormFieldTypeName());
		Assert.assertEquals(
			cpDefinitionOptionRel.getPriority(),
			importedCPDefinitionOptionRel.getPriority(), 0.01);
		Assert.assertEquals(
			cpDefinitionOptionRel.isFacetable(),
			importedCPDefinitionOptionRel.isFacetable());
		Assert.assertEquals(
			cpDefinitionOptionRel.isRequired(),
			importedCPDefinitionOptionRel.isRequired());
		Assert.assertEquals(
			cpDefinitionOptionRel.isSkuContributor(),
			importedCPDefinitionOptionRel.isSkuContributor());
	}

}