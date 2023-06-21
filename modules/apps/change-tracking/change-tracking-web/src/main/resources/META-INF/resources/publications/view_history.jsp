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
%>

<clay:navigation-bar
	navigationItems="<%= viewHistoryDisplayContext.getViewNavigationItems() %>"
/>

<c:choose>
	<c:when test='<%= FeatureFlagManagerUtil.isEnabled("LPS-180155") %>'>
		<clay:container-fluid>
			<frontend-data-set:headless-display
				apiURL="<%= viewHistoryDisplayContext.getAPIURL() %>"
				fdsActionDropdownItems="<%= viewHistoryDisplayContext.getFDSActionDropdownItems() %>"
				id="<%= PublicationsFDSNames.PUBLICATIONS_HISTORY %>"
				style="stacked"
			/>
		</clay:container-fluid>
	</c:when>
	<c:otherwise>

		<%
		SearchContainer<CTProcess> searchContainer = viewHistoryDisplayContext.getSearchContainer();
		%>

		<clay:management-toolbar
			managementToolbarDisplayContext="<%= new ViewHistoryManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, searchContainer, viewHistoryDisplayContext) %>"
		/>

		<clay:container-fluid>
			<div class="container-view">
				<c:choose>
					<c:when test="<%= !searchContainer.hasResults() && viewHistoryDisplayContext.isSearch() %>">
						<liferay-frontend:empty-result-message
							animationType="<%= EmptyResultMessageKeys.AnimationType.SEARCH %>"
							title='<%= LanguageUtil.get(resourceBundle, "no-publication-has-been-published-yet") %>'
						/>
					</c:when>
					<c:when test="<%= !searchContainer.hasResults() %>">
						<liferay-frontend:empty-result-message
							title='<%= LanguageUtil.get(resourceBundle, "no-publication-has-been-published-yet") %>'
						/>
					</c:when>
					<c:otherwise>
						<div>
							<span aria-hidden="true" class="loading-animation"></span>

							<react:component
								module="publications/js/views/PublicationsHistoryView"
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
	</c:otherwise>
</c:choose>