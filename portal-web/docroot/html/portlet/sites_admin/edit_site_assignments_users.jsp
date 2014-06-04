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
viewUsersURL.setParameter("redirect", currentURL);
viewUsersURL.setParameter("groupId", String.valueOf(group.getGroupId()));

UserGroupChecker userGroupChecker = null;

if (!tabs1.equals("summary") && !tabs2.equals("current")) {
	userGroupChecker = new UserGroupChecker(renderResponse, group);
}

String emptyResultsMessage = UserSearch.EMPTY_RESULTS_MESSAGE;

if (tabs2.equals("current")) {
	emptyResultsMessage ="no-user-was-found-that-is-a-direct-member-of-this-site";
}

SearchContainer searchContainer = new UserSearch(renderRequest, viewUsersURL);

searchContainer.setEmptyResultsMessage(emptyResultsMessage);
%>

<aui:input name="tabs1" type="hidden" value="users" />
<aui:input name="addUserIds" type="hidden" />
<aui:input name="removeUserIds" type="hidden" />

<liferay-ui:membership-policy-error />

<liferay-ui:search-container
	rowChecker="<%= userGroupChecker %>"
	searchContainer="<%= searchContainer %>"
	var="userSearchContainer"
>
	<c:if test='<%= !tabs1.equals("summary") %>'>
		<liferay-ui:search-form
			page="/html/portlet/users_admin/user_search.jsp"
		/>

		<div class="separator"><!-- --></div>
	</c:if>

	<%
	UserSearchTerms searchTerms = (UserSearchTerms)userSearchContainer.getSearchTerms();

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
				total = UserLocalServiceUtil.getGroupUsersCount(group.getParentGroupId());

				searchContainer.setTotal(total);

				results = UserLocalServiceUtil.getGroupUsers(group.getParentGroupId(), userSearchContainer.getStart(), userSearchContainer.getEnd());

				searchContainer.setResults(results);
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
			name="name"
		>

			<%= user2.getFullName() %>

			<%
			List<String> names = new ArrayList<String>();

			List<String> organizationNames = SitesUtil.getOrganizationNames(group, user2);

			names.addAll(organizationNames);

			boolean organizationUser = !organizationNames.isEmpty();

			row.setParameter("organizationUser", organizationUser);

			List<String> userGroupNames = SitesUtil.getUserGroupNames(group, user2);

			names.addAll(userGroupNames);

			boolean userGroupUser = !userGroupNames.isEmpty();

			row.setParameter("userGroupUser", userGroupUser);
			%>

			<c:if test="<%= organizationUser || userGroupUser %>">
				<c:choose>
					<c:when test="<%= names.size() == 1 %>">
						<liferay-ui:icon-help message='<%= LanguageUtil.format(pageContext, "this-user-is-a-member-of-x-because-he-belongs-to-x", new Object[] {HtmlUtil.escape(group.getDescriptiveName(locale)), names.get(0)}, false) %>' />
					</c:when>
					<c:otherwise>
						<liferay-ui:icon-help message='<%= LanguageUtil.format(pageContext, "this-user-is-a-member-of-x-because-he-belongs-to-x-and-x", new Object[] {HtmlUtil.escape(group.getDescriptiveName(locale)), StringUtil.merge(names.subList(0, names.size() - 1).toArray(new String[names.size() - 1]), ", "), names.get(names.size() - 1)}, false) %>' />
					</c:otherwise>
				</c:choose>
			</c:if>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			name="screen-name"
			orderable="<%= true %>"
			property="screenName"
		/>

		<c:if test='<%= tabs1.equals("summary") || tabs2.equals("current") %>'>

			<%
			List<UserGroupRole> userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(user2.getUserId(), group.getGroupId());

			List<Team> teams = TeamLocalServiceUtil.getUserTeams(user2.getUserId(), group.getGroupId());

			List<String> names = ListUtil.toList(userGroupRoles, UsersAdmin.USER_GROUP_ROLE_TITLE_ACCESSOR);

			names.addAll(ListUtil.toList(teams, Team.NAME_ACCESSOR));
			%>

			<liferay-ui:search-container-column-text
				name="site-roles-and-teams"
				value="<%= StringUtil.merge(names, StringPool.COMMA_AND_SPACE) %>"
			/>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/html/portlet/sites_admin/user_action.jsp"
			/>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-util:buffer var="formButton">
		<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ASSIGN_MEMBERS) %>">
			<c:choose>
				<c:when test='<%= tabs2.equals("current") %>'>

					<%
					viewUsersURL.setParameter("tabs2", "available");
					viewUsersURL.setParameter("redirect", currentURL);
					%>

					<liferay-ui:icon
						iconCssClass="icon-user"
						label="<%= true %>"
						message="assign-users"
						url="<%= viewUsersURL.toString() %>"
					/>

					<%
					viewUsersURL.setParameter("tabs2", "current");
					%>

				</c:when>
				<c:otherwise>

					<%
					portletURL.setParameter("tabs2", "current");
					portletURL.setParameter("cur", String.valueOf(cur));

					String taglibOnClick = renderResponse.getNamespace() + "updateGroupUsers('" + redirect + "');";
					%>

					<aui:button-row>
						<aui:button onClick="<%= taglibOnClick %>" primary="<%= true %>" value="save" />
					</aui:button-row>
				</c:otherwise>
			</c:choose>
		</c:if>
	</liferay-util:buffer>

	<c:choose>
		<c:when test='<%= tabs1.equals("summary") && (total > 0) %>'>
			<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" persistState="<%= true %>" title='<%= LanguageUtil.format(pageContext, (total > 1) ? "x-users" : "x-user", total, false) %>'>
				<span class="form-search">
					<liferay-ui:input-search name='<%= DisplayTerms.KEYWORDS + "_users" %>' />
				</span>

				<liferay-ui:search-iterator paginate="<%= false %>" />

				<c:if test="<%= total > searchContainer.getDelta() %>">
					<a href="<%= viewUsersURL %>"><liferay-ui:message key="view-more" /> &raquo;</a>
				</c:if>
			</liferay-ui:panel>
		</c:when>
		<c:when test='<%= !tabs1.equals("summary") %>'>
			<c:if test="<%= PropsValues.SEARCH_CONTAINER_SHOW_PAGINATION_TOP && (results.size() > PropsValues.SEARCH_CONTAINER_SHOW_PAGINATION_TOP_DELTA) %>">
				<%= formButton %>
			</c:if>

			<liferay-ui:search-iterator />

			<%= formButton %>
		</c:when>
	</c:choose>
</liferay-ui:search-container>