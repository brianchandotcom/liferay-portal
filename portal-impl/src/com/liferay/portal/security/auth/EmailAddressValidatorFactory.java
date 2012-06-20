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
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Brian Wing Shun Chan
 */
public class EmailAddressValidatorFactory {

	public static EmailAddressValidator getInstance() {
		if (_emailAddressValidator == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Instantiate " + PropsValues.USERS_EMAIL_ADDRESS_VALIDATOR);
			}

			ClassLoader classLoader =
				PACLClassLoaderUtil.getPortalClassLoader();

			try {
				_emailAddressValidator =
					(EmailAddressValidator)classLoader.loadClass(
						PropsValues.USERS_EMAIL_ADDRESS_VALIDATOR)
							.newInstance();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Return " + _emailAddressValidator.getClass().getName());
		}

		return _emailAddressValidator;
	}

	public static void setInstance(
		EmailAddressValidator emailAddressValidator) {

		if (_log.isDebugEnabled()) {
			_log.debug("Set " + emailAddressValidator.getClass().getName());
		}

		_emailAddressValidator = emailAddressValidator;
	}

	private static Log _log = LogFactoryUtil.getLog(
		EmailAddressValidatorFactory.class);

	private static EmailAddressValidator _emailAddressValidator;

}