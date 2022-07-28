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

package com.liferay.object.rest.internal.extension.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.vulcan.extension.ExtensionProvider;
import com.liferay.portal.vulcan.extension.PropertyDefinition;

import java.io.Serializable;

import java.math.BigDecimal;

import java.sql.Timestamp;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

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
public class ObjectEntryExtensionProviderTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-135404", "true"
			).build());

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
				TestPropsValues.getCompanyId(), User.class.getName());

		_addCustomObjectField(
			objectDefinition.getObjectDefinitionId(), "Boolean", "Boolean",
			"booleanField", false);
		_addCustomObjectField(
			objectDefinition.getObjectDefinitionId(), "Date", "Date",
			"dateField", true);
		_addCustomObjectField(
			objectDefinition.getObjectDefinitionId(), "Decimal", "Double",
			"decimalField", false);
		_addCustomObjectField(
			objectDefinition.getObjectDefinitionId(), "PrecisionDecimal",
			"BigDecimal", "precisionDecimalField", true);

		_user = UserTestUtil.addUser();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-135404", "false"
			).build());
	}

	@Test
	public void testGetExtendedPropertyDefinitions() throws Exception {
		Map<String, PropertyDefinition> extendedPropertyDefinitions =
			_extensionProvider.getExtendedPropertyDefinitions(
				TestPropsValues.getCompanyId(), UserAccount.class.getName());

		_assertPropertyDefinition(
			"booleanField", PropertyDefinition.PropertyType.BOOLEAN, false,
			extendedPropertyDefinitions.get("booleanField"));
		_assertPropertyDefinition(
			"dateField", PropertyDefinition.PropertyType.DATE_TIME, true,
			extendedPropertyDefinitions.get("dateField"));
		_assertPropertyDefinition(
			"decimalField", PropertyDefinition.PropertyType.DOUBLE, false,
			extendedPropertyDefinitions.get("decimalField"));
		_assertPropertyDefinition(
			"precisionDecimalField",
			PropertyDefinition.PropertyType.BIG_DECIMAL, true,
			extendedPropertyDefinitions.get("precisionDecimalField"));
	}

	@Test
	public void testIsApplicableExtension() throws Exception {
		Assert.assertFalse(
			_extensionProvider.isApplicableExtension(
				TestPropsValues.getCompanyId(), null));

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", RandomTestUtil.randomString(),
						StringUtil.randomId())));

		objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId());

		Assert.assertFalse(
			_extensionProvider.isApplicableExtension(
				TestPropsValues.getCompanyId(),
				ObjectEntry.class.getName() + "#" +
					objectDefinition.getName()));

		_objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition.getObjectDefinitionId());

		Assert.assertTrue(
			_extensionProvider.isApplicableExtension(
				TestPropsValues.getCompanyId(), UserAccount.class.getName()));
	}

	@Test
	public void testSetAndGetExtendedProperties() throws Exception {
		UserAccount userAccount = new UserAccount() {
			{
				setId(_user.getUserId());
			}
		};

		_testSetAndGetExtendedProperties(
			userAccount,
			HashMapBuilder.<String, Serializable>put(
				"booleanField", true
			).put(
				"dateField", "2010-12-25"
			).put(
				"decimalField", 1.2
			).put(
				"precisionDecimalField", 100.5
			).build());
		_testSetAndGetExtendedProperties(
			userAccount,
			HashMapBuilder.<String, Serializable>put(
				"booleanField", false
			).put(
				"dateField", "2020-07-30"
			).put(
				"decimalField", 10.8
			).put(
				"precisionDecimalField", 20.55
			).build());
	}

	private static void _addCustomObjectField(
			long objectDefinitionId, String businessType, String dbType,
			String name, boolean required)
		throws Exception {

		_objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0, objectDefinitionId, businessType,
			dbType, null, RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomBoolean(), null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			name, required, false, Collections.emptyList());
	}

	private void _assertPropertyDefinition(
		String expectedPropertyName,
		PropertyDefinition.PropertyType expectedPropertyType,
		boolean expectedRequired, PropertyDefinition propertyDefinition) {

		Assert.assertEquals(
			expectedPropertyName, propertyDefinition.getPropertyName());
		Assert.assertEquals(
			expectedPropertyType, propertyDefinition.getPropertyType());
		Assert.assertEquals(expectedRequired, propertyDefinition.isRequired());
	}

	private void _testSetAndGetExtendedProperties(
			UserAccount userAccount, Map<String, Serializable> values)
		throws Exception {

		_extensionProvider.setExtendedProperties(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			UserAccount.class.getName(), userAccount, values);

		Map<String, Serializable> extendedProperties =
			_extensionProvider.getExtendedProperties(
				TestPropsValues.getCompanyId(), UserAccount.class.getName(),
				userAccount);

		Assert.assertEquals(
			values.get("booleanField"), extendedProperties.get("booleanField"));

		Date date = DateUtil.parseDate(
			"yyyy-MM-dd", String.valueOf(values.get("dateField")),
			LocaleUtil.getSiteDefault());

		Assert.assertEquals(
			new Timestamp(date.getTime()), extendedProperties.get("dateField"));

		Assert.assertEquals(
			values.get("decimalField"), extendedProperties.get("decimalField"));

		BigDecimal bigDecimal = new BigDecimal(
			String.valueOf(values.get("precisionDecimalField")));

		Assert.assertEquals(
			bigDecimal.setScale(16),
			extendedProperties.get("precisionDecimalField"));
	}

	@Inject
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private static ObjectFieldLocalService _objectFieldLocalService;

	private static User _user;

	@Inject(filter = "component.name=*.ObjectEntryExtensionProvider")
	private ExtensionProvider _extensionProvider;

}