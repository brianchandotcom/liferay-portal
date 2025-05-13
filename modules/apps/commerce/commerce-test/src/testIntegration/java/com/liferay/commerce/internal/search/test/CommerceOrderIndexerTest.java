/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
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

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michele Vigilante
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
	}

	@Test
	public void testCommercePlacedOrderShippingAddressData() throws Exception {
		Address randomAddress1 = _addRandomAddress();

		CommerceOrder commerceOrder1 = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		commerceOrder1.setBillingAddressId(randomAddress1.getAddressId());
		commerceOrder1.setShippingAddressId(randomAddress1.getAddressId());

		commerceOrder1 = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder1);

		commerceOrder1 = _commerceOrderEngine.checkoutCommerceOrder(
			commerceOrder1, _user.getUserId());

		commerceOrder1 = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder1);

		Address randomAddress2 = _addRandomAddress();

		CommerceOrder commerceOrder2 = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		commerceOrder2.setBillingAddressId(randomAddress2.getAddressId());
		commerceOrder2.setShippingAddressId(randomAddress2.getAddressId());

		commerceOrder2 = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder2);

		commerceOrder2 = _commerceOrderEngine.checkoutCommerceOrder(
			commerceOrder2, _user.getUserId());

		_commerceOrderLocalService.updateCommerceOrder(commerceOrder2);

		Country country = randomAddress1.getCountry();

		_assertSearch(country.getA2(), commerceOrder1);
		_assertSearch(country.getName(), commerceOrder1);

		_assertSearch(randomAddress1.getCity(), commerceOrder1);
		_assertSearch(
			randomAddress1.getExternalReferenceCode(), commerceOrder1);
		_assertSearch(randomAddress1.getName(), commerceOrder1);
		_assertSearch(randomAddress1.getStreet1(), commerceOrder1);
		_assertSearch(randomAddress1.getStreet2(), commerceOrder1);
		_assertSearch(randomAddress1.getStreet3(), commerceOrder1);
		_assertSearch(randomAddress1.getZip(), commerceOrder1);

		Region region = randomAddress1.getRegion();

		_assertSearch(region.getName(), commerceOrder1);
	}

	private Address _addRandomAddress() throws Exception {
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
			String keywords, CommerceOrder... expectedCommerceOrders)
		throws Exception {

		SearchContext searchContext = _getSearchContext();

		searchContext.setKeywords(keywords);

		Hits hits = _indexer.search(searchContext);

		_assertSearch(hits, expectedCommerceOrders);
	}

	private CommerceOrder _getCommerceOrder(Document document)
		throws Exception {

		long commerceOrderId = GetterUtil.getLong(
			document.get(Field.ENTRY_CLASS_PK));

		return _commerceOrderLocalService.getCommerceOrder(commerceOrderId);
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
			commerceOrders.add(_getCommerceOrder(document));
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

	@Inject
	private static IndexerRegistry _indexerRegistry;

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AddressLocalService _addressLocalService;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceOrderEngine _commerceOrderEngine;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	private Group _group;
	private Indexer<CommerceOrder> _indexer;
	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}