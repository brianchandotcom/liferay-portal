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

package com.liferay.vulcan.response.control.internal;

import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.vulcan.pagination.Pagination;
import com.liferay.vulcan.provider.Provider;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true)
public class PaginationProvider implements Provider<Pagination> {

	@Override
	public Pagination createContext(HttpServletRequest httpServletRequest) {
		Map<String, String[]> parameterMap =
			httpServletRequest.getParameterMap();

		int itemsPerPage = MapUtil.getInteger(
			parameterMap, "per_page", _ITEMS_PER_PAGE_DEFAULT);
		int pageNumber = MapUtil.getInteger(
			parameterMap, "page", _PAGE_NUMBER_DEFAULT);

		return new DefaultPagination(itemsPerPage, pageNumber);
	}

	private static final int _ITEMS_PER_PAGE_DEFAULT = 30;

	private static final int _PAGE_NUMBER_DEFAULT = 1;

	@Reference
	private Http _http;

	private static class DefaultPagination implements Pagination {

		public DefaultPagination(int itemsPerPage, int pageNumber) {
			_itemsPerPage = itemsPerPage;
			_pageNumber = pageNumber;
		}

		@Override
		public int getEndPosition() {
			return _pageNumber * _itemsPerPage;
		}

		@Override
		public int getItemsPerPage() {
			return _itemsPerPage;
		}

		@Override
		public int getPageNumber() {
			return _pageNumber;
		}

		@Override
		public int getStartPosition() {
			return (_pageNumber - 1) * _itemsPerPage;
		}

		private final int _itemsPerPage;
		private final int _pageNumber;

	}

}