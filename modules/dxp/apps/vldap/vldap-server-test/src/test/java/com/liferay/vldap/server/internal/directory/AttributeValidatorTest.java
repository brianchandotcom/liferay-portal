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

package com.liferay.vldap.server.internal.directory;

import com.liferay.vldap.server.internal.directory.builder.AttributeValidator;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jonathan McCann
 */
public class AttributeValidatorTest {

	@Test
	public void testIsAlwaysValidAttribute() {
		_attributeValidator.addAlwaysValidAttribute("ou");

		Assert.assertTrue(_attributeValidator.isValidAttribute("ou", "*"));
		Assert.assertTrue(_attributeValidator.isValidAttribute("OU", "TEST1"));
	}

	@Test
	public void testIsValidAttribute() {
		_attributeValidator.addValidAttributeValues("cn", "test1", "test2");

		Assert.assertTrue(_attributeValidator.isValidAttribute("cn", "test1"));
		Assert.assertTrue(_attributeValidator.isValidAttribute("cn", "test2"));
		Assert.assertTrue(_attributeValidator.isValidAttribute("CN", "TEST1"));
		Assert.assertTrue(_attributeValidator.isValidAttribute("CN", "TEST2"));
		Assert.assertFalse(_attributeValidator.isValidAttribute("cn", "test3"));
		Assert.assertFalse(_attributeValidator.isValidAttribute("CN", "TEST3"));
	}

	@Test
	public void testIsValidAttributeWithNullAttributeValues() {
		Assert.assertFalse(_attributeValidator.isValidAttribute("ou", "*"));
		Assert.assertFalse(_attributeValidator.isValidAttribute("cn", "test1"));
	}

	private final AttributeValidator _attributeValidator =
		new AttributeValidator();

}