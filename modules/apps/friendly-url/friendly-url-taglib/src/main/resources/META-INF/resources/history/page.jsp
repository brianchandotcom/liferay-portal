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

<%@ include file="/history/init.jsp" %>

<%
String defaultLanguageId = (String)request.getAttribute("liferay-friendly-url:history:defaultLanguageId");
boolean disabled = (boolean)request.getAttribute("liferay-friendly-url:history:disabled");
String elementId = (String)request.getAttribute("liferay-friendly-url:history:elementId");
String friendlyURLEntryURL = (String)request.getAttribute("liferay-friendly-url:history:friendlyURLEntryURL");
boolean localizable = (boolean)request.getAttribute("liferay-friendly-url:history:localizable");
%>

<liferay-util:html-top
	outputKey="com.liferay.friendly.url.taglib.servlet.taglib.HistoryTag#/page.jsp"
>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div class="btn-url-history-wrapper">
	<react:component
		module="js/FriendlyURLHistory"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"defaultLanguageId", defaultLanguageId
			).put(
				"disabled", disabled
			).put(
				"elementId", elementId
			).put(
				"friendlyURLEntryURL", friendlyURLEntryURL
			).put(
				"localizable", localizable
			).build()
		%>'
	/>
</div>