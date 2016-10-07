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

package com.liferay.portal.kernel.dao.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergio González
 */
public class SearchContainerResults<R> {

	public SearchContainerResults() {
	}

	public SearchContainerResults(List<R> results, int total) {
		_results = results;
		_total = total;
	}

	public List<R> getResults() {
		return _results;
	}

	public int getTotal() {
		return _total;
	}

	public void setResults(List<R> results) {
		_results = results;
	}

	public void setTotal(int total) {
		_total = total;
	}

	private List<R> _results = new ArrayList<>();
	private int _total;

}