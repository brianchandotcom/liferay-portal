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
long liveGroupId = ParamUtil.getLong(request, "liveGroupId");
boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
%>

<div id="<portlet:namespace />exportProcessesSearchContainer">
	<clay:container-fluid
		id='<%= liferayPortletResponse.getNamespace() + "processesContainer" %>'
	>
		<liferay-ui:breadcrumb
			showLayout="<%= false %>"
		/>

		<liferay-util:include page="/export/processes_list/export_layouts_processes.jsp" servletContext="<%= application %>">
			<liferay-util:param name="groupId" value="<%= String.valueOf(liveGroupId) %>" />
			<liferay-util:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
			<liferay-util:param name="displayStyle" value='<%= ParamUtil.getString(request, "displayStyle") %>' />
			<liferay-util:param name="navigation" value='<%= ParamUtil.getString(request, "navigation", "all") %>' />
			<liferay-util:param name="orderByCol" value='<%= ParamUtil.getString(request, "orderByCol") %>' />
			<liferay-util:param name="orderByType" value='<%= ParamUtil.getString(request, "orderByType") %>' />
			<liferay-util:param name="searchContainerId" value='<%= ParamUtil.getString(request, "searchContainerId") %>' />
		</liferay-util:include>
	</clay:container-fluid>
</div>