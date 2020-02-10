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

package com.liferay.mule.internal.connection.config;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Password;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

/**
 * @author Matija Petanjek
 */
public class BasicAuthenticationConfig {

	public String getPassword() {
		return _password;
	}

	public String getUsername() {
		return _username;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public void setUsername(String username) {
		_username = username;
	}

	@DisplayName("Password")
	@Parameter
	@Password
	@Placement(order = 2)
	private String _password;

	@DisplayName("Username")
	@Expression(ExpressionSupport.NOT_SUPPORTED)
	@Parameter
	@Placement(order = 1)
	private String _username;

}