<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/import/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId");
boolean validate = ParamUtil.getBoolean(request, "validate", true);

String[] tempFileNames = LayoutServiceUtil.getTempFileNames(groupId, ExportImportHelper.TEMP_FOLDER_NAME);

portletDisplay.setShowBackIcon(true);

portletDisplay.setURLBack(
	PortletURLBuilder.create(
		PortalUtil.getControlPanelPortletURL(request, ExportImportPortletKeys.IMPORT, PortletRequest.RENDER_PHASE)
	).setMVCPath(
		"/import/view_import_layouts.jsp"
	).buildString());

renderResponse.setTitle(LanguageUtil.get(request, "new-import-process"));
%>

<clay:container-fluid
	cssClass="container-form-lg"
	id='<%= liferayPortletResponse.getNamespace() + "exportImportOptions" %>'
>

	<%
	int incompleteBackgroundTasksCount = BackgroundTaskManagerUtil.getBackgroundTasksCount(groupId, BackgroundTaskExecutorNames.LAYOUT_IMPORT_BACKGROUND_TASK_EXECUTOR, false);
	%>

	<div class="<%= (incompleteBackgroundTasksCount == 0) ? "hide" : "in-progress" %>" id="<portlet:namespace />incompleteProcessMessage">
		<liferay-util:include page="/incomplete_processes_message.jsp" servletContext="<%= application %>">
			<liferay-util:param name="incompleteBackgroundTasksCount" value="<%= String.valueOf(incompleteBackgroundTasksCount) %>" />
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
</clay:container-fluid>