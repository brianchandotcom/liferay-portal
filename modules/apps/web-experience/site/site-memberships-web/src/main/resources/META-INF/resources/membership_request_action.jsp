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

MembershipRequest membershipRequest = (MembershipRequest)row.getObject();
%>

<c:if test="<%= (membershipRequest.getStatusId() == MembershipRequestConstants.STATUS_PENDING) && GroupPermissionUtil.contains(permissionChecker, themeDisplay.getScopeGroup(), ActionKeys.ASSIGN_MEMBERS) %>">
	<portlet:renderURL var="replyRequestURL">
		<portlet:param name="mvcPath" value="/reply_membership_request.jsp" />
		<portlet:param name="p_u_i_d" value="<%= String.valueOf(membershipRequest.getUserId()) %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
		<portlet:param name="membershipRequestId" value="<%= String.valueOf(membershipRequest.getMembershipRequestId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		icon="reply"
		linkCssClass="icon-monospaced text-default"
		markupView="lexicon"
		message="reply"
		url="<%= replyRequestURL %>"
	/>
</c:if>