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
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-ui:section:data");
String name = (String)request.getAttribute("liferay-ui:section:name");
String param = (String)request.getAttribute("liferay-ui:section:param");
boolean selected = (Boolean)request.getAttribute("liferay-ui:section:selected");
%>

<div class="<%= selected ? StringPool.BLANK : "hide" %>" <%= AUIUtil.buildData(data) %> id="<%= namespace %><%= param %><%= StringUtil.toCharCode(name) %>TabsSection">