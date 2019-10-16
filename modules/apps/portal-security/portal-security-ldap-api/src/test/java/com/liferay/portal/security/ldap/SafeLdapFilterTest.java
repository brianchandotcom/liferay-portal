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

package com.liferay.portal.security.ldap;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.security.ldap.validator.LDAPFilterException;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tomas Polesovsky
 */
public class SafeLdapFilterTest {

	@Test
	public void testAnd() {
		SafeLdapFilter safeLdapFilter = SafeLdapFilter.eq("key1", "value1");

		test(
			safeLdapFilter.and(SafeLdapFilter.eq("key2", "value2")),
			"(&(key1={0})(key2={1}))", new Object[] {"value1", "value2"});
	}

	@Test
	public void testApprox() {
		test(SafeLdapFilter.approx("key", "value"), "(key~={0})", "value");
	}

	@Test
	public void testComplexScenario() throws LDAPFilterException {
		SafeLdapFilter safeLdapFilter = SafeLdapFilter.validate(
			"(|(key1=@placeholder1@)((key2=@placeholder2@)))", filter -> true);

		safeLdapFilter = safeLdapFilter.and(
			SafeLdapFilter.validate(
				"(|(key2=@placeholder2@)((key1=@placeholder1@)))",
				filter -> true));

		safeLdapFilter = safeLdapFilter.and(
			SafeLdapFilter.validate(
				"(|(key3=@placeholder1@)((key4=@placeholder2@)))",
				filter -> true));

		safeLdapFilter = safeLdapFilter.and(
			SafeLdapFilter.eq("(keyInvalid=@placeholderInvalid@)", "invalid"));

		safeLdapFilter = safeLdapFilter.or(safeLdapFilter.not());

		safeLdapFilter = safeLdapFilter.replace(
			new String[] {
				"@placeholder1@", "@placeholder2@", "@placeholderInvalid@"
			},
			new String[] {"value1", "value2", "valueInvalid"});

		test(
			safeLdapFilter,
			StringBundler.concat(
				"(|(&(&(&(|(key1={0})((key2={1})))",
				"(|(key2={2})((key1={3}))))(|(key3={4})((key4={5}))))",
				"(\\28keyInvalid={6}\\29={7}))(!(&(&(&",
				"(|(key1={8})((key2={9})))(|(key2={10})((key1={11}))))",
				"(|(key3={12})((key4={13}))))",
				"(\\28keyInvalid={14}\\29={15}))))"),
			new Object[] {
				"value1", "value2", "value2", "value1", "value1", "value2",
				"valueInvalid", "invalid", "value1", "value2", "value2",
				"value1", "value1", "value2", "valueInvalid", "invalid"
			});
	}

	@Test
	public void testReplaceInKey() {
		SafeLdapFilter safeLdapFilter =
			SafeLdapFilter.eq("keyInvalid@placeholderInvalid@", "invalid");

		safeLdapFilter = safeLdapFilter.replace(
			new String[] {"@placeholderInvalid@"},
			new String[] {"=valueInvalid"});

		test(safeLdapFilter, "(keyInvalid@placeholderInvalid@={0})", "invalid");

		safeLdapFilter = SafeLdapFilter.eq("keySUBvalue", "invalid");

		safeLdapFilter = safeLdapFilter.replace(
			new String[] {"SUB"},
			new String[] {"="});

		test(safeLdapFilter, "(keySUBvalue={0})", "invalid");
	}

	@Test
	public void testKeyInjection() {
		String[] operators = {
			"~=", StringPool.EQUAL, StringPool.GREATER_THAN_OR_EQUAL,
			StringPool.LESS_THAN_OR_EQUAL
		};

		for (String operator : operators) {
			String operatorEscaped =
				"\\" + Integer.toHexString(operator.charAt(0) & 0xFF);

			if (operator.length() == 2) {
				operatorEscaped +=
					"\\" + Integer.toHexString(operator.charAt(1) & 0xFF);
			}

			test(
				SafeLdapFilter.approx("key" + operator + "value", "invalid"),
				"(key" + operatorEscaped + "value~={0})", "invalid");

			test(
				SafeLdapFilter.eq("key" + operator + "value", "invalid"),
				"(key" + operatorEscaped + "value={0})", "invalid");

			test(
				SafeLdapFilter.ge("key" + operator + "value", "invalid"),
				"(key" + operatorEscaped + "value>={0})", "invalid");

			test(
				SafeLdapFilter.le("key" + operator + "value", "invalid"),
				"(key" + operatorEscaped + "value<={0})", "invalid");
		}
	}

	@Test
	public void testEq() {
		test(SafeLdapFilter.eq("key", "value"), "(key={0})", "value");
	}

	@Test
	public void testEx() {
		test(SafeLdapFilter.ex("key"), "(key=*)");
	}

	@Test
	public void testGe() {
		test(SafeLdapFilter.ge("key", "value"), "(key>={0})", "value");
	}

	@Test
	public void testGenerateFilter() {
		test(
			new SafeLdapFilter(
				new StringBundler(
					"(key1=value1)"
				).append(
					"(key2=value2)"
				),
				Collections.emptyList()),
			"(key1=value1)(key2=value2)");
	}

	@Test
	public void testGetArguments() {
		Object[] arguments = {new Object()};

		Assert.assertArrayEquals(
			arguments,
			new SafeLdapFilter(
				new StringBundler(), Arrays.asList(arguments)
			).getArguments());
	}

	@Test
	public void testLe() {
		test(SafeLdapFilter.le("key", "value"), "(key<={0})", "value");
	}

	@Test
	public void testNot() {
		SafeLdapFilter safeLdapFilter = SafeLdapFilter.eq("key", "value");

		test(safeLdapFilter.not(), "(!(key={0}))", "value");
	}

	@Test
	public void testOr() {
		SafeLdapFilter safeLdapFilter = SafeLdapFilter.eq("key1", "value1");

		test(
			safeLdapFilter.or(SafeLdapFilter.eq("key2", "value2")),
			"(|(key1={0})(key2={1}))", new Object[] {"value1", "value2"});
	}

	@Test
	public void testReplace() {
		test(
			new SafeLdapFilter(
				new StringBundler("(key=@placeholder@)"),
				Collections.emptyList()
			).replace(
				new String[] {"@placeholder@"}, new String[] {"value"}
			),
			"(key={0})", "value");

		test(
			new SafeLdapFilter(
				new StringBundler("(name=@name@)"), Collections.emptyList()
			).and(
				new SafeLdapFilter(
					new StringBundler("(email=@email@)"),
					Collections.emptyList()
				).or(
					new SafeLdapFilter(
						new StringBundler("(email2=@email@)"),
						Collections.emptyList())
				)
			).replace(
				new String[] {"@email@", "@name@"},
				new String[] {"test@liferay.com", "Joe Bloggs"}
			),
			"(&(name={0})(|(email={1})(email2={2})))",
			new Object[] {
				"Joe Bloggs", "test@liferay.com", "test@liferay.com"
			});
	}

	@Test
	public void testRfc2254Escape() {
		Assert.assertEquals(
			"C:\\5c\\2a \\28DOS\\29 \\00",
			SafeLdapFilter.rfc2254Escape("C:\\* (DOS) \u0000"));
	}

	@Test
	public void testRfc2254EscapePreserveStar() {
		Assert.assertEquals(
			"C:\\5c* \\28DOS\\29 \\00",
			SafeLdapFilter.rfc2254Escape("C:\\* (DOS) \u0000", true));

		Assert.assertEquals(
			"C:\\5c\\2a \\28DOS\\29 \\00",
			SafeLdapFilter.rfc2254Escape("C:\\* (DOS) \u0000", false));
	}

	@Test
	public void testSubstring() {
		test(SafeLdapFilter.substring("key", "prefix*"), "(key=prefix*)");
	}

	@Test
	public void testValidate() {
		String ldapFilter = "(key=value)";

		try {
			SafeLdapFilter.validate(
				ldapFilter,
				filter -> {
					Assert.assertEquals(ldapFilter, filter);

					return true;
				});
		}
		catch (LDAPFilterException ldapfe) {
			Assert.fail(ldapfe.getMessage());
		}
	}

	protected void test(SafeLdapFilter safeLdapFilter, String filter) {
		test(safeLdapFilter, filter, new Object[0]);
	}

	protected void test(
		SafeLdapFilter safeLdapFilter, String filter, Object argument) {

		test(safeLdapFilter, filter, new Object[] {argument});
	}

	protected void test(
		SafeLdapFilter safeLdapFilter, String filter, Object[] arguments) {

		Assert.assertEquals(filter, safeLdapFilter.generateFilter());
		Assert.assertArrayEquals(arguments, safeLdapFilter.getArguments());
	}

}