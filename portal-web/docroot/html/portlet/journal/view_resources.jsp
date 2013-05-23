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
JournalFolder folder = (JournalFolder)request.getAttribute(WebKeys.JOURNAL_FOLDER);

long folderId = ParamUtil.getLong(request, "folderId", JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

if ((folder == null) && (folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	try {
		folder = JournalFolderLocalServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

boolean viewEntries = ParamUtil.getBoolean(request, "viewEntries");
boolean viewEntriesPage = ParamUtil.getBoolean(request, "viewEntriesPage");
boolean viewFolders = ParamUtil.getBoolean(request, "viewFolders");

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));
%>

<div>
	<c:if test="<%= viewEntries %>">

		<%
		PortalUtil.addPortletBreadcrumbEntry(request, themeDisplay.getScopeGroup().getDescriptiveName(), null);
		PortalUtil.addPortletBreadcrumbEntry(request, "Web Content", liferayPortletResponse.createRenderURL().toString());
		%>

		<div id="<portlet:namespace />entries">
			<liferay-util:include page="/html/portlet/journal/view_entries.jsp" />
		</div>

		<span id="<portlet:namespace />addButton">
			<liferay-util:include page="/html/portlet/journal/add_button.jsp" />
		</span>

		<span id="<portlet:namespace />displayStyleButtons">
			<liferay-util:include page="/html/portlet/journal/display_style_buttons.jsp" />
		</span>

		<span id="<portlet:namespace />breadcrumb">
			<div class="portlet-breadcrumb">
				<liferay-util:include page="/html/portlet/journal/breadcrumb.jsp" />
			</div>

			<c:if test="<%= layout.isTypeControlPanel() %>">
				<div class="portal-breadcrumb">
					<liferay-ui:breadcrumb showCurrentGroup="<%= true %>" showCurrentPortlet="<%= layout.isTypeControlPanel() %>" showGuestGroup="<%= !layout.isTypeControlPanel() %>" showLayout="<%= true %>" showParentGroups="<%= false %>" showPortletBreadcrumb="<%= true %>" />
				</div>
			</c:if>
		</span>
	</c:if>

	<c:if test="<%= viewEntriesPage %>">
		<div id="<portlet:namespace />entries">
			<liferay-util:include page="/html/portlet/journal/view_entries.jsp" />
		</div>
	</c:if>

	<c:if test="<%= viewFolders %>">
		<div id="<portlet:namespace />folders">
			<liferay-util:include page="/html/portlet/journal/view_folders.jsp" />
		</div>
	</c:if>
</div>