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

package com.liferay.headless.foundation.resource;

import com.liferay.headless.foundation.dto.Phone;
import com.liferay.headless.foundation.dto.PhoneCollection;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.vulcan.context.Pagination;

import javax.annotation.Generated;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-foundation/1.0.0/phone
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/1.0.0/phone")
public interface PhoneResource {

	@GET
	@Produces("application/json")
	@RequiresScope("headless-foundation-application.read")
	public PhoneCollection<Phone> getPhoneCollection(
			@Context Pagination pagination, @QueryParam("size") String size)
		throws Exception;

}