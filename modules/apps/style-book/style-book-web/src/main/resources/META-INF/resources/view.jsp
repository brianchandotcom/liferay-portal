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
StyleBookDisplayContext styleBookDisplayContext = new StyleBookDisplayContext(request, liferayPortletRequest, liferayPortletResponse);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new StyleBookManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, styleBookDisplayContext.getStyleBookEntriesSearchContainer()) %>"
	propsTransformer="js/StyleBookManagementToolbarPropsTransformer"
/>

<portlet:actionURL name="/style_book/delete_style_book_entry" var="deleteStyleBookEntryURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<clay:container-fluid>
	<aui:form action="<%= deleteStyleBookEntryURL %>" name="fm">
		<liferay-ui:search-container
			searchContainer="<%= styleBookDisplayContext.getStyleBookEntriesSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.style.book.model.StyleBookEntry"
				keyProperty="styleBookEntryId"
				modelVar="styleBookEntry"
			>
				<liferay-ui:search-container-column-text>
					<clay:vertical-card
						propsTransformer="js/StylebookEntryActionDropdownPropsTransformer"
						verticalCard="<%= new StyleBookVerticalCard(styleBookEntry, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
					/>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="icon"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>

<aui:form name="styleBookEntryFm">
	<aui:input name="styleBookEntryIds" type="hidden" />
</aui:form>

<portlet:actionURL name="/style_book/update_style_book_entry_preview" var="styleBookEntryPreviewURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= styleBookEntryPreviewURL %>" name="styleBookEntryPreviewFm">
	<aui:input name="styleBookEntryId" type="hidden" />
	<aui:input name="fileEntryId" type="hidden" />
</aui:form>