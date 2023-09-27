<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId");
boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
%>

<div id="<portlet:namespace />importProcessesSearchContainer">
	<clay:container-fluid
		id='<%= liferayPortletResponse.getNamespace() + "processesContainer" %>'
	>
		<liferay-site-navigation:breadcrumb
			breadcrumbEntries="<%= BreadcrumbEntriesUtil.getBreadcrumbEntries(request, true, false, false, true, true) %>"
		/>

		<liferay-util:include page="/import/processes_list/import_layouts_processes.jsp" servletContext="<%= application %>">
			<liferay-util:param name="groupId" value="<%= String.valueOf(groupId) %>" />
			<liferay-util:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
			<liferay-util:param name="validate" value="<%= String.valueOf(Boolean.TRUE) %>" />
			<liferay-util:param name="displayStyle" value='<%= ParamUtil.getString(request, "displayStyle") %>' />
			<liferay-util:param name="navigation" value='<%= ParamUtil.getString(request, "navigation", "all") %>' />
			<liferay-util:param name="orderByCol" value='<%= ParamUtil.getString(request, "orderByCol") %>' />
			<liferay-util:param name="orderByType" value='<%= ParamUtil.getString(request, "orderByType") %>' />
			<liferay-util:param name="searchContainerId" value='<%= ParamUtil.getString(request, "searchContainerId") %>' />
		</liferay-util:include>
	</clay:container-fluid>
</div>