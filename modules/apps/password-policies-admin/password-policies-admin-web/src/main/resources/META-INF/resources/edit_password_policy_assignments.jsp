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
String tabs1 = ParamUtil.getString(request, "tabs1", "assignees");
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

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/edit_password_policy_assignments.jsp");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("passwordPolicyId", String.valueOf(passwordPolicy.getPasswordPolicyId()));

request.setAttribute("edit_password_policy_assignments.jsp-portletURL", portletURL);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(passwordPolicy.getName());

String[] orderColumns = {"first-name", "last-name", "screen-name"};
RowChecker rowChecker = new DeleteUserPasswordPolicyChecker(renderResponse, passwordPolicy);
PortletURL searchURL = PortletURLUtil.clone(portletURL, renderResponse);
SearchContainer searchContainer = new UserSearch(renderRequest, searchURL);

if (tabs2.equals("organizations")) {
	orderColumns = new String[] {"name", "type"};
	searchContainer = new OrganizationSearch(renderRequest, searchURL);
	rowChecker = new DeleteOrganizationPasswordPolicyChecker(renderResponse, passwordPolicy);
}

PortletURL homeURL = renderResponse.createRenderURL();

homeURL.setParameter("mvcPath", "/view.jsp");

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "password-policies"), homeURL.toString());
PortalUtil.addPortletBreadcrumbEntry(request, passwordPolicy.getName(), null);
%>

<liferay-util:include page="/edit_password_policy_tabs.jsp" servletContext="<%= application %>" />

<liferay-ui:tabs
	names="users,organizations"
	param="tabs2"
	type="tabs nav-tabs-default"
	url="<%= portletURL.toString() %>"
/>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="passwordPolicyMembers"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= searchContainer.getOrderByCol() %>"
			orderByType="<%= searchContainer.getOrderByType() %>"
			orderColumns="<%= orderColumns %>"
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
		/>

		<c:if test='<%= passwordPolicyDisplayContext.hasAssignMembersPermission() && tabs1.equals("assignees") %>'>
			<li>
				<aui:form action="<%= portletURL.toString() %>" name="searchFm">
					<liferay-ui:input-search
						markupView="lexicon"
					/>
				</aui:form>
			</li>
		</c:if>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>

		<liferay-frontend:add-menu
			inline="<%= true %>"
		>
			<liferay-frontend:add-menu-item
				id="addAssignees"
				title='<%= LanguageUtil.get(request, "add-assignees") %>'
				url="javascript:;"
			/>
		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>

		<%
		String taglibURL = "javascript:;";

		if (tabs2.equals("users")) {
			taglibURL = "javascript:" + renderResponse.getNamespace() + "deleteUsers();";
		}
		else if (tabs2.equals("organizations")) {
			taglibURL = "javascript:" + renderResponse.getNamespace() + "deleteOrganizations();";
		}
		%>

		<liferay-frontend:management-bar-button
			href="<%= taglibURL %>"
			icon="trash"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="editPasswordPolicyAssignments" var="editPasswordPolicyAssignmentsURL" />

<aui:form action="<%= editPasswordPolicyAssignmentsURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="passwordPolicyId" type="hidden" value="<%= String.valueOf(passwordPolicy.getPasswordPolicyId()) %>" />

	<div id="breadcrumb">
		<liferay-ui:breadcrumb
			showCurrentGroup="<%= false %>"
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showPortletBreadcrumb="<%= true %>"
		/>
	</div>

	<c:choose>
		<c:when test='<%= tabs2.equals("users") %>'>
			<aui:input name="addUserIds" type="hidden" />
			<aui:input name="removeUserIds" type="hidden" />

			<liferay-ui:search-container
				id="passwordPolicyMembers"
				rowChecker="<%= rowChecker %>"
				searchContainer="<%= searchContainer %>"
				var="userSearchContainer"
			>

				<%
				UserSearchTerms searchTerms = (UserSearchTerms)userSearchContainer.getSearchTerms();

				if (!searchTerms.isSearch()) {
					userSearchContainer.setEmptyResultsMessageCssClass("taglib-empty-result-message-header-has-plus-btn");
				}

				LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

				userParams.put("usersPasswordPolicies", Long.valueOf(passwordPolicy.getPasswordPolicyId()));
				%>

				<%@ include file="/user_search_columns.jspf" %>

				<liferay-ui:search-iterator
					displayStyle="<%= displayStyle %>"
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</c:when>
		<c:when test='<%= tabs2.equals("organizations") %>'>
			<aui:input name="addOrganizationIds" type="hidden" />
			<aui:input name="removeOrganizationIds" type="hidden" />

			<liferay-ui:search-container
				id="passwordPolicyMembers"
				rowChecker="<%= rowChecker %>"
				searchContainer="<%= searchContainer %>"
				var="organizationSearchContainer"
			>

				<%
				OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)organizationSearchContainer.getSearchTerms();

				if (!searchTerms.isSearch()) {
					organizationSearchContainer.setEmptyResultsMessageCssClass("taglib-empty-result-message-header-has-plus-btn");
				}

				LinkedHashMap<String, Object> organizationParams = new LinkedHashMap<String, Object>();

				organizationParams.put("organizationsPasswordPolicies", Long.valueOf(passwordPolicy.getPasswordPolicyId()));
				%>

				<%@ include file="/organization_search_columns.jspf" %>

				<liferay-ui:search-iterator
					displayStyle="<%= displayStyle %>"
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</c:when>
	</c:choose>
</aui:form>

<aui:script use="liferay-item-selector-dialog">
	var Util = Liferay.Util;

	var $ = AUI.$;

	var form = $(document.<portlet:namespace />fm);

	<portlet:renderURL var="selectMembersURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcPath" value="/select_members.jsp" />
		<portlet:param name="tabs1" value="<%= tabs1 %>" />
		<portlet:param name="tabs2" value="<%= tabs2 %>" />
		<portlet:param name="passwordPolicyId" value="<%= String.valueOf(passwordPolicyId) %>" />
	</portlet:renderURL>

	$('#<portlet:namespace />addAssignees').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: '<portlet:namespace />selectMember',
					on: {
						selectedItemChange: function(event) {
							var result = event.newVal;

							if (result && result.item) {
								if (result.memberType == 'users') {
									form.fm('addUserIds').val(result.item);
								}
								else if (result.memberType == 'organizations') {
									form.fm('addOrganizationIds').val(result.item);
								}

								submitForm(form);
							}
						}
					},
					title: '<liferay-ui:message arguments="<%= HtmlUtil.escape(passwordPolicy.getName()) %>" key="add-assignees-to-x" />',
					url: '<%= selectMembersURL %>'
				}
			);

			itemSelectorDialog.open();
		}
	);

	window.<portlet:namespace />deleteOrganizations = function() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			form.fm('removeOrganizationIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	};

	window.<portlet:namespace />deleteUsers = function() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			form.fm('removeUserIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	};
</aui:script>