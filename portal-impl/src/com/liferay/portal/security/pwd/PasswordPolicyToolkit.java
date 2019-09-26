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

package com.liferay.portal.security.pwd;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.UserPasswordException;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.security.SecureRandom;
import com.liferay.portal.kernel.security.pwd.BasicToolkit;
import com.liferay.portal.kernel.service.PasswordTrackerLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.words.WordsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * @author Scott Lee
 * @author Mika Koivisto
 */
public class PasswordPolicyToolkit extends BasicToolkit {

	public PasswordPolicyToolkit() {
		_generatorLowerCaseCharsetArray = getSortedCharArray(
			PropsValues.
				PASSWORDS_PASSWORDPOLICYTOOLKIT_GENERATOR_CHARSET_LOWERCASE);
		_generatorUpperCaseCharsetArray = getSortedCharArray(
			PropsValues.
				PASSWORDS_PASSWORDPOLICYTOOLKIT_GENERATOR_CHARSET_UPPERCASE);
		_generatorNumbersCharsetArray = getSortedCharArray(
			PropsValues.
				PASSWORDS_PASSWORDPOLICYTOOLKIT_GENERATOR_CHARSET_NUMBERS);

		_generatorAlphanumericCharsetArray = ArrayUtil.append(
			_generatorLowerCaseCharsetArray, _generatorUpperCaseCharsetArray,
			_generatorNumbersCharsetArray);

		Arrays.sort(_generatorAlphanumericCharsetArray);

		_generatorSymbolsCharsetArray = getSortedCharArray(
			PropsValues.
				PASSWORDS_PASSWORDPOLICYTOOLKIT_GENERATOR_CHARSET_SYMBOLS);

		StringBundler sb = new StringBundler(4);

		sb.append(
			PropsValues.
				PASSWORDS_PASSWORDPOLICYTOOLKIT_GENERATOR_CHARSET_LOWERCASE);
		sb.append(
			PropsValues.
				PASSWORDS_PASSWORDPOLICYTOOLKIT_GENERATOR_CHARSET_NUMBERS);
		sb.append(
			PropsValues.
				PASSWORDS_PASSWORDPOLICYTOOLKIT_GENERATOR_CHARSET_SYMBOLS);
		sb.append(
			PropsValues.
				PASSWORDS_PASSWORDPOLICYTOOLKIT_GENERATOR_CHARSET_UPPERCASE);

		_generatorCompleteCharset = sb.toString();

		_validatorLowerCaseCharsetArray = getSortedCharArray(
			PropsValues.
				PASSWORDS_PASSWORDPOLICYTOOLKIT_VALIDATOR_CHARSET_LOWERCASE);
		_validatorUpperCaseCharsetArray = getSortedCharArray(
			PropsValues.
				PASSWORDS_PASSWORDPOLICYTOOLKIT_VALIDATOR_CHARSET_UPPERCASE);
		_validatorNumbersCharsetArray = getSortedCharArray(
			PropsValues.
				PASSWORDS_PASSWORDPOLICYTOOLKIT_VALIDATOR_CHARSET_NUMBERS);

		_validatorAlphanumericCharsetArray = ArrayUtil.append(
			_validatorLowerCaseCharsetArray, _validatorUpperCaseCharsetArray,
			_validatorNumbersCharsetArray);

		Arrays.sort(_validatorAlphanumericCharsetArray);

		_validatorSymbolsCharsetArray = getSortedCharArray(
			PropsValues.
				PASSWORDS_PASSWORDPOLICYTOOLKIT_VALIDATOR_CHARSET_SYMBOLS);
	}

	@Override
	public String generate(PasswordPolicy passwordPolicy) {
		if (PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_GENERATOR.equals(
				"static")) {

			return generateStatic(passwordPolicy);
		}

		return generateDynamic(passwordPolicy);
	}

	@Override
	public void validate(
			long userId, String password1, String password2,
			PasswordPolicy passwordPolicy)
		throws PortalException {

		if (passwordPolicy.isCheckSyntax()) {
			if (!passwordPolicy.isAllowDictionaryWords() &&
				WordsUtil.isDictionaryWord(password1)) {

				throw new UserPasswordException.MustNotContainDictionaryWords(
					userId, WordsUtil.getDictionaryList());
			}

			if (password1.length() < passwordPolicy.getMinLength()) {
				throw new UserPasswordException.MustBeLonger(
					userId, passwordPolicy.getMinLength());
			}

			if (getUsageCount(password1, _validatorAlphanumericCharsetArray) <
					passwordPolicy.getMinAlphanumeric()) {

				throw new UserPasswordException.MustHaveMoreAlphanumeric(
					passwordPolicy.getMinAlphanumeric());
			}

			if (getUsageCount(password1, _validatorLowerCaseCharsetArray) <
					passwordPolicy.getMinLowerCase()) {

				throw new UserPasswordException.MustHaveMoreLowercase(
					passwordPolicy.getMinLowerCase());
			}

			if (getUsageCount(password1, _validatorNumbersCharsetArray) <
					passwordPolicy.getMinNumbers()) {

				throw new UserPasswordException.MustHaveMoreNumbers(
					passwordPolicy.getMinNumbers());
			}

			if (getUsageCount(password1, _validatorSymbolsCharsetArray) <
					passwordPolicy.getMinSymbols()) {

				throw new UserPasswordException.MustHaveMoreSymbols(
					passwordPolicy.getMinSymbols());
			}

			if (getUsageCount(password1, _validatorUpperCaseCharsetArray) <
					passwordPolicy.getMinUpperCase()) {

				throw new UserPasswordException.MustHaveMoreUppercase(
					passwordPolicy.getMinUpperCase());
			}

			String regex = passwordPolicy.getRegex();

			if (Validator.isNotNull(regex) && !password1.matches(regex)) {
				throw new UserPasswordException.MustComplyWithRegex(
					userId, regex);
			}
		}

		if (!passwordPolicy.isChangeable() && (userId != 0)) {
			throw new UserPasswordException.MustNotBeChanged(userId);
		}

		if (userId == 0) {
			return;
		}

		User user = UserLocalServiceUtil.getUserById(userId);

		Date passwordModfiedDate = user.getPasswordModifiedDate();

		if (passwordModfiedDate != null) {
			Date now = new Date();

			long passwordModificationElapsedTime =
				now.getTime() - passwordModfiedDate.getTime();

			long minAge = passwordPolicy.getMinAge() * 1000;

			if ((passwordModificationElapsedTime < minAge) &&
				!user.isPasswordReset()) {

				throw new UserPasswordException.MustNotBeChangedYet(
					userId, new Date(passwordModfiedDate.getTime() + minAge));
			}
		}

		if (PasswordTrackerLocalServiceUtil.isSameAsCurrentPassword(
				userId, password1)) {

			throw new UserPasswordException.MustNotBeEqualToCurrent(userId);
		}

		if (!PasswordTrackerLocalServiceUtil.isValidPassword(
				userId, password1)) {

			throw new UserPasswordException.MustNotBeRecentlyUsed(userId);
		}
	}

	protected String generateDynamic(PasswordPolicy passwordPolicy) {
		int alphanumericActualMinLength =
			passwordPolicy.getMinLowerCase() + passwordPolicy.getMinNumbers() +
				passwordPolicy.getMinUpperCase();

		int alphanumericMinLength = Math.max(
			passwordPolicy.getMinAlphanumeric(), alphanumericActualMinLength);

		int passwordMinLength = Math.max(
			passwordPolicy.getMinLength(),
			alphanumericMinLength + passwordPolicy.getMinSymbols());

		StringBundler sb = new StringBundler(6);

		if (passwordPolicy.getMinLowerCase() > 0) {
			sb.append(
				getRandomString(
					passwordPolicy.getMinLowerCase(),
					_generatorLowerCaseCharsetArray));
		}

		if (passwordPolicy.getMinNumbers() > 0) {
			sb.append(
				getRandomString(
					passwordPolicy.getMinNumbers(),
					_generatorNumbersCharsetArray));
		}

		if (passwordPolicy.getMinSymbols() > 0) {
			sb.append(
				getRandomString(
					passwordPolicy.getMinSymbols(),
					_generatorSymbolsCharsetArray));
		}

		if (passwordPolicy.getMinUpperCase() > 0) {
			sb.append(
				getRandomString(
					passwordPolicy.getMinUpperCase(),
					_generatorUpperCaseCharsetArray));
		}

		if (alphanumericMinLength > alphanumericActualMinLength) {
			int count = alphanumericMinLength - alphanumericActualMinLength;

			sb.append(
				getRandomString(count, _generatorAlphanumericCharsetArray));
		}

		if (passwordMinLength >
				(alphanumericMinLength + passwordPolicy.getMinSymbols())) {

			int count =
				passwordMinLength -
					(alphanumericMinLength + passwordPolicy.getMinSymbols());

			sb.append(
				PwdGenerator.getPassword(_generatorCompleteCharset, count));
		}

		if (sb.index() == 0) {
			sb.append(
				PwdGenerator.getPassword(
					_generatorCompleteCharset,
					PropsValues.PASSWORDS_DEFAULT_POLICY_MIN_LENGTH));
		}

		return RandomUtil.shuffle(new SecureRandom(), sb.toString());
	}

	protected String generateStatic(PasswordPolicy passwordPolicy) {
		return PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_STATIC;
	}

	protected String getRandomString(int count, char[] chars) {
		Random random = new SecureRandom();

		StringBundler sb = new StringBundler(count);

		for (int i = 0; i < count; i++) {
			int index = random.nextInt(chars.length);

			sb.append(chars[index]);
		}

		return sb.toString();
	}

	protected char[] getSortedCharArray(String s) {
		char[] chars = s.toCharArray();

		Arrays.sort(chars);

		return chars;
	}

	protected int getUsageCount(String s, char[] chars) {
		int count = 0;

		for (int i = 0; i < s.length(); i++) {
			if (Arrays.binarySearch(chars, s.charAt(i)) >= 0) {
				count++;
			}
		}

		return count;
	}

	private final char[] _generatorAlphanumericCharsetArray;
	private final String _generatorCompleteCharset;
	private final char[] _generatorLowerCaseCharsetArray;
	private final char[] _generatorNumbersCharsetArray;
	private final char[] _generatorSymbolsCharsetArray;
	private final char[] _generatorUpperCaseCharsetArray;
	private final char[] _validatorAlphanumericCharsetArray;
	private final char[] _validatorLowerCaseCharsetArray;
	private final char[] _validatorNumbersCharsetArray;
	private final char[] _validatorSymbolsCharsetArray;
	private final char[] _validatorUpperCaseCharsetArray;

}