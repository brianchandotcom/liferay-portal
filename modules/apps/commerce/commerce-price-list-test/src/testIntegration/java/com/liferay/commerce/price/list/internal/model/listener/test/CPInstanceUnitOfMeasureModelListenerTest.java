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

package com.liferay.commerce.price.list.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalServiceUtil;
import com.liferay.commerce.price.list.test.util.CommercePriceEntryTestUtil;
import com.liferay.commerce.price.list.test.util.CommercePriceListTestUtil;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import java.util.List;

import org.frutilla.FrutillaRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Crescenzo Rega
 */
@RunWith(Arquillian.class)
public class CPInstanceUnitOfMeasureModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());
	}

	@Test
	public void testDeleteCPInstanceUnitOfMeasure() throws PortalException {
		frutillaRule.scenario(
			"Delete a CPInstance Unit Of Measure"
		).given(
			"A Price List"
		).and(
			"A (SKU) CpInstance in a random CpDefinition"
		).and(
			"The SKU of the new entry"
		).and(
			"The price of the entry"
		).and(
			"The promo price of the entry"
		).and(
			"The unit of measure key of the entry"
		).when(
			"The CPInstance Unit Of Measure is delete"
		).then(
			"it is not the last the remove"
		).and(
			"Also the corresponding price entry"
		);

		CPInstance cpInstance =
			CPTestUtil.addCPInstanceWithRandomSkuFromCatalog(
				_group.getGroupId());

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				null, _group.getGroupId(), _commerceCurrency.getCode(),
				RandomTestUtil.randomString(), RandomTestUtil.randomDouble(),
				true, null, null);

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure1 =
			CPTestUtil.addCPInstanceUnitOfMeasure(
				_group.getGroupId(), cpInstance.getCPInstanceId(),
				RandomTestUtil.randomString(),
				BigDecimal.valueOf(RandomTestUtil.randomDouble()),
				cpInstance.getSku());

		CommercePriceEntryTestUtil.addOrUpdateCommercePriceEntry(
			null, 0, cpInstance.getCPInstanceId(),
			commercePriceList.getCommercePriceListId(), null,
			RandomTestUtil.randomDouble(), RandomTestUtil.randomDouble(),
			cpInstanceUnitOfMeasure1.getKey());

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure2 =
			CPTestUtil.addCPInstanceUnitOfMeasure(
				_group.getGroupId(), cpInstance.getCPInstanceId(),
				RandomTestUtil.randomString(),
				BigDecimal.valueOf(RandomTestUtil.randomDouble()),
				cpInstance.getSku());

		CommercePriceEntryTestUtil.addOrUpdateCommercePriceEntry(
			null, 0, cpInstance.getCPInstanceId(),
			commercePriceList.getCommercePriceListId(), null,
			RandomTestUtil.randomDouble(), RandomTestUtil.randomDouble(),
			cpInstanceUnitOfMeasure2.getKey());

		List<CommercePriceEntry> commercePriceEntries =
			CommercePriceEntryLocalServiceUtil.getCommercePriceEntries(
				commercePriceList.getCommercePriceListId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			commercePriceEntries.toString(), 2, commercePriceEntries.size());

		CPInstanceUnitOfMeasureLocalServiceUtil.deleteCPInstanceUnitOfMeasure(
			cpInstanceUnitOfMeasure2);

		commercePriceEntries =
			CommercePriceEntryLocalServiceUtil.getCommercePriceEntries(
				commercePriceList.getCommercePriceListId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			commercePriceEntries.toString(), 1, commercePriceEntries.size());
	}

	@Test
	public void testDeleteLastCPInstanceUnitOfMeasure() throws PortalException {
		frutillaRule.scenario(
			"Delete a CPInstance Unit Of Measure"
		).given(
			"A Price List"
		).and(
			"A (SKU) CpInstance in a random CpDefinition"
		).and(
			"The SKU of the new entry"
		).and(
			"The price of the entry"
		).and(
			"The promo price of the entry"
		).and(
			"The unit of measure key of the entry"
		).when(
			"The CPInstance Unit Of Measure is delete"
		).then(
			"It is the last"
		).and(
			"Set key = null and quantity = 1 on commercePriceEntry"
		);

		CPInstance cpInstance =
			CPTestUtil.addCPInstanceWithRandomSkuFromCatalog(
				_group.getGroupId());

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				null, _group.getGroupId(), _commerceCurrency.getCode(),
				RandomTestUtil.randomString(), RandomTestUtil.randomDouble(),
				true, null, null);

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			CPTestUtil.addCPInstanceUnitOfMeasure(
				_group.getGroupId(), cpInstance.getCPInstanceId(),
				RandomTestUtil.randomString(),
				BigDecimal.valueOf(RandomTestUtil.randomDouble()),
				cpInstance.getSku());

		CommercePriceEntry commercePriceEntry =
			CommercePriceEntryTestUtil.addOrUpdateCommercePriceEntry(
				null, 0, cpInstance.getCPInstanceId(),
				commercePriceList.getCommercePriceListId(), null,
				RandomTestUtil.randomDouble(), RandomTestUtil.randomDouble(),
				cpInstanceUnitOfMeasure.getKey());

		CPInstanceUnitOfMeasureLocalServiceUtil.deleteCPInstanceUnitOfMeasure(
			cpInstanceUnitOfMeasure);

		commercePriceEntry =
			CommercePriceEntryLocalServiceUtil.fetchCommercePriceEntry(
				commercePriceEntry.getCommercePriceEntryId());

		Assert.assertEquals(BigDecimal.ONE, commercePriceEntry.getQuantity());
		Assert.assertEquals(
			StringPool.BLANK, commercePriceEntry.getUnitOfMeasureKey());
	}

	@Test
	public void testUpdateCPInstanceUnitOfMeasure() throws PortalException {
		frutillaRule.scenario(
			"Update a CPInstance Unit Of Measure"
		).given(
			"A Price List"
		).and(
			"A (SKU) CpInstance in a random CpDefinition"
		).and(
			"The SKU of the new entry"
		).and(
			"The price of the entry"
		).and(
			"The promo price of the entry"
		).and(
			"The unit of measure key of the entry"
		).when(
			"The CPInstance Unit Of Measure is updated"
		).then(
			"Update key and quantity"
		);

		CPInstance cpInstance =
			CPTestUtil.addCPInstanceWithRandomSkuFromCatalog(
				_group.getGroupId());

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				null, _group.getGroupId(), _commerceCurrency.getCode(),
				RandomTestUtil.randomString(), RandomTestUtil.randomDouble(),
				true, null, null);

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			CPTestUtil.addCPInstanceUnitOfMeasure(
				_group.getGroupId(), cpInstance.getCPInstanceId(),
				RandomTestUtil.randomString(),
				BigDecimal.valueOf(RandomTestUtil.randomDouble()),
				cpInstance.getSku());

		CommercePriceEntry commercePriceEntry =
			CommercePriceEntryTestUtil.addOrUpdateCommercePriceEntry(
				null, 0, cpInstance.getCPInstanceId(),
				commercePriceList.getCommercePriceListId(), null,
				RandomTestUtil.randomDouble(), RandomTestUtil.randomDouble(),
				cpInstanceUnitOfMeasure.getKey());

		BigDecimal quantity = BigDecimal.TEN;

		cpInstanceUnitOfMeasure.setIncrementalOrderQuantity(quantity);

		String unitOfMeasureKey = "new";

		cpInstanceUnitOfMeasure.setKey(unitOfMeasureKey);

		CPInstanceUnitOfMeasureLocalServiceUtil.updateCPInstanceUnitOfMeasure(
			cpInstanceUnitOfMeasure);

		commercePriceEntry =
			CommercePriceEntryLocalServiceUtil.fetchCommercePriceEntry(
				commercePriceEntry.getCommercePriceEntryId());

		Assert.assertEquals(quantity, commercePriceEntry.getQuantity());
		Assert.assertEquals(
			unitOfMeasureKey, commercePriceEntry.getUnitOfMeasureKey());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private CommerceCurrency _commerceCurrency;
	private Group _group;

}