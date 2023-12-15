/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.zendesk.search;

import java.util.List;

/**
 * @author Amos Fong
 */
public class SearchHits<T> {

	public int getCount() {
		return _count;
	}

	public int getNextPage() {
		return _nextPage;
	}

	public List<T> getResults() {
		return _results;
	}

	public boolean hasNextPage() {
		if (_nextPage > 0) {
			return true;
		}

		return false;
	}

	public void setCount(int count) {
		_count = count;
	}

	public void setNextPage(int nextPage) {
		_nextPage = nextPage;
	}

	public void setResults(List<T> results) {
		_results = results;
	}

	private int _count;
	private int _nextPage;
	private List<T> _results;

}