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
RoleItemSelectorViewDisplayContext roleItemSelectorViewDisplayContext = (RoleItemSelectorViewDisplayContext)request.getAttribute(RoleItemSelectorViewConstants.ROLE_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);

String itemSelectedEventName = roleItemSelectorViewDisplayContext.getItemSelectedEventName();

PortletURL portletURL = roleItemSelectorViewDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="roles"
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
			orderByCol="<%= roleItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= roleItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"title", "description"} %>'
			portletURL="<%= portletURL %>"
		/>

		<li>
			<liferay-item-selector:search />
		</li>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />roleSelectorWrapper">
	<liferay-ui:search-container
		id="roles"
		searchContainer="<%= roleItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Role"
			cssClass="role-row"
			keyProperty="roleId"
			modelVar="role"
		>

			<%
			Map<String, Object> data = new HashMap<>();

			data.put("id", role.getRoleId());
			data.put("name", role.getName());

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
			searchContainer="<%= roleItemSelectorViewDisplayContext.getSearchContainer() %>"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="aui-parse-content,liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />roles');

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