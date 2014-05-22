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
String message = (String)request.getAttribute("liferay-ui:rss:message");
String url = (String)request.getAttribute("liferay-ui:rss:url");
%>

<liferay-ui:icon
	cssClass="taglib-rss"
	iconCssClass="icon-rss"
	label="<%= true %>"
	message="<%= message %>"
	target="_blank"
	url="<%= url %>"
/>

<liferay-util:html-top>
	<link href="<%= HtmlUtil.escape(url) %>" rel="alternate" title="RSS" type="application/rss+xml" />
</liferay-util:html-top>