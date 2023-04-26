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

<%@ include file="/logo_selector/init.jsp" %>

<%
String defaultLogoURL = (String)request.getAttribute("liferay-frontend:logo-selector:defaultLogoURL");
String description = (String)request.getAttribute("liferay-frontend:logo-selector:description");
String label = (String)request.getAttribute("liferay-frontend:logo-selector:label");
String logoURL = (String)request.getAttribute("liferay-frontend:logo-selector:logoURL");
String selectLogoURL = (String)request.getAttribute("liferay-frontend:logo-selector:selectLogoURL");
%>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathProxy() + application.getContextPath() + "/css/logo_selector.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div>
	<react:component
		module="logo_selector/LogoSelector"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"defaultLogoURL", defaultLogoURL
			).put(
				"description", description
			).put(
				"label", label
			).put(
				"logoURL", logoURL
			).put(
				"selectLogoURL", selectLogoURL
			).build()
		%>'
	/>
</div>