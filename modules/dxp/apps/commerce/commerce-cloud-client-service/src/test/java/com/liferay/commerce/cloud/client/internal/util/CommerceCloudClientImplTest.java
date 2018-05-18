/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.cloud.client.internal.util;

import com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Andrea Di Giorgi
 */
@RunWith(MockitoJUnitRunner.class)
public class CommerceCloudClientImplTest {

	@Before
	public void setUp() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			CommerceCloudClientImpl.class, "_commerceOrderLocalService");

		field.set(_commerceCloudClientImpl, _commerceOrderLocalService);

		field = ReflectionUtil.getDeclaredField(
			CommerceCloudClientImpl.class, "_jsonFactory");

		field.set(_commerceCloudClientImpl, new JSONFactoryImpl());

		Mockito.when(
			_commerceCloudForecastOrder.getCreateDate()
		).thenReturn(
			RandomTestUtil.nextDate()
		);

		Mockito.when(
			_commerceOrderLocalService.getCommerceOrder(Matchers.anyLong())
		).thenReturn(
			_commerceOrder
		);
	}

	@Test
	public void testGetJSONObject() throws Exception {
		JSONObject jsonObject = _commerceCloudClientImpl.getJSONObject(
			_commerceCloudForecastOrder);

		String json = jsonObject.toJSONString();

		Assert.assertTrue(json.contains("\"companyId\":0"));
		Assert.assertTrue(json.contains("\"customerId\":0"));
		Assert.assertTrue(json.contains("\"orderId\":0"));
	}

	private final CommerceCloudClientImpl _commerceCloudClientImpl =
		new CommerceCloudClientImpl();

	@Mock
	private CommerceCloudForecastOrder _commerceCloudForecastOrder;

	@Mock
	private CommerceOrder _commerceOrder;

	@Mock
	private CommerceOrderLocalService _commerceOrderLocalService;

}