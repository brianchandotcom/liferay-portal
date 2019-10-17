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

<%@ include file="/process_summary_link/init.jsp" %>

<liferay-portlet:renderURL portletName="<%= ExportImportPortletKeys.EXPORT_IMPORT %>" var="processSummaryURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="showProcessSummary" />
	<portlet:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
</liferay-portlet:renderURL>

<%
BackgroundTaskDisplay backgroundTaskDisplay = BackgroundTaskDisplayFactoryUtil.getBackgroundTaskDisplay(backgroundTask);

String taglibOnClick = liferayPortletResponse.getNamespace() + "showProcessSummary(" + String.valueOf(backgroundTask.getBackgroundTaskId()) + ", '" + HtmlUtil.escapeJS(backgroundTaskDisplay.getDisplayName(request)) + "', '" + HtmlUtil.escape(processSummaryURL) + "');";
%>

<liferay-ui:icon
	message="summary"
	onClick="<%= taglibOnClick %>"
	url="javascript:;"
/>

<aui:script>
	function <portlet:namespace />showProcessSummary(
		backgroundTaskId,
		backgroundTaskName,
		processSummaryURL
	) {
		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true
			},
			id: '<portlet:namespace />showSummary_' + backgroundTaskId,
			title: backgroundTaskName,
			uri:
				processSummaryURL +
				'&<portlet:namespace />backgroundTaskId=' +
				backgroundTaskId
		});
	}
</aui:script>