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

package com.liferay.portal.vulcan.internal.context.provider;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.internal.context.provider.base.BaseContextProvider;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ext.Provider;

/**
 * Allows JAX-RS resources to provide {@link Pagination} objects in method
 * parameters, fields or setters by annotating them with {@code
 * javax.ws.rs.core.Context}.
 *
 * @author Alejandro Hernández
 * @author Zoltán Takács
 * @review
 */
@Provider
public class PaginationContextProvider extends BaseContextProvider<Pagination> {

	@Override
	public Pagination createContext(HttpServletRequest request) {
		int itemsPerPage = ParamUtil.getInteger(request, "pageSize", 20);
		int pageNumber = ParamUtil.getInteger(request, "page", 1);

		return Pagination.of(itemsPerPage, pageNumber);
	}

}