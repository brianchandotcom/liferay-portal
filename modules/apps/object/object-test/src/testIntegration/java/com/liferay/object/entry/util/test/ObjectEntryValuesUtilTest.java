/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.entry.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.entry.util.ObjectEntryValuesUtil;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectField;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Yuri Monteiro
 */
@RunWith(Arquillian.class)
public class ObjectEntryValuesUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetTitleFieldValue() {
		String objectFieldName = RandomTestUtil.randomString();

		ObjectField objectField = _createObjectField(false, objectFieldName);

		Assert.assertNull(
			ObjectEntryValuesUtil.getTitleFieldValue(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				Collections.singletonMap(
					objectFieldName, RandomTestUtil.randomString()),
				objectField, null, null));

		String value = RandomTestUtil.randomString();

		Assert.assertEquals(
			value,
			ObjectEntryValuesUtil.getTitleFieldValue(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				Collections.singletonMap(
					objectFieldName, RandomTestUtil.randomString()),
				objectField, null,
				Collections.singletonMap(objectFieldName, value)));
		Assert.assertEquals(
			value,
			ObjectEntryValuesUtil.getTitleFieldValue(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				HashMapBuilder.<String, Object>put(
					objectField.getDBColumnName(), value
				).put(
					objectFieldName, RandomTestUtil.randomString()
				).build(),
				objectField, null,
				HashMapBuilder.<String, Object>put(
					RandomTestUtil.randomString(), RandomTestUtil.randomString()
				).build()));
	}

	@Test
	public void testGetValue() {
		Assert.assertNull(
			ObjectEntryValuesUtil.getValue(null, null, Collections.emptyMap()));

		String value = RandomTestUtil.randomString();

		Assert.assertEquals(
			value,
			ObjectEntryValuesUtil.getValue(
				RandomTestUtil.randomString(),
				_createObjectField(false, "creator"),
				HashMapBuilder.<String, Object>put(
					"userName", value
				).build()));
		Assert.assertEquals(
			value,
			ObjectEntryValuesUtil.getValue(
				RandomTestUtil.randomString(), _createObjectField(false, "id"),
				HashMapBuilder.<String, Object>put(
					"objectEntryId", value
				).build()));

		String objectFieldName = RandomTestUtil.randomString();

		Assert.assertEquals(
			value,
			ObjectEntryValuesUtil.getValue(
				RandomTestUtil.randomString(),
				_createObjectField(false, objectFieldName),
				HashMapBuilder.<String, Object>put(
					objectFieldName, value
				).build()));

		objectFieldName = RandomTestUtil.randomString();

		ObjectField objectField = _createObjectField(true, objectFieldName);

		Assert.assertEquals(
			value,
			ObjectEntryValuesUtil.getValue(
				"pt_BR", objectField,
				HashMapBuilder.<String, Object>put(
					objectField.getI18nObjectFieldName(),
					HashMapBuilder.<String, Object>put(
						"en_US", RandomTestUtil.randomString()
					).put(
						"pt_BR", value
					).build()
				).put(
					objectFieldName, RandomTestUtil.randomString()
				).build()));
	}

	@Test
	public void testGetValueString() {
		String fieldName = RandomTestUtil.randomString();
		String value = RandomTestUtil.randomString();

		Assert.assertEquals(
			value,
			ObjectEntryValuesUtil.getValueString(
				_createObjectField(false, fieldName),
				Collections.singletonMap(fieldName, value)));
	}

	private ObjectField _createObjectField(boolean localized, String name) {
		return new TextObjectFieldBuilder(
		).labelMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
		).localized(
			localized
		).name(
			name
		).build();
	}

}