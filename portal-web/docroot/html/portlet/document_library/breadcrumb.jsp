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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
Folder folder = (Folder)request.getAttribute("view.jsp-folder");

DLUtil.addPortletBreadcrumbEntries(folder, request, liferayPortletResponse);

boolean showSyncMessage = GetterUtil.getBoolean(SessionClicks.get(request, liferayPortletResponse.getNamespace() + "show-sync-message", "true"));

String cssClass = "show-sync-message";

if (showSyncMessage || !PropsValues.DL_SHOW_LIFERAY_SYNC_MESSAGE) {
	cssClass = "show-sync-message aui-helper-hidden";
}
%>

<img alt="<%= LanguageUtil.get(pageContext, "show-liferay-sync-tip") %>" class="<%= cssClass %>" id="<portlet:namespace />showSyncMessageIcon" src="<%= themeDisplay.getPathThemeImages() + "/common/liferay_sync.png" %>" title="<%= LanguageUtil.get(pageContext, "liferay-sync") %>" />

<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showCurrentPortlet="<%= false %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />