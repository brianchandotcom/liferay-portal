/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.pricing.constants.CommercePricingClassActionKeys;
import com.liferay.commerce.pricing.constants.CommercePricingClassConstants;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.service.CommercePricingClassService;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Beslic
 */
@RunWith(Arquillian.class)
public class CommercePricingClassServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testAddPricingClassHasOwnerPermission() throws Exception {
		RoleTestUtil.addResourcePermission(
			"User", CommercePricingClassConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			CommercePricingClassActionKeys.ADD_COMMERCE_PRICING_CLASS);

		User user1 = UserTestUtil.addUser();

		UserTestUtil.setUser(user1);

		CommercePricingClass commercePricingClass1 =
			_commercePricingClassService.addCommercePricingClass(
				RandomTestUtil.randomString(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getCompanyId(), user1.getGroupId(),
					user1.getUserId()));

		Assert.assertNotNull(
			_commercePricingClassService.getCommercePricingClass(
				commercePricingClass1.getCommercePricingClassId()));

		User user2 = UserTestUtil.addUser();

		UserTestUtil.setUser(user2);

		Class<? extends Exception> exceptionClass = Exception.class;

		try {
			CommercePricingClass commercePricingClass2 =
				_commercePricingClassService.getCommercePricingClass(
					commercePricingClass1.getCommercePricingClassId());

			Assert.assertNull(
				"User 2 has no owner permission", commercePricingClass2);
		}
		catch (Exception exception) {
			exceptionClass = exception.getClass();
		}

		Assert.assertEquals(
			PrincipalException.MustHavePermission.class, exceptionClass);
	}

	@Inject
	private CommercePricingClassService _commercePricingClassService;

}