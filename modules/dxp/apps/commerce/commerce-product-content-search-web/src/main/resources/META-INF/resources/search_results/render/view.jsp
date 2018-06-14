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

SearchContainer searchContainer = cpSearchResultsDisplayContext.getSearchContainer();

List<CPCatalogEntry> results = searchContainer.getResults();
%>

<c:choose>
	<c:when test="<%= results.size() > 0 %>">
		<div class="row">

			<%
			for (Object object : results) {
				CPCatalogEntry cpCatalogEntry = (CPCatalogEntry)object;

				request.setAttribute("search_result.jsp-cpContentListRenderer-cpCatalogEntry", cpCatalogEntry);

				cpSearchResultsDisplayContext.renderCPContentListEntry(cpCatalogEntry);
			}
			%>

		</div>
	</c:when>
	<c:otherwise>
		<div class="alert alert-info">
			<liferay-ui:message key="no-products-were-found" />
		</div>
	</c:otherwise>
</c:choose>

<aui:form useNamespace="<%= false %>">
	<liferay-ui:search-paginator
		markupView="lexicon"
		searchContainer="<%= searchContainer %>"
		type="more"
	/>
</aui:form>