<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "information-templates");
%>

<c:choose>
	<c:when test='<%= Objects.equals(tabs1, "information-templates") %>'>
		<liferay-util:include page="/view_information_templates.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= Objects.equals(tabs1, "widget-templates") %>'>
		<liferay-util:include page="/view_widget_templates.jsp" servletContext="<%= application %>" />
	</c:when>
</c:choose>