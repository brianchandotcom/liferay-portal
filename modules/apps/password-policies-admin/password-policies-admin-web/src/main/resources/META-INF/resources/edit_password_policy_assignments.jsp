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
String tabs3 = ParamUtil.getString(request, "tabs3", "current");

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");
String redirect = ParamUtil.getString(request, "redirect");

long passwordPolicyId = ParamUtil.getLong(request, "passwordPolicyId");

PasswordPolicy passwordPolicy = PasswordPolicyLocalServiceUtil.fetchPasswordPolicy(passwordPolicyId);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/edit_password_policy_assignments.jsp");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("passwordPolicyId", String.valueOf(passwordPolicy.getPasswordPolicyId()));

PortalUtil.addPortletBreadcrumbEntry(request, passwordPolicy.getName(), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "assign-members"), portletURL.toString());

portletURL.setParameter("tabs2", tabs2);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, tabs2), portletURL.toString());

portletURL.setParameter("tabs3", tabs3);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(passwordPolicy.getName());

SearchContainer searchContainer = new SearchContainer();

if (tabs2.equals("users")) {
	searchContainer = new UserSearch(renderRequest, portletURL);
}
else if (tabs2.equals("organizations")) {
	searchContainer = new OrganizationSearch(renderRequest, portletURL);
}
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		PortletURL usersURL = PortletURLUtil.clone(portletURL, renderResponse);

		usersURL.setParameter("tabs2", "users");
		%>

		<aui:nav-item href="<%= usersURL.toString() %>" label="users" selected='<%= tabs2.equals("users") %>' />

		<%
		PortletURL userGroupsURL = PortletURLUtil.clone(portletURL, renderResponse);

		userGroupsURL.setParameter("tabs2", "organizations");
		%>

		<aui:nav-item href="<%= userGroupsURL.toString() %>" label="organizations" selected='<%= tabs2.equals("organizations") %>' />
	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= portletURL.toString() %>" name="searchFm">
			<liferay-ui:input-search markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="passwordPolicyMembers"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= searchContainer.getOrderByCol() %>"
			orderByType="<%= searchContainer.getOrderByType() %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<portlet:actionURL name="editPasswordPolicyAssignments" var="editPasswordPolicyAssignmentsURL" />

<aui:form action="<%= editPasswordPolicyAssignmentsURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="tabs3" type="hidden" value="<%= tabs3 %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="passwordPolicyId" type="hidden" value="<%= String.valueOf(passwordPolicy.getPasswordPolicyId()) %>" />

	<c:choose>
		<c:when test='<%= tabs2.equals("users") %>'>
			<aui:input name="addUserIds" type="hidden" />
			<aui:input name="removeUserIds" type="hidden" />

			<liferay-ui:tabs
				names="current,available"
				param="tabs3"
				url="<%= portletURL.toString() %>"
			/>

			<liferay-ui:search-container
				id="passwordPolicyMembers"
				rowChecker="<%= new UserPasswordPolicyChecker(renderResponse, passwordPolicy) %>"
				searchContainer="<%= searchContainer %>"
				var="userSearchContainer"
			>

				<%
				UserSearchTerms searchTerms = (UserSearchTerms)userSearchContainer.getSearchTerms();

				LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

				if (tabs3.equals("current")) {
					userParams.put("usersPasswordPolicies", Long.valueOf(passwordPolicy.getPasswordPolicyId()));
				}
				%>

				<liferay-ui:user-search-container-results
					useIndexer="<%= false %>"
					userParams="<%= userParams %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portal.model.User"
					escapedModel="<%= true %>"
					keyProperty="userId"
					modelVar="user2"
					rowIdProperty="screenName"
				>
					<liferay-ui:search-container-column-text
						name="name"
					>

						<%= user2.getFullName() %>

						<%
						PasswordPolicyRel passwordPolicyRel = PasswordPolicyRelLocalServiceUtil.fetchPasswordPolicyRel(User.class.getName(), user.getUserId());
						%>

						<c:if test="<%= (passwordPolicyRel != null) && (passwordPolicyRel.getPasswordPolicyId() != passwordPolicy.getPasswordPolicyId()) %>">

							<%
							PasswordPolicy curPasswordPolicy = PasswordPolicyLocalServiceUtil.getPasswordPolicy(passwordPolicyRel.getPasswordPolicyId());
							%>

							<portlet:renderURL var="assignMembersURL">
								<portlet:param name="mvcPath" value="/edit_password_policy_assignments.jsp" />
								<portlet:param name="tabs1" value="<%= tabs1 %>" />
								<portlet:param name="tabs2" value="users" />
								<portlet:param name="tabs3" value="current" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="passwordPolicyId" value="<%= String.valueOf(curPasswordPolicy.getPasswordPolicyId()) %>" />
							</portlet:renderURL>

							<liferay-ui:icon-help message='<%= LanguageUtil.format(request, "this-user-is-already-assigned-to-password-policy-x", new Object[] {assignMembersURL, curPasswordPolicy.getName()}, false) %>' />
						</c:if>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="screen-name"
						value="<%= user2.getScreenName() %>"
					/>
				</liferay-ui:search-container-row>

				<div class="separator"><!-- --></div>

				<%
				String taglibOnClick = renderResponse.getNamespace() + "updatePasswordPolicyUsers();";
				%>

				<aui:button onClick="<%= taglibOnClick %>" value="update-associations" />

				<liferay-ui:search-iterator markupView="lexicon" />
			</liferay-ui:search-container>
		</c:when>
		<c:when test='<%= tabs2.equals("organizations") %>'>
			<aui:input name="addOrganizationIds" type="hidden" />
			<aui:input name="removeOrganizationIds" type="hidden" />

			<liferay-ui:tabs
				names="current,available"
				param="tabs3"
				url="<%= portletURL.toString() %>"
			/>

			<liferay-ui:search-container
				id="passwordPolicyMembers"
				rowChecker="<%= new OrganizationPasswordPolicyChecker(renderResponse, passwordPolicy) %>"
				searchContainer="<%= searchContainer %>"
				var="organizationSearchContainer"
			>

				<%
				OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)organizationSearchContainer.getSearchTerms();

				LinkedHashMap<String, Object> organizationParams = new LinkedHashMap<String, Object>();

				if (tabs3.equals("current")) {
					organizationParams.put("organizationsPasswordPolicies", Long.valueOf(passwordPolicy.getPasswordPolicyId()));
				}
				%>

				<liferay-ui:organization-search-container-results
					organizationParams="<%= organizationParams %>"
					parentOrganizationId="<%= OrganizationConstants.ANY_PARENT_ORGANIZATION_ID %>"
					useIndexer="<%= false %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portal.model.Organization"
					escapedModel="<%= true %>"
					keyProperty="organizationId"
					modelVar="organization"
				>
					<liferay-ui:search-container-column-text
						name="name"
						orderable="<%= true %>"
					>

						<%= organization.getName() %>

						<%
						PasswordPolicyRel passwordPolicyRel = PasswordPolicyRelLocalServiceUtil.fetchPasswordPolicyRel(Organization.class.getName(), organization.getOrganizationId());
						%>

						<c:if test="<%= (passwordPolicyRel != null) && (passwordPolicyRel.getPasswordPolicyId() != passwordPolicy.getPasswordPolicyId()) %>">

							<%
							PasswordPolicy curPasswordPolicy = PasswordPolicyLocalServiceUtil.getPasswordPolicy(passwordPolicyRel.getPasswordPolicyId());
							%>

							<portlet:renderURL var="assignMembersURL">
								<portlet:param name="mvcPath" value="/edit_password_policy_assignments.jsp" />
								<portlet:param name="tabs1" value="<%= tabs1 %>" />
								<portlet:param name="tabs2" value="organizations" />
								<portlet:param name="tabs3" value="current" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="passwordPolicyId" value="<%= String.valueOf(curPasswordPolicy.getPasswordPolicyId()) %>" />
							</portlet:renderURL>

							<liferay-ui:icon-help message='<%= LanguageUtil.format(request, "this-organization-is-already-assigned-to-password-policy-x", new Object[] {assignMembersURL, curPasswordPolicy.getName()}, false) %>' />
						</c:if>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="parent-organization"
						value="<%= HtmlUtil.escape(organization.getParentOrganizationName()) %>"
					/>

					<liferay-ui:search-container-column-text
						name="type"
						orderable="<%= true %>"
						value="<%= LanguageUtil.get(request, organization.getType()) %>"
					/>

					<liferay-ui:search-container-column-text
						name="city"
						value="<%= HtmlUtil.escape(organization.getAddress().getCity()) %>"
					/>

					<liferay-ui:search-container-column-text
						name="region"
						value="<%= UsersAdmin.ORGANIZATION_REGION_NAME_ACCESSOR.get(organization) %>"
					/>

					<liferay-ui:search-container-column-text
						name="country"
						value="<%= UsersAdmin.ORGANIZATION_COUNTRY_NAME_ACCESSOR.get(organization) %>"
					/>
				</liferay-ui:search-container-row>

				<div class="separator"><!-- --></div>

				<%
				String taglibOnClick = renderResponse.getNamespace() + "updatePasswordPolicyOrganizations();";
				%>

				<aui:button onClick="<%= taglibOnClick %>" value="update-associations" />

				<liferay-ui:search-iterator markupView="lexicon" />
			</liferay-ui:search-container>
		</c:when>
	</c:choose>
</aui:form>

<aui:script>
	function <portlet:namespace />updatePasswordPolicyOrganizations() {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('addOrganizationIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		form.fm('removeOrganizationIds').val(Util.listUncheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form);
	}

	function <portlet:namespace />updatePasswordPolicyUsers() {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('addUserIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		form.fm('removeUserIds').val(Util.listUncheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form);
	}
</aui:script>