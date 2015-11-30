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

String keywords = ParamUtil.getString(request, "keywords");
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= !user.isDefaultUser() && journalDisplayContext.isShowEditActions() %>"
	searchContainerId="<%= searchContainerId %>"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-button cssClass="infoPanelToggler" href="javascript:;" iconCssClass="icon-info-sign" />

		<c:if test="<%= Validator.isNull(keywords) %>">
			<liferay-util:include page="/display_style_buttons.jsp" servletContext="<%= application %>" />
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-util:include page="/sort_button.jsp" servletContext="<%= application %>" />
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button cssClass="infoPanelToggler" href="javascript:;" iconCssClass="icon-info-sign" />

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