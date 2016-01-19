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
	<c:when test="<%= showStagingConfiguration || (!group.isStaged() && !group.hasLocalOrRemoteStagingGroup()) %>">

		<%
		if (group.isStaged() || group.hasLocalOrRemoteStagingGroup()) {
			portletDisplay.setShowBackIcon(true);

			PortletURL stagingProcessesURL = PortalUtil.getControlPanelPortletURL(request, StagingProcessesPortletKeys.STAGING_PROCESSES, PortletRequest.RENDER_PHASE);

			stagingProcessesURL.setParameter("mvcPath", "/view.jsp");

			portletDisplay.setURLBack(stagingProcessesURL.toString());
		}
		%>

		<liferay-portlet:runtime portletName="<%= StagingConfigurationPortletKeys.STAGING_CONFIGURATION %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/navigation.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>

<aui:script use="liferay-export-import">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="publishLayouts" var="publishProcessesURL">
		<portlet:param name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_CUR_PARAM) %>" />
		<portlet:param name="<%= SearchContainer.DEFAULT_DELTA_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_DELTA_PARAM) %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(stagingGroupId) %>" />
		<portlet:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
		<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
	</liferay-portlet:resourceURL>

	new Liferay.ExportImport(
		{
			incompleteProcessMessageNode: '#<portlet:namespace />incompleteProcessMessage',
			locale: '<%= locale.toLanguageTag() %>',
			namespace: '<portlet:namespace />',
			processesNode: '#publishProcessesSearchContainer',
			processesResourceURL: '<%= publishProcessesURL.toString() %>',
			timeZone: '<%= timeZone.getID() %>'
		}
	);
</aui:script>