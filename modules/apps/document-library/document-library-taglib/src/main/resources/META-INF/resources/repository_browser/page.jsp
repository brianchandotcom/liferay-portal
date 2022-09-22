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

<clay:management-toolbar
	additionalProps="<%= repositoryBrowserTagDisplayContext.getAdditionalProps() %>"
	managementToolbarDisplayContext="<%= repositoryBrowserTagDisplayContext.getManagementToolbarDisplayContext() %>"
	propsTransformer="repository_browser/js/RepositoryBrowserManagementToolbarPropsTransformer"
/>

<clay:container-fluid>
	<liferay-site-navigation:breadcrumb
		breadcrumbEntries="<%= repositoryBrowserTagDisplayContext.getBreadcrumbEntries() %>"
	/>

	<liferay-ui:search-container
		id="repositoryEntries"
		searchContainer="<%= repositoryBrowserTagDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.repository.model.RepositoryEntry"
			keyProperty="primaryKey"
			modelVar="repositoryEntry"
		>
			<liferay-ui:search-container-column-text>
				<c:choose>
					<c:when test="<%= repositoryBrowserTagDisplayContext.isVerticalCard(repositoryEntry) %>">
						<clay:vertical-card
							verticalCard="<%= repositoryBrowserTagDisplayContext.getVerticalCard(repositoryEntry) %>"
						/>
					</c:when>
					<c:otherwise>
						<clay:horizontal-card
							horizontalCard="<%= repositoryBrowserTagDisplayContext.getHorizontalCard(repositoryEntry) %>"
						/>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
			resultRowSplitter="<%= repositoryBrowserTagDisplayContext.getResultRowSplitter() %>"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>

<div>
	<liferay-frontend:component
		componentId="repositoryBrowserEventHandler"
		module="repository_browser/js/ElementsDefaultEventHandler.es"
	/>
</div>