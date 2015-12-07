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
		<a href="<%= themeDisplay.getURLPortal() %>">
			<span class="company-details">
				<img alt="" class="company-logo" src="<%= themeDisplay.getCompanyLogo() %>" />
				<span class="company-name"><%= company.getName() %></span>
			</span>

			<aui:icon cssClass="icon-monospaced sidenav-close visible-xs-block" image="remove" url="javascript:;" />
		</a>
	</h4>

	<div class="sidebar-body">
		<div aria-multiselectable="true" class="panel-group" id="<portlet:namespace />Accordion" role="tablist">

			<%
			List<PanelCategory> childPanelCategories = productMenuDisplayContext.getChildPanelCategories();

			for (PanelCategory childPanelCategory : childPanelCategories) {
			%>

				<div class="panel">
					<div class="panel-heading" id="<portlet:namespace /><%= AUIUtil.normalizeId(childPanelCategory.getKey()) %>Heading" role="tab">
						<div class="panel-title">
							<c:if test="<%= !childPanelCategory.includeHeader(request, new PipingServletResponse(pageContext)) %>">
								<div aria-controls="#<portlet:namespace /><%= AUIUtil.normalizeId(childPanelCategory.getKey()) %>Collapse" aria-expanded="<%= Validator.equals(childPanelCategory.getKey(), productMenuDisplayContext.getRootPanelCategoryKey()) %>" class="panel-toggler collapse-icon <%= Validator.equals(childPanelCategory.getKey(), productMenuDisplayContext.getRootPanelCategoryKey()) ? StringPool.BLANK : "collapsed" %>" class="collapsed" data-parent="#<portlet:namespace />Accordion" data-toggle="collapse" href="#<portlet:namespace /><%= AUIUtil.normalizeId(childPanelCategory.getKey()) %>Collapse" role="button">
									<span><%= childPanelCategory.getLabel(locale) %></span>

									<%
									int notificationsCount = productMenuDisplayContext.getNotificationsCount(childPanelCategory);
									%>

									<c:if test="<%= notificationsCount > 0 %>">
										<span class="sticker sticker-right sticker-rounded sticker-sm sticker-warning"><%= notificationsCount %></span>
									</c:if>
								</div>
							</c:if>
						</div>
					</div>

					<div aria-expanded="false" aria-labelledby="<portlet:namespace /><%= AUIUtil.normalizeId(childPanelCategory.getKey()) %>Heading" class="panel-collapse collapse <%= Validator.equals(childPanelCategory.getKey(), productMenuDisplayContext.getRootPanelCategoryKey()) ? "in" : StringPool.BLANK %>" id="<portlet:namespace /><%= AUIUtil.normalizeId(childPanelCategory.getKey()) %>Collapse" role="tabpanel">
						<div class="panel-body">
							<liferay-application-list:panel-content panelCategory="<%= childPanelCategory %>" />
						</div>
					</div>
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