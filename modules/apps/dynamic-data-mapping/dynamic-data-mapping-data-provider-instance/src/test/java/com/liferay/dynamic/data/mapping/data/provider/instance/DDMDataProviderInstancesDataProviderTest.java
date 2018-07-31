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

package com.liferay.dynamic.data.mapping.data.provider.instance;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalService;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@PrepareForTest(LocaleThreadLocal.class)
@RunWith(PowerMockRunner.class)
public class DDMDataProviderInstancesDataProviderTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		_setUpLocaleThreadLocal();

		_ddmDataProviderInstancesDataProvider =
			new DDMDataProviderInstancesDataProvider();

		_ddmDataProviderInstancesDataProvider.
			ddmDataProviderInstanceLocalService =
				_ddmDataProviderInstanceLocalService;
		_ddmDataProviderInstancesDataProvider.portal = _portal;
	}

	@Test
	public void testGetData() throws Exception {
		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest = builder.withGroupId(
			1
		).build();

		long[] groups = {1, 2};

		when(
			_portal.getCurrentAndAncestorSiteGroupIds(1)
		).thenReturn(
			groups
		);

		DDMDataProviderInstance ddmDataProviderInstance1 =
			_createDDMDataProviderInstanceMock(1, "Data Provider Instance 1");

		DDMDataProviderInstance ddmDataProviderInstance2 =
			_createDDMDataProviderInstanceMock(2, "Data Provider Instance 2");

		when(
			_ddmDataProviderInstanceLocalService.getDataProviderInstances(
				groups)
		).thenReturn(
			Arrays.asList(ddmDataProviderInstance1, ddmDataProviderInstance2)
		);

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmDataProviderInstancesDataProvider.getData(
				ddmDataProviderRequest);

		Assert.assertTrue(ddmDataProviderResponse.hasOutput("Default-Output"));

		Optional<List<KeyValuePair>> optional =
			ddmDataProviderResponse.getOutput("Default-Output", List.class);

		Assert.assertTrue(optional.isPresent());

		List<KeyValuePair> keyValuePairs = new ArrayList() {
			{
				add(new KeyValuePair("1", "Data Provider Instance 1"));
				add(new KeyValuePair("2", "Data Provider Instance 2"));
			}
		};

		Assert.assertEquals(keyValuePairs, optional.get());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetSettings() {
		_ddmDataProviderInstancesDataProvider.getSettings();
	}

	@Test
	public void testThrowException() throws Exception {
		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest = builder.withGroupId(
			1
		).build();

		when(
			_portal.getCurrentAndAncestorSiteGroupIds(1)
		).thenThrow(
			Exception.class
		);

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmDataProviderInstancesDataProvider.getData(
				ddmDataProviderRequest);

		Assert.assertTrue(ddmDataProviderResponse.hasOutput("Default-Output"));

		Optional<List<KeyValuePair>> optional =
			ddmDataProviderResponse.getOutput("Default-Output", List.class);

		Assert.assertTrue(optional.isPresent());

		List<KeyValuePair> keyValuePairs = optional.get();

		Assert.assertEquals(keyValuePairs.toString(), 0, keyValuePairs.size());
	}

	private DDMDataProviderInstance _createDDMDataProviderInstanceMock(
		long dataProviderInstanceId, String name) {

		DDMDataProviderInstance ddmDataProviderInstance = mock(
			DDMDataProviderInstance.class);

		when(
			ddmDataProviderInstance.getDataProviderInstanceId()
		).thenReturn(
			dataProviderInstanceId
		);

		when(
			ddmDataProviderInstance.getName(_locale)
		).thenReturn(
			name
		);

		return ddmDataProviderInstance;
	}

	private void _setUpLocaleThreadLocal() {
		mockStatic(LocaleThreadLocal.class);

		when(
			LocaleThreadLocal.getThemeDisplayLocale()
		).thenReturn(
			_locale
		);
	}

	private static final Locale _locale = new Locale("pt", "BR");

	@Mock
	private DDMDataProviderInstanceLocalService
		_ddmDataProviderInstanceLocalService;

	private DDMDataProviderInstancesDataProvider
		_ddmDataProviderInstancesDataProvider;

	@Mock
	private Portal _portal;

}