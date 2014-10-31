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

package com.liferay.portal;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 * @author Preston Crary
 */
public class UserPasswordException extends PortalException {

	@Deprecated
	public static final int PASSWORD_ALREADY_USED = 1;

	@Deprecated
	public static final int PASSWORD_CONTAINS_TRIVIAL_WORDS = 2;

	@Deprecated
	public static final int PASSWORD_INVALID = 3;

	@Deprecated
	public static final int PASSWORD_LENGTH = 4;

	@Deprecated
	public static final int PASSWORD_NOT_CHANGEABLE = 5;

	@Deprecated
	public static final int PASSWORD_SAME_AS_CURRENT = 6;

	@Deprecated
	public static final int PASSWORD_TOO_TRIVIAL = 8;

	@Deprecated
	public static final int PASSWORD_TOO_YOUNG = 9;

	@Deprecated
	public static final int PASSWORDS_DO_NOT_MATCH = 10;

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public UserPasswordException(int type) {
		_type = type;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public int getType() {
		return _type;
	}

	public static class MustBeLonger extends UserPasswordException {

		public MustBeLonger(long userId) {
			super(
				String.format("Password for user %s must be longer", userId),
				PASSWORD_LENGTH);

			_userId = userId;
		}

		public long getUserId() {
			return _userId;
		}

		private long _userId;

	}

	public static class MustBeValid extends UserPasswordException {

		public MustBeValid(long userId) {
			super(
				String.format("Password for user %s must be valid", userId),
				PASSWORD_INVALID);

			_userId = userId;
		}

		public long getUserId() {
			return _userId;
		}

		private long _userId;

	}

	public static class MustMatch extends UserPasswordException {

		public MustMatch(long userId) {
			super(
				String.format("Passwords for user %s must match", userId),
				PASSWORDS_DO_NOT_MATCH);

			_userId = userId;
		}

		public long getUserId() {
			return _userId;
		}

		private long _userId;

	}

	public static class MustNotBeChanged extends UserPasswordException {

		public MustNotBeChanged(long userId) {
			super(
				String.format("Password for user %s cannot be changed", userId),
				PASSWORD_NOT_CHANGEABLE);

			_userId = userId;
		}

		public long getUserId() {
			return _userId;
		}

		private long _userId;

	}

	public static class MustNotBeCurrent extends UserPasswordException {

		public MustNotBeCurrent(long userId) {
			super(
				String.format(
					"Password for user %s must not their current password",
					userId),
				PASSWORD_SAME_AS_CURRENT);

			_userId = userId;
		}

		public long getUserId() {
			return _userId;
		}

		private long _userId;

	}

	public static class MustNotBeNull extends UserPasswordException {

		public MustNotBeNull(long userId) {
			super(
				String.format("Password for user %s must not be null", userId),
				PASSWORD_INVALID);

			_userId = userId;
		}

		public long getUserId() {
			return _userId;
		}

		private long _userId;

	}

	public static class MustNotBeRecent extends UserPasswordException {

		public MustNotBeRecent(long userId) {
			super(
				String.format(
					"Password for user %s has already been used recently",
						userId),
				PASSWORD_ALREADY_USED);

			_userId = userId;
		}

		public long getUserId() {
			return _userId;
		}

		private long _userId;

	}

	public static class MustNotBeTooYoung extends UserPasswordException {

		public MustNotBeTooYoung(long userId, Date changeableDate) {
			super(
				String.format(
					"Password for user %s must not be changed until %s", userId,
					changeableDate),
				PASSWORD_TOO_YOUNG);

			_userId = userId;
			_changeableDate = changeableDate;
		}

		public Date getChangeableDate() {
			return _changeableDate;
		}

		public long getUserId() {
			return _userId;
		}

		private final Date _changeableDate;
		private long _userId;

	}

	public static class MustNotBeTrivial extends UserPasswordException {

		public MustNotBeTrivial(long userId) {
			super(
				String.format(
					"Password for user %s must not be too trivial", userId),
				PASSWORD_TOO_TRIVIAL);

			_userId = userId;
		}

		public long getUserId() {
			return _userId;
		}

		private long _userId;

	}

	public static class MustNotHaveTrivialWords extends UserPasswordException {

		public MustNotHaveTrivialWords(long userId) {
			super(
				String.format(
					"Password for user %s must not contain trivial words",
					userId),
				PASSWORD_CONTAINS_TRIVIAL_WORDS);

			_userId = userId;
		}

		public long getUserId() {
			return _userId;
		}

		private long _userId;

	}

	private UserPasswordException(String message, int type) {
		super(message);

		_type = type;
	}

	private final int _type;

}