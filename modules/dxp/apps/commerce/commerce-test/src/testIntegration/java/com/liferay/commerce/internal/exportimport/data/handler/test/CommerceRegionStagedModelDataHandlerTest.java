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

package com.liferay.commerce.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.internal.test.util.CommerceTestUtil;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.service.CommerceCountryLocalServiceUtil;
import com.liferay.commerce.service.CommerceRegionLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
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
public class CommerceRegionStagedModelDataHandlerTest
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

		CommerceCountry commerceCountry = CommerceTestUtil.addCommerceCountry(
			group.getGroupId());

		addDependentStagedModel(
			dependentStagedModelsMap, CommerceCountry.class, commerceCountry);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> commerceCountryDependentStagedModels =
			dependentStagedModelsMap.get(CommerceCountry.class.getSimpleName());

		CommerceCountry commerceCountry =
			(CommerceCountry)commerceCountryDependentStagedModels.get(0);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		return CommerceRegionLocalServiceUtil.addCommerceRegion(
			commerceCountry.getCommerceCountryId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomDouble(), RandomTestUtil.randomBoolean(),
			serviceContext);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return CommerceRegionLocalServiceUtil.
				getCommerceRegionByUuidAndGroupId(uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return CommerceRegion.class;
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> commerceCountryDependentStagedModels =
			dependentStagedModelsMap.get(CommerceCountry.class.getSimpleName());

		Assert.assertEquals(
			commerceCountryDependentStagedModels.toString(), 1,
			commerceCountryDependentStagedModels.size());

		CommerceCountry commerceCountry =
			(CommerceCountry)commerceCountryDependentStagedModels.get(0);

		CommerceCountryLocalServiceUtil.getCommerceCountryByUuidAndGroupId(
			commerceCountry.getUuid(), group.getGroupId());
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		CommerceRegion commerceRegion = (CommerceRegion)stagedModel;
		CommerceRegion importedCommerceRegion =
			(CommerceRegion)importedStagedModel;

		Assert.assertEquals(
			commerceRegion.getName(), importedCommerceRegion.getName());
		Assert.assertEquals(
			commerceRegion.getCode(), importedCommerceRegion.getCode());
		Assert.assertEquals(
			commerceRegion.getPriority(), importedCommerceRegion.getPriority(),
			0.01);
		Assert.assertEquals(
			commerceRegion.isActive(), importedCommerceRegion.isActive());
	}

}