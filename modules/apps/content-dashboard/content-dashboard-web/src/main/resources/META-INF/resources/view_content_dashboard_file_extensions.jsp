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
ContentDashboardFileExtensionItemSelectorViewDisplayContext contentDashboardFileExtensionItemSelectorViewDisplayContext = (ContentDashboardFileExtensionItemSelectorViewDisplayContext)request.getAttribute(ContentDashboardFileExtensionItemSelectorViewDisplayContext.class.getName());
%>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/tree.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<section class="h-100">
	<span aria-hidden="true" class="loading-animation mt-0 tree-filter-loader"></span>

	<react:component
		module="js/SelectFileExtension"
		props="<%= contentDashboardFileExtensionItemSelectorViewDisplayContext.getData() %>"
	/>
</section>