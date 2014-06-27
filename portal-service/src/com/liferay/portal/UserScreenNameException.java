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
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.ScreenNameValidator;

/**
 * @author Brian Wing Shun Chan
 */
public class UserScreenNameException extends PortalException {

	/**
	 * @deprecated As of 7.0.0. Use one of the specific inner classes instead.
	 */
	@Deprecated
	public UserScreenNameException() {
		super();
	}

	/**
	 * @deprecated As of 7.0.0. Use one of the specific inner classes instead.
	 */
	@Deprecated
	public UserScreenNameException(String msg) {
		super(msg);
	}

	/**
	 * @deprecated As of 7.0.0. Use one of the specific inner classes instead.
	 */
	@Deprecated
	public UserScreenNameException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * @deprecated As of 7.0.0. Use one of the specific inner classes instead.
	 */
	@Deprecated
	public UserScreenNameException(Throwable cause) {
		super(cause);
	}

	public static class MustBeAlphaNumeric extends UserScreenNameException {

		public MustBeAlphaNumeric(char ... validSpecialChars) {
			super(
				"Screen Name must be alpha-numeric. Only the following " +
					"additional special characters are allowed: " +
						validSpecialChars);
		}
	}

	public static class MustNotBeNull extends UserScreenNameException {

		public MustNotBeNull() {
			super("Screen Name must not be null");
		}

		public MustNotBeNull(String fullName) {
			super("Screen Name must not be null for " + fullName);
		}
	}

	public static class MustNotBeNumeric extends UserScreenNameException {

		public MustNotBeNumeric(String screenName) {
			super(
				"Screen Name " + screenName + " is numeric but the portal " +
					"property " + PropsKeys.USERS_SCREEN_NAME_ALLOW_NUMERIC +
						" is enabled");
		}
	}

	public static class MustNotBeReservedForAnonymous
		extends UserScreenNameException {

		public MustNotBeReservedForAnonymous(String[] reservedScreenNames) {
			super(
				"Screen Name must not be a reserved name for anonymous users " +
					"such as " + StringUtil.merge(reservedScreenNames));

			_reservedScreenNames = reservedScreenNames;
		}

		public String[] getReservedScreenNames() {
			return _reservedScreenNames;
		}

		private final String[] _reservedScreenNames;
	}

	public static class MustNotBeUsedByGroup extends UserScreenNameException {

		public MustNotBeUsedByGroup(String screenName, Group group) {
			super(
				"Screen Name " + screenName + " is already being used by " +
					" group " + group.getGroupId());

			_group = group;
			_screenName = screenName;
		}

		public Group getGroup() {
			return _group;
		}

		public String getScreenName() {
			return _screenName;
		}

		private final Group _group;
		private final String _screenName;
	}

	public static class MustProduceValidFriendlyURL
		extends UserScreenNameException {

		public MustProduceValidFriendlyURL(
			String screenName, int exceptionType) {

			super(
				"Screen Name " + screenName + " cannot be used to build a " +
					"valid friendlyURL",
				new GroupFriendlyURLException(exceptionType));

			_exceptionType = exceptionType;
			_screenName = screenName;
		}

		public int getExceptionType() {
			return _exceptionType;
		}

		public String getScreenName() {
			return _screenName;
		}

		private final int _exceptionType;
		private final String _screenName;
	}

	public static class MustValidate extends UserScreenNameException {

		public MustValidate(
			String screenName, ScreenNameValidator screenNameValidator) {

			super(
				"Screen Name " + screenName + " is not valid according to " +
					ClassUtil.getClassName(screenNameValidator));

			_screenName = screenName;
			_screenNameValidator = screenNameValidator;
		}

		public String getScreenName() {
			return _screenName;
		}

		public ScreenNameValidator getScreenNameValidator() {
			return _screenNameValidator;
		}

		private final String _screenName;
		private final ScreenNameValidator _screenNameValidator;
	}

}