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

<%
CPSearchResultsDisplayContext cpSearchResultsDisplayContext = (CPSearchResultsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CPCatalogEntry> cpCatalogEntrySearchContainer = cpSearchResultsDisplayContext.getSearchContainer();
%>

<div class="m-0 mb-3 row">
	<div class="d-flex ml-auto pull-right">
		<p class="mb-auto mr-3 mt-auto">
			<liferay-ui:message arguments="<%= cpCatalogEntrySearchContainer.getTotal() %>" key="x-products-available" />
		</p>

		<button aria-expanded="false" aria-haspopup="true" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" id="commerce-order-by" type="button">
			<c:set var="orderByColArgument">
				<span class="ml-1">
					<liferay-ui:message key="<%= cpSearchResultsDisplayContext.getOrderByCol() %>" />
				</span>
			</c:set>

			<liferay-ui:message arguments="${orderByColArgument}" key="sort-by-colon-x" />

			<clay:icon
				symbol="caret-double-l"
			/>
		</button>

		<div class="dropdown-menu dropdown-menu-right" id="<portlet:namespace />commerce-dropdown-order-by">

			<%
			String[] sortOptions = CPSearchResultsConstants.SORT_OPTIONS;

			for (String sortOption : sortOptions) {
			%>

				<clay:link
					cssClass="dropdown-item transition-link sortWidgetOptions"
					data-label="<%= sortOption %>"
					href="#"
					id="<%= liferayPortletResponse.getNamespace() + sortOption %>"
					label="<%= LanguageUtil.get(request, sortOption) %>"
					style="secondary"
				/>

			<%
			}
			%>

		</div>
	</div>
</div>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"currentURL", themeDisplay.getURLCurrent()
		).put(
			"portletDisplayId", portletDisplay.getId()
		).build()
	%>'
	module="js/sort/view"
/>