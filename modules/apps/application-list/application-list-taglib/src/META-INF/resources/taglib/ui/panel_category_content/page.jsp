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

<%@ include file="/taglib/ui/panel_category_content/init.jsp" %>

<%
PanelCategory panelCategory = (PanelCategory)request.getAttribute("application-list-ui:panel-category-content:panelCategory");

request.setAttribute(ApplicationListWebKeys.PANEL_CATEGORY, panelCategory);

boolean include = panelCategory.include(request, response);
%>

<c:if test="<%= !include %>">
	<application-list-ui:panel panelCategory="<%= panelCategory %>" />
</c:if>