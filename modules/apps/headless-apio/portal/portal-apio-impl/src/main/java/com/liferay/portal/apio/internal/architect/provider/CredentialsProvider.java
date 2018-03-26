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

import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.provider.Provider;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * Lets resources provide the requested {@link Credentials} as a parameter in
 * the methods of any of the routes builders.
 *
 * <p>
 * This provider is mandatory for any APIO application.
 * </p>
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class CredentialsProvider implements Provider<Credentials> {

	@Override
	public Credentials createContext(HttpServletRequest httpServletRequest) {
		return () -> httpServletRequest.getHeader("Authorization");
	}

}