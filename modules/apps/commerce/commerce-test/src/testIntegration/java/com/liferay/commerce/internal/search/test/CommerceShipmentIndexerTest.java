/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceShipmentItemLocalService;
import com.liferay.commerce.service.CommerceShipmentLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

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
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
@Sync
public class CommerceShipmentIndexerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser(_group.getGroupId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId());

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_commerceCatalog = CommerceTestUtil.addCommerceCatalog(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId(),
			_commerceCurrency.getCode());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		_cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog.getGroupId());

		CommerceTestUtil.updateBackOrderCPDefinitionInventory(
			_cpInstance.getCPDefinition());

		_indexer = _indexerRegistry.getIndexer(CommerceShipment.class);
	}

	@Test
	public void testIndexCommerceOrderUserIds() throws Exception {
		CommerceOrder commerceOrder = _addPlacedCommerceOrder(_user);

		CommerceOrderItem commerceOrderItem = _getCommerceOrderItem(
			commerceOrder);

		CommerceShipment commerceShipment = _addCommerceShipment(commerceOrder);

		_addCommerceShipmentItem(commerceShipment, commerceOrderItem);

		Document document = _searchCommerceShipmentDocument(
			commerceShipment.getCommerceShipmentId(),
			commerceOrder.getCommerceOrderId());

		Assert.assertEquals(
			String.valueOf(commerceOrder.getCommerceAccountId()),
			document.get("commerceAccountId"));
		Assert.assertEquals(
			String.valueOf(_commerceChannel.getCommerceChannelId()),
			document.get("commerceChannelId"));
		Assert.assertEquals(
			Arrays.asList(String.valueOf(_user.getUserId())),
			ListUtil.fromArray(document.getValues("commerceOrderUserIds")));
	}

	@Test
	public void testIndexConsolidatedCommerceOrderUserIds() throws Exception {
		User user2 = UserTestUtil.addUser(_group.getGroupId());

		CommerceOrder commerceOrder1 = _addPlacedCommerceOrder(_user);
		CommerceOrder commerceOrder2 = _addCommerceOrderWithItem(user2);

		CommerceShipment commerceShipment = _addCommerceShipment(
			commerceOrder1);

		_addCommerceShipmentItem(
			commerceShipment, _getCommerceOrderItem(commerceOrder1));
		_addCommerceShipmentItem(
			commerceShipment, _getCommerceOrderItem(commerceOrder2));

		Document document = _searchCommerceShipmentDocument(
			commerceShipment.getCommerceShipmentId(),
			commerceOrder1.getCommerceOrderId(),
			commerceOrder2.getCommerceOrderId());

		List<String> commerceOrderUserIds = ListUtil.fromArray(
			document.getValues("commerceOrderUserIds"));

		Assert.assertEquals(
			commerceOrderUserIds.toString(), 2, commerceOrderUserIds.size());
		Assert.assertTrue(
			commerceOrderUserIds.contains(String.valueOf(_user.getUserId())));
		Assert.assertTrue(
			commerceOrderUserIds.contains(String.valueOf(user2.getUserId())));
	}

	@Test
	public void testReindexCommerceOrderUserIdsOnShipmentItemDeletion()
		throws Exception {

		User user2 = UserTestUtil.addUser(_group.getGroupId());

		CommerceOrder commerceOrder1 = _addPlacedCommerceOrder(_user);
		CommerceOrder commerceOrder2 = _addCommerceOrderWithItem(user2);

		CommerceShipment commerceShipment = _addCommerceShipment(
			commerceOrder1);

		_addCommerceShipmentItem(
			commerceShipment, _getCommerceOrderItem(commerceOrder1));

		CommerceShipmentItem commerceShipmentItem = _addCommerceShipmentItem(
			commerceShipment, _getCommerceOrderItem(commerceOrder2));

		_commerceShipmentItemLocalService.deleteCommerceShipmentItem(
			commerceShipmentItem, true);

		Document document = _searchCommerceShipmentDocument(
			commerceShipment.getCommerceShipmentId(),
			commerceOrder1.getCommerceOrderId());

		Assert.assertEquals(
			Arrays.asList(String.valueOf(_user.getUserId())),
			ListUtil.fromArray(document.getValues("commerceOrderUserIds")));
	}

	private CommerceOrder _addCommerceOrderWithItem(User user)
		throws Exception {

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency.getCommerceCurrencyId());

		CommerceTestUtil.addCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), _cpInstance.getCPInstanceId(),
			BigDecimal.TEN);

		commerceOrder = _commerceOrderLocalService.getCommerceOrder(
			commerceOrder.getCommerceOrderId());

		_commerceOrders.add(commerceOrder);

		return commerceOrder;
	}

	private CommerceShipment _addCommerceShipment(CommerceOrder commerceOrder)
		throws Exception {

		CommerceShipment commerceShipment =
			_commerceShipmentLocalService.addCommerceShipment(
				commerceOrder.getCommerceOrderId(), _serviceContext);

		_commerceShipments.add(commerceShipment);

		return commerceShipment;
	}

	private CommerceShipmentItem _addCommerceShipmentItem(
			CommerceShipment commerceShipment,
			CommerceOrderItem commerceOrderItem)
		throws Exception {

		return _commerceShipmentItemLocalService.addCommerceShipmentItem(
			RandomTestUtil.randomString(),
			commerceShipment.getCommerceShipmentId(),
			commerceOrderItem.getCommerceOrderItemId(), 0, BigDecimal.ONE, null,
			false, _serviceContext);
	}

	private CommerceOrder _addPlacedCommerceOrder(User user) throws Exception {
		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency.getCommerceCurrencyId());

		CommerceTestUtil.addCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), _cpInstance.getCPInstanceId(),
			BigDecimal.TEN);

		commerceOrder = _commerceOrderLocalService.getCommerceOrder(
			commerceOrder.getCommerceOrderId());

		commerceOrder = CommerceTestUtil.addCommerceOrderShippingDetails(
			commerceOrder, BigDecimal.valueOf(RandomTestUtil.nextDouble()));

		commerceOrder.setOrderStatus(
			CommerceOrderConstants.ORDER_STATUS_PROCESSING);

		commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder);

		_commerceOrders.add(commerceOrder);

		return commerceOrder;
	}

	private CommerceOrderItem _getCommerceOrderItem(CommerceOrder commerceOrder)
		throws Exception {

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		return commerceOrderItems.get(0);
	}

	private Document _searchCommerceShipmentDocument(
			long commerceShipmentId, long... commerceOrderIds)
		throws Exception {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute("commerceOrderIds", commerceOrderIds);
		searchContext.setCompanyId(_group.getCompanyId());
		searchContext.setGroupIds(new long[] {_commerceChannel.getGroupId()});

		Hits hits = _indexer.search(searchContext);

		for (Document document : hits.getDocs()) {
			if (commerceShipmentId == GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK))) {

				return document;
			}
		}

		throw new AssertionError(
			"Unable to find indexed commerce shipment " + commerceShipmentId);
	}

	@DeleteAfterTestRun
	private CommerceCatalog _commerceCatalog;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@DeleteAfterTestRun
	private final List<CommerceOrder> _commerceOrders = new ArrayList<>();

	@Inject
	private CommerceShipmentItemLocalService _commerceShipmentItemLocalService;

	@Inject
	private CommerceShipmentLocalService _commerceShipmentLocalService;

	@DeleteAfterTestRun
	private final List<CommerceShipment> _commerceShipments = new ArrayList<>();

	private CPInstance _cpInstance;
	private Group _group;
	private Indexer<CommerceShipment> _indexer;

	@Inject
	private IndexerRegistry _indexerRegistry;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}