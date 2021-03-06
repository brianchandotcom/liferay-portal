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

package com.liferay.info.field.type;

import com.liferay.info.field.InfoField;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Optional;

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
		).name(
			"test-field"
		).attribute(
			TextInfoFieldType.MULTILINE, false
		).build();

		Optional<Boolean> attributeOptional = infoField.getAttributeOptional(
			TextInfoFieldType.MULTILINE);

		Assert.assertFalse(attributeOptional.get());
	}

	@Test
	public void testMultilineAttributeCanBeSetToTrue() {
		InfoField<TextInfoFieldType> infoField = InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"test-field"
		).attribute(
			TextInfoFieldType.MULTILINE, true
		).build();

		Optional<Boolean> attributeOptional = infoField.getAttributeOptional(
			TextInfoFieldType.MULTILINE);

		Assert.assertTrue(attributeOptional.get());
	}

	@Test
	public void testMultilineAttributeIsEmptyByDefault() {
		InfoField<TextInfoFieldType> infoField = InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"test-field"
		).build();

		Optional<Boolean> attributeOptional = infoField.getAttributeOptional(
			TextInfoFieldType.MULTILINE);

		Assert.assertFalse(attributeOptional.isPresent());
	}

}