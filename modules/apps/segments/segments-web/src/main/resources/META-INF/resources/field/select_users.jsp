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
SelectUsersDisplayContext selectUsersDisplayContext = (SelectUsersDisplayContext)request.getAttribute(SegmentsWebKeys.SELECT_USERS_DISPLAY_CONTEXT);
%>

<clay:management-toolbar
	clearResultsURL="<%= selectUsersDisplayContext.getClearResultsURL() %>"
	componentId="selectSegmentsEntryUsersManagementToolbar"
	disabled="<%= selectUsersDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= selectUsersDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= selectUsersDisplayContext.getTotalItems() %>"
	searchActionURL="<%= selectUsersDisplayContext.getSearchActionURL() %>"
	searchContainerId="selectSegmentsEntryUsers"
	searchFormName="searchFm"
	showSearch="<%= selectUsersDisplayContext.isShowSearch() %>"
	sortingOrder="<%= selectUsersDisplayContext.getOrderByType() %>"
	sortingURL="<%= selectUsersDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= selectUsersDisplayContext.getViewTypeItems() %>"
/>

<aui:form cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="selectUsers"
		searchContainer="<%= selectUsersDisplayContext.getUserSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.User"
			escapedModel="<%= true %>"
			keyProperty="userId"
			modelVar="user2"
			rowIdProperty="screenName"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200 table-title"
				name="name"
				value="<%= user2.getFullName() %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200"
				name="screen-name"
				orderable="<%= true %>"
				property="screenName"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= selectUsersDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />selectUsers');

	searchContainer.on(
		'rowToggled',
		function(event) {
			var result = {};

			var data = event.elements.allSelectedElements.getDOMNodes();

			if (data.length) {
				result = {
					data: data
				};
			}

			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(selectUsersDisplayContext.getEventName()) %>',
				result);
		}
	);
</aui:script>