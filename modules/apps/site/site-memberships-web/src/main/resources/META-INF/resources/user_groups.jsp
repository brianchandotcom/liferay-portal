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
String tabs1 = (String)request.getAttribute("edit_site_assignments.jsp-tabs1");

Group group = (Group)request.getAttribute("edit_site_assignments.jsp-group");

String orderByCol = ParamUtil.getString(request, "orderByCol", "name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_site_assignments.jsp-portletURL");

PortletURL viewUserGroupsURL = renderResponse.createRenderURL();

viewUserGroupsURL.setParameter("mvcPath", "/view.jsp");
viewUserGroupsURL.setParameter("tabs1", "user-groups");
viewUserGroupsURL.setParameter("tabs2", "current");
viewUserGroupsURL.setParameter("redirect", currentURL);
viewUserGroupsURL.setParameter("groupId", String.valueOf(group.getGroupId()));

UserGroupSearch userGroupSearch = new UserGroupSearch(renderRequest, PortletURLUtil.clone(viewUserGroupsURL, renderResponse));

userGroupSearch.setEmptyResultsMessage("no-user-group-was-found-that-is-a-member-of-this-site");
%>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"name", "description"} %>'
			portletURL="<%= PortletURLUtil.clone(viewUserGroupsURL, renderResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="tabs1" type="hidden" value="user-groups" />
	<aui:input name="tabs2" type="hidden" value="current" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="groupId" type="hidden" value="<%= String.valueOf(group.getGroupId()) %>" />
	<aui:input name="addUserGroupIds" type="hidden" />
	<aui:input name="removeUserGroupIds" type="hidden" />

	<liferay-ui:search-container
		searchContainer="<%= userGroupSearch %>"
	>

		<%
		UserGroupDisplayTerms searchTerms = (UserGroupDisplayTerms)searchContainer.getSearchTerms();

		LinkedHashMap<String, Object> userGroupParams = new LinkedHashMap<String, Object>();

		userGroupParams.put("userGroupsGroups", Long.valueOf(group.getGroupId()));
		%>

		<liferay-ui:search-container-results>

			<%
			total = UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams);

			searchContainer.setTotal(total);

			results = UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

			searchContainer.setResults(results);
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.UserGroup"
			escapedModel="<%= true %>"
			keyProperty="userGroupId"
			modelVar="userGroup"
		>

			<%
			boolean selectUserGroup = false;
			%>

			<%@ include file="/user_group_columns.jspf" %>
		</liferay-ui:search-container-row>

		<c:choose>
			<c:when test='<%= tabs1.equals("summary") && (total > 0) %>'>
				<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" persistState="<%= true %>" title='<%= LanguageUtil.format(request, (total > 1) ? "x-user-groups" : "x-user-group", total, false) %>'>
					<liferay-ui:search-iterator markupView="lexicon" paginate="<%= false %>" />

					<c:if test="<%= total > userGroupSearch.getDelta() %>">
						<a href="<%= viewUserGroupsURL %>"><liferay-ui:message key="view-more" /> &raquo;</a>
					</c:if>
				</liferay-ui:panel>
			</c:when>
			<c:when test='<%= !tabs1.equals("summary") %>'>
				<liferay-ui:search-iterator markupView="lexicon" />

				<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ASSIGN_MEMBERS) %>">

					<%
					viewUserGroupsURL.setParameter("tabs2", "available");
					%>

					<liferay-frontend:add-menu>
						<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "assign-user-groups") %>' url="<%= viewUserGroupsURL.toString() %>" />
					</liferay-frontend:add-menu>
				</c:if>
			</c:when>
		</c:choose>
	</liferay-ui:search-container>
</aui:form>