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

import com.liferay.portal.UserPasswordException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.security.SecureRandom;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.words.WordsUtil;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.User;
import com.liferay.portal.service.PasswordTrackerLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
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
		_lowerCaseCharsetArray = getSortedCharArray(
			PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_LOWERCASE);
		_numbersCharsetArray = getSortedCharArray(
			PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_NUMBERS);
		_symbolsCharsetArray = getSortedCharArray(
			PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_SYMBOLS);
		_upperCaseCharsetArray = getSortedCharArray(
			PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_UPPERCASE);

		_alphanumericCharsetArray = ArrayUtil.append(
			_lowerCaseCharsetArray, _upperCaseCharsetArray,
			_numbersCharsetArray);

		Arrays.sort(_alphanumericCharsetArray);

		StringBundler sb = new StringBundler(4);

		sb.append(
			PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_LOWERCASE);
		sb.append(PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_NUMBERS);
		sb.append(PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_SYMBOLS);
		sb.append(
			PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_CHARSET_UPPERCASE);

		_completeCharset = sb.toString();

		_lowerCaseGeneratorCharsetArray = getSortedCharArray(
			PropsValues.
				PASSWORDS_PASSWORDPOLICYTOOLKIT_GENERATOR_CHARSET_LOWERCASE);
		_numbersGeneratorCharsetArray = getSortedCharArray(
			PropsValues.
				PASSWORDS_PASSWORDPOLICYTOOLKIT_GENERATOR_CHARSET_NUMBERS);
		_symbolsGeneratorCharsetArray = getSortedCharArray(
			PropsValues.
				PASSWORDS_PASSWORDPOLICYTOOLKIT_GENERATOR_CHARSET_SYMBOLS);
		_upperCaseGeneratorCharsetArray = getSortedCharArray(
			PropsValues.
				PASSWORDS_PASSWORDPOLICYTOOLKIT_GENERATOR_CHARSET_UPPERCASE);

		_alphanumericGeneratorCharsetArray = ArrayUtil.append(
			_lowerCaseGeneratorCharsetArray, _upperCaseGeneratorCharsetArray,
			_numbersGeneratorCharsetArray);

		Arrays.sort(_alphanumericGeneratorCharsetArray);

		sb = new StringBundler(4);

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

		_completeGeneratorCharset = sb.toString();
	}

	@Override
	public String generate(PasswordPolicy passwordPolicy) {
		if (PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_GENERATOR.equals(
				"static")) {

			return generateStatic(passwordPolicy);
		}
		else {
			return generateDynamic(passwordPolicy);
		}
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

			if ((getUsageCount(password1, _alphanumericCharsetArray) <
					passwordPolicy.getMinAlphanumeric()) ||
				(getUsageCount(password1, _lowerCaseCharsetArray) <
					passwordPolicy.getMinLowerCase()) ||
				(getUsageCount(password1, _numbersCharsetArray) <
					passwordPolicy.getMinNumbers()) ||
				(getUsageCount(password1, _symbolsCharsetArray) <
					passwordPolicy.getMinSymbols()) ||
				(getUsageCount(password1, _upperCaseCharsetArray) <
					passwordPolicy.getMinUpperCase())) {

				throw new UserPasswordException.MustNotBeTrivial(userId);
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
				!user.getPasswordReset()) {

				throw new UserPasswordException.MustNotBeChangedYet(
					userId, new Date(passwordModfiedDate.getTime() + minAge));
			}
		}

		if (PasswordTrackerLocalServiceUtil.isSameAsCurrentPassword(
				userId, password1)) {

			throw new UserPasswordException.MustNotBeEqualToCurrent(userId);
		}
		else if (!PasswordTrackerLocalServiceUtil.isValidPassword(
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
					_lowerCaseGeneratorCharsetArray));
		}

		if (passwordPolicy.getMinNumbers() > 0) {
			sb.append(
				getRandomString(
					passwordPolicy.getMinNumbers(),
					_numbersGeneratorCharsetArray));
		}

		if (passwordPolicy.getMinSymbols() > 0) {
			sb.append(
				getRandomString(
					passwordPolicy.getMinSymbols(),
					_symbolsGeneratorCharsetArray));
		}

		if (passwordPolicy.getMinUpperCase() > 0) {
			sb.append(
				getRandomString(
					passwordPolicy.getMinUpperCase(),
					_upperCaseGeneratorCharsetArray));
		}

		if (alphanumericMinLength > alphanumericActualMinLength) {
			int count = alphanumericMinLength - alphanumericActualMinLength;

			sb.append(
				getRandomString(count, _alphanumericGeneratorCharsetArray));
		}

		if (passwordMinLength >
				(alphanumericMinLength + passwordPolicy.getMinSymbols())) {

			int count =
				passwordMinLength -
					(alphanumericMinLength + passwordPolicy.getMinSymbols());

			sb.append(
				PwdGenerator.getPassword(_completeGeneratorCharset, count));
		}

		if (sb.index() == 0) {
			sb.append(
				PwdGenerator.getPassword(
					_completeGeneratorCharset,
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

	private final char[] _alphanumericCharsetArray;
	private final char[] _alphanumericGeneratorCharsetArray;
	private final String _completeCharset;
	private final String _completeGeneratorCharset;
	private final char[] _lowerCaseCharsetArray;
	private final char[] _lowerCaseGeneratorCharsetArray;
	private final char[] _numbersCharsetArray;
	private final char[] _numbersGeneratorCharsetArray;
	private final char[] _symbolsCharsetArray;
	private final char[] _symbolsGeneratorCharsetArray;
	private final char[] _upperCaseCharsetArray;
	private final char[] _upperCaseGeneratorCharsetArray;

}