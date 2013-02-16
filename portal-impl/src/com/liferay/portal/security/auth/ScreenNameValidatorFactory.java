/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ScreenNameValidatorFactory {

	public static ScreenNameValidator getInstance() {
		return _screenNameValidator;
	}

	public static void setInstance(ScreenNameValidator screenNameValidator) {
		if (_log.isDebugEnabled()) {
			_log.debug("Set " + ClassUtil.getClassName(screenNameValidator));
		}

		if (screenNameValidator == null) {
			_screenNameValidator = _originalScreenNameValidator;
		}
		else {
			_screenNameValidator = screenNameValidator;
		}
	}

	public void afterPropertiesSet() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Instantiate " + PropsValues.USERS_SCREEN_NAME_VALIDATOR);
		}

		ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

		_originalScreenNameValidator =
			(ScreenNameValidator)InstanceFactory.newInstance(
				classLoader, PropsValues.USERS_SCREEN_NAME_VALIDATOR);

		_screenNameValidator = _originalScreenNameValidator;
	}

	private static Log _log = LogFactoryUtil.getLog(
		ScreenNameValidatorFactory.class);

	private static ScreenNameValidator _originalScreenNameValidator;
	private static volatile ScreenNameValidator _screenNameValidator;

}