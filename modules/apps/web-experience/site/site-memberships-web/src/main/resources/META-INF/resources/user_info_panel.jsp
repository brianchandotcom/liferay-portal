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
List<User> users = (List<User>)request.getAttribute(SiteMembershipWebKeys.USERS);

Group group = siteMembershipsDisplayContext.getGroup();
%>

<c:choose>
	<c:when test="<%= ListUtil.isEmpty(users) %>">
		<div class="sidebar-header">
			<h4 class="sidebar-title">
				<liferay-ui:message key="membership-type" />: <liferay-ui:message key="<%= GroupConstants.getTypeLabel(group.getType()) %>" />

				<%
				int pendingRequests = 0;

				if (group.getType() == GroupConstants.TYPE_SITE_RESTRICTED) {
					pendingRequests = MembershipRequestLocalServiceUtil.searchCount(group.getGroupId(), MembershipRequestConstants.STATUS_PENDING);
				}
				%>

				<c:if test="<%= pendingRequests > 0 %>">
					<portlet:renderURL var="viewMembershipRequestsURL">
						<portlet:param name="mvcPath" value="/view_membership_requests.jsp" />
						<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
					</portlet:renderURL>

					<aui:a cssClass="badge badge-primary badge-sm" href="<%= viewMembershipRequestsURL %>" label="<%= String.valueOf(pendingRequests) %>" title='<%= LanguageUtil.format(request, "there-are-x-membership-requests-pending", String.valueOf(pendingRequests), false) %>' />
				</c:if>
			</h4>

			<h6 class="sidebar-caption">
				<liferay-ui:message key='<%= "membership-type-" + GroupConstants.getTypeLabel(group.getType()) + "-help" %>' />
			</h6>
		</div>

		<aui:nav-bar cssClass="navbar-no-collapse" markupView="lexicon">
			<aui:nav collapsible="<%= false %>" cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<dl class="sidebar-block">
				<dt class="h5"><liferay-ui:message key="num-of-users" /></dt>

				<%
				LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

				userParams.put("inherit", Boolean.TRUE);
				userParams.put("usersGroups", Long.valueOf(siteMembershipsDisplayContext.getGroupId()));
				%>

				<dd class="h6 sidebar-caption">
					<%= UserLocalServiceUtil.searchCount(company.getCompanyId(), StringPool.BLANK, WorkflowConstants.STATUS_APPROVED, userParams) %>
				</dd>
			</dl>
		</div>
	</c:when>
	<c:when test="<%= ListUtil.isNotEmpty(users) && (users.size() == 1) %>">

		<%
		User curUser = users.get(0);
		%>

		<div class="sidebar-header">
			<h4 class="sidebar-title">
				<%= curUser.getFullName() %>
			</h4>

			<h5>
				<%= curUser.getScreenName() %>
			</h5>
		</div>

		<aui:nav-bar cssClass="navbar-no-collapse" markupView="lexicon">
			<aui:nav collapsible="<%= false %>" cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">

			<%
			List<String> names = new ArrayList<String>();

			names.addAll(SitesUtil.getOrganizationNames(group, curUser));

			names.addAll(SitesUtil.getUserGroupNames(group, curUser));
			%>

			<c:if test="<%= ListUtil.isNotEmpty(names) %>">
				<p>
					<c:choose>
						<c:when test="<%= names.size() == 1 %>">
							<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(group.getDescriptiveName(locale)), HtmlUtil.escape(names.get(0))} %>" key="this-user-is-a-member-of-x-because-he-belongs-to-x" translateArguments="<%= false %>" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message arguments='<%= new Object[] {HtmlUtil.escape(group.getDescriptiveName(locale)), HtmlUtil.escape(StringUtil.merge(names.subList(0, names.size() - 1).toArray(new String[names.size() - 1]), ", ")), HtmlUtil.escape(names.get(names.size() - 1))} %>' key="this-user-is-a-member-of-x-because-he-belongs-to-x-and-x" translateArguments="<%= false %>" />
						</c:otherwise>
					</c:choose>
				</p>
			</c:if>

			<%
			String portraitURL = curUser.getPortraitURL(themeDisplay);
			%>

			<c:if test="<%= Validator.isNotNull(portraitURL) %>">
				<div class="crop-img crop-img-center crop-img-middle sidebar-panel">
					<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="thumbnail" />" class="img-responsive" src="<%= portraitURL %>" />
				</div>
			</c:if>

			<dl class="sidebar-block">
				<dt class="h5"><liferay-ui:message key="email" /></dt>

				<dd class="h6 sidebar-caption">
					<%= curUser.getEmailAddress() %>
				</dd>

				<%
				List<UserGroupRole> userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(curUser.getUserId(), siteMembershipsDisplayContext.getGroupId());

				List<Team> teams = TeamLocalServiceUtil.getUserOrUserGroupTeams(siteMembershipsDisplayContext.getGroupId(), curUser.getUserId());

				List<String> siteRolesAndTeamsNames = ListUtil.toList(userGroupRoles, UsersAdmin.USER_GROUP_ROLE_TITLE_ACCESSOR);

				siteRolesAndTeamsNames.addAll(ListUtil.toList(teams, Team.NAME_ACCESSOR));
				%>

				<c:if test="<%= !ListUtil.isEmpty(siteRolesAndTeamsNames) %>">
					<dt class="h5"><liferay-ui:message key="site-roles-and-teams" /></dt>

					<dd class="h6 sidebar-caption">
						<%= HtmlUtil.escape(StringUtil.merge(siteRolesAndTeamsNames, StringPool.COMMA_AND_SPACE)) %>
					</dd>
				</c:if>
		</div>
	</c:when>
	<c:when test="<%= ListUtil.isNotEmpty(users) && (users.size() > 1) %>">
		<div class="sidebar-header">
			<h4 class="sidebar-title"><liferay-ui:message arguments="<%= users.size() %>" key="x-items-are-selected" /></h4>
		</div>

		<aui:nav-bar cssClass="navbar-no-collapse" markupView="lexicon">
			<aui:nav collapsible="<%= false %>" cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<h5><liferay-ui:message arguments="<%= users.size() %>" key="x-items-are-selected" /></h5>
		</div>
	</c:when>
</c:choose>