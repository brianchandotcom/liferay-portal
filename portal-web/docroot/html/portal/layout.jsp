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

<%@ page import="com.liferay.portal.kernel.exception.NoSuchLayoutException" %>

<%@ include file="/html/portal/init.jsp" %>

<%
String url = ParamUtil.getString(request, "previousURL");

if (Validator.isNull(url)) {
	url = PortalUtil.getCurrentURL(request);
}

url = HttpUtil.decodeURL(themeDisplay.getPortalURL() + url);
%>

<c:if test="<%= SessionErrors.contains(request, NoSuchLayoutException.class) %>">
	<h3 class="alert alert-danger">
		<liferay-ui:message key="not-found" />
	</h3>

	<liferay-ui:message key="the-requested-resource-could-not-be-found" />

	<br /><br />

	<code class="lfr-url-error"><%= HtmlUtil.escape(url) %></code>

	<div class="separator"><!-- --></div>

	<a href="javascript:history.go(-1);">&laquo; <liferay-ui:message key="back" /></a>
</c:if>

<%
StringBundler sb = (StringBundler)request.getAttribute(WebKeys.LAYOUT_CONTENT);

sb.writeTo(out);

request.removeAttribute(WebKeys.LAYOUT_CONTENT);
%>