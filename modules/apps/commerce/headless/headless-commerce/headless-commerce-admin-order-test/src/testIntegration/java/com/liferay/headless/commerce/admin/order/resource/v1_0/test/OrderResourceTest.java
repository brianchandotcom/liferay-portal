/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.order.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.client.pagination.Page;
import com.liferay.headless.commerce.admin.order.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 * @author Riccardo Ferrari
 * @author Stefano Motta
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class OrderResourceTest extends BaseOrderResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		User user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			user.getUserId());

		_accountEntry = _accountEntryLocalService.addAccountEntry(
			user.getUserId(), 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null, null,
			"business", 1, _serviceContext);

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			testGroup.getCompanyId());

		_commerceChannel = _commerceChannelLocalService.addCommerceChannel(
			RandomTestUtil.randomString(),
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT, testGroup.getGroupId(),
			RandomTestUtil.randomString(),
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
			_commerceCurrency.getCode(), _serviceContext);

		Country country = _countryLocalService.addCountry(
			"XY", "XYZ", true, true, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.nextDouble(), true, true, false, _serviceContext);

		Region region = _regionLocalService.addRegion(
			country.getCountryId(), true, RandomTestUtil.randomString(),
			RandomTestUtil.nextDouble(), RandomTestUtil.randomString(),
			_serviceContext);

		_orderAddress = _addressLocalService.addAddress(
			RandomTestUtil.randomString(), user.getUserId(),
			AccountEntry.class.getName(), _accountEntry.getAccountEntryId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), region.getRegionId(),
			country.getCountryId(), 0, false, true,
			RandomTestUtil.randomString(), _serviceContext);
	}

	@Ignore
	@Override
	@Test
	public void testGetOrdersPage() throws Exception {
		super.testGetOrdersPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetOrdersPageWithFilterDateTimeEquals() throws Exception {
		super.testGetOrdersPageWithFilterDateTimeEquals();
	}

	@Override
	@Test
	public void testGetOrdersPageWithFilterStringEquals() throws Exception {

		// Fixes generated test to filter for different order creators

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Order order1 = testGetOrdersPage_addOrder(randomOrder());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Order order2 = testGetOrdersPage_addOrder(randomOrder());

		for (EntityField entityField : entityFields) {
			String entityFieldName = entityField.getName();

			if (entityFieldName.equals("creatorEmailAddress")) {
				Role role = _roleLocalService.getRole(
					testCompany.getCompanyId(), RoleConstants.ADMINISTRATOR);
				User user = UserTestUtil.addUser(
					testCompany.getCompanyId(), testCompany.getUserId(), "test",
					"UserServiceTest." + RandomTestUtil.nextLong() +
						"@liferay.com",
					StringPool.BLANK, LocaleUtil.getDefault(),
					"UserServiceTest", "UserServiceTest", null,
					_serviceContext);

				_userLocalService.addRoleUser(role.getRoleId(), user);

				orderResource = OrderResource.builder(
				).authentication(
					user.getEmailAddress(), "test"
				).locale(
					LocaleUtil.getDefault()
				).build();

				Order order3 = orderResource.postOrder(randomOrder());

				Page<Order> page = orderResource.getOrdersPage(
					null, getFilterString(entityField, "eq", order3),
					Pagination.of(1, 2), null);

				assertEquals(
					Collections.singletonList(order3),
					(List<Order>)page.getItems());
			}
			else {
				Page<Order> page = orderResource.getOrdersPage(
					null, getFilterString(entityField, "eq", order1),
					Pagination.of(1, 2), null);

				assertEquals(
					Collections.singletonList(order1),
					(List<Order>)page.getItems());
			}
		}
	}

	@Ignore
	@Override
	@Test
	public void testGetOrdersPageWithPagination() throws Exception {
		super.testGetOrdersPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetOrdersPageWithSortDateTime() throws Exception {
		super.testGetOrdersPageWithSortDateTime();
	}

	@Override
	@Test
	public void testGetOrdersPageWithSortInteger() throws Exception {
		super.testGetOrdersPageWithSortInteger();
	}

	@Test
	public void testGetOrderWithNestedFields() throws Exception {
		OrderResource orderResource = OrderResource.builder(
		).authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).parameters(
			"nestedFields", "orderItems,orderItems.shippingAddress"
		).build();

		Order expectedOrder = orderResource.postOrder(
			_randomOrderWithNestedFields());

		Order actualOrder = orderResource.getOrder(expectedOrder.getId());

		assertEquals(expectedOrder, actualOrder);

		OrderItem[] expectedOrderItems = expectedOrder.getOrderItems();

		OrderItem[] actualOrderItems = actualOrder.getOrderItems();

		Assert.assertEquals(
			Arrays.toString(actualOrderItems), expectedOrderItems.length,
			actualOrderItems.length);
		Assert.assertNotNull(actualOrderItems[0].getShippingAddress());
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteOrder() throws Exception {
		super.testGraphQLDeleteOrder();
	}

	@Ignore
	@Override
	@Test
	public void testPatchOrder() throws Exception {
		super.testPatchOrder();
	}

	@Ignore
	@Override
	@Test
	public void testPatchOrderByExternalReferenceCode() throws Exception {
		super.testPatchOrderByExternalReferenceCode();
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"currencyCode", "paymentMethod", "printedNote",
			"purchaseOrderNumber"
		};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"channelId", "creatorEmailAddress", "orderId"};
	}

	@Override
	protected Order randomOrder() throws Exception {
		return new Order() {
			{
				accountExternalReferenceCode =
					_accountEntry.getExternalReferenceCode();
				accountId = _accountEntry.getAccountEntryId();
				advanceStatus = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				billingAddressId = _orderAddress.getAddressId();
				channelExternalReferenceCode =
					_commerceChannel.getExternalReferenceCode();
				channelId = _commerceChannel.getCommerceChannelId();
				couponCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				createDate = RandomTestUtil.nextDate();
				currencyCode = _commerceCurrency.getCode();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				modifiedDate = RandomTestUtil.nextDate();
				paymentMethod = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				printedNote = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				purchaseOrderNumber = RandomTestUtil.randomString();
				requestedDeliveryDate = RandomTestUtil.nextDate();
				shippingAddressId = _orderAddress.getAddressId();
			}
		};
	}

	@Override
	protected Order testDeleteOrder_addOrder() throws Exception {
		return orderResource.postOrder(randomOrder());
	}

	@Override
	protected Order testDeleteOrderByExternalReferenceCode_addOrder()
		throws Exception {

		return orderResource.postOrder(randomOrder());
	}

	@Override
	protected Order testGetOrder_addOrder() throws Exception {
		return orderResource.postOrder(randomOrder());
	}

	@Override
	protected Order testGetOrderByExternalReferenceCode_addOrder()
		throws Exception {

		return orderResource.postOrder(randomOrder());
	}

	@Override
	protected Order testGetOrdersPage_addOrder(Order order) throws Exception {
		return orderResource.postOrder(order);
	}

	@Override
	protected Order testGraphQLOrder_addOrder() throws Exception {
		return orderResource.postOrder(randomOrder());
	}

	@Override
	protected Order testPostOrder_addOrder(Order order) throws Exception {
		return orderResource.postOrder(order);
	}

	private OrderItem _randomOrderItem() throws Exception {
		CPInstance cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId());

		return new OrderItem() {
			{
				bookedQuantityId = RandomTestUtil.randomLong();
				deliveryGroup = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				orderExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				orderId = RandomTestUtil.randomLong();
				printedNote = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				quantity = BigDecimal.valueOf(RandomTestUtil.randomInt());
				shippedQuantity = BigDecimal.valueOf(
					RandomTestUtil.randomInt());
				shippingAddressId = _orderAddress.getAddressId();
				skuId = cpInstance.getCPInstanceId();
				subscription = RandomTestUtil.randomBoolean();
			}
		};
	}

	private Order _randomOrderWithNestedFields() throws Exception {
		Order order = randomOrder();

		OrderItem orderItem = _randomOrderItem();

		orderItem.setOrderId(order.getId());

		order.setOrderItems(new OrderItem[] {orderItem});

		return order;
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AddressLocalService _addressLocalService;

	private CommerceChannel _commerceChannel;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Inject
	private CountryLocalService _countryLocalService;

	private Address _orderAddress;

	@Inject
	private RegionLocalService _regionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	private ServiceContext _serviceContext;

	@Inject
	private UserLocalService _userLocalService;

}