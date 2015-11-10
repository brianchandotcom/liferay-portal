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
String redirect = ParamUtil.getString(request, "redirect");

String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectUser");

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");
String orderByCol = ParamUtil.getString(request, "orderByCol", "first-name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

long teamId = ParamUtil.getLong(request, "teamId");

Team team = TeamLocalServiceUtil.fetchTeam(teamId);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/select_users.jsp");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("eventName", eventName);
portletURL.setParameter("teamId", String.valueOf(teamId));

SearchContainer userSearchContainer = new UserSearch(renderRequest, PortletURLUtil.clone(portletURL, renderResponse));

UserSearchTerms searchTerms = (UserSearchTerms)userSearchContainer.getSearchTerms();

LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

userParams.put("inherit", Boolean.TRUE);

Group group = GroupLocalServiceUtil.getGroup(team.getGroupId());

if (group != null) {
	group = StagingUtil.getLiveGroup(group.getGroupId());
}

userParams.put("usersGroups", group.getGroupId());

int usersCount = UserLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), searchTerms.getStatus(), userParams);

userSearchContainer.setTotal(usersCount);

List<User> users = UserLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), searchTerms.getStatus(), userParams, userSearchContainer.getStart(), userSearchContainer.getEnd(), userSearchContainer.getOrderByComparator());

userSearchContainer.setResults(users);

RowChecker rowChecker = new UserTeamChecker(renderResponse, team);
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav-bar-search>
		<aui:form action="<%= portletURL.toString() %>" name="searchFm">
			<liferay-ui:input-search markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<c:if test="<%= usersCount > 0 %>">
	<liferay-frontend:management-bar
		checkBoxContainerId="usersSearchContainer"
		includeCheckBox="<%= true %>"
	>
		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= portletURL %>"
			/>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= orderByCol %>"
				orderByType="<%= orderByType %>"
				orderColumns='<%= new String[] {"first-name", "screen-name"} %>'
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
</c:if>

<aui:form cssClass="container-fluid-1280" name="selectUserFm">
	<liferay-ui:search-container
		id="users"
		rowChecker="<%= rowChecker %>"
		searchContainer="<%= userSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.model.User"
			cssClass="selectable"
			escapedModel="<%= true %>"
			keyProperty="userId"
			modelVar="user2"
			rowIdProperty="screenName"
		>

			<%
			boolean showActions = false;
			%>

			<%@ include file="/user_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	var A = AUI();

	var <portlet:namespace />userIds = [];

	$('input[name="<portlet:namespace />rowIds"]').on(
		'change',
		function(event) {
			var target = event.target;

			if (target.checked) {
				<portlet:namespace />userIds.push(target.value);
			}
			else {
				A.Array.removeItem(<portlet:namespace />userIds, target.value);
			}

			var event = {};

			if (<portlet:namespace />userIds.length > 0) {
				event = {
					data: {
						value: <portlet:namespace />userIds.join(',')
					}
				};
			}

			Liferay.Util.getOpener().Liferay.fire('<%= HtmlUtil.escapeJS(eventName) %>', event);
		}
	);
</aui:script>