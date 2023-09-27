/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider.instance.internal;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Leonardo Barros
 */
public class DDMDataProviderInstancesDataProviderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
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

		Mockito.when(
			_portal.getCurrentAndAncestorSiteGroupIds(1)
		).thenReturn(
			groups
		);

		DDMDataProviderInstance ddmDataProviderInstance1 =
			_createDDMDataProviderInstanceMock(1, "Data Provider Instance 1");

		DDMDataProviderInstance ddmDataProviderInstance2 =
			_createDDMDataProviderInstanceMock(2, "Data Provider Instance 2");

		Mockito.when(
			_ddmDataProviderInstanceLocalService.getDataProviderInstances(
				groups)
		).thenReturn(
			Arrays.asList(ddmDataProviderInstance1, ddmDataProviderInstance2)
		);

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmDataProviderInstancesDataProvider.getData(
				ddmDataProviderRequest);

		Assert.assertTrue(ddmDataProviderResponse.hasOutput("Default-Output"));

		List<KeyValuePair> keyValuePairs = ddmDataProviderResponse.getOutput(
			"Default-Output", List.class);

		Assert.assertNotNull(keyValuePairs);

		List<KeyValuePair> expectedKeyValuePairs =
			new ArrayList<KeyValuePair>() {
				{
					add(new KeyValuePair("1", "Data Provider Instance 1"));
					add(new KeyValuePair("2", "Data Provider Instance 2"));
				}
			};

		Assert.assertEquals(expectedKeyValuePairs, keyValuePairs);
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

		Mockito.when(
			_portal.getCurrentAndAncestorSiteGroupIds(1)
		).thenThrow(
			PortalException.class
		);

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmDataProviderInstancesDataProvider.getData(
				ddmDataProviderRequest);

		Assert.assertTrue(ddmDataProviderResponse.hasOutput("Default-Output"));

		List<KeyValuePair> keyValuePairs = ddmDataProviderResponse.getOutput(
			"Default-Output", List.class);

		Assert.assertNotNull(keyValuePairs);

		Assert.assertEquals(keyValuePairs.toString(), 0, keyValuePairs.size());
	}

	private static void _setUpLocaleThreadLocal() {
		LocaleThreadLocal.setThemeDisplayLocale(_locale);
	}

	private DDMDataProviderInstance _createDDMDataProviderInstanceMock(
		long dataProviderInstanceId, String name) {

		DDMDataProviderInstance ddmDataProviderInstance = Mockito.mock(
			DDMDataProviderInstance.class);

		Mockito.when(
			ddmDataProviderInstance.getDataProviderInstanceId()
		).thenReturn(
			dataProviderInstanceId
		);

		Mockito.when(
			ddmDataProviderInstance.getName(_locale)
		).thenReturn(
			name
		);

		return ddmDataProviderInstance;
	}

	private static final DDMDataProviderInstanceLocalService
		_ddmDataProviderInstanceLocalService = Mockito.mock(
			DDMDataProviderInstanceLocalService.class);
	private static DDMDataProviderInstancesDataProvider
		_ddmDataProviderInstancesDataProvider;
	private static final Locale _locale = new Locale("pt", "BR");
	private static final Portal _portal = Mockito.mock(Portal.class);

}