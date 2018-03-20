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
String tabs1 = ParamUtil.getString(request, "tabs1", "general");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("tabs1", tabs1);

String tabs1Names = "general";

if (samlProviderConfigurationHelper.isRoleIdp()) {
	tabs1Names += ",identity-provider,service-provider-connections";
}
else if (samlProviderConfigurationHelper.isRoleSp()) {
	tabs1Names += ",service-provider,identity-provider-connection";
}
%>

<liferay-ui:tabs
	names="<%= tabs1Names %>"
	url="<%= portletURL.toString() %>"
>
	<div class="container-fluid-1280">
		<c:choose>
			<c:when test='<%= tabs1.equals("general") %>'>
				<liferay-util:include page="/admin/general.jsp" servletContext="<%= pageContext.getServletContext() %>" />
			</c:when>
			<c:when test='<%= tabs1.equals("identity-provider") %>'>
				<liferay-util:include page="/admin/identity_provider.jsp" servletContext="<%= pageContext.getServletContext() %>" />
			</c:when>
			<c:when test='<%= tabs1.equals("identity-provider-connection") %>'>
				<liferay-util:include page="/admin/edit_identity_provider_connection.jsp" servletContext="<%= pageContext.getServletContext() %>" />
			</c:when>
			<c:when test='<%= tabs1.equals("service-provider") %>'>
				<liferay-util:include page="/admin/service_provider.jsp" servletContext="<%= pageContext.getServletContext() %>" />
			</c:when>
			<c:when test='<%= tabs1.equals("service-provider-connections") %>'>
				<liferay-util:include page="/admin/view_service_provider_connections.jsp" servletContext="<%= pageContext.getServletContext() %>" />
			</c:when>
		</c:choose>
	</div>
</liferay-ui:tabs>