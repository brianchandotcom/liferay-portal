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
 * @author Amos Fong
 * @author Shuyang Zhou
 */
public class EmailAddressGeneratorFactory {

	public static EmailAddressGenerator getInstance() {
		return _emailAddressGenerator;
	}

	public static void setInstance(
		EmailAddressGenerator emailAddressGenerator) {

		if (_log.isDebugEnabled()) {
			_log.debug("Set " + ClassUtil.getClassName(emailAddressGenerator));
		}

		if (emailAddressGenerator == null) {
			_emailAddressGenerator = _originalEmailAddressGenerator;
		}
		else {
			_emailAddressGenerator = emailAddressGenerator;
		}
	}

	public void afterPropertiesSet() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Instantiate " + PropsValues.USERS_EMAIL_ADDRESS_GENERATOR);
		}

		ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

		_originalEmailAddressGenerator =
			(EmailAddressGenerator)InstanceFactory.newInstance(
				classLoader, PropsValues.USERS_EMAIL_ADDRESS_GENERATOR);

		_emailAddressGenerator = _originalEmailAddressGenerator;
	}

	private static Log _log = LogFactoryUtil.getLog(
		EmailAddressGeneratorFactory.class);

	private static volatile EmailAddressGenerator _emailAddressGenerator;
	private static EmailAddressGenerator _originalEmailAddressGenerator;

}