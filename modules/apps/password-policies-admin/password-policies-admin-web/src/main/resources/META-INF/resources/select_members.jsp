<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1");
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

String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectMember");

EditPasswordPolicyAssignmentsManagementToolbarDisplayContext editPasswordPolicyAssignmentsManagementToolbarDisplayContext = new EditPasswordPolicyAssignmentsManagementToolbarDisplayContext(request, renderRequest, renderResponse, displayStyle, "/select_members.jsp");

SearchContainer<?> searchContainer = editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getSearchContainer();
%>

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

<aui:form cssClass="container-fluid container-fluid-max-xl" name="selectMemberFm">
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

	searchContainer.on('rowToggled', (event) => {
		var selectedItems = event.elements.allSelectedElements;

		var result = {};

		if (selectedItems.size()) {
			result = {
				data: {
					item: selectedItems.attr('value').join(','),
					memberType: '<%= HtmlUtil.escapeJS(tabs2) %>',
				},
			};
		}

		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(eventName) %>',
			result
		);
	});
</aui:script>