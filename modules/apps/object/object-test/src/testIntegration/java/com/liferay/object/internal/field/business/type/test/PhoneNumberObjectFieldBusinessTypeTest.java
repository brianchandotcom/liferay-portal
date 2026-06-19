/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.field.business.type.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.exception.ObjectFieldSettingNameException;
import com.liferay.object.exception.ObjectFieldSettingValueException;
import com.liferay.object.field.builder.PhoneNumberObjectFieldBuilder;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.object.field.setting.builder.ObjectFieldSettingBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marco Leo
 */
@RunWith(Arquillian.class)
public class PhoneNumberObjectFieldBusinessTypeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition();

		_objectField = ObjectFieldUtil.addCustomObjectField(
			new PhoneNumberObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				_OBJECT_FIELD_NAME
			).objectDefinitionId(
				_objectDefinition.getObjectDefinitionId()
			).objectFieldSettings(
				Collections.singletonList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_COUNTRY_SOURCE
					).value(
						ObjectFieldSettingConstants.VALUE_DEFINED_BY_USER
					).build())
			).userId(
				TestPropsValues.getUserId()
			).build());

		_objectFieldBusinessType =
			_objectFieldBusinessTypeRegistry.getObjectFieldBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_PHONE_NUMBER);
	}

	@Test
	public void testProcessValue() throws Exception {

		// Defined by user country source

		AssertUtils.assertFailure(
			ObjectEntryValuesException.InvalidPhoneNumber.class,
			StringBundler.concat(
				"The phone number \"abc\" has an invalid format for object ",
				"field \"", _OBJECT_FIELD_NAME, "\""),
			() -> _objectFieldBusinessType.processValue(_objectField, "abc"));

		Assert.assertEquals(
			"", _objectFieldBusinessType.processValue(_objectField, ""));
		Assert.assertEquals(
			"+15551234567",
			_objectFieldBusinessType.processValue(
				_objectField, "+1 (555) 123-4567"));

		// Fixed country source

		ObjectField objectField = ObjectFieldUtil.addCustomObjectField(
			new PhoneNumberObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				"a" + RandomTestUtil.randomString()
			).objectDefinitionId(
				_objectDefinition.getObjectDefinitionId()
			).objectFieldSettings(
				Arrays.asList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_COUNTRY
					).value(
						"US"
					).build(),
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_COUNTRY_SOURCE
					).value(
						ObjectFieldSettingConstants.VALUE_FIXED
					).build())
			).userId(
				TestPropsValues.getUserId()
			).build());

		AssertUtils.assertFailure(
			ObjectEntryValuesException.InvalidPhoneNumber.class,
			StringBundler.concat(
				"The phone number \"+44 555 1234567\" has an invalid format ",
				"for object field \"", objectField.getName(), "\""),
			() -> _objectFieldBusinessType.processValue(
				objectField, " +44 555 1234567"));
		AssertUtils.assertFailure(
			ObjectEntryValuesException.InvalidPhoneNumber.class,
			StringBundler.concat(
				"The phone number \"+445551234567\" has an invalid format for ",
				"object field \"", objectField.getName(), "\""),
			() -> _objectFieldBusinessType.processValue(
				objectField, "+445551234567"));

		Assert.assertEquals(
			"", _objectFieldBusinessType.processValue(objectField, ""));
		Assert.assertEquals(
			"+15551234567",
			_objectFieldBusinessType.processValue(objectField, " 555 1234567"));
		Assert.assertEquals(
			"+15551234567",
			_objectFieldBusinessType.processValue(
				objectField, "+1 (555) 123-4567"));
		Assert.assertEquals(
			"+15551234567",
			_objectFieldBusinessType.processValue(objectField, "5551234567"));
	}

	@Test
	public void testValidateObjectFieldSettings() throws Exception {

		// Country

		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			StringBundler.concat(
				"The value ZZ of setting \"country\" is invalid for object ",
				"field \"", _OBJECT_FIELD_NAME, "\""),
			() -> _objectFieldBusinessType.validateObjectFieldSettings(
				_objectField,
				Arrays.asList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_COUNTRY
					).value(
						"ZZ"
					).build(),
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_COUNTRY_SOURCE
					).value(
						ObjectFieldSettingConstants.VALUE_FIXED
					).build())));
		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.MissingRequiredValues.class,
			StringBundler.concat(
				"The settings \"country\" are required for object field \"",
				_OBJECT_FIELD_NAME, "\""),
			() -> _objectFieldBusinessType.validateObjectFieldSettings(
				_objectField,
				Collections.singletonList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_COUNTRY_SOURCE
					).value(
						ObjectFieldSettingConstants.VALUE_FIXED
					).build())));
		AssertUtils.assertFailure(
			ObjectFieldSettingNameException.NotAllowedNames.class,
			"The settings country are not allowed for object field " +
				_OBJECT_FIELD_NAME,
			() -> _objectFieldBusinessType.validateObjectFieldSettings(
				_objectField,
				Arrays.asList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_COUNTRY
					).value(
						"US"
					).build(),
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_COUNTRY_SOURCE
					).value(
						ObjectFieldSettingConstants.VALUE_DEFINED_BY_USER
					).build())));

		_objectFieldBusinessType.validateObjectFieldSettings(
			_objectField,
			Arrays.asList(
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_COUNTRY
				).value(
					"US"
				).build(),
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_COUNTRY_SOURCE
				).value(
					ObjectFieldSettingConstants.VALUE_FIXED
				).build()));
		_objectFieldBusinessType.validateObjectFieldSettings(
			_objectField,
			Arrays.asList(
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_COUNTRY
				).value(
					"us"
				).build(),
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_COUNTRY_SOURCE
				).value(
					ObjectFieldSettingConstants.VALUE_FIXED
				).build()));

		// Country source

		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			StringBundler.concat(
				"The value defineByUser of setting \"countrySource\" is ",
				"invalid for object field \"", _OBJECT_FIELD_NAME, "\""),
			() -> _objectFieldBusinessType.validateObjectFieldSettings(
				_objectField,
				Collections.singletonList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_COUNTRY_SOURCE
					).value(
						"defineByUser"
					).build())));
		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.MissingRequiredValues.class,
			StringBundler.concat(
				"The settings \"countrySource\" are required for object field ",
				"\"", _OBJECT_FIELD_NAME, "\""),
			() -> _objectFieldBusinessType.validateObjectFieldSettings(
				_objectField, Collections.emptyList()));

		_objectFieldBusinessType.validateObjectFieldSettings(
			_objectField,
			Collections.singletonList(
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_COUNTRY_SOURCE
				).value(
					ObjectFieldSettingConstants.VALUE_DEFINED_BY_USER
				).build()));

		// Default value

		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			StringBundler.concat(
				"The value +445551234567 of setting \"defaultValue\" is ",
				"invalid for object field \"", _OBJECT_FIELD_NAME, "\""),
			() ->
				_objectFieldBusinessType.
					validateObjectFieldSettingsDefaultValue(
						_objectField,
						HashMapBuilder.put(
							ObjectFieldSettingConstants.NAME_COUNTRY, "US"
						).put(
							ObjectFieldSettingConstants.NAME_COUNTRY_SOURCE,
							ObjectFieldSettingConstants.VALUE_FIXED
						).put(
							ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
							"+445551234567"
						).put(
							ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE,
							ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE
						).build()));
		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			StringBundler.concat(
				"The value 5551234567 of setting \"defaultValue\" is invalid ",
				"for object field \"", _OBJECT_FIELD_NAME, "\""),
			() ->
				_objectFieldBusinessType.
					validateObjectFieldSettingsDefaultValue(
						_objectField,
						HashMapBuilder.put(
							ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
							"5551234567"
						).put(
							ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE,
							ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE
						).build()));

		_objectFieldBusinessType.validateObjectFieldSettingsDefaultValue(
			_objectField,
			HashMapBuilder.put(
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
				"+1 (555) 123-4567"
			).put(
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE,
				ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE
			).build());
		_objectFieldBusinessType.validateObjectFieldSettingsDefaultValue(
			_objectField,
			HashMapBuilder.put(
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE, "+15551234567"
			).put(
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE,
				ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE
			).build());
	}

	private static final String _OBJECT_FIELD_NAME =
		"a" + RandomTestUtil.randomString();

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	private ObjectField _objectField;
	private ObjectFieldBusinessType _objectFieldBusinessType;

	@Inject
	private ObjectFieldBusinessTypeRegistry _objectFieldBusinessTypeRegistry;

}