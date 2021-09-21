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

package com.liferay.dynamic.data.mapping.storage.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterDeleteRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterDeleteResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterTracker;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class DDMStorageAdapterTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_setUpDDMTestStorageAdapter();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_serviceRegistration.unregister();
	}

	@Test
	public void testGetDDMStorageAdapter() throws Exception {
		DDMStorageAdapter jsonDDMStorageAdapter =
			_ddmStorageAdapterTracker.getDDMStorageAdapter(_STORAGE_TYPE_JSON);

		Assert.assertNotNull(jsonDDMStorageAdapter);

		DDMStorageAdapter testDDMStorageAdapter =
			_ddmStorageAdapterTracker.getDDMStorageAdapter(_STORAGE_TYPE_TEST);

		Assert.assertNotNull(testDDMStorageAdapter);
	}

	@Test
	public void testGetDDMStorageAdapterTypes() throws Exception {
		List<String> ddmStorageAdapterTypes = new ArrayList<>(
			_ddmStorageAdapterTracker.getDDMStorageAdapterTypes());

		Assert.assertTrue(ddmStorageAdapterTypes.contains(_STORAGE_TYPE_JSON));
		Assert.assertTrue(ddmStorageAdapterTypes.contains(_STORAGE_TYPE_TEST));
	}

	@Test
	public void testInvokeDDMTestStorageAdapter() throws Exception {
		DDMStorageAdapter testDDMStorageAdapter =
			_ddmStorageAdapterTracker.getDDMStorageAdapter(_STORAGE_TYPE_TEST);

		long primaryKey = _saveDDMFormValues(
			_createDDMFormValues(), testDDMStorageAdapter);

		DDMFormValues ddmFormValues = _getDDMFormValues(
			testDDMStorageAdapter, primaryKey);

		Assert.assertTrue(_containsValue(ddmFormValues, "TextField Value 1"));
		Assert.assertTrue(_containsValue(ddmFormValues, "TextField Value 2"));

		_deleteDDMFormValues(testDDMStorageAdapter, primaryKey);

		Assert.assertNull(_getDDMFormValues(testDDMStorageAdapter, primaryKey));
	}

	private static void _setUpDDMTestStorageAdapter() {
		Bundle bundle = FrameworkUtil.getBundle(DDMStorageAdapterTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			DDMStorageAdapter.class, new DDMTestStorageAdapter(),
			MapUtil.singletonDictionary(
				"ddm.storage.adapter.type", _STORAGE_TYPE_TEST));
	}

	private boolean _containsValue(DDMFormValues ddmFormValues, String value) {
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			"TextField");

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			Value fieldValue = ddmFormFieldValue.getValue();

			if (Objects.equals(
					value,
					fieldValue.getString(fieldValue.getDefaultLocale()))) {

				return true;
			}
		}

		return false;
	}

	private DDMFormValues _createDDMFormValues() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"1", "TextField", new UnlocalizedValue("TextField Value 1")));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"2", "TextField", new UnlocalizedValue("TextField Value 2")));

		return ddmFormValues;
	}

	private void _deleteDDMFormValues(
			DDMStorageAdapter ddmStorageAdapter, long primaryKey)
		throws Exception {

		ddmStorageAdapter.delete(
			DDMStorageAdapterDeleteRequest.Builder.newBuilder(
				primaryKey
			).build());
	}

	private DDMFormValues _getDDMFormValues(
			DDMStorageAdapter ddmStorageAdapter, long primaryKey)
		throws Exception {

		DDMStorageAdapterGetResponse ddmStorageAdapterGetResponse =
			ddmStorageAdapter.get(
				DDMStorageAdapterGetRequest.Builder.newBuilder(
					primaryKey, null
				).build());

		return ddmStorageAdapterGetResponse.getDDMFormValues();
	}

	private long _saveDDMFormValues(
			DDMFormValues ddmFormValues, DDMStorageAdapter ddmStorageAdapter)
		throws Exception {

		DDMStorageAdapterSaveResponse ddmStorageAdapterSaveResponse =
			ddmStorageAdapter.save(
				DDMStorageAdapterSaveRequest.Builder.newBuilder(
					TestPropsValues.getUserId(), ddmFormValues
				).build());

		return ddmStorageAdapterSaveResponse.getPrimaryKey();
	}

	private static final String _STORAGE_TYPE_JSON =
		StorageType.DEFAULT.getValue();

	private static final String _STORAGE_TYPE_TEST = "test";

	@Inject
	private static DDMStorageAdapterTracker _ddmStorageAdapterTracker;

	private static ServiceRegistration<DDMStorageAdapter> _serviceRegistration;

	private static class DDMTestStorageAdapter implements DDMStorageAdapter {

		@Override
		public DDMStorageAdapterDeleteResponse delete(
				DDMStorageAdapterDeleteRequest ddmStorageAdapterDeleteRequest)
			throws StorageException {

			DDMStorageAdapterDeleteResponse.Builder
				ddmStorageAdapterDeleteResponseBuilder =
					DDMStorageAdapterDeleteResponse.Builder.newBuilder();

			if (Objects.equals(
					_DEFAULT_PRIMARY_KEY,
					ddmStorageAdapterDeleteRequest.getPrimaryKey())) {

				_ddmFormValues = null;
			}

			return ddmStorageAdapterDeleteResponseBuilder.build();
		}

		@Override
		public DDMStorageAdapterGetResponse get(
				DDMStorageAdapterGetRequest ddmStorageAdapterGetRequest)
			throws StorageException {

			if (Objects.equals(
					_DEFAULT_PRIMARY_KEY,
					ddmStorageAdapterGetRequest.getPrimaryKey())) {

				DDMStorageAdapterGetResponse.Builder
					ddmStorageAdapterGetResponseBuilder =
						DDMStorageAdapterGetResponse.Builder.newBuilder(
							_ddmFormValues);

				return ddmStorageAdapterGetResponseBuilder.build();
			}

			return null;
		}

		@Override
		public DDMStorageAdapterSaveResponse save(
				DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest)
			throws StorageException {

			DDMStorageAdapterSaveResponse.Builder
				ddmStorageAdapterSaveResponseBuilder =
					DDMStorageAdapterSaveResponse.Builder.newBuilder(
						_DEFAULT_PRIMARY_KEY);

			_ddmFormValues = ddmStorageAdapterSaveRequest.getDDMFormValues();

			return ddmStorageAdapterSaveResponseBuilder.build();
		}

		private static final long _DEFAULT_PRIMARY_KEY =
			RandomTestUtil.randomLong();

		private static DDMFormValues _ddmFormValues;

	}

}