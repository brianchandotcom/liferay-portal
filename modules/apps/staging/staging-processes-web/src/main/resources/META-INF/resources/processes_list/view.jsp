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

<liferay-ui:success key="localStagingEnabled" message="local-staging-is-successfully-enabled" />

<liferay-ui:success key="remoteStagingEnabled" message="remote-staging-is-successfully-enabled" />

<div id="<portlet:namespace />publishProcessesSearchContainer">
	<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>">
		<liferay-util:param name="mvcRenderCommandName" value="/staging_processes/view_publish_layouts" />
		<liferay-util:param name="tabs1" value='<%= ParamUtil.getString(request, "tabs1") %>' />
		<liferay-util:param name="displayStyle" value="<%= stagingProcessesWebToolbarDisplayContext.getDisplayStyle() %>" />
		<liferay-util:param name="navigation" value='<%= ParamUtil.getString(request, "navigation", "all") %>' />
		<liferay-util:param name="orderByCol" value='<%= ParamUtil.getString(request, "orderByCol") %>' />
		<liferay-util:param name="orderByType" value='<%= ParamUtil.getString(request, "orderByType") %>' />
		<liferay-util:param name="searchContainerId" value='<%= ParamUtil.getString(request, "searchContainerId") %>' />
	</liferay-util:include>

	<clay:container-fluid
		id='<%= liferayPortletResponse.getNamespace() + "processesContainer" %>'
	>
		<liferay-ui:breadcrumb
			showLayout="<%= false %>"
		/>

		<liferay-util:include page="/processes_list/publish_layouts_processes.jsp" servletContext="<%= application %>" />
	</clay:container-fluid>
</div>