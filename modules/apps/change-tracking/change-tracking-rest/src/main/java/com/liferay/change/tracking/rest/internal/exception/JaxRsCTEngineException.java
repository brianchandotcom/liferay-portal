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

package com.liferay.change.tracking.rest.internal.exception;

import com.liferay.change.tracking.engine.exception.CTEngineException;

import javax.ws.rs.core.Response;

/**
 * @author Máté Thurzó
 */
public class JaxRsCTEngineException extends CTEngineException {

	public JaxRsCTEngineException(long companyId) {
		super(companyId);
	}

	public JaxRsCTEngineException(long companyId, String msg) {
		super(companyId, msg);
	}

	public JaxRsCTEngineException(long companyId, String msg, Throwable cause) {
		super(companyId, msg, cause);
	}

	public JaxRsCTEngineException(long companyId, Throwable cause) {
		super(companyId, cause);
	}

	public Response.Status getResponseStatus() {
		return responseStatus;
	}

	public int getResponseStatusCode() {
		return responseStatusCode;
	}

	public void setResponseStatus(Response.Status responseStatus) {
		this.responseStatus = responseStatus;
		responseStatusCode = responseStatus.getStatusCode();
	}

	protected Response.Status responseStatus;
	protected int responseStatusCode;

}