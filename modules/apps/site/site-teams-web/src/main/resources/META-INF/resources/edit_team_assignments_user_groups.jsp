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

String orderByCol = ParamUtil.getString(request, "orderByCol", "name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

String tabs1 = (String)request.getAttribute("edit_team_assignments.jsp-tabs1");
String tabs2 = (String)request.getAttribute("edit_team_assignments.jsp-tabs2");

int cur = (Integer)request.getAttribute("edit_team_assignments.jsp-cur");

String redirect = (String)request.getAttribute("edit_team_assignments.jsp-redirect");

Team team = (Team)request.getAttribute("edit_team_assignments.jsp-team");

Group group = (Group)request.getAttribute("edit_team_assignments.jsp-group");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_team_assignments.jsp-portletURL");

RowChecker rowChecker = new UserGroupTeamChecker(renderResponse, team);
%>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"name", "description"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>

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
	searchContainer="<%= new UserGroupSearch(renderRequest, portletURL) %>"
>
	<portlet:renderURL var="searchURL">
		<portlet:param name="mvcPath" value="/edit_team_assignments.jsp" />
		<portlet:param name="tabs1" value="<%= tabs1 %>" />
		<portlet:param name="tabs2" value="<%= tabs2 %>" />
		<portlet:param name="teamId" value="<%= String.valueOf(team.getTeamId()) %>" />
	</portlet:renderURL>

	<aui:form action="<%= searchURL %>" name="searchFm">
		<liferay-ui:user-group-search-form />
	</aui:form>

	<%
	UserGroupDisplayTerms searchTerms = (UserGroupDisplayTerms)searchContainer.getSearchTerms();

	LinkedHashMap<String, Object> userGroupParams = new LinkedHashMap<String, Object>();

	userGroupParams.put("userGroupsGroups", Long.valueOf(group.getGroupId()));

	if (tabs2.equals("current")) {
		userGroupParams.put("userGroupsTeams", Long.valueOf(team.getTeamId()));
	}
	%>

	<liferay-ui:search-container-results>

		<%
		if (searchTerms.isAdvancedSearch()) {
			total = UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), userGroupParams, searchTerms.isAndOperator());

			searchContainer.setTotal(total);

			results = UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), userGroupParams, searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
		}
		else {
			total = UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams);

			searchContainer.setTotal(total);

			results = UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
		}

		searchContainer.setResults(results);
		%>

	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.UserGroup"
		cssClass="selectable"
		escapedModel="<%= true %>"
		keyProperty="userGroupId"
		modelVar="userGroup"
	>

		<%
		boolean showActions = true;
		%>

		<%@ include file="/user_group_columns.jspf" %>
	</liferay-ui:search-container-row>

	<portlet:actionURL name="editTeamUserGroups" var="editTeamUserGroupsURL" />

	<aui:form action="<%= editTeamUserGroupsURL %>" method="post" name="fm">
		<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
		<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="assignmentsRedirect" type="hidden" />
		<aui:input name="teamId" type="hidden" value="<%= String.valueOf(team.getTeamId()) %>" />
		<aui:input name="addUserGroupIds" type="hidden" />
		<aui:input name="removeUserGroupIds" type="hidden" />

		<div class="separator"><!-- --></div>

		<%
		portletURL.setParameter("cur", String.valueOf(cur));

		String taglibOnClick = renderResponse.getNamespace() + "updateTeamUserGroups('" + portletURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur + "');";
		%>

		<aui:button onClick="<%= taglibOnClick %>" value="update-associations" />

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
	</aui:form>
</liferay-ui:search-container>

<aui:script>
	function <portlet:namespace />updateTeamUserGroups(assignmentsRedirect) {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('assignmentsRedirect').val(assignmentsRedirect);
		form.fm('addUserGroupIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		form.fm('removeUserGroupIds').val(Util.listUncheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form);
	}
</aui:script>