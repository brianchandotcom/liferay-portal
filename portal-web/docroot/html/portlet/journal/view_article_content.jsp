<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/journal/init.jsp" %>

<html>

<head>
	<link href="<%= PortalUtil.getStaticResourceURL(request, themeDisplay.getDynamicResourcesCDNHost() + themeDisplay.getPathContext() + "/html/css/main.css") %>" type="text/css" rel="stylesheet" />
	<link href="<%= PortalUtil.getStaticResourceURL(request, themeDisplay.getPathThemeCss() + "/main.css") %>" rel="stylesheet" type="text/css" />

	<c:if test="<%= (layout != null) && Validator.isNotNull(layout.getCssText()) %>">
		<style type="text/css">
			<%= layout.getCssText() %>
		</style>
	</c:if>
</head>

<body>

<%= request.getAttribute(WebKeys.JOURNAL_ARTICLE_CONTENT) %>

</body>

</html>