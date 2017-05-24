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
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.util.test.CPTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.lar.test.BaseWorkflowedStagedModelDataHandlerTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
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
public class CPDefinitionStagedModelDataHandlerTest
	extends BaseWorkflowedStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		return CPTestUtil.addCPDefinition(group.getGroupId());
	}

	@Override
	protected List<StagedModel> addWorkflowedStagedModels(Group group)
		throws Exception {

		List<StagedModel> stagedModels = new ArrayList<>();

		long groupId = group.getGroupId();

		CPDefinition approvedEntry = CPTestUtil.addCPDefinition(groupId);

		stagedModels.add(approvedEntry);

		CPDefinition pendingEntry = CPTestUtil.addCPDefinitionWithWorkflow(
			groupId, false);

		stagedModels.add(pendingEntry);

		return stagedModels;
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return CPDefinitionLocalServiceUtil.getCPDefinitionByUuidAndGroupId(
				uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return CPDefinition.class;
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		CPDefinition cpDefinition = (CPDefinition)stagedModel;
		CPDefinition importedCPDefinition = (CPDefinition)importedStagedModel;

		Assert.assertEquals(
			cpDefinition.getTitleMap(), importedCPDefinition.getTitleMap());
		Assert.assertEquals(
			cpDefinition.getDescriptionMap(),
			importedCPDefinition.getDescriptionMap());
		Assert.assertEquals(
			cpDefinition.getProductTypeName(),
			importedCPDefinition.getProductTypeName());
		Assert.assertEquals(
			cpDefinition.isAvailableIndividually(),
			importedCPDefinition.isAvailableIndividually());
		Assert.assertEquals(
			cpDefinition.getBaseSKU(), importedCPDefinition.getBaseSKU());
		Assert.assertEquals(
			cpDefinition.getBaseSKU(), importedCPDefinition.getBaseSKU());
		CPTestUtil.assertDateEquals(
			cpDefinition.getDisplayDate(),
			importedCPDefinition.getDisplayDate());
		CPTestUtil.assertDateEquals(
			cpDefinition.getExpirationDate(),
			importedCPDefinition.getExpirationDate());
	}

}