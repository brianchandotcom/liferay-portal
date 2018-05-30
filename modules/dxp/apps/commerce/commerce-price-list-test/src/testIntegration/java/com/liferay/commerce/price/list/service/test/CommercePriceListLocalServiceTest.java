/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.price.list.service.test;

import static org.hamcrest.CoreMatchers.equalTo;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.price.list.test.util.CommercePriceListTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.frutilla.FrutillaRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class CommercePriceListLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testUpsertCommercePriceList() throws Exception {
		frutillaRule.scenario(
			"Adding a new Price List"
		).given(
			"A site (group)"
		).and(
			"A currency code expressed with 3-letter ISO 4217 format"
		).and(
			"The name of the new list"
		).when(
			"The external reference code (externalReferenceCode)"
		).and(
			"(commercePriceListId) is not used in the method invocation"
		).then(
			"The result should be a new Price List on the given site"
		);

		String currency = "EUR";
		String name = RandomTestUtil.randomString();

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.upsertCommercePriceList(
				_group.getGroupId(), 0L, currency, name, 0D, true, null, null,
				null);

		Assert.assertThat(name, equalTo(commercePriceList.getName()));
	}

	@Rule
	public final FrutillaRule frutillaRule = new FrutillaRule();

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

	@DeleteAfterTestRun
	private Group _group;

}