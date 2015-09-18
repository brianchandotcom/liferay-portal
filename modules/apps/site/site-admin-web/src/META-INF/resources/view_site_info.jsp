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

int pendingRequests = 0;

if (group.getType() == GroupConstants.TYPE_SITE_RESTRICTED) {
	pendingRequests = MembershipRequestLocalServiceUtil.searchCount(group.getGroupId(), MembershipRequestConstants.STATUS_PENDING);
}

String portletId = PortletProviderUtil.getPortletId(MembershipRequest.class.getName(), PortletProvider.Action.VIEW);

LayoutSet layoutSet = group.getPublicLayoutSet();
%>

<div class="lfr-asset-summary">
	<img alt="<%= HtmlUtil.escapeAttribute(group.getDescriptiveName()) %>" class="avatar" src='<%= themeDisplay.getPathImage() + "/layout_set_logo?img_id=" + layoutSet.getLogoId() + "&t=" + WebServerServletTokenUtil.getToken(layoutSet.getLogoId()) %>' />

	<div class="lfr-asset-name">
		<h4><%= HtmlUtil.escape(group.getDescriptiveName()) %></h4>
	</div>
</div>

<%
request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

request.setAttribute("view_entries.jspf-site", group);
%>

<liferay-util:include page="/site_action.jsp" servletContext="<%= application %>" />

<c:if test="<%= group.isOrganization() %>">

	<%
	Organization groupOrganization = OrganizationLocalServiceUtil.getOrganization(group.getOrganizationId());
	%>

	<div class="organizations-msg-info portlet-msg">
		<i class="icon-group"></i>

		<liferay-ui:message arguments="<%= new String[] {groupOrganization.getName(), LanguageUtil.get(request, groupOrganization.getType())} %>" key="this-site-belongs-to-x-which-is-an-organization-of-type-x" translateArguments="<%= false %>" />
	</div>
</c:if>

<div class="membership-info">
	<dl>
		<dt class="first">
			<liferay-ui:message key="members" />:
		</dt>

		<dd class="members-info">
			<c:if test="<%= (usersCount == 0) && (organizationsCount == 0) && (userGroupsCount == 0) %>">
				<liferay-ui:message key="none" />
			</c:if>

			<liferay-portlet:renderURL doAsGroupId="<%= groupId %>" portletName="<%= portletId %>" var="assignMembersURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</liferay-portlet:renderURL>

			<c:if test="<%= usersCount > 0 %>">
				<aui:a href='<%= HttpUtil.addParameter(assignMembersURL.toString(), "tabs1", "users") %>' label='<%= LanguageUtil.format(request, (usersCount == 1) ? "x-user" : "x-users", usersCount, false) %>' />
			</c:if>

			<c:if test="<%= organizationsCount > 0 %>">
				<aui:a href='<%= HttpUtil.addParameter(assignMembersURL.toString(), "tabs1", "organizations") %>' label='<%= LanguageUtil.format(request, (organizationsCount == 1) ? "x-organization" : "x-organizations", organizationsCount, false) %>' />
			</c:if>

			<c:if test="<%= userGroupsCount > 0 %>">
				<aui:a href='<%= HttpUtil.addParameter(assignMembersURL.toString(), "tabs1", "user-groups") %>' label='<%= LanguageUtil.format(request, (userGroupsCount == 1) ? "x-user-groups" : "x-user-groups", userGroupsCount, false) %>' />
			</c:if>
		</dd>

		<c:if test="<%= pendingRequests > 0 %>">
			<dt class="hide-accessible">
				<liferay-ui:message key="request-pending" />
			</dt>

			<dd class="membership-requests">
				<liferay-portlet:renderURL portletName="<%= portletId %>" var="viewMembershipRequestsURL">
					<portlet:param name="mvcPath" value="/view_membership_requests.jsp" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
				</liferay-portlet:renderURL>

				<aui:a href="<%= viewMembershipRequestsURL %>" label='<%= LanguageUtil.format(request, (pendingRequests == 1) ? "x-request-pending" : "x-requests-pending", pendingRequests, false) %>' />
			</dd>
		</c:if>

		<dt>
			<liferay-ui:message key="membership-type" />:
		</dt>

		<dd class="last">
			<%= LanguageUtil.get(request, GroupConstants.getTypeLabel(group.getType())) %>
		</dd>
	</dl>
</div>

<c:if test="<%= Validator.isNotNull(group.getDescription()) %>">
	<div class="description">
		<%= HtmlUtil.escape(group.getDescription()) %>
	</div>
</c:if>

<div class="site-categorization">
	<liferay-ui:asset-categories-summary
		className="<%= Group.class.getName() %>"
		classPK="<%= groupId %>"
		/>

	<liferay-ui:asset-tags-summary
		className="<%= Group.class.getName() %>"
		classPK="<%= groupId %>"
		/>
</div>