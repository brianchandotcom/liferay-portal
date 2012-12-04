<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/common/init.jsp" %>

<%
String duplicateEntryAction = (String)request.getAttribute("liferay-ui:restore-entry:duplicateEntryAction");
String overrideMessage = (String)request.getAttribute("liferay-ui:restore-entry:overrideMessage");
String renameMessage = (String)request.getAttribute("liferay-ui:restore-entry:renameMessage");
String restoreEntryAction = (String)request.getAttribute("liferay-ui:restore-entry:restoreEntryAction");
%>

<aui:script use="liferay-restore-entry">
	<portlet:actionURL var="restoreEntryURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.CHECK %>" />
		<portlet:param name="struts_action" value="<%= restoreEntryAction %>" />
	</portlet:actionURL>

	<portlet:renderURL var="duplicateEntryURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
		<portlet:param name="struts_action" value="<%= duplicateEntryAction %>" />
		<portlet:param name="redirect" value="<%= PortalUtil.getCurrentURL(request) %>" />
		<portlet:param name="restoreEntryAction" value="<%= restoreEntryAction %>" />
	</portlet:renderURL>

	new Liferay.RestoreEntry(
		{
			duplicateEntryURL: '<%= duplicateEntryURL %>',
			namespace: '<portlet:namespace />',
			overrideMessage: '<%= overrideMessage %>',
			renameMessage: '<%= renameMessage %>',
			restoreEntryURL: '<%= restoreEntryURL %>'
		}
	);
</aui:script>