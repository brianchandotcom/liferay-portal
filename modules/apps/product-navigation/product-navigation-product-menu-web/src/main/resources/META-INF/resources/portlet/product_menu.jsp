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
ProductMenuDisplayContext productMenuDisplayContext = new ProductMenuDisplayContext(liferayPortletRequest, liferayPortletResponse);
%>

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
								<div aria-controls="#<portlet:namespace /><%= AUIUtil.normalizeId(childPanelCategory.getKey()) %>Collapse" aria-expanded="<%= Validator.equals(childPanelCategory.getKey(), productMenuDisplayContext.getRootPanelCategoryKey()) %>" class="collapse-icon collapse-icon-middle panel-toggler <%= Validator.equals(childPanelCategory.getKey(), productMenuDisplayContext.getRootPanelCategoryKey()) ? StringPool.BLANK : "collapsed" %>" data-parent="#<portlet:namespace />Accordion" data-toggle="collapse" href="#<portlet:namespace /><%= AUIUtil.normalizeId(childPanelCategory.getKey()) %>Collapse" role="button">
									<span class="category-name"><%= childPanelCategory.getLabel(locale) %></span>

									<%
									int notificationsCount = productMenuDisplayContext.getNotificationsCount(childPanelCategory);
									%>

									<c:if test="<%= notificationsCount > 0 %>">
										<span class="panel-notifications-count sticker sticker-right sticker-rounded sticker-sm sticker-warning"><%= notificationsCount %></span>
									</c:if>

									<aui:icon cssClass="collapse-icon-closed" image="angle-right" markupView="lexicon" />

									<aui:icon cssClass="collapse-icon-open" image="angle-down" markupView="lexicon" />
								</div>
							</c:if>
						</div>
					</div>

					<div aria-expanded="false" aria-labelledby="<portlet:namespace /><%= AUIUtil.normalizeId(childPanelCategory.getKey()) %>Heading" class="collapse panel-collapse <%= Validator.equals(childPanelCategory.getKey(), productMenuDisplayContext.getRootPanelCategoryKey()) ? "in" : StringPool.BLANK %>" id="<portlet:namespace /><%= AUIUtil.normalizeId(childPanelCategory.getKey()) %>Collapse" role="tabpanel">
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
</c:if>