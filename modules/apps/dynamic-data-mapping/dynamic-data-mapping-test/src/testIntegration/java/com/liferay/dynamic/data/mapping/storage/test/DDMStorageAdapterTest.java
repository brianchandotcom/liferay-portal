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
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

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
		_setUpDDMStorageAdapterTracker();
		_setUpDDMTestStorageAdapter();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_serviceRegistration.unregister();
	}

	@Test
	public void testGetDDMStorageAdapter() throws Exception {
		DDMStorageAdapter ddmJSONStorageAdapter =
			_ddmStorageAdapterTracker.getDDMStorageAdapter(_STORAGE_TYPE_JSON);

		Assert.assertNotNull(ddmJSONStorageAdapter);

		DDMStorageAdapter ddmTestStorageAdapter =
			_ddmStorageAdapterTracker.getDDMStorageAdapter(_STORAGE_TYPE_TEST);

		Assert.assertNotNull(ddmTestStorageAdapter);
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
		DDMStorageAdapter ddmTestStorageAdapter =
			_ddmStorageAdapterTracker.getDDMStorageAdapter(_STORAGE_TYPE_TEST);

		long primaryKey = _saveDDMFormValues(
			_createDDMFormValues(), ddmTestStorageAdapter);

		DDMFormValues ddmFormValues = _getDDMFormValues(
			ddmTestStorageAdapter, primaryKey);

		Assert.assertTrue(_containsValue(ddmFormValues, "TextField Value 1"));
		Assert.assertTrue(_containsValue(ddmFormValues, "TextField Value 2"));

		_deleteDDMFormValues(ddmTestStorageAdapter, primaryKey);

		ddmFormValues = _getDDMFormValues(ddmTestStorageAdapter, primaryKey);

		Assert.assertNull(ddmFormValues);
	}

	private static void _setUpDDMStorageAdapterTracker() {
		Registry registry = RegistryUtil.getRegistry();

		_ddmStorageAdapterTracker = registry.getService(
			registry.getServiceReference(DDMStorageAdapterTracker.class));
	}

	private static void _setUpDDMTestStorageAdapter() {
		Map<String, Object> properties = HashMapBuilder.<String, Object>put(
			"ddm.storage.adapter.type", _STORAGE_TYPE_TEST
		).build();

		Registry registry = RegistryUtil.getRegistry();

		_serviceRegistration = registry.registerService(
			DDMStorageAdapter.class, new DDMTestStorageAdapter(), properties);
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
			DDMStorageAdapter ddmTestStorageAdapter, long primaryKey)
		throws Exception {

		DDMStorageAdapterDeleteRequest.Builder
			ddmStorageAdapterDeleteRequestBuilder =
				DDMStorageAdapterDeleteRequest.Builder.newBuilder(primaryKey);

		ddmTestStorageAdapter.delete(
			ddmStorageAdapterDeleteRequestBuilder.build());
	}

	private DDMFormValues _getDDMFormValues(
			DDMStorageAdapter ddmTestStorageAdapter, long primaryKey)
		throws Exception {

		DDMStorageAdapterGetRequest.Builder ddmStorageAdapterGetRequestBuilder =
			DDMStorageAdapterGetRequest.Builder.newBuilder(primaryKey, null);

		DDMStorageAdapterGetResponse ddmStorageAdapterGetResponse =
			ddmTestStorageAdapter.get(
				ddmStorageAdapterGetRequestBuilder.build());

		return ddmStorageAdapterGetResponse.getDDMFormValues();
	}

	private long _saveDDMFormValues(
			DDMFormValues ddmFormValues,
			DDMStorageAdapter ddmTestStorageAdapter)
		throws Exception {

		DDMStorageAdapterSaveRequest.Builder
			ddmStorageAdapterSaveRequestBuilder =
				DDMStorageAdapterSaveRequest.Builder.newBuilder(
					TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
					ddmFormValues);

		DDMStorageAdapterSaveResponse ddmStorageAdapterSaveResponse =
			ddmTestStorageAdapter.save(
				ddmStorageAdapterSaveRequestBuilder.build());

		return ddmStorageAdapterSaveResponse.getPrimaryKey();
	}

	private static final String _STORAGE_TYPE_JSON = "json";

	private static final String _STORAGE_TYPE_TEST = "test";

	private static DDMStorageAdapterTracker _ddmStorageAdapterTracker;
	private static ServiceRegistration<DDMStorageAdapter> _serviceRegistration;

	private static class DDMTestStorageAdapter implements DDMStorageAdapter {

		@Override
		public DDMStorageAdapterDeleteResponse delete(
				DDMStorageAdapterDeleteRequest ddmStorageAdapterDeleteRequest)
			throws StorageException {

			DDMStorageAdapterDeleteResponse.Builder builder =
				DDMStorageAdapterDeleteResponse.Builder.newBuilder();

			if (Objects.equals(
					_DEFAULT_PRIMARY_KEY,
					ddmStorageAdapterDeleteRequest.getPrimaryKey())) {

				_ddmFormValues = null;
			}

			return builder.build();
		}

		@Override
		public DDMStorageAdapterGetResponse get(
				DDMStorageAdapterGetRequest ddmStorageAdapterGetRequest)
			throws StorageException {

			if (Objects.equals(
					_DEFAULT_PRIMARY_KEY,
					ddmStorageAdapterGetRequest.getPrimaryKey())) {

				DDMStorageAdapterGetResponse.Builder builder =
					DDMStorageAdapterGetResponse.Builder.newBuilder(
						_ddmFormValues);

				return builder.build();
			}

			return null;
		}

		@Override
		public DDMStorageAdapterSaveResponse save(
				DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest)
			throws StorageException {

			DDMStorageAdapterSaveResponse.Builder builder =
				DDMStorageAdapterSaveResponse.Builder.newBuilder(
					_DEFAULT_PRIMARY_KEY);

			_ddmFormValues = ddmStorageAdapterSaveRequest.getDDMFormValues();

			return builder.build();
		}

		private static final long _DEFAULT_PRIMARY_KEY =
			RandomTestUtil.randomLong();

		private static DDMFormValues _ddmFormValues;

	}

}