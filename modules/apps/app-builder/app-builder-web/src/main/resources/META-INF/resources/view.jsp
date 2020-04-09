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
String appBuilderRootElementId = renderResponse.getNamespace() + "-app-builder-root";
%>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathModule() + "/data-engine-taglib/data_layout_builder/css/main.css") %>" rel="stylesheet" />
</liferay-util:html-top>

<portlet:renderURL var="basePortletURL" />

<div id="<%= appBuilderRootElementId %>">

	<%
	Map<String, Object> data = HashMapBuilder.<String, Object>put(
		"basePortletURL", basePortletURL
	).put(
		"pathFriendlyURLPublic", PortalUtil.getPathFriendlyURLPublic()
	).build();
	%>

	<react:component
		data="<%= data %>"
		module="js/index.es"
	/>
</div>