/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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