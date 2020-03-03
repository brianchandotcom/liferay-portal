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
String className = GetterUtil.getString((String)request.getAttribute("liferay-ratings:ratings:className"));
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ratings:ratings:classPK"));
Boolean inTrash = (Boolean)request.getAttribute("liferay-ui:ratings:inTrash");
String type = GetterUtil.getString((String)request.getAttribute("liferay-ratings:ratings:type"));
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-ratings:ratings:data");
%>

<liferay-util:html-top
	outputKey="ratings-taglib"
>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<c:choose>
	<c:when test="<%= type.equals(RatingsType.LIKE.getValue()) %>">
		<clay:button
			elementClasses="btn-outline-borderless btn-outline-secondary btn-sm"
			icon="heart"
		/>

		<react:component
			data="<%= data %>"
			module="js/components/RatingsLike.es"
		/>
	</c:when>
	<c:otherwise>
		<liferay-ui:ratings
			className="<%= className %>"
			classPK="<%= classPK %>"
			inTrash="<%= inTrash %>"
			type="<%= type %>"
		/>
	</c:otherwise>
</c:choose>