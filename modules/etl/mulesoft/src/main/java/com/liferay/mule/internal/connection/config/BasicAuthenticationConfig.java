/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
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

import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Password;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

/**
 * @author Matija Petanjek
 */
public class BasicAuthenticationConfig {

	public String getOpenApiSpecPath() {
		return openApiSpecPath;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	@DisplayName("OpenAPI Spec URL")
	@Parameter
	@Placement(order = 1)
	private String openApiSpecPath;

	@DisplayName("Password")
	@Parameter
	@Password
	@Placement(order = 3)
	private String password;

	@DisplayName("Username")
	@Parameter
	@Placement(order = 2)
	private String username;

}