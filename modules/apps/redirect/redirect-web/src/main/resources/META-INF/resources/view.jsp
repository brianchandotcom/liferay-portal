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

<clay:management-toolbar
	displayContext="<%= new RedirectManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse) %>"
/>

<div class="container-fluid container-fluid-max-xl main-content-body">
	<liferay-ui:search-container
		emptyResultsMessage="no-redirects-were-found"
		total="<%= RedirectEntryLocalServiceUtil.getRedirectEntriesCount() %>"
	>
		<liferay-ui:search-container-results
			results="<%= RedirectEntryLocalServiceUtil.getRedirectEntries(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.redirect.model.RedirectEntry"
			keyProperty="redirectEntryId"
			modelVar="redirectEntry"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="source-url"
			>
				<%= HtmlUtil.escape(redirectEntry.getSourceURL()) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="destination-url"
			>
				<%= HtmlUtil.escape(redirectEntry.getDestinationURL()) %>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>