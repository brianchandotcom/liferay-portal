/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.dto.v1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class FieldTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testEscape() throws Exception {
		_testEscape(
			"es\\\\ca\\\"pe\\bES\\fCA\\nPE\\rRO\\tOM",
			"es\\ca\"pe\bES\fCA\nPE\rRO\tOM");
		_testEscape(
			"es\\\\\\\\ca\\\\\\\"pe\\\\\\bES\\\\\\fCA\\\\\\nPE" +
				"\\\\\\rRO\\\\\\tOM",
			"es\\\\ca\\\"pe\\\bES\\\fCA\\\nPE\\\rRO\\\tOM");
	}

	@Test
	public void testToStringDefaultValueCollection() throws Exception {
		_testToStringDefaultValue(_createMaps());
	}

	@Test
	public void testToStringDefaultValueObjectArray() throws Exception {
		List<Map<String, String>> maps = _createMaps();

		_testToStringDefaultValue(maps.toArray());
	}

	private List<Map<String, String>> _createMaps() {
		return Arrays.asList(
			_createMap("label1", "value1"), _createMap("label2", "value2"));
	}

	private Map<String, String> _createMap(String label, String value) {
		return HashMapBuilder.put(
			"label", label
		).put(
			"value", value
		).build();
	}

	private void _testEscape(String escaped, String unescaped) {
		Field field1 = new Field();

		field1.setDefaultValue(unescaped);
		field1.setName(unescaped);

		Assert.assertEquals(
			StringBundler.concat(
				"{\"defaultValue\": \"", escaped, "\", \"name\": \"", escaped,
				"\"}"),
			field1.toString());

		Field field2 = Field.unsafeToDTO(field1.toString());

		Assert.assertEquals(unescaped, field2.getDefaultValue());
		Assert.assertEquals(unescaped, field2.getName());

		Assert.assertEquals(field1.toString(), field2.toString());
	}

	private void _testToStringDefaultValue(Object defaultValue) {
		Field field1 = new Field();

		field1.setDefaultValue(defaultValue);

		Field field2 = Field.unsafeToDTO(field1.toString());

		Object[] expectedDefaultValues = null;

		if (defaultValue instanceof Collection) {
			Collection<?> collection = (Collection<?>)defaultValue;

			expectedDefaultValues = collection.toArray();
		}
		else {
			expectedDefaultValues = (Object[])defaultValue;
		}

		Object[] defaultValues = (Object[])field2.getDefaultValue();

		Assert.assertArrayEquals(expectedDefaultValues, defaultValues);
	}

}