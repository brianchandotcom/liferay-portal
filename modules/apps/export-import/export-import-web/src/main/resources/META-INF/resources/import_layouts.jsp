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
String backURL = ParamUtil.getString(request, "backURL");
long groupId = ParamUtil.getLong(request, "groupId");
boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
boolean showHeader = ParamUtil.getBoolean(request, "showHeader", true);
boolean validate = ParamUtil.getBoolean(request, "validate", true);

String[] tempFileNames = LayoutServiceUtil.getTempFileNames(groupId, ExportImportHelper.TEMP_FOLDER_NAME);
%>

<div class="container-fluid-1280">
	<c:if test="<%= showHeader %>">
		<liferay-ui:header
			backURL="<%= backURL %>"
			title='<%= privateLayout ? LanguageUtil.get(request, "import-private-pages") : LanguageUtil.get(request, "import-public-pages") %>'
		/>
	</c:if>

	<liferay-ui:tabs
		names="new-import-process,current-and-previous"
		param="tabs2"
		refresh="<%= false %>"
	>
		<liferay-ui:section>
			<div id="<portlet:namespace />exportImportOptions">

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
						<liferay-util:include page="/import_layouts_resources.jsp" servletContext="<%= application %>" />
					</c:when>
					<c:otherwise>
						<liferay-util:include page="/import_layouts_validation.jsp" servletContext="<%= application %>" />
					</c:otherwise>
				</c:choose>
			</div>
		</liferay-ui:section>

		<liferay-ui:section>
			<div class="process-list" id="<portlet:namespace />importProcesses">
				<liferay-util:include page="/import_layouts_processes.jsp" servletContext="<%= application %>" />
			</div>
		</liferay-ui:section>
	</liferay-ui:tabs>
</div>

<aui:script use="liferay-export-import">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="importLayouts" var="importProcessesURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.IMPORT %>" />
		<portlet:param name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_CUR_PARAM) %>" />
		<portlet:param name="<%= SearchContainer.DEFAULT_DELTA_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_DELTA_PARAM) %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		<portlet:param name="showHeader" value="<%= String.valueOf(showHeader) %>" />
	</liferay-portlet:resourceURL>

	new Liferay.ExportImport(
		{
			form: document.<portlet:namespace />fm1,
			incompleteProcessMessageNode: '#<portlet:namespace />incompleteProcessMessage',
			locale: '<%= locale.toLanguageTag() %>',
			namespace: '<portlet:namespace />',
			processesNode: '#importProcesses',
			processesResourceURL: '<%= importProcessesURL.toString() %>',
			timeZone: '<%= timeZone.getID() %>'
		}
	);
</aui:script>