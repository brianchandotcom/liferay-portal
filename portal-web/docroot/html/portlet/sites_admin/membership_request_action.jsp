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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

User user2 = (User)objArray[0];
Group group = (Group)objArray[1];
MembershipRequest membershipRequest = (MembershipRequest)objArray[2];
%>

<liferay-ui:icon-menu icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>">
	<c:if test="<%= (membershipRequest.getStatusId() == MembershipRequestConstants.STATUS_PENDING) && GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.ASSIGN_MEMBERS) %>">
		<portlet:renderURL var="replyRequestURL">
			<portlet:param name="struts_action" value="/sites_admin/reply_membership_request" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="p_u_i_d" value="<%= String.valueOf(user2.getUserId()) %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			<portlet:param name="membershipRequestId" value="<%= String.valueOf(membershipRequest.getMembershipRequestId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-reply"
			message="reply"
			url="<%= replyRequestURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>