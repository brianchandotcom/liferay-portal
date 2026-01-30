/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search.test;

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.configuration.CommerceAccountGroupServiceConfiguration;
import com.liferay.commerce.configuration.CommerceOrderConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.commerce.util.CommerceGroupThreadLocal;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.settings.FallbackKeysSettingsUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michele Vigilante
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
@Sync
public class CommerceOrderIndexerTest {

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
		_orderManagerRole = _roleLocalService.getRole(
			_group.getCompanyId(),
			AccountRoleConstants.ROLE_NAME_ACCOUNT_ORDER_MANAGER);

		_user = UserTestUtil.addUser(_group.getGroupId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId());

		_accountEntry = _accountEntryLocalService.addAccountEntry(
			StringPool.BLANK, _user.getUserId(), 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null,
			RandomTestUtil.randomString(), "business", 1, _serviceContext);

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		_indexer = _indexerRegistry.getIndexer(CommerceOrder.class);

		CommerceGroupThreadLocal.setCommerceGroupWithSafeCloseable(
			_commerceChannel.getGroupId());
	}

	@After
	public void tearDown() throws Exception {
		_configurationProvider.deleteGroupConfiguration(
			CommerceAccountGroupServiceConfiguration.class,
			_commerceChannel.getCompanyId(), _commerceChannel.getGroupId());
		_configurationProvider.deleteGroupConfiguration(
			CommerceOrderConfiguration.class, _commerceChannel.getCompanyId(),
			_commerceChannel.getGroupId());
	}

	@Test
	public void testCommerceOrderUserVisibilityScope() throws Exception {
		_updateCommerceAccountConfiguration();
		_updateCommerceOrderConfiguration();

		AccountEntry accountEntry =
			CommerceAccountTestUtil.addBusinessAccountEntry(
				_serviceContext.getUserId(), "Test Business Account", null,
				null, new long[] {_user.getUserId()}, null, _serviceContext);

		User orderManagerUser = UserTestUtil.addUser(_group.getGroupId());

		_addUserAccountRole(
			accountEntry, _orderManagerRole.getRoleId(),
			orderManagerUser.getUserId());

		CommerceOrder orderManagerCommerceOrder = _addUserCommerceOrder(
			accountEntry.getAccountEntryId(), orderManagerUser);

		User buyerUser = UserTestUtil.addUser(_group.getGroupId());

		_addUserAccountRole(
			accountEntry, _buyerRole.getRoleId(), buyerUser.getUserId());

		CommerceOrder buyerCommerceOrder = _addUserCommerceOrder(
			accountEntry.getAccountEntryId(), buyerUser);

		SearchContext searchContext = _getSearchContext();

		searchContext.setAttribute(
			"commerceAccountIds",
			new long[] {accountEntry.getAccountEntryId()});
		searchContext.setAttribute(
			"orderStatuses",
			new int[] {CommerceOrderConstants.ORDER_STATUS_OPEN});

		_assertSearch(searchContext, buyerUser, buyerCommerceOrder);
		_assertSearch(
			searchContext, orderManagerUser, buyerCommerceOrder,
			orderManagerCommerceOrder);

		searchContext.setAttribute("negateOrderStatuses", Boolean.TRUE);

		buyerCommerceOrder.setOrderStatus(
			CommerceOrderConstants.ORDER_STATUS_COMPLETED);
		orderManagerCommerceOrder.setOrderStatus(
			CommerceOrderConstants.ORDER_STATUS_COMPLETED);

		buyerCommerceOrder = _commerceOrderLocalService.updateCommerceOrder(
			buyerCommerceOrder);
		orderManagerCommerceOrder =
			_commerceOrderLocalService.updateCommerceOrder(
				orderManagerCommerceOrder);

		_assertSearch(searchContext, buyerUser, buyerCommerceOrder);
		_assertSearch(
			searchContext, orderManagerUser, buyerCommerceOrder,
			orderManagerCommerceOrder);
	}

	@Test
	public void testPlacedCommerceOrderShippingAddressData() throws Exception {
		CommerceOrder commerceOrder1 = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		Address address1 = _addAddress();

		commerceOrder1.setBillingAddressId(address1.getAddressId());
		commerceOrder1.setShippingAddressId(address1.getAddressId());

		commerceOrder1 = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder1);

		commerceOrder1 = _commerceOrderEngine.checkoutCommerceOrder(
			commerceOrder1, _user.getUserId());

		commerceOrder1 = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder1);

		CommerceOrder commerceOrder2 = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		Address address2 = _addAddress();

		commerceOrder2.setBillingAddressId(address2.getAddressId());
		commerceOrder2.setShippingAddressId(address2.getAddressId());

		commerceOrder2 = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder2);

		commerceOrder2 = _commerceOrderEngine.checkoutCommerceOrder(
			commerceOrder2, _user.getUserId());

		commerceOrder2 = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder2);

		Country country = address1.getCountry();

		_assertSearch(country.getA2(), commerceOrder1);
		_assertSearch(country.getName(), commerceOrder1);

		_assertSearch(address1.getCity(), commerceOrder1);
		_assertSearch(address1.getExternalReferenceCode(), commerceOrder1);
		_assertSearch(address1.getName(), commerceOrder1);
		_assertSearch(address1.getStreet1(), commerceOrder1);
		_assertSearch(address1.getStreet2(), commerceOrder1);
		_assertSearch(address1.getStreet3(), commerceOrder1);
		_assertSearch(address1.getZip(), commerceOrder1);

		Region region = address2.getRegion();

		_assertSearch(region.getName(), commerceOrder2);
	}

	private Address _addAddress() throws Exception {
		Country country = CommerceInventoryTestUtil.addCountry(_serviceContext);

		Region region = CommerceInventoryTestUtil.addRegion(
			country.getCountryId(), _serviceContext);

		return _addressLocalService.addAddress(
			RandomTestUtil.randomString(), _user.getUserId(),
			AccountEntry.class.getName(), _accountEntry.getAccountEntryId(),
			country.getCountryId(), 0, region.getRegionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			RandomTestUtil.randomString(), true, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_serviceContext);
	}

	private void _addUserAccountRole(
			AccountEntry accountEntry, long roleId, long userId)
		throws Exception {

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), userId);

		_userGroupRoleLocalService.addUserGroupRole(
			userId, accountEntry.getAccountEntryGroupId(), roleId);
	}

	private CommerceOrder _addUserCommerceOrder(long accountEntryId, User user)
		throws Exception {

		if (user != null) {
			PrincipalThreadLocal.setName(user.getUserId());

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));
		}

		return _commerceOrderService.addCommerceOrder(
			_commerceChannel.getGroupId(), accountEntryId,
			_commerceCurrency.getCode(), 0);
	}

	private void _assertSearch(
			Hits hits, CommerceOrder... expectedCommerceOrders)
		throws Exception {

		List<CommerceOrder> actualCommerceOrders = _getCommerceOrders(hits);

		long[] actualCommerceOrderIds = _getCommerceOrderIds(
			actualCommerceOrders);

		long[] expectedCommerceOrderIds = _getCommerceOrderIds(
			Arrays.asList(expectedCommerceOrders));

		Assert.assertArrayEquals(
			expectedCommerceOrderIds, actualCommerceOrderIds);
	}

	private void _assertSearch(
			SearchContext searchContext, User user,
			CommerceOrder... expectedCommerceOrders)
		throws Exception {

		if (user != null) {
			PrincipalThreadLocal.setName(user.getUserId());

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));
		}

		Hits hits = _indexer.search(searchContext);

		_assertSearch(hits, expectedCommerceOrders);
	}

	private void _assertSearch(
			String keywords, CommerceOrder... expectedCommerceOrders)
		throws Exception {

		SearchContext searchContext = _getSearchContext();

		searchContext.setKeywords(keywords);

		Hits hits = _indexer.search(searchContext);

		_assertSearch(hits, expectedCommerceOrders);
	}

	private long[] _getCommerceOrderIds(List<CommerceOrder> commerceOrders) {
		long[] commerceOrderIds = new long[commerceOrders.size()];

		for (int i = 0; i < commerceOrders.size(); i++) {
			CommerceOrder commerceOrder = commerceOrders.get(i);

			commerceOrderIds[i] = commerceOrder.getCommerceOrderId();
		}

		Arrays.sort(commerceOrderIds);

		return commerceOrderIds;
	}

	private List<CommerceOrder> _getCommerceOrders(Hits hits) throws Exception {
		Document[] documents = hits.getDocs();

		List<CommerceOrder> commerceOrders = new ArrayList<>(documents.length);

		for (Document document : documents) {
			commerceOrders.add(
				_commerceOrderLocalService.getCommerceOrder(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
		}

		return commerceOrders;
	}

	private SearchContext _getSearchContext() {
		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(_group.getCompanyId());
		searchContext.setGroupIds(new long[] {_commerceChannel.getGroupId()});
		searchContext.setSorts(SortFactoryUtil.getDefaultSorts());

		return searchContext;
	}

	private void _updateCommerceAccountConfiguration() throws Exception {
		Settings settings = FallbackKeysSettingsUtil.getSettings(
			new GroupServiceSettingsLocator(
				_commerceChannel.getGroupId(),
				CommerceConstants.SERVICE_NAME_COMMERCE_ACCOUNT));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		modifiableSettings.setValue(
			"commerceSiteType",
			String.valueOf(CommerceChannelConstants.SITE_TYPE_B2B));

		modifiableSettings.store();
	}

	private void _updateCommerceOrderConfiguration() throws Exception {
		Settings settings = FallbackKeysSettingsUtil.getSettings(
			new GroupServiceSettingsLocator(
				_commerceChannel.getGroupId(),
				CommerceConstants.SERVICE_NAME_COMMERCE_ORDER));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		modifiableSettings.setValue(
			"openOrdersVisibilityScope",
			CommerceOrderConstants.ORDER_VISIBILITY_SCOPE_USER);
		modifiableSettings.setValue(
			"placedOrdersVisibilityScope",
			CommerceOrderConstants.ORDER_VISIBILITY_SCOPE_USER);

		modifiableSettings.store();
	}

	@Inject
	private static IndexerRegistry _indexerRegistry;

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private AddressLocalService _addressLocalService;

	private Role _buyerRole;
	private CommerceChannel _commerceChannel;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceOrderEngine _commerceOrderEngine;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Inject
	private CommerceOrderService _commerceOrderService;

	@Inject
	private ConfigurationProvider _configurationProvider;

	private Group _group;
	private Indexer<CommerceOrder> _indexer;
	private Role _orderManagerRole;

	@Inject
	private RoleLocalService _roleLocalService;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}