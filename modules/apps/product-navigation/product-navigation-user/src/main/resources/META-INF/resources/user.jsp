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
PanelCategoryHelper panelCategoryHelper = (PanelCategoryHelper)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_HELPER);

UserPanelCategory userPanelCategory = (UserPanelCategory)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY);

int notificationsCount = panelCategoryHelper.getNotificationsCount(userPanelCategory.getKey(), permissionChecker, themeDisplay.getScopeGroup(), user);

ProductMenuDisplayContext productMenuDisplayContext = new ProductMenuDisplayContext(liferayPortletRequest, liferayPortletResponse);
%>

<div aria-controls="#<portlet:namespace /><%= AUIUtil.normalizeId(userPanelCategory.getKey()) %>Collapse" aria-expanded="<%= Validator.equals(userPanelCategory.getKey(), productMenuDisplayContext.getRootPanelCategoryKey()) %>" class="panel-toggler collapse-icon <%= Validator.equals(userPanelCategory.getKey(), productMenuDisplayContext.getRootPanelCategoryKey()) ? StringPool.BLANK : "collapsed" %>" class="collapsed" data-parent="#<portlet:namespace />Accordion" data-toggle="collapse" href="#<portlet:namespace /><%= AUIUtil.normalizeId(userPanelCategory.getKey()) %>Collapse" role="button">
	<liferay-ui:user-portrait
		imageCssClass="user-icon-lg"
		userId="<%= user.getUserId() %>"
	/>

	<span>
		<%= HtmlUtil.escape(user.getFirstName()) %>
	</span>

	<c:if test="<%= notificationsCount > 0 %>">
		<span class="sticker sticker-right sticker-rounded sticker-sm sticker-warning"><%= notificationsCount %></span>
	</c:if>
</div>