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

package com.liferay.vulcan.liferay.portal.internal.provider;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.vulcan.liferay.portal.context.CurrentUser;
import com.liferay.vulcan.provider.Provider;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Allows resources to provide the current {@link
 * com.liferay.portal.kernel.model.User} as a parameter in {@link
 * com.liferay.vulcan.resource.builder.RoutesBuilder} methods.
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
				throw new NotFoundException(pe);
			}
		};
	}

	@Reference
	private UserService _userService;

}