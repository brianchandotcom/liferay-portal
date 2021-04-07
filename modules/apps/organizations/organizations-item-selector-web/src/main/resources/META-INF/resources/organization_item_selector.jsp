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
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new OrganizationItemSelectorViewManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, organizationItemSelectorViewDisplayContext) %>"
/>

<clay:container-fluid
	id='<%= liferayPortletResponse.getNamespace() + "organizationSelectorWrapper" %>'
>
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
			row.setData(
				HashMapBuilder.<String, Object>put(
					"id", organization.getOrganizationId()
				).put(
					"name", organization.getName()
				).build());
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="path"
				value="<%= organizationItemSelectorViewDisplayContext.getPath(organization) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				property="type"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
			searchContainer="<%= organizationItemSelectorViewDisplayContext.getSearchContainer() %>"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />organizations'
	);

	searchContainer.on('rowToggled', (event) => {
		var allSelectedElements = event.elements.allSelectedElements;

		var arr = [];

		allSelectedElements.each(function () {
			var row = this.ancestor('tr');

			var data = row.getDOM().dataset;

			arr.push({
				id: data.id,
				name: data.name,
			});
		});

		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(itemSelectedEventName) %>',
			{
				data: arr,
			}
		);
	});
</aui:script>