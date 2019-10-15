/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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