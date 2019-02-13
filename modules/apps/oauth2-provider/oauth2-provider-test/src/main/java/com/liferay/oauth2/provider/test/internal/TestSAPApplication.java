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

import com.liferay.portal.kernel.security.service.access.policy.ServiceAccessPolicyThreadLocal;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;

/**
 * @author Carlos Sierra Andrés
 */
public class TestSAPApplication extends Application {

	@Override
	public Set<Object> getSingletons() {
		return Collections.<Object>singleton(this);
	}

	@GET
	@Path("/{sapName}")
	public boolean isSapActive(@PathParam("sapName") String sapName) {
		List<String> activeServiceAccessPolicyNames =
			ServiceAccessPolicyThreadLocal.getActiveServiceAccessPolicyNames();

		return activeServiceAccessPolicyNames.contains(sapName);
	}

	@POST
	public String post(String post) {
		return "post";
	}

}