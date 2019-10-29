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
		SafeLdapFilter safeLdapFilter = SafeLdapFilter.fromUnsafeFilter(
			"(|(key1={0})((key2={1})))", filter -> true);

		safeLdapFilter = safeLdapFilter.and(
			SafeLdapFilterTemplate.fromUnsafeFilter(
				"(|(key2={2})((key1={3})))", filter -> true));

		safeLdapFilter = safeLdapFilter.and(
			SafeLdapFilterTemplate.fromUnsafeFilter(
				"(|(key3={4})((key4={5})))", filter -> true));

		safeLdapFilter = safeLdapFilter.and(
			SafeLdapFilter.fromUnsafeFilter(
				"(keyInvalid={6})", filter -> true));

		safeLdapFilter = safeLdapFilter.or(safeLdapFilter.not());

		test(
			safeLdapFilter,
			StringBundler.concat(
				"(|(&(&(&(|(key1={0})((key2={1})))",
				"(|(key2={2})((key1={3}))))(|(key3={4})((key4={5}))))",
				"(keyInvalid={6}))(!(&(&(&",
				"(|(key1={0})((key2={1})))(|(key2={2})((key1={3}))))",
				"(|(key3={4})((key4={5}))))(keyInvalid={6}))))"));
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
	public void testFromUnsafeFilter() {
		String ldapFilter = "(key=value)";

		try {
			SafeLdapFilter.fromUnsafeFilter(
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
	public void testLe() {
		test(SafeLdapFilter.le("key", "value"), "(key<={0})", "value");
	}

	@Test
	public void testNot() {
		SafeLdapFilter safeLdapFilter = SafeLdapFilter.eq("key", "value");

		test(safeLdapFilter.not(), "(!(key={0}))", "value");
	}

	@Test
	public void testOperatorsEscape() {
		Assert.assertEquals(
			"\\3c\\3d\\3e\\7e", SafeLdapFilter.rfc2254Escape("<=>~"));
	}

	@Test
	public void testOr() {
		SafeLdapFilter safeLdapFilter = SafeLdapFilter.eq("key1", "value1");

		test(
			safeLdapFilter.or(SafeLdapFilter.eq("key2", "value2")),
			"(|(key1={0})(key2={1}))", new Object[] {"value1", "value2"});
	}

	@Test
	public void testReplace() throws LDAPFilterException {
		test(
			new SafeLdapFilterTemplate(
				new StringBundler("(key=@placeholder@)"),
				Collections.emptyList(), new String[] {"@placeholder@"},
				filter -> true
			).replace(
				new String[] {"@placeholder@"}, new String[] {"value"}
			),
			"(key={0})", "value");

		test(
			new SafeLdapFilterTemplate(
				new StringBundler(
					"(&(name=@name@)(|(email=@email@)(email2=@email@)))"),
				Collections.emptyList(), new String[] {"@name@", "@email@"},
				filter -> true
			).replace(
				new String[] {"@email@", "@name@"},
				new String[] {"test@liferay.com", "Joe Bloggs"}
			),
			"(&(name={0})(|(email={1})(email2={2})))",
			new Object[] {
				"Joe Bloggs", "test@liferay.com", "test@liferay.com"
			});

		try {
			test(
				new SafeLdapFilterTemplate(
					new StringBundler("(key=@placeholder@)"),
					Collections.emptyList(), new String[] {"@placeholder@"},
					filter -> true
				).replace(
					new String[] {"@unknownKey@"}, new String[] {"value"}
				),
				"(key={0})", "value");

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Parameter key @unknownKey@ is not supported by the template",
				iae.getMessage());
		}
	}

	@Test
	public void testReplaceInTemplateKey() {
		try {
			new SafeLdapFilterTemplate(
				"(key>=@in@)", new String[] {"@in@"}, filter -> true);

			new SafeLdapFilterTemplate(
				"(key>=val@in@ue)", new String[] {"@in@"}, filter -> true);

			new SafeLdapFilterTemplate(
				"(key>=value<=@in@)", new String[] {"@in@"}, filter -> true);

			new SafeLdapFilterTemplate(
				"(key>=@in@<=value)", new String[] {"@in@"}, filter -> true);

			new SafeLdapFilterTemplate(
				"(&(key=in)(key=@in@))", new String[] {"@in@"}, filter -> true);

			new SafeLdapFilterTemplate(
				"(&(key=@in@)(key=in))", new String[] {"@in@"}, filter -> true);

			new SafeLdapFilterTemplate(
				"(&(key=@in@)(key=@in@))", new String[] {"@in@"},
				filter -> true);

			new SafeLdapFilterTemplate(
				"(&(key>=val@in@ue)(key>=val@in@ue))", new String[] {"@in@"},
				filter -> true);

			new SafeLdapFilterTemplate(
				"(&(key>=value<=@in@)(key>=value<=@in@))",
				new String[] {"@in@"}, filter -> true);

			new SafeLdapFilterTemplate(
				"(&(key>=@in@<=value)(key>=@in@<=value))",
				new String[] {"@in@"}, filter -> true);
		}
		catch (LDAPFilterException ldapfe) {
			Assert.fail(ldapfe.getMessage());
		}

		try {
			new SafeLdapFilterTemplate(
				"(key@in@=value)", new String[] {"@in@"}, filter -> true);

			Assert.fail();
		}
		catch (LDAPFilterException ldapfe) {
			Assert.assertEquals(
				"Expression '(key@in@' cannot contain '@in@' inside template " +
					"'(key@in@=value)'",
				ldapfe.getMessage());
		}
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
	public void testTemplateComplexScenario() throws LDAPFilterException {
		String template = StringBundler.concat(
			"(|(key1=@placeholder1@)((key2=@placeholder2@)))",
			"(|(key2=@placeholder2@)((key1=@placeholder1@)))",
			"(|(key3=@placeholder1@)((key4=@placeholder2@)))",
			"(keyInvalid=@placeholderInvalid@)");

		try {
			new SafeLdapFilterTemplate(
				template,
				new String[] {
					"@placeholder1@", "@placeholder2@", "@placeholderInvalid@"
				},
				filter -> true);
		}
		catch (LDAPFilterException ldapfe) {
			Assert.fail(ldapfe.getMessage());
		}

		String result =
			"(|(key1={0})((key2={1})))(|(key2={2})((key1={3})))" +
				"(|(key3={4})((key4={5})))(keyInvalid={6})";

		test(
			new SafeLdapFilterTemplate(
				new StringBundler(template), Collections.emptyList(),
				new String[] {
					"@placeholder1@", "@placeholder2@", "@placeholderInvalid@"
				},
				filter -> true
			).replace(
				new String[] {
					"@placeholder1@", "@placeholder2@", "@placeholderInvalid@"
				},
				new String[] {"value1", "value2", "valueInvalid"}
			),
			result,
			new Object[] {
				"value1", "value2", "value2", "value1", "value1", "value2",
				"valueInvalid"
			});
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