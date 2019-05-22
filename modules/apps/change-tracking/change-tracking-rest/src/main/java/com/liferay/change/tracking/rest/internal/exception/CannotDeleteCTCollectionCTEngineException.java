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

import javax.ws.rs.core.Response;

/**
 * @author Daniel Kocsis
 */
public class CannotDeleteCTCollectionCTEngineException
	extends JaxRsCTEngineException {

	public CannotDeleteCTCollectionCTEngineException(long companyId) {
		super(companyId);

		setResponseStatus(Response.Status.BAD_REQUEST);
	}

	public CannotDeleteCTCollectionCTEngineException(
		long companyId, String msg) {

		super(companyId, msg);

		setResponseStatus(Response.Status.BAD_REQUEST);
	}

}