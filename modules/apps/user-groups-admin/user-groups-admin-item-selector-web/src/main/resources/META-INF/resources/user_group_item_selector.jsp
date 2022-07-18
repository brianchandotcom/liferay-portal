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

SearchContainer<UserGroup> userGroupSearchContainer = userGroupItemSelectorViewDisplayContext.getSearchContainer();
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new UserGroupItemSelectorViewManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, userGroupSearchContainer) %>"
/>

<clay:container-fluid
	id='<%= liferayPortletResponse.getNamespace() + "userGroupSelectorWrapper" %>'
>
	<liferay-ui:search-container
		id="userGroups"
		searchContainer="<%= userGroupSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.UserGroup"
			cssClass="user-group-row"
			keyProperty="userGroupId"
			modelVar="userGroup"
		>

			<%
			row.setData(
				HashMapBuilder.<String, Object>put(
					"id", userGroup.getUserGroupId()
				).put(
					"name", userGroup.getName()
				).build());
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				property="description"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />userGroupsSearchContainer'
	);

	searchContainer.on('rowToggled', (event) => {
		var allSelectedElements = event.elements.allSelectedElements;
		var arr = [];

		allSelectedElements.each(function () {
			var row = this.ancestor('tr');

			var data = row.getDOM().dataset;

			arr.push({id: data.id, name: data.name});
		});

		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(userGroupItemSelectorViewDisplayContext.getItemSelectedEventName()) %>',
			{
				data: arr,
			}
		);
	});
</aui:script>