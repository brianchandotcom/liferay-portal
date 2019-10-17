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
SelectUserGroupsDisplayContext selectUserGroupsDisplayContext = new SelectUserGroupsDisplayContext(request, renderRequest, renderResponse);
%>

<clay:management-toolbar
	displayContext="<%= new SelectUserGroupsManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, selectUserGroupsDisplayContext) %>"
/>

<aui:form cssClass="container-fluid-1280 portlet-site-memberships-user-groups" name="fm">
	<liferay-ui:search-container
		id="userGroups"
		searchContainer="<%= selectUserGroupsDisplayContext.getUserGroupSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.UserGroup"
			escapedModel="<%= true %>"
			keyProperty="userGroupId"
			modelVar="userGroup"
		>

			<%
			String displayStyle = selectUserGroupsDisplayContext.getDisplayStyle();

			boolean selectUserGroup = true;
			%>

			<%@ include file="/user_group_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= selectUserGroupsDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />userGroups'
	);

	searchContainer.on('rowToggled', function(event) {
		var result = {};

		var data = event.elements.allSelectedElements.getDOMNodes();

		if (data.length) {
			result = {
				data: data
			};
		}

		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(selectUserGroupsDisplayContext.getEventName()) %>',
			result
		);
	});
</aui:script>