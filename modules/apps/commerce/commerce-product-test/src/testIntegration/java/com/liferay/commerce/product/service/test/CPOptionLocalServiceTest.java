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

package com.liferay.commerce.product.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.exception.CPOptionSKUContributorException;
import com.liferay.commerce.product.service.CPOptionLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Arrays;

import org.frutilla.FrutillaRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Beslic
 */
@RunWith(Arquillian.class)
public class CPOptionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), _user.getUserId());
	}

	@After
	public void tearDown() throws Exception {
		_cpOptionLocalService.deleteCPOptions(_serviceContext.getCompanyId());
	}

	@Test
	public void testAddOption() throws Exception {
		String[] cpOptionFieldTypes = CPTestUtil.getCPOptionFieldTypes();

		frutillaRule.scenario(
			"Add SKU contributor option"
		).given(
			"There is no any options"
		).when(
			String.format(
				"%d SKU contributor options are added",
				cpOptionFieldTypes.length)
		).and(
			String.format(
				"option types are [%s]", Arrays.toString(cpOptionFieldTypes))
		).then(
			String.format(
				"%d options should be created", cpOptionFieldTypes.length)
		);

		_addCPOptions(cpOptionFieldTypes, false, _serviceContext);

		Assert.assertEquals(
			"Options count", cpOptionFieldTypes.length,
			_cpOptionLocalService.getCPOptionsCount(
				_serviceContext.getCompanyId()));
	}

	@Test(expected = CPOptionSKUContributorException.class)
	public void testAddOptionIfOptionTypeIsInvalid() throws Exception {
		frutillaRule.scenario(
			"Add option with invalid option type"
		).given(
			"There is no any options"
		).when(
			"Option with type invalid_type is added"
		).then(
			"option creation should fail"
		);

		_cpOptionLocalService.addCPOption(
			null, _serviceContext.getUserId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), "invalid_type",
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean(),
			false, RandomTestUtil.randomString(), _serviceContext);
	}

	@Test(expected = CPOptionSKUContributorException.class)
	public void testAddOptionIfOptionTypeIsNull() throws Exception {
		frutillaRule.scenario(
			"Add SKU contributor option with boolean option option type"
		).given(
			"There is no any options"
		).when(
			"SKU contributor option is added"
		).and(
			"option is boolean"
		).then(
			"option creation should fail"
		);

		_cpOptionLocalService.addCPOption(
			null, _serviceContext.getUserId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), null,
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean(),
			false, RandomTestUtil.randomString(), _serviceContext);
	}

	@Test
	public void testAddOptionSKUContributor() throws Exception {
		String[] cpOptionSKUContributorFieldTypes =
			CPConstants.PRODUCT_OPTION_SKU_CONTRIBUTOR_FIELD_TYPES;

		frutillaRule.scenario(
			"Add SKU contributor option"
		).given(
			"There is no any options"
		).when(
			String.format(
				"%d SKU contributor options are added",
				cpOptionSKUContributorFieldTypes.length)
		).and(
			String.format(
				"option types are [%s]",
				Arrays.toString(cpOptionSKUContributorFieldTypes))
		).then(
			String.format(
				"%d options should be created",
				cpOptionSKUContributorFieldTypes.length)
		);

		_addCPOptions(cpOptionSKUContributorFieldTypes, true, _serviceContext);

		Assert.assertEquals(
			"SKU contributor options count",
			cpOptionSKUContributorFieldTypes.length,
			_cpOptionLocalService.getCPOptionsCount(
				_serviceContext.getCompanyId()));
	}

	@Test(expected = CPOptionSKUContributorException.class)
	public void testAddOptionSKUContributorIfInvalidOptionType()
		throws Exception {

		frutillaRule.scenario(
			"Add SKU contributor option with boolean option type"
		).given(
			"There is no any option"
		).when(
			"SKU contributor option is added"
		).and(
			"option is boolean"
		).then(
			"option creation should fail"
		);

		_cpOptionLocalService.addCPOption(
			null, _serviceContext.getUserId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), "checkbox",
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean(),
			true, RandomTestUtil.randomString(), _serviceContext);
	}

	@Rule
	public final FrutillaRule frutillaRule = new FrutillaRule();

	private void _addCPOptions(
			String[] optionFieldTypes, boolean skuContributor,
			ServiceContext serviceContext)
		throws Exception {

		for (String optionFieldType : optionFieldTypes) {
			_cpOptionLocalService.addCPOption(
				null, _serviceContext.getUserId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), optionFieldType,
				RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean(),
				skuContributor, RandomTestUtil.randomString(), serviceContext);
		}
	}

	private static User _user;

	@Inject
	private CPOptionLocalService _cpOptionLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

}