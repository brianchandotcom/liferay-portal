/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field.type;

import com.liferay.info.field.InfoField;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alejandro Tardín
 */
public class TextInfoFieldTypeTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testMultilineAttributeCanBeSetToFalse() {
		InfoField<TextInfoFieldType> infoField = InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).namespace(
			StringPool.BLANK
		).name(
			"test-field"
		).attribute(
			TextInfoFieldType.MULTILINE, false
		).build();

		Assert.assertFalse(infoField.getAttribute(TextInfoFieldType.MULTILINE));
	}

	@Test
	public void testMultilineAttributeCanBeSetToTrue() {
		InfoField<TextInfoFieldType> infoField = InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).namespace(
			StringPool.BLANK
		).name(
			"test-field"
		).attribute(
			TextInfoFieldType.MULTILINE, true
		).build();

		Assert.assertTrue(infoField.getAttribute(TextInfoFieldType.MULTILINE));
	}

	@Test
	public void testMultilineAttributeIsEmptyByDefault() {
		InfoField<TextInfoFieldType> infoField = InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).namespace(
			StringPool.BLANK
		).name(
			"test-field"
		).build();

		Assert.assertNull(infoField.getAttribute(TextInfoFieldType.MULTILINE));
	}

}