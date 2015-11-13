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
JournalFolder folder = journalDisplayContext.getFolder();

int restrictionType = JournalFolderConstants.RESTRICTION_TYPE_INHERIT;

if (folder != null) {
	restrictionType = folder.getRestrictionType();
}
%>

<c:if test="<%= JournalFolderPermission.contains(permissionChecker, scopeGroupId, journalDisplayContext.getFolderId(), ActionKeys.ADD_FOLDER) || JournalFolderPermission.contains(permissionChecker, scopeGroupId, journalDisplayContext.getFolderId(), ActionKeys.ADD_ARTICLE) %>">
	<liferay-frontend:add-menu>
		<c:if test="<%= JournalFolderPermission.contains(permissionChecker, scopeGroupId, journalDisplayContext.getFolderId(), ActionKeys.ADD_FOLDER) %>">
			<portlet:renderURL var="addFolderURL">
				<portlet:param name="mvcPath" value="/edit_folder.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
				<portlet:param name="parentFolderId" value="<%= String.valueOf(journalDisplayContext.getFolderId()) %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, (folder != null) ? "subfolder" : "folder") %>' url="<%= addFolderURL.toString() %>" />
		</c:if>

		<c:if test="<%= JournalFolderPermission.contains(permissionChecker, scopeGroupId, journalDisplayContext.getFolderId(), ActionKeys.ADD_ARTICLE) %>">

			<%
			List<DDMStructure> ddmStructures = JournalFolderServiceUtil.getDDMStructures(PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId), journalDisplayContext.getFolderId(), restrictionType);

			for (DDMStructure ddmStructure : ddmStructures) {
			%>

				<portlet:renderURL var="addArticleURL">
					<portlet:param name="mvcPath" value="/edit_article.jsp" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(journalDisplayContext.getFolderId()) %>" />
					<portlet:param name="ddmStructureKey" value="<%= ddmStructure.getStructureKey() %>" />
				</portlet:renderURL>

				<liferay-frontend:add-menu-item title="<%= HtmlUtil.escape(ddmStructure.getUnambiguousName(ddmStructures, themeDisplay.getScopeGroupId(), locale)) %>" url="<%= addArticleURL.toString() %>" />

			<%
			}
			%>

		</c:if>
	</liferay-frontend:add-menu>
</c:if>