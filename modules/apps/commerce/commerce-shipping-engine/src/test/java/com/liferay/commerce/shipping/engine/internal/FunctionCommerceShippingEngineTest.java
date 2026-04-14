/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.internal;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.shipping.engine.internal.configuration.FunctionCommerceShippingEngineConfiguration;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.portal.catapult.PortalCatapult;
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;

import java.math.BigDecimal;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Crescenzo Rega
 */
public class FunctionCommerceShippingEngineTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		_setUpCommerceChannelLocalService();
		_setUpCommerceShippingMethodLocalService();
		_setUpDtoConverterRegistry();
		_setUpFunctionCommerceShippingEngineConfiguration();
		_setUpJSONFactory();
		_setUpPortalCatapult();
		_setUpUserService();
	}

	@Test
	public void testDeactivate() throws Exception {
		_functionCommerceShippingEngine.deactivate();

		Mockito.verify(
			_commerceShippingMethodLocalService, Mockito.never()
		).deleteCommerceShippingMethod(
			Mockito.anyLong()
		);
	}

	@Test
	public void testGetCommerceShippingOptions() throws Exception {
		List<CommerceShippingOption> commerceShippingOptions =
			_functionCommerceShippingEngine.getCommerceShippingOptions(
				_setUpCommerceContext(), _setUpCommerceOrder(), null);

		Assert.assertNotNull(commerceShippingOptions);

		for (CommerceShippingOption commerceShippingOption :
				commerceShippingOptions) {

			if (Objects.equals(commerceShippingOption.getKey(), "CXOption1")) {
				Assert.assertTrue(
					BigDecimalUtil.eq(
						commerceShippingOption.getAmount(),
						BigDecimal.valueOf(1.0)));

				continue;
			}
			else if (Objects.equals(
						commerceShippingOption.getKey(), "CXOption2")) {

				Assert.assertTrue(
					BigDecimalUtil.eq(
						commerceShippingOption.getAmount(),
						BigDecimal.valueOf(0.0)));

				continue;
			}
			else if (Objects.equals(
						commerceShippingOption.getKey(), "CXOption3")) {

				Assert.assertTrue(
					BigDecimalUtil.eq(
						commerceShippingOption.getAmount(),
						BigDecimal.valueOf(17.5)));

				continue;
			}

			Assert.assertTrue(false);
		}
	}

	private void _setUpCommerceChannelLocalService() throws Exception {
		CommerceChannel commerceChannel = Mockito.mock(CommerceChannel.class);

		Mockito.when(
			commerceChannel.getGroupId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			_commerceChannelLocalService.getCommerceChannel(Mockito.anyLong())
		).thenReturn(
			commerceChannel
		);
	}

	private CommerceContext _setUpCommerceContext() throws Exception {
		CommerceContext commerceContext = Mockito.mock(CommerceContext.class);

		Mockito.when(
			commerceContext.getAccountEntry()
		).thenReturn(
			Mockito.mock(AccountEntry.class)
		);

		Mockito.when(
			commerceContext.getCommerceChannelId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		return commerceContext;
	}

	private CommerceOrder _setUpCommerceOrder() {
		CommerceOrder commerceOrder = Mockito.mock(CommerceOrder.class);

		Mockito.when(
			commerceOrder.getCommerceAccountId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			commerceOrder.getCommerceCurrencyCode()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			commerceOrder.getCommerceOrderId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			commerceOrder.getCompanyId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		CommerceOrderItem commerceOrderItem = Mockito.mock(
			CommerceOrderItem.class);

		Mockito.when(
			commerceOrderItem.getCommerceOrderItemId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			commerceOrder.getCommerceOrderItems()
		).thenReturn(
			Collections.singletonList(commerceOrderItem)
		);

		return commerceOrder;
	}

	private void _setUpCommerceShippingMethodLocalService() {
		CommerceShippingMethod commerceShippingMethod = Mockito.mock(
			CommerceShippingMethod.class);

		Mockito.when(
			commerceShippingMethod.getTypeSettingsUnicodeProperties()
		).thenReturn(
			RandomTestUtil.randomUnicodeProperties(4, 3, 3)
		);

		Mockito.when(
			_commerceShippingMethodLocalService.fetchCommerceShippingMethod(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			commerceShippingMethod
		);
	}

	private void _setUpDtoConverterRegistry() throws Exception {
		DTOConverter<?, ?> dtoConverter = Mockito.mock(DTOConverter.class);

		Mockito.doReturn(
			dtoConverter
		).when(
			_dtoConverterRegistry
		).getDTOConverter(
			Mockito.anyString(), Mockito.anyString(), Mockito.anyString()
		);

		Mockito.doReturn(
			new Order()
		).when(
			dtoConverter
		).toDTO(
			(DTOConverterContext)Mockito.any()
		);
	}

	private void _setUpFunctionCommerceShippingEngineConfiguration() {
		Mockito.when(
			_functionCommerceShippingEngineConfiguration.key()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_functionCommerceShippingEngineConfiguration.
				oAuth2ApplicationExternalReferenceCode()
		).thenReturn(
			RandomTestUtil.randomString()
		);
	}

	private void _setUpJSONFactory() throws Exception {
		Mockito.when(
			_jsonFactory.createJSONObject(Mockito.anyString())
		).thenReturn(
			JSONUtil.put(
				"shippingOptions",
				JSONUtil.putAll(
					JSONUtil.put(
						"amount", 1.0
					).put(
						"key", "CXOption1"
					).put(
						"name", "CXOption1"
					).put(
						"priority", 1
					)
				).put(
					JSONUtil.put(
						"amount", 0.0
					).put(
						"key", "CXOption2"
					).put(
						"name", "CXOption2"
					).put(
						"priority", 2
					)
				).put(
					JSONUtil.put(
						"amount", 17.50
					).put(
						"key", "CXOption3"
					).put(
						"name", "CXOption3"
					).put(
						"priority", 3
					)
				))
		);

		Mockito.when(
			_jsonFactory.createJSONArray()
		).thenReturn(
			new JSONArrayImpl()
		);

		Mockito.when(
			_jsonFactory.createJSONObject()
		).thenReturn(
			new JSONObjectImpl()
		);

		Mockito.when(
			_jsonFactory.looseSerializeDeep(Mockito.any())
		).thenReturn(
			RandomTestUtil.randomString()
		);
	}

	private void _setUpPortalCatapult() throws Exception {
		Future<byte[]> future = Mockito.mock(Future.class);

		Mockito.when(
			future.get()
		).thenReturn(
			RandomTestUtil.randomBytes()
		);

		Mockito.when(
			_portalCatapult.launch(
				Mockito.anyLong(), Mockito.any(), Mockito.anyString(),
				Mockito.any(), Mockito.anyString(), Mockito.anyLong())
		).thenReturn(
			future
		);
	}

	private void _setUpUserService() throws Exception {
		User user = Mockito.mock(User.class);

		Mockito.when(
			user.getUserId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			_userService.getCurrentUser()
		).thenReturn(
			user
		);
	}

	@Mock
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Mock
	private CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;

	@Mock
	private DTOConverterRegistry _dtoConverterRegistry;

	@InjectMocks
	private FunctionCommerceShippingEngine _functionCommerceShippingEngine;

	@Mock
	private FunctionCommerceShippingEngineConfiguration
		_functionCommerceShippingEngineConfiguration;

	@Mock
	private JSONFactory _jsonFactory;

	@Mock
	private PortalCatapult _portalCatapult;

	@Mock
	private UserService _userService;

}