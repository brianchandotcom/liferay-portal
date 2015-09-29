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

List<AddMenuItem> addMenuItems = new ArrayList<>();

if (JournalFolderPermission.contains(permissionChecker, scopeGroupId, journalDisplayContext.getFolderId(), ActionKeys.ADD_FOLDER)) {
	PortletURL addFolderURL = renderResponse.createRenderURL();

	addFolderURL.setParameter("mvcPath", "/edit_folder.jsp");
	addFolderURL.setParameter("redirect", currentURL);
	addFolderURL.setParameter("groupId", String.valueOf(scopeGroupId));
	addFolderURL.setParameter("parentFolderId", String.valueOf(journalDisplayContext.getFolderId()));

	String label = "folder";

	if (folder != null) {
		label = "subfolder";
	}

	addMenuItems.add(new AddMenuItem(LanguageUtil.get(request, label), addFolderURL.toString()));
}

List<DDMStructure> ddmStructures = JournalFolderServiceUtil.getDDMStructures(PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId), journalDisplayContext.getFolderId(), restrictionType);

if (JournalFolderPermission.contains(permissionChecker, scopeGroupId, journalDisplayContext.getFolderId(), ActionKeys.ADD_ARTICLE)) {
	for (DDMStructure ddmStructure : ddmStructures) {
		PortletURL addArticleURL = renderResponse.createRenderURL();

		addArticleURL.setParameter("mvcPath", "/edit_article.jsp");
		addArticleURL.setParameter("redirect", currentURL);
		addArticleURL.setParameter("groupId", String.valueOf(scopeGroupId));
		addArticleURL.setParameter("folderId", String.valueOf(journalDisplayContext.getFolderId()));
		addArticleURL.setParameter("ddmStructureKey", ddmStructure.getStructureKey());
		addArticleURL.setWindowState(LiferayWindowState.MAXIMIZED);

		addMenuItems.add(new AddMenuItem(HtmlUtil.escape(ddmStructure.getUnambiguousName(ddmStructures, themeDisplay.getScopeGroupId(), locale)), addArticleURL.toString()));
	}
}
%>

<c:if test="<%= !addMenuItems.isEmpty() %>">
	<liferay-frontend:add-menu
		addMenuItems="<%= addMenuItems %>"
	/>
</c:if>