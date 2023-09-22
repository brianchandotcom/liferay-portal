<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

<script>
	function sendAnalyticsEvent() {
		if (window.Analytics) {
			Analytics.send('documentPreviewed', 'Document', {
				fileEntryId: '<%= fileEntry.getFileEntryId() %>',
				groupId: '<%= fileEntry.getGroupId() %>',
				fileEntryUUID: '<%= fileEntry.getUuid() %>',
				title: '<%= HtmlUtil.escapeJS(fileEntry.getTitle()) %>',
				version: '<%= fileEntry.getVersion() %>',
			});
		}
	}

	if (Liferay.SPA && document.readyState === 'complete') {
		sendAnalyticsEvent();
	}

	window.addEventListener('load', sendAnalyticsEvent);
</script>