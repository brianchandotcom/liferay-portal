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
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ScreenNameGeneratorFactory {

	public static ScreenNameGenerator getInstance() {
		return _screenNameGenerator;
	}

	public static void setInstance(ScreenNameGenerator screenNameGenerator) {
		if (_log.isDebugEnabled()) {
			_log.debug("Set " + ClassUtil.getClassName(screenNameGenerator));
		}

		if (screenNameGenerator == null) {
			screenNameGenerator = _createScreenNameGenerator();
		}

		_screenNameGenerator = screenNameGenerator;
	}

	public void afterPropertiesSet() {
		_screenNameGenerator = _createScreenNameGenerator();
	}

	private static ScreenNameGenerator _createScreenNameGenerator() {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Instantiate " + PropsValues.USERS_SCREEN_NAME_GENERATOR);
		}

		ScreenNameGenerator screenNameGenerator = null;

		ClassLoader classLoader = PACLClassLoaderUtil.getPortalClassLoader();

		try {
			screenNameGenerator =
				(ScreenNameGenerator)InstanceFactory.newInstance(
					classLoader, PropsValues.USERS_SCREEN_NAME_GENERATOR);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return screenNameGenerator;
	}

	private static Log _log = LogFactoryUtil.getLog(
		ScreenNameGeneratorFactory.class);

	private static volatile ScreenNameGenerator _screenNameGenerator;

}