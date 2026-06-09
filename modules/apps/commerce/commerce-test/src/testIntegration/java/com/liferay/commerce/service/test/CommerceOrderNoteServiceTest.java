/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderNoteLocalService;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@RunWith(Arquillian.class)
public class CommerceOrderNoteServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			TestPropsValues.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			TestPropsValues.getGroupId(), _commerceCurrency.getCode());

		_commerceOrder = _commerceOrderLocalService.addCommerceOrder(
			TestPropsValues.getUserId(), _commerceChannel.getGroupId(),
			AccountConstants.ACCOUNT_ENTRY_ID_GUEST,
			_commerceCurrency.getCode(), 0);

		_company = CompanyTestUtil.addCompany(true);
	}

	@Test
	public void testAddCommerceOrderNote() throws Exception {
		UserTestUtil.setUser(UserTestUtil.addCompanyAdminUser(_company));

		Assert.assertThrows(
			PrincipalException.class,
			() -> _commerceOrderNoteService.addCommerceOrderNote(
				_commerceOrder.getCommerceOrderId(),
				RandomTestUtil.randomString(), false,
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getGroupId(),
					TestPropsValues.getUserId())));

		UserTestUtil.setUser(TestPropsValues.getUser());

		_commerceOrderNoteService.addCommerceOrderNote(
			_commerceOrder.getCommerceOrderId(), RandomTestUtil.randomString(),
			false,
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));
	}

	@Test
	public void testDeleteCommerceOrderNote() throws Exception {
		CommerceOrderNote commerceOrderNote =
			_commerceOrderNoteLocalService.addCommerceOrderNote(
				_commerceOrder.getCommerceOrderId(),
				RandomTestUtil.randomString(), false,
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		UserTestUtil.setUser(UserTestUtil.addCompanyAdminUser(_company));

		Assert.assertThrows(
			PrincipalException.class,
			() -> _commerceOrderNoteService.deleteCommerceOrderNote(
				commerceOrderNote.getCommerceOrderNoteId()));

		UserTestUtil.setUser(TestPropsValues.getUser());

		_commerceOrderNoteService.deleteCommerceOrderNote(
			commerceOrderNote.getCommerceOrderNoteId());
	}

	@Test
	public void testGetCommerceOrderNotes() throws Exception {
		_commerceOrderNoteLocalService.addCommerceOrderNote(
			_commerceOrder.getCommerceOrderId(), RandomTestUtil.randomString(),
			false,
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		UserTestUtil.setUser(UserTestUtil.addCompanyAdminUser(_company));

		Assert.assertThrows(
			PrincipalException.class,
			() -> _commerceOrderNoteService.getCommerceOrderNotes(
				_commerceOrder.getCommerceOrderId(), false));

		UserTestUtil.setUser(TestPropsValues.getUser());

		_commerceOrderNoteService.getCommerceOrderNotes(
			_commerceOrder.getCommerceOrderId(), false);
	}

	@Test
	public void testUpdateCommerceOrderNote() throws Exception {
		CommerceOrderNote commerceOrderNote1 =
			_commerceOrderNoteLocalService.addCommerceOrderNote(
				_commerceOrder.getCommerceOrderId(),
				RandomTestUtil.randomString(), false,
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		UserTestUtil.setUser(UserTestUtil.addCompanyAdminUser(_company));

		Assert.assertThrows(
			PrincipalException.class,
			() -> _commerceOrderNoteService.updateCommerceOrderNote(
				commerceOrderNote1.getCommerceOrderNoteId(),
				RandomTestUtil.randomString(), false));

		UserTestUtil.setUser(TestPropsValues.getUser());

		_commerceOrderNoteService.updateCommerceOrderNote(
			commerceOrderNote1.getCommerceOrderNoteId(),
			RandomTestUtil.randomString(), false);

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			TestPropsValues.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency.getCommerceCurrencyId());

		CommerceOrderNote commerceOrderNote2 =
			_commerceOrderNoteLocalService.addCommerceOrderNote(
				commerceOrder.getCommerceOrderId(),
				RandomTestUtil.randomString(), true,
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getGroupId(), TestPropsValues.getUserId()));
		CommerceOrderNote commerceOrderNote3 =
			_commerceOrderNoteLocalService.addCommerceOrderNote(
				commerceOrder.getCommerceOrderId(),
				RandomTestUtil.randomString(), false,
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		Role role = _roleLocalService.addRole(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(), null, 0,
			RandomTestUtil.randomString(), null, null,
			RoleConstants.TYPE_REGULAR, null,
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));
		User user = UserTestUtil.addUser();

		_roleLocalService.addUserRole(user.getUserId(), role);

		RoleTestUtil.addResourcePermission(
			role, CommerceOrderConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_NOTES);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, PermissionCheckerFactoryUtil.create(user))) {

			Assert.assertThrows(
				PrincipalException.class,
				() -> _commerceOrderNoteService.addOrUpdateCommerceOrderNote(
					null, commerceOrderNote2.getCommerceOrderNoteId(),
					commerceOrder.getCommerceOrderId(),
					RandomTestUtil.randomString(), false,
					ServiceContextTestUtil.getServiceContext(
						TestPropsValues.getGroupId(), user.getUserId())));
			Assert.assertThrows(
				PrincipalException.class,
				() -> _commerceOrderNoteService.updateCommerceOrderNote(
					commerceOrderNote2.getCommerceOrderNoteId(),
					RandomTestUtil.randomString(), false));

			_commerceOrderNoteService.updateCommerceOrderNote(
				commerceOrderNote3.getCommerceOrderNoteId(),
				RandomTestUtil.randomString(), false);
		}
	}

	private static CommerceChannel _commerceChannel;
	private static CommerceCurrency _commerceCurrency;
	private static CommerceOrder _commerceOrder;

	@Inject
	private static CommerceOrderLocalService _commerceOrderLocalService;

	private static Company _company;

	@Inject
	private CommerceOrderNoteLocalService _commerceOrderNoteLocalService;

	@Inject
	private CommerceOrderNoteService _commerceOrderNoteService;

	@Inject
	private RoleLocalService _roleLocalService;

}