<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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