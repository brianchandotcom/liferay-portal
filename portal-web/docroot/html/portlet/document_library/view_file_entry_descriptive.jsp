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
DLFileShortcut fileShortcut = (DLFileShortcut)request.getAttribute("view_entries.jsp-fileShortcut");

PortletURL tempRowURL = (PortletURL)request.getAttribute("view_entries.jsp-tempRowURL");

boolean showCheckBox = DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.DELETE) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE);
%>

<%@ include file="/html/portlet/document_library/document_thumbnail.jspf" %>

<div class="document-display-style descriptive <%= showCheckBox ? "selectable" : StringPool.BLANK %>">
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

		<span class="entry-title"><%= fileEntry.getTitle() %></span>

		<span class="document-description"><%= fileEntry.getDescription() %></span>
	</a>

	<liferay-util:include page="/html/portlet/document_library/file_entry_action.jsp" />

	<c:if test="<%= showCheckBox %>">
		<aui:input cssClass="overlay document-selector" label="" name="<%= RowChecker.ROW_IDS + FileEntry.class.getSimpleName() %>" type="checkbox" value="<%= fileEntry.getFileEntryId() %>" />
	</c:if>
</div>