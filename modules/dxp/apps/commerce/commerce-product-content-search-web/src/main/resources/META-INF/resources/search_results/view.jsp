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

Map<String, Object> contextObjects = new HashMap<>();

contextObjects.put("cpSearchResultsDisplayContext", cpSearchResultsDisplayContext);

SearchContainer searchContainer = cpSearchResultsDisplayContext.getSearchContainer();
%>

<liferay-ddm:template-renderer
	className="<%= CPSearchResultsPortlet.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= cpSearchResultsDisplayContext.getDisplayStyle() %>"
	displayStyleGroupId="<%= cpSearchResultsDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= searchContainer.getResults() %>"
>
	<div class="row">

	<%
	for (Object object : searchContainer.getResults()) {
		Document document = (Document)object;
	%>

		<div class="col-md-4">
			<div class="card">
				<div class="aspect-ratio aspect-ratio-center aspect-ratio-vertical">

					<%
					String img = cpSearchResultsDisplayContext.getProductDefaultImage(document, themeDisplay);
					%>

					<c:if test="<%= Validator.isNotNull(img) %>">
						<img src="<%= img %>">
					</c:if>
				</div>
				<div class="card-row card-row-padded card-row-valign-top">
					<div class="card-col-content">
						<a href="<%= cpSearchResultsDisplayContext.getProductFriendlyURL(themeDisplay.getPortalURL(), document) %>">
							<%= cpSearchResultsDisplayContext.getTitle(document) %>
						</a>
					</div>
				</div>
			</div>
		</div>

	<%
	}
	%>

	</div>

</liferay-ddm:template-renderer>

<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />