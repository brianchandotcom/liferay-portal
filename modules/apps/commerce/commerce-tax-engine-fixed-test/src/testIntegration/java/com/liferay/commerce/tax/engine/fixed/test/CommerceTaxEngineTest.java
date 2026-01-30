/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.engine.fixed.test;

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.payment.test.util.TestCommercePaymentMethod;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPTaxCategoryLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.tax.engine.fixed.configuration.CommerceTaxByAddressTypeConfiguration;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateAddressRelLocalService;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateLocalService;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.commerce.test.util.CommerceTaxTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.commerce.test.util.price.list.CommercePriceEntryTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.settings.FallbackKeysSettingsUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import org.junit.After;
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
public class CommerceTaxEngineTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_buyerRole = _roleLocalService.getRole(
			_group.getCompanyId(),
			AccountRoleConstants.ROLE_NAME_ACCOUNT_BUYER);

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		_orderManagerRole = _roleLocalService.getRole(
			_group.getCompanyId(),
			AccountRoleConstants.ROLE_NAME_ACCOUNT_ORDER_MANAGER);

		_user = UserTestUtil.addUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId());

		_accountEntry = CommerceAccountTestUtil.addBusinessAccountEntry(
			_serviceContext.getUserId(), "Test Business Account", null, null,
			new long[] {_user.getUserId()}, null, _serviceContext);

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), _user.getUserId());

		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_cpTaxCategory = _cpTaxCategoryLocalService.addCPTaxCategory(
			StringPool.BLANK, RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), _serviceContext);

		_cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			commerceCatalog.getGroupId(), _cpTaxCategory.getCPTaxCategoryId(),
			false);

		CPDefinition cpDefinition = _cpInstance.getCPDefinition();

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchCatalogBaseCommercePriceList(
				commerceCatalog.getGroupId());

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, cpDefinition.getCProductId(),
			_cpInstance.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), BigDecimal.valueOf(35));

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				_serviceContext);

		CommerceTestUtil.addWarehouseCommerceChannelRel(
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId());

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			_user.getUserId(), commerceInventoryWarehouse,
			BigDecimal.valueOf(1000), _cpInstance.getSku(), StringPool.BLANK);

		_commerceShippingMethod =
			CommerceTestUtil.addFixedRateCommerceShippingMethod(
				_user.getUserId(), _commerceChannel.getGroupId(),
				BigDecimal.ONE);

		_originalName = PrincipalThreadLocal.getName();
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
	}

	@After
	public void tearDown() throws Exception {
		_configurationProvider.deleteGroupConfiguration(
			CommerceTaxByAddressTypeConfiguration.class,
			_commerceChannel.getCompanyId(), _commerceChannel.getGroupId());

		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testByAddressTaxToBillingAddressNotApplied() throws Exception {
		_updateCommerceTaxByAddressTypeConfiguration(false);

		CommerceTaxMethod commerceTaxMethod =
			CommerceTaxTestUtil.addByAddressCommerceTaxMethod(
				_user.getUserId(), _commerceChannel.getGroupId(), false);

		Country country1 = CommerceInventoryTestUtil.addCountry(
			_serviceContext);

		Region region1 = CommerceInventoryTestUtil.addRegion(
			country1.getCountryId(), _serviceContext);

		String zip1 = RandomTestUtil.randomString();

		BigDecimal rate = BigDecimal.TEN;

		_commerceTaxFixedRateAddressRelLocalService.
			addCommerceTaxFixedRateAddressRel(
				_user.getUserId(), _commerceChannel.getGroupId(),
				commerceTaxMethod.getCommerceTaxMethodId(),
				_cpTaxCategory.getCPTaxCategoryId(), country1.getCountryId(),
				region1.getRegionId(), zip1, rate.doubleValue());

		Country country2 = CommerceInventoryTestUtil.addCountry(
			_serviceContext);

		Region region2 = CommerceInventoryTestUtil.addRegion(
			country2.getCountryId(), _serviceContext);

		Address billingAddress = _addAddress(
			country2, region2, RandomTestUtil.randomString());

		Address shippingAddress = _addAddress(country1, region1, zip1);

		CommerceOrder commerceOrder = _addCommerceOrder(
			billingAddress.getAddressId(), shippingAddress.getAddressId());

		BigDecimal taxAmount = commerceOrder.getTaxAmount();

		Assert.assertNotEquals(
			rate.stripTrailingZeros(), taxAmount.stripTrailingZeros());
	}

	@Test
	public void testByAddressTaxToDifferentShippingAddressNotApplied()
		throws Exception {

		_updateCommerceTaxByAddressTypeConfiguration(true);

		CommerceTaxMethod commerceTaxMethod =
			CommerceTaxTestUtil.addByAddressCommerceTaxMethod(
				_user.getUserId(), _commerceChannel.getGroupId(), false);

		Country country1 = CommerceInventoryTestUtil.addCountry(
			_serviceContext);

		Region region1 = CommerceInventoryTestUtil.addRegion(
			country1.getCountryId(), _serviceContext);

		BigDecimal rate = BigDecimal.TEN;

		_commerceTaxFixedRateAddressRelLocalService.
			addCommerceTaxFixedRateAddressRel(
				_user.getUserId(), _commerceChannel.getGroupId(),
				commerceTaxMethod.getCommerceTaxMethodId(),
				_cpTaxCategory.getCPTaxCategoryId(), country1.getCountryId(),
				region1.getRegionId(), RandomTestUtil.randomString(),
				rate.doubleValue());

		Country country2 = CommerceInventoryTestUtil.addCountry(
			_serviceContext);

		Region region2 = CommerceInventoryTestUtil.addRegion(
			country2.getCountryId(), _serviceContext);

		Address address = _addAddress(
			country2, region2, RandomTestUtil.randomString());

		CommerceOrder commerceOrder = _addCommerceOrder(
			address.getAddressId(), address.getAddressId());

		BigDecimal taxAmount = commerceOrder.getTaxAmount();

		Assert.assertNotEquals(
			rate.stripTrailingZeros(), taxAmount.stripTrailingZeros());
	}

	@Test
	public void testByAddressTaxToSameShippingAddressAppliedCorrectly()
		throws Exception {

		_updateCommerceTaxByAddressTypeConfiguration(true);

		CommerceTaxMethod commerceTaxMethod =
			CommerceTaxTestUtil.addByAddressCommerceTaxMethod(
				_user.getUserId(), _commerceChannel.getGroupId(), false);

		Country country1 = CommerceInventoryTestUtil.addCountry(
			_serviceContext);

		Region region1 = CommerceInventoryTestUtil.addRegion(
			country1.getCountryId(), _serviceContext);

		String zip1 = RandomTestUtil.randomString();

		BigDecimal rate = BigDecimal.TEN;

		_commerceTaxFixedRateAddressRelLocalService.
			addCommerceTaxFixedRateAddressRel(
				_user.getUserId(), _commerceChannel.getGroupId(),
				commerceTaxMethod.getCommerceTaxMethodId(),
				_cpTaxCategory.getCPTaxCategoryId(), country1.getCountryId(),
				region1.getRegionId(), zip1, rate.doubleValue());

		Country country2 = CommerceInventoryTestUtil.addCountry(
			_serviceContext);

		Region region2 = CommerceInventoryTestUtil.addRegion(
			country2.getCountryId(), _serviceContext);

		Address billingAddress = _addAddress(
			country2, region2, RandomTestUtil.randomString());

		Address shippingAddress = _addAddress(country1, region1, zip1);

		CommerceOrder commerceOrder = _addCommerceOrder(
			billingAddress.getAddressId(), shippingAddress.getAddressId());

		BigDecimal taxAmount = commerceOrder.getTaxAmount();

		Assert.assertEquals(
			rate.stripTrailingZeros(), taxAmount.stripTrailingZeros());
	}

	@Test
	public void testFixedTaxAppliedCorrectly() throws Exception {
		CommerceTaxMethod commerceTaxMethod =
			CommerceTaxTestUtil.addFixedTaxCommerceTaxMethod(
				_user.getUserId(), _commerceChannel.getGroupId(), false);

		BigDecimal rate = BigDecimal.TEN;

		_commerceTaxFixedRateLocalService.addCommerceTaxFixedRate(
			_user.getUserId(), _commerceChannel.getGroupId(),
			commerceTaxMethod.getCommerceTaxMethodId(),
			_cpTaxCategory.getCPTaxCategoryId(), rate.doubleValue());

		Country country = CommerceInventoryTestUtil.addCountry(_serviceContext);

		Region region = CommerceInventoryTestUtil.addRegion(
			country.getCountryId(), _serviceContext);

		Address address = _addAddress(
			country, region, RandomTestUtil.randomString());

		CommerceOrder commerceOrder = _addCommerceOrder(
			address.getAddressId(), address.getAddressId());

		BigDecimal taxAmount = commerceOrder.getTaxAmount();

		Assert.assertEquals(
			rate.stripTrailingZeros(), taxAmount.stripTrailingZeros());
	}

	private Address _addAddress(Country country, Region region, String zip)
		throws Exception {

		return _addressLocalService.addAddress(
			RandomTestUtil.randomString(), _user.getUserId(),
			AccountEntry.class.getName(), _accountEntry.getAccountEntryId(),
			country.getCountryId(), 0, region.getRegionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			RandomTestUtil.randomString(), true, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			zip, RandomTestUtil.randomString(), _serviceContext);
	}

	private CommerceOrder _addCommerceOrder(
			long billingAddressId, long shippingAddressId)
		throws Exception {

		User buyerUser = UserTestUtil.addUser(_group.getGroupId());

		_addUserAccountRole(
			_accountEntry, _buyerRole.getRoleId(), buyerUser.getUserId());

		PrincipalThreadLocal.setName(buyerUser.getUserId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(buyerUser));

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		CommerceTestUtil.addCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), _cpInstance.getCPInstanceId(),
			BigDecimal.ONE);

		commerceOrder = _commerceOrderLocalService.fetchCommerceOrder(
			commerceOrder.getCommerceOrderId());

		commerceOrder.setBillingAddressId(billingAddressId);

		commerceOrder.setCommercePaymentMethodKey(
			TestCommercePaymentMethod.KEY);

		commerceOrder.setCommerceShippingMethodId(
			_commerceShippingMethod.getCommerceShippingMethodId());

		commerceOrder.setShippingAddressId(shippingAddressId);

		commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder);

		User orderManagerUser = UserTestUtil.addUser(_group.getGroupId());

		_addUserAccountRole(
			_accountEntry, _orderManagerRole.getRoleId(),
			orderManagerUser.getUserId());

		PrincipalThreadLocal.setName(orderManagerUser.getUserId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(orderManagerUser));

		_commerceOrderEngine.checkoutCommerceOrder(
			commerceOrder, orderManagerUser.getUserId());

		return _commerceOrderLocalService.fetchCommerceOrder(
			commerceOrder.getCommerceOrderId());
	}

	private void _addUserAccountRole(
			AccountEntry accountEntry, long roleId, long userId)
		throws Exception {

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), userId);

		_userGroupRoleLocalService.addUserGroupRole(
			userId, accountEntry.getAccountEntryGroupId(), roleId);
	}

	private void _updateCommerceTaxByAddressTypeConfiguration(
			boolean taxAppliedToShippingAddress)
		throws Exception {

		Settings settings = FallbackKeysSettingsUtil.getSettings(
			new GroupServiceSettingsLocator(
				_commerceChannel.getGroupId(),
				CommerceTaxByAddressTypeConfiguration.class.getName()));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		modifiableSettings.setValue(
			"taxAppliedToShippingAddress",
			String.valueOf(taxAppliedToShippingAddress));

		modifiableSettings.store();
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private AddressLocalService _addressLocalService;

	private Role _buyerRole;

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	private CommerceChannel _commerceChannel;
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceOrderEngine _commerceOrderEngine;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

	private CommerceShippingMethod _commerceShippingMethod;

	@Inject
	private CommerceTaxFixedRateAddressRelLocalService
		_commerceTaxFixedRateAddressRelLocalService;

	@Inject
	private CommerceTaxFixedRateLocalService _commerceTaxFixedRateLocalService;

	@Inject
	private ConfigurationProvider _configurationProvider;

	private CPInstance _cpInstance;
	private CPTaxCategory _cpTaxCategory;

	@Inject
	private CPTaxCategoryLocalService _cpTaxCategoryLocalService;

	private Group _group;
	private Role _orderManagerRole;
	private String _originalName;
	private PermissionChecker _originalPermissionChecker;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	private ServiceContext _serviceContext;
	private User _user;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}