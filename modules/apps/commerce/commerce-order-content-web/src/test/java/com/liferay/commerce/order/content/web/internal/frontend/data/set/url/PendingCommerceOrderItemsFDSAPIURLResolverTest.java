/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.frontend.data.set.url;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.util.CommerceOrderInfoItemUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Gianmarco Brunialti Masera
 */
public class PendingCommerceOrderItemsFDSAPIURLResolverTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		Mockito.when(
			_commerceOrder.getCommerceOrderId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			_commerceOrder.getExternalReferenceCode()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		_commerceOrderInfoItemUtilMockedStatic.when(
			() -> CommerceOrderInfoItemUtil.getCommerceOrder(
				Mockito.any(), Mockito.any())
		).thenReturn(
			_commerceOrder
		);
	}

	@Test
	public void testResolve() {
		Assert.assertEquals(
			_pendingCommerceOrderItemsFDSAPIURLResolver.resolve(
				"/v1.0/carts/by-externalReferenceCode/{externalReferenceCode}" +
					"/items",
				_mockHttpServletRequest),
			StringBundler.concat(
				"/v1.0/carts/by-externalReferenceCode/",
				_commerceOrder.getExternalReferenceCode(), "/items"));
		Assert.assertEquals(
			_pendingCommerceOrderItemsFDSAPIURLResolver.resolve(
				"/v1.0/carts/{cartId}/items", _mockHttpServletRequest),
			StringBundler.concat(
				"/v1.0/carts/", _commerceOrder.getCommerceOrderId(), "/items"));
	}

	private static final MockedStatic<CommerceOrderInfoItemUtil>
		_commerceOrderInfoItemUtilMockedStatic = Mockito.mockStatic(
			CommerceOrderInfoItemUtil.class);

	@Mock
	private CommerceOrder _commerceOrder;

	private final MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();

	@InjectMocks
	private PendingCommerceOrderItemsFDSAPIURLResolver
		_pendingCommerceOrderItemsFDSAPIURLResolver;

}