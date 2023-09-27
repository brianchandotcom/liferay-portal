<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long backgroundTaskId = GetterUtil.getLong(request.getAttribute("liferay-staging:process-message-task-details:backgroundTaskId"));
String backgroundTaskStatusMessage = GetterUtil.getString(request.getAttribute("liferay-staging:process-message-task-details:backgroundTaskStatusMessage"));
String linkClass = GetterUtil.getString(request.getAttribute("liferay-staging:process-message-task-details:linkClass"));
%>