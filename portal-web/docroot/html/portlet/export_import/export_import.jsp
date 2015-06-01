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

<%@ include file="/html/portlet/export_import/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "export");

String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/export_import/export_import");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
portletURL.setParameter("portletResource", portletResource);
%>

<liferay-ui:tabs
	names="export,import"
	param="tabs2"
	portletURL="<%= portletURL %>"
/>

<liferay-util:include page="/html/portlet/export_import/export_import_error.jsp" />

<c:choose>
	<c:when test='<%= tabs2.equals("export") %>'>
		<liferay-util:include page="/html/portlet/export_import/export_portlet.jsp" />
	</c:when>
	<c:when test='<%= tabs2.equals("import") %>'>
		<liferay-util:include page="/html/portlet/export_import/import_portlet.jsp" />
	</c:when>
</c:choose>