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
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
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

		_objectField1 = ObjectFieldUtil.addCustomObjectField(
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
		_objectField2 = ObjectFieldUtil.addCustomObjectField(
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
						StringUtil.merge(
							new String[] {
								_getRandomDomain(), _getRandomDomain()
							})
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
						_BLOCKED_DOMAIN
					).build())
			).userId(
				TestPropsValues.getUserId()
			).build());

		_objectFieldBusinessType =
			_objectFieldBusinessTypeRegistry.getObjectFieldBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_EMAIL_ADDRESS);
	}

	@Test
	public void testProcessValue() throws Exception {
		AssertUtils.assertFailure(
			ObjectEntryValuesException.BlockedEmailAddressDomain.class,
			StringBundler.concat(
				"The email address domain \"",
				StringUtil.toLowerCase(_BLOCKED_DOMAIN), "\" is blocked for ",
				"object field \"", _objectField2.getName(), "\""),
			() -> _objectFieldBusinessType.processValue(
				_objectField2,
				RandomTestUtil.randomString() + _BLOCKED_DOMAIN));
		AssertUtils.assertFailure(
			ObjectEntryValuesException.ExceedsTextMaxLength.class,
			StringBundler.concat(
				"Object entry value exceeds the maximum length of 254 ",
				"characters for object field \"", _objectField1.getName(),
				"\""),
			() -> _objectFieldBusinessType.processValue(
				_objectField1, _getRandomEmailAddress(245)));

		String invalidEmailAddress = RandomTestUtil.randomString();

		AssertUtils.assertFailure(
			ObjectEntryValuesException.InvalidEmailAddress.class,
			StringBundler.concat(
				"The email address \"", invalidEmailAddress,
				"\" is invalid for object field \"", _objectField1.getName(),
				"\""),
			() -> _objectFieldBusinessType.processValue(
				_objectField1, invalidEmailAddress));

		Assert.assertEquals(
			StringPool.BLANK,
			_objectFieldBusinessType.processValue(
				_objectField1, StringPool.BLANK));

		String emailAddress = _getRandomEmailAddress(10);

		Assert.assertEquals(
			StringUtil.toLowerCase(emailAddress),
			_objectFieldBusinessType.processValue(_objectField1, emailAddress));
	}

	@Test
	public void testValidateObjectFieldSettings() throws Exception {
		AssertUtils.assertFailure(
			ObjectFieldSettingNameException.NotAllowedNames.class,
			StringBundler.concat(
				"The settings ",
				ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_DOMAINS,
				" are not allowed for object field ", _objectField1.getName()),
			() -> _objectFieldBusinessType.validateObjectFieldSettings(
				_objectField1,
				Collections.singletonList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_DOMAINS
					).value(
						_getRandomDomain()
					).build())));

		String invalidValue = RandomTestUtil.randomString();

		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			StringBundler.concat(
				"The value ", invalidValue, " of setting \"",
				ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_DOMAINS,
				"\" is invalid for object field \"", _objectField1.getName(),
				"\""),
			() -> _objectFieldBusinessType.validateObjectFieldSettings(
				_objectField1,
				Arrays.asList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_DOMAINS
					).value(
						invalidValue
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
				"The value ", invalidValue, " of setting \"",
				ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_ENABLED,
				"\" is invalid for object field \"", _objectField1.getName(),
				"\""),
			() -> _objectFieldBusinessType.validateObjectFieldSettings(
				_objectField1,
				Collections.singletonList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_ENABLED
					).value(
						invalidValue
					).build())));
		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			StringBundler.concat(
				"The value ", invalidValue, " of setting \"",
				ObjectFieldSettingConstants.NAME_BLOCKED_DOMAINS,
				"\" is invalid for object field \"", _objectField1.getName(),
				"\""),
			() -> _objectFieldBusinessType.validateObjectFieldSettings(
				_objectField1,
				Collections.singletonList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_BLOCKED_DOMAINS
					).value(
						invalidValue
					).build())));
		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			StringBundler.concat(
				"The value ", invalidValue, " of setting \"",
				ObjectFieldSettingConstants.NAME_UNIQUE_VALUES,
				"\" is invalid for object field \"", _objectField1.getName(),
				"\""),
			() -> _objectFieldBusinessType.validateObjectFieldSettings(
				_objectField1,
				Collections.singletonList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_UNIQUE_VALUES
					).value(
						invalidValue
					).build())));

		_objectFieldBusinessType.validateObjectFieldSettings(
			_objectField1, Collections.emptyList());
		_objectFieldBusinessType.validateObjectFieldSettings(
			_objectField1,
			Collections.singletonList(
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_ENABLED
				).value(
					String.valueOf(RandomTestUtil.randomBoolean())
				).build()));
		_objectFieldBusinessType.validateObjectFieldSettings(
			_objectField1,
			Arrays.asList(
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_DOMAINS
				).value(
					StringUtil.merge(
						new String[] {_getRandomDomain(), _getRandomDomain()})
				).build(),
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_ENABLED
				).value(
					StringPool.TRUE
				).build()));
		_objectFieldBusinessType.validateObjectFieldSettings(
			_objectField1,
			Collections.singletonList(
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_BLOCKED_DOMAINS
				).value(
					StringUtil.merge(
						new String[] {_getRandomDomain(), _getRandomDomain()})
				).build()));
	}

	@Test
	public void testValidateObjectFieldSettingsDefaultValue() throws Exception {
		String invalidDefaultValue1 =
			RandomTestUtil.randomString() + _BLOCKED_DOMAIN;

		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			StringBundler.concat(
				"The value ", invalidDefaultValue1, " of setting \"",
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
				"\" is invalid for object field \"", _objectField1.getName(),
				"\""),
			() ->
				_objectFieldBusinessType.
					validateObjectFieldSettingsDefaultValue(
						_objectField1,
						HashMapBuilder.put(
							ObjectFieldSettingConstants.NAME_BLOCKED_DOMAINS,
							_BLOCKED_DOMAIN
						).put(
							ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
							invalidDefaultValue1
						).put(
							ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE,
							ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE
						).build()));

		String invalidDefaultValue2 = _getRandomEmailAddress(245);

		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			StringBundler.concat(
				"The value ", invalidDefaultValue2, " of setting \"",
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
				"\" is invalid for object field \"", _objectField1.getName(),
				"\""),
			() ->
				_objectFieldBusinessType.
					validateObjectFieldSettingsDefaultValue(
						_objectField1,
						HashMapBuilder.put(
							ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
							invalidDefaultValue2
						).put(
							ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE,
							ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE
						).build()));

		String invalidDefaultValue3 = RandomTestUtil.randomString();

		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			StringBundler.concat(
				"The value ", invalidDefaultValue3, " of setting \"",
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
				"\" is invalid for object field \"", _objectField1.getName(),
				"\""),
			() ->
				_objectFieldBusinessType.
					validateObjectFieldSettingsDefaultValue(
						_objectField1,
						HashMapBuilder.put(
							ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
							invalidDefaultValue3
						).put(
							ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE,
							ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE
						).build()));

		_objectFieldBusinessType.validateObjectFieldSettingsDefaultValue(
			_objectField1,
			HashMapBuilder.put(
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
				_getRandomEmailAddress(10)
			).put(
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE,
				ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE
			).build());
	}

	private String _getRandomDomain() {
		return CharPool.AT + RandomTestUtil.randomString() + ".com";
	}

	private String _getRandomEmailAddress(int length) {
		return RandomTestUtil.randomString(length) + _getRandomDomain();
	}

	private static final String _BLOCKED_DOMAIN =
		CharPool.AT + RandomTestUtil.randomString() + ".com";

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	private ObjectField _objectField1;
	private ObjectField _objectField2;
	private ObjectFieldBusinessType _objectFieldBusinessType;

	@Inject
	private ObjectFieldBusinessTypeRegistry _objectFieldBusinessTypeRegistry;

}