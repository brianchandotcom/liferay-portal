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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Group group = (Group)row.getObject();

String tabs1 = (String)request.getAttribute("view.jsp-tabs1");
%>

<liferay-ui:icon-menu showWhenSingleIcon='<%= tabs1.equals("my-sites") %>'>
	<c:choose>
		<c:when test='<%= tabs1.equals("my-sites") %>'>
			<c:if test="<%= group.getPublicLayoutsPageCount() > 0 %>">
				<portlet:actionURL var="viewPublicPagesURL">
					<portlet:param name="struts_action" value="/sites_admin/page" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
					<portlet:param name="privateLayout" value="<%= Boolean.FALSE.toString() %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					image="view"
					message="go-to-public-pages"
					target="_blank"
					url="<%= viewPublicPagesURL %>"
				/>
			</c:if>

			<c:if test="<%= group.getPrivateLayoutsPageCount() > 0 %>">
				<portlet:actionURL var="viewPrivatePagesURL">
					<portlet:param name="struts_action" value="/sites_admin/page" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
					<portlet:param name="privateLayout" value="<%= Boolean.TRUE.toString() %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					image="view"
					message="go-to-private-pages"
					target="_blank"
					url="<%= viewPrivatePagesURL %>"
				/>
			</c:if>

			<c:if test="<%= ((group.getType() == GroupConstants.TYPE_SITE_OPEN) || (group.getType() == GroupConstants.TYPE_SITE_RESTRICTED)) && GroupLocalServiceUtil.hasUserGroup(user.getUserId(), group.getGroupId(), false) && !SiteMembershipPolicyUtil.isMembershipRequired(user.getUserId(), group.getGroupId()) && group.isManualMembership() %>">
				<portlet:actionURL var="leaveURL">
					<portlet:param name="struts_action" value="/sites_admin/edit_site_assignments" />
					<portlet:param name="<%= Constants.CMD %>" value="group_users" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
					<portlet:param name="removeUserIds" value="<%= String.valueOf(user.getUserId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					image="leave"
					url="<%= leaveURL %>"
				/>
			</c:if>
		</c:when>
		<c:when test="<%= group.isManualMembership() %>">
			<c:choose>
				<c:when test="<%= !GroupLocalServiceUtil.hasUserGroup(user.getUserId(), group.getGroupId()) && SiteMembershipPolicyUtil.isMembershipAllowed(user.getUserId(), group.getGroupId()) %>">
					<c:choose>
						<c:when test="<%= group.getType() == GroupConstants.TYPE_SITE_OPEN %>">
							<portlet:actionURL var="joinURL">
								<portlet:param name="struts_action" value="/sites_admin/edit_site_assignments" />
								<portlet:param name="<%= Constants.CMD %>" value="group_users" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
								<portlet:param name="addUserIds" value="<%= String.valueOf(user.getUserId()) %>" />
							</portlet:actionURL>

							<liferay-ui:icon
								image="join"
								url="<%= joinURL %>"
							/>
						</c:when>
						<c:when test="<%= (group.getType() == GroupConstants.TYPE_SITE_RESTRICTED) && !MembershipRequestLocalServiceUtil.hasMembershipRequest(user.getUserId(), group.getGroupId(), MembershipRequestConstants.STATUS_PENDING) && SiteMembershipPolicyUtil.isMembershipAllowed(user.getUserId(), group.getGroupId()) %>">
							<portlet:renderURL var="membershipRequestURL">
								<portlet:param name="struts_action" value="/sites_admin/post_membership_request" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
							</portlet:renderURL>

							<liferay-ui:icon
								image="post"
								message="request-membership"
								url="<%= membershipRequestURL %>"
							/>
						</c:when>
						<c:when test="<%= MembershipRequestLocalServiceUtil.hasMembershipRequest(user.getUserId(), group.getGroupId(), MembershipRequestConstants.STATUS_PENDING) %>">
							<liferay-ui:icon
								image="checked"
								message="membership-requested"
							/>
						</c:when>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:if test="<%= ((group.getType() == GroupConstants.TYPE_SITE_OPEN) || (group.getType() == GroupConstants.TYPE_SITE_RESTRICTED)) && GroupLocalServiceUtil.hasUserGroup(user.getUserId(), group.getGroupId(), false) && !SiteMembershipPolicyUtil.isMembershipRequired(user.getUserId(), group.getGroupId()) %>">
						<portlet:actionURL var="leaveURL">
							<portlet:param name="struts_action" value="/sites_admin/edit_site_assignments" />
							<portlet:param name="<%= Constants.CMD %>" value="group_users" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
							<portlet:param name="removeUserIds" value="<%= String.valueOf(user.getUserId()) %>" />
						</portlet:actionURL>

						<liferay-ui:icon
							image="leave"
							url="<%= leaveURL %>"
						/>
					</c:if>
				</c:otherwise>
			</c:choose>
		</c:when>
	</c:choose>
</liferay-ui:icon-menu>