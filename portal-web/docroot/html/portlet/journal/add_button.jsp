<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
JournalFolder folder = (JournalFolder)request.getAttribute("view.jsp-folder");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

List<DDMStructure> ddmStructures = DDMStructureLocalServiceUtil.getStructures(scopeGroupId, PortalUtil.getClassNameId(JournalArticle.class));
%>

<liferay-ui:icon-menu align="left" direction="down" icon="" message="add" showExpanded="<%= false %>" showWhenSingleIcon="<%= true %>">
	<c:if test="<%= JournalFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_FOLDER) %>">
		<portlet:renderURL var="addFolderURL">
			<portlet:param name="struts_action" value="/journal/edit_folder" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="folder"
			message='<%= (folder != null) ? "subfolder" : "folder" %>'
			url="<%= addFolderURL %>"
		/>
	</c:if>

	<c:if test="<%= JournalFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_ARTICLE) %>">
		<liferay-portlet:renderURL var="addArticleURL" windowState="<%= LiferayWindowState.MAXIMIZED.toString() %>">
			<portlet:param name="struts_action" value="/journal/edit_article" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			message="basic-web-content"
			src='<%= themeDisplay.getPathThemeImages() + "/common/history.png" %>'
			url="<%= addArticleURL.toString() %>"
		/>

		<%
		for (DDMStructure ddmStructure : ddmStructures) {
		%>

			<liferay-portlet:renderURL var="addArticleURL" windowState="<%= LiferayWindowState.MAXIMIZED.toString() %>">
				<portlet:param name="struts_action" value="/journal/edit_article" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
				<portlet:param name="structureId" value="<%= ddmStructure.getStructureKey() %>" />
			</liferay-portlet:renderURL>

			<%
			String ddmStructureName = HtmlUtil.escape(ddmStructure.getName(themeDisplay.getLocale()));

			if (ddmStructure.getGroupId() == themeDisplay.getCompanyGroupId()) {
				ddmStructureName += " (" + LanguageUtil.get(themeDisplay.getLocale(), "global") + ")";
			}
			%>

			<liferay-ui:icon
				message="<%= ddmStructureName %>"
				src='<%= themeDisplay.getPathThemeImages() + "/common/history.png" %>'
				url="<%= addArticleURL.toString() %>"
			/>

		<%
		}
		%>

	</c:if>
</liferay-ui:icon-menu>