<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ContentDashboardAdminSharingDisplayContext contentDashboardAdminConfigurationDisplayContext = (ContentDashboardAdminSharingDisplayContext)request.getAttribute(ContentDashboardAdminSharingDisplayContext.class.getName());
%>

<c:if test="<%= contentDashboardAdminConfigurationDisplayContext.isSharingCollaboratorsVisible() %>">
	<liferay-sharing:collaborators
		className="<%= contentDashboardAdminConfigurationDisplayContext.getClassName() %>"
		classPK="<%= contentDashboardAdminConfigurationDisplayContext.getClassPK() %>"
	/>
</c:if>