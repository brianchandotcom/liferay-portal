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

<%@ include file="/dynamic_include/init.jsp" %>

<%
FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);
%>

<script data-senna-track="temporary" type="text/javascript">
	if (window.Analytics) {
		window.<%= DocumentLibraryAnalyticsConstants.JS_PREFIX %>isViewFileEntry = true;
	}
</script>

<aui:script>
	if (window.Analytics) {
		Analytics.send('documentPreviewed', 'Document', {
			fileEntryId: '<%= fileEntry.getFileEntryId() %>',
			groupId: '<%= fileEntry.getGroupId() %>',
			fileEntryUUID: '<%= fileEntry.getUuid() %>',
			title: '<%= HtmlUtil.escapeJS(fileEntry.getTitle()) %>',
			version: '<%= fileEntry.getVersion() %>'
		});
	}
</aui:script>