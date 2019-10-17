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
UserGroupItemSelectorViewDisplayContext userGroupItemSelectorViewDisplayContext = (UserGroupItemSelectorViewDisplayContext)request.getAttribute(UserGroupItemSelectorWebKeys.USER_GROUP_ITEM_SELECTOR_DISPLAY_CONTEXT);

String itemSelectedEventName = userGroupItemSelectorViewDisplayContext.getItemSelectedEventName();

PortletURL portletURL = userGroupItemSelectorViewDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="userGroups"
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
			orderByCol="<%= userGroupItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= userGroupItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= portletURL %>"
		/>

		<li>
			<liferay-item-selector:search />
		</li>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />userGroupSelectorWrapper">
	<liferay-ui:search-container
		id="userGroups"
		searchContainer="<%= userGroupItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.UserGroup"
			cssClass="user-group-row"
			keyProperty="userGroupId"
			modelVar="userGroup"
		>

			<%
			Map<String, Object> data = new HashMap<>();

			data.put("id", userGroup.getUserGroupId());
			data.put("name", userGroup.getName());

			row.setData(data);
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="description"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
			searchContainer="<%= userGroupItemSelectorViewDisplayContext.getSearchContainer() %>"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />userGroups'
	);

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