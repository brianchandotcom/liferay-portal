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
String tabs1 = ParamUtil.getString(request, "tabs1");
String tabs2 = ParamUtil.getString(request, "tabs2", "users");

String redirect = ParamUtil.getString(request, "redirect");

long passwordPolicyId = ParamUtil.getLong(request, "passwordPolicyId");

PasswordPolicy passwordPolicy = PasswordPolicyLocalServiceUtil.fetchPasswordPolicy(passwordPolicyId);

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PasswordPoliciesAdminPortletKeys.PASSWORD_POLICIES_ADMIN, "display-style", "list");
}
else {
	portalPreferences.setValue(PasswordPoliciesAdminPortletKeys.PASSWORD_POLICIES_ADMIN, "display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}

String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectMember");

EditPasswordPolicyAssignmentsManagementToolbarDisplayContext editPasswordPolicyAssignmentsManagementToolbarDisplayContext = new EditPasswordPolicyAssignmentsManagementToolbarDisplayContext(request, renderRequest, renderResponse, displayStyle, "/select_members.jsp");

SearchContainer searchContainer = editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getSearchContainer();
%>

<clay:navigation-bar
	navigationItems="<%= passwordPolicyDisplayContext.getSelectMembersNavigationItems() %>"
/>

<clay:management-toolbar
	clearResultsURL="<%= editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getClearResultsURL() %>"
	filterDropdownItems="<%= editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= searchContainer.getTotal() %>"
	searchActionURL="<%= editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchContainerId="passwordPolicyMembers"
	searchFormName="searchFm"
	selectable="<%= true %>"
	showSearch="<%= true %>"
	sortingOrder="<%= searchContainer.getOrderByType() %>"
	sortingURL="<%= editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getViewTypeItems() %>"
/>

<aui:form cssClass="container-fluid-1280" name="selectMemberFm">
	<liferay-ui:search-container
		id="passwordPolicyMembers"
		searchContainer="<%= searchContainer %>"
		var="memberSearchContainer"
	>
		<c:choose>
			<c:when test='<%= tabs2.equals("users") %>'>
				<%@ include file="/user_search_columns.jspf" %>
			</c:when>
			<c:when test='<%= tabs2.equals("organizations") %>'>
				<%@ include file="/organization_search_columns.jspf" %>
			</c:when>
		</c:choose>

		<liferay-ui:search-iterator
			displayStyle="<%= displayStyle %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />' + 'passwordPolicyMembers'
	);

	searchContainer.on('rowToggled', function(event) {
		var selectedItems = event.elements.allSelectedElements;

		var result = {};

		if (selectedItems.size()) {
			result = {
				data: {
					item: selectedItems.attr('value').join(','),
					memberType: '<%= HtmlUtil.escapeJS(tabs2) %>'
				}
			};
		}

		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(eventName) %>',
			result
		);
	});
</aui:script>