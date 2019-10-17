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
UserItemSelectorViewDisplayContext userItemSelectorViewDisplayContext = (UserItemSelectorViewDisplayContext)request.getAttribute(UserItemSelectorViewConstants.USER_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);

String itemSelectedEventName = userItemSelectorViewDisplayContext.getItemSelectedEventName();

PortletURL portletURL = userItemSelectorViewDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="users"
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
			orderByCol="<%= userItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= userItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"first-name", "last-name", "screen-name"} %>'
			portletURL="<%= portletURL %>"
		/>

		<li>
			<liferay-item-selector:search />
		</li>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />userSelectorWrapper">
	<liferay-ui:search-container
		id="users"
		searchContainer="<%= userItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.User"
			cssClass="user-row"
			keyProperty="userId"
			modelVar="user"
		>

			<%
			String userFullName = user.getFullName();

			Map<String, Object> data = new HashMap<>();

			data.put("id", user.getUserId());
			data.put("name", userFullName);

			row.setData(data);
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
				value="<%= HtmlUtil.escape(userFullName) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="screen-name"
				property="screenName"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
			searchContainer="<%= userItemSelectorViewDisplayContext.getSearchContainer() %>"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />users');

	searchContainer.on('rowToggled', function(event) {
		var allSelectedElements = event.elements.allSelectedElements;
		var arr = [];

		allSelectedElements.each(function() {
			var row = this.ancestor('tr');

			var data = row.getDOM().dataset;

			arr.push({id: data.id, name: data.name});
		});

		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(itemSelectedEventName) %>',
			{
				data: arr
			}
		);
	});
</aui:script>