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

<%@ include file="/admin/init.jsp" %>

<%
long kaleoProcessId = ParamUtil.getLong(request, renderResponse.getNamespace() + "kaleoProcessId");
%>

<portlet:resourceURL id="kaleoProcess" var="exportURL">
	<portlet:param name="kaleoProcessId" value="<%= String.valueOf(kaleoProcessId) %>" />
</portlet:resourceURL>

<%
StringBundler sb = new StringBundler(6);

sb.append("javascript:");
sb.append(renderResponse.getNamespace());
sb.append("exportKaleoProcess");
sb.append("('");
sb.append(exportURL);
sb.append("');");
%>

<liferay-ui:icon
	message="export"
	url="<%= sb.toString() %>"
/>