/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.validator.test;

import com.liferay.account.configuration.AccountEntryEmailDomainsConfiguration;
import com.liferay.account.validator.AccountEntryEmailAddressValidator;
import com.liferay.account.validator.AccountEntryEmailAddressValidatorFactory;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class AccountEntryEmailAddressValidatorTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_companyId = TestPropsValues.getCompanyId();
	}

	@Test
	public void testGetBlockedDomains() throws Exception {
		_withValidator(
			validatorArgs -> validatorArgs.blockedDomains = _EMPTY_STRING_ARRAY,
			validator -> Assert.assertArrayEquals(
				_EMPTY_STRING_ARRAY, validator.getBlockedDomains()));
		_withValidator(
			validatorArgs -> validatorArgs.blockedDomains = _BLOCKED_DOMAINS,
			validator -> Assert.assertArrayEquals(
				_BLOCKED_DOMAINS, validator.getBlockedDomains()));
	}

	@Test
	public void testGetValidDomains() throws Exception {
		_withValidator(
			validatorArgs -> validatorArgs.validDomains = _EMPTY_STRING_ARRAY,
			validator -> Assert.assertArrayEquals(
				_EMPTY_STRING_ARRAY, validator.getValidDomains()));
		_withValidator(
			validatorArgs -> validatorArgs.validDomains = _VALID_DOMAINS,
			validator -> Assert.assertArrayEquals(
				_VALID_DOMAINS, validator.getValidDomains()));
	}

	@Test
	public void testIsBlockedDomain() throws Exception {
		_withValidator(
			validatorArgs -> validatorArgs.blockedDomains = _EMPTY_STRING_ARRAY,
			validator -> {
				Assert.assertFalse(
					validator.isBlockedDomain(_BLOCKED_DOMAINS[0]));
				Assert.assertFalse(
					validator.isBlockedDomain(
						_toEmailAddress(_BLOCKED_DOMAINS[0])));
			});
		_withValidator(
			validatorArgs -> validatorArgs.blockedDomains = _BLOCKED_DOMAINS,
			validator -> Assert.assertTrue(
				validator.isBlockedDomain(_BLOCKED_DOMAINS[0])));
	}

	@Test
	public void testIsEmailAddressDomainValidationEnabled() throws Exception {
		_withValidator(
			validatorArgs ->
				validatorArgs.emailAddressDomainValidationEnabled = true,
			validator -> Assert.assertTrue(
				validator.isEmailAddressDomainValidationEnabled()));
		_withValidator(
			validatorArgs ->
				validatorArgs.emailAddressDomainValidationEnabled = false,
			validator -> Assert.assertFalse(
				validator.isEmailAddressDomainValidationEnabled()));
	}

	@Test
	public void testIsValidDomain() throws Exception {
		_withValidator(
			validatorArgs -> validatorArgs.blockedDomains = _BLOCKED_DOMAINS,
			validator -> Assert.assertFalse(
				validator.isValidDomain(_BLOCKED_DOMAINS[0])));
		_withValidator(
			validatorArgs -> {
				validatorArgs.emailAddressDomainValidationEnabled = false;
				validatorArgs.validDomains = _VALID_DOMAINS;
			},
			validator -> {
				Assert.assertTrue(validator.isValidDomain(_VALID_DOMAINS[0]));
				Assert.assertTrue(
					validator.isValidDomain(
						_toEmailAddress(_VALID_DOMAINS[0])));
				Assert.assertTrue(validator.isValidDomain("whatever.com"));
			});
		_withValidator(
			validatorArgs -> {
				validatorArgs.emailAddressDomainValidationEnabled = true;
				validatorArgs.validDomains = _EMPTY_STRING_ARRAY;
			},
			validator -> {
				Assert.assertTrue(validator.isValidDomain(_VALID_DOMAINS[0]));
				Assert.assertTrue(validator.isValidDomain("whatever.com"));
			});
		_withValidator(
			validatorArgs -> {
				validatorArgs.emailAddressDomainValidationEnabled = true;
				validatorArgs.validDomains = _VALID_DOMAINS;
			},
			validator -> {
				Assert.assertTrue(validator.isValidDomain(_VALID_DOMAINS[0]));
				Assert.assertFalse(validator.isValidDomain("whatever.com"));
			});
	}

	@Test
	public void testIsValidDomainFormat() throws Exception {
		_withValidator(
			validatorArgs -> validatorArgs.customTLDs = _EMPTY_STRING_ARRAY,
			validator -> {
				Assert.assertTrue(validator.isValidDomainFormat("valid.com"));
				Assert.assertFalse(validator.isValidDomainFormat("invalid"));
				Assert.assertFalse(
					validator.isValidDomainFormat("invalid.unknowntld"));
				Assert.assertFalse(
					validator.isValidDomainFormat("invalid.demo"));
			});
		_withValidator(
			validatorArgs -> validatorArgs.customTLDs = _CUSTOM_TLDS,
			validator -> Assert.assertTrue(
				validator.isValidDomainFormat("valid.demo")));
	}

	@Test
	public void testIsValidDomainStrict() throws Exception {
		_withValidator(
			validatorArgs -> validatorArgs.blockedDomains = _BLOCKED_DOMAINS,
			validator -> Assert.assertFalse(
				validator.isValidDomainStrict(_BLOCKED_DOMAINS[0])));
		_withValidator(
			validatorArgs -> {
				validatorArgs.emailAddressDomainValidationEnabled = false;
				validatorArgs.validDomains = _VALID_DOMAINS;
			},
			validator -> {
				Assert.assertFalse(
					validator.isValidDomainStrict(_VALID_DOMAINS[0]));
				Assert.assertFalse(
					validator.isValidDomainStrict("whatever.com"));
			});
		_withValidator(
			validatorArgs -> {
				validatorArgs.emailAddressDomainValidationEnabled = true;
				validatorArgs.validDomains = _EMPTY_STRING_ARRAY;
			},
			validator -> {
				Assert.assertFalse(
					validator.isValidDomainStrict(_VALID_DOMAINS[0]));
				Assert.assertFalse(
					validator.isValidDomainStrict("whatever.com"));
			});
		_withValidator(
			validatorArgs -> {
				validatorArgs.emailAddressDomainValidationEnabled = true;
				validatorArgs.validDomains = _VALID_DOMAINS;
			},
			validator -> {
				Assert.assertTrue(
					validator.isValidDomainStrict(_VALID_DOMAINS[0]));
				Assert.assertTrue(
					validator.isValidDomainStrict(
						_toEmailAddress(_VALID_DOMAINS[0])));
				Assert.assertFalse(
					validator.isValidDomainStrict("whatever.com"));
			});
	}

	@Test
	public void testIsValidEmailAddressFormat() throws Exception {
		_withValidator(
			validatorArgs -> validatorArgs.customTLDs = _EMPTY_STRING_ARRAY,
			validator -> {
				Assert.assertTrue(
					validator.isValidEmailAddressFormat("user@valid.com"));
				Assert.assertFalse(
					validator.isValidEmailAddressFormat("user@invalid"));
				Assert.assertFalse(
					validator.isValidEmailAddressFormat(
						"user@invalid.unknowntld"));
				Assert.assertFalse(
					validator.isValidEmailAddressFormat("user@invalid.demo"));
				Assert.assertFalse(
					validator.isValidEmailAddressFormat("invalid.com"));
			});
		_withValidator(
			validatorArgs -> validatorArgs.customTLDs = _CUSTOM_TLDS,
			validator -> Assert.assertTrue(
				validator.isValidEmailAddressFormat("user@valid.demo")));
	}

	private String _toEmailAddress(String domain) {
		return "user@" + domain;
	}

	private void _withValidator(
			Consumer<ValidatorArgs> validatorArgsConsumer,
			UnsafeConsumer<AccountEntryEmailAddressValidator, Exception>
				accountEntryEmailAddressValidatorExceptionUnsafeConsumer)
		throws Exception {

		ValidatorArgs validatorArgs = new ValidatorArgs();

		validatorArgsConsumer.accept(validatorArgs);

		accountEntryEmailAddressValidatorExceptionUnsafeConsumer.accept(
			_accountEntryEmailAddressValidatorFactory.create(
				validatorArgs.blockedDomains, _companyId,
				validatorArgs.customTLDs,
				validatorArgs.emailAddressDomainValidationEnabled,
				validatorArgs.validDomains));

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						AccountEntryEmailDomainsConfiguration.class.getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"blockedEmailDomains",
							StringUtil.merge(
								validatorArgs.blockedDomains,
								StringPool.NEW_LINE)
						).put(
							"companyId", _companyId
						).put(
							"customTLDs", validatorArgs.customTLDs
						).put(
							"enableEmailDomainValidation",
							validatorArgs.emailAddressDomainValidationEnabled
						).build())) {

			accountEntryEmailAddressValidatorExceptionUnsafeConsumer.accept(
				_accountEntryEmailAddressValidatorFactory.create(
					_companyId, validatorArgs.validDomains));
		}
	}

	private static final String[] _BLOCKED_DOMAINS = {"blocked.com"};

	private static final String[] _CUSTOM_TLDS = {"demo"};

	private static final String[] _EMPTY_STRING_ARRAY = new String[0];

	private static final String[] _VALID_DOMAINS = {"valid.com"};

	@Inject
	private AccountEntryEmailAddressValidatorFactory
		_accountEntryEmailAddressValidatorFactory;

	private long _companyId;

	private static final class ValidatorArgs {

		public String[] blockedDomains = _EMPTY_STRING_ARRAY;
		public String[] customTLDs = _EMPTY_STRING_ARRAY;
		public boolean emailAddressDomainValidationEnabled = false;
		public String[] validDomains = _EMPTY_STRING_ARRAY;

	}

}