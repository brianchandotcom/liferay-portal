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
String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");
%>

<c:if test="<%= !layout.isTypeControlPanel() && !windowState.equals(LiferayWindowState.EXCLUSIVE) %>">
	<liferay-util:include page="/tabs1.jsp" servletContext="<%= application %>">
		<liferay-util:param name="tabs1" value="setup" />
	</liferay-util:include>
</c:if>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, layout.getGroupId(), ActionKeys.MANAGE_ARCHIVED_SETUPS) && !windowState.equals(LiferayWindowState.EXCLUSIVE) %>">
	<portlet:renderURL var="archivedSetupsURL">
		<portlet:param name="mvcPath" value="/edit_archived_setups.jsp" />
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="returnToFullPageURL" value="<%= returnToFullPageURL %>" />
		<portlet:param name="portletConfiguration" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="portletResource" value="<%= portletResource %>" />
	</portlet:renderURL>

	<div class="archived-setups">
		<aui:a href="archivedSetupsURL" label="archive-restore-setup" />
	</div>
</c:if>

<%
ConfigurationAction configurationAction = (ConfigurationAction)request.getAttribute(WebKeys.CONFIGURATION_ACTION);

if (configurationAction != null) {
	configurationAction.include(portletConfig, request, new PipingServletResponse(pageContext));
}
%>