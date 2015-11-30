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

<c:if test="<%= productMenuDisplayContext.isShowProductMenu() %>">
	<h4 class="sidebar-header">
		<span class="company-details">
			<img alt="" class="company-logo" src="<%= themeDisplay.getCompanyLogo() %>" />
			<span class="company-name"><%= company.getName() %></span>
		</span>

		<aui:icon cssClass="icon-monospaced sidenav-close visible-xs-block" image="remove" url="javascript:;" />
	</h4>

	<ul class="nav nav-tabs product-menu-tabs">

		<%
		List<PanelCategory> childPanelCategories = productMenuDisplayContext.getChildPanelCategories();

		for (PanelCategory childPanelCategory : childPanelCategories) {
		%>

			<li class="<%= "col-xs-" + (12 / childPanelCategories.size()) %> <%= Validator.equals(childPanelCategory.getKey(), productMenuDisplayContext.getRootPanelCategoryKey()) ? "active" : StringPool.BLANK %>">
				<a aria-expanded="true" data-toggle="tab" href="#<portlet:namespace /><%= AUIUtil.normalizeId(childPanelCategory.getKey()) %>">
					<c:if test="<%= !childPanelCategory.includeHeader(request, new PipingServletResponse(pageContext)) %>">
						<div class="product-menu-tab-icon">
							<span class="<%= childPanelCategory.getIconCssClass() %> icon-monospaced"></span>

							<%
							int notificationsCount = productMenuDisplayContext.getNotificationsCount(childPanelCategory);
							%>

							<c:if test="<%= notificationsCount > 0 %>">
								<span class="sticker sticker-right sticker-rounded sticker-sm sticker-warning"><%= notificationsCount %></span>
							</c:if>
						</div>

						<div class="product-menu-tab-text">
							<%= childPanelCategory.getLabel(locale) %>
						</div>
					</c:if>
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

				<div class="fade in tab-pane <%= Validator.equals(childPanelCategory.getKey(), productMenuDisplayContext.getRootPanelCategoryKey()) ? "active" : StringPool.BLANK %>" id="<portlet:namespace /><%= AUIUtil.normalizeId(childPanelCategory.getKey()) %>">
					<liferay-application-list:panel-content panelCategory="<%= childPanelCategory %>" />
				</div>

			<%
			}
			%>

		</div>
	</div>

	<aui:script use="liferay-store">
		AUI.$('#sidenavToggleId').sideNavigation();

		var sidenavSlider = AUI.$('#sidenavSliderId');

		sidenavSlider.off('closed.lexicon.sidenav');
		sidenavSlider.off('open.lexicon.sidenav');

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
</c:if>