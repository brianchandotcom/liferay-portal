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

package com.liferay.commerce.frontend.internal.application.context.provider;

import com.liferay.commerce.frontend.Pagination;
import com.liferay.commerce.frontend.PaginationImpl;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = PaginationContextProvider.class)
@Provider
public class PaginationContextProvider implements ContextProvider<Pagination> {

	@Override
	public Pagination createContext(Message message) {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)message.getContextualProperty("HTTP.REQUEST");

		int pageNumber = ParamUtil.getInteger(httpServletRequest, "page", 1);
		int itemsPerPage = ParamUtil.getInteger(
			httpServletRequest, "pageSize", 20);

		return new PaginationImpl(itemsPerPage, pageNumber);
	}

}