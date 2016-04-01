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
renderResponse.setTitle(layoutsAdminDisplayContext.getRootNodeName());
%>

<div class="container-fluid-1280 text-center">
	<liferay-ui:empty-result-message message='<%= layoutsAdminDisplayContext.isPrivateLayout() ? "there-are-no-private-pages" : "there-are-no-public-pages" %>'>
		<p class="text-center text-muted">
			<liferay-ui:message key='<%= layoutsAdminDisplayContext.isPrivateLayout() ? "private-pages-help" : "public-pages-help" %>' />
		</p>
	</liferay-ui:empty-result-message>

	<c:if test="<%= layoutsAdminDisplayContext.isShowAddRootLayoutButton() %>">

		<%
		PortletURL addLayoutURL = layoutsAdminDisplayContext.getAddLayoutURL(LayoutConstants.DEFAULT_PLID, layoutsAdminDisplayContext.isPrivateLayout());
		%>

		<liferay-frontend:add-menu>
			<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, layoutsAdminDisplayContext.isPrivateLayout() ? "add-private-page" : "add-public-page") %>' url="<%= addLayoutURL.toString() %>" />
		</liferay-frontend:add-menu>
	</c:if>
</div>