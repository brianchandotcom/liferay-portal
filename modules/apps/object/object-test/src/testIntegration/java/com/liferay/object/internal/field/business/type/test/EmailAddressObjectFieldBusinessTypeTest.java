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
import com.liferay.object.field.builder.EmailAddressObjectFieldBuilder;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.object.field.setting.builder.ObjectFieldSettingBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Nathaly Gomes
 */
@FeatureFlag("LPD-70673")
@RunWith(Arquillian.class)
public class EmailAddressObjectFieldBusinessTypeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition();

		_objectField = ObjectFieldUtil.addCustomObjectField(
			new EmailAddressObjectFieldBuilder(
			).labelMap(
				RandomTestUtil.randomLocaleStringMap()
			).name(
				"a" + RandomTestUtil.randomString()
			).objectDefinitionId(
				_objectDefinition.getObjectDefinitionId()
			).userId(
				TestPropsValues.getUserId()
			).build());

		_objectFieldBusinessType =
			_objectFieldBusinessTypeRegistry.getObjectFieldBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_EMAIL_ADDRESS);
	}

	@Test
	public void testProcessValue() throws Exception {
		Assert.assertEquals(
			"", _objectFieldBusinessType.processValue(_objectField, ""));

		Assert.assertEquals(
			"user@example.com",
			_objectFieldBusinessType.processValue(
				_objectField, "User@Example.com"));
		Assert.assertEquals(
			"user@example.com",
			_objectFieldBusinessType.processValue(
				_objectField, "user@example.com"));

		AssertUtils.assertFailure(
			ObjectEntryValuesException.InvalidEmailAddress.class,
			StringBundler.concat(
				"The email address \"not-an-email\" is invalid for object ",
				"field \"", _objectField.getName(), "\""),
			() -> _objectFieldBusinessType.processValue(
				_objectField, "not-an-email"));
		AssertUtils.assertFailure(
			ObjectEntryValuesException.InvalidEmailAddress.class,
			StringBundler.concat(
				"The email address \"missing@\" is invalid for object field \"",
				_objectField.getName(), "\""),
			() -> _objectFieldBusinessType.processValue(
				_objectField, "missing@"));

		AssertUtils.assertFailure(
			ObjectEntryValuesException.ExceedsTextMaxLength.class,
			StringBundler.concat(
				"Object entry value exceeds the maximum length of 254 ",
				"characters for object field \"", _objectField.getName(), "\""),
			() -> _objectFieldBusinessType.processValue(
				_objectField,
				RandomTestUtil.randomString(245) + "@example.com"));

		ObjectField objectField = ObjectFieldUtil.addCustomObjectField(
			new EmailAddressObjectFieldBuilder(
			).labelMap(
				RandomTestUtil.randomLocaleStringMap()
			).name(
				"a" + RandomTestUtil.randomString()
			).objectDefinitionId(
				_objectDefinition.getObjectDefinitionId()
			).objectFieldSettings(
				Arrays.asList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_DOMAINS
					).value(
						"@gmail.com,@outlook.com"
					).build(),
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_ENABLED
					).value(
						StringPool.TRUE
					).build(),
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_BLOCKED_DOMAINS
					).value(
						"@blocked.com"
					).build())
			).userId(
				TestPropsValues.getUserId()
			).build());

		String errorMessage = StringBundler.concat(
			"The email address domain \"@blocked.com\" is blocked for object ",
			"field \"", objectField.getName(), "\"");

		AssertUtils.assertFailure(
			ObjectEntryValuesException.BlockedEmailAddressDomain.class,
			errorMessage,
			() -> _objectFieldBusinessType.processValue(
				objectField, "user@blocked.com"));
		AssertUtils.assertFailure(
			ObjectEntryValuesException.BlockedEmailAddressDomain.class,
			errorMessage,
			() -> _objectFieldBusinessType.processValue(
				objectField, "User@Blocked.com"));
	}

	@Test
	public void testValidateObjectFieldSettings() throws Exception {
		AssertUtils.assertFailure(
			ObjectFieldSettingNameException.NotAllowedNames.class,
			StringBundler.concat(
				"The settings ",
				ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_DOMAINS,
				" are not allowed for object field ", _objectField.getName()),
			() -> _objectFieldBusinessType.validateObjectFieldSettings(
				_objectField,
				Arrays.asList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_DOMAINS
					).value(
						"liferay.com"
					).build(),
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_ENABLED
					).value(
						StringPool.FALSE
					).build())));

		AssertUtils.assertFailure(
			ObjectFieldSettingNameException.NotAllowedNames.class,
			StringBundler.concat(
				"The settings ",
				ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_DOMAINS,
				" are not allowed for object field ", _objectField.getName()),
			() -> _objectFieldBusinessType.validateObjectFieldSettings(
				_objectField,
				Collections.singletonList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_DOMAINS
					).value(
						"@liferay.com"
					).build())));

		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			StringBundler.concat(
				"The value invalid.com of setting \"",
				ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_DOMAINS,
				"\" is invalid for object field \"", _objectField.getName(),
				"\""),
			() -> _objectFieldBusinessType.validateObjectFieldSettings(
				_objectField,
				Arrays.asList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_DOMAINS
					).value(
						"invalid.com"
					).build(),
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_ENABLED
					).value(
						StringPool.TRUE
					).build())));

		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			StringBundler.concat(
				"The value invalid of setting \"",
				ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_ENABLED,
				"\" is invalid for object field \"", _objectField.getName(),
				"\""),
			() -> _objectFieldBusinessType.validateObjectFieldSettings(
				_objectField,
				Collections.singletonList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_ENABLED
					).value(
						"invalid"
					).build())));

		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			StringBundler.concat(
				"The value invalid of setting \"",
				ObjectFieldSettingConstants.NAME_BLOCKED_DOMAINS,
				"\" is invalid for object field \"", _objectField.getName(),
				"\""),
			() -> _objectFieldBusinessType.validateObjectFieldSettings(
				_objectField,
				Collections.singletonList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_BLOCKED_DOMAINS
					).value(
						"invalid"
					).build())));

		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			StringBundler.concat(
				"The value invalid of setting \"",
				ObjectFieldSettingConstants.NAME_UNIQUE_VALUES,
				"\" is invalid for object field \"", _objectField.getName(),
				"\""),
			() -> _objectFieldBusinessType.validateObjectFieldSettings(
				_objectField,
				Collections.singletonList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_UNIQUE_VALUES
					).value(
						"invalid"
					).build())));

		_objectFieldBusinessType.validateObjectFieldSettings(
			_objectField,
			Arrays.asList(
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_DOMAINS
				).value(
					"@liferay.com,@gmail.com"
				).build(),
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_ENABLED
				).value(
					StringPool.TRUE
				).build()));
		_objectFieldBusinessType.validateObjectFieldSettings(
			_objectField,
			Collections.singletonList(
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_ENABLED
				).value(
					StringPool.TRUE
				).build()));
		_objectFieldBusinessType.validateObjectFieldSettings(
			_objectField,
			Collections.singletonList(
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_ENABLED
				).value(
					StringPool.FALSE
				).build()));
		_objectFieldBusinessType.validateObjectFieldSettings(
			_objectField,
			Collections.singletonList(
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_BLOCKED_DOMAINS
				).value(
					"@blocked.com,@restricted.org"
				).build()));
		_objectFieldBusinessType.validateObjectFieldSettings(
			_objectField, Collections.emptyList());
	}

	@Test
	public void testValidateObjectFieldSettingsDefaultValue() throws Exception {
		String defaultValue = RandomTestUtil.randomString(245) + "@example.com";

		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			StringBundler.concat(
				"The value ", defaultValue, " of setting \"",
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
				"\" is invalid for object field \"", _objectField.getName(),
				"\""),
			() ->
				_objectFieldBusinessType.
					validateObjectFieldSettingsDefaultValue(
						_objectField,
						HashMapBuilder.put(
							ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
							defaultValue
						).put(
							ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE,
							ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE
						).build()));

		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			StringBundler.concat(
				"The value not-an-email of setting \"",
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
				"\" is invalid for object field \"", _objectField.getName(),
				"\""),
			() ->
				_objectFieldBusinessType.
					validateObjectFieldSettingsDefaultValue(
						_objectField,
						HashMapBuilder.put(
							ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
							"not-an-email"
						).put(
							ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE,
							ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE
						).build()));

		_objectFieldBusinessType.validateObjectFieldSettingsDefaultValue(
			_objectField,
			HashMapBuilder.put(
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
				"user@example.com"
			).put(
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE,
				ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE
			).build());
		_objectFieldBusinessType.validateObjectFieldSettingsDefaultValue(
			_objectField,
			HashMapBuilder.put(
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
				"User@Example.com"
			).put(
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE,
				ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE
			).build());
	}

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	private ObjectField _objectField;
	private ObjectFieldBusinessType _objectFieldBusinessType;

	@Inject
	private ObjectFieldBusinessTypeRegistry _objectFieldBusinessTypeRegistry;

}