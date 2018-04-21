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
CommerceUserSegmentEntryItemSelectorViewDisplayContext commerceUserSegmentEntryItemSelectorViewDisplayContext = (CommerceUserSegmentEntryItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer commerceUserSegmentEntrySearchContainer = commerceUserSegmentEntryItemSelectorViewDisplayContext.getSearchContainer();
String itemSelectedEventName = commerceUserSegmentEntryItemSelectorViewDisplayContext.getItemSelectedEventName();
PortletURL portletURL = commerceUserSegmentEntryItemSelectorViewDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceUserSegmentEntries"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceUserSegmentEntryItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceUserSegmentEntryItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"priority"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />commerceUserSegmentEntrySelectorWrapper">
	<liferay-ui:search-container
		id="commerceUserSegmentEntries"
		searchContainer="<%= commerceUserSegmentEntrySearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry"
			cssClass="commerce-user-segment-entry-row"
			keyProperty="commerceUserSegmentEntryId"
			modelVar="commerceUserSegmentEntry"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="priority"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="active"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var commerceUserSegmentEntrySelectorWrapper = A.one("#<portlet:namespace />commerceUserSegmentEntrySelectorWrapper");

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />commerceUserSegmentEntries');

	searchContainer.on(
		'rowToggled',
		function(event) {
			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(itemSelectedEventName) %>',
				{
					data: Liferay.Util.listCheckedExcept(commerceUserSegmentEntrySelectorWrapper, '<portlet:namespace />allRowIds')
				}
			);
		}
	);
</aui:script>