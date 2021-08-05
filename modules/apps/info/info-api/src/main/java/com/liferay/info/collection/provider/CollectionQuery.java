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

package com.liferay.info.collection.provider;

import com.liferay.info.filter.InfoFilter;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;

import java.util.Map;
import java.util.Optional;

/**
 * @author Jorge Ferrer
 * @author Eudaldo Alonso
 */
public class CollectionQuery {

	public Optional<Map<String, String[]>> getConfigurationOptional() {
		return Optional.ofNullable(_configuration);
	}

	public Optional<InfoFilter> getInfoFilterOptional() {
		return Optional.ofNullable(_infoFilter);
	}

	public Pagination getPagination() {
		if (_pagination == null) {
			return Pagination.of(20, 0);
		}

		return _pagination;
	}

	public Optional<Object> getRelatedItemObjectOptional() {
		return Optional.ofNullable(_relatedItemObject);
	}

	public Optional<Sort> getSortOptional() {
		return Optional.ofNullable(_sort);
	}

	public void setConfiguration(Map<String, String[]> configuration) {
		_configuration = configuration;
	}

	public void setInfoFilter(InfoFilter infoFilter) {
		_infoFilter = infoFilter;
	}

	public void setPagination(Pagination pagination) {
		_pagination = pagination;
	}

	public void setRelatedItemObject(Object relatedItemObject) {
		_relatedItemObject = relatedItemObject;
	}

	public void setSort(Sort sort) {
		_sort = sort;
	}

	private Map<String, String[]> _configuration;
	private InfoFilter _infoFilter;
	private Pagination _pagination;
	private Object _relatedItemObject;
	private Sort _sort;

}