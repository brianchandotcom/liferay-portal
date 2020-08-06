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

package com.liferay.portal.remote.cors.client.test;

import com.liferay.portal.remote.cors.annotation.CORS;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

/**
 * @author Marta Medio
 */
@CORS
@Path("/cors-app")
public class CORSTestApplication extends Application {

	@Override
	public Set<Object> getSingletons() {
		return Collections.<Object>singleton(this);
	}

	@GET
	public String getString() {
		return "get";
	}

	@GET
	@Path("/duplicate/path/whatever")
	public String getTestPath1() {
		return "/duplicate/path/whatever";
	}

	@GET
	@Path("/instance/only/path/whatever")
	public String getTestPath2() {
		return "/instance/only/path/whatever";
	}

	@GET
	@Path("/overwritten/path/whatever")
	public String getTestPath3() {
		return "/overwritten/path/whatever";
	}

	@GET
	@Path("/system/only/path/whatever")
	public String getTestPath4() {
		return "/system/only/path/whatever";
	}

}