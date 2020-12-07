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

package com.liferay.dynamic.data.mapping.data.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderException;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.data.provider.configuration.DDMDataProviderConfiguration;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.ResourcePermissionTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcellus Tavares
 */
@RunWith(Arquillian.class)
public class DDMRESTDataProviderTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		ConfigurationTestUtil.saveConfiguration(
			DDMDataProviderConfiguration.class.getName(),
			new HashMapDictionary() {
				{
					put("accessLocalNetwork", true);
				}
			});
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ConfigurationTestUtil.saveConfiguration(
			DDMDataProviderConfiguration.class.getName(),
			new HashMapDictionary() {
				{
					put("accessLocalNetwork", false);
				}
			});
	}

	@Before
	public void setUp() throws Exception {
		setUpPermissionThreadLocal();
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testGetCountries() throws Exception {
		setPermissionCheckerUser(false);

		Class<?> ddmDataProviderSettings = _ddmDataProvider.getSettings();

		DDMForm ddmForm = DDMFormFactory.create(ddmDataProviderSettings);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"cacheable", Boolean.FALSE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"filterable", Boolean.FALSE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"filterParameterName", StringPool.BLANK));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"password", "test"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"url",
				"http://localhost:8080/api/jsonws/country/get-countries"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"username", "test@liferay.com"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"timeout", "1000"));

		DDMFormFieldValue outputParameters =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"outputParameters", null);

		ddmFormValues.addDDMFormFieldValue(outputParameters);

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterName", "output"));

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterPath", "nameCurrentValue;name"));

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterType", "[\"list\"]"));

		String outputParameterId = StringUtil.randomString();

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterId", outputParameterId));

		long ddmDataProviderInstanceId = saveDDMDataProviderInstance(
			ddmFormValues, false, false);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest =
			builder.withDDMDataProviderId(
				String.valueOf(ddmDataProviderInstanceId)
			).build();

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmDataProvider.getData(ddmDataProviderRequest);

		Assert.assertNotNull(ddmDataProviderResponse);

		Optional<List<KeyValuePair>> optionalKeyValuePairs =
			ddmDataProviderResponse.getOutputOptional(
				outputParameterId, List.class);

		Assert.assertTrue(optionalKeyValuePairs.isPresent());

		List<KeyValuePair> actualKeyValuePairs = optionalKeyValuePairs.get();

		List<KeyValuePair> expectedKeyValuePairs = createKeyValuePairs();

		Assert.assertTrue(
			actualKeyValuePairs.containsAll(expectedKeyValuePairs));

		_ddmDataProviderInstanceLocalService.deleteDataProviderInstance(
			ddmDataProviderInstanceId);
	}

	@Test(expected = DDMDataProviderException.class)
	public void testGetCountriesUsingGuestWithoutViewPermission()
		throws Exception {

		setPermissionCheckerUser(true);

		Class<?> ddmDataProviderSettings = _ddmDataProvider.getSettings();

		DDMForm ddmForm = DDMFormFactory.create(ddmDataProviderSettings);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"cacheable", Boolean.FALSE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"filterable", Boolean.FALSE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"filterParameterName", StringPool.BLANK));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"password", "test"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"url",
				"http://localhost:8080/api/jsonws/country/get-countries"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"username", "test@liferay.com"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"timeout", "1000"));

		DDMFormFieldValue outputParameters =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"outputParameters", null);

		ddmFormValues.addDDMFormFieldValue(outputParameters);

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterName", "output"));

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterPath", "nameCurrentValue;countryId"));

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterType", "[\"list\"]"));

		long ddmDataProviderInstanceId = saveDDMDataProviderInstance(
			ddmFormValues, true, false);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest =
			builder.withDDMDataProviderId(
				String.valueOf(ddmDataProviderInstanceId)
			).build();

		// It throws a PrincipalException$MustHavePermission

		_ddmDataProvider.getData(ddmDataProviderRequest);
	}

	@Test
	public void testGetCountryByName() throws Exception {
		setPermissionCheckerUser(false);

		Class<?> ddmDataProviderSettings = _ddmDataProvider.getSettings();

		DDMForm ddmForm = DDMFormFactory.create(ddmDataProviderSettings);

		String url =
			"http://localhost:8080/api/jsonws/country/get-country-by-name";

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"cacheable", Boolean.FALSE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"filterable", Boolean.TRUE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"filterParameterName", "name"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"password", "test"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"url", url));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"username", "test@liferay.com"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"timeout", "1000"));

		DDMFormFieldValue outputParameters =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"outputParameters", null);

		ddmFormValues.addDDMFormFieldValue(outputParameters);

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterName", "output"));

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterPath", "nameCurrentValue;name"));

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterType", "[\"list\"]"));

		String outputParameterId = StringUtil.randomString();

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterId", outputParameterId));

		long ddmDataProviderInstanceId = saveDDMDataProviderInstance(
			ddmFormValues, false, false);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest =
			builder.withDDMDataProviderId(
				String.valueOf(ddmDataProviderInstanceId)
			).withParameter(
				"filterParameterValue", "brazil"
			).build();

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmDataProvider.getData(ddmDataProviderRequest);

		Assert.assertNotNull(ddmDataProviderResponse);

		Optional<List<KeyValuePair>> optionalKeyValuePairs =
			ddmDataProviderResponse.getOutputOptional(
				outputParameterId, List.class);

		Assert.assertTrue(optionalKeyValuePairs.isPresent());

		List<KeyValuePair> expectedKeyValuePairs = optionalKeyValuePairs.get();

		int actualSize = expectedKeyValuePairs.size();

		Assert.assertEquals(1, actualSize);

		KeyValuePair actualKeyValuePair = expectedKeyValuePairs.get(0);

		Assert.assertEquals("brazil", actualKeyValuePair.getKey());
		Assert.assertEquals("Brazil", actualKeyValuePair.getValue());

		_ddmDataProviderInstanceLocalService.deleteDataProviderInstance(
			ddmDataProviderInstanceId);
	}

	@Test
	public void testGetCountryByNameUsingGuestWithViewPermission()
		throws Exception {

		setPermissionCheckerUser(true);

		Class<?> ddmDataProviderSettings = _ddmDataProvider.getSettings();

		DDMForm ddmForm = DDMFormFactory.create(ddmDataProviderSettings);

		String url =
			"http://localhost:8080/api/jsonws/country/get-country-by-name";

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"cacheable", Boolean.FALSE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"filterable", Boolean.TRUE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"filterParameterName", "name"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"password", "test"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"url", url));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"username", "test@liferay.com"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"timeout", "1000"));

		DDMFormFieldValue outputParameters =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"outputParameters", null);

		ddmFormValues.addDDMFormFieldValue(outputParameters);

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterName", "output"));

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterPath", "nameCurrentValue;name"));

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterType", "[\"list\"]"));

		String outputParameterId = StringUtil.randomString();

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterId", outputParameterId));

		long ddmDataProviderInstanceId = saveDDMDataProviderInstance(
			ddmFormValues, true, true);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest =
			builder.withDDMDataProviderId(
				String.valueOf(ddmDataProviderInstanceId)
			).withParameter(
				"filterParameterValue", "canada"
			).build();

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmDataProvider.getData(ddmDataProviderRequest);

		Assert.assertNotNull(ddmDataProviderResponse);

		Optional<List<KeyValuePair>> optionalKeyValuePairs =
			ddmDataProviderResponse.getOutputOptional(
				outputParameterId, List.class);

		Assert.assertTrue(optionalKeyValuePairs.isPresent());

		List<KeyValuePair> expectedKeyValuePairs = optionalKeyValuePairs.get();

		int actualSize = expectedKeyValuePairs.size();

		Assert.assertEquals(1, actualSize);

		KeyValuePair actualKeyValuePair = expectedKeyValuePairs.get(0);

		Assert.assertEquals("canada", actualKeyValuePair.getKey());
		Assert.assertEquals("Canada", actualKeyValuePair.getValue());

		_ddmDataProviderInstanceLocalService.deleteDataProviderInstance(
			ddmDataProviderInstanceId);
	}

	protected List<KeyValuePair> createKeyValuePairs() {
		List<KeyValuePair> keyValuePairs = new ArrayList<>();

		keyValuePairs.add(new KeyValuePair("france", "France"));
		keyValuePairs.add(new KeyValuePair("spain", "Spain"));
		keyValuePairs.add(new KeyValuePair("united-states", "United States"));
		keyValuePairs.add(new KeyValuePair("brazil", "Brazil"));

		return keyValuePairs;
	}

	protected long saveDDMDataProviderInstance(
			DDMFormValues ddmFormValues, boolean guest,
			boolean addGuestViewPermission)
		throws Exception {

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.US, "Test"
		).build();

		long userId = TestPropsValues.getUserId();

		if (guest) {
			userId = _userLocalService.getDefaultUserId(
				TestPropsValues.getCompanyId());
		}

		DDMDataProviderInstance ddmDataProviderInstance =
			_ddmDataProviderInstanceLocalService.addDataProviderInstance(
				userId, TestPropsValues.getGroupId(), nameMap, null,
				ddmFormValues, "rest", new ServiceContext());

		long dataProviderInstanceId =
			ddmDataProviderInstance.getDataProviderInstanceId();

		if (addGuestViewPermission) {
			Role role = _roleLocalService.getRole(
				TestPropsValues.getCompanyId(), RoleConstants.GUEST);

			ResourcePermissionTestUtil.addResourcePermission(
				1L,
				"com.liferay.dynamic.data.mapping.model." +
					"DDMDataProviderInstance",
				String.valueOf(dataProviderInstanceId), role.getRoleId(),
				ResourceConstants.SCOPE_INDIVIDUAL);
		}

		return dataProviderInstanceId;
	}

	protected void setPermissionCheckerUser(boolean guest) throws Exception {
		User user = TestPropsValues.getUser();

		if (guest) {
			user = _userLocalService.getDefaultUser(
				TestPropsValues.getCompanyId());
		}

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));
	}

	protected void setUpPermissionThreadLocal() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
	}

	@Inject(
		filter = "ddm.data.provider.type=rest", type = DDMDataProvider.class
	)
	private DDMDataProvider _ddmDataProvider;

	@Inject(type = DDMDataProviderInstanceLocalService.class)
	private DDMDataProviderInstanceLocalService
		_ddmDataProviderInstanceLocalService;

	private PermissionChecker _originalPermissionChecker;

	@Inject(type = RoleLocalService.class)
	private RoleLocalService _roleLocalService;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}