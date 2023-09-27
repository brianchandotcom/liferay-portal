/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.pwd;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.model.impl.PasswordPolicyImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Mika Koivisto
 */
public class PasswordPolicyToolkitTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_passwordPolicyToolkit = new PasswordPolicyToolkit();

		_passwordPolicy = new PasswordPolicyImpl();

		_passwordPolicy.setChangeable(true);
		_passwordPolicy.setCheckSyntax(true);
		_passwordPolicy.setAllowDictionaryWords(true);
		_passwordPolicy.setMinAlphanumeric(5);
		_passwordPolicy.setMinLength(8);
		_passwordPolicy.setMinLowerCase(2);
		_passwordPolicy.setMinNumbers(1);
		_passwordPolicy.setMinSymbols(1);
		_passwordPolicy.setMinUpperCase(2);
		_passwordPolicy.setRegex(".{5,}");
	}

	@Test
	public void testGeneratePassword() throws PortalException {
		String password = _passwordPolicyToolkit.generate(_passwordPolicy);

		_passwordPolicyToolkit.validate(password, password, _passwordPolicy);
	}

	@Test
	public void testValidateLength() {
		Assert.assertFalse(validate("xH9fxM@"));
	}

	@Test
	public void testValidateMinAlphanumeric() {
		Assert.assertFalse(validate("xH9f.,@-"));
	}

	@Test
	public void testValidateMinLowerChars() {
		Assert.assertFalse(validate("xHFXM@W"));
	}

	@Test
	public void testValidateMinNumbers() {
		Assert.assertFalse(validate("xHafxMkw"));
	}

	@Test
	public void testValidateMinSpecial() {
		Assert.assertFalse(validate("xH9fxMkw"));
	}

	@Test
	public void testValidateMinUpperChars() {
		Assert.assertFalse(validate("xh9fxM@w"));
	}

	@Test
	public void testValidateRegex() {
		Assert.assertFalse(validate("xH9fxM@"));
	}

	@Test
	public void testValidateValid() {
		Assert.assertTrue(validate("xH9fxM@w"));
	}

	@Test
	public void testValidateValidUpperCase() {
		Assert.assertTrue(validate("xO9fxlM@w"));
	}

	protected boolean validate(String password) {
		try {
			_passwordPolicyToolkit.validate(
				password, password, _passwordPolicy);
		}
		catch (Exception exception) {
			return false;
		}

		return true;
	}

	private PasswordPolicy _passwordPolicy;
	private PasswordPolicyToolkit _passwordPolicyToolkit;

}