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

<%@ include file="/message_boards/init.jsp" %>

<%
String mvcRenderCommandName = ParamUtil.getString(request, "mvcRenderCommandName", "/message_boards/view");

MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = MBUtil.getCategoryId(request, category);
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		PortletURL messageBoardsHomeURL = renderResponse.createRenderURL();

		messageBoardsHomeURL.setParameter("mvcRenderCommandName", "/message_boards/view");
		messageBoardsHomeURL.setParameter("tag", StringPool.BLANK);
		%>

		<aui:nav-item href="<%= messageBoardsHomeURL.toString() %>" label="home" selected='<%= mvcRenderCommandName.equals("/message_boards/edit_category") || mvcRenderCommandName.equals("/message_boards/edit_message") || (mvcRenderCommandName.equals("/message_boards/view") || mvcRenderCommandName.equals("/message_boards/view_category") || mvcRenderCommandName.equals("/message_boards/view_message")) %>' />

		<%
		PortletURL viewRecentPostsURL = renderResponse.createRenderURL();

		viewRecentPostsURL.setParameter("mvcRenderCommandName", "/message_boards/view_recent_posts");
		%>

		<aui:nav-item href="<%= viewRecentPostsURL.toString() %>" label="recent-posts" selected='<%= mvcRenderCommandName.equals("/message_boards/view_recent_posts") %>' />

		<c:if test="<%= themeDisplay.isSignedIn() && !portletName.equals(MBPortletKeys.MESSAGE_BOARDS_ADMIN) %>">

			<%
			PortletURL viewMyPostsURL = renderResponse.createRenderURL();

			viewMyPostsURL.setParameter("mvcRenderCommandName", "/message_boards/view_my_posts");
			%>

			<aui:nav-item href="<%= viewMyPostsURL.toString() %>" label="my-posts" selected='<%= mvcRenderCommandName.equals("/message_boards/view_my_posts") %>' />

			<c:if test="<%= mbGroupServiceSettings.isEmailMessageAddedEnabled() || mbGroupServiceSettings.isEmailMessageUpdatedEnabled() %>">

				<%
				PortletURL viewMySubscriptionsURL = renderResponse.createRenderURL();

				viewMySubscriptionsURL.setParameter("mvcRenderCommandName", "/message_boards/view_my_subscriptions");
				%>

				<aui:nav-item href="<%= viewMySubscriptionsURL.toString() %>" label="my-subscriptions" selected='<%= mvcRenderCommandName.equals("/message_boards/view_my_subscriptions") %>' />
			</c:if>
		</c:if>

		<%
		PortletURL viewStatisticsURL = renderResponse.createRenderURL();

		viewStatisticsURL.setParameter("mvcRenderCommandName", "/message_boards/view_statistics");
		%>

		<aui:nav-item href="<%= viewStatisticsURL.toString() %>" label="statistics" selected='<%= mvcRenderCommandName.equals("/message_boards/view_statistics") %>' />

		<c:if test="<%= portletName.equals(MBPortletKeys.MESSAGE_BOARDS_ADMIN) && MBResourcePermission.contains(permissionChecker, scopeGroupId, ActionKeys.BAN_USER) %>">

			<%
			PortletURL bannedUsersURL = renderResponse.createRenderURL();

			bannedUsersURL.setParameter("mvcRenderCommandName", "/message_boards/view_banned_users");
			%>

			<aui:nav-item href="<%= bannedUsersURL.toString() %>" label="banned-users" selected='<%= mvcRenderCommandName.equals("/message_boards/view_banned_users") %>' />
		</c:if>
	</aui:nav>

	<c:if test="<%= showSearch %>">
		<liferay-portlet:renderURL varImpl="searchURL">
			<portlet:param name="mvcRenderCommandName" value="/message_boards/search" />
		</liferay-portlet:renderURL>

		<aui:nav-bar-search>
			<aui:form action="<%= searchURL %>" method="get" name="searchFm">
				<liferay-portlet:renderURLParams varImpl="searchURL" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="breadcrumbsCategoryId" type="hidden" value="<%= categoryId %>" />
				<aui:input name="searchCategoryId" type="hidden" value="<%= categoryId %>" />

				<liferay-ui:input-search id="keywords1" markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>

<div class="lfr-alert-container"></div>

<div id="breadcrumb">
	<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showPortletBreadcrumb="<%= true %>" />
</div>