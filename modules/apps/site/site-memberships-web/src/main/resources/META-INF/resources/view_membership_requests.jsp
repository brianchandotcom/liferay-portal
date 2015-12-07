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

long groupId = ParamUtil.getLong(request, "groupId", themeDisplay.getSiteGroupId());

Group group = GroupLocalServiceUtil.getGroup(groupId);

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_membership_requests.jsp");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("groupId", String.valueOf(group.getGroupId()));

int membershipRequestCount = MembershipRequestLocalServiceUtil.searchCount(group.getGroupId(), statusId);
%>

<liferay-ui:success key="membershipReplySent" message="your-reply-will-be-sent-to-the-user-by-email" />

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		PortletURL pendingURL = PortletURLUtil.clone(portletURL, renderResponse);

		pendingURL.setParameter("tabs1", "pending");
		%>

		<aui:nav-item href="<%= pendingURL.toString() %>" label="pending" selected='<%= tabs1.equals("pending") %>' />

		<%
		PortletURL approvedURL = PortletURLUtil.clone(portletURL, renderResponse);

		approvedURL.setParameter("tabs1", "approved");
		%>

		<aui:nav-item href="<%= approvedURL.toString() %>" label="approved" selected='<%= tabs1.equals("approved") %>' />

		<%
		PortletURL deniedURL = PortletURLUtil.clone(portletURL, renderResponse);

		deniedURL.setParameter("tabs1", "denied");
		%>

		<aui:nav-item href="<%= deniedURL.toString() %>" label="denied" selected='<%= tabs1.equals("denied") %>' />
	</aui:nav>
</aui:nav-bar>

<c:if test="<%= membershipRequestCount > 0 %>">
	<liferay-frontend:management-bar>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"list"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			/>
		</liferay-frontend:management-bar-filters>
	</liferay-frontend:management-bar>
</c:if>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		emptyResultsMessage="no-requests-were-found"
		iteratorURL="<%= portletURL %>"
		total="<%= membershipRequestCount %>"
	>
		<liferay-ui:search-container-results
			results="<%= MembershipRequestLocalServiceUtil.search(group.getGroupId(), statusId, searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.MembershipRequest"
			modelVar="membershipRequest"
		>

			<%
			User membershipRequestUser = UserLocalServiceUtil.getUserById(membershipRequest.getUserId());

			row.setObject(new Object[] {membershipRequestUser, group, membershipRequest});
			%>

			<liferay-ui:search-container-column-text>
				<liferay-ui:user-portrait
					imageCssClass="user-icon-lg"
					userId="<%= membershipRequestUser.getUserId() %>"
				/>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-date
				name="date"
				value="<%= membershipRequest.getCreateDate() %>"
			/>

			<liferay-ui:search-container-column-text
				name="user"
			>
				<%= HtmlUtil.escape(membershipRequestUser.getFullName()) %> (<%= membershipRequestUser.getEmailAddress() %>)
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="user-comments"
				value="<%= HtmlUtil.escape(membershipRequest.getComments()) %>"
			/>

			<c:if test='<%= !tabs1.equals("pending") %>'>
				<liferay-ui:search-container-column-date
					name="reply-date"
					value="<%= membershipRequest.getReplyDate() %>"
				/>

				<%
				User membershipRequestReplierUser = UserLocalServiceUtil.getUserById(membershipRequest.getReplierUserId());
				%>

				<liferay-ui:search-container-column-text
					name="replier"
				>
					<c:choose>
						<c:when test="<%= membershipRequestReplierUser.isDefaultUser() %>">

							<%
							Company membershipRequestReplierCompany = CompanyLocalServiceUtil.getCompanyById(membershipRequestReplierUser.getCompanyId());
							%>

							<%= HtmlUtil.escape(membershipRequestReplierCompany.getName()) %>
						</c:when>
						<c:otherwise>
							<%= HtmlUtil.escape(membershipRequestReplierUser.getFullName()) %>
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="reply-comments"
					value="<%= HtmlUtil.escape(membershipRequest.getReplyComments()) %>"
				/>
			</c:if>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action"
				path="/membership_request_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" searchContainer="<%= searchContainer %>" />
	</liferay-ui:search-container>
</div>