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

<%@ include file="/portlet/init.jsp" %>

<%
PanelCategoryRegistry panelCategoryRegistry = (PanelCategoryRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);
PanelAppRegistry panelAppRegistry = (PanelAppRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_APP_REGISTRY);

String portletId = themeDisplay.getPpid();

PanelCategory firstChildPanelCategory = panelCategoryRegistry.getFirstChildPanelCategory(PanelCategoryKeys.ROOT);

String rootPanelCategoryKey = firstChildPanelCategory.getKey();

if (Validator.isNotNull(portletId)) {
	PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(panelAppRegistry, panelCategoryRegistry);

	for (PanelCategory panelCategory : panelCategoryRegistry.getChildPanelCategories(PanelCategoryKeys.ROOT)) {
		if (panelCategoryHelper.containsPortlet(portletId, panelCategory)) {
			rootPanelCategoryKey = panelCategory.getKey();

			break;
		}
	}
}
%>

<h4 class="sidebar-header">
	<span class="company-details">
		<img alt="" class="company-logo" src="<%= themeDisplay.getCompanyLogo() %>" />
		<span class="company-name"><%= company.getName() %></span>
	</span>

	<aui:icon cssClass="sidenav-close" image="remove" url="javascript:;" />
</h4>

<ul class="nav nav-tabs product-menu-tabs">

	<%
	for (PanelCategory curPanelCategory : panelCategoryRegistry.getChildPanelCategories(PanelCategoryKeys.ROOT)) {
	%>

		<li class="col-xs-4 <%= rootPanelCategoryKey.equals(curPanelCategory.getKey()) ? "active" : StringPool.BLANK %>">
			<a aria-expanded="true" data-toggle="tab" href="#<%= curPanelCategory.getKey() %>">
				<div class="product-menu-tab-icon">
					<span class="<%= curPanelCategory.getIconCssClass() %> icon-monospaced"></span>
				</div>

				<div class="product-menu-tab-text">
					<%= curPanelCategory.getLabel(locale) %>
				</div>
			</a>
		</li>

	<%
	}
	%>

</ul>

<div class="sidebar-body">
	<div class="tab-content">

		<%
		for (PanelCategory curPanelCategory : panelCategoryRegistry.getChildPanelCategories(PanelCategoryKeys.ROOT)) {
		%>

			<div class="fade in tab-pane <%= rootPanelCategoryKey.equals(curPanelCategory.getKey()) ? "active" : StringPool.BLANK %>" id="<%= curPanelCategory.getKey() %>">
				<liferay-application-list:panel-content panelCategory="<%= curPanelCategory %>" />
			</div>

		<%
		}
		%>

	</div>
</div>

<div class="sidebar-footer">
	<div class="nameplate">
		<div class="nameplate-field">
			<div class="user-icon user-icon-lg">
				<img alt="<%= HtmlUtil.escapeAttribute(user.getFullName()) %>" src="<%= HtmlUtil.escape(user.getPortraitURL(themeDisplay)) %>" />
			</div>
		</div>

		<div class="nameplate-content">
			<h4 class="user-heading">
				<%= HtmlUtil.escape(user.getFullName()) %>
			</h4>
		</div>

		<c:if test="<%= themeDisplay.isShowSignOutIcon() %>">
			<div class="nameplate-field">
				<a class="icon-monospaced icon-off user-signout" href="<%= themeDisplay.getURLSignOut() %>"></a>
			</div>
		</c:if>
	</div>
</div>