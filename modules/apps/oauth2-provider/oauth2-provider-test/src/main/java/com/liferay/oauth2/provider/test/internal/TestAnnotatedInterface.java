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

package com.liferay.oauth2.provider.test.internal;

import com.liferay.oauth2.provider.scope.RequiresNoScope;
import com.liferay.oauth2.provider.scope.RequiresScope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
* @author Carlos Sierra Andrés
*/
public interface TestAnnotatedInterface {

	@GET
	@RequestScopeRead
	public String getString();

	@GET
	@Path("/no-scope")
	@RequiresNoScope
	public String getStringNoScope();

	@RequiresScope("everything.read")
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface RequestScopeRead {
	}

}