<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "assignees");
String tabs2 = ParamUtil.getString(request, "tabs2", "users");

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

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));

renderResponse.setTitle(passwordPolicy.getName());

PortalUtil.addPortletBreadcrumbEntry(
	request, LanguageUtil.get(request, "password-policies"),
	PortletURLBuilder.createRenderURL(
		renderResponse
	).setMVCPath(
		"/view.jsp"
	).buildString());

PortalUtil.addPortletBreadcrumbEntry(request, passwordPolicy.getName(), null);

EditPasswordPolicyAssignmentsManagementToolbarDisplayContext editPasswordPolicyAssignmentsManagementToolbarDisplayContext = new EditPasswordPolicyAssignmentsManagementToolbarDisplayContext(request, renderRequest, renderResponse, displayStyle, "/edit_password_policy_assignments.jsp");

SearchContainer<?> searchContainer = editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getSearchContainer();
%>

<liferay-util:include page="/edit_password_policy_tabs.jsp" servletContext="<%= application %>" />

<clay:navigation-bar
	navigationItems="<%= passwordPolicyDisplayContext.getEditPasswordPolicyAssignmentsNavigationItems(editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getPortletURL()) %>"
/>

<portlet:renderURL var="selectMembersURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/select_members.jsp" />
	<portlet:param name="tabs1" value="<%= tabs1 %>" />
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
	<portlet:param name="passwordPolicyId" value="<%= String.valueOf(passwordPolicyId) %>" />
</portlet:renderURL>

<clay:management-toolbar
	actionDropdownItems="<%= editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getActionDropdownItems() %>"
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"passwordPolicyName", HtmlUtil.escape(passwordPolicy.getName())
		).put(
			"selectMembersURL", selectMembersURL.toString()
		).build()
	%>'
	clearResultsURL="<%= editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getCreationMenu() %>"
	filterDropdownItems="<%= editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= searchContainer.getTotal() %>"
	orderDropdownItems="<%= editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getOrderByDropdownItems() %>"
	propsTransformer="js/EditPasswordPolicyAssignmentsManagementToolbarPropsTransformer"
	searchActionURL="<%= editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchContainerId="passwordPolicyMembers"
	searchFormName="searchFm"
	selectable="<%= true %>"
	showCreationMenu="<%= true %>"
	showSearch="<%= true %>"
	sortingOrder="<%= searchContainer.getOrderByType() %>"
	sortingURL="<%= editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getViewTypeItems() %>"
/>

<portlet:actionURL name="editPasswordPolicyAssignments" var="editPasswordPolicyAssignmentsURL" />

<aui:form action="<%= editPasswordPolicyAssignmentsURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="passwordPolicyId" type="hidden" value="<%= String.valueOf(passwordPolicy.getPasswordPolicyId()) %>" />

	<div id="breadcrumb">
		<liferay-site-navigation:breadcrumb
			breadcrumbEntries="<%= BreadcrumbEntriesUtil.getBreadcrumbEntries(request, false, false, false, true, true) %>"
		/>
	</div>

	<liferay-ui:search-container
		id="passwordPolicyMembers"
		searchContainer="<%= searchContainer %>"
		var="memberSearchContainer"
	>
		<c:choose>
			<c:when test='<%= tabs2.equals("users") %>'>
				<aui:input name="addUserIds" type="hidden" />
				<aui:input name="removeUserIds" type="hidden" />

				<%@ include file="/user_search_columns.jspf" %>
			</c:when>
			<c:when test='<%= tabs2.equals("organizations") %>'>
				<aui:input name="addOrganizationIds" type="hidden" />
				<aui:input name="removeOrganizationIds" type="hidden" />

				<%@ include file="/organization_search_columns.jspf" %>
			</c:when>
		</c:choose>

		<liferay-ui:search-iterator
			displayStyle="<%= displayStyle %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<%@ include file="/action/delete_password_policy.jspf" %>