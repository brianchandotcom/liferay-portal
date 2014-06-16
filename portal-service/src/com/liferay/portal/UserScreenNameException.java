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
import com.liferay.portal.security.auth.ScreenNameValidator;

/**
 * @author Brian Wing Shun Chan
 */
public class UserScreenNameException extends PortalException {

	@Deprecated
	public UserScreenNameException() {
		super();
	}

	public UserScreenNameException(String msg) {
		super(msg);
	}

	public UserScreenNameException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public UserScreenNameException(Throwable cause) {
		super(cause);
	}

	public static class MustNotBeNull extends UserScreenNameException {

		public MustNotBeNull() {
			super("Screen Name must not be null");
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

	public static class MustValidate extends UserScreenNameException {

		public MustValidate(
			String screenName, ScreenNameValidator screenNameValidator) {

			super(
				"Invalid email address " + screenName + " according to " +
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