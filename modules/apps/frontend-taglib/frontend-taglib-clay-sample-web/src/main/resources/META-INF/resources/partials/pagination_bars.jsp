<%--
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
--%>

<%@ include file="/init.jsp" %>

<blockquote>
	<p>A pagination bar provides navigation through datasets.</p>
</blockquote>

<h3>Default</h3>

<clay:pagination-bar
	activePage="<%= 1 %>"
	disabledPages="<%= Arrays.asList(5, 6, 7) %>"
	ellipsisBuffer="<%= 3 %>"
	paginationBarDeltas="<%= Arrays.asList(new PaginationBarDelta(10), new PaginationBarDelta(20), new PaginationBarDelta(30), new PaginationBarDelta(50)) %>"
	paginationBarLabels='<%= new PaginationBarLabels("Showing {0} - {1} of {2}", "{0} items", "{0} items") %>'
	totalItems="<%= 100 %>"
/>

<h3>Without DropDown</h3>

<clay:pagination-bar
	activePage="<%= 4 %>"
	ellipsisBuffer="<%= 2 %>"
	paginationBarLabels='<%= new PaginationBarLabels("Showing {0} - {1} of {2}", "{0} items", "{0} items") %>'
	showDeltasDropDown="<%= false %>"
	totalItems="<%= 80 %>"
/>