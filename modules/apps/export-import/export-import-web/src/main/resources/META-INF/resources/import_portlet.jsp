<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String tabs3 = ParamUtil.getString(request, "tabs3", "new-import-process");

PortletURL portletURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCRenderCommandName(
	"/export_import/export_import"
).setPortletResource(
	portletResource
).setTabs2(
	"import"
).buildPortletURL();

boolean validate = ParamUtil.getBoolean(request, "validate", true);

String[] tempFileNames = LayoutServiceUtil.getTempFileNames(scopeGroupId, ExportImportHelper.TEMP_FOLDER_NAME + portletDisplay.getId());
%>

<clay:navigation-bar
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				portletURL.setParameter("tabs3", "new-import-process");

				add(
					navigationItem -> {
						navigationItem.setActive(tabs3.equals("new-import-process"));
						navigationItem.setHref(portletURL.toString());
						navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "new-import-process"));
					});

				portletURL.setParameter("tabs3", "current-and-previous");

				add(
					navigationItem -> {
						navigationItem.setActive(tabs3.equals("current-and-previous"));
						navigationItem.setHref(portletURL.toString());
						navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "current-and-previous"));
					});
			}
		}
	%>'
/>

<c:choose>
	<c:when test='<%= tabs3.equals("new-import-process") %>'>
		<div class="export-import-options" id="<portlet:namespace />exportImportOptions">

			<%
			int incompleteBackgroundTasksCount = BackgroundTaskManagerUtil.getBackgroundTasksCount(themeDisplay.getScopeGroupId(), selPortlet.getPortletId(), BackgroundTaskExecutorNames.PORTLET_IMPORT_BACKGROUND_TASK_EXECUTOR, false);
			%>

			<div class="<%= (incompleteBackgroundTasksCount == 0) ? "hide" : "in-progress" %>" id="<portlet:namespace />incompleteProcessMessage">
				<liferay-util:include page="/incomplete_processes_message.jsp" servletContext="<%= application %>">
					<liferay-util:param name="incompleteBackgroundTasksCount" value="<%= String.valueOf(incompleteBackgroundTasksCount) %>" />
				</liferay-util:include>
			</div>

			<c:choose>
				<c:when test="<%= (tempFileNames.length > 0) && !validate %>">
					<liferay-util:include page="/import_portlet_resources.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:otherwise>
					<liferay-util:include page="/import_portlet_validation.jsp" servletContext="<%= application %>" />
				</c:otherwise>
			</c:choose>
		</div>
	</c:when>
	<c:when test='<%= tabs3.equals("current-and-previous") %>'>
		<div class="portlet-export-import-import-processes process-list" id="<portlet:namespace />importProcesses">
			<liferay-util:include page="/import_portlet_processes.jsp" servletContext="<%= application %>" />
		</div>
	</c:when>
</c:choose>

<aui:script use="liferay-export-import-export-import">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/export_import/export_import" var="importProcessesURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.IMPORT %>" />
		<portlet:param name="tabs2" value="import" />
		<portlet:param name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_CUR_PARAM) %>" />
		<portlet:param name="<%= SearchContainer.DEFAULT_DELTA_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_DELTA_PARAM) %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(themeDisplay.getScopeGroupId()) %>" />
		<portlet:param name="portletResource" value="<%= portletResource %>" />
	</liferay-portlet:resourceURL>

	new Liferay.ExportImport({
		form: document.<portlet:namespace />fm1,
		incompleteProcessMessageNode:
			'#<portlet:namespace />incompleteProcessMessage',
		locale: '<%= locale.toLanguageTag() %>',
		namespace: '<portlet:namespace />',
		processesNode: '#importProcesses',
		processesResourceURL:
			'<%= HtmlUtil.escapeJS(importProcessesURL.toString()) %>',
		timeZoneOffset: <%= timeZoneOffset %>,
	});
</aui:script>