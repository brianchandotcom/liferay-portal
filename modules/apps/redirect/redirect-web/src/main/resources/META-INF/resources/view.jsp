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
RedirectDisplayContext redirectDisplayContext = new RedirectDisplayContext(request, liferayPortletRequest, liferayPortletResponse);
%>

<clay:management-toolbar
	displayContext="<%= new RedirectManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, redirectDisplayContext.searchContainer()) %>"
/>

<div class="container-fluid container-fluid-max-xl main-content-body">
	<liferay-ui:search-container
		id="<%= redirectDisplayContext.getSearchContainerId() %>"
		searchContainer="<%= redirectDisplayContext.searchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.redirect.model.RedirectEntry"
			keyProperty="redirectEntryId"
			modelVar="redirectEntry"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="source-url"
			>

				<%
				String url = redirectDisplayContext.getGroupBaseURL() + StringPool.SLASH + redirectEntry.getSourceURL();
				%>

				<aui:a href="<%= HtmlUtil.escapeAttribute(url) %>" target="_blank">
					<%= HtmlUtil.escape(url) %>
				</aui:a>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="destination-url"
			>
				<aui:a href="<%= HtmlUtil.escapeAttribute(redirectEntry.getDestinationURL()) %>" target="_blank">
					<%= HtmlUtil.escape(redirectEntry.getDestinationURL()) %>
				</aui:a>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text>
				<clay:dropdown-actions
					dropdownItems="<%= redirectDisplayContext.getActionDropdownItems(redirectEntry) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
			searchContainer="<%= searchContainer %>"
		/>
	</liferay-ui:search-container>
</div>