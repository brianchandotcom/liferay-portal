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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class FullNameGeneratorFactory {

	public static FullNameGenerator getInstance() {
		return _fullNameGenerator;
	}

	public static void setInstance(FullNameGenerator fullNameGenerator) {
		if (_log.isDebugEnabled()) {
			_log.debug("Set " + ClassUtil.getClassName(fullNameGenerator));
		}

		if (fullNameGenerator == null) {
			_fullNameGenerator = _originalFullNameGenerator;
		}
		else {
			_fullNameGenerator = fullNameGenerator;
		}
	}

	public void afterPropertiesSet() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Instantiate " + PropsValues.USERS_FULL_NAME_GENERATOR);
		}

		ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

		_originalFullNameGenerator =
			(FullNameGenerator)InstanceFactory.newInstance(
				classLoader, PropsValues.USERS_FULL_NAME_GENERATOR);

		_fullNameGenerator = _originalFullNameGenerator;
	}

	private static Log _log = LogFactoryUtil.getLog(
		FullNameGeneratorFactory.class);

	private static volatile FullNameGenerator _fullNameGenerator;
	private static FullNameGenerator _originalFullNameGenerator;

}