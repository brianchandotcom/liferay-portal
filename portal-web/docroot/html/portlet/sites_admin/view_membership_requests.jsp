<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

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

Group group = (Group)request.getAttribute(WebKeys.GROUP);
%>

<liferay-ui:success key="membershipReplySent" message="your-reply-will-be-sent-to-the-user-by-email" />

<c:if test="<%= !layout.isTypeControlPanel() %>">
	<liferay-ui:header
		backURL="<%= redirect %>"
		escapeXml="<%= false %>"
		localizeTitle="<%= false %>"
		title='<%= HtmlUtil.escape(group.getDescriptiveName(locale)) + StringPool.COLON + StringPool.SPACE + LanguageUtil.get(pageContext, "manage-memberships") %>'
	/>
</c:if>

<liferay-util:include page="/html/portlet/sites_admin/edit_site_assignments_toolbar.jsp">
	<liferay-util:param name="toolbarItem" value="view-membership-requests" />
</liferay-util:include>

<liferay-ui:tabs
	names="pending,approved,denied"
	url="<%= currentURL %>"
/>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/sites_admin/view_membership_requests");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("groupId", String.valueOf(group.getGroupId()));

List<String> headerNames = new ArrayList<String>();

headerNames.add("date");
headerNames.add("user");
headerNames.add("user-comments");

if (!tabs1.equals("pending")) {
	headerNames.add("reply-date");
	headerNames.add("replier");
	headerNames.add("reply-comments");
}

headerNames.add(StringPool.BLANK);

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, LanguageUtil.get(pageContext, "no-requests-were-found"));

searchContainer.setHeaderNames(headerNames);

List results = MembershipRequestLocalServiceUtil.search(group.getGroupId(), statusId, searchContainer.getStart(), searchContainer.getEnd());

int total = MembershipRequestLocalServiceUtil.searchCount(group.getGroupId(), statusId);

searchContainer.setTotal(total);

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	MembershipRequest membershipRequest = (MembershipRequest)results.get(i);

	long userId = 0L;

	User user2 = UserLocalServiceUtil.getUserById(membershipRequest.getUserId());

	ResultRow row = new ResultRow(new Object[] {user2, group, membershipRequest}, userId, i);

	// Date

	row.addText(dateFormatDate.format(membershipRequest.getCreateDate()));

	// User

	StringBundler sb = new StringBundler(4);

	sb.append(HtmlUtil.escape(user2.getFullName()));
	sb.append(" (");
	sb.append(user2.getEmailAddress());
	sb.append(StringPool.CLOSE_PARENTHESIS);

	row.addText(sb.toString());

	// Comments

	row.addText(HtmlUtil.escape(membershipRequest.getComments()));

	if (!tabs1.equals("pending")) {

		// Reply Date

		row.addText(dateFormatDate.format(membershipRequest.getReplyDate()));

		// Replier

		User user3 = UserLocalServiceUtil.getUserById(membershipRequest.getReplierUserId());

		if (user3.isDefaultUser()) {
			Company user3Company = CompanyLocalServiceUtil.getCompanyById(user3.getCompanyId());

			row.addText(HtmlUtil.escape(user3Company.getName()));
		}
		else {
			row.addText(HtmlUtil.escape(user3.getFullName()));
		}

		// Reply comments

		row.addText(HtmlUtil.escape(membershipRequest.getReplyComments()));
	}

	// Actions

	row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/sites_admin/membership_request_action.jsp");

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />