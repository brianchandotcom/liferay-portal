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
PanelCategory panelCategory = (PanelCategory)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY);

MyAccountPanelCategoryDisplayContext myAccountPanelCategoryDisplayContext = new MyAccountPanelCategoryDisplayContext(liferayPortletRequest, liferayPortletResponse);
%>

<liferay-application-list:panel-category panelCategory="<%= panelCategory %>" showOpen="<%= true %>" />

<c:if test="<%= myAccountPanelCategoryDisplayContext.isShowMySiteGroup(false) %>">
	<aui:a
		cssClass='<%= "list-group-heading" + (myAccountPanelCategoryDisplayContext.isMySiteGroupActive(false) ? " active" : StringPool.BLANK) %>'
		href="<%= myAccountPanelCategoryDisplayContext.getMySiteGroupURL(false) %>"
		label="profile"
	/>
</c:if>

<c:if test="<%= myAccountPanelCategoryDisplayContext.isShowMySiteGroup(true) %>">
	<aui:a
		cssClass='<%= "list-group-heading" + (myAccountPanelCategoryDisplayContext.isMySiteGroupActive(true) ? " active" : StringPool.BLANK) %>'
		href="<%= myAccountPanelCategoryDisplayContext.getMySiteGroupURL(true) %>"
		label="dashboard"
	/>
</c:if>

<c:if test="<%= themeDisplay.isShowSignOutIcon() %>">
	<aui:a
		cssClass="list-group-heading"
		href="<%= themeDisplay.getURLSignOut() %>"
		label="sign-out"
	/>
</c:if>