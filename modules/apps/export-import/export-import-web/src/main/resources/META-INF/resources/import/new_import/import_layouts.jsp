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

<%@ include file="/import/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId");
boolean validate = ParamUtil.getBoolean(request, "validate", true);

String[] tempFileNames = LayoutServiceUtil.getTempFileNames(groupId, ExportImportHelper.TEMP_FOLDER_NAME);
%>

<div class="container-fluid-1280" id="<portlet:namespace />exportImportOptions">

	<%
	int incompleteBackgroundTaskCount = BackgroundTaskManagerUtil.getBackgroundTasksCount(groupId, BackgroundTaskExecutorNames.LAYOUT_IMPORT_BACKGROUND_TASK_EXECUTOR, false);
	%>

	<div class="<%= (incompleteBackgroundTaskCount == 0) ? "hide" : "in-progress" %>" id="<portlet:namespace />incompleteProcessMessage">
		<liferay-util:include page="/incomplete_processes_message.jsp" servletContext="<%= application %>">
			<liferay-util:param name="incompleteBackgroundTaskCount" value="<%= String.valueOf(incompleteBackgroundTaskCount) %>" />
		</liferay-util:include>
	</div>

	<c:choose>
		<c:when test="<%= (tempFileNames.length > 0) && !validate %>">
			<liferay-util:include page="/import/new_import/import_layouts_resources.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:otherwise>
			<liferay-util:include page="/import/new_import/import_layouts_validation.jsp" servletContext="<%= application %>" />
		</c:otherwise>
	</c:choose>
</div>