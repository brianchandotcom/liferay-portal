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

package com.liferay.portal.security.pwd;

import com.liferay.portal.UserPasswordException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Randomizer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.User;
import com.liferay.portal.service.PasswordTrackerLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.words.WordsUtil;
import com.liferay.util.PwdGenerator;

import java.util.Arrays;
import java.util.Date;

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
		throws PortalException, SystemException {

		if (passwordPolicy.isCheckSyntax()) {
			if (!passwordPolicy.isAllowDictionaryWords() &&
				WordsUtil.isDictionaryWord(password1)) {

				throw new UserPasswordException(
					UserPasswordException.PASSWORD_CONTAINS_TRIVIAL_WORDS);
			}

			if (password1.length() < passwordPolicy.getMinLength()) {
				throw new UserPasswordException(
					UserPasswordException.PASSWORD_LENGTH);
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

				throw new UserPasswordException(
					UserPasswordException.PASSWORD_TOO_TRIVIAL);
			}

			if (Validator.isNotNull(passwordPolicy.getRegex()) &&
				!password1.matches(passwordPolicy.getRegex())) {

				throw new UserPasswordException(
					UserPasswordException.PASSWORD_INVALID);
			}
		}

		if (!passwordPolicy.isChangeable()) {
			throw new UserPasswordException(
				UserPasswordException.PASSWORD_NOT_CHANGEABLE);
		}

		if (userId != 0) {
			User user = UserLocalServiceUtil.getUserById(userId);

			Date passwordModfiedDate = user.getPasswordModifiedDate();

			if (passwordModfiedDate != null) {

				// LEP-2961

				Date now = new Date();

				long passwordModificationElapsedTime =
					now.getTime() - passwordModfiedDate.getTime();

				long userCreationElapsedTime =
					now.getTime() - user.getCreateDate().getTime();

				long minAge = passwordPolicy.getMinAge() * 1000;

				if ((passwordModificationElapsedTime < minAge) &&
					(userCreationElapsedTime > minAge)) {

					throw new UserPasswordException(
						UserPasswordException.PASSWORD_TOO_YOUNG);
				}
			}

			if (PasswordTrackerLocalServiceUtil.isSameAsCurrentPassword(
					userId, password1)) {

				throw new UserPasswordException(
					UserPasswordException.PASSWORD_SAME_AS_CURRENT);
			}
			else if (!PasswordTrackerLocalServiceUtil.isValidPassword(
						userId, password1)) {

				throw new UserPasswordException(
					UserPasswordException.PASSWORD_ALREADY_USED);
			}
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
					passwordPolicy.getMinLowerCase(), _lowerCaseCharsetArray));
		}

		if (passwordPolicy.getMinNumbers() > 0) {
			sb.append(
				getRandomString(
					passwordPolicy.getMinNumbers(), _numbersCharsetArray));
		}

		if (passwordPolicy.getMinSymbols() > 0) {
			sb.append(
				getRandomString(
					passwordPolicy.getMinSymbols(), _symbolsCharsetArray));
		}

		if (passwordPolicy.getMinUpperCase() > 0) {
			sb.append(
				getRandomString(
					passwordPolicy.getMinUpperCase(), _upperCaseCharsetArray));
		}

		if (alphanumericMinLength > alphanumericActualMinLength) {
			int count = alphanumericMinLength - alphanumericActualMinLength;

			sb.append(getRandomString(count, _alphanumericCharsetArray));
		}

		if (passwordMinLength >
				(alphanumericMinLength + passwordPolicy.getMinSymbols())) {

			int count =
				passwordMinLength -
					(alphanumericMinLength + passwordPolicy.getMinSymbols());

			sb.append(PwdGenerator.getSecurePassword(_completeCharset, count));
		}

		if (sb.index() == 0) {
			sb.append(
				PwdGenerator.getSecurePassword(
					_completeCharset,
					PropsValues.PASSWORDS_DEFAULT_POLICY_MIN_LENGTH));
		}

		Randomizer randomizer = Randomizer.getInstance();

		return randomizer.randomize(sb.toString());
	}

	protected String generateStatic(PasswordPolicy passwordPolicy) {
		return PropsValues.PASSWORDS_PASSWORDPOLICYTOOLKIT_STATIC;
	}

	protected String getRandomString(int count, char[] chars) {
		StringBundler sb = new StringBundler(count);

		Randomizer randomizer = Randomizer.getInstance();

		for (int i = 0; i < count; i++) {
			int index = randomizer.nextInt(chars.length);

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

	private char[] _alphanumericCharsetArray;
	private String _completeCharset;
	private char[] _lowerCaseCharsetArray;
	private char[] _numbersCharsetArray;
	private char[] _symbolsCharsetArray;
	private char[] _upperCaseCharsetArray;

}