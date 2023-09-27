<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	navigationItems="<%= viewMembershipRequestsDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new ViewMembershipRequestsManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, viewMembershipRequestsDisplayContext) %>"
/>

<liferay-ui:success key="membershipReplySent" message="your-reply-will-be-sent-to-the-user-by-email" />

<clay:container-fluid>
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
</clay:container-fluid>