<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
FileEntrySearch searchContainer = (FileEntrySearch)request.getAttribute("liferay-ui:search:searchContainer");

FileEntryDisplayTerms displayTerms = (FileEntryDisplayTerms)searchContainer.getDisplayTerms();
%>

<liferay-ui:search-toggle
	buttonLabel="search"
	displayTerms="<%= displayTerms %>"
	id="toggle_id_asset_search"
>
	<aui:fieldset>
		<aui:input name="keywords" size="20" type="text" value="" />

		<aui:select label="scope" name="<%= displayTerms.SELECTED_GROUP_ID %>" showEmptyOption="<%= false %>">
			<aui:option label="global" selected="<%= displayTerms.getSelectedGroupId() == themeDisplay.getCompanyGroupId() %>" value="<%= themeDisplay.getCompanyGroupId() %>" />
			<aui:option label="<%= themeDisplay.getScopeGroupName() %>" selected="<%= displayTerms.getSelectedGroupId() == scopeGroupId %>" value="<%= scopeGroupId %>" />
		</aui:select>
	</aui:fieldset>
</liferay-ui:search-toggle>