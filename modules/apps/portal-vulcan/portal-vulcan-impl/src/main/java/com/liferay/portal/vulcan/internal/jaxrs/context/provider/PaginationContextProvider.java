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

package com.liferay.portal.vulcan.internal.jaxrs.context.provider;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

/**
 * @author Zoltán Takács
 */
@Provider
public class PaginationContextProvider implements ContextProvider<Pagination> {

	@Override
	public Pagination createContext(Message message) {
		HttpServletRequest httpServletRequest =
			ContextProviderUtil.getHttpServletRequest(message);

		String page = httpServletRequest.getParameter("page");
		String pageSize = httpServletRequest.getParameter("pageSize");

		if (StringUtil.equals(page, "0") || StringUtil.equals(pageSize, "0")) {
			return null;
		}

		return Pagination.of(
			GetterUtil.getInteger(page, 1),
			GetterUtil.getInteger(pageSize, 20));
	}

}