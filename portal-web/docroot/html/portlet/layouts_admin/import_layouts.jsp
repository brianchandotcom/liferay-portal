<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId");
boolean validate = ParamUtil.getBoolean(request, "validate", true);

String[] tempFileEntryNames = LayoutServiceUtil.getTempFileEntryNames(groupId, ExportImportHelper.TEMP_FOLDER_NAME);
%>

<liferay-ui:tabs
	names="new-import-process,all-import-processes"
	param="tabs2"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<div id="<portlet:namespace />exportImportOptions">
			<c:choose>
				<c:when test="<%= (tempFileEntryNames.length > 0) && !validate %>">
					<liferay-util:include page="/html/portlet/layouts_admin/import_layouts_resources.jsp" />
				</c:when>
				<c:otherwise>
					<liferay-util:include page="/html/portlet/layouts_admin/import_layouts_validation.jsp" />
				</c:otherwise>
			</c:choose>
		</div>
	</liferay-ui:section>

	<liferay-ui:section>
		<div class="process-list" id="<portlet:namespace />importProcesses">
			<liferay-util:include page="/html/portlet/layouts_admin/import_layouts_processes.jsp" />
		</div>
	</liferay-ui:section>
</liferay-ui:tabs>

<aui:script use="liferay-export-import">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="importProcessesURL">
		<portlet:param name="struts_action" value="/layouts_admin/import_layouts" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.IMPORT %>" />
		<portlet:param name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_CUR_PARAM) %>" />
		<portlet:param name="<%= SearchContainer.DEFAULT_DELTA_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_DELTA_PARAM) %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	</liferay-portlet:resourceURL>

	new Liferay.ExportImport(
		{
			form: document.<portlet:namespace />fm1,
			namespace: '<portlet:namespace />',
			processesNode: '#importProcesses',
			processesResourceURL: '<%= importProcessesURL.toString() %>'
		}
	);
</aui:script>