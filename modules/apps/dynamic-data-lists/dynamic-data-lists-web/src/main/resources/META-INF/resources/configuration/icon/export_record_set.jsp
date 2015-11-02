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
long recordSetId = ParamUtil.getLong(request, renderResponse.getNamespace() + "recordSetId");
%>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="exportRecordSet" var="exportRecordSetURL">
	<portlet:param name="recordSetId" value="<%= String.valueOf(recordSetId) %>" />
</liferay-portlet:resourceURL>

<%
StringBundler sb = new StringBundler(6);

sb.append("javascript:");
sb.append(renderResponse.getNamespace());
sb.append("exportRecordSet");
sb.append("('");
sb.append(exportRecordSetURL);
sb.append("');");
%>

<liferay-ui:icon
	message="export"
	url="<%= sb.toString() %>"
/>