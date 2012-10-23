/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.test.TestCase;

/**
 * @author James Lefeu
 */
public class LDAPUtilTest extends TestCase {

	public void testSpecialChars() {
		assertEquals(true, LDAPUtil.validateFilter(""));
		assertEquals(true, LDAPUtil.validateFilter("*"));
		assertEquals(true, LDAPUtil.validateFilter("  *   "));
	}

	public void testOpenAndCloseParentheses() {
		assertEquals(true, LDAPUtil.validateFilter("(object=value)"));
		assertEquals(true, LDAPUtil.validateFilter("  (object=value)"));
		assertEquals(true, LDAPUtil.validateFilter("(object=value)  "));
		assertEquals(false, LDAPUtil.validateFilter("object=value)"));
		assertEquals(false, LDAPUtil.validateFilter("(object=value"));
		assertEquals(false, LDAPUtil.validateFilter("object=value"));
		assertEquals(false, LDAPUtil.validateFilter("("));
		assertEquals(false, LDAPUtil.validateFilter(")"));
		assertEquals(false, LDAPUtil.validateFilter(")("));
	}

	public void testBalancedParentheses() {
		assertEquals(true, LDAPUtil.validateFilter("(object=value)"));
		assertEquals(true, LDAPUtil.validateFilter("((((object=value))))"));
		assertEquals(
			true, LDAPUtil.validateFilter("((((object=value))(org=liferay)))"));
		assertEquals(
			true,
			LDAPUtil.validateFilter(
				"(((inetorg=www)((object=value))(org=liferay)))(user=test)"));
		assertEquals(false, LDAPUtil.validateFilter("(object=value))"));
		assertEquals(false, LDAPUtil.validateFilter("(((object=value))"));
		assertEquals(
			false,
			LDAPUtil.validateFilter("((((object=value)))(org=liferay)))"));
		assertEquals(
			false,
			LDAPUtil.validateFilter(
				"(((inetorg=www)((object=value))(org=liferay)))(user=test))"));
	}

	public void testFilterTypesInSequence() {
		assertEquals(true, LDAPUtil.validateFilter("(object=value)"));
		assertEquals(true, LDAPUtil.validateFilter("(object=value=subvalue)"));
		assertEquals(true, LDAPUtil.validateFilter("(object<=value)"));
		assertEquals(
			true, LDAPUtil.validateFilter("(object<=value<=subvalue)"));
		assertEquals(true, LDAPUtil.validateFilter("(object>=value)"));
		assertEquals(
			true, LDAPUtil.validateFilter("(object>=value>=subvalue)"));
		assertEquals(true, LDAPUtil.validateFilter("(object~=value)"));
		assertEquals(
			true, LDAPUtil.validateFilter("(object~=value~=subvalue)"));
		assertEquals(
			true,
			LDAPUtil.validateFilter("(object~=value>=subvalue<=subsubvalue)"));
		assertEquals(false, LDAPUtil.validateFilter("(object==value)"));
		assertEquals(
			false, LDAPUtil.validateFilter("(object=value=<=subvalue)"));
		assertEquals(false, LDAPUtil.validateFilter("(object~==value)"));
		assertEquals(
			false, LDAPUtil.validateFilter("(object=value=>=subvalue)"));
		assertEquals(
			false,
			LDAPUtil.validateFilter("(object~=value>==subvalue<=subsubvalue)"));
	}

	public void testFilterTypeAfterOpenParenthesis() {
		assertEquals(true, LDAPUtil.validateFilter("(object=value)"));
		assertEquals(false, LDAPUtil.validateFilter("(=value)"));
		assertEquals(false, LDAPUtil.validateFilter("(<=value)"));
		assertEquals(false, LDAPUtil.validateFilter("(>=value)"));
		assertEquals(false, LDAPUtil.validateFilter("(~=value)"));
		assertEquals(false, LDAPUtil.validateFilter("(~=value)(object=value)"));
	}

	public void testFilterTypeBeforeCloseParenthesis() {
		assertEquals(true, LDAPUtil.validateFilter("(object=value)"));
		assertEquals(true, LDAPUtil.validateFilter("(object=*)"));
		assertEquals(true, LDAPUtil.validateFilter("(object=subobject=*)"));
		assertEquals(false, LDAPUtil.validateFilter("(object=)"));
		assertEquals(false, LDAPUtil.validateFilter("(object<=)"));
		assertEquals(false, LDAPUtil.validateFilter("(object>=)"));
		assertEquals(false, LDAPUtil.validateFilter("(object~=)"));
		assertEquals(false, LDAPUtil.validateFilter("(object=subobject=)"));
		assertEquals(
			false, LDAPUtil.validateFilter("(org=liferay)(object=subobject=)"));
	}

	public void testNoFilterType() {
		assertEquals(true, LDAPUtil.validateFilter("(object=value)"));
		assertEquals(false, LDAPUtil.validateFilter("(object)"));
		assertEquals(false, LDAPUtil.validateFilter("(object)(value)"));
		assertEquals(false, LDAPUtil.validateFilter("(!object)"));
	}

}