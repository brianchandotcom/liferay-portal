<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

List<DDMStructure> ddmStructures = DDMStructureLocalServiceUtil.getStructures(PortalUtil.getSiteAndCompanyGroupIds(themeDisplay), PortalUtil.getClassNameId(JournalArticle.class));
%>

<aui:nav-item dropdown="<%= true %>" id="addButtonContainer" label="add">
	<c:if test="<%= JournalFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_FOLDER) %>">
		<portlet:renderURL var="addFolderURL">
			<portlet:param name="struts_action" value="/journal/edit_folder" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" />
		</portlet:renderURL>

		<aui:nav-item href="<%= addFolderURL %>" iconClass="icon-folder-open" label='<%= (folder != null) ? "subfolder" : "folder" %>' />
	</c:if>

	<c:if test="<%= JournalFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_ARTICLE) %>">
		<liferay-portlet:renderURL var="addArticleURL" windowState="<%= LiferayWindowState.MAXIMIZED.toString() %>">
			<portlet:param name="struts_action" value="/journal/edit_article" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		</liferay-portlet:renderURL>

		<aui:nav-item href="<%= addArticleURL %>" label="basic-web-content" />

		<%
		for (DDMStructure ddmStructure : ddmStructures) {
		%>

			<liferay-portlet:renderURL var="addArticleURL" windowState="<%= LiferayWindowState.MAXIMIZED.toString() %>">
				<portlet:param name="struts_action" value="/journal/edit_article" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
				<portlet:param name="structureId" value="<%= ddmStructure.getStructureKey() %>" />
			</liferay-portlet:renderURL>

			<aui:nav-item href="<%= addArticleURL %>" label="<%= HtmlUtil.escape(ddmStructure.getName(themeDisplay.getLocale())) %>" />

		<%
		}
		%>

	</c:if>
</aui:nav-item>