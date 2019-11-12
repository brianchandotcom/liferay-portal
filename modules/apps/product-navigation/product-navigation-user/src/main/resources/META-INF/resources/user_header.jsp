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

<a aria-controls="<portlet:namespace /><%= AUIUtil.normalizeId(userPanelCategory.getKey()) %>Collapse" aria-expanded="<%= Objects.equals(userPanelCategory.getKey(), productMenuDisplayContext.getRootPanelCategoryKey()) %>" class="collapse-icon collapse-icon-middle <%= Objects.equals(userPanelCategory.getKey(), productMenuDisplayContext.getRootPanelCategoryKey()) ? StringPool.BLANK : "collapsed" %> panel-toggler" data-parent="#<portlet:namespace />Accordion" data-qa-id="productMenuUserPanelCategory" data-toggle="collapse" href="#<portlet:namespace /><%= AUIUtil.normalizeId(userPanelCategory.getKey()) %>Collapse" role="button">
	<liferay-ui:user-portrait
		user="<%= user %>"
	/>

	<span class="text-truncate user-name">
		<%= HtmlUtil.escape(user.getFirstName()) %>

		<c:if test="<%= themeDisplay.isImpersonated() %>">

			<%
			String impersonatingUserLabel = "you-are-impersonating-the-guest-user";

			if (themeDisplay.isSignedIn()) {
				impersonatingUserLabel = LanguageUtil.format(request, "you-are-impersonating-x", HtmlUtil.escape(user.getFullName()), false);
			}
			%>

			<small class="impersonation-message">(<%= impersonatingUserLabel %>)</small>
		</c:if>
	</span>

	<c:if test="<%= notificationsCount > 0 %>">
		<span class="badge badge-danger panel-notifications-count sticker-top-right">
			<span class="badge-item badge-item-expand"><%= notificationsCount %></span>
		</span>
	</c:if>

	<aui:icon cssClass="collapse-icon-closed" image="angle-right" markupView="lexicon" />

	<aui:icon cssClass="collapse-icon-open" image="angle-down" markupView="lexicon" />
</a>