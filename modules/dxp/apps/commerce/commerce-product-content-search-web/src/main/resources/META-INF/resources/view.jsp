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
%>

<div class="container-fluid-1280">
	<liferay-ui:search-container
			id="cpSearchResults"
			searchContainer="<%= cpSearchResultsDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.search.Document"
				escapedModel="<%= false %>"
				keyProperty="UID"
				modelVar="document"
				stringKey="<%= true %>"
		>
			<liferay-ui:search-container-column-text colspan="<%= 2 %>">
				<h4>

					<%
						String img = cpSearchResultsDisplayContext.getProductDefaultImage(document, themeDisplay);
					%>

					<div>
						<c:if test="<%= Validator.isNotNull(img) %>">
							<img src="<%= img %>">
						</c:if>
					</div>

					<div>
						<a href="<%= cpSearchResultsDisplayContext.getProductFriendlyURL(themeDisplay.getPortalURL(), document) %>">
							<strong><%= cpSearchResultsDisplayContext.getTitle(document) %></strong>
						</a>
					</div>
				</h4>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" type="more" />
	</liferay-ui:search-container>
</div>