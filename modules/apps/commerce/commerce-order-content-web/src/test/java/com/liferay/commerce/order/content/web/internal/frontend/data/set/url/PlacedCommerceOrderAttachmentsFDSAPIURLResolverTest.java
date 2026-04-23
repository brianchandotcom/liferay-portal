/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.frontend.data.set.url;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.util.CommerceOrderInfoItemUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
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
 * @author Tancredi Covioli
 */
public class PlacedCommerceOrderAttachmentsFDSAPIURLResolverTest {

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

		_commerceOrderInfoItemUtilMockedStatic.when(
			() -> CommerceOrderInfoItemUtil.getCommerceOrder(
				Mockito.any(), Mockito.any())
		).thenReturn(
			_commerceOrder
		);
	}

	@Test
	public void testResolve() throws PortalException {
		String baseURLString =
			"/commerce/order-attachment?filter=r_" +
				"commerceOrderToCommerceOrderAttachments_commerceOrderId eq '";

		Assert.assertEquals(
			baseURLString + _commerceOrder.getCommerceOrderId() +
				StringPool.APOSTROPHE,
			_placedCommerceOrderAttachmentsFDSAPIURLResolver.resolve(
				baseURLString + "{commerceOrderId}'", _mockHttpServletRequest));
	}

	private static final MockedStatic<CommerceOrderInfoItemUtil>
		_commerceOrderInfoItemUtilMockedStatic = Mockito.mockStatic(
			CommerceOrderInfoItemUtil.class);

	@Mock
	private CommerceOrder _commerceOrder;

	private final MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();

	@InjectMocks
	private PlacedCommerceOrderAttachmentsFDSAPIURLResolver
		_placedCommerceOrderAttachmentsFDSAPIURLResolver;

}