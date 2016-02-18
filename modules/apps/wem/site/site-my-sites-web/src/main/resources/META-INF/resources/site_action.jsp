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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Group group = (Group)row.getObject();

String tabs1 = (String)request.getAttribute("view.jsp-tabs1");
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:choose>
		<c:when test='<%= tabs1.equals("my-sites") %>'>
			<c:if test="<%= group.getPublicLayoutsPageCount() > 0 %>">
				<liferay-ui:icon
					message="go-to-public-pages"
					target="_blank"
					url="<%= group.getDisplayURL(themeDisplay, false) %>"
				/>
			</c:if>

			<c:if test="<%= group.getPrivateLayoutsPageCount() > 0 %>">
				<liferay-ui:icon
					message="go-to-private-pages"
					target="_blank"
					url="<%= group.getDisplayURL(themeDisplay, true) %>"
				/>
			</c:if>

			<c:if test="<%= ((group.getType() == GroupConstants.TYPE_SITE_OPEN) || (group.getType() == GroupConstants.TYPE_SITE_RESTRICTED)) && GroupLocalServiceUtil.hasUserGroup(user.getUserId(), group.getGroupId(), false) && !SiteMembershipPolicyUtil.isMembershipRequired(user.getUserId(), group.getGroupId()) && group.isManualMembership() %>">
				<portlet:actionURL name="updateGroupUsers" var="leaveURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
					<portlet:param name="removeUserIds" value="<%= String.valueOf(user.getUserId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					message="leave"
					url="<%= leaveURL %>"
				/>
			</c:if>
		</c:when>
		<c:when test="<%= group.isManualMembership() %>">
			<c:choose>
				<c:when test="<%= !GroupLocalServiceUtil.hasUserGroup(user.getUserId(), group.getGroupId()) && SiteMembershipPolicyUtil.isMembershipAllowed(user.getUserId(), group.getGroupId()) %>">
					<c:choose>
						<c:when test="<%= group.getType() == GroupConstants.TYPE_SITE_OPEN %>">
							<portlet:actionURL name="updateGroupUsers" var="joinURL">
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
								<portlet:param name="addUserIds" value="<%= String.valueOf(user.getUserId()) %>" />
							</portlet:actionURL>

							<liferay-ui:icon
								message="join"
								url="<%= joinURL %>"
							/>
						</c:when>
						<c:when test="<%= (group.getType() == GroupConstants.TYPE_SITE_RESTRICTED) && !MembershipRequestLocalServiceUtil.hasMembershipRequest(user.getUserId(), group.getGroupId(), MembershipRequestConstants.STATUS_PENDING) && SiteMembershipPolicyUtil.isMembershipAllowed(user.getUserId(), group.getGroupId()) %>">
							<portlet:renderURL var="membershipRequestURL">
								<portlet:param name="mvcPath" value="/post_membership_request.jsp" />
								<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
							</portlet:renderURL>

							<liferay-ui:icon
								message="request-membership"
								url="<%= membershipRequestURL %>"
							/>
						</c:when>
						<c:when test="<%= MembershipRequestLocalServiceUtil.hasMembershipRequest(user.getUserId(), group.getGroupId(), MembershipRequestConstants.STATUS_PENDING) %>">
							<liferay-ui:icon
								message="membership-requested"
							/>
						</c:when>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:if test="<%= ((group.getType() == GroupConstants.TYPE_SITE_OPEN) || (group.getType() == GroupConstants.TYPE_SITE_RESTRICTED)) && GroupLocalServiceUtil.hasUserGroup(user.getUserId(), group.getGroupId(), false) && !SiteMembershipPolicyUtil.isMembershipRequired(user.getUserId(), group.getGroupId()) %>">
						<portlet:actionURL name="updateGroupUsers" var="leaveURL">
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
							<portlet:param name="removeUserIds" value="<%= String.valueOf(user.getUserId()) %>" />
						</portlet:actionURL>

						<liferay-ui:icon
							message="leave"
							url="<%= leaveURL %>"
						/>
					</c:if>
				</c:otherwise>
			</c:choose>
		</c:when>
	</c:choose>
</liferay-ui:icon-menu>