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
import com.liferay.commerce.service.CommerceCountryLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.lar.test.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
public class CommerceCountryStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

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

		return CommerceTestUtil.addCommerceCountry(group.getGroupId());
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return CommerceCountryLocalServiceUtil.
				getCommerceCountryByUuidAndGroupId(uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return CommerceCountry.class;
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		CommerceCountry commerceCountry = (CommerceCountry)stagedModel;
		CommerceCountry importedCommerceCountry =
			(CommerceCountry)importedStagedModel;

		Assert.assertEquals(
			commerceCountry.getNameMap(), importedCommerceCountry.getNameMap());
		Assert.assertEquals(
			commerceCountry.isBillingAllowed(),
			importedCommerceCountry.isBillingAllowed());
		Assert.assertEquals(
			commerceCountry.isShippingAllowed(),
			importedCommerceCountry.isShippingAllowed());
		Assert.assertEquals(
			commerceCountry.getTwoLettersISOCode(),
			importedCommerceCountry.getTwoLettersISOCode());
		Assert.assertEquals(
			commerceCountry.getThreeLettersISOCode(),
			importedCommerceCountry.getThreeLettersISOCode());
		Assert.assertEquals(
			commerceCountry.getNumericISOCode(),
			importedCommerceCountry.getNumericISOCode());
		Assert.assertEquals(
			commerceCountry.isSubjectToVAT(),
			importedCommerceCountry.isSubjectToVAT());
		Assert.assertEquals(
			commerceCountry.getPriority(),
			importedCommerceCountry.getPriority(), 0.01);
		Assert.assertEquals(
			commerceCountry.isActive(), importedCommerceCountry.isActive());
	}

}