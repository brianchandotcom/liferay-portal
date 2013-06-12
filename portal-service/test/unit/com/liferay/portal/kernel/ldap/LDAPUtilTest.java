/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.ldap;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author James Lefeu
 */
public class LDAPUtilTest {

	@Test
	public void testIsValidFilterBalancedParentheses() {
		Assert.assertTrue(isValidFilter("(object=value)"));
		Assert.assertTrue(isValidFilter("((((object=value))))"));
		Assert.assertTrue(isValidFilter("((((object=value))(org=liferay)))"));
		Assert.assertTrue(
			isValidFilter(
				"(((inetorg=www)((object=value))(org=liferay)))(user=test)"));
		Assert.assertFalse(isValidFilter("(object=value))"));
		Assert.assertFalse(isValidFilter("(((object=value))"));
		Assert.assertFalse(isValidFilter("((((object=value)))(org=liferay)))"));
		Assert.assertFalse(
			isValidFilter(
				"(((inetorg=www)((object=value))(org=liferay)))(user=test))"));
	}

	@Test
	public void testIsValidFilterNoFilterType() {
		Assert.assertTrue(isValidFilter("(object=value)"));
		Assert.assertFalse(isValidFilter("(object)"));
		Assert.assertFalse(isValidFilter("(object)(value)"));
		Assert.assertFalse(isValidFilter("(!object)"));
	}

	@Test
	public void testIsValidFilterOpenAndCloseParentheses() {
		Assert.assertTrue(isValidFilter("(object=value)"));
		Assert.assertTrue(isValidFilter("  (object=value)"));
		Assert.assertTrue(isValidFilter("(object=value)  "));
		Assert.assertFalse(isValidFilter("object=value)"));
		Assert.assertFalse(isValidFilter("(object=value"));
		Assert.assertFalse(isValidFilter("object=value"));
		Assert.assertFalse(isValidFilter("("));
		Assert.assertFalse(isValidFilter(")"));
		Assert.assertFalse(isValidFilter(")("));
	}

	@Test
	public void testIsValidFilterSpecialChars() {
		Assert.assertTrue(isValidFilter(""));
		Assert.assertTrue(isValidFilter("*"));
		Assert.assertTrue(isValidFilter("  *   "));
	}

	@Test
	public void testIsValidFilterTypeAfterOpenParenthesis() {
		Assert.assertTrue(isValidFilter("(object=value)"));
		Assert.assertFalse(isValidFilter("(=value)"));
		Assert.assertFalse(isValidFilter("(<=value)"));
		Assert.assertFalse(isValidFilter("(>=value)"));
		Assert.assertFalse(isValidFilter("(~=value)"));
		Assert.assertFalse(isValidFilter("(~=value)(object=value)"));
	}

	@Test
	public void testIsValidFilterTypeBeforeCloseParenthesis() {
		Assert.assertTrue(isValidFilter("(object=value)"));
		Assert.assertTrue(isValidFilter("(object=*)"));
		Assert.assertTrue(isValidFilter("(object=subobject=*)"));
		Assert.assertFalse(isValidFilter("(object=)"));
		Assert.assertFalse(isValidFilter("(object<=)"));
		Assert.assertFalse(isValidFilter("(object>=)"));
		Assert.assertFalse(isValidFilter("(object~=)"));
		Assert.assertFalse(isValidFilter("(object=subobject=)"));
		Assert.assertFalse(isValidFilter("(org=liferay)(object=subobject=)"));
	}

	@Test
	public void testIsValidFilterTypesInSequence() {
		Assert.assertTrue(isValidFilter("(object=value)"));
		Assert.assertTrue(isValidFilter("(object=value=subvalue)"));
		Assert.assertTrue(isValidFilter("(object<=value)"));
		Assert.assertTrue(isValidFilter("(object<=value<=subvalue)"));
		Assert.assertTrue(isValidFilter("(object>=value)"));
		Assert.assertTrue(isValidFilter("(object>=value>=subvalue)"));
		Assert.assertTrue(isValidFilter("(object~=value)"));
		Assert.assertTrue(isValidFilter("(object~=value~=subvalue)"));
		Assert.assertTrue(
			isValidFilter("(object~=value>=subvalue<=subsubvalue)"));
		Assert.assertFalse(isValidFilter("(object==value)"));
		Assert.assertFalse(isValidFilter("(object=value=<=subvalue)"));
		Assert.assertFalse(isValidFilter("(object~==value)"));
		Assert.assertFalse(isValidFilter("(object=value=>=subvalue)"));
		Assert.assertFalse(
			isValidFilter("(object~=value>==subvalue<=subsubvalue)"));
	}

	protected boolean isValidFilter(String filter) {
		return LDAPUtil.isValidFilter(filter);
	}

}