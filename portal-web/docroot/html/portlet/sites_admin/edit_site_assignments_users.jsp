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
String tabs1 = (String)request.getAttribute("edit_site_assignments.jsp-tabs1");
String tabs2 = (String)request.getAttribute("edit_site_assignments.jsp-tabs2");

int cur = (Integer)request.getAttribute("edit_site_assignments.jsp-cur");

String redirect = ParamUtil.getString(request, "redirect");

Group group = (Group)request.getAttribute("edit_site_assignments.jsp-group");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_site_assignments.jsp-portletURL");

PortletURL viewUsersURL = renderResponse.createRenderURL();

viewUsersURL.setParameter("struts_action", "/sites_admin/edit_site_assignments");
viewUsersURL.setParameter("tabs1", "users");
viewUsersURL.setParameter("tabs2", tabs2);
viewUsersURL.setParameter("redirect", redirect);
viewUsersURL.setParameter("groupId", String.valueOf(group.getGroupId()));

UserGroupChecker userGroupChecker = null;

if (!tabs1.equals("summary") && !tabs2.equals("current")) {
	userGroupChecker = new UserGroupChecker(renderResponse, group);
}

String emptyResultsMessage = UserSearch.EMPTY_RESULTS_MESSAGE;

if (tabs2.equals("current")) {
	emptyResultsMessage ="no-user-was-found-that-is-a-direct-member-of-this-site";
}

UserSearch userSearch = new UserSearch(renderRequest, viewUsersURL);

userSearch.setEmptyResultsMessage(emptyResultsMessage);
%>

<aui:input name="tabs1" type="hidden" value="users" />
<aui:input name="addUserIds" type="hidden" />
<aui:input name="removeUserIds" type="hidden" />

<liferay-ui:membership-policy-error />

<liferay-ui:search-container
	rowChecker="<%= userGroupChecker %>"
	searchContainer="<%= userSearch %>"
>
	<c:if test='<%= !tabs1.equals("summary") %>'>
		<liferay-ui:search-form
			page="/html/portlet/users_admin/user_search.jsp"
		/>

		<div class="separator"><!-- --></div>
	</c:if>

	<%
	UserSearchTerms searchTerms = (UserSearchTerms)searchContainer.getSearchTerms();

	LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

	if (tabs1.equals("summary") || tabs2.equals("current")) {
		userParams.put("inherit", Boolean.TRUE);
		userParams.put("usersGroups", new Long(group.getGroupId()));
	}
	%>

	<liferay-ui:search-container-results>
		<c:choose>
			<c:when test='<%= tabs1.equals("summary") || tabs2.equals("current") || !group.isLimitedToParentSiteMembers() %>'>
				<%@ include file="/html/portlet/users_admin/user_search_results.jspf" %>
			</c:when>
			<c:otherwise>

				<%
				results = UserLocalServiceUtil.getGroupUsers(group.getParentGroupId(), searchContainer.getStart(), searchContainer.getEnd());
				total = UserLocalServiceUtil.getGroupUsersCount(group.getParentGroupId());

				pageContext.setAttribute("results", results);
				pageContext.setAttribute("total", total);
				%>

			</c:otherwise>
		</c:choose>
	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.User"
		escapedModel="<%= true %>"
		keyProperty="userId"
		modelVar="user2"
		rowIdProperty="screenName"
	>
		<liferay-ui:search-container-row-parameter
			name="group"
			value="<%= group %>"
		/>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			name="name"
		>

			<%
			buffer.append(HtmlUtil.escape(user2.getFullName()));

			List<String> names = new ArrayList<String>();

			boolean organizationUser = SitesUtil.isOrganizationUser(company.getCompanyId(), group, user2, names);

			row.setParameter("organizationUser", organizationUser);

			boolean userGroupUser = SitesUtil.isUserGroupUser(company.getCompanyId(), group, user2, names);

			row.setParameter("userGroupUser", userGroupUser);

			String message = StringPool.BLANK;

			if (organizationUser || userGroupUser) {
				if (names.size() == 1) {
					message = LanguageUtil.format(pageContext, "this-user-is-a-member-of-x-because-he-belongs-to-x", new Object[] {HtmlUtil.escape(group.getDescriptiveName(locale)), names.get(0)});
				}
				else {
					message = LanguageUtil.format(pageContext, "this-user-is-a-member-of-x-because-he-belongs-to-x-and-x", new Object[] {HtmlUtil.escape(group.getDescriptiveName(locale)), StringUtil.merge(names.subList(0, names.size() - 1).toArray(new String[names.size() - 1]), ", "), names.get(names.size() - 1)});
				}
			%>

				<liferay-util:buffer var="iconHelp">
					<liferay-ui:icon-help message="<%= message %>" />
				</liferay-util:buffer>

			<%
				buffer.append(iconHelp);
			}
			%>

		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			name="screen-name"
			orderable="<%= true %>"
			property="screenName"
		/>

		<c:if test='<%= tabs1.equals("summary") || tabs2.equals("current") %>'>
			<liferay-ui:search-container-column-text
				buffer="buffer"
				name="site-roles-and-teams"
			>

				<%
				List<UserGroupRole> userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(user2.getUserId(), group.getGroupId());

				for (int i = 0; i < userGroupRoles.size(); i++) {
					UserGroupRole userGroupRole = userGroupRoles.get(i);

					Role role = RoleLocalServiceUtil.getRole(userGroupRole.getRoleId());

					buffer.append(HtmlUtil.escape(role.getTitle(locale)));

					if ((i + 1) < userGroupRoles.size()) {
						buffer.append(StringPool.COMMA_AND_SPACE);
					}
				}

				List<Team> teams = TeamLocalServiceUtil.getUserTeams(user2.getUserId(), group.getGroupId());

				if (!userGroupRoles.isEmpty()) {
					buffer.append(StringPool.COMMA_AND_SPACE);
				}

				for (int i = 0; i < teams.size(); i++) {
					Team team = teams.get(i);

					buffer.append(HtmlUtil.escape(team.getName()));

					if ((i + 1) < teams.size()) {
						buffer.append(StringPool.COMMA_AND_SPACE);
					}
				}
				%>

			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/html/portlet/sites_admin/user_action.jsp"
			/>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-util:buffer var="formButton">
		<c:choose>
			<c:when test='<%= tabs2.equals("current") %>'>

				<%
				viewUsersURL.setParameter("tabs2", "available");
				%>

				<aui:button-row>
					<aui:button href="<%= viewUsersURL.toString() %>" value="assign-users" />
				</aui:button-row>

				<%
				viewUsersURL.setParameter("tabs2", "current");
				%>

			</c:when>
			<c:otherwise>

				<%
				portletURL.setParameter("tabs2", "current");

				String taglibOnClick = renderResponse.getNamespace() + "updateGroupUsers('" + portletURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur + "');";
				%>

				<aui:button-row>
					<aui:button onClick="<%= taglibOnClick %>" value="save" />
				</aui:button-row>
			</c:otherwise>
		</c:choose>
	</liferay-util:buffer>

	<c:choose>
		<c:when test='<%= tabs1.equals("summary") && (total > 0) %>'>
			<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" persistState="<%= true %>" title='<%= LanguageUtil.format(pageContext, (total > 1) ? "x-users" : "x-user", total) %>'>
				<span class="form-search">
					<aui:input inlineField="<%= true %>" label="" name='<%= DisplayTerms.KEYWORDS + "_users" %>' size="30" value="" />

					<aui:button type="submit" value="search" />
				</span>

				<br /><br />

				<liferay-ui:search-iterator paginate="<%= false %>" />

				<c:if test="<%= total > userSearch.getDelta() %>">
					<a href="<%= viewUsersURL %>"><liferay-ui:message key="view-more" /> &raquo;</a>
				</c:if>
			</liferay-ui:panel>

			<div class="separator"><!-- --></div>
		</c:when>
		<c:when test='<%= !tabs1.equals("summary") %>'>
			<c:if test="<%= total > userSearch.getDelta() %>">
				<%= formButton %>
			</c:if>

			<liferay-ui:search-iterator />

			<%= formButton %>
		</c:when>
	</c:choose>
</liferay-ui:search-container>