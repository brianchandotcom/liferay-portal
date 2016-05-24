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

package com.liferay.portal.language.util;

import com.liferay.portal.kernel.language.LanguageConstants;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class LanguageValidatorTest {

	@Test
	public void testLangDirValues() {
		Assert.assertTrue(
			LanguageValidator.isValid(LanguageConstants.KEY_DIR, "ltr"));
		Assert.assertTrue(
			LanguageValidator.isValid(LanguageConstants.KEY_DIR, "rtl"));
		Assert.assertFalse(
			LanguageValidator.isValid(LanguageConstants.KEY_DIR, "tlr"));
		Assert.assertFalse(
			LanguageValidator.isValid(LanguageConstants.KEY_DIR, "lrt"));
		Assert.assertFalse(
			LanguageValidator.isValid(
				LanguageConstants.KEY_DIR, "Any other value"));
	}

	@Test
	public void testLineBeginValues() {
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_LINE_BEGIN, "left"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_LINE_BEGIN, "right"));
		Assert.assertFalse(
			LanguageValidator.isValid(LanguageConstants.KEY_LINE_BEGIN, "up"));
		Assert.assertFalse(
			LanguageValidator.isValid(
				LanguageConstants.KEY_LINE_BEGIN, "down"));
		Assert.assertFalse(
			LanguageValidator.isValid(
				LanguageConstants.KEY_LINE_BEGIN, "any-other-value"));
		Assert.assertFalse(
			LanguageValidator.isValid(
				LanguageConstants.KEY_LINE_BEGIN, "Any other value"));
	}

	@Test
	public void testLineEndValues() {
		Assert.assertTrue(
			LanguageValidator.isValid(LanguageConstants.KEY_LINE_END, "left"));
		Assert.assertTrue(
			LanguageValidator.isValid(LanguageConstants.KEY_LINE_END, "right"));
		Assert.assertFalse(
			LanguageValidator.isValid(LanguageConstants.KEY_LINE_END, "up"));
		Assert.assertFalse(
			LanguageValidator.isValid(LanguageConstants.KEY_LINE_END, "down"));
		Assert.assertFalse(
			LanguageValidator.isValid(
				LanguageConstants.KEY_LINE_END, "any-other-value"));
		Assert.assertFalse(
			LanguageValidator.isValid(
				LanguageConstants.KEY_LINE_END, "Any other value"));
	}

	@Test
	public void testUserNameFieldNameValues() {
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"prefix,first-name,middle-name,last-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"prefix,first-name,last-name,middle-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"prefix,middle-name,first-name,last-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"prefix,middle-name,last-name,first-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"prefix,last-name,first-name,middle-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"prefix,last-name,middle-name,first-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"prefix,first-name,last-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"prefix,last-name,first-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"prefix,first-name,middle-name,last-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"prefix,first-name,last-name,middle-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"prefix,middle-name,first-name,last-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"prefix,middle-name,last-name,first-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"prefix,last-name,first-name,middle-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"prefix,last-name,middle-name,first-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"prefix,first-name,last-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"prefix,last-name,first-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"first-name,middle-name,last-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"first-name,last-name,middle-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"middle-name,first-name,last-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"middle-name,last-name,first-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"last-name,first-name,middle-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"last-name,middle-name,first-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"first-name,last-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"last-name,first-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"first-name,middle-name,last-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"first-name,last-name,middle-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"middle-name,first-name,last-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"middle-name,last-name,first-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"last-name,first-name,middle-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"last-name,middle-name,first-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"first-name,last-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"last-name,first-name"));

		Assert.assertFalse(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"first-name,prefix,middle-name,last-name,suffix"));
		Assert.assertFalse(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"prefix,first-name,middle-name,suffix,last-name"));
		Assert.assertFalse(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"prefix,middle-name,last-name,suffix"));
		Assert.assertFalse(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"prefix,first-name,middle-name,suffix"));
		Assert.assertFalse(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES, "first-name"));
		Assert.assertFalse(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES, "last-name"));
		Assert.assertFalse(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"Prefix,First Name,Middle Name,Last Name,Suffix"));
		Assert.assertFalse(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"Any other values"));
		Assert.assertFalse(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES,
				"any,other,values"));
		Assert.assertFalse(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES, ""));
	}

	@Test
	public void testUserNameRequireFieldNamesValues() {
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"prefix,first-name,middle-name,last-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"prefix,first-name,last-name,middle-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"prefix,middle-name,first-name,last-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"prefix,middle-name,last-name,first-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"prefix,last-name,first-name,middle-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"prefix,last-name,middle-name,first-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"prefix,first-name,last-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"prefix,last-name,first-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"prefix,first-name,middle-name,last-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"prefix,first-name,last-name,middle-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"prefix,middle-name,first-name,last-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"prefix,middle-name,last-name,first-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"prefix,last-name,first-name,middle-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"prefix,last-name,middle-name,first-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"prefix,first-name,last-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"prefix,last-name,first-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"first-name,middle-name,last-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"first-name,last-name,middle-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"middle-name,first-name,last-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"middle-name,last-name,first-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"last-name,first-name,middle-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"last-name,middle-name,first-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"first-name,last-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"last-name,first-name,suffix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"first-name,middle-name,last-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"first-name,last-name,middle-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"middle-name,first-name,last-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"middle-name,last-name,first-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"last-name,first-name,middle-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"last-name,middle-name,first-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"first-name,last-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"last-name,first-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"prefix"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"first-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"middle-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"last-name"));
		Assert.assertTrue(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"suffix"));

		Assert.assertFalse(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"Prefix,First Name,Middle Name,Last Name,Suffix"));
		Assert.assertFalse(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"Any other values"));
		Assert.assertFalse(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES,
				"any,other,values"));
		Assert.assertFalse(
			LanguageValidator.isValid(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES, ""));
	}

}