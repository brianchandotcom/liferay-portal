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

package com.liferay.vulcan.provider.pagination;

import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.vulcan.pagination.Page;
import com.liferay.vulcan.pagination.PaginationParams;

import java.util.Collection;
import java.util.Map;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Provider
public class PaginationParamsContextProvider
	implements ContextProvider<PaginationParams> {

	public static final int DEFAULT_ITEMS_PER_PAGE = 30;

	public static final int DEFAULT_PAGE = 1;

	@Override
	public PaginationParams createContext(Message message) {
		String queryString = (String)message.getContextualProperty(
			Message.QUERY_STRING);

		Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
			queryString);

		int itemsPerPage = MapUtil.getInteger(
			parameterMap, "per_page", DEFAULT_ITEMS_PER_PAGE);

		int page = MapUtil.getInteger(parameterMap, "page", DEFAULT_PAGE);

		return new DefaultPaginationParams(itemsPerPage, page);
	}

	private class DefaultPage<T> implements Page<T> {

		public DefaultPage(
			Collection<T> items, int itemsPerPage, int pageNumber,
			int totalCount) {

			_items = items;
			_itemsPerPage = itemsPerPage;
			_pageNumber = pageNumber;
			_totalCount = totalCount;
		}

		@Override
		public Collection<T> getItems() {
			return _items;
		}

		@Override
		public int getItemsPerPage() {
			return _itemsPerPage;
		}

		@Override
		public int getLastPageNumber() {
			return -Math.floorDiv(-_totalCount, _itemsPerPage);
		}

		@Override
		public int getPageNumber() {
			return _pageNumber;
		}

		@Override
		public int getTotalCount() {
			return _totalCount;
		}

		@Override
		public boolean hasNext() {
			if (getLastPageNumber() > _pageNumber) {
				return true;
			}

			return false;
		}

		@Override
		public boolean hasPrevious() {
			if (_pageNumber > 1) {
				return true;
			}

			return false;
		}

		private final Collection<T> _items;
		private final int _itemsPerPage;
		private final int _pageNumber;
		private final int _totalCount;

	}

	private class DefaultPaginationParams implements PaginationParams {

		public DefaultPaginationParams(int itemsPerPage, int page) {
			_itemsPerPage = itemsPerPage;
			_page = page;
		}

		@Override
		public <T> Page<T> createPage(Collection<T> items, int totalCount) {
			return new DefaultPage<>(
				items, getItemsPerPage(), getPage(), totalCount);
		}

		@Override
		public int getEndPosition() {
			return _page * _itemsPerPage;
		}

		@Override
		public int getItemsPerPage() {
			return _itemsPerPage;
		}

		@Override
		public int getPage() {
			return _page;
		}

		@Override
		public int getStartPosition() {
			return (_page - 1) * _itemsPerPage;
		}

		private final int _itemsPerPage;
		private final int _page;

	}

}