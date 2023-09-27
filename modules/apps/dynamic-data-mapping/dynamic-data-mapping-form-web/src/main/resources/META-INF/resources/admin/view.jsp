<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
String currentTab = ParamUtil.getString(request, "currentTab", "forms");
%>

<liferay-util:include page="/admin/navigation_bar.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/admin/management_bar.jsp" servletContext="<%= application %>" />

<c:choose>
	<c:when test='<%= currentTab.equals("forms") %>'>
		<liferay-util:include page="/admin/view_form_instance.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= currentTab.equals("element-set") %>'>
		<liferay-util:include page="/admin/view_element_set.jsp" servletContext="<%= application %>" />
	</c:when>
</c:choose>