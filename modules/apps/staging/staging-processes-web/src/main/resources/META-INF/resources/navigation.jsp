<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "processes");

String displayStyle = stagingProcessesWebToolbarDisplayContext.getDisplayStyle();
String navigation = ParamUtil.getString(request, "navigation", "all");

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
	portalPreferences.setValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-col", orderByCol);
	portalPreferences.setValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-type", orderByType);
}
else {
	orderByCol = portalPreferences.getValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-col", "create-date");
	orderByType = portalPreferences.getValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-type", "desc");
}

String searchContainerId = "publishLayoutProcesses";
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= stagingProcessesWebDisplayContext.getNavigationItems() %>"
/>

<c:choose>
	<c:when test='<%= tabs1.equals("processes") %>'>
		<liferay-util:include page="/processes_list/view.jsp" servletContext="<%= application %>">
			<liferay-util:param name="tabs1" value="<%= tabs1 %>" />
			<liferay-util:param name="displayStyle" value="<%= displayStyle %>" />
			<liferay-util:param name="navigation" value="<%= navigation %>" />
			<liferay-util:param name="orderByCol" value="<%= orderByCol %>" />
			<liferay-util:param name="orderByType" value="<%= orderByType %>" />
			<liferay-util:param name="searchContainerId" value="<%= searchContainerId %>" />
		</liferay-util:include>
	</c:when>
	<c:when test='<%= tabs1.equals("scheduled") %>'>
		<liferay-util:include page="/scheduled_list/view.jsp" servletContext="<%= application %>" />
	</c:when>
</c:choose>

<aui:script use="liferay-staging-processes-export-import">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/staging_processes/publish_layouts" var="publishProcessesURL">
		<portlet:param name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_CUR_PARAM) %>" />
		<portlet:param name="<%= SearchContainer.DEFAULT_DELTA_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_DELTA_PARAM) %>" />
		<portlet:param name="tabs1" value="<%= tabs1 %>" />
		<portlet:param name="displayStyle" value="<%= displayStyle %>" />
		<portlet:param name="navigation" value="<%= navigation %>" />
		<portlet:param name="orderByCol" value="<%= orderByCol %>" />
		<portlet:param name="orderByType" value="<%= orderByType %>" />
		<portlet:param name="searchContainerId" value="<%= searchContainerId %>" />
	</liferay-portlet:resourceURL>

	var exportImport = new Liferay.ExportImport({
		incompleteProcessMessageNode:
			'#<portlet:namespace />incompleteProcessMessage',
		locale: '<%= locale.toLanguageTag() %>',
		namespace: '<portlet:namespace />',
		processesNode: '#publishProcessesSearchContainer',
		processesResourceURL:
			'<%= HtmlUtil.escapeJS(publishProcessesURL.toString()) %>',
		timeZoneOffset: <%= timeZoneOffset %>,
	});

	Liferay.once('destroyPortlet', () => {
		exportImport.destroy();
	});
</aui:script>