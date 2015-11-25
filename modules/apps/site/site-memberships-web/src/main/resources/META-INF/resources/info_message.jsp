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
Group group = siteMembershipsDisplayContext.getGroup();
%>

<div class="alert alert-info container-fluid-1280 site-membership-type">
	<liferay-ui:icon
		iconCssClass="icon-signin"
		label="<%= true %>"
		message='<%= LanguageUtil.get(request, "membership-type") + StringPool.COLON + StringPool.SPACE + LanguageUtil.get(request, GroupConstants.getTypeLabel(group.getType())) %>'
	/>

	<liferay-ui:icon-help message='<%= LanguageUtil.get(request, "membership-type-" + GroupConstants.getTypeLabel(group.getType()) + "-help") %>' />

	<c:if test="<%= group.getType() == GroupConstants.TYPE_SITE_RESTRICTED %>">

		<%
		int pendingRequests = MembershipRequestLocalServiceUtil.searchCount(group.getGroupId(), MembershipRequestConstants.STATUS_PENDING);
		%>

		<c:if test="<%= pendingRequests > 0 %>">
			<br />

			<portlet:renderURL var="viewMembershipRequestsURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcPath" value="/view_membership_requests.jsp" />
				<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			</portlet:renderURL>

			<liferay-ui:icon
				cssClass="alert-link"
				iconCssClass="icon-tasks"
				label="<%= true %>"
				message='<%= LanguageUtil.format(request, "there-are-x-membership-requests-pending", String.valueOf(pendingRequests), false) %>'
				url="<%= viewMembershipRequestsURL %>"
				useDialog="<%= true %>"
			/>
		</c:if>
	</c:if>
</div>