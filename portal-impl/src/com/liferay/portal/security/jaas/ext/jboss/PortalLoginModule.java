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

package com.liferay.portal.security.jaas.ext.jboss;

import com.liferay.portal.kernel.security.jaas.PortalGroup;
import com.liferay.portal.kernel.security.jaas.PortalPrincipal;
import com.liferay.portal.security.jaas.ext.BasicLoginModule;

import java.security.Principal;

import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

/**
 * @author Brian Wing Shun Chan
 * @deprecated As of Cavanaugh (7.4.x), with no replacement
 */
@Deprecated
public class PortalLoginModule extends BasicLoginModule {

	@Override
	public boolean commit() throws LoginException {
		boolean commitValue = super.commit();

		if (commitValue) {
			PortalGroup rolesPortalGroup = new PortalGroup("Roles");

			rolesPortalGroup.addMember(new PortalPrincipal("users"));

			Subject subject = getSubject();

			Set<Principal> principals = subject.getPrincipals();

			principals.add(rolesPortalGroup);

			PortalGroup callerPrincipalGroup = new PortalGroup(
				"CallerPrincipal");

			callerPrincipalGroup.addMember(getPrincipal());

			principals.add(callerPrincipalGroup);
		}

		return commitValue;
	}

}