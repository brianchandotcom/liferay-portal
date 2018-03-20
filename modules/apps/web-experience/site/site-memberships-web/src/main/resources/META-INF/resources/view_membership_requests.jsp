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
String tabs1 = ParamUtil.getString(request, "tabs1", "pending");

int statusId = -1;

if (tabs1.equals("approved")) {
	statusId = MembershipRequestConstants.STATUS_APPROVED;
}
else if (tabs1.equals("denied")) {
	statusId = MembershipRequestConstants.STATUS_DENIED;
}
else {
	statusId = MembershipRequestConstants.STATUS_PENDING;
}

String displayStyle = portalPreferences.getValue(SiteMembershipsPortletKeys.SITE_MEMBERSHIPS_ADMIN, "display-style", "icon");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_membership_requests.jsp");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("groupId", String.valueOf(scopeGroupId));

SearchContainer siteMembershipSearch = new SearchContainer(renderRequest, portletURL, null, "no-requests-were-found");

String orderByCol = ParamUtil.getString(request, "orderByCol", "date");

siteMembershipSearch.setOrderByCol(orderByCol);

boolean orderByAsc = false;

String orderByType = ParamUtil.getString(request, "orderByType", "asc");

if (orderByType.equals("asc")) {
	orderByAsc = true;
}

OrderByComparator<MembershipRequest> orderByComparator = new MembershipRequestCreateDateComparator(orderByAsc);

siteMembershipSearch.setOrderByComparator(orderByComparator);

siteMembershipSearch.setOrderByType(orderByType);

int membershipRequestCount = MembershipRequestLocalServiceUtil.searchCount(scopeGroupId, statusId);

siteMembershipSearch.setTotal(membershipRequestCount);

List results = MembershipRequestLocalServiceUtil.search(scopeGroupId, statusId, siteMembershipSearch.getStart(), siteMembershipSearch.getEnd(), siteMembershipSearch.getOrderByComparator());

siteMembershipSearch.setResults(results);

PortletURL backURL = renderResponse.createRenderURL();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL.toString());

renderResponse.setTitle(LanguageUtil.get(request, "membership-requests"));

List<NavigationItem> navigationItems = new ArrayList<>();

NavigationItem pendingNavigationItem = new NavigationItem();

pendingNavigationItem.setActive(tabs1.equals("pending"));

PortletURL pendingURL = PortletURLUtil.clone(portletURL, renderResponse);

pendingURL.setParameter("tabs1", "pending");

pendingNavigationItem.setHref(pendingURL.toString());

pendingNavigationItem.setLabel(LanguageUtil.get(request, "pending"));

navigationItems.add(pendingNavigationItem);

NavigationItem approvedNavigationItem = new NavigationItem();

approvedNavigationItem.setActive(tabs1.equals("approved"));

PortletURL approvedURL = PortletURLUtil.clone(portletURL, renderResponse);

approvedURL.setParameter("tabs1", "approved");

approvedNavigationItem.setHref(approvedURL.toString());

approvedNavigationItem.setLabel(LanguageUtil.get(request, "approved"));

navigationItems.add(approvedNavigationItem);

NavigationItem deniedNavigationItem = new NavigationItem();

deniedNavigationItem.setActive(tabs1.equals("denied"));

PortletURL deniedURL = PortletURLUtil.clone(portletURL, renderResponse);

deniedURL.setParameter("tabs1", "denied");

deniedNavigationItem.setHref(deniedURL.toString());

deniedNavigationItem.setLabel(LanguageUtil.get(request, "denied"));

navigationItems.add(deniedNavigationItem);
%>

<clay:navigation-bar
	items="<%= navigationItems %>"
/>

<liferay-ui:success key="membershipReplySent" message="your-reply-will-be-sent-to-the-user-by-email" />

<liferay-frontend:management-bar
	disabled="<%= membershipRequestCount <= 0 %>"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-portlet:actionURL name="changeDisplayStyle" varImpl="changeDisplayStyleURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:actionURL>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= changeDisplayStyleURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"date"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		searchContainer="<%= siteMembershipSearch %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.MembershipRequest"
			modelVar="membershipRequest"
		>
			<c:choose>
				<c:when test='<%= tabs1.equals("pending") %>'>
					<%@ include file="/view_membership_requests_pending_columns.jspf" %>
				</c:when>
				<c:otherwise>
					<%@ include file="/view_membership_requests_columns.jspf" %>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= displayStyle %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>