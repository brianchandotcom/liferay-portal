/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.spring.extension.internal.mvc;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;

/**
 * @author Neil Griffin
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