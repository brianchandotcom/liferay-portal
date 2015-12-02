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
String searchContainerId = ParamUtil.getString(request, "searchContainerId");
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= !user.isDefaultUser() && journalDisplayContext.isShowEditActions() %>"
	searchContainerId="<%= searchContainerId %>"
>
	<liferay-frontend:management-bar-buttons>
		<c:if test="<%= journalDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-button cssClass="infoPanelToggler" href="javascript:;" iconCssClass="icon-info-sign" />
		</c:if>

		<c:if test="<%= !journalDisplayContext.isSearch() %>">
			<liferay-frontend:management-bar-display-buttons
				displayViews="<%= journalDisplayContext.getDisplayViews() %>"
				portletURL="<%= journalDisplayContext.getPortletURL() %>"
				selectedDisplayStyle="<%= journalDisplayContext.getDisplayStyle() %>"
			/>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-filter
			label="status"
			managementBarFilterItems="<%= journalDisplayContext.getManagementBarStatusFilterItems() %>"
			value="<%= journalDisplayContext.getManagementBarStatusFilterValue() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= journalDisplayContext.getOrderByCol() %>"
			orderByType="<%= journalDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"display-date", "modified-date"} %>'
			portletURL="<%= journalDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<c:if test="<%= journalDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-button cssClass="infoPanelToggler" href="javascript:;" iconCssClass="icon-info-sign" />
		</c:if>

		<%
		String taglibURL = "javascript:" + renderResponse.getNamespace() + "deleteEntries();";
		%>

		<liferay-frontend:management-bar-button href="<%= taglibURL %>" iconCssClass='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "icon-trash" : "icon-remove" %>' />

		<%
		taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: 'expireEntries'}); void(0);";
		%>

		<liferay-frontend:management-bar-button href="<%= taglibURL %>" iconCssClass="icon-time" />

		<%
		taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: 'moveEntries'}); void(0);";
		%>

		<liferay-frontend:management-bar-button href="<%= taglibURL %>" iconCssClass="icon-move" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:script>
	function <portlet:namespace />deleteEntries() {
		if (<%= TrashUtil.isTrashEnabled(scopeGroupId) %> || confirm(' <%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			Liferay.fire(
				'<%= renderResponse.getNamespace() %>editEntry',
				{
					action: '<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "moveEntriesToTrash" : "deleteEntries" %>'
				}
			);
		}
	}
</aui:script>