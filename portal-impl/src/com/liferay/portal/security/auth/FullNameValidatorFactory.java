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
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Amos Fong
 * @author Shuyang Zhou
 */
public class FullNameValidatorFactory {

	public static FullNameValidator getInstance() {
		return _fullNameValidator;
	}

	public static void setInstance(FullNameValidator fullNameValidator) {
		if (_log.isDebugEnabled()) {
			_log.debug("Set " + ClassUtil.getClassName(fullNameValidator));
		}

		if (fullNameValidator == null) {
			fullNameValidator = _createFullNameValidator();
		}

		_fullNameValidator = fullNameValidator;
	}

	public void afterPropertiesSet() {
		_fullNameValidator = _createFullNameValidator();
	}

	private static FullNameValidator _createFullNameValidator() {
		if (_log.isDebugEnabled()) {
			_log.debug("Instantiate " + PropsValues.USERS_FULL_NAME_VALIDATOR);
		}

		FullNameValidator fullNameValidator = null;

		ClassLoader classLoader = PACLClassLoaderUtil.getPortalClassLoader();

		try {
			fullNameValidator =
				(FullNameValidator)InstanceFactory.newInstance(
					classLoader, PropsValues.USERS_FULL_NAME_VALIDATOR);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return fullNameValidator;
	}

	private static Log _log = LogFactoryUtil.getLog(
		FullNameValidatorFactory.class);

	private static volatile FullNameValidator _fullNameValidator;

}