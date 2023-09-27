<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/portlet/init.jsp" %>

<%
LayoutsTreeDisplayContext layoutsTreeDisplayContext = (LayoutsTreeDisplayContext)request.getAttribute(LayoutsTreeDisplayContext.class.getName());
%>

<react:component
	module="js/ProductMenuTree"
	props="<%= layoutsTreeDisplayContext.getData() %>"
	servletContext="<%= application %>"
/>