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

Group group = (Group)request.getAttribute("edit_site_assignments.jsp-group");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_site_assignments.jsp-portletURL");

PortletURL viewUserGroupsURL = renderResponse.createRenderURL();

viewUserGroupsURL.setParameter("struts_action", "/sites_admin/edit_site_assignments");
viewUserGroupsURL.setParameter("tabs1", "user-groups");
viewUserGroupsURL.setParameter("tabs2", tabs2);
viewUserGroupsURL.setParameter("redirect", currentURL);
viewUserGroupsURL.setParameter("groupId", String.valueOf(group.getGroupId()));

UserGroupGroupChecker userGroupGroupChecker = null;

if (!tabs1.equals("summary") && !tabs2.equals("current")) {
	userGroupGroupChecker = new UserGroupGroupChecker(renderResponse, group);
}

String emptyResultsMessage = UserGroupSearch.EMPTY_RESULTS_MESSAGE;

if (tabs2.equals("current")) {
	emptyResultsMessage ="no-user-group-was-found-that-is-a-member-of-this-site";
}

UserGroupSearch userGroupSearch = new UserGroupSearch(renderRequest, viewUserGroupsURL);

userGroupSearch.setEmptyResultsMessage(emptyResultsMessage);
%>

<aui:input name="tabs1" type="hidden" value="user-groups" />
<aui:input name="addUserGroupIds" type="hidden" />
<aui:input name="removeUserGroupIds" type="hidden" />

<liferay-ui:search-container
	rowChecker="<%= userGroupGroupChecker %>"
	searchContainer="<%= userGroupSearch %>"
>
	<c:if test='<%= !tabs1.equals("summary") %>'>
		<liferay-ui:search-form
			page="/html/portlet/user_groups_admin/user_group_search.jsp"
		/>

		<div class="separator"><!-- --></div>
	</c:if>

	<%
	UserGroupDisplayTerms searchTerms = (UserGroupDisplayTerms)searchContainer.getSearchTerms();

	LinkedHashMap<String, Object> userGroupParams = new LinkedHashMap<String, Object>();

	if (tabs1.equals("summary") || tabs2.equals("current")) {
		userGroupParams.put("userGroupsGroups", new Long(group.getGroupId()));
	}

	total = UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams);

	searchContainer.setTotal(total);
	%>

	<liferay-ui:search-container-results
		results="<%= UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.UserGroup"
		escapedModel="<%= true %>"
		keyProperty="userGroupId"
		modelVar="userGroup"
	>
		<liferay-ui:search-container-row-parameter
			name="group"
			value="<%= group %>"
		/>

		<liferay-ui:search-container-column-text
			name="name"
			orderable="<%= true %>"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			name="description"
			orderable="<%= true %>"
			property="description"
		/>

		<c:if test='<%= tabs1.equals("summary") || tabs2.equals("current") %>'>
			<liferay-ui:search-container-column-text
				buffer="buffer"
				name="site-roles"
			>

				<%
				List<UserGroupGroupRole> userGroupGroupRoles = UserGroupGroupRoleLocalServiceUtil.getUserGroupGroupRoles(userGroup.getUserGroupId(), group.getGroupId());

				for (int i = 0; i < userGroupGroupRoles.size(); i++) {
					UserGroupGroupRole userGroupGroupRole = userGroupGroupRoles.get(i);

					Role role = RoleLocalServiceUtil.getRole(userGroupGroupRole.getRoleId());

					buffer.append(HtmlUtil.escape(role.getTitle(locale)));

					if ((i + 1) < userGroupGroupRoles.size()) {
						buffer.append(StringPool.COMMA_AND_SPACE);
					}
				}
				%>

			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/html/portlet/sites_admin/user_group_action.jsp"
			/>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-util:buffer var="formButton">
		<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ASSIGN_MEMBERS) %>">
			<c:choose>
				<c:when test='<%= tabs2.equals("current") %>'>

					<%
					viewUserGroupsURL.setParameter("tabs2", "available");
					%>

					<liferay-ui:icon
						image="../aui/globe"
						label="<%= true %>"
						message="assign-user-groups"
						url="<%= viewUserGroupsURL.toString() %>"
					/>

					<%
					viewUserGroupsURL.setParameter("tabs2", "current");
					%>

				</c:when>
				<c:otherwise>

					<%
					portletURL.setParameter("tabs2", "current");

					String taglibOnClick = renderResponse.getNamespace() + "updateGroupUserGroups('" + portletURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur + "');";
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
			<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" persistState="<%= true %>" title='<%= LanguageUtil.format(pageContext, (total > 1) ? "x-user-groups" : "x-user-group", total) %>'>
				<span class="form-search">
					<liferay-ui:input-search name='<%= DisplayTerms.KEYWORDS + "_user_groups" %>' />
				</span>

				<liferay-ui:search-iterator paginate="<%= false %>" />

				<c:if test="<%= total > userGroupSearch.getDelta() %>">
					<a href="<%= viewUserGroupsURL %>"><liferay-ui:message key="view-more" /> &raquo;</a>
				</c:if>
			</liferay-ui:panel>
		</c:when>
		<c:when test='<%= !tabs1.equals("summary") %>'>
			<c:if test="<%= total > userGroupSearch.getDelta() %>">
				<%= formButton %>
			</c:if>

			<liferay-ui:search-iterator />

			<%= formButton %>
		</c:when>
	</c:choose>
</liferay-ui:search-container>