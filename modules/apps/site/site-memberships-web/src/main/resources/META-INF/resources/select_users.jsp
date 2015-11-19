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
int cur = (Integer)request.getAttribute("edit_site_assignments.jsp-cur");

Group group = (Group)request.getAttribute("edit_site_assignments.jsp-group");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_site_assignments.jsp-portletURL");

PortletURL viewUsersURL = renderResponse.createRenderURL();

viewUsersURL.setParameter("mvcPath", "/view.jsp");
viewUsersURL.setParameter("tabs1", "users");
viewUsersURL.setParameter("tabs2", "available");
viewUsersURL.setParameter("redirect", currentURL);
viewUsersURL.setParameter("groupId", String.valueOf(group.getGroupId()));

SiteMembershipChecker siteMembershipChecker = new SiteMembershipChecker(renderResponse, group);

SearchContainer searchContainer = new UserSearch(renderRequest, viewUsersURL);
%>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="tabs1" type="hidden" value="users" />
	<aui:input name="tabs2" type="hidden" value="available" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="groupId" type="hidden" value="<%= String.valueOf(group.getGroupId()) %>" />
	<aui:input name="addUserIds" type="hidden" />
	<aui:input name="removeUserIds" type="hidden" />

	<liferay-ui:membership-policy-error />

	<liferay-ui:search-container
		rowChecker="<%= siteMembershipChecker %>"
		searchContainer="<%= searchContainer %>"
		var="userSearchContainer"
	>

		<%
		UserSearchTerms searchTerms = (UserSearchTerms)userSearchContainer.getSearchTerms();

		LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

		if (group.isLimitedToParentSiteMembers()) {
			userParams.put("inherit", Boolean.TRUE);
			userParams.put("usersGroups", Long.valueOf(group.getParentGroupId()));
		}
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
			boolean selectUsers = true;
			%>

			<%@ include file="/user_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />

		<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ASSIGN_MEMBERS) %>">

			<%
			portletURL.setParameter("tabs2", "current");
			portletURL.setParameter("cur", String.valueOf(cur));

			String taglibOnClick = renderResponse.getNamespace() + "updateGroupUsers('" + portletURL.toString() + "');";
			%>

			<aui:button-row>
				<aui:button onClick="<%= taglibOnClick %>" primary="<%= true %>" value="save" />
			</aui:button-row>
		</c:if>
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	function <portlet:namespace />updateGroupUsers(assignmentsRedirect) {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('assignmentsRedirect').val(assignmentsRedirect);
		form.fm('addUserIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		form.fm('removeUserIds').val(Util.listUncheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form, '<portlet:actionURL name="editGroupUsers" />');
	}
</aui:script>