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

package com.liferay.portal.kernel.exception;

import com.liferay.petra.string.StringPool;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Preston Crary
 */
public class UserPasswordException extends PortalException {

	public static class MustBeLonger extends UserPasswordException {

		public MustBeLonger(long userId, int minLength) {
			super(
				String.format(
					"Password for user %s must be at least %s characters",
					userId, minLength));

			this.minLength = minLength;
			this.userId = userId;
		}

		public final int minLength;
		public final long userId;

	}

	public static class MustComplyWithModelListeners
		extends UserPasswordException {

		public MustComplyWithModelListeners(
			long userId, ModelListenerException modelListenerException) {

			super(
				String.format(
					"Password must comply with model listeners: " +
						modelListenerException.getMessage()));

			this.userId = userId;
			this.modelListenerException = modelListenerException;
		}

		public final ModelListenerException modelListenerException;
		public final long userId;

	}

	public static class MustComplyWithRegex extends UserPasswordException {

		public MustComplyWithRegex(long userId, String regex) {
			super(String.format("Password must comply with regex: " + regex));

			this.regex = regex;
			this.userId = userId;
		}

		public final String regex;
		public final long userId;

	}

	public static class MustHaveMoreAlphanumeric extends UserPasswordException {

		public MustHaveMoreAlphanumeric(long minAlphanumeric) {
			super(
				String.format(
					"Password must have at least %s alphanumeric characters",
					minAlphanumeric));

			this.minAlphanumeric = minAlphanumeric;
		}

		public final long minAlphanumeric;

	}

	public static class MustHaveMoreLowercase extends UserPasswordException {

		public MustHaveMoreLowercase(long minLowercase) {
			super(
				String.format(
					"Password must have at least %s lowercase characters",
					minLowercase));

			this.minLowercase = minLowercase;
		}

		public final long minLowercase;

	}

	public static class MustHaveMoreNumbers extends UserPasswordException {

		public MustHaveMoreNumbers(long minNumbers) {
			super(
				String.format(
					"Password must have at least %s numbers", minNumbers));

			this.minNumbers = minNumbers;
		}

		public final long minNumbers;

	}

	public static class MustHaveMoreSymbols extends UserPasswordException {

		public MustHaveMoreSymbols(long minSymbols) {
			super(
				String.format(
					"Password must have at least %s symbols", minSymbols));

			this.minSymbols = minSymbols;
		}

		public final long minSymbols;

	}

	public static class MustHaveMoreUppercase extends UserPasswordException {

		public MustHaveMoreUppercase(long minUppercase) {
			super(
				String.format(
					"Password must have at least %s uppercase characters",
					minUppercase));

			this.minUppercase = minUppercase;
		}

		public final long minUppercase;

	}

	public static class MustMatch extends UserPasswordException {

		public MustMatch(long userId) {
			super(String.format("Passwords for user %s must match", userId));

			this.userId = userId;
		}

		public final long userId;

	}

	public static class MustMatchCurrentPassword extends UserPasswordException {

		public MustMatchCurrentPassword(long userId) {
			super(
				String.format(
					"Password for user %s does not match the current password",
					userId));

			this.userId = userId;
		}

		public final long userId;

	}

	public static class MustNotBeChanged extends UserPasswordException {

		public MustNotBeChanged(long userId) {
			super(
				String.format(
					"Password for user %s must not be changed under the " +
						"current password policy",
					userId));

			this.userId = userId;
		}

		public final long userId;

	}

	public static class MustNotBeChangedYet extends UserPasswordException {

		public MustNotBeChangedYet(long userId, Date changeableDate) {
			super(
				String.format(
					"Password for user %s must not be changed until %s", userId,
					changeableDate));

			this.userId = userId;
			this.changeableDate = changeableDate;
		}

		public final Date changeableDate;
		public long userId;

	}

	public static class MustNotBeEqualToCurrent extends UserPasswordException {

		public MustNotBeEqualToCurrent(long userId) {
			super(
				String.format(
					"Password for user %s must not be equal to their current " +
						"password",
					userId));

			this.userId = userId;
		}

		public final long userId;

	}

	public static class MustNotBeNull extends UserPasswordException {

		public MustNotBeNull(long userId) {
			super(
				String.format("Password for user %s must not be null", userId));

			this.userId = userId;
		}

		public long userId;

	}

	public static class MustNotBeRecentlyUsed extends UserPasswordException {

		public MustNotBeRecentlyUsed(long userId) {
			super(
				String.format(
					"Password for user %s was used too recently", userId));

			this.userId = userId;
		}

		public long userId;

	}

	public static class MustNotBeTrivial extends UserPasswordException {

		public MustNotBeTrivial(long userId) {
			super(
				String.format(
					"Password for user %s must not be too trivial", userId));

			this.userId = userId;
		}

		public long userId;

	}

	public static class MustNotContainDictionaryWords
		extends UserPasswordException {

		public MustNotContainDictionaryWords(
			long userId, List<String> dictionaryWords) {

			super(
				String.format(
					"Password for user %s must not contain dictionary words " +
						"such as: %s",
					userId, _getDictionaryWordsString(dictionaryWords)));

			this.userId = userId;
			this.dictionaryWords = dictionaryWords;
		}

		public final List<String> dictionaryWords;
		public long userId;

	}

	private static String _getDictionaryWordsString(
		List<String> dictionaryWords) {

		if (dictionaryWords.size() <= 10) {
			return dictionaryWords.toString();
		}

		List<String> sampleDictionaryWords = dictionaryWords.subList(0, 10);

		return sampleDictionaryWords.toString() + StringPool.SPACE +
			StringPool.TRIPLE_PERIOD;
	}

	private UserPasswordException(String message) {
		super(message);
	}

}