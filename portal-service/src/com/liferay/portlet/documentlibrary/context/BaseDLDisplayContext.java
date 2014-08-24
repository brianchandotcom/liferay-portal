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

package com.liferay.portlet.documentlibrary.context;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author Iván Zaera
 */
public abstract class BaseDLDisplayContext<T extends DLDisplayContext>
	implements DLDisplayContext {

	public BaseDLDisplayContext(
		UUID uuid, T parentDLDisplayContext, PageContext pageContext) {

		_uuid = uuid;
		this.parentDLDisplayContext = parentDLDisplayContext;
		this.pageContext = pageContext;
		this.request = (HttpServletRequest)pageContext.getRequest();
		this.response = (HttpServletResponse)pageContext.getResponse();
	}

	@Override
	public UUID getUuid() {
		return _uuid;
	}

	protected PageContext pageContext;
	protected T parentDLDisplayContext;
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	private UUID _uuid;

}