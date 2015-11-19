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
String tabs2 = (String)request.getAttribute("edit_site_assignments.jsp-tabs2");

int cur = (Integer)request.getAttribute("edit_site_assignments.jsp-cur");

String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	redirect = portletURL.toString();
}

Group group = (Group)request.getAttribute("edit_site_assignments.jsp-group");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_site_assignments.jsp-portletURL");

PortletURL viewUserGroupsURL = renderResponse.createRenderURL();

viewUserGroupsURL.setParameter("mvcPath", "/view.jsp");
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

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "submit();" %>'>
	<aui:input name="tabs1" type="hidden" value="user-groups" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="groupId" type="hidden" value="<%= String.valueOf(group.getGroupId()) %>" />
	<aui:input name="addUserGroupIds" type="hidden" />
	<aui:input name="removeUserGroupIds" type="hidden" />

	<liferay-ui:search-container
		rowChecker="<%= userGroupGroupChecker %>"
		searchContainer="<%= userGroupSearch %>"
	>
		<c:if test='<%= !tabs1.equals("summary") %>'>
			<liferay-ui:input-search />

			<div class="separator"><!-- --></div>
		</c:if>

		<%
		UserGroupDisplayTerms searchTerms = (UserGroupDisplayTerms)searchContainer.getSearchTerms();

		LinkedHashMap<String, Object> userGroupParams = new LinkedHashMap<String, Object>();

		if (tabs1.equals("summary") || tabs2.equals("current")) {
			userGroupParams.put("userGroupsGroups", Long.valueOf(group.getGroupId()));
		}
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

			<%@ include file="/user_group_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-util:buffer var="formButton">
			<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ASSIGN_MEMBERS) %>">
				<c:choose>
					<c:when test='<%= tabs2.equals("current") %>'>

						<%
						viewUserGroupsURL.setParameter("tabs2", "available");
						%>

						<liferay-frontend:add-menu>
							<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "assign-user-groups") %>' url="<%= viewUserGroupsURL.toString() %>" />
						</liferay-frontend:add-menu>

						<%
						viewUserGroupsURL.setParameter("tabs2", "current");
						%>

					</c:when>
					<c:otherwise>

						<%
						portletURL.setParameter("tabs2", "current");
						portletURL.setParameter("cur", String.valueOf(cur));

						String taglibOnClick = renderResponse.getNamespace() + "updateGroupUserGroups('" + portletURL.toString() + "');";
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
				<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" persistState="<%= true %>" title='<%= LanguageUtil.format(request, (total > 1) ? "x-user-groups" : "x-user-group", total, false) %>'>
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
				<c:if test="<%= PropsValues.SEARCH_CONTAINER_SHOW_PAGINATION_TOP && (results.size() > PropsValues.SEARCH_CONTAINER_SHOW_PAGINATION_TOP_DELTA) %>">
					<%= formButton %>
				</c:if>

				<liferay-ui:search-iterator />

				<%= formButton %>
			</c:when>
		</c:choose>
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	function <portlet:namespace />submit() {
		var form = AUI.$(document.<portlet:namespace />fm);

		var keywords = form.fm('<%= DisplayTerms.KEYWORDS %>_user_groups').val();

		var url = '<portlet:renderURL><portlet:param name="tabs1" value="user-groups" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" /></portlet:renderURL>';

		if (keywords) {
			form.fm('<%= DisplayTerms.KEYWORDS %>').val(keywords);
		}

		submitForm(form, url);
	}

	function <portlet:namespace />updateGroupUserGroups(assignmentsRedirect) {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('assignmentsRedirect').val(assignmentsRedirect);
		form.fm('addUserGroupIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		form.fm('removeUserGroupIds').val(Util.listUncheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form, '<portlet:actionURL name="editGroupUserGroups" />');
	}
</aui:script>