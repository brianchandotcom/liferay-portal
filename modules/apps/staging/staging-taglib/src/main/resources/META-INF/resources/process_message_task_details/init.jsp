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
long backgroundTaskId = GetterUtil.getLong(request.getAttribute("liferay-staging:process-message-task-details:backgroundTaskId"));
String backgroundTaskStatusMessage = GetterUtil.getString(request.getAttribute("liferay-staging:process-message-task-details:backgroundTaskStatusMessage"));
String linkClass = GetterUtil.getString(request.getAttribute("liferay-staging:process-message-task-details:linkClass"));
%>