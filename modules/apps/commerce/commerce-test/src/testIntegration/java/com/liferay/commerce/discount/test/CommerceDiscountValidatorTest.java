/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.commerce.discount.test.util.CommerceDiscountTestUtil;
import com.liferay.commerce.discount.test.util.TestCommerceDiscountValidator;
import com.liferay.commerce.discount.validator.CommerceDiscountValidatorResult;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.frutilla.FrutillaRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Riccardo Alberti
 */
@RunWith(Arquillian.class)
public class CommerceDiscountValidatorTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();
	}

	@Test
	public void testIsValidNotValidDiscount() throws Exception {
		frutillaRule.scenario(
			"Verify that the discount is validated by a discount validator"
		).given(
			"A invalid discount"
		).and(
			"A discount validator"
		).when(
			"I check if the discount is valid using the validator"
		).then(
			"The discount should result as not valid"
		);

		CommerceDiscount commerceDiscount =
			CommerceDiscountTestUtil.addFixedCommerceDiscount(
				_user.getGroupId(), 1, CommerceDiscountConstants.TARGET_TOTAL,
				null);

		commerceDiscount.setTitle("notValidDiscount");

		commerceDiscount = _commerceDiscountLocalService.updateCommerceDiscount(
			commerceDiscount);

		TestCommerceDiscountValidator commerceDiscountValidator =
			new TestCommerceDiscountValidator();

		CommerceDiscountValidatorResult validate =
			commerceDiscountValidator.validate(null, commerceDiscount);

		Assert.assertFalse(validate.isValid());
	}

	@Test
	public void testIsValidValidDiscount() throws Exception {
		frutillaRule.scenario(
			"Verify that the discount is validated by a discount validator"
		).given(
			"A valid discount"
		).and(
			"A discount validator"
		).when(
			"I check if the discount is valid using the validator"
		).then(
			"The discount should result as valid"
		);

		CommerceDiscount commerceDiscount =
			CommerceDiscountTestUtil.addFixedCommerceDiscount(
				_user.getGroupId(), 1, CommerceDiscountConstants.TARGET_TOTAL,
				null);

		commerceDiscount.setTitle("validDiscount");

		commerceDiscount = _commerceDiscountLocalService.updateCommerceDiscount(
			commerceDiscount);

		TestCommerceDiscountValidator commerceDiscountValidator =
			new TestCommerceDiscountValidator();

		CommerceDiscountValidatorResult validate =
			commerceDiscountValidator.validate(null, commerceDiscount);

		Assert.assertTrue(validate.isValid());
	}

	@Test
	public void testValidateNotValidDiscount() throws Exception {
		frutillaRule.scenario(
			"Verify that the discount is validated by a discount validator"
		).given(
			"A invalid discount"
		).and(
			"A discount validator"
		).when(
			"I check if the discount is valid using the validator"
		).then(
			"The discount should result as not valid"
		);

		CommerceDiscount commerceDiscount =
			CommerceDiscountTestUtil.addFixedCommerceDiscount(
				_user.getGroupId(), 1, CommerceDiscountConstants.TARGET_TOTAL,
				null);

		commerceDiscount.setTitle("notValidDiscount");

		commerceDiscount = _commerceDiscountLocalService.updateCommerceDiscount(
			commerceDiscount);

		TestCommerceDiscountValidator commerceDiscountValidator =
			new TestCommerceDiscountValidator();

		CommerceDiscountValidatorResult validate =
			commerceDiscountValidator.validate(null, commerceDiscount);

		Assert.assertFalse(validate.isValid());
	}

	@Test
	public void testValidateValidDiscount() throws Exception {
		frutillaRule.scenario(
			"Verify that the discount is validated by a discount validator"
		).given(
			"A valid discount"
		).and(
			"A discount validator"
		).when(
			"I check if the discount is valid using the validator"
		).then(
			"The discount should result as valid"
		);

		CommerceDiscount commerceDiscount =
			CommerceDiscountTestUtil.addFixedCommerceDiscount(
				_user.getGroupId(), 1, CommerceDiscountConstants.TARGET_TOTAL,
				null);

		commerceDiscount.setTitle("validDiscount");

		commerceDiscount = _commerceDiscountLocalService.updateCommerceDiscount(
			commerceDiscount);

		TestCommerceDiscountValidator commerceDiscountValidator =
			new TestCommerceDiscountValidator();

		CommerceDiscountValidatorResult validate =
			commerceDiscountValidator.validate(null, commerceDiscount);

		Assert.assertTrue(validate.isValid());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private static User _user;

	@Inject
	private CommerceDiscountLocalService _commerceDiscountLocalService;

	private Group _group;

}