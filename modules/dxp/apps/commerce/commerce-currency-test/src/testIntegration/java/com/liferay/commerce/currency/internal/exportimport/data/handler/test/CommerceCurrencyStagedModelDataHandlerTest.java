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

package com.liferay.commerce.currency.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalServiceUtil;
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
public class CommerceCurrencyStagedModelDataHandlerTest
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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		return CommerceCurrencyLocalServiceUtil.addCommerceCurrency(
			RandomTestUtil.randomString(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomDouble(), null, false,
			RandomTestUtil.randomDouble(), RandomTestUtil.randomBoolean(),
			serviceContext);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return CommerceCurrencyLocalServiceUtil.
				getCommerceCurrencyByUuidAndGroupId(uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return CommerceCurrency.class;
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		CommerceCurrency commerceCurrency = (CommerceCurrency)stagedModel;
		CommerceCurrency importedCommerceCurrency =
			(CommerceCurrency)importedStagedModel;

		Assert.assertEquals(
			commerceCurrency.getCode(), importedCommerceCurrency.getCode());
		Assert.assertEquals(
			commerceCurrency.getNameMap(),
			importedCommerceCurrency.getNameMap());
		Assert.assertEquals(
			commerceCurrency.getRate(), importedCommerceCurrency.getRate(),
			0.01);
		Assert.assertEquals(
			commerceCurrency.getRoundingType(),
			importedCommerceCurrency.getRoundingType());
		Assert.assertEquals(
			commerceCurrency.isPrimary(), importedCommerceCurrency.isPrimary());
		Assert.assertEquals(
			commerceCurrency.getPriority(),
			importedCommerceCurrency.getPriority(), 0.01);
		Assert.assertEquals(
			commerceCurrency.isActive(), importedCommerceCurrency.isActive());
	}

}