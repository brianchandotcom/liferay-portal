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

package com.liferay.portal.apio.internal.architect.provider;

import com.liferay.apio.architect.provider.Provider;
import com.liferay.portal.apio.architect.context.user.CurrentUser;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.UserService;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ForbiddenException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Lets resources provide the current {@code
 * com.liferay.portal.kernel.model.User} as a parameter in the methods of the
 * different routes builders.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true)
public class CurrentUserProvider implements Provider<CurrentUser> {

	@Override
	public CurrentUser createContext(HttpServletRequest httpServletRequest) {
		return () -> {
			try {
				return _userService.getCurrentUser();
			}
			catch (PortalException pe) {
				throw new ForbiddenException(
					"Unable to get authenticated user", pe);
			}
		};
	}

	@Reference
	private UserService _userService;

}