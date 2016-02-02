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
<%@ include file="/html/taglib/init.jsp" %>

<%
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon-menu:cssClass"));
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-ui:icon-menu:data");
String direction = (String)request.getAttribute("liferay-ui:icon-menu:direction");
String icon = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon-menu:icon"));
String id = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon-menu:id"));
String message = (String)request.getAttribute("liferay-ui:icon-menu:message");
boolean scroll = GetterUtil.getBoolean(request.getAttribute("liferay-ui:icon-menu:scroll"));
String triggerCssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon-menu:triggerCssClass"));

if (Validator.isNull(icon)) {
	icon = "ellipsis-v";
}
%>

<div class="dropdown dropdown-menu-no-arrow <%= cssClass %>" id="<%= id %>" <%= AUIUtil.buildData(data) %>>
	<a aria-expanded="false" class="dropdown-toggle icon-monospaced <%= triggerCssClass %>" data-onclick="toggle" href="#1" title="<%= message %>">
		<aui:icon image="<%= icon %>" markupView="lexicon" />
	</a>

	<c:choose>
		<c:when test="<%= scroll %>">
			<div class="dropdown-menu dropdown-menu-<%= direction %>">
				<ul class="inline-scroller">
		</c:when>
		<c:otherwise>
			<ul class="dropdown-menu dropdown-menu-<%= direction %>">
		</c:otherwise>
	</c:choose>