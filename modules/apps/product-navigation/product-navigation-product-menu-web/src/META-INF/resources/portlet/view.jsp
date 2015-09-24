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
PanelAppRegistry panelAppRegistry = (PanelAppRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_APP_REGISTRY);
PanelCategoryRegistry panelCategoryRegistry = (PanelCategoryRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);

List<PanelCategory> childPanelCategories = panelCategoryRegistry.getChildPanelCategories(PanelCategoryKeys.ROOT, permissionChecker, themeDisplay.getScopeGroup());

PanelCategory firstChildPanelCategory = childPanelCategories.get(0);

String rootPanelCategoryKey = firstChildPanelCategory.getKey();

if (Validator.isNotNull(themeDisplay.getPpid())) {
	PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(panelAppRegistry, panelCategoryRegistry);

	for (PanelCategory panelCategory : panelCategoryRegistry.getChildPanelCategories(PanelCategoryKeys.ROOT)) {
		if (panelCategoryHelper.containsPortlet(themeDisplay.getPpid(), panelCategory)) {
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

	<aui:icon cssClass="sidenav-close visible-xs-block" image="remove" url="javascript:;" />
</h4>

<ul class="nav nav-tabs product-menu-tabs">

	<%
	for (PanelCategory childPanelCategory : childPanelCategories) {
	%>

		<li class="<%= "col-xs-" + (12 / childPanelCategories.size()) %> <%= rootPanelCategoryKey.equals(childPanelCategory.getKey()) ? "active" : StringPool.BLANK %>">
			<a aria-expanded="true" data-toggle="tab" href="#<portlet:namespace /><%= AUIUtil.normalizeId(childPanelCategory.getKey()) %>">
				<div class="product-menu-tab-icon">
					<span class="<%= childPanelCategory.getIconCssClass() %> icon-monospaced"></span>
				</div>

				<div class="product-menu-tab-text">
					<%= childPanelCategory.getLabel(locale) %>
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
		for (PanelCategory childPanelCategory : childPanelCategories) {
		%>

			<div class="fade in tab-pane <%= rootPanelCategoryKey.equals(childPanelCategory.getKey()) ? "active" : StringPool.BLANK %>" id="<portlet:namespace /><%= AUIUtil.normalizeId(childPanelCategory.getKey()) %>">
				<liferay-application-list:panel-content panelCategory="<%= childPanelCategory %>" />
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
			<div class="user-heading">
				<%= HtmlUtil.escape(user.getFullName()) %>
			</div>

			<ul class="user-subheading">

				<%
				List<Group> mySiteGroups = user.getMySiteGroups(new String[] {User.class.getName()}, QueryUtil.ALL_POS);

				for (Group mySiteGroup : mySiteGroups) {
				%>

					<c:if test="<%= mySiteGroup.getPublicLayoutsPageCount() > 0 %>">
						<li>
							<aui:a href="<%= mySiteGroup.getDisplayURL(themeDisplay, false) %>" label="profile" />
						</li>
					</c:if>

					<c:if test="<%= mySiteGroup.getPrivateLayoutsPageCount() > 0 %>">
						<li>
							<aui:a href="<%= mySiteGroup.getDisplayURL(themeDisplay, true) %>" label="dashboard" />
						</li>
					</c:if>

				<%
				}
				%>

			</ul>
		</div>

		<c:if test="<%= themeDisplay.isShowSignOutIcon() %>">
			<div class="nameplate-field">
				<a class="icon-lg icon-monospaced icon-off user-signout" href="<%= themeDisplay.getURLSignOut() %>"></a>
			</div>
		</c:if>
	</div>
</div>

<aui:script use="liferay-store">
	AUI.$('#sidenavToggleId').sideNavigation();

	var sidenavSlider = AUI.$('#sidenavSliderId');

	sidenavSlider.on(
		'closed.lexicon.sidenav',
		function(event) {
			Liferay.Store('com.liferay.control.menu.web_productMenuState', 'closed');
		}
	);

	sidenavSlider.on(
		'open.lexicon.sidenav',
		function(event) {
			Liferay.Store('com.liferay.control.menu.web_productMenuState', 'open');
		}
	);
</aui:script>