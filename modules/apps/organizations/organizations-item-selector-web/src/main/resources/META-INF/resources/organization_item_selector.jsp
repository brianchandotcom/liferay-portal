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
OrganizationItemSelectorViewDisplayContext organizationItemSelectorViewDisplayContext = (OrganizationItemSelectorViewDisplayContext)request.getAttribute(OrganizationItemSelectorViewConstants.ORGANIZATION_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);

String itemSelectedEventName = organizationItemSelectorViewDisplayContext.getItemSelectedEventName();

PortletURL portletURL = organizationItemSelectorViewDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="organizations"
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
			orderByCol="<%= organizationItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= organizationItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"name", "type"} %>'
			portletURL="<%= portletURL %>"
		/>

		<li>
			<liferay-item-selector:search />
		</li>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />organizationSelectorWrapper">
	<liferay-ui:search-container
		id="organizations"
		searchContainer="<%= organizationItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Organization"
			cssClass="organization-row"
			keyProperty="organizationId"
			modelVar="organization"
		>

			<%
			Map<String, Object> data = new HashMap<>();

			data.put("id", organization.getOrganizationId());
			data.put("name", organization.getName());

			row.setData(data);
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="path"
				value="<%= organizationItemSelectorViewDisplayContext.getPath(organization) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="type"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
			searchContainer="<%= organizationItemSelectorViewDisplayContext.getSearchContainer() %>"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />organizations'
	);

	searchContainer.on('rowToggled', function(event) {
		var allSelectedElements = event.elements.allSelectedElements;

		var arr = [];

		allSelectedElements.each(function() {
			var row = this.ancestor('tr');

			var data = row.getDOM().dataset;

			arr.push({
				id: data.id,
				name: data.name
			});
		});

		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(itemSelectedEventName) %>',
			{
				data: arr
			}
		);
	});
</aui:script>