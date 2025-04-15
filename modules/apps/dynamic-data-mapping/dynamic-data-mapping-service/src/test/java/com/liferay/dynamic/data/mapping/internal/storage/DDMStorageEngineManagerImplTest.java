/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.storage;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterRegistry;
import com.liferay.dynamic.data.mapping.storage.DDMStorageEngineManager;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Carolina Barbosa
 */
public class DDMStorageEngineManagerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		DDMStorageAdapter ddmStorageAdapter = Mockito.mock(
			DDMStorageAdapter.class);

		Mockito.when(
			ddmStorageAdapter.get(
				Mockito.any(DDMStorageAdapterGetRequest.class))
		).thenReturn(
			_ddmStorageAdapterGetResponse
		);

		DDMStorageAdapterRegistry ddmStorageAdapterRegistry = Mockito.mock(
			DDMStorageAdapterRegistry.class);

		Mockito.when(
			ddmStorageAdapterRegistry.getDDMStorageAdapter(
				StorageType.DEFAULT.toString())
		).thenReturn(
			ddmStorageAdapter
		);

		ReflectionTestUtil.setFieldValue(
			_ddmStorageEngineManager, "_ddmStorageAdapterRegistry",
			ddmStorageAdapterRegistry);
	}

	@Test
	public void testGetDDMFormValues() throws Exception {
		Assert.assertNull(
			_ddmStorageEngineManager.getDDMFormValues(
				RandomTestUtil.randomLong(), _ddmForm));

		Mockito.when(
			_ddmForm.getDefaultLocale()
		).thenReturn(
			LocaleUtil.BRAZIL
		);

		Mockito.when(
			_ddmStorageAdapterGetResponse.getDDMFormValues()
		).thenReturn(
			new DDMFormValues(_ddmForm)
		);

		DDMFormValues ddmFormValues = _ddmStorageEngineManager.getDDMFormValues(
			RandomTestUtil.randomLong(), _ddmForm);

		Assert.assertEquals(
			LocaleUtil.BRAZIL, ddmFormValues.getDefaultLocale());
	}

	private final DDMForm _ddmForm = Mockito.mock(DDMForm.class);
	private final DDMStorageAdapterGetResponse _ddmStorageAdapterGetResponse =
		Mockito.mock(DDMStorageAdapterGetResponse.class);
	private final DDMStorageEngineManager _ddmStorageEngineManager =
		new DDMStorageEngineManagerImpl();

}