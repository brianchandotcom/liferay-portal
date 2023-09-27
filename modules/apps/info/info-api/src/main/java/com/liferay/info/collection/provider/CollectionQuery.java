/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.collection.provider;

import com.liferay.info.filter.InfoFilter;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;

/**
 * @author Jorge Ferrer
 * @author Eudaldo Alonso
 */
public class CollectionQuery {

	public Map<String, String[]> getConfiguration() {
		return _configuration;
	}

	public <T> T getInfoFilter(Class<? extends InfoFilter> clazz) {
		if (MapUtil.isEmpty(_infoFilters)) {
			return null;
		}

		InfoFilter infoFilter = _infoFilters.getOrDefault(
			clazz.getName(), null);

		if (infoFilter != null) {
			return (T)infoFilter;
		}

		return null;
	}

	public Pagination getPagination() {
		if (_pagination == null) {
			return Pagination.of(20, 0);
		}

		return _pagination;
	}

	public Object getRelatedItem() {
		return _relatedItemObject;
	}

	public Sort getSort() {
		return _sort;
	}

	public void setConfiguration(Map<String, String[]> configuration) {
		_configuration = configuration;
	}

	public void setInfoFilters(Map<String, InfoFilter> infoFilters) {
		_infoFilters = infoFilters;
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
	private Map<String, InfoFilter> _infoFilters;
	private Pagination _pagination;
	private Object _relatedItemObject;
	private Sort _sort;

}