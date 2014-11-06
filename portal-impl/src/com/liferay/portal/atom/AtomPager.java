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

package com.liferay.portal.atom;

import com.liferay.portal.kernel.atom.AtomRequestContext;
import com.liferay.portal.kernel.dao.search.SearchContainer;

import org.apache.abdera.ext.history.FeedPagingHelper;
import org.apache.abdera.model.Feed;

/**
 * @author Igor Spasic
 */
public class AtomPager {

	public AtomPager(AtomRequestContext atomRequestContext, int totalElements) {
		this(
			atomRequestContext.getIntParameter("page"),
			atomRequestContext.getIntParameter(
				"max", SearchContainer.DEFAULT_DELTA),
			totalElements);
	}

	public AtomPager(int page, int totalElements) {
		this(page, SearchContainer.DEFAULT_DELTA, totalElements);
	}

	public AtomPager(int page, int elementsPerPage, int totalElements) {
		_firstPage = 1;

		int lastPage = totalElements / elementsPerPage;

		if ((totalElements % elementsPerPage) > 0) {
			lastPage++;
		}

		if (lastPage == 0) {
			lastPage = 1;
		}

		_lastPage = lastPage;

		if (page < _firstPage) {
			_page = _firstPage;
		}
		else if (page > _lastPage) {
			_page = _lastPage;
		}
		else {
			_page = page;
		}

		_elementsPerPage = elementsPerPage;
		_totalElements = totalElements;

		_start = (_page - 1) * _elementsPerPage;

		int end = _start + _elementsPerPage;

		if (end > totalElements) {
			end = totalElements;
		}

		_end = end - 1;

		_previousPage = _page - 1;

		int nextPage = _page + 1;

		if (nextPage > _lastPage) {
			nextPage = 0;
		}

		_nextPage = nextPage;
	}

	public int getElementsPerPage() {
		return _elementsPerPage;
	}

	public int getEnd() {
		return _end;
	}

	public int getFirstPage() {
		return _firstPage;
	}

	public int getLastPage() {
		return _lastPage;
	}

	public int getNextPage() {
		return _nextPage;
	}

	public int getPage() {
		return _page;
	}

	public int getPreviousPage() {
		return _previousPage;
	}

	public int getStart() {
		return _start;
	}

	public int getTotalElements() {
		return _totalElements;
	}

	public void setFeedPagingLinks(Feed feed, String url) {
		FeedPagingHelper.setFirst(feed, AtomUtil.setPageInUrl(url, 1));
		FeedPagingHelper.setLast(feed, AtomUtil.setPageInUrl(url, _lastPage));

		if (_previousPage != 0) {
			FeedPagingHelper.setPrevious(
				feed, AtomUtil.setPageInUrl(url, _previousPage));
		}

		if (_nextPage != 0) {
			FeedPagingHelper.setNext(
				feed, AtomUtil.setPageInUrl(url, _nextPage));
		}
	}

	private final int _elementsPerPage;
	private final int _end;
	private final int _firstPage;
	private final int _lastPage;
	private final int _nextPage;
	private final int _page;
	private final int _previousPage;
	private final int _start;
	private final int _totalElements;

}