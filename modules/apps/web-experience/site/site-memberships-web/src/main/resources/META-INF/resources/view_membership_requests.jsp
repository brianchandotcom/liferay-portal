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

PortletURL backURL = renderResponse.createRenderURL();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL.toString());

renderResponse.setTitle(LanguageUtil.get(request, "membership-requests"));
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

<liferay-frontend:management-bar
	disabled="<%= membershipRequestCount <= 0 %>"
>
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
			className="com.liferay.portal.kernel.model.MembershipRequest"
			modelVar="membershipRequest"
		>

			<%
			User membershipRequestUser = UserLocalServiceUtil.fetchUserById(membershipRequest.getUserId());

			row.setObject(new Object[] {membershipRequestUser, group, membershipRequest});
			%>

			<liferay-ui:search-container-column-text
				cssClass="content-column user-column title-column"
				name="user"
				truncate="<%= true %>"
				value='<%= (membershipRequestUser != null) ? HtmlUtil.escape(membershipRequestUser.getFullName()) : LanguageUtil.get(request, "the-user-could-not-be-found") %>'
			/>

			<liferay-ui:search-container-column-text
				cssClass="content-column user-comments-column"
				name="user-comments"
				truncate="<%= true %>"
				value="<%= HtmlUtil.escape(membershipRequest.getComments()) %>"
			/>

			<c:if test="<%= membershipRequestUser != null %>">
				<liferay-ui:search-container-column-text
					cssClass="content-column email-address-column"
					name="email-address"
					value="<%= membershipRequestUser.getEmailAddress() %>"
				/>
			</c:if>

			<liferay-ui:search-container-column-date
				cssClass="date-column text-column"
				name="date"
				value="<%= membershipRequest.getCreateDate() %>"
			/>

			<c:if test='<%= !tabs1.equals("pending") %>'>

				<%
				User membershipRequestReplierUser = UserLocalServiceUtil.fetchUserById(membershipRequest.getReplierUserId());
				%>

				<liferay-ui:search-container-column-text
					cssClass="content-column replier-comments"
					name="replier"
					truncate="<%= true %>"
				>
					<c:choose>
						<c:when test="<%= membershipRequestReplierUser != null %>">
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
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="the-user-could-not-be-found" />
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					cssClass="content-column reply-comments"
					name="reply-comments"
					truncate="<%= true %>"
					value="<%= HtmlUtil.escape(membershipRequest.getReplyComments()) %>"
				/>

				<liferay-ui:search-container-column-date
					cssClass="reply-date-column text-column"
					name="reply-date"
					value="<%= membershipRequest.getReplyDate() %>"
				/>
			</c:if>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/membership_request_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" searchContainer="<%= searchContainer %>" />
	</liferay-ui:search-container>
</div>