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
ViewMembershipRequestsDisplayContext viewMembershipRequestsDisplayContext = new ViewMembershipRequestsDisplayContext(request, renderRequest, renderResponse);

PortletURL backURL = renderResponse.createRenderURL();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL.toString());

renderResponse.setTitle(LanguageUtil.get(request, "membership-requests"));
%>

<clay:navigation-bar
	items="<%= viewMembershipRequestsDisplayContext.getNavigationItems() %>"
/>

<liferay-ui:success key="membershipReplySent" message="your-reply-will-be-sent-to-the-user-by-email" />

<liferay-frontend:management-bar
	disabled="<%= viewMembershipRequestsDisplayContext.isDisabledManagementBar() %>"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-portlet:actionURL name="changeDisplayStyle" varImpl="changeDisplayStyleURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:actionURL>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= changeDisplayStyleURL %>"
			selectedDisplayStyle="<%= viewMembershipRequestsDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= viewMembershipRequestsDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= viewMembershipRequestsDisplayContext.getOrderByCol() %>"
			orderByType="<%= viewMembershipRequestsDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"date"} %>'
			portletURL="<%= viewMembershipRequestsDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		searchContainer="<%= viewMembershipRequestsDisplayContext.getSiteMembershipSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.MembershipRequest"
			modelVar="membershipRequest"
		>

			<%
			String displayStyle = viewMembershipRequestsDisplayContext.getDisplayStyle();
			%>

			<c:choose>
				<c:when test='<%= Objects.equals(viewMembershipRequestsDisplayContext.getTabs1(), "pending") %>'>
					<%@ include file="/view_membership_requests_pending_columns.jspf" %>
				</c:when>
				<c:otherwise>
					<%@ include file="/view_membership_requests_columns.jspf" %>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= viewMembershipRequestsDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>