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
UADExportProcessDisplayContext uadExportProcessDisplayContext = new UADExportProcessDisplayContext(request, renderResponse);

portletDisplay.setShowBackIcon(true);

LiferayPortletURL usersAdminURL = liferayPortletResponse.createLiferayPortletURL(UsersAdminPortletKeys.USERS_ADMIN, PortletRequest.RENDER_PHASE);

portletDisplay.setURLBack(usersAdminURL.toString());

renderResponse.setTitle(StringBundler.concat(selectedUser.getFullName(), " - ", LanguageUtil.get(request, "export-personal-data")));
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(StringPool.BLANK);
						navigationItem.setLabel(LanguageUtil.get(request, "processes"));
					});
			}
		}
	%>'
/>

<clay:management-toolbar
	creationMenu="<%= uadExportProcessDisplayContext.getCreationMenu() %>"
	filterItems="<%= uadExportProcessDisplayContext.getFilterItems() %>"
	namespace="<%= renderResponse.getNamespace() %>"
	showCreationMenu="<%= true %>"
	showSearch="<%= false %>"
/>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= uadExportProcessDisplayContext.getOrderByCol() %>"
			orderByType="<%= uadExportProcessDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date", "name"} %>'
			portletURL="<%= PortletURLUtil.clone(uadExportProcessDisplayContext.getPortletURL(), renderResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<aui:form cssClass="container-fluid-1280">
	<div id="<portlet:namespace />exportProcesses">

		<%
		request.setAttribute("UADExportProcessDisplayContext", uadExportProcessDisplayContext);
		%>

		<liferay-util:include page="/export_processes.jsp" servletContext="<%= application %>" />
	</div>
</aui:form>

<aui:script use="liferay-uad-export">
	<portlet:resourceURL id="/get_export_processes" var="exportProcessesURL">
		<portlet:param name="p_u_i_d" value="<%= String.valueOf(selectedUser.getUserId()) %>" />
		<portlet:param name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_CUR_PARAM) %>" />
		<portlet:param name="<%= SearchContainer.DEFAULT_DELTA_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_DELTA_PARAM) %>" />
	</portlet:resourceURL>

	var uadExport = new Liferay.UADExport(
		{
			namespace: '<portlet:namespace />',
			exportProcessesNode: '#exportProcesses',
			exportProcessesResourceURL: '<%= exportProcessesURL.toString() %>'
		}
	);
</aui:script>