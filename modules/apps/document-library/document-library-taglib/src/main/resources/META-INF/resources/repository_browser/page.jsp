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

<%@ include file="/repository_browser/init.jsp" %>

<%
RepositoryBrowserTagDisplayContext repositoryBrowserTagDisplayContext = (RepositoryBrowserTagDisplayContext)request.getAttribute(RepositoryBrowserTagDisplayContext.class.getName());
%>

<clay:container-fluid>
	<liferay-ui:search-container
		searchContainer="<%= repositoryBrowserTagDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.repository.model.RepositoryEntry"
			modelVar="repositoryEntry"
		>
			<liferay-ui:search-container-column-text>
				<clay:vertical-card
					verticalCard="<%= repositoryBrowserTagDisplayContext.getVerticalCard(repositoryEntry) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>