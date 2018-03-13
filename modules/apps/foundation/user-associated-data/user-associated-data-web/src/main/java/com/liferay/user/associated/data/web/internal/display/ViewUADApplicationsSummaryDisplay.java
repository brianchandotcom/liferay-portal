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

package com.liferay.user.associated.data.web.internal.display;

import com.liferay.portal.kernel.dao.search.SearchContainer;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Drew Brokke
 */
public class ViewUADApplicationsSummaryDisplay {

	public SearchContainer<UADApplicationSummaryDisplay> getSearchContainer() {
		return _searchContainer;
	}

	public int getTotalCount() {
		List<UADApplicationSummaryDisplay> uadApplicationSummaryDisplays =
			_searchContainer.getResults();

		Stream<UADApplicationSummaryDisplay>
			uadApplicationSummaryDisplaysStream =
				uadApplicationSummaryDisplays.stream();

		return uadApplicationSummaryDisplaysStream.mapToInt(
			UADApplicationSummaryDisplay::getCount
		).sum();
	}

	public void setSearchContainer(
		SearchContainer<UADApplicationSummaryDisplay> searchContainer) {

		_searchContainer = searchContainer;
	}

	private SearchContainer<UADApplicationSummaryDisplay> _searchContainer;

}