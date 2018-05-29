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

package com.liferay.commerce.internal.order.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.internal.test.util.CommerceTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.portal.theme.ThemeDisplayFactory;

import javax.servlet.http.HttpServletRequest;

import org.frutilla.FrutillaRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Luca Pellizzon
 */
@RunWith(Arquillian.class)
public class CommerceOrderHttpHelperImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		_httpServletRequest = new MockHttpServletRequest();

		_themeDisplay = ThemeDisplayFactory.create();

		_themeDisplay.setScopeGroupId(_group.getGroupId());
		_themeDisplay.setUser(_user);
		_themeDisplay.setSignedIn(true);
		_themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		_httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);

		_themeDisplay.setRequest(_httpServletRequest);
	}

	@After
	public void tearDown() {
		CentralizedThreadLocal.clearShortLivedThreadLocals();
	}

	@Test
	public void testGetCommerceOrder() throws Exception {
		frutillaRule.scenario(
			"Get commerce order from http servlet request"
		).given(
			"An HttpServletRequest and a ThemeDisplay"
		).when(
			"I add an order to the HttpServletRequest"
		).then(
			"I should be able to get it from the HttpServletRequest"
		);

		CommerceOrder expectedCommerceOrder =
			_commerceOrderHttpHelper.addCommerceOrder(_httpServletRequest);

		CommerceOrder actualCommerceOrder =
			_commerceOrderHttpHelper.getCurrentCommerceOrder(
				_httpServletRequest);

		Assert.assertEquals(
			expectedCommerceOrder.getCommerceOrderId(),
			actualCommerceOrder.getCommerceOrderId());
	}

	@Test
	public void testGetCommerceOrderItemsQuantity() throws Exception {
		frutillaRule.scenario(
			"Get the total quantity of items in an order retrieved from http " +
				"servlet request"
		).given(
			"A group, a user, an HttpServletRequest and a ThemeDisplay"
		).when(
			"I add an order to the HttpServletRequest"
		).and(
			"I add an item to the order"
		).then(
			"I should be able to get the total amount of items inside the " +
				"order from HttpServletRequest"
		);

		CommerceOrder commerceOrder = _commerceOrderHttpHelper.addCommerceOrder(
			_httpServletRequest);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		_commerceWarehouse = CommerceTestUtil.addCommerceWarehouse(
			_group.getGroupId());

		CommerceTestUtil.addCommerceWarehouseItem(
			_commerceWarehouse, cpInstance.getCPInstanceId(), 10);

		CommerceOrderItem commerceOrderItem =
			CommerceTestUtil.addCommerceOrderItem(
				commerceOrder.getCommerceOrderId(),
				cpInstance.getCPInstanceId(), 2);

		Assert.assertEquals(
			commerceOrderItem.getQuantity(),
			_commerceOrderHttpHelper.getCommerceOrderItemsQuantity(
				_httpServletRequest));
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	@Inject
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@DeleteAfterTestRun
	private CommerceWarehouse _commerceWarehouse;

	@DeleteAfterTestRun
	private Group _group;

	private HttpServletRequest _httpServletRequest;
	private ThemeDisplay _themeDisplay;

	@DeleteAfterTestRun
	private User _user;

}