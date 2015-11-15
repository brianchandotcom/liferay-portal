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
String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

String tabs1 = (String)request.getAttribute("edit_team_assignments.jsp-tabs1");
String tabs2 = (String)request.getAttribute("edit_team_assignments.jsp-tabs2");

int cur = (Integer)request.getAttribute("edit_team_assignments.jsp-cur");

String redirect = (String)request.getAttribute("edit_team_assignments.jsp-redirect");

Group group = (Group)request.getAttribute("edit_team_assignments.jsp-group");

Team team = (Team)request.getAttribute("edit_team_assignments.jsp-team");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_team_assignments.jsp-portletURL");

RowChecker rowChecker = new UserTeamChecker(renderResponse, team);
%>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<liferay-ui:tabs
	names="current,available"
	param="tabs2"
	portletURL="<%= portletURL %>"
/>

<liferay-ui:search-container
	rowChecker="<%= rowChecker %>"
	searchContainer="<%= new UserSearch(renderRequest, portletURL) %>"
	var="userSearchContainer"
>
	<portlet:renderURL var="searchURL">
		<portlet:param name="mvcPath" value="/edit_team_assignments.jsp" />
		<portlet:param name="tabs1" value="<%= tabs1 %>" />
		<portlet:param name="tabs2" value="<%= tabs2 %>" />
		<portlet:param name="teamId" value="<%= String.valueOf(team.getTeamId()) %>" />
	</portlet:renderURL>

	<aui:form action="<%= searchURL %>" name="searchFm">
		<liferay-ui:user-search-form />
	</aui:form>

	<%
	UserSearchTerms searchTerms = (UserSearchTerms)userSearchContainer.getSearchTerms();

	LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

	userParams.put("inherit", Boolean.TRUE);
	userParams.put("usersGroups", group.getGroupId());

	if (tabs2.equals("current")) {
		userParams.put("usersTeams", team.getTeamId());
	}
	%>

	<liferay-ui:search-container-results>

		<%
		if (searchTerms.isAdvancedSearch()) {
			total = UserLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getFirstName(), searchTerms.getMiddleName(), searchTerms.getLastName(), searchTerms.getScreenName(), searchTerms.getEmailAddress(), searchTerms.getStatus(), userParams, searchTerms.isAndOperator());

			userSearchContainer.setTotal(total);

			results = UserLocalServiceUtil.search(company.getCompanyId(), searchTerms.getFirstName(), searchTerms.getMiddleName(), searchTerms.getLastName(), searchTerms.getScreenName(), searchTerms.getEmailAddress(), searchTerms.getStatus(), userParams, searchTerms.isAndOperator(), userSearchContainer.getStart(), userSearchContainer.getEnd(), userSearchContainer.getOrderByComparator());
		}
		else {
			total = UserLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), searchTerms.getStatus(), userParams);

			userSearchContainer.setTotal(total);

			results = UserLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), searchTerms.getStatus(), userParams, userSearchContainer.getStart(), userSearchContainer.getEnd(), userSearchContainer.getOrderByComparator());
		}

		userSearchContainer.setResults(results);
		%>

	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.User"
		cssClass="selectable"
		escapedModel="<%= true %>"
		keyProperty="userId"
		modelVar="user2"
		rowIdProperty="screenName"
	>

		<%
		boolean showActions = true;
		%>

		<%@ include file="/user_columns.jspf" %>
	</liferay-ui:search-container-row>

	<portlet:actionURL name="editTeamUsers" var="editTeamUsersURL" />

	<aui:form action="<%= editTeamUsersURL %>" method="post" name="fm">
		<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
		<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="assignmentsRedirect" type="hidden" />
		<aui:input name="teamId" type="hidden" value="<%= String.valueOf(team.getTeamId()) %>" />
		<aui:input name="addUserIds" type="hidden" />
		<aui:input name="removeUserIds" type="hidden" />

		<div class="separator"><!-- --></div>

		<%
		portletURL.setParameter("cur", String.valueOf(cur));

		String taglibOnClick = renderResponse.getNamespace() + "updateTeamUsers('" + portletURL.toString() + "');";
		%>

		<aui:button onClick="<%= taglibOnClick %>" value="update-associations" />

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
	</aui:form>
</liferay-ui:search-container>

<aui:script>
	function <portlet:namespace />updateTeamUsers(assignmentsRedirect) {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('assignmentsRedirect').val(assignmentsRedirect);
		form.fm('addUserIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		form.fm('removeUserIds').val(Util.listUncheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form);
	}
</aui:script>