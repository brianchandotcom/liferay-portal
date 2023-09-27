<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/process_status/init.jsp" %>

<%
String displayType = "info";

if (backgroundTaskStatus == BackgroundTaskConstants.STATUS_FAILED) {
	displayType = "danger";
}
else if (backgroundTaskStatus == BackgroundTaskConstants.STATUS_IN_PROGRESS) {
	displayType = "warning";
}
else if (backgroundTaskStatus == BackgroundTaskConstants.STATUS_SUCCESSFUL) {
	displayType = "success";
}
%>

<clay:label
	cssClass='<%= "process-status background-task-status-" + backgroundTaskStatusLabel %>'
	data-qa-id="processResult"
	displayType="<%= displayType %>"
	label="<%= backgroundTaskStatusLabel %>"
/>