/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.checkout.helper.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.checkout.helper.CommerceCheckoutStepHttpHelper;
import com.liferay.commerce.constants.CommerceAddressConstants;
import com.liferay.commerce.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelQualifierLocalService;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceAddressLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderTypeLocalService;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.commerce.test.util.context.TestCommerceContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

import java.util.Collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Crescenzo Rega
 */
@RunWith(Arquillian.class)
public class CommerceChannelDefaultPaymentMethodTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser(true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getCompanyId(), _group.getGroupId(), _user.getUserId());

		_accountEntry = CommerceAccountTestUtil.addBusinessAccountEntry(
			_user.getUserId(),
			"Commerce Account " + RandomTestUtil.randomString(), null, null,
			new long[] {_user.getUserId()}, null, serviceContext);

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_commerceChannel = _commerceChannelLocalService.addCommerceChannel(
			null, AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			_group.getGroupId(),
			"Test Channel " + RandomTestUtil.randomString(),
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
			_commerceCurrency.getCode(), serviceContext);

		_commerceOrder = _commerceOrderLocalService.addCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_accountEntry.getAccountEntryId(), _commerceCurrency.getCode(), 0);

		CPInstance cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			_commerceChannel.getGroupId(), BigDecimal.valueOf(50));

		CommerceTestUtil.addCommerceOrderItem(
			_commerceOrder.getCommerceOrderId(), cpInstance.getCPInstanceId(),
			BigDecimal.valueOf(2));

		Country country = CommerceInventoryTestUtil.addCountry(serviceContext);

		Region region = CommerceInventoryTestUtil.addRegion(
			country.getCountryId(), serviceContext);

		CommerceAddress commerceAddress =
			_commerceAddressLocalService.addCommerceAddress(
				StringPool.BLANK, AccountEntry.class.getName(),
				_accountEntry.getAccountEntryId(), country.getCountryId(),
				region.getRegionId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				StringPool.BLANK,
				CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING,
				RandomTestUtil.randomString(), serviceContext);

		_commerceOrder = _commerceOrderLocalService.getCommerceOrder(
			_commerceOrder.getCommerceOrderId());

		_commerceOrder.setBillingAddressId(
			commerceAddress.getCommerceAddressId());
		_commerceOrder.setShippingAddressId(
			commerceAddress.getCommerceAddressId());

		_commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
			_commerceOrder);
	}

	@After
	public void tearDown() throws Exception {
		_commerceOrderLocalService.deleteCommerceOrders(
			_commerceChannel.getGroupId());
	}

	@Test
	public void testIsActivePaymentMethodCommerceCheckoutStep()
		throws Exception {

		_addCommerceChannelAccountEntryRel(
			_addCommercePaymentMethodGroupRel(true, "authorize-net", 1));
		_addCommercePaymentMethodGroupRel(true, "mercanet", 2);
		_addCommercePaymentMethodGroupRel(true, "money-order", 3);
		_addCommercePaymentMethodGroupRel(true, "paypal", 4);

		_isActivePaymentMethodCommerceCheckoutStep();

		Assert.assertEquals(
			"authorize-net", _commerceOrder.getCommercePaymentMethodKey());
	}

	@Test
	public void testIsActivePaymentMethodCommerceCheckoutStepDefaultEligibleForOrderType()
		throws Exception {

		CommerceOrderType commerceOrderType = _addCommerceOrderType();

		_setCommerceOrderType(commerceOrderType);

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel1 =
			_addCommercePaymentMethodGroupRel(true, "mercanet", 2);

		_addCommercePaymentMethodGroupRelQualifier(
			commerceOrderType, commercePaymentMethodGroupRel1);

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel2 =
			_addCommercePaymentMethodGroupRel(true, "money-order", 1);

		_addCommerceChannelAccountEntryRel(commercePaymentMethodGroupRel2);

		_addCommercePaymentMethodGroupRelQualifier(
			commerceOrderType, commercePaymentMethodGroupRel2);

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel3 =
			_addCommercePaymentMethodGroupRel(true, "paypal", 3);

		_addCommercePaymentMethodGroupRelQualifier(
			commerceOrderType, commercePaymentMethodGroupRel3);

		_isActivePaymentMethodCommerceCheckoutStep();

		Assert.assertEquals(
			"money-order", _commerceOrder.getCommercePaymentMethodKey());
	}

	@Test
	public void testIsActivePaymentMethodCommerceCheckoutStepDefaultIneligibleForOrderType()
		throws Exception {

		CommerceOrderType commerceOrderType1 = _addCommerceOrderType();

		_setCommerceOrderType(commerceOrderType1);

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel1 =
			_addCommercePaymentMethodGroupRel(true, "mercanet", 2);

		_addCommercePaymentMethodGroupRelQualifier(
			commerceOrderType1, commercePaymentMethodGroupRel1);

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel2 =
			_addCommercePaymentMethodGroupRel(true, "money-order", 1);

		_addCommercePaymentMethodGroupRelQualifier(
			commerceOrderType1, commercePaymentMethodGroupRel2);

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel3 =
			_addCommercePaymentMethodGroupRel(true, "paypal", 3);

		CommerceOrderType commerceOrderType2 = _addCommerceOrderType();

		_addCommerceChannelAccountEntryRel(commercePaymentMethodGroupRel3);

		_addCommercePaymentMethodGroupRelQualifier(
			commerceOrderType2, commercePaymentMethodGroupRel3);

		_isActivePaymentMethodCommerceCheckoutStep();

		Assert.assertEquals(
			"mercanet", _commerceOrder.getCommercePaymentMethodKey());
	}

	@Test
	public void testIsActivePaymentMethodCommerceCheckoutStepDisabledDefault()
		throws Exception {

		_addCommercePaymentMethodGroupRel(true, "money-order", 2);
		_addCommerceChannelAccountEntryRel(
			_addCommercePaymentMethodGroupRel(false, "paypal", 1));

		_isActivePaymentMethodCommerceCheckoutStep();

		Assert.assertEquals(
			"money-order", _commerceOrder.getCommercePaymentMethodKey());
	}

	@Test
	public void testIsActivePaymentMethodCommerceCheckoutStepDisabledDefaultEligibleForOrderType()
		throws Exception {

		CommerceOrderType commerceOrderType = _addCommerceOrderType();

		_setCommerceOrderType(commerceOrderType);

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel1 =
			_addCommercePaymentMethodGroupRel(true, "mercanet", 2);

		_addCommercePaymentMethodGroupRelQualifier(
			commerceOrderType, commercePaymentMethodGroupRel1);

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel2 =
			_addCommercePaymentMethodGroupRel(true, "money-order", 1);

		_addCommercePaymentMethodGroupRelQualifier(
			commerceOrderType, commercePaymentMethodGroupRel2);

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel3 =
			_addCommercePaymentMethodGroupRel(false, "paypal", 3);

		_addCommerceChannelAccountEntryRel(commercePaymentMethodGroupRel3);
		_addCommercePaymentMethodGroupRelQualifier(
			commerceOrderType, commercePaymentMethodGroupRel3);

		_isActivePaymentMethodCommerceCheckoutStep();

		Assert.assertEquals(
			"mercanet", _commerceOrder.getCommercePaymentMethodKey());
	}

	private void _addCommerceChannelAccountEntryRel(
			CommercePaymentMethodGroupRel commercePaymentMethodGroupRel)
		throws Exception {

		_commerceChannelAccountEntryRelLocalService.
			addCommerceChannelAccountEntryRel(
				_user.getUserId(), _accountEntry.getAccountEntryId(),
				CommercePaymentMethodGroupRel.class.getName(),
				commercePaymentMethodGroupRel.
					getCommercePaymentMethodGroupRelId(),
				_commerceChannel.getCommerceChannelId(), true, 0,
				CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT);
	}

	private CommerceOrderType _addCommerceOrderType() throws Exception {
		return _commerceOrderTypeLocalService.addCommerceOrderType(
			RandomTestUtil.randomString(), _user.getUserId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), true, 1, 1, 2024, 0, 0, 0,
			0, 0, 0, 0, 0, true,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private CommercePaymentMethodGroupRel _addCommercePaymentMethodGroupRel(
			boolean active, String paymentIntegrationKey, int priority)
		throws Exception {

		return _commercePaymentMethodGroupRelLocalService.
			addCommercePaymentMethodGroupRel(
				_user.getUserId(), _commerceChannel.getGroupId(),
				Collections.singletonMap(LocaleUtil.US, paymentIntegrationKey),
				Collections.singletonMap(LocaleUtil.US, paymentIntegrationKey),
				active, null, paymentIntegrationKey, priority, null);
	}

	private void _addCommercePaymentMethodGroupRelQualifier(
			CommerceOrderType commerceOrderType,
			CommercePaymentMethodGroupRel commercePaymentMethodGroupRel)
		throws Exception {

		_commercePaymentMethodGroupRelQualifierLocalService.
			addCommercePaymentMethodGroupRelQualifier(
				_user.getUserId(), CommerceOrderType.class.getName(),
				commerceOrderType.getCommerceOrderTypeId(),
				commercePaymentMethodGroupRel.
					getCommercePaymentMethodGroupRelId());
	}

	private void _isActivePaymentMethodCommerceCheckoutStep() throws Exception {
		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			CommerceCheckoutWebKeys.COMMERCE_ORDER, _commerceOrder);
		httpServletRequest.setAttribute(
			CommerceWebKeys.COMMERCE_CONTEXT,
			new TestCommerceContext(
				_accountEntry, _commerceCurrency, _commerceChannel, _user,
				_group, _commerceOrder));

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_group.getCompanyId()));
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRequest(new MockHttpServletRequest());
		themeDisplay.setScopeGroupId(_commerceChannel.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		_commerceCheckoutStepHttpHelper.
			isActivePaymentMethodCommerceCheckoutStep(
				httpServletRequest, _commerceOrder);

		_commerceOrder = _commerceOrderLocalService.getCommerceOrder(
			_commerceOrder.getCommerceOrderId());
	}

	private void _setCommerceOrderType(CommerceOrderType commerceOrderType) {
		_commerceOrder.setCommerceOrderTypeId(
			commerceOrderType.getCommerceOrderTypeId());

		_commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
			_commerceOrder);
	}

	private AccountEntry _accountEntry;

	@Inject
	private CommerceAddressLocalService _commerceAddressLocalService;

	private CommerceChannel _commerceChannel;

	@Inject
	private CommerceChannelAccountEntryRelLocalService
		_commerceChannelAccountEntryRelLocalService;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Inject
	private CommerceCheckoutStepHttpHelper _commerceCheckoutStepHttpHelper;

	private CommerceCurrency _commerceCurrency;
	private CommerceOrder _commerceOrder;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Inject
	private CommerceOrderTypeLocalService _commerceOrderTypeLocalService;

	@Inject
	private CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;

	@Inject
	private CommercePaymentMethodGroupRelQualifierLocalService
		_commercePaymentMethodGroupRelQualifierLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	private Group _group;
	private User _user;

}