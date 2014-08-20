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

package com.liferay.portal.security.pwd;

import com.liferay.portal.UserPasswordException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.security.ldap.LDAPSettingsUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author Brian Wing Shun Chan
 */
public class PwdToolkitUtil {

	public static String generate(PasswordPolicy passwordPolicy) {
		Toolkit toolkit = getToolkit();

		return toolkit.generate(passwordPolicy);
	}

	public static Toolkit getToolkit() {
		return _instance._serviceTracker.getService();
	}

	public static void validate(
			long companyId, long userId, String password1, String password2,
			PasswordPolicy passwordPolicy)
		throws PortalException {

		if (!password1.equals(password2)) {
			throw new UserPasswordException(
				UserPasswordException.PASSWORDS_DO_NOT_MATCH);
		}

		if (!LDAPSettingsUtil.isPasswordPolicyEnabled(companyId) &&
			PwdToolkitUtilThreadLocal.isValidate()) {

			Toolkit toolkit = getToolkit();

			toolkit.validate(userId, password1, password2, passwordPolicy);
		}
	}

	private PwdToolkitUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(Toolkit.class);

		_serviceTracker.open();
	}

	private static final PwdToolkitUtil _instance = new PwdToolkitUtil();

	private final ServiceTracker<Toolkit, Toolkit> _serviceTracker;

}