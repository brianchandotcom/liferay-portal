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

package com.liferay.portal.security.jaas.ext.weblogic;

import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.security.jaas.ext.BasicLoginModule;

import java.security.Principal;

import javax.security.auth.login.LoginException;

/**
 * @author Brian Wing Shun Chan
 * @deprecated As of Cavanaugh (7.4.x), with no replacement
 */
@Deprecated
public class PortalLoginModule extends BasicLoginModule {

	@Override
	protected Principal getPortalPrincipal(String name) throws LoginException {
		try {
			return (Principal)InstanceFactory.newInstance(
				_WLS_USER_IMPL, String.class, name);
		}
		catch (Exception exception) {
			throw new LoginException(exception.getMessage());
		}
	}

	private static final String _WLS_USER_IMPL =
		"weblogic.security.principal.WLSUserImpl";

}