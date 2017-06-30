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
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.service.CPDefinitionLinkLocalServiceUtil;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
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
public class CPDefinitionLinkStagedModelDataHandlerTest
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

		CPDefinition cpDefinition1 = CPTestUtil.addCPDefinition(groupId);

		addDependentStagedModel(
			dependentStagedModelsMap, CPDefinition.class, cpDefinition1);

		CPDefinition cpDefinition2 = CPTestUtil.addCPDefinition(groupId);

		addDependentStagedModel(
			dependentStagedModelsMap, CPDefinition.class, cpDefinition2);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> cpDefinitionDependentStagedModels =
			dependentStagedModelsMap.get(CPDefinition.class.getSimpleName());

		CPDefinition cpDefinition1 =
			(CPDefinition)cpDefinitionDependentStagedModels.get(0);
		CPDefinition cpDefinition2 =
			(CPDefinition)cpDefinitionDependentStagedModels.get(1);

		return CPTestUtil.addCPDefinitionLink(
			group.getGroupId(), cpDefinition1.getCPDefinitionId(),
			cpDefinition2.getCPDefinitionId());
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return CPDefinitionLinkLocalServiceUtil.
				getCPDefinitionLinkByUuidAndGroupId(uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return CPDefinitionLink.class;
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> cpDefinitionDependentStagedModels =
			dependentStagedModelsMap.get(CPDefinition.class.getSimpleName());

		Assert.assertEquals(
			cpDefinitionDependentStagedModels.toString(), 2,
			cpDefinitionDependentStagedModels.size());

		for (StagedModel stagedModel : cpDefinitionDependentStagedModels) {
			CPDefinition cpDefinition = (CPDefinition)stagedModel;

			CPDefinitionLocalServiceUtil.getCPDefinitionByUuidAndGroupId(
				cpDefinition.getUuid(), group.getGroupId());
		}
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		CPDefinitionLink cpDefinitionLink = (CPDefinitionLink)stagedModel;
		CPDefinitionLink importedCPDefinitionLink =
			(CPDefinitionLink)importedStagedModel;

		Assert.assertEquals(
			cpDefinitionLink.getPriority(),
			importedCPDefinitionLink.getPriority(), 0.01);
		Assert.assertEquals(
			cpDefinitionLink.getType(), importedCPDefinitionLink.getType());
	}

}