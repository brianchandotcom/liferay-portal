<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
FileEntry fileEntry = (FileEntry)request.getAttribute("view_entries.jsp-fileEntry");

FileVersion fileVersion = fileEntry.getFileVersion();

FileVersion latestFileVersion = fileVersion;

if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isCompanyAdmin() || permissionChecker.isGroupAdmin(scopeGroupId) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE)) {
	latestFileVersion = fileEntry.getLatestFileVersion();
}

DLFileShortcut fileShortcut = (DLFileShortcut)request.getAttribute("view_entries.jsp-fileShortcut");

PortletURL tempRowURL = (PortletURL)request.getAttribute("view_entries.jsp-tempRowURL");

boolean showCheckBox = DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.DELETE) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE);
%>

<%@ include file="/html/portlet/document_library/document_thumbnail.jspf" %>

<div class="document-display-style display-descriptive <%= showCheckBox ? "selectable" : StringPool.BLANK %>" data-draggable="<%= showCheckBox ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" data-title="<%= StringUtil.shorten(fileEntry.getTitle(), 60) %>">
	<a class="document-link" data-folder="<%= Boolean.FALSE.toString() %>" href="<%= tempRowURL.toString() %>" title="<%= HtmlUtil.escapeAttribute(HtmlUtil.unescape(fileEntry.getTitle()) + " - " + HtmlUtil.unescape(fileEntry.getDescription())) %>">
		<span class="document-thumbnail">
			<img alt="" border="no" src="<%= thumbnailSrc %>" style="<%= thumbnailStyle %>" />

			<c:if test="<%= fileShortcut != null %>">
				<img alt="<liferay-ui:message key="shortcut" />" class="shortcut-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_link.png">
			</c:if>

			<c:if test="<%= fileEntry.isCheckedOut() %>">
				<img alt="<liferay-ui:message key="locked" />" class="locked-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_lock.png">
			</c:if>
		</span>

		<span class="entry-title">
			<%= fileEntry.getTitle() %>

			<c:if test="<%= latestFileVersion.isPending() %>">
				<span class="workflow-status-pending">
					(<liferay-ui:message key="pending" />)
				</span>
			</c:if>
		</span>

		<span class="document-description"><%= fileEntry.getDescription() %></span>
	</a>

	<%
	request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
	%>

	<liferay-util:include page="/html/portlet/document_library/file_entry_action.jsp" />

	<c:if test="<%= showCheckBox %>">

		<%
		String rowCheckerName = FileEntry.class.getSimpleName();
		long rowCheckerId = fileEntry.getFileEntryId();

		if (fileShortcut != null) {
			rowCheckerName = DLFileShortcut.class.getSimpleName();
			rowCheckerId = fileShortcut.getFileShortcutId();
		}
		%>

		<aui:input cssClass="overlay document-selector" label="" name="<%= RowChecker.ROW_IDS + rowCheckerName %>" type="checkbox" value="<%= rowCheckerId %>" />
	</c:if>
</div>