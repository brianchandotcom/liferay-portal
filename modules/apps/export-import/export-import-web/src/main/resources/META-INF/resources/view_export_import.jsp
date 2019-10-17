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

long backgroundTaskId = ParamUtil.getLong(request, "backgroundTaskId");

portletDisplay.setDescription(LanguageUtil.get(request, "process-details"));

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}

renderResponse.setTitle(LanguageUtil.get(request, "process-details"));
%>

<div class="container-fluid-1280" id="<portlet:namespace />exportImportProcessContainer">
	<liferay-util:include page="/export_import_process.jsp" servletContext="<%= application %>" />
</div>

<aui:script use="liferay-export-import-export-import">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="exportImport" var="exportImportProcessURL">
		<portlet:param name="<%= Constants.CMD %>" value="export_import" />
		<portlet:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTaskId) %>" />
	</liferay-portlet:resourceURL>

	new Liferay.ExportImport({
		incompleteProcessMessageNode:
			'#<portlet:namespace />incompleteProcessMessage',
		locale: '<%= locale.toLanguageTag() %>',
		namespace: '<portlet:namespace />',
		processesNode: '#exportImportProcessContainer',
		processesResourceURL: '<%= HtmlUtil.escapeJS(exportImportProcessURL) %>',
		timeZoneOffset: <%= timeZoneOffset %>
	});
</aui:script>