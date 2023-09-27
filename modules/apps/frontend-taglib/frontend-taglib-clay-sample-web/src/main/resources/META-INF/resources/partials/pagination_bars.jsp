<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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