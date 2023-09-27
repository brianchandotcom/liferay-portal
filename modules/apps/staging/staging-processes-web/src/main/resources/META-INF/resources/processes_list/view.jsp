<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		<liferay-site-navigation:breadcrumb
			breadcrumbEntries="<%= BreadcrumbEntriesUtil.getBreadcrumbEntries(request, true, false, false, true, true) %>"
		/>

		<liferay-util:include page="/processes_list/publish_layouts_processes.jsp" servletContext="<%= application %>" />
	</clay:container-fluid>
</div>