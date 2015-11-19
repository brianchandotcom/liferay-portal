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

String orderByCol = ParamUtil.getString(request, "orderByCol", "first-name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_site_assignments.jsp-portletURL");

PortletURL viewUsersURL = renderResponse.createRenderURL();

viewUsersURL.setParameter("mvcPath", "/view.jsp");
viewUsersURL.setParameter("tabs1", "users");
viewUsersURL.setParameter("tabs2", "current");
viewUsersURL.setParameter("redirect", currentURL);
viewUsersURL.setParameter("groupId", String.valueOf(group.getGroupId()));

SearchContainer searchContainer = new UserSearch(renderRequest, PortletURLUtil.clone(viewUsersURL, renderResponse));

searchContainer.setEmptyResultsMessage("no-user-was-found-that-is-a-direct-member-of-this-site");
%>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"first-name", "screen-name"} %>'
			portletURL="<%= PortletURLUtil.clone(viewUsersURL, renderResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="tabs1" type="hidden" value="users" />
	<aui:input name="tabs2" type="hidden" value="current" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="groupId" type="hidden" value="<%= String.valueOf(group.getGroupId()) %>" />
	<aui:input name="addUserIds" type="hidden" />
	<aui:input name="removeUserIds" type="hidden" />

	<liferay-ui:membership-policy-error />

	<liferay-ui:search-container
		searchContainer="<%= searchContainer %>"
		var="userSearchContainer"
	>

		<%
		UserSearchTerms searchTerms = (UserSearchTerms)userSearchContainer.getSearchTerms();

		LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

		userParams.put("inherit", Boolean.TRUE);
		userParams.put("usersGroups", Long.valueOf(group.getGroupId()));
		%>

		<liferay-ui:search-container-results>

			<%
			total = UserLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), searchTerms.getStatus(), userParams);

			userSearchContainer.setTotal(total);

			results = UserLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), searchTerms.getStatus(), userParams, userSearchContainer.getStart(), userSearchContainer.getEnd(), userSearchContainer.getOrderByComparator());

			userSearchContainer.setResults(results);
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.User"
			escapedModel="<%= true %>"
			keyProperty="userId"
			modelVar="user2"
			rowIdProperty="screenName"
		>

			<%
			boolean selectUsers = false;
			%>

			<%@ include file="/user_columns.jspf" %>
		</liferay-ui:search-container-row>

		<c:choose>
			<c:when test='<%= tabs1.equals("summary") && (total > 0) %>'>
				<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" persistState="<%= true %>" title='<%= LanguageUtil.format(request, (total > 1) ? "x-users" : "x-user", total, false) %>'>
					<liferay-ui:search-iterator markupView="lexicon" paginate="<%= false %>" />

					<c:if test="<%= total > searchContainer.getDelta() %>">
						<a href="<%= HtmlUtil.escapeAttribute(viewUsersURL.toString()) %>"><liferay-ui:message key="view-more" /> &raquo;</a>
					</c:if>
				</liferay-ui:panel>
			</c:when>
			<c:when test='<%= !tabs1.equals("summary") %>'>
				<liferay-ui:search-iterator markupView="lexicon" />

				<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ASSIGN_MEMBERS) %>">

					<%
					viewUsersURL.setParameter("tabs2", "available");
					viewUsersURL.setParameter("redirect", currentURL);
					%>

					<liferay-frontend:add-menu>
						<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "assign-users") %>' url="<%= viewUsersURL.toString() %>" />
					</liferay-frontend:add-menu>
				</c:if>
			</c:when>
		</c:choose>
	</liferay-ui:search-container>
</aui:form>