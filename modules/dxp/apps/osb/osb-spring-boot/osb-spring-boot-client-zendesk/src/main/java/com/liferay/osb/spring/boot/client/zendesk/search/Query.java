/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.zendesk.search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Amos Fong
 */
public class Query {

	public void addCriterion(String criterion) {
		_criteria.add(criterion);
	}

	public void addParameter(String key, String value) {
		_parameters.put(key, value);
	}

	public void addSideload(String sideload) {
		_sideloads.add(sideload);
	}

	public int getPage() {
		return _page;
	}

	public Map<String, String> getParameters() {
		Map<String, String> parameters = new HashMap<>();

		if (!_parameters.isEmpty()) {
			parameters.putAll(_parameters);
		}

		if (!_sideloads.isEmpty()) {
			parameters.put("include", getSideLoads());
		}

		if (_page > 0) {
			parameters.put("page", String.valueOf(_page));
		}

		if (!_criteria.isEmpty()) {
			parameters.put("query", getQuery());
		}

		if (_sortBy != null) {
			parameters.put("sort_by", _sortBy);

			if (_sortOrder != null) {
				parameters.put("sort_order", _sortOrder);
			}
		}

		return parameters;
	}

	public void setPage(int page) {
		_page = page;
	}

	public void setSortBy(String sortBy) {
		_sortBy = sortBy;
	}

	public void setSortOrder(boolean asc) {
		if (asc) {
			_sortOrder = "asc";
		}
		else {
			_sortOrder = "desc";
		}
	}

	protected String getQuery() {
		StringBuilder sb = new StringBuilder();

		for (String criterion : _criteria) {
			if (sb.length() != 0) {
				sb.append(" ");
			}

			sb.append(criterion);
		}

		return sb.toString();
	}

	protected String getSideLoads() {
		StringBuilder sb = new StringBuilder();

		for (String sideload : _sideloads) {
			if (sb.length() != 0) {
				sb.append(",");
			}

			sb.append(sideload);
		}

		return sb.toString();
	}

	private final Set<String> _criteria = new HashSet<>();
	private int _page;
	private final Map<String, String> _parameters = new HashMap<>();
	private final Set<String> _sideloads = new HashSet<>();
	private String _sortBy;
	private String _sortOrder;

}