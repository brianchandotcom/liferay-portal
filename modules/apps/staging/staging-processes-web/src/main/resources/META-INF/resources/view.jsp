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
boolean showStagingConfiguration = ParamUtil.getBoolean(request, "showStagingConfiguration");
%>

<c:choose>
	<c:when test="<%= !GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.VIEW_STAGING) %>">
		<div class="alert alert-info">
			<liferay-ui:message key="you-do-not-have-permission-to-access-the-requested-resource" />
		</div>
	</c:when>
	<c:when test="<%= showStagingConfiguration || (PropsValues.STAGING_LIVE_GROUP_REMOTE_STAGING_ENABLED && !group.isStaged()) || (!group.isStaged() && !group.hasLocalOrRemoteStagingGroup()) %>">

		<%
		if (group.isStaged() || group.hasLocalOrRemoteStagingGroup()) {
			portletDisplay.setShowBackIcon(true);

			PortletURL stagingProcessesURL = PortalUtil.getControlPanelPortletURL(request, StagingProcessesPortletKeys.STAGING_PROCESSES, PortletRequest.RENDER_PHASE);

			stagingProcessesURL.setParameter("mvcPath", "/view.jsp");

			portletDisplay.setURLBack(stagingProcessesURL.toString());
		}
		%>

		<liferay-portlet:runtime
			portletName="<%= StagingConfigurationPortletKeys.STAGING_CONFIGURATION %>"
		/>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/navigation.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>