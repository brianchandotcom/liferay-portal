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
long groupId = ParamUtil.getLong(request, "groupId", GroupConstants.DEFAULT_PARENT_GROUP_ID);

Group group = null;

if (groupId > 0) {
	group = GroupServiceUtil.getGroup(groupId);
}

LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

userParams.put("inherit", Boolean.TRUE);
userParams.put("usersGroups", Long.valueOf(groupId));

int usersCount = UserLocalServiceUtil.searchCount(company.getCompanyId(), null, WorkflowConstants.STATUS_APPROVED, userParams);

LinkedHashMap<String, Object> organizationParams = new LinkedHashMap<String, Object>();

organizationParams.put("groupOrganization", Long.valueOf(groupId));
organizationParams.put("organizationsGroups", Long.valueOf(groupId));

int organizationsCount = OrganizationLocalServiceUtil.searchCount(company.getCompanyId(), OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null, null, null, null, organizationParams);

LinkedHashMap<String, Object> userGroupParams = new LinkedHashMap<String, Object>();

userGroupParams.put("userGroupsGroups", Long.valueOf(groupId));

int userGroupsCount = UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), null, userGroupParams);

request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

request.setAttribute("view_entries.jspf-site", group);
%>

<div class="sidebar-header">
	<ul class="list-inline list-unstyled sidebar-header-actions">
		<li>
			<liferay-util:include page="/site_action.jsp" servletContext="<%= application %>" />
		</li>
	</ul>

	<h4><%= HtmlUtil.escape(group.getDescriptiveName()) %></h4>
</div>

<aui:nav-bar>
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item cssClass="active" label="details" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<div class="sidebar-body">
	<img alt="<%= HtmlUtil.escapeAttribute(group.getDescriptiveName()) %>" class="center-block img-responsive" src="<%= group.getLogo(themeDisplay, true) %>" />

	<c:if test="<%= group.isOrganization() %>">

		<%
		Organization groupOrganization = OrganizationLocalServiceUtil.getOrganization(group.getOrganizationId());
		%>

		<p>
			<liferay-ui:message arguments="<%= new String[] {groupOrganization.getName(), LanguageUtil.get(request, groupOrganization.getType())} %>" key="this-site-belongs-to-x-which-is-an-organization-of-type-x" translateArguments="<%= false %>" />
		</p>
	</c:if>

	<h5><liferay-ui:message key="members" /></h5>

	<c:if test="<%= (usersCount == 0) && (organizationsCount == 0) && (userGroupsCount == 0) %>">
		<p>
			<liferay-ui:message key="none" />
		</p>
	</c:if>

	<%
	String portletId = PortletProviderUtil.getPortletId(MembershipRequest.class.getName(), PortletProvider.Action.VIEW);

	PortletURL assignMembersURL = PortalUtil.getControlPanelPortletURL(request, portletId, PortletRequest.RENDER_PHASE);

	assignMembersURL.setParameter("redirect", currentURL);
	%>

	<c:if test="<%= usersCount > 0 %>">
		<p>
			<aui:a href='<%= HttpUtil.addParameter(assignMembersURL.toString(), "tabs1", "users") %>' label='<%= LanguageUtil.format(request, (usersCount == 1) ? "x-user" : "x-users", usersCount, false) %>' />
		</p>
	</c:if>

	<c:if test="<%= organizationsCount > 0 %>">
		<p>
			<aui:a href='<%= HttpUtil.addParameter(assignMembersURL.toString(), "tabs1", "organizations") %>' label='<%= LanguageUtil.format(request, (organizationsCount == 1) ? "x-organization" : "x-organizations", organizationsCount, false) %>' />
		</p>
	</c:if>

	<c:if test="<%= userGroupsCount > 0 %>">
		<p>
			<aui:a href='<%= HttpUtil.addParameter(assignMembersURL.toString(), "tabs1", "user-groups") %>' label='<%= LanguageUtil.format(request, (userGroupsCount == 1) ? "x-user-groups" : "x-user-groups", userGroupsCount, false) %>' />
		</p>
	</c:if>

	<%
	int pendingRequests = 0;

	if (group.getType() == GroupConstants.TYPE_SITE_RESTRICTED) {
		pendingRequests = MembershipRequestLocalServiceUtil.searchCount(group.getGroupId(), MembershipRequestConstants.STATUS_PENDING);
	}
	%>

	<c:if test="<%= pendingRequests > 0 %>">
		<h5><liferay-ui:message key="request-pending" /></h5>

		<liferay-portlet:renderURL portletName="<%= portletId %>" var="viewMembershipRequestsURL">
			<portlet:param name="mvcPath" value="/view_membership_requests.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
		</liferay-portlet:renderURL>

		<p>
			<aui:a href="<%= viewMembershipRequestsURL %>" label='<%= LanguageUtil.format(request, (pendingRequests == 1) ? "x-request-pending" : "x-requests-pending", pendingRequests, false) %>' />
		</p>
	</c:if>

	<h5><liferay-ui:message key="membership-type" /></h5>

	<p>
		<liferay-ui:message key="<%= GroupConstants.getTypeLabel(group.getType()) %>" />
	</p>

	<c:if test="<%= Validator.isNotNull(group.getDescription()) %>">
		<h5><liferay-ui:message key="description" /></h5>

		<p>
			<%= HtmlUtil.escape(group.getDescription()) %>
		</p>
	</c:if>

	<liferay-ui:asset-categories-summary
		className="<%= Group.class.getName() %>"
		classPK="<%= groupId %>"
	/>

	<liferay-ui:asset-tags-summary
		className="<%= Group.class.getName() %>"
		classPK="<%= groupId %>"
	/>
</div>