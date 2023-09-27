<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/process_summary_link/init.jsp" %>

<liferay-portlet:renderURL portletName="<%= ExportImportPortletKeys.EXPORT_IMPORT %>" var="processSummaryURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/export_import/view_process_summary" />
	<portlet:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
</liferay-portlet:renderURL>

<%
BackgroundTaskDisplay backgroundTaskDisplay = BackgroundTaskDisplayFactoryUtil.getBackgroundTaskDisplay(backgroundTask);

String taglibOnClick = liferayPortletResponse.getNamespace() + "showProcessSummary(" + String.valueOf(backgroundTask.getBackgroundTaskId()) + ", '" + HtmlUtil.escapeJS(backgroundTaskDisplay.getDisplayName(request)) + "', '" + HtmlUtil.escapeJS(processSummaryURL) + "');";
%>

<liferay-ui:icon
	message="summary"
	onClick="<%= taglibOnClick %>"
	url="javascript:void(0);"
/>

<aui:script>
	function <portlet:namespace />showProcessSummary(
		backgroundTaskId,
		backgroundTaskName,
		processSummaryURL
	) {
		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
			},
			id: '<portlet:namespace />showSummary_' + backgroundTaskId,
			title: backgroundTaskName,
			uri:
				processSummaryURL +
				'&<portlet:namespace />backgroundTaskId=' +
				backgroundTaskId,
		});
	}
</aui:script>