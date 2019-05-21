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

package com.liferay.bean.portlet.spring.extension.internal.mvc;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;

/**
 * @author  Neil Griffin
 */
public class RequestImpl implements Request {

	@Override
	public Response.ResponseBuilder evaluatePreconditions() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Response.ResponseBuilder evaluatePreconditions(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Response.ResponseBuilder evaluatePreconditions(
		Date date, EntityTag entityTag) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Response.ResponseBuilder evaluatePreconditions(EntityTag entityTag) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getMethod() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Variant selectVariant(List<Variant> list) {
		throw new UnsupportedOperationException();
	}

}