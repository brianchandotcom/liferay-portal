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

<%@ include file="/publications/init.jsp" %>

<%
ViewHistoryDisplayContext viewHistoryDisplayContext = (ViewHistoryDisplayContext)request.getAttribute(CTWebKeys.VIEW_HISTORY_DISPLAY_CONTEXT);

SearchContainer<CTProcess> searchContainer = viewHistoryDisplayContext.getSearchContainer();
%>

<clay:navigation-bar
	navigationItems="<%= viewHistoryDisplayContext.getViewNavigationItems() %>"
/>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new ViewHistoryManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, searchContainer, viewHistoryDisplayContext) %>"
/>

<clay:container-fluid>
	<div class="container-view">
		<c:choose>
			<c:when test="<%= !searchContainer.hasResults() && viewHistoryDisplayContext.isSearch() %>">
				<liferay-ui:empty-result-message
					message="no-publication-has-been-published-yet"
					search="<%= true %>"
				/>
			</c:when>
			<c:when test="<%= !searchContainer.hasResults() %>">
				<liferay-ui:empty-result-message
					message="no-publication-has-been-published-yet"
				/>
			</c:when>
			<c:otherwise>
				<div>
					<span aria-hidden="true" class="loading-animation"></span>

					<react:component
						module="publications/js/PublicationsHistoryView"
						props="<%= viewHistoryDisplayContext.getReactProps() %>"
					/>
				</div>
			</c:otherwise>
		</c:choose>

		<liferay-ui:search-paginator
			markupView="lexicon"
			searchContainer="<%= searchContainer %>"
		/>
	</div>
</clay:container-fluid>