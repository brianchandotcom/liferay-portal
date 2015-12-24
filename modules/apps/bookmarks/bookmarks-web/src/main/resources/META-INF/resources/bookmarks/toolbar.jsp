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

<%@ include file="/bookmarks/init.jsp" %>

<%
String searchContainerId = ParamUtil.getString(request, "searchContainerId");

String navigation = ParamUtil.getString(request, "navigation", "home");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("categoryId", StringPool.BLANK);
portletURL.setParameter("tag", StringPool.BLANK);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= BookmarksFolderServiceUtil.getFoldersAndEntriesCount(scopeGroupId, folderId) > 0 %>"
	searchContainerId="<%= searchContainerId %>"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-button cssClass="infoPanelToggler" href="javascript:;" iconCssClass="icon-info-sign" label="info" />

		<liferay-util:include page="/bookmarks/display_style_buttons.jsp" servletContext="<%= application %>" />
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation>

			<%
			portletURL.setParameter("navigation", "home");
			%>

			<liferay-frontend:management-bar-navigation-item active='<%= navigation.equals("home") %>' label="all" url="<%= portletURL.toString() %>" />

			<%
			portletURL.setParameter("navigation", "recent");
			%>

			<liferay-frontend:management-bar-navigation-item active='<%= navigation.equals("recent") %>' label="recent" url="<%= portletURL.toString() %>" />

			<c:if test="<%= themeDisplay.isSignedIn() %>">

				<%
				portletURL.setParameter("navigation", "mine");
				%>

				<liferay-frontend:management-bar-navigation-item active='<%= navigation.equals("mine") %>' label="mine" url="<%= portletURL.toString() %>" />
			</c:if>
		</liferay-frontend:management-bar-navigation>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>

		<%
		String taglibURL = "javascript:" + renderResponse.getNamespace() + "deleteEntries();";
		%>

		<liferay-frontend:management-bar-button href="<%= taglibURL %>" iconCssClass='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "icon-trash" : "icon-remove" %>' label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:script>
	function <portlet:namespace />deleteEntries() {
		if (<%= TrashUtil.isTrashEnabled(scopeGroupId) %> || confirm(' <%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= TrashUtil.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>');

			submitForm(form, '<portlet:actionURL name="/bookmarks/edit_entry" />');
		}
	}
</aui:script>