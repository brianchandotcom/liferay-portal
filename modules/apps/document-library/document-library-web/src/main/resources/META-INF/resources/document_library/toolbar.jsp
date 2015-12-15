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

<%@ include file="/document_library/init.jsp" %>

<%
String mvcRenderCommandName = ParamUtil.getString(request, "mvcRenderCommandName");

String navigation = ParamUtil.getString(request, "navigation", "home");

long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

String searchContainerId = ParamUtil.getString(request, "searchContainerId");

boolean search = mvcRenderCommandName.equals("/document_library/search");
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId, folderId, WorkflowConstants.STATUS_ANY, false) > 0 %>"
	searchContainerId="<%= searchContainerId %>"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-button cssClass="infoPanelToggler" href="javascript:;" icon="info-circle" label="info" />

		<c:if test="<%= !search %>">
			<liferay-util:include page="/document_library/display_style_buttons.jsp" servletContext="<%= application %>" />
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<c:if test='<%= !search && !navigation.equals("recent") %>'>
		<liferay-frontend:management-bar-filters>
			<liferay-util:include page="/document_library/sort_button.jsp" servletContext="<%= application %>" />
		</liferay-frontend:management-bar-filters>
	</c:if>

	<liferay-frontend:management-bar-action-buttons>

		<%
		Group scopeGroup = themeDisplay.getScopeGroup();
		%>

		<c:if test="<%= !scopeGroup.isStaged() || scopeGroup.isStagingGroup() || !scopeGroup.isStagedPortlet(DLPortletKeys.DOCUMENT_LIBRARY) %>">

			<%
			String taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.CANCEL_CHECKOUT + "'}); void(0);";
			%>

			<liferay-frontend:management-bar-button href="<%= taglibURL %>" icon="times" label="cancel-checkout[document]" />

			<%
			taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.CHECKIN + "'}); void(0);";
			%>

			<liferay-frontend:management-bar-button href="<%= taglibURL %>" icon="lock" label="lock" />

			<%
			taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.CHECKOUT + "'}); void(0);";
			%>

			<liferay-frontend:management-bar-button href="<%= taglibURL %>" icon="unlock" label="unlock" />

			<%
			taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.MOVE + "'}); void(0);";
			%>

			<liferay-frontend:management-bar-button href="<%= taglibURL %>" icon="change" label="move" />
		</c:if>

		<%
		String taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.MOVE_TO_TRASH + "'}); void(0);";
		%>

		<aui:a cssClass="btn" href="<%= taglibURL %>" icon="trash" id="moveToTrashAction" />

		<%
		taglibURL = "javascript:" + renderResponse.getNamespace() + "deleteEntries();";
		%>

		<aui:a cssClass="btn" href="<%= taglibURL %>" iconCssClass="icon-remove" id="deleteAction" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:script>
	function <portlet:namespace />deleteEntries() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			Liferay.fire(
				'<%= renderResponse.getNamespace() %>editEntry',
				{
					action: '<%= Constants.DELETE %>'
				}
			);
		}
	}
</aui:script>