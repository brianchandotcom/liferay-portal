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
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSpecificationOption;
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
public class CPSpecificationOptionStagedModelDataHandlerTest
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

		CPOptionCategory cpOptionCategory = CPTestUtil.addCPOptionCategory(
			group.getGroupId());

		addDependentStagedModel(
			dependentStagedModelsMap, CPOptionCategory.class, cpOptionCategory);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> cpOptionCategoryDependentStagedModels =
			dependentStagedModelsMap.get(
				CPOptionCategory.class.getSimpleName());

		CPOptionCategory cpOptionCategory =
			(CPOptionCategory)cpOptionCategoryDependentStagedModels.get(0);

		return CPTestUtil.addCPSpecificationOption(
			group.getGroupId(), cpOptionCategory.getCPOptionCategoryId());
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return CPSpecificationOptionLocalServiceUtil.
				getCPSpecificationOptionByUuidAndGroupId(
					uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return CPSpecificationOption.class;
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> cpOptionCategoryDependentStagedModels =
			dependentStagedModelsMap.get(
				CPOptionCategory.class.getSimpleName());

		Assert.assertEquals(
			cpOptionCategoryDependentStagedModels.toString(), 1,
			cpOptionCategoryDependentStagedModels.size());

		CPOptionCategory cpOptionCategory =
			(CPOptionCategory)cpOptionCategoryDependentStagedModels.get(0);

		CPOptionCategoryLocalServiceUtil.getCPOptionCategoryByUuidAndGroupId(
			cpOptionCategory.getUuid(), group.getGroupId());
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		CPSpecificationOption cpSpecificationOption =
			(CPSpecificationOption)stagedModel;
		CPSpecificationOption importedCPSpecificationOption =
			(CPSpecificationOption)importedStagedModel;

		Assert.assertEquals(
			cpSpecificationOption.getTitleMap(),
			importedCPSpecificationOption.getTitleMap());
		Assert.assertEquals(
			cpSpecificationOption.getDescriptionMap(),
			importedCPSpecificationOption.getDescriptionMap());
		Assert.assertEquals(
			cpSpecificationOption.isFacetable(),
			importedCPSpecificationOption.isFacetable());
		Assert.assertEquals(
			cpSpecificationOption.getKey(),
			importedCPSpecificationOption.getKey());
	}

}