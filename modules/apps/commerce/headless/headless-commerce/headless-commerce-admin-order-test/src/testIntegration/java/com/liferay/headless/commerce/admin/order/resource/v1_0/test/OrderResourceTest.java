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

package com.liferay.headless.commerce.admin.order.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.ShippingAddress;
import com.liferay.headless.commerce.admin.order.client.pagination.Page;
import com.liferay.headless.commerce.admin.order.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.rule.Inject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 * @author Riccardo Ferrari
 */
@RunWith(Arquillian.class)
public class OrderResourceTest extends BaseOrderResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_commerceAccount = CommerceTestUtil.addAccount(
			testGroup.getGroupId(), _user.getUserId());
		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_user.getCompanyId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		_commerceChannel = _commerceChannelLocalService.addCommerceChannel(
			StringPool.BLANK, testGroup.getGroupId(),
			testGroup.getName(_serviceContext.getLanguageId()) + " Portal",
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
			_commerceCurrency.getCode(), _serviceContext);
	}

	@Override
	@Test
	public void testGetOrder() throws Exception {
		super.testGetOrder();

		// Nested fields

		OrderResource orderResource = OrderResource.builder(
		).authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).parameters(
			"nestedFields", "orderItems,orderItems.shippingAddress"
		).build();

		Order expectedOrder = orderResource.postOrder(_randomOrder());

		Order actualOrder = orderResource.getOrder(expectedOrder.getId());

		assertEquals(expectedOrder, actualOrder);

		OrderItem[] expectedOrderItems = expectedOrder.getOrderItems();

		OrderItem[] actualOrderItems = actualOrder.getOrderItems();

		Assert.assertEquals(
			Arrays.toString(actualOrderItems), expectedOrderItems.length,
			actualOrderItems.length);
		Assert.assertNotNull(actualOrderItems[0].getShippingAddress());
	}

	@Override
	@Test
	public void testGetOrdersPage() throws Exception {
	}

	@Override
	@Test
	public void testGetOrdersPageWithFilterDateTimeEquals() throws Exception {
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
				Role role = RoleLocalServiceUtil.getRole(
					testCompany.getCompanyId(), RoleConstants.ADMINISTRATOR);
				User user = UserTestUtil.addUser(
					testCompany.getCompanyId(), testCompany.getUserId(), "test",
					"UserServiceTest." + RandomTestUtil.nextLong() +
						"@liferay.com",
					StringPool.BLANK, LocaleUtil.getDefault(),
					"UserServiceTest", "UserServiceTest", null,
					_serviceContext);

				UserLocalServiceUtil.addRoleUser(role.getRoleId(), user);

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

	@Override
	@Test
	public void testGetOrdersPageWithPagination() throws Exception {
	}

	@Override
	@Test
	public void testGetOrdersPageWithSortDateTime() throws Exception {
	}

	@Override
	@Test
	public void testGetOrdersPageWithSortInteger() throws Exception {
	}

	@Override
	@Test
	public void testGraphQLDeleteOrder() throws Exception {
	}

	@Override
	@Test
	public void testGraphQLGetOrder() throws Exception {
	}

	@Override
	@Test
	public void testGraphQLGetOrderByExternalReferenceCode() throws Exception {
	}

	@Override
	@Test
	public void testGraphQLGetOrderByExternalReferenceCodeNotFound()
		throws Exception {
	}

	@Override
	@Test
	public void testGraphQLGetOrderNotFound() throws Exception {
	}

	@Override
	@Test
	public void testGraphQLGetOrdersPage() throws Exception {
	}

	@Override
	@Test
	public void testPatchOrder() throws Exception {
	}

	@Override
	@Test
	public void testPatchOrderByExternalReferenceCode() throws Exception {
	}

	@Override
	protected Order randomOrder() throws Exception {
		Order order = super.randomOrder();

		order.setAccountId(_commerceAccount.getCommerceAccountId());
		order.setChannelId(_commerceChannel.getCommerceChannelId());
		order.setCurrencyCode(_commerceCurrency.getCode());

		return order;
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
	protected Order testPostOrder_addOrder(Order order) throws Exception {
		return orderResource.postOrder(order);
	}

	private Order _randomOrder() throws Exception {
		Order order = randomOrder();

		ShippingAddress shippingAddress = _randomShippingAddress();

		OrderItem orderItem = _randomOrderItem(shippingAddress);

		orderItem.setOrderId(order.getId());

		order.setOrderItems(new OrderItem[] {orderItem});

		return order;
	}

	private OrderItem _randomOrderItem(ShippingAddress shippingAddress)
		throws Exception {

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
				quantity = RandomTestUtil.randomInt();
				shippedQuantity = RandomTestUtil.randomInt();
				skuId = cpInstance.getCPInstanceId();
				subscription = RandomTestUtil.randomBoolean();

				setShippingAddress(
					() -> {
						if (shippingAddress == null) {
							return null;
						}

						return shippingAddress;
					});
				setShippingAddressId(
					() -> {
						if (shippingAddress == null) {
							return null;
						}

						return shippingAddress.getId();
					});
			}
		};
	}

	private ShippingAddress _randomShippingAddress() throws Exception {
		CommerceAddress commerceAddress =
			CommerceTestUtil.addUserCommerceAddress(
				testGroup.getGroupId(), _user.getUserId());

		Country country = commerceAddress.getCountry();

		return new ShippingAddress() {
			{
				city = commerceAddress.getCity();
				countryISOCode = country.getA2();
				description = commerceAddress.getDescription();
				externalReferenceCode =
					commerceAddress.getExternalReferenceCode();
				id = commerceAddress.getCommerceAddressId();
				latitude = commerceAddress.getLatitude();
				longitude = commerceAddress.getLongitude();
				name = commerceAddress.getName();
				phoneNumber = commerceAddress.getPhoneNumber();
				street1 = commerceAddress.getStreet1();
				street2 = commerceAddress.getStreet2();
				street3 = commerceAddress.getStreet3();
				zip = commerceAddress.getZip();
			}
		};
	}

	private CommerceAccount _commerceAccount;
	private CommerceChannel _commerceChannel;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	private CommerceCurrency _commerceCurrency;
	private ServiceContext _serviceContext;
	private User _user;

}