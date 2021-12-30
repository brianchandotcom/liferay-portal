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

package com.liferay.frontend.data.set.taglib.internal.jaxrs.context.provider;

import com.liferay.frontend.data.set.provider.search.FDSPagination;
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
public class PaginationContextProvider
	implements ContextProvider<FDSPagination> {

	@Override
	public FDSPagination createContext(Message message) {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)message.getContextualProperty("HTTP.REQUEST");

		int page = ParamUtil.getInteger(httpServletRequest, "page", 1);
		int pageSize = ParamUtil.getInteger(httpServletRequest, "pageSize", 20);

		return new FDSPaginationImpl(page, pageSize);
	}

	private class FDSPaginationImpl implements FDSPagination {

		public FDSPaginationImpl(int page, int pageSize) {
			_page = page;
			_pageSize = pageSize;
		}

		@Override
		public int getEndPosition() {
			return _page * _pageSize;
		}

		@Override
		public int getPage() {
			return _page;
		}

		@Override
		public int getPageSize() {
			return _pageSize;
		}

		@Override
		public int getStartPosition() {
			return (_page - 1) * _pageSize;
		}

		private final int _page;
		private final int _pageSize;

	}

}