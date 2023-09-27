/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.dto.v1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

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

}